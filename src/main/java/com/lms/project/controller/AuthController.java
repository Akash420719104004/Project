package com.lms.project.controller;
import com.lms.project.Service.Authservice;
import com.lms.project.Service.ServiceImpl.AuthServiceImpl;
import com.lms.project.exceptions.ApplicationException;
import com.lms.project.exceptions.ErrorCode;
import com.lms.project.model.User;
import com.lms.project.request.LoginRequestDto;
import com.lms.project.response.AuthResponse;
import com.lms.project.response.LoginResponse;
import com.lms.project.utils.Constants;
import com.lms.project.utils.JWTUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    Authservice authService;
    @Autowired
    AuthServiceImpl authServiceImpl;
    @Autowired
    JWTUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginRequest, HttpSession session) throws Exception {
        if (loginRequest == null) {
            throw new Exception("Bad Request");
        }
        AuthResponse response = authenticate(loginRequest.getEmail(), loginRequest.getPassword(),session,false);
        return ResponseEntity.ok(authService.login(response));
    }
    public AuthResponse authenticate(String username, String password, HttpSession session,Boolean refreshFlag) throws Exception {
        String token = null;
        String refreshToken = null;
        try {
            User user = authService.getUser(username);
            token = jwtUtils.generateToken(user, session);
            refreshToken = jwtUtils.refreshToken(token, user);
        } catch (DisabledException e) {
            throw new ApplicationException(ErrorCode.CAP_1001);
        } catch (BadCredentialsException e) {
            throw new ApplicationException(ErrorCode.CAP_1016);
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(username);
        authResponse.setToken(token);
        authResponse.setRefreshToken(refreshToken);
        return authResponse;
    }
//public AuthResponse authenticate(String username, String password, HttpSession session,Boolean refreshFlag) throws Exception {
//    String token = null;
//    String refreshToken = null;
//
//    try {
//        if(!refreshFlag) {
//            final Authentication authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            if (Objects.nonNull(authentication)) {
//                User user = authService.getUser(username);
//                token = jwtUtils.generateToken(user, session);
//                refreshToken = jwtUtils.refreshToken(token, user);
//            }
//        }
//        User user = authService.getUser(username);
//        token = jwtUtils.generateToken(user, session);
//        refreshToken = jwtUtils.refreshToken(token, user);
//    } catch (DisabledException e) {
//        throw new ApplicationException(ErrorCode.CAP_1001);
//    } catch (BadCredentialsException e) {
//        throw new ApplicationException(ErrorCode.CAP_1016);
//    }
//
//    return new AuthResponse(token, refreshToken, jwtUtils.getEmail(token));
//}
}
