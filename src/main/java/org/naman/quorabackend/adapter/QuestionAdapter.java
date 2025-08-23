package org.naman.quorabackend.adapter;

import org.naman.quorabackend.dto.QuestionRequestDTO;
import org.naman.quorabackend.dto.QuestionResponseDTO;
import org.naman.quorabackend.models.Question;

public class QuestionAdapter {
    public static QuestionResponseDTO toQuestionResponseDTO(Question question) {
    return QuestionResponseDTO.builder()
            .Id(question.getId())
            .title(question.getTitle())
            .content(question.getContent())
            .createdAt(question.getCreatedAt())
            .build();
    }
}
