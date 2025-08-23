package org.naman.quorabackend.repositories;

import org.naman.quorabackend.models.Like;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LikeRepository extends ReactiveMongoRepository<Like, String> {

}
