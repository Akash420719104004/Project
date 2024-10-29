package com.lms.project.Service.Services;

import com.lms.project.entity1.dtos.JourneyDto;
import com.lms.project.response.SuccessResponse;

public interface JourneryService {
    public String addJourney(JourneyDto journeyDto);
   // public SuccessResponse<Object>addJourney(JourneyDto journeyDto);
}
