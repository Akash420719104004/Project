package com.lms.project.Service;
import com.lms.project.dto.UserDto;
import com.lms.project.exceptions.ApplicationException;
import com.lms.project.model.User;
import com.lms.project.request.UserSignUpRequestDto;
import com.lms.project.response.PageResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public String userSignup(UserSignUpRequestDto userSignUpRequestDto) throws ApplicationException;
    UserDto getUserById(String id) throws ApplicationException;
   PageResponse<Object> getUser(Integer pageNo, String search) throws ApplicationException;
}
