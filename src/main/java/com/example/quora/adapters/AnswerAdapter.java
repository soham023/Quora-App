package com.example.quora.adapters;

import com.example.quora.dtos.AnswerResponseDTO;
import com.example.quora.dtos.QuestionResponseDTO;
import com.example.quora.models.Answer;
import com.example.quora.models.Question;

public class AnswerAdapter {

    public static AnswerResponseDTO toAnswerResponseDTO(Answer answer){
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .questionId(answer.getQuestionId())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }
}
