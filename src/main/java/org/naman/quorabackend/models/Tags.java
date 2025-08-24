package org.naman.quorabackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Tags {
    @Indexed(unique = true)
    private String TagName;
//    it will help in finding the most used tags or trending tags
    private int usageCount;
}
