package com.lms.project.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String id;

    private String name;

    private String userType;

    private String emailId;

    private String mobileNo;

    private String address;

    private Boolean active;
}
