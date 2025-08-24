package org.naman.quorabackend.services;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.adapter.LikeAdapter;
import org.naman.quorabackend.dto.LikeRequestDTO;
import org.naman.quorabackend.dto.LikeResponseDTO;
import org.naman.quorabackend.models.Like;
import org.naman.quorabackend.models.enums.TargetType;
import org.naman.quorabackend.repositories.AnswerRepository;
import org.naman.quorabackend.repositories.LikeRepository;
import org.naman.quorabackend.repositories.QuestionRepository;
import org.naman.quorabackend.repositories.TagRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final TagRepository tagRepository;

    @Override
    public Mono<LikeResponseDTO> createLike(LikeRequestDTO dto) {
        // validate target exists
        Mono<?> validationMono;
        if (dto.getTargetType() == TargetType.QUESTION) {
            validationMono = questionRepository.findById(dto.getTargetId());
        } else if (dto.getTargetType() == TargetType.ANSWER) {
            validationMono = answerRepository.findById(dto.getTargetId());
        } else {
            validationMono = tagRepository.findById(dto.getTargetId());
        }

        return validationMono
                .switchIfEmpty(Mono.error(new RuntimeException("Target not found")))
                .flatMap(existing -> {
                    Like like = new Like();
                    like.setTargetId(dto.getTargetId());
                    like.setTargetType(dto.getTargetType());
                    like.setIsLike(dto.getIsLike());
                    like.setCreatedAt(LocalDateTime.now());
                    return likeRepository.save(like);
                })
                .map(LikeAdapter::toResponseDTO);
    }

    @Override
    public Flux<LikeResponseDTO> countLikeByTargetIdandTargetType(String targetId, TargetType targetType) {
        return likeRepository.findByTargetIdAndTargetTypeAndIsLike(targetId, targetType, true)
                .map(LikeAdapter::toResponseDTO);
    }

    @Override
    public Mono<LikeResponseDTO> countDisLikesByTargetIdandTargetType(String targetId, TargetType targetType) {
        return likeRepository.countByTargetIdAndTargetTypeAndIsLike(targetId, targetType, false)
                .map(count -> new LikeResponseDTO(targetId, targetType, false, LocalDate.now()));
    }


}
