package org.naman.quorabackend.services;

import org.naman.quorabackend.dto.PaginationRespnseDTO;
import org.naman.quorabackend.dto.QuestionRequestDTO;
import org.naman.quorabackend.dto.QuestionResponseDTO;
import org.naman.quorabackend.models.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);
    public Mono<QuestionResponseDTO> getQuestionById(String id);
    public Flux<QuestionResponseDTO> getAllQuestions();
    public Mono<Void> deleteById(String id);

    public Flux<QuestionResponseDTO> SearchQuestions(String searchTerm , int offset, int pagesize);
// BUG
    public Flux<QuestionResponseDTO> getAllQuestions(String Cursor,int size);
    public Mono<PaginationRespnseDTO<Question>> getPaginatedQuestions(String searchTerm, int page, int size);
}
