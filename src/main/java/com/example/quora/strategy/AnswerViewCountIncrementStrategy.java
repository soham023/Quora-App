package com.example.quora.strategy;

import org.springframework.stereotype.Component;

@Component
public class AnswerViewCountIncrementStrategy
        implements ViewCountIncrementStrategy {
    @Override
    public void incrementView(String targetId) {
        // increment answer view in DB
    }
}