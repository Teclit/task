package com.ecolepmn.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private Map<String, String> users = new HashMap<>();

    public MyUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        users.put("user", passwordEncoder.encode("user"));
        users.put("admin", passwordEncoder.encode("admin"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!users.containsKey(username)){
            throw new UsernameNotFoundException("User not found: "+ username);
        }
        log.info("LoadUserByUsername");
        return new User(username, users.get(username), new ArrayList<>());
    }

}
