package com.lms.project.controller;

import com.lms.project.Service.RegisterUserService;
import com.lms.project.dto.RegisterDto;
import com.lms.project.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    RegisterUserService registerUserService;
    @PostMapping("/register")
    public SuccessResponse<Object> register(@RequestBody(required = false) RegisterDto registerDTO) {
        return registerUserService.registerUser(registerDTO);
    }
}
