package com.example.quora.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// this document annotation is coming from elastic search
@Document(indexName = "questions")
public class QuestionElasticDocument {
    @Id
    private String id;

    private String title;

    private String content;
}
