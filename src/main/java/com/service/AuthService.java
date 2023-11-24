package com.service;


import com.entity.User;
import com.model.LoginAuthReq;
import com.model.UserRequest;
import com.repository.AuthRepository;
import com.repository.UserRepository;
import com.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userservice;

    public Boolean auth(LoginAuthReq req) {
        User user = (User) userservice.loadUserByUsername(req.getUsername());
        return user != null && BCrypt.checkpw(req.getPassword(), user.getPassword());
//        return user.filter(value -> BCrypt.checkpw(req.getPassword(), value.getPassword())).isPresent();
    }
}
