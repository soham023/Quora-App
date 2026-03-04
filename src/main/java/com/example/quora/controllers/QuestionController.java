package com.example.quora.controllers;

import com.example.quora.dtos.QuestionRequestDTO;
import com.example.quora.dtos.QuestionResponseDTO;
import com.example.quora.models.QuestionElasticDocument;
import com.example.quora.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/questions")

//Sorthand for Constructor Injection
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

//    @GetMapping("/author/{authorId}")
//    public Flux<QuestionResponseDTO> getQuestionByAuthor(@RequestParam String authorId){
//
//    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id){
        return questionService.getQuestionById(id)
                .doOnError(error -> System.out.println("Error fetching questions : " + error))
                .doOnSuccess(response -> System.out.println("Questions fetched successfully"));
    }

    @PostMapping()
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO){
        return this.questionService.createQuestion(questionRequestDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully : " + response))
                .doOnError(error -> System.out.println("Error creating question : "+ error));
    }


//    @GetMapping("/{id}")
//    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id){
//        throw new UnsupportedOperationException("Not Implemented");
//    }



// Implementing Cursor Based Pagination
    @GetMapping()
    public Flux<QuestionResponseDTO>getAllQuestions(
            // required = false -> not mandatory
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return this.questionService.getAllQuestions(cursor, pageSize);
    }


//      @DeleteMapping("/{id}")
//      public Mono<Void> deleteQuestionById(@PathVariable String id){
//
//      }


//
//SearchQuestions
    // Offset Based Pagination
    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam String query,
            // offset -> how much records to skip
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return this.questionService.searchQuestions(query, offset, pageSize);
    }
//use flux
// include pagination

    @GetMapping("/elasticsearch")
    public List<QuestionElasticDocument> searchQuestionsByElasticSearch(@RequestParam String query){
        return questionService.searchQuestionsByElasticSearch(query);
    }

}
