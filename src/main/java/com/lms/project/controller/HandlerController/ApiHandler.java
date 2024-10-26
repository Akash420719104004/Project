package com.lms.project.controller.HandlerController;
import com.lms.project.entity1.RegisterUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
@RestController
public class ApiHandler {
    @GetMapping("/")
    public String great(HttpServletRequest request) {
        return " Its Working now!!!..." + request.getSession().getId();
    }
    private List<RegisterUser> check = new ArrayList<>(List.of(
            new RegisterUser("1", "AkashSelvakumar", "Admin", "email@gmail.com", "Cuddalore")
    ));
    @GetMapping("/students")
    public List<RegisterUser> getRegisterUser() {
        return List.of();
    }
}



