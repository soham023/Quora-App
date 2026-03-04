package com.example.quora.repositories;

import com.example.quora.models.QuestionElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDocumentRepository extends ElasticsearchRepository<QuestionElasticDocument, String> {

    List<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title, String content);


}
