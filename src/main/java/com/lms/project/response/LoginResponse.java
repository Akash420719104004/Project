package com.lms.project.response;
import com.lms.project.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private UserDto users;
    private String token;
    private String refreshToken;
}
