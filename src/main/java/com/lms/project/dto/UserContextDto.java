package com.lms.project.dto;
import lombok.Data;
import java.io.Serializable;
@Data
public class UserContextDto implements Serializable {
    private static final long serialVersionUID = 7545340461305338564L;
    private String id;
    private String dob;
    private String userName;
    private boolean isActive;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private int tenantId;
    private String authorization;
    private String fullName;
    private long currentDate;
    private long iat;
    private long exp;
    private String iss;
    private String mobileNumber;
    private String email;
    private String userType;
    private Integer isSuperAdmin;
    private Integer isRoleAdmin;

}