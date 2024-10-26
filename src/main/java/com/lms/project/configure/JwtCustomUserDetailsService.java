package com.lms.project.configure;
import com.lms.project.Service.Authservice;
import com.lms.project.exceptions.CustomValidationException;
import com.lms.project.model.User;
import com.lms.project.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class JwtCustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    Authservice authService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = null;
        try {
            user = Optional.ofNullable(authService.getUser(username));
            List<GrantedAuthority> listRole = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User
                    (user.get().getUserName(), user.get().getPassword(), listRole);
        } catch (Exception e) {
            throw new CustomValidationException("User Not Found");
        }

    }
}