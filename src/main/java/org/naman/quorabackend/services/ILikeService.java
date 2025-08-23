package org.naman.quorabackend.services;

import org.naman.quorabackend.dto.LikeRequestDTO;
import org.naman.quorabackend.dto.LikeResponseDTO;
import org.naman.quorabackend.models.enums.TargetType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ILikeService {
    Mono<LikeResponseDTO> createLike(LikeRequestDTO likeRequestDTO);
    Flux<LikeResponseDTO> countLikeByTargetIdandTargetType(String Targetid, TargetType targetType);
    Mono<LikeResponseDTO> countDisLikesByTargetIdandTargetType(String TargetId,TargetType targetType);
}
