package org.naman.quorabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naman.quorabackend.models.enums.TargetType;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LikeResponseDTO {
    private String TargetId;
    private TargetType targetType;
    private Boolean IsLike;
    private LocalDate createdDate;
}
