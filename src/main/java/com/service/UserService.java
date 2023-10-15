package com.service;

import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void create(){
        if(userRepository.existsById("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exists");

        }
        User user = new User();

        user.setEmail("");
        user.setPassword("");

        userRepository.save(user);
    }
}
