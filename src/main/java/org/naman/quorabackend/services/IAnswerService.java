package org.naman.quorabackend.services;

import org.naman.quorabackend.dto.AnswerRequestDTO;
import org.naman.quorabackend.dto.AnswerResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) ;
    Mono<AnswerResponseDTO> getAnswerById(String id);
    Mono<AnswerResponseDTO> updateAnswer(String id, AnswerRequestDTO answerRequestDTO);
    Mono<Void> deleteAnswer(String id);
    Flux<AnswerResponseDTO> getAllAnswers();
     Flux<AnswerResponseDTO> getAnswersByQuestionId(String questionId);

    Mono<Long> getAnswerCountByQuestionId(String questionId);

    Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtDesc(String questionId);

    Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtAsc(String questionId);
}
