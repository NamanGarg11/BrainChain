package org.naman.quorabackend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "question")
public class Question {
    @Id
    private String id;

    @NotBlank(message = "Title is required ")
    @Size(min = 10, max = 100, message = "Title must Lobe btw 10 to 100 words")
    private String title;

    @NotBlank(message = "Content is required ")
    @Size(min = 10, max = 100 , message = "Content must be btw 10 to 100 words")
    private String content;


    private Integer views;

    private List<String> tags;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
