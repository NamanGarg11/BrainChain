package org.naman.quorabackend.adapter;

import org.naman.quorabackend.dto.AnswerResponseDTO;
import org.naman.quorabackend.models.Answer;

import java.time.LocalDateTime;

public class AnswerAdapter {
    public static AnswerResponseDTO toResponseDTO(Answer answer) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
