package com.leverx.controller;

import com.leverx.model.User;
import com.leverx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserRepository userRepository;

    @PostMapping
    public String saveUser(@RequestBody User user) {
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreatedDate(new Date());
        userRepository.save(user);

        return "good";
    }
}
