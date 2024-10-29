//package com.lms.project.utils;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Service
//public class SendSMSService {
//
//    @Value("${otp.service.username}")
//    private String username;
//    @Value("${otp.service.password}")
//    private String password;
//    @Value("${otp.service.url}")
//    private String otpServiceUrl;
//    private final WebClient webClient;
//
//    public SendSMSService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("https://www.agilecomm.co.in").build();
//    }
//
//    // http://www.agilecomm.co.in/aglcsms/sendsms?username=cavinotp&password=Cavin2Otp4&to=7358049936&message=<#>
//
//    /**
//     * Uses webclient to send OTP to the mobile number
//     * @param phoneNumber to send message
//     * @param otp
//     */
//    @Async
//    public void sendOtpToMobileNumber(String phoneNumber, String otp) {
//        String message = "<#>Your Livewire OTP is "+ otp+". Please use this to verify your Mobile Number -Cavin Kare";
//        this.webClient.get()
//                .uri(otpServiceUrl+"username={username}&password={password}&to={phoneNumber}&message={message}"
//                        ,username,password,phoneNumber,message)
//                .retrieve().bodyToMono(String.class).block();
//    }
//
//}
