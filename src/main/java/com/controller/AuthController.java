package com.controller;


import com.model.Response;
import com.model.UserRequest;
import com.service.AuthService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<UserRequest>> violations;
    Map<String, String> res =  new HashMap<>();

    @PostMapping("/login")
    public Response<Map<String, String>> login( @RequestBody UserRequest req) {
        violations = validator.validate(req);
        if(!violations.isEmpty()){
            res.put("status", "fail");
            res.put("message", "email or password are invalid");
            res.put("code", String.valueOf(HttpStatus.BAD_REQUEST));
            violations.forEach(v -> res.put(String.valueOf(v.getPropertyPath()), v.getMessage()));
            return Response.<Map<String, String>>builder().data(res).build();
        }
        boolean isExists = authService.auth(req);
        if (!isExists){
            res.put("status", "fail");
            res.put("message", "email or password doesnt match to our records");
            res.put("code", String.valueOf(HttpStatus.NOT_FOUND));
            return Response.<Map<String, String>>builder().data(res).build();
        }
        res.put("status", "success");
        res.put("message", "login success");
        res.put("code", String.valueOf(HttpStatus.OK));

        return Response.<Map<String, String>>builder().data(res).build();
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Map<String, String>> register(@Valid @RequestBody UserRequest req) {
        violations = validator.validate(req);
        if(!violations.isEmpty()){
            res.put("status", "fail");
            res.put("code", String.valueOf(HttpStatus.BAD_REQUEST));
            violations.forEach(v -> res.put(String.valueOf(v.getPropertyPath()), v.getMessage()));
        return Response.<Map<String, String>>builder().data(res).build();
        }

        userService.create(req);
        res.put("status", "success");
        res.put("message", "register success");
        res.put("code", String.valueOf(HttpStatus.OK));
        return Response.<Map<String, String>>builder().data(res).build();
    }

}
