package org.naman.quorabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naman.quorabackend.models.enums.TargetType;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LikeRequestDTO {
    private String TargetId;
    private TargetType targetType;
    private Boolean isLike;
}
