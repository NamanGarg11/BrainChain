package org.naman.quorabackend.services;

import org.naman.quorabackend.models.Question;
import org.naman.quorabackend.models.QuestionElasticDocument;

public interface IQuestionIndexService {
    void createQuestionIndex(Question question);
}
