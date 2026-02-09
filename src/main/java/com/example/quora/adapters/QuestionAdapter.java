package com.example.quora.adapters;

import com.example.quora.dtos.QuestionResponseDTO;
import com.example.quora.models.Question;

public class QuestionAdapter {

    public static QuestionResponseDTO toQuestionResponseDTO(Question question){
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .title(question.getQuestionTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }
}
