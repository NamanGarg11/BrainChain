package org.naman.quorabackend.controllers;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.dto.LikeRequestDTO;
import org.naman.quorabackend.dto.LikeResponseDTO;
import org.naman.quorabackend.models.enums.TargetType;
import org.naman.quorabackend.services.ILikeService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final ILikeService likeService;

    @PostMapping
    public Mono<LikeResponseDTO> createLike(@RequestBody LikeRequestDTO dto) {
        return likeService.createLike(dto);
    }

    @GetMapping("/{targetType}/{targetId}/likes")
    public Flux<LikeResponseDTO> getLikes(
            @PathVariable TargetType targetType,
            @PathVariable String targetId
    ) {
        return likeService.countLikeByTargetIdandTargetType(targetId, targetType);
    }

    @GetMapping("/{targetType}/{targetId}/dislikes")
    public Mono<LikeResponseDTO> getDislikes(
            @PathVariable TargetType targetType,
            @PathVariable String targetId
    ) {
        return likeService.countDisLikesByTargetIdandTargetType(targetId, targetType);
    }
}
