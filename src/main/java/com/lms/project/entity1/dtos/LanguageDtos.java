package com.lms.project.entity1.dtos;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LanguageDtos {
    private String id;
    private String langaugeName;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
