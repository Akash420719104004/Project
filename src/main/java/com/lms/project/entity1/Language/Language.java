package com.lms.project.entity1.Language;
import com.lms.project.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
public class Language {
    @Id
    private String id;
    private String langaugeName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DocumentReference
    private User createdBy;
    @DocumentReference
    private User updatedBy;

}
