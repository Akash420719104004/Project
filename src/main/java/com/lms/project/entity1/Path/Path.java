package com.lms.project.entity1.Path;
import com.lms.project.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Path {
    @Id
    private String id;
    private String pathName;
    @DocumentReference
    private User createdBy;
    @DocumentReference
    private User updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
