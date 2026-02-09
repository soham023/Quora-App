package com.example.quora.services;

import com.example.quora.dtos.QuestionRequestDTO;
import com.example.quora.dtos.QuestionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);

    public Flux<QuestionResponseDTO> searchQuestions(String searchedTerm, int page, int size);

    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int pageSize);
}
