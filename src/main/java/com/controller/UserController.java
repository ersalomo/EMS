package com.controller;


import com.dao.UserParamReq;
import com.entity.User;
import com.response.Response;
import com.response.SuccessResponse;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Response<Page<User>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        UserParamReq paramReq = new UserParamReq();
        paramReq.setUsername(username);
        paramReq.setEmail(email);
        paramReq.setSize(size);
        paramReq.setPage(page);
        return Response.<Page<User>>builder().data(userService.findAll(paramReq)).build();
    }
    @GetMapping(value = "/all-users")
    public Response<Page<User>> findAllUsers(
            @ModelAttribute UserParamReq userParamReq
            ) {

        return Response.<Page<User>>builder().data(userService.findAll(userParamReq)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<String>builder().message("User delete").build()
        );
    }
}
