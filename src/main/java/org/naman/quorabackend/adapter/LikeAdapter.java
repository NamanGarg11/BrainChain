package org.naman.quorabackend.adapter;

import org.naman.quorabackend.dto.LikeResponseDTO;
import org.naman.quorabackend.models.Like;

import java.time.LocalDate;

public class LikeAdapter {
   public static LikeResponseDTO toResponseDTO(Like like) {
        return new LikeResponseDTO(
                like.getTargetId(),
                like.getTargetType(),
                like.getIsLike(),
                LocalDate.now()
        );
    }
}
