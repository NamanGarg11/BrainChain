package org.naman.quorabackend.services;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.adapter.PaginationAdapter;
import org.naman.quorabackend.adapter.QuestionAdapter;
import org.naman.quorabackend.dto.PaginationRespnseDTO;
import org.naman.quorabackend.dto.QuestionRequestDTO;
import org.naman.quorabackend.dto.QuestionResponseDTO;
import org.naman.quorabackend.events.ViewCountEvent;
import org.naman.quorabackend.models.Question;
import org.naman.quorabackend.producers.KafkaEventProducer;
import org.naman.quorabackend.repositories.QuestionRepository;
import org.naman.quorabackend.utils.CursorUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.event.KafkaEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final KafkaEventProducer  kafkaEventProducer;
    private final IQuestionIndexService questionIndexService;
    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
       Question question =Question.builder()
               .title(questionRequestDTO.getTitle())
               .content(questionRequestDTO.getContent())
               .createdAt(LocalDateTime.now())
               .updatedAt(LocalDateTime.now())
               .build();
      return questionRepository.save(question)
              .map(QuestionAdapter::toQuestionResponseDTO)
//               .map(savedQuestion->{
////                   questionIndexService.createQuestionIndex(savedQuestion);
//                   return QuestionAdapter.toQuestionResponseDTO(savedQuestion);
//               })
              .doOnSuccess(response-> System.out.println(""+response))
              .doOnError(error-> System.out.println(""+error));
    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id) {
      return  questionRepository.findById(id)
               .map(QuestionAdapter::toQuestionResponseDTO)
              .doOnError(error-> System.out.println(""+error))
              .doOnSuccess(response->{
                  System.out.println(""+response);
                  ViewCountEvent viewCountEvent = new ViewCountEvent(id , "question",LocalDateTime.now());
                  kafkaEventProducer.publishViewCountEvent(viewCountEvent);
              });
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll()
                .map(QuestionAdapter::toQuestionResponseDTO);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return questionRepository.deleteById(id);
    }

    @Override
    public Flux<QuestionResponseDTO> SearchQuestions(String searchTerm, int offset, int pagesize) {
        return questionRepository.findByTitleOrContentContainsIgnoreCase(searchTerm, PageRequest.of(offset,pagesize))
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("error is "+error))
                .doOnComplete(()-> System.out.println("Completed"));

    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String Cursor, int size) {
        Pageable pageable = PageRequest.of(0,size);
        if(!CursorUtils.isValidCursor(Cursor)) {
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("error is "+error))
                    .doOnComplete(()-> System.out.println("Completed"));

        }else{
            LocalDateTime cursortime =  LocalDateTime.parse(Cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursortime,pageable)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("error is "+error))
                    .doOnComplete(()-> System.out.println("Completed"));
        }

    }
@Override
    public Mono<PaginationRespnseDTO<Question>> getPaginatedQuestions(String searchTerm, int page, int size) {
       Pageable pageable =  PageRequest.of(page,size);
   Mono<PaginationRespnseDTO<Question>> result = questionRepository
            .countByTitleContainingIgnoreCaseOrContentContainingIgnoreCase (searchTerm,searchTerm)
            .zipWith(questionRepository.findByTitleOrContentContainsIgnoreCase(searchTerm, pageable).collectList())
            .map(tuple -> PaginationAdapter.toPaginationResponse(tuple.getT2(), tuple.getT1(), page, size));
return result;
}



}
