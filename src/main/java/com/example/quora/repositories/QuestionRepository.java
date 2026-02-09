package com.example.quora.repositories;

import com.example.quora.models.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {

    /*
    * A regular expression is a "prefix expression" if it starts with a caret (^) or a left anchor (\A), followed by a string of simple symbols. For example, the regex /^abc.*/
    //will be optimized by matching only against the values from the index that start with abc.
    //Additionally, while /^a/, /^a.*/, and /^a.*$/ match equivalent strings, they have different performance characteristics. All of these expressions use an index if an appropriate index exists; however, /^a.*/, and /^a.*$/ are slower. /^a/ can stop scanning after matching the prefix.

    @Query("{ '$or' : [ { 'questionTitle' : { $regex : ?0 , $options : 'i' } } , { 'content' : { $regex : ?0 , $options : 'i' } } ] } ")
    Flux<Question> findByQuestionTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String query,  Pageable pageable);

    Flux<Question> findByCreatedAtGreaterThanOrderByCreatedAtAsc(Instant cursorTimeStamp, Pageable pageable);



    Flux<Question> findTop10ByOrderByCreatedAtAsc();

//    Flux  ->
//    Whenever you have to represent more than 1 record

//    Mono  ->
//    Whenever you have to represent 1 record


//     instead of List<Question> in reactive programming we use Flux<Question>
//    Flux<Question> findByAuthorId(String authorId);

//    instead of saying Long in reactive programming we will use Mono<Long>
//    Mono<Long> countByAuthorId(String authorId);
}
