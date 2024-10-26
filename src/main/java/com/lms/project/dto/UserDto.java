package com.lms.project.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
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
