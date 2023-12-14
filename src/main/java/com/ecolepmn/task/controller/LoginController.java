package com.ecolepmn.task.controller;


import com.ecolepmn.task.entity.User;
import com.ecolepmn.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;


@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User savedUser = null;
        ResponseEntity<String> response = null;

        try {
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            user.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            savedUser = userRepository.save(user);

            if (savedUser.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(savedUser));
            }
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred due to " + ex.getMessage());
        }

        return response;
    }

    @RequestMapping("/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        List<User> users = userRepository.findByEmail(authentication.getName());
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }

    }

}
