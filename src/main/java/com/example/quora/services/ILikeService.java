package com.example.quora.services;

import com.example.quora.dtos.LikeRequestDTO;
import com.example.quora.dtos.LikeResponseDTO;
import reactor.core.publisher.Mono;

public interface ILikeService {

    Mono<LikeResponseDTO> createLike(LikeRequestDTO likeRequestDTO);

    // count is mono because we'll return a single property for count
    Mono<LikeResponseDTO> countLikesByTargetIdAndTargetType(String targetId, String targetType);

    Mono<LikeResponseDTO> countDislikesByTargetIdAndTargetType(String targetId, String targetType);

    Mono<LikeResponseDTO> toggleLike(String targetId, String targetType, Boolean isLike);

}
