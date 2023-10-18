package com.service;

import com.entity.User;
import com.model.UserRequest;
import com.repository.UserRepository;
import com.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void create(UserRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already exists :)");

        }
        User user = new User();
        user.setEmail(req.getEmail());
        String hashedPwd = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPwd);
        userRepository.save(user);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found :)");
        }
        userRepository.deleteById(id);
    }
}
