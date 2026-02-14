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
@Document(collection = "answers")
public class Answer {

    @Id
    private String id;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 Characters")
    private String content;

    // A Question can have multiple answers but an answer belongs to a single question.
    // that means each answer will be having associated question's id with itself.
    private String questionId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;




}
