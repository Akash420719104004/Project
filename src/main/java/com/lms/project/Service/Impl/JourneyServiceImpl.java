package com.lms.project.Service.Impl;

import com.lms.project.Service.Services.JourneryService;
import com.lms.project.dto.UserContextDto;
import com.lms.project.dto.UserContextHolder;
import com.lms.project.entity1.Journey;
import com.lms.project.entity1.Path.Path;
import com.lms.project.entity1.dtos.JourneyDto;
import com.lms.project.model.User;
import com.lms.project.repository.JourneyRepository;
import com.lms.project.repository.LanguageRepository;
import com.lms.project.repository.PathRepository;
import com.lms.project.repository.UserRepository;
import com.lms.project.response.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JourneyServiceImpl implements JourneryService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JourneyRepository journeyRepository;
    @Autowired
    PathRepository pathRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public String addJourney(JourneyDto journeyDto) {
        UserContextDto userContextDto=UserContextHolder.getUserDto();
        User user=modelMapper.map(userContextDto,User.class);
        Journey journey=journeyRepository.findById(journeyDto.getJourneyId()).orElse(new Journey());
        journey.setJourneyName(journeyDto.getJourneyName());
        journey.setLanguage(languageRepository.findById(journeyDto.getLanguage()).get());
//        Path path=pathRepository.findById(journeyDto.getPath()).get();
//        journey.setPath(path);
        journey.setCreatedAt(LocalDateTime.now());
        journey.setCreatedBy(user);
        journeyRepository.save(journey);
        return "Journey Added";
    }

   // @Override
//        public SuccessResponse<Object> addJourney(JourneyDto journeyDto) {
//        UserContextDto userContextDto=new UserContextDto();
//        User user=modelMapper.map(userContextDto,User.class);
//            SuccessResponse<Object> response = new SuccessResponse<>();
//                Journey journey;
//                journeyRepository.findByName(journeyDto.getJourneyName()).ifPresent(journey1->{
//                    throw new RuntimeException("Journey with this name already exists");
//                });
//        journey = journeyRepository.findById(journeyDto.getJourneyId()).orElse(new Journey());
//        response.setStatusMessage("Journey added");
//        journey.setJourneyName(journeyDto.getJourneyName());
//        journey.setPath(pathRepository.findById(journeyDto.getPath())
//                .orElseThrow(() -> new RuntimeException("Path not found")));
//        journey.setLanguage(languageRepository.findById(journeyDto.getLanguage())
//                .orElseThrow(() -> new RuntimeException("Language not found")));
//        journey.setCreatedAt(LocalDateTime.now());
//        journey.setCreatedBy(user);
//        return response;
//        }

    }
//    @Override
//    public String addJourney(JourneyDto journeyDto) {
//        UserContextDto userContextDto= UserContextHolder.getUserDto();
//        User user=modelMapper.map(userContextDto,User.class);
//        Journey journey=
//        return "";
//    }

