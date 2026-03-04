package com.example.quora.services;

import com.example.quora.models.Question;
import com.example.quora.models.QuestionElasticDocument;
import com.example.quora.repositories.QuestionDocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionIndexService implements IQuestionIndexService{

    private final QuestionDocumentRepository questionDocumentRepository;

    public QuestionIndexService(QuestionDocumentRepository _questionDocumentRepository) {
        this.questionDocumentRepository = _questionDocumentRepository;
    }

    @Override
    public void createQuestionIndex(Question question) {
        QuestionElasticDocument document = QuestionElasticDocument.builder()
                .id(question.getId())
                .title(question.getQuestionTitle())
                .content(question.getContent())
                .build();
        questionDocumentRepository.save(document);
    }
}
