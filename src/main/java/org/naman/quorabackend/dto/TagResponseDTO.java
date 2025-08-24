package org.naman.quorabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naman.quorabackend.models.Tags;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagResponseDTO {
    private String tagName;
    private int usageCount;

    public static TagResponseDTO from(Tags tag) {
        return TagResponseDTO.builder()
                .tagName(tag.getTagName())
                .usageCount(tag.getUsageCount())
                .build();
    }
}