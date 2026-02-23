package com.example.quora.services;

import com.example.quora.dtos.QuestionRequestDTO;
import com.example.quora.dtos.QuestionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);

    public Flux<QuestionResponseDTO> searchQuestions(String searchedTerm, int page, int size);

    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int pageSize);

    // whenever a new view will be coming up, we need to have getQuestionById
    // whenever someone will hit the getQuestionById -> viewcount will increase.(either by Immediate Consistency / Eventual Consistency)
    public Mono<QuestionResponseDTO> getQuestionById(String id);

}
