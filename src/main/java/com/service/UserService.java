package com.service;

import com.dao.ResetPasswordModel;
import com.dao.UserParamReq;
import com.entity.User;
import com.model.UserRequest;
import com.repository.UserRepository;
import com.security.BCrypt;
import com.util.MailTemplate;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passowordEncoder;

    @Autowired
    private MailTemplate mailTemplate;

    @Autowired
    private MailService mailService;


    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));
    }
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"email not found"));
    }
    @Transactional
    public User create(UserRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists :)");
        }

        if (userRepository.findOneByUsername(req.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already taken :)");
        }

        User user = new User();
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
public Map<String, Object> updatePassword(ResetPasswordModel req) {
    User user = getByEmail(req.getEmail());
    Map<String, Object> status = new HashMap<>();
    if (!user.getOtp().equals(req.getOtp())) {
        status.put("code", HttpStatus.BAD_REQUEST);
        status.put("message", "OTP is invalid");
    } else {
        user.setPassword(passowordEncoder.passwordEncoder().encode(req.getNewPassword()));
        user.setOtp("");
//        user.setOtpExpiredDate(null);
        userRepository.save(user);
        status.put("code", HttpStatus.OK);
        status.put("message", "Password updated");
        String template = mailTemplate.getUpdatePasswordTemplate(user.getUsername(), req.getNewPassword());
        mailService.sendAsync(user.getEmail(), "Password changed", template);
    }
    return status;
}
    public <T extends  User> T save(T s) {
        return userRepository.save(s);
    }
}
