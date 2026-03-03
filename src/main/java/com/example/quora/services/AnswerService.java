package com.example.quora.services;

import com.example.quora.adapters.AnswerAdapter;
import com.example.quora.dtos.AnswerRequestDTO;
import com.example.quora.dtos.AnswerResponseDTO;
import com.example.quora.models.Answer;
import com.example.quora.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AnswerService implements IAnswerService{

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) {
        Answer answer = Answer.builder()
                .content(answerRequestDTO.getContent())
                .questionId(answerRequestDTO.getQuestionId())
                .build();
        return mongoTemplate.save(answer)
                .map(AnswerAdapter::toAnswerResponseDTO)
                .doOnSuccess(answerResponse -> {
                    System.out.println("Answer created successfully : " + answerResponse);
                })
                .doOnError(error -> {
                    System.out.println("Error creating answer : " + error.getMessage());
                });
    }

    @Override
    public Mono<AnswerResponseDTO> getAnswerById(String id) {
        return null;
    }
}
