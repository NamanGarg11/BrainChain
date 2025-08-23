package org.naman.quorabackend.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
    private String Id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
