package com.example.quora.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// this annotation is used to mark the particular model valid for mongodb,
// and it has a property collections by which you can decide at which collection you want to store this model as a document
@Document (collection = "questions")
public class Question {

    @Id
    private String id;

    //for this annotation to work, have to add a dependency on build.gradle 'org.springframework.boot:spring-boot-starter-validation'
    @NotBlank(message = "Title is required")
    @Size(min = 10, max = 100, message = "Title must be between 10 and 100 characters")
    private String questionTitle;

    @NotBlank
    @Size(min = 10, max = 100, message = "Content must be between 10 and 1000 characters")
    private String content;


    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant  updatedAt;



}
