package com.service;

import com.dao.UserParamReq;
import com.entity.User;
import com.model.UserRequest;
import com.repository.UserRepository;
import com.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    }
    @Transactional
    public User create(UserRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already exists :)");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        String hashedPwd = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPwd);
        return userRepository.save(user);

    }

    public Page<User> findAll(UserParamReq req) {
        Pageable paging = PageRequest.of(req.getPage(), req.getSize());
        if (req.getUsername() != null) {
            String username = "%" + req.getUsername() + "%";
            if (req.getEmail() != null) {
                String email = "%" + req.getEmail() + "%";
                return userRepository.findByUsernameLikeOrEmailLike(username, email, paging);
            }
            return userRepository.findByUsernameLike(username, paging);
        }

        if (req.getEmail() != null) {
            String email = "%" + req.getEmail() + "%";
            return userRepository.findAllByEmailLike(email, paging);
        }

        return userRepository.findAllUser(paging);
    }

    public void delete(Long id) {
        User user = this.find(id);
        user.setDeletedAt(new Date());
        userRepository.save(user);
    }
}
