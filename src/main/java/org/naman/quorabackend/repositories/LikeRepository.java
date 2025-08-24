package org.naman.quorabackend.repositories;

import org.naman.quorabackend.models.Like;
import org.naman.quorabackend.models.enums.TargetType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeRepository extends ReactiveMongoRepository<Like, String> {
    Flux<Like> findByTargetIdAndTargetTypeAndLiked(String targetId, TargetType targetType, Boolean liked);

    Mono<Long> countByTargetIdAndTargetTypeAndLiked(String targetId, TargetType targetType, Boolean liked);

}
