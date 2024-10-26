package com.lms.project.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse implements Serializable {
    private static final long serialVersionUID = 8286210631647330695L;
    private String user;
    private String token;
    private String refreshToken;
}
