package org.naman.quorabackend.repositories;

import org.naman.quorabackend.models.Tags;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Optional;

public interface TagRepository extends ReactiveMongoRepository<Tags, String> {
    Mono<Tags> findByTagName(String tagName);
    Flux<Tags> findAllByTagNameIn(Collection<String> tagNames);
    Flux<Tags> findAllByOrderByUsageCountDesc(Pageable pageable);

}
