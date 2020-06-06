package com.leverx.controller;

import com.leverx.exception.UserNotActivatedException;
import com.leverx.model.User;
import com.leverx.service.JwtUserDetailsService;
import com.leverx.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class UserController {
    public static final String SUCCESS = "The user was successfully created.\n" +
            "An email with your confirmation code has been sent to you.\n" +
            "Enter received code into /auth/confirm/{code} to activate your account.\n" +
            "Code have a 24 hours life";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            jwtUserDetailsService.save(user);
            return ResponseEntity.ok(SUCCESS);
        } catch (MessagingException e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Failed to send confirmation code. User is not created");
        }
    }

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        User existingUser = jwtUserDetailsService.findByEmail(user.getEmail());
        if (jwtUserDetailsService.isUserNotActivate(existingUser)) {
            try {
                jwtUserDetailsService.sendCode(existingUser);
            } catch (MessagingException e) {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Failed to send confirmation code. User is not created");
            }
            throw new UserNotActivatedException("User has not been activated. Code was sent again");
        }
        String token = jwtTokenUtil.generateToken(existingUser);
        return ResponseEntity.ok("token: " + token);
    }

    @PostMapping
    @RequestMapping("/confirm/{code}")
    public ResponseEntity<String> activateUser(@PathVariable String code) {
        if (jwtUserDetailsService.activateUserByCode(code)) {
            return ResponseEntity
                    .ok()
                    .body("Your account has been activated successfully");
        }
        return ResponseEntity
                .badRequest()
                .body("Account not found");
    }
}

