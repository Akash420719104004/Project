package com.lms.project.controller;
import com.lms.project.Service.Authservice;
import com.lms.project.Service.UserService;
import com.lms.project.dto.UserDto;
import com.lms.project.exceptions.ApplicationException;
import com.lms.project.model.User;
import com.lms.project.request.UserSignUpRequestDto;
import com.lms.project.response.PageResponse;
import com.lms.project.utils.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    Authservice authService;
    @Autowired
    UserService userService;
    @Autowired
    JWTUtils jwtUtils;
    @PostMapping("/signup")
    public ResponseEntity<String> userSignup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) throws ApplicationException, ApplicationException {
        return ResponseEntity.ok(userService.userSignup(userSignUpRequestDto));
    }
    @GetMapping("/name")
    public String apiHandler(){
        return "Its Working!!!! ....";
    }
    @GetMapping("/getById")
    public UserDto fetchById(@RequestParam String id) throws ApplicationException {
        return userService.getUserById(id);
    }
    @GetMapping("/getAll")
    public PageResponse<Object> getUser(@RequestParam(value = "id") Integer pageNo, @RequestParam (required = false)String search) throws ApplicationException{
        return userService.getUser(pageNo,search);
    }
    @GetMapping("/checkApi")
    public String checkApi(){
        return "Checked Api Sucessfully!!!";
    }
    @GetMapping("/dummy")
    public String dummy(){
        return "dummy Successfully";
    }

}



