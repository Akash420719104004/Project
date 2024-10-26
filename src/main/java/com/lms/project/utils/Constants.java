package com.lms.project.utils;
import lombok.Data;
@Data
public class Constants {
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String USER_SIGNUP = "/signup";
    public static final String USER_CREATED_SUCCESSFULLY = "USER CREATED SUCCESSFULLY";
    public static final String EMAIL = "email";
    public static final String MOBILE_NO = "mobile";
    public static final String USERID = "id";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String MYPROJECT_USER = "PROJECT-user";
    public interface UserType{
        String admin="Admin";
        String users ="Users";
        String normal ="Normal";
    }
}
