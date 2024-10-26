package com.lms.project.Service.ServiceImpl;

import com.lms.project.Service.RegisterUserService;
import com.lms.project.dto.RegisterDto;
import com.lms.project.entity1.RegisterUser;
import com.lms.project.repository.RegisterRepository;
import com.lms.project.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {
    @Autowired
    RegisterRepository registerRepository;
    @Override
    public SuccessResponse<Object> registerUser(RegisterDto registerDto) {
        try {
            SuccessResponse<Object> response = new SuccessResponse<>();
            RegisterUser registerUser;
            registerRepository.findByMobileNo(registerDto.getMobileNo()).ifPresent(user -> {
                throw new RuntimeException("MobileNo");
            });
            if (registerDto.getId() != null) {
                registerUser = registerRepository.findById(String.valueOf(registerDto.getId())).orElseThrow(() -> new RuntimeException("User Not"));
                response.setStatusMessage("user Updated Successfully");
            } else {
                registerUser = new RegisterUser();
                if (registerDto.getId() == null) {
                    response.setStatusMessage("User Added Successfully");
                }
            }
            registerUser.setActive(registerDto.getActive());
            registerUser.setUserType(registerDto.getUserType());
            registerUser.setMobileNo(registerDto.getMobileNo());
            registerUser.setEmailId(registerDto.getEmailId());
            registerUser.setAddress(registerDto.getAddress());
            registerUser.setName(registerDto.getName());
            registerUser=registerRepository.save(registerUser);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return new SuccessResponse<>();
    }
}
