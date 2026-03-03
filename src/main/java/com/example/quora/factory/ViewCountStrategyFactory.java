package com.example.quora.factory;

import com.example.quora.models.LikeableType;
import com.example.quora.strategy.AnswerViewCountIncrementStrategy;
import com.example.quora.strategy.QuestionViewCountIncrementStrategy;
import com.example.quora.strategy.ViewCountIncrementStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ViewCountStrategyFactory {

    private final Map<String, ViewCountIncrementStrategy> strategyMap;

    public ViewCountStrategyFactory(
            QuestionViewCountIncrementStrategy questionStrategy,
            AnswerViewCountIncrementStrategy answerStrategy
    ) {
        strategyMap = new HashMap<>();
        strategyMap.put("QUESTION", questionStrategy);
        strategyMap.put("ANSWER", answerStrategy);
    }

    public ViewCountIncrementStrategy getStrategy(LikeableType type) {
        return strategyMap.get(type);
    }
}
