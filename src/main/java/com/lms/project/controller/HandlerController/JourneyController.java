package com.lms.project.controller.HandlerController;

import com.lms.project.Service.Services.JourneryService;
import com.lms.project.entity1.dtos.JourneyDto;
import com.lms.project.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JourneyController {
    @Autowired
    JourneryService journeryService;
    @PostMapping("/addJourney")
    public String addJourney(JourneyDto journeyDto){
        return journeryService.addJourney(journeyDto);
    }
    @GetMapping("/addDemo")
    public ResponseEntity<String> addDemo(){
        return new ResponseEntity<>("Welcome to my world," , HttpStatus.OK);
    }
}
