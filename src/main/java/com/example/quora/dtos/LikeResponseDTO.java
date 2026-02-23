package com.example.quora.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDTO {

    private String id;

    private String targetId;

    private String targetType;

    private Boolean isLike;

    private Instant createdAt;

    private Instant updatedAt;

}
