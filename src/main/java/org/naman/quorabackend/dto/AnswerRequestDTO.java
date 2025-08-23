package org.naman.quorabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AnswerRequestDTO {

    @NotBlank(message = "Content is required")
    @Size(min=10, max=100 ,message = "Content is btw 10 to 100 words")
    private String content;
    @NotBlank
    private String QuestionID;
}
