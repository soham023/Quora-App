package com.example.quora.services;

import com.example.quora.dtos.AnswerRequestDTO;
import com.example.quora.dtos.AnswerResponseDTO;
import reactor.core.publisher.Mono;

public interface IAnswerService {

    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);

    public Mono<AnswerResponseDTO> getAnswerById(String id);

}
