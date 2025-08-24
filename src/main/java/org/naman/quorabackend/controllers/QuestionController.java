package org.naman.quorabackend.controllers;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.dto.PaginationRespnseDTO;
import org.naman.quorabackend.dto.QuestionRequestDTO;
import org.naman.quorabackend.dto.QuestionResponseDTO;
import org.naman.quorabackend.models.Question;
import org.naman.quorabackend.services.IQuestionService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")

public class QuestionController {

    private final IQuestionService questionService;
//    creating question
    @PostMapping
    public ResponseEntity<Mono<QuestionResponseDTO>> createQuestion(@RequestBody QuestionRequestDTO question) {
        return ResponseEntity.ok(questionService.createQuestion(question)
        .doOnSuccess(response -> System.out.println(""+response))
        .doOnError(error -> System.out.println(""+error)));
    }
// getting question by ID
    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id);
    }
//getting all questions
    @GetMapping
    public Flux<QuestionResponseDTO> getAllQuestions() {
    return questionService.getAllQuestions();
    }
// delete question by id
    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id) {
        return questionService.deleteById(id);
    }


    // search question by searchterm offset type of pagination
    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return questionService.SearchQuestions(query, page, size);
    }
    // getting all question using cursor based pagination

    @GetMapping("/questions")
    public Flux<QuestionResponseDTO> getAllQuestionsPagination(
            @RequestParam(required = false) String Cursor,
            @RequestParam int size
    ) {
        return questionService.getAllQuestions();
    }

// GETING the paginated response of the data
    @GetMapping("/pagination")
    public Mono<PaginationRespnseDTO<Question>> getPaginatedData(
            @RequestParam String searchTerm,
            @RequestParam int page,
            @RequestParam int size
    ){
        return questionService.getPaginatedQuestions(searchTerm, page,size);
    }


    @GetMapping("/tag/{tag}")
    public Flux<QuestionResponseDTO> getQuestionsByTag(@PathVariable String tag,
     @RequestParam(defaultValue = "0") int page,
     @RequestParam(defaultValue = "10") int size
    ) {
      return  questionService.getQuestionByTag(tag,page,size);
    }



}
