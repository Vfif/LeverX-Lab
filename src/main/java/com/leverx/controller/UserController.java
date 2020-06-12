package com.leverx.controller;

import com.leverx.model.User;
import com.leverx.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class UserController {
    private static final String USER_SUCCESSFULLY_CREATED = "The user was successfully created.\n" +
            "An email with your confirmation code has been sent to you.\n" +
            "Enter received code into /auth/confirm/{code} to activate your account.\n" +
            "Code have a 24 hours life";
    private static final String FAILED_SEND_CODE = "Failed to send confirmation code. Please try again later.";
    private static final String USER_SUCCESSFULLY_ACTIVATED = "Your account has been activated successfully";

    private UserService userService;
    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok(USER_SUCCESSFULLY_CREATED);
        } catch (MessagingException e) {
            return failedSendCodeResponse();
        }
    }

    @PostMapping
    public ResponseEntity<String> createToken(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        try {
            String token = userService.createToken(user);
            return ResponseEntity.ok("token: " + token);
        } catch (MessagingException e) {
            return failedSendCodeResponse();
        }
    }

    @PostMapping
    @RequestMapping("/confirm/{code}")
    public ResponseEntity<String> activateUser(@PathVariable String code) {
        try {
            userService.activateUserByCode(code);
            return ResponseEntity
                    .ok()
                    .body(USER_SUCCESSFULLY_ACTIVATED);
        } catch (MessagingException e) {
            return failedSendCodeResponse();
        }
    }

    private ResponseEntity<String> failedSendCodeResponse() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(FAILED_SEND_CODE);
    }
}

