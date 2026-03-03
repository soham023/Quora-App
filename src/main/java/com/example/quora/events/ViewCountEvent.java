package com.example.quora.events;

import com.example.quora.models.LikeableType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewCountEvent {

    /* Why both id and type?
        Because IDs may overlap across tables.
        Example:
        Table	     ID
        Question	101
        Answer	    101
        You don't know whether:
            Question 101 was viewed
            Answer 101 was viewed

    */
    private String targetId;

    private LikeableType targetType;

    private Instant timestamp;

}
