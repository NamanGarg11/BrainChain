package org.naman.quorabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AnswerResponseDTO {
    private String id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
