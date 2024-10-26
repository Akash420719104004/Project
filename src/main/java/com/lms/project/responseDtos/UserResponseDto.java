package com.lms.project.responseDtos;
import lombok.Data;
@Data
public class UserResponseDto {
    private String id;
    private String username;
    private String email;
    private String dob;
    private String doj;
    private String mobile;
    private String userType;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;
    private String occupation;
    private String gender;
    private boolean status;
    private int loginCount=0;
    private Boolean isActive;
}
