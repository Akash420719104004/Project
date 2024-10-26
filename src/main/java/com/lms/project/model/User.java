package com.lms.project.model;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
@Data
@Document(collection = "m_users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @Field(name = "name")
    private String userName;
    @NotBlank(message = "Email is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Field(name = "email")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Field(name = "password")
    private String password;
    private String ogP;
    @Field(name = "dob")
    private LocalDateTime dob;
    @Field(name = "doj")
    private LocalDateTime doj;
    @Field(name = "mobile")
    @Pattern(regexp="[1-9][0-9]{9}", message="Please provide a valid 10-digit mobile number")
    private String mobile;
    @Field(name = "userType")
    private String userType;
    @NotNull(message = "isActive cannot be null")
    @PastOrPresent(message = "createdAt must be in the past or present")
    @Field(name = "createdOn")
    private LocalDateTime createdOn;
    @NotNull(message = "createdBy cannot be null")
    @Field(name = "createdBy")
    private String createdBy;
    @PastOrPresent(message = "updatedAt must be in the past or present")
    @Field(name = "updatedOn")
    private LocalDateTime updatedOn;
    @NotNull(message = "updatedBy cannot be null")
    @Field(name = "updatedBy")
    private String updatedBy;
    private int loginCount=0;
    private Boolean isActive;
    @Field(name = "occupation")
    private String occupation;
    @Field(name = "gender")
    private String gender;
    @Field(name = "status")
    private boolean status;
}
