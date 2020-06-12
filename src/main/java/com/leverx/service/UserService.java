package com.leverx.service;

import com.leverx.exception.ExpiredConfirmationCodeException;
import com.leverx.exception.ResourceNotFoundException;
import com.leverx.exception.UserAlreadyExistException;
import com.leverx.exception.UserNotActivatedException;
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

    public Code sendCode(User user) throws MessagingException {
        String confirmationCode = RandomStringUtils.randomAlphanumeric(codeSize);
        mailUtil.sendCode(user, confirmationCode);
        return Code.builder()
                .userId(user.getId())
                .confirmationCode(confirmationCode)
                .createdDate(new Date())
                .build();
    }

    @Transactional // FIXME: 12.06.2020 //(rollbackFor = MessagingException.class)
    public void save(User user) throws MessagingException {
        if (isExistUserWithEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exist with email " + user.getEmail());
        }
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreatedDate(new Date());
        User newUser = userRepository.save(user);
        sendCode(newUser);
    }

    public void activateUserByCode(String confirmationCode) throws MessagingException {
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
        return codeRepository.existsByUserId(user.getId());
    }

    public String createToken(User user) throws MessagingException {
        User existingUser = findByEmail(user.getEmail());
        if (isUserNotActivate(existingUser)) {
            Code newCode = sendCode(existingUser);
            codeRepository.save(newCode);// FIXME: 12.06.2020   check not double code with user id
            throw new UserNotActivatedException("User has not been activated. Code was sent again.");
        }
        return tokenUtil.generateToken(existingUser);
    }
}
