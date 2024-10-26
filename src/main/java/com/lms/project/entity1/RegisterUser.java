package com.lms.project.entity1;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "register")
public class RegisterUser {
    @Id
    private String id;

    private String name;

    private String userType;

    private String emailId;

    private String mobileNo;

    private String address;

    private Boolean active;


    public RegisterUser(String number, String akashSelvakumar, String admin, String mail, String cuddalore) {
    }

    public RegisterUser() {

    }
}
