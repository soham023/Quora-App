package com.example.quora.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "likes")
public class Like {

    @Id
    private String id;

    // eg if targetType = "comment" then targetId will be some particular id of a comment
    private String targetId;

    // TODO : enum for targetType
    private LikeableType targetType; // LikeableType //


    // you can access parameterised constructor like that
    // LikeableType type = LikeableType.QUESTION;

    // this property denotes if it's a like or dislike
    private Boolean isLike;

    @CreatedDate
    private Instant createdAt;



}
