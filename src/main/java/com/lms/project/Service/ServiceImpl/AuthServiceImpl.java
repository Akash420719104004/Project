package com.lms.project.Service.ServiceImpl;
import com.lms.project.Service.Authservice;
import com.lms.project.dto.UserDto;
import com.lms.project.exceptions.ApplicationException;
import com.lms.project.exceptions.ErrorCode;
import com.lms.project.model.User;
import com.lms.project.repository.UserRepository;
import com.lms.project.response.AuthResponse;
import com.lms.project.response.LoginResponse;
import com.lms.project.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Optional;
@Service
public class AuthServiceImpl implements Authservice {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    Authservice authService;
    @Override
    public LoginResponse login(AuthResponse authResponse) throws Exception {
        try {
            if (authResponse == null) {
                throw new ApplicationException(ErrorCode.CAP_1002);
            }
            Optional<User> userOptional = userRepository.findByEmail(authResponse.getUser());
            if (userOptional.isEmpty()) {
                throw new ApplicationException(ErrorCode.CAP_1003);
            }
            User user = userOptional.get();
            UserDto userDTO = modelMapper.map(user, UserDto.class);
            return new LoginResponse(userDTO, authResponse.getToken(), authResponse.getRefreshToken());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(ErrorCode.CAP_1002);
        }
    }
    @Override
    public User getUser(String username) throws Exception {
        Optional<User> users = userRepository.findByEmail(username);
        if (users.isEmpty()) {
            throw new ApplicationException(ErrorCode.CAP_10039);
        }
        return users.get();
    }

}



