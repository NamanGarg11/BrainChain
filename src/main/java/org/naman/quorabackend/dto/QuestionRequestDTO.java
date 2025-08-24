package org.naman.quorabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {
    @NotBlank(message = "Title is required ")
    @Size(min = 10,max = 100 ,message = "Title is btw 10 to 100 words")
    private String title;
    @NotBlank(message = "Content is required")
    @Size(min=10, max=100 ,message = "Content is btw 10 to 100 words")
    private String content;
    private List<String> tag;
}
