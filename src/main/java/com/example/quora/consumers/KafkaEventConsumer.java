package com.example.quora.consumers;

import com.example.quora.configurationsKafka.KafkaConfig;
import com.example.quora.events.ViewCountEvent;
import com.example.quora.factory.ViewCountStrategyFactory;
import com.example.quora.models.LikeableType;
import com.example.quora.repositories.QuestionRepository;
import com.example.quora.strategy.ViewCountIncrementStrategy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    private final ViewCountStrategyFactory viewCountStrategyFactory;

    public KafkaEventConsumer(QuestionRepository questionRepository, ViewCountStrategyFactory viewCountStrategyFactory) {
        this.questionRepository = questionRepository;
        this.viewCountStrategyFactory = viewCountStrategyFactory;
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_NAME,
            groupId = "view-count-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
//        if(viewCountEvent.getTargetType().equals(LikeableType.QUESTION)){
//
//        }else if(viewCountEvent.getTargetType().equals(LikeableType.ANSWER)){
//
//        }
//        --> This if else structure is violating Open-Closed Principle -->(A class should be Open for Extension, Closed for Modification)

        // So we'll use a strategy Pattern
        /* based on the fact that what kind of viewcount is coming up, you can write a viewcount incremental strategy,
            and you can have a viewcountIncrementalStrategy
        */
        /*
         A factory Class comprises all the new instance creation that you have to handle. Its responsibility is to
         create  objects. You don't want your new object creation to lie here and there randomly
         because if the constructor/anything changes in the object creation, you have to change everywhere.
         But if you have consolidated that in a factory class , then you are following a single responsibility principle and
         that class is responsible for creating objects of certain type. then you have to change it only at 1 place
         */

//        ViewCountIncrementStrategy strategy = viewCountStrategyFactory.getStrategy(viewCountEvent.getTargetType());
//
//        strategy.incrementView(viewCountEvent.getTargetId());
        questionRepository.findById(viewCountEvent.getTargetId())
                // use flatmap for mono
                .flatMap(question -> {
                    Integer currentViews = question.getViews();
                    if (currentViews == null) {
                        currentViews = 0;
                    }
                    question.setViews(currentViews + 1);
                    return questionRepository.save(question);
                })
                .subscribe(updatedQuestion -> {
                            System.out.println("View Count incremented for question : " + updatedQuestion.getId());
                        },
                        error -> {
                            System.out.println("Error incrementing view count for question : " + error.getMessage());
                        }
                );
    }
}

// interface ViewCountIncrStrategy

// QuestionViewCountIncrementStrategy

// AnswerViewCountIncrementalStrategy

// ViewCountStrategyFactory
