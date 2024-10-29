package com.lms.project.entity1;

import com.lms.project.entity1.Language.Language;
import com.lms.project.entity1.Path.Path;
import com.lms.project.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
public class Journey {
    @Id
    private String id;
    private String journeyId;
    private String journeyName;
    @DocumentReference
    private Path path;
    @DocumentReference
    private User createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DocumentReference
    private User updatedBy;
    @DocumentReference
    private Language language;
}
