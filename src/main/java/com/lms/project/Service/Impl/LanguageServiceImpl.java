package com.lms.project.Service.Impl;
import com.lms.project.Service.Services.LanguageService;
import com.lms.project.dto.UserContextDto;
import com.lms.project.dto.UserContextHolder;
import com.lms.project.entity1.Language.Language;
import com.lms.project.entity1.dtos.LanguageDtos;
import com.lms.project.model.User;
import com.lms.project.repository.LanguageRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    LanguageRepository languageRepository;

    @Override
    public String addLanguage(LanguageDtos languageDtos) {
        UserContextDto userDto = UserContextHolder.getUserDto();
        User user = modelMapper.map(userDto, User.class);
        Language language = languageRepository.findById(languageDtos.getId()).orElse(new Language());
        if (StringUtils.isBlank(languageDtos.getId())) {
            if (languageRepository.findByLanguageName(StringUtils.capitalize(languageDtos.getLangaugeName()).trim()).isPresent()) {
                throw new RuntimeException("LanguaqeName Already Exists ");
            }
            language.setLangaugeName(languageDtos.getLangaugeName());
            language.setCreatedAt(LocalDateTime.now());
            language.setCreatedBy(user);
        } else if (languageDtos.getId() != null) {
            language.setLangaugeName(languageDtos.getLangaugeName());
            language.setUpdatedBy(user);
            language.setUpdatedAt(LocalDateTime.now());
        }
        languageRepository.save(language);
        return StringUtils.isBlank(languageDtos.getId()) ? "Langage Created" : "Language Updated";
    }
}

