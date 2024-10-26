package com.lms.project.request;
import com.lms.project.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserSignUpRequestDto {
    private String id;
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String userEmailID;
    private String password;
    private String ogP;
    private String dob;
    private String doj;
    @Pattern(regexp="[1-9][0-9]{9}", message="Please provide a valid 10-digit mobile number")
    private String mobile;
    private String userType= Constants.UserType.admin;
    private Boolean status;
    private String occupation;
    private String gender;
    private String createdBy;
    private String updatedBy;
    private int loginCount=0;
    private Boolean isActive;

}