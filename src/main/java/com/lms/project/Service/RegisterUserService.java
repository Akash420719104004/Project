package com.lms.project.Service;

import com.lms.project.dto.RegisterDto;
import com.lms.project.response.SuccessResponse;

public interface RegisterUserService {
    public SuccessResponse<Object>registerUser(RegisterDto registerDto);
}
