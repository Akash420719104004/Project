package com.lms.project.Service.Impl;

import com.lms.project.Service.Services.EmailService;
import com.lms.project.Service.Services.OTPService;
import com.lms.project.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OTPServiceImpl implements OTPService {
    @Autowired
    EmailService emailService;
    private Map<String, String> otpStorage = new HashMap<>();
    @Override
    public String generateOTP(String email) {OtpUtils otpGenerator=new OtpUtils();
       String otp= otpGenerator.generateOtp();
        otpStorage.put(email,otp);
        String subject = "Your OTP Code";
        String body = "Your OTP is: " + otp;
        emailService.sendEmail(new String[]{email}, subject, body);
        return "OTP generated";
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        System.out.println("Stored OTP: " + storedOtp + ", Provided OTP: " + otp);
        return storedOtp != null && storedOtp.equals(otp);
    }
    @Override
    public void clearOtp(String email) {
        otpStorage.remove(email);
    }

}
