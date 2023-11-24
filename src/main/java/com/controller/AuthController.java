package com.controller;


import com.dao.EmailReq;
import com.dao.ResetPasswordModel;
import com.entity.User;
import com.model.JwtResponse;
import com.model.LoginAuthReq;
import com.model.UserRequest;
import com.response.SuccessResponse;
import com.service.AuthService;
import com.service.MailService;
import com.service.UserService;
import com.service.ValidatorService;
import com.util.GenerateRandom;
import com.util.JwtUtils;
import com.util.MailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorService validator;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MailTemplate mailTemplate;

    @Autowired
    private MailService mailService;


    @Value("${BASE_URL}")
    private String BASEURL;


//    @Autowired private ModelMapper

    @PostMapping("/login")// token btw
    public ResponseEntity<SuccessResponse<String>> login(@RequestBody LoginAuthReq req) {
        validator.validate(req);
        boolean isExists = authService.auth(req);
        if (!isExists) {
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

//    @PreAuthorize("hasRole("ROLE_ADMIN")")
//    @PostAuthorize("")
    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<User>> register(@RequestBody UserRequest req) {
        validator.validate(req);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<User>builder()
                        .data(userService.create(req))
                        .message("Registered success").build()
        );
    }

    @PostMapping(value = "/auth")
    public String generateToken( @RequestBody LoginAuthReq req) throws Exception {
        validator.validate(req);
        boolean isExists = authService.auth(req);
        if (!isExists) {
            return "User unauthorized";
        }

        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            req.getEmail(), req.getPassword()
//                    ));

        } catch (Exception e) {
            throw new Exception("Invalid");
        }
        return jwtUtils.generateToken(req.getUsername());

    }

    @PostMapping(value = "/token")
    public ResponseEntity<?> generateToken2(@RequestBody LoginAuthReq req) throws Exception {
        validator.validate(req);
        boolean isExists = authService.auth(req);
        if (!isExists) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account is Unauthorized");}
        return ResponseEntity.status(HttpStatus.OK).body(jwtUtils.generateToken2(req.getUsername()));
    }

    @PostMapping(path = "send-otp",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<String>> sendOtp(@RequestBody EmailReq req) {
        this.validator.validate(req);
        User user = userService.getByEmail(req.getEmail());

        String template = "";

        if (StringUtils.isEmpty(user.getOtp())) {
            String otp = GenerateRandom.getRandom() + GenerateRandom.getRandom(1);
            Date now =  new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, Math.floorDiv(3600, 2));
            Date expiration = calendar.getTime();
            user.setOtp(otp);
            template = mailTemplate.getRegisterTemplate(user.getUsername(), otp);
            userService.save(user);

        }else {
            template = mailTemplate.getRegisterTemplate(user.getUsername(), user.getOtp());
        }

        mailService.sendAsync(user.getEmail(), "Register", template);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<String>builder()
                        .message("Otp has been sent")
                        .build()
        );

    }

    @PostMapping("forget-password")
    public ResponseEntity<SuccessResponse<String>> forgetPassword( @RequestBody ResetPasswordModel req) {
        validator.validate(req);
        Map<String, Object> status = userService.updatePassword(req);

        return ResponseEntity.status((HttpStatus) status.get("code")).body(
                SuccessResponse.<String>builder()
                        .message((String) status.get("message"))
                        .build()
        );
    }

}
