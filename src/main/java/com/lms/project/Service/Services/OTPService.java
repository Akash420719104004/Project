package com.lms.project.Service.Services;

public interface OTPService {
    public String generateOTP(String email);
    public boolean verifyOTP(String email,String otp);
    void clearOtp(String email);

}
