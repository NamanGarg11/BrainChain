package org.naman.quorabackend.services;

import lombok.RequiredArgsConstructor;
import org.naman.quorabackend.models.Question;
import org.naman.quorabackend.models.QuestionElasticDocument;
import org.naman.quorabackend.repositories.QuestionDocumentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  QuestionIndexService implements IQuestionIndexService {
    private final QuestionDocumentRepository questionDocumentRepository;
    @Override
    public void createQuestionIndex(Question question) {
        QuestionElasticDocument questionElasticDocument =  QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();
        questionDocumentRepository.save(questionElasticDocument);
    }
}
