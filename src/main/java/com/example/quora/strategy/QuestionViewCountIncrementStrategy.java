package com.example.quora.strategy;

import org.springframework.stereotype.Component;

@Component
public class QuestionViewCountIncrementStrategy
        implements ViewCountIncrementStrategy {

    @Override
    public void incrementView(String targetId) {
        // increment question view in DB
    }
}
