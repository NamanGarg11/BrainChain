package org.naman.quorabackend.services;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.adapter.AnswerAdapter;
import org.naman.quorabackend.dto.AnswerRequestDTO;
import org.naman.quorabackend.dto.AnswerResponseDTO;
import org.naman.quorabackend.models.Answer;
import org.naman.quorabackend.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) {
        Answer answer = Answer.builder()
                .id(answerRequestDTO.getQuestionID())
                .content(answerRequestDTO.getContent())
                .build();
       return answerRepository.save(answer)
               .map(AnswerAdapter::toResponseDTO);
    }

    @Override
    public Mono<AnswerResponseDTO> getAnswerById(String id) {
        return answerRepository.findById(id)
                .map(AnswerAdapter::toResponseDTO);
    }

    @Override
    public Mono<AnswerResponseDTO> updateAnswer(String id, AnswerRequestDTO answerRequestDTO) {
        return answerRepository.findById(id)
                .flatMap(changeContent->{
                    changeContent.setContent(answerRequestDTO.getContent());
                    return answerRepository.save(changeContent);
                })
                .map(AnswerAdapter::toResponseDTO)
                .doOnError(error -> System.out.println(error.getMessage()))
                .doOnSuccess(answer -> System.out.println("completed the change"));
    }

    @Override
    public Mono<Void> deleteAnswer(String id) {
        return answerRepository.deleteById(id)
                .doOnError(error -> System.out.println(error.getMessage()))
                .doOnSuccess(answer -> System.out.println("Deleted successfully"));
    }

    @Override
    public Flux<AnswerResponseDTO> getAllAnswers() {
        return answerRepository.findAll()
                .map(AnswerAdapter::toResponseDTO);
    }

    public Flux<AnswerResponseDTO> getAnswersByQuestionId(String questionId) {
        return answerRepository.findByQuestionId(questionId)
                .map(this::mapToResponseDTO);
    }

    public Mono<Long> getAnswerCountByQuestionId(String questionId) {
        return answerRepository.countByQuestionId(questionId);
    }

    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtDesc(String questionId) {
        return answerRepository.findByQuestionIdOrderByCreatedAtDesc(questionId)
                .map(this::mapToResponseDTO);
    }

    public Flux<AnswerResponseDTO> getAnswersByQuestionIdOrderByCreatedAtAsc(String questionId) {
        return answerRepository.findByQuestionIdOrderByCreatedAtAsc(questionId)
                .map(this::mapToResponseDTO);
    }

    private AnswerResponseDTO mapToResponseDTO(Answer answer) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .questionId(answer.getQuestionId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
