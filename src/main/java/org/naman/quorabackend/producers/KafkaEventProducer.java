package org.naman.quorabackend.producers;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.config.KafkaConfig;
import org.naman.quorabackend.events.ViewCountEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void publishViewCountEvent(ViewCountEvent viewCountEvent) {
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, viewCountEvent.getTargetID(), viewCountEvent)
                .whenComplete((result, error) -> {
                    if(error != null) {
                        System.out.println(error.getMessage());
                    }
                });
    }
}
