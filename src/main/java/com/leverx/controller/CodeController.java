package com.leverx.controller;

import com.leverx.model.Code;
import com.leverx.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("code")
public class CodeController {
    @Autowired
    private CodeRepository codeRepository;

    @GetMapping
    public String get(){
        codeRepository.save(new Code(1, "123"));
        return "good";
    }
}
