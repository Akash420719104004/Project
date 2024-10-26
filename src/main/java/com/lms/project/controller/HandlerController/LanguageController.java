package com.lms.project.controller.HandlerController;

import com.lms.project.Service.Impl.LanguageServiceImpl;
import com.lms.project.Service.Services.LanguageService;
import com.lms.project.entity1.dtos.LanguageDtos;
import com.lms.project.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LanguageController {
    @Autowired
    LanguageServiceImpl languageService;
    @PostMapping("/addLang")
    public ResponseEntity<String>addLanguageName(@RequestBody LanguageDtos languageDtos){
        String responseEntity=languageService.addLanguage(languageDtos);
        return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
    }
}
