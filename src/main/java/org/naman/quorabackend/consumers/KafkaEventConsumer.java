package org.naman.quorabackend.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naman.quorabackend.config.KafkaConfig;
import org.naman.quorabackend.events.ViewCountEvent;
import org.naman.quorabackend.repositories.QuestionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerConfigurationSelector;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;


@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    @KafkaListener(
            topics = KafkaConfig.TOPIC_NAME,
            groupId = "quora-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
        questionRepository.findById(viewCountEvent.getTargetID())
                .flatMap(question -> {
                    System.out.println("Incrementing view count for question: " + question.getId());
                    Integer views = question.getViews();
                    question.setViews(views == null ? 0 : views + 1);
                    return questionRepository.save(question);
                })
                .subscribe(updatedQuestion -> {
                    System.out.println("View count incremented for question: " + updatedQuestion.getId());
                }, error -> {
                    System.out.println("Error incrementing view count for question: " + error.getMessage());
                });
    }

}