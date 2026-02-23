package com.example.quora.services;


import com.example.quora.adapters.QuestionAdapter;
import com.example.quora.dtos.QuestionRequestDTO;
import com.example.quora.dtos.QuestionResponseDTO;
import com.example.quora.models.Question;
import com.example.quora.repositories.QuestionRepository;
import com.example.quora.utils.CursorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class QuestionService implements IQuestionService{

    @Autowired
    private QuestionRepository questionRepository;


    @Autowired
    private ReactiveMongoTemplate mongoTemplate;


//    public QuestionService(QuestionRepository _questionRepository){
//        this.questionRepository =_questionRepository;
//    }

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {

        Question question = Question.builder()
                .questionTitle(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .createdAt(Instant .now())
                .updatedAt(Instant .now())
                .build();

//        now we need mongoTemplate/questionRepository to save
        return mongoTemplate.save(question)
                .map(QuestionAdapter::toQuestionResponseDTO)
        //        as this is a reactive code we can decide at this point what we should do at success or failure
                .doOnSuccess(response -> System.out.println("Question created successfully : " + response))
                .doOnError(error -> System.out.println("Error creating question : "+ error));


    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String searchedTerm, int offset, int pageSize) {

        Flux<Question> questionList =  questionRepository.findByQuestionTitleContainingIgnoreCaseOrContentContainingIgnoreCase(".*" +searchedTerm + ".*",   PageRequest.of(offset , pageSize ));

        return questionList
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnNext((question) -> System.out.println("Questions searched successfully : " + question ))
                .doOnError(error -> System.out.println("Error searching question : " + error));
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        String decodedCursor = URLDecoder.decode(cursor, StandardCharsets.UTF_8);
        if(!CursorUtils.isValidCursor(decodedCursor)){
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(pageSize)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching Top 10 questions : " + error))
                    .doOnComplete(() -> System.out.println("Top 10 Questions Fetched successfully"));
        }
        else{

            Instant cursorTimeStamp = CursorUtils.parseCursor(decodedCursor);

            // SQL equivalent -> Select * from <table-name> where createdAt > cursor limit 10 ORDER BY createdAt ASC;
            // It is first going to fetch all the records where createdAt > Timestamp (that means that is createdAt after those)
            // and then order those by ascending order.
            // so whatever was the latest one will be coming at the last
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions : " + error))
                    .doOnComplete(() -> System.out.println("Questions Fetched successfully"));
        }
    }

    @Override
    public Mono<QuestionResponseDTO>  getQuestionById(String id) {

        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error fetching questions : " + error))
                .doOnSuccess(response -> System.out.println("Question fetched successfully : "+ response));
    }

}
