package com.service;


import com.entity.User;
import com.model.UserRequest;
import com.repository.AuthRepository;
import com.repository.UserRepository;
import com.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Boolean auth(UserRequest req) {
        User user = userRepository.findByEmail(req.getEmail());
        if (user == null) return false;
        return BCrypt.checkpw(req.getPassword(), user.getPassword());
    }
}
