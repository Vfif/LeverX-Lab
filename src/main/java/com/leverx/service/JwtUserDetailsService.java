package com.leverx.service;

import com.leverx.exception.ExpiredConfirmationCodeException;
import com.leverx.exception.UserAlreadyExistException;
import com.leverx.model.User;
import com.leverx.repository.UserRepository;
import com.leverx.util.MailUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    public static final int LIFE_TIME = 24;
    private static final int CODE_SIZE = 4;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MailUtil mailUtil;

    public com.leverx.model.User findByEmail(String email) {
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

    @Transactional(rollbackFor = MessagingException.class)
    public void save(User user) throws MessagingException {
        if (isExistUserWithEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exist with email " + user.getEmail());
        }
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreatedDate(new Date());
        sendCode(user);
    }

    public boolean activateUserByCode(String code) {
        AtomicBoolean result = new AtomicBoolean(false);
        userRepository.findByCode(code)
                .ifPresent(user -> {
                    if (new Date().getTime() - user.getCreatedCodeDate().getTime() < TimeUnit.HOURS.toMillis(LIFE_TIME)) {
                        user.setCode(null);
                        userRepository.save(user);
                        result.set(true);
                    } else {
                        try {
                            sendCode(user);
                        } catch (MessagingException e) {

                        }
                        throw new ExpiredConfirmationCodeException("Expired confirmation code. Code was sent again");
                    }
                });
        return result.get();
    }

    public boolean isUserNotActivate(User user) {
        return user.getCode() != null;
    }

    public void sendCode(User user) throws MessagingException {// FIXME: 06.06.2020 change architecture
        String code = RandomStringUtils.randomAlphanumeric(CODE_SIZE);
        //mailUtil.sendCode(user, code);
        user.setCode(code);
        user.setCreatedCodeDate(new Date());
        userRepository.save(user);
    }
}
