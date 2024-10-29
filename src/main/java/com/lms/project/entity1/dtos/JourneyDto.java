package com.lms.project.entity1.dtos;

import com.lms.project.entity1.Language.Language;
import com.lms.project.entity1.Path.Path;
import com.lms.project.model.User;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class JourneyDto {
    private String id;
    private String journeyId;
    private String journeyName;
    private String path;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
    private String language;
}
