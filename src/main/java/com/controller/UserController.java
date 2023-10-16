package com.controller;


import com.entity.User;
import com.model.Response;
import com.model.UserRequest;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> register( @Valid @RequestBody UserRequest req) {
        userService.create(req);
        return Response.<String>builder().data("Account created").build();
    }

    @GetMapping
    public Response<List<User>> getUsers() {
        return Response.<List<User>>builder().data(userService.findAll()).build();
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return Response.<String>builder().data("Data berhasil dihapus").build();
    }
}
