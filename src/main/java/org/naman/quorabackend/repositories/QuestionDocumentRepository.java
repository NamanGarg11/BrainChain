package org.naman.quorabackend.repositories;

import java.util.List;

import org.naman.quorabackend.models.QuestionElasticDocument;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface QuestionDocumentRepository extends ReactiveMongoRepository<QuestionElasticDocument, String> {

    List<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title, String content);
}