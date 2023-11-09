package com.controller;


import com.entity.User;
import com.response.Response;
import com.model.UserRequest;
import com.response.SuccessResponse;
import com.service.AuthService;
import com.service.UserService;
import com.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorService validator;

    @PostMapping("/login")// token btw
    public ResponseEntity<SuccessResponse<String>> login(@RequestBody UserRequest req) {
        validator.validate(req);
        boolean isExists = authService.auth(req);
        if (!isExists){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    SuccessResponse.<String>builder()
                            .status("fail")
                            .statusCode(HttpStatus.UNAUTHORIZED)
                            .message("Your data doesn't match in our records").build()
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<String>builder()
                        .message("Login success").build()
        );
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<User>> register(@RequestBody UserRequest req) {
        validator.validate(req);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<User>builder()
                        .data(userService.create(req))
                        .message("Login success").build()
        );
    }

}
