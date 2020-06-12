package com.leverx.service;

import com.leverx.exception.*;
import com.leverx.model.Code;
import com.leverx.model.User;
import com.leverx.repository.CodeRepository;
import com.leverx.repository.UserRepository;
import com.leverx.util.MailUtil;
import com.leverx.util.TokenUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements UserDetailsService {
    @Value("${code.lifetime}")
    private int codeLifetime;
    @Value("${code.size}")
    private int codeSize;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    private TokenUtil tokenUtil;
    private MailUtil mailUtil;

    private UserRepository userRepository;
    private CodeRepository codeRepository;

    public UserService(TokenUtil tokenUtil, MailUtil mailUtil, UserRepository userRepository, CodeRepository codeRepository) {
        this.tokenUtil = tokenUtil;
        this.mailUtil = mailUtil;
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }

    public User findByEmail(String email) {
        return userRepository
                .findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles()
                .build();
    }

    public boolean isExistUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Code sendCode(User user) {
        String confirmationCode = RandomStringUtils.randomAlphanumeric(codeSize);
        try {
            mailUtil.sendCode(user, confirmationCode);
        } catch (MessagingException e) {
            throw new MailException("Failed to send confirmation code. Please try again later.");
        }
        return Code.builder()
                .userId(user.getId())
                .confirmationCode(confirmationCode)
                .createdDate(new Date())
                .build();
    }

    @Transactional
    public void save(User user) {
        if (isExistUserWithEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exist with email " + user.getEmail());
        }
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreatedDate(new Date());
        User newUser = userRepository.save(user);
        Code code = sendCode(newUser);
        codeRepository.save(code);
    }

    public void activateUserByCode(String confirmationCode) {
        Code code = codeRepository.findByConfirmationCode(confirmationCode).orElseThrow(() ->
                new ResourceNotFoundException("Account with activation code " + confirmationCode + " not found"));

        long now = new Date().getTime();
        long codeCreateDate = code.getCreatedDate().getTime();

        if (now - codeCreateDate < TimeUnit.HOURS.toMillis(codeLifetime)) {
            codeRepository.delete(code);
        } else {
            User user = userRepository.findById(code.getUserId()).get();
            Code newCode = sendCode(user);
            codeRepository.save(newCode);
            throw new ExpiredConfirmationCodeException("Expired confirmation code. Code was sent again");
        }
    }

    public boolean isUserNotActivate(User user) {
        return codeRepository.findById(user.getId()).isPresent();
    }

    public String createToken(User user) {
        User existingUser = findByEmail(user.getEmail());
        if (isUserNotActivate(existingUser)) {
            Code newCode = sendCode(existingUser);
            codeRepository.save(newCode);
            throw new UserNotActivatedException("User has not been activated. Code was sent again.");
        }
        return tokenUtil.generateToken(existingUser);
    }
}
