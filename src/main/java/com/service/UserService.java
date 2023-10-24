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

    public Page<User> findAll(UserParamReq req) {
        Pageable paging = PageRequest.of(req.getPage(), req.getSize());
        if (req.getUsername() != null  && req.getEmail() != null) {
            return userRepository.findByUsernameAndEmail(req.getUsername(), req.getEmail(), paging);
        }
        if (req.getUsername() != null) {
            return userRepository.findByUsername(req.getUsername(), paging);
        }
        if (req.getEmail() != null) {
            return userRepository.findByUsername(req.getEmail(), paging);
        }
        return userRepository.findAllUser(paging);
    }


//    public Page<User> get(UserParamReq req) {
//        Pageable paging = PageRequest.of(req.getPage(), req.getSize());
//        Specification<User> spec = (root ,query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if(req.getUsername() != null)  {
//                predicates.add(cb(root.get("username"), req.setUsername()));
//            }
//
//            if (req.getEmail() != null) {
//                predicates.add(cb(root.get("email"),  req.getEmail()));
//            }
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//        return userRepository.findAll(spec);
//    }

    public void delete(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found :)");
        }
        userRepository.deleteById(id);
    }
}
