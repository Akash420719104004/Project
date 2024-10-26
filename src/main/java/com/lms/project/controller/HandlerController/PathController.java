package com.lms.project.controller.HandlerController;

import com.lms.project.Service.Services.PatrhService;
import com.lms.project.entity1.dtos.PathDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {
    @Autowired
    PatrhService pathService;
    @PostMapping("/addPath")
    public ResponseEntity<String> addPath(PathDtos pathDto) {
        String reponse= pathService.addPath(pathDto);
        return new ResponseEntity<>(reponse, HttpStatus.CREATED);
    }
}
