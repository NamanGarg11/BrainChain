package org.naman.quorabackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naman.quorabackend.models.enums.TargetType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Likes")
public class Like {
    @Id
    private String id;
    private String TargetId;

    private TargetType TargetType;

     private Boolean isLike;

    @CreatedDate
    private LocalDateTime createdAt;
}
