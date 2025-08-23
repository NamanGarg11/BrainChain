package org.naman.quorabackend.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewCountEvent {
    private String TargetID;
    private String TargetType;
    private LocalDateTime Timestamp;

}
