package com.leverx.controller;

import com.leverx.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class TagController {

    @Autowired
    private MailUtil mailUtil;

    @PostMapping
    public String saveUser() {
        //mailUtil.sendSignUpVerify("gmariya6666@gmail.com", "login", "code");

        return "good";
    }
}
