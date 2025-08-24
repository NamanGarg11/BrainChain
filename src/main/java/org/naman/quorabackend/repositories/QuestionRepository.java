package org.naman.quorabackend.repositories;

import org.naman.quorabackend.dto.QuestionResponseDTO;
import org.naman.quorabackend.models.Question;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {

    @Query("{ '$or': [ { 'title': { $regex: ?0, $options: 'i' } }, { 'content': { $regex: ?0, $options: 'i' } } ] }")
    Flux<Question> findByTitleOrContentContainsIgnoreCase(String searchTerm, Pageable pageable);

    Flux<Question> findByCreatedAtGreaterThanOrderByCreatedAtAsc(LocalDateTime cursor , Pageable pageable);

    Flux<Question> findTop10ByOrderByCreatedAtAsc();

    Mono<Long> countByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

    Flux<Question> findByTagsContaining(String tagName, Pageable pageable);
}

