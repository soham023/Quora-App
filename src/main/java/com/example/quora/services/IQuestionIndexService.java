package com.example.quora.services;

import com.example.quora.models.Question;
import com.example.quora.models.QuestionElasticDocument;
import reactor.core.publisher.Mono;

public interface IQuestionIndexService {

    void  createQuestionIndex(Question question);
}
