package com.service;

import com.dao.UserParamReq;
import com.entity.User;
import com.model.UserRequest;
import com.repository.UserRepository;
import com.security.BCrypt;
import com.util.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;


@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passowordEncoder;


    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    }
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Email not found"));
    }
    @Transactional
    public User create(UserRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already exists :)");
        }

        if (userRepository.findOneByUsername(req.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username Already taken :)");
        }

        User user = new User();
//        String hashedPwd = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        String hashedPwd = passowordEncoder.passwordEncoder().encode(req.getPassword());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPassword(hashedPwd);
        user.setRoleUser(req.getRoleUser());
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

    @Transactional
    public void delete(Long id) {
        User user = this.find(id);
        user.setDeletedAt(new Date());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[Username] {}", username);
        return userRepository.findOneByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found"));

    }
}
