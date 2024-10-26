package com.lms.project.controller.HandlerController;

import com.lms.project.Service.Services.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OTPController {
    @Autowired
    OTPService otpService;
    @PostMapping("/send")
    public String sendOtp(@RequestParam (required = false)String email) {
        otpService.generateOTP(email);
        return "OTP sent to " + email;
    }
    @PostMapping("/verify")
    public String verifyOTP(@RequestParam(required = false) String email,@RequestParam(required = false) String otp){
        boolean isvalid= otpService.verifyOTP(email,otp);
        if(isvalid){
            otpService.clearOtp(email);
            return "OTP verified Successfully";
        }
        else {
            return "Invalid Otp";
        }
    }
}
