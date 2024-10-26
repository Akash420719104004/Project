package com.lms.project.Service;
import com.lms.project.model.User;
import com.lms.project.response.AuthResponse;
import com.lms.project.response.LoginResponse;
public interface Authservice {
    LoginResponse login(AuthResponse response) throws Exception;
    User getUser(String username) throws Exception;
  //  AuthResponse authenticate(String mobile, String password, HttpSession session, boolean b) throws Exception;
}