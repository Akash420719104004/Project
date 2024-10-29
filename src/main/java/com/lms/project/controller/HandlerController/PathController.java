package com.lms.project.controller.HandlerController;

import com.lms.project.Service.Impl.PathServiceImpl;
import com.lms.project.Service.Services.PatrhService;
import com.lms.project.entity1.dtos.PathDtos;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PathController {
    @Autowired
    PatrhService pathService;
    @Autowired
    PathServiceImpl pathServiceImpl;

    @PostMapping("/addPath")
    public ResponseEntity<String> addPath(PathDtos pathDto) {
        String reponse = pathService.addPath(pathDto);
        return new ResponseEntity<>(reponse, HttpStatus.CREATED);
    }

    @GetMapping("/fetch/pathName")
    public List<String> filterPathName() {
        return pathService.filterPathName();
    }

    @DeleteMapping("/deleteById")
    public Optional<DeleteResult> findById(String id) {
        return pathServiceImpl.findById(id);
    }
}
