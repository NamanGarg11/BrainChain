package org.naman.quorabackend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
    private String Id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private List<String> tags;
}
