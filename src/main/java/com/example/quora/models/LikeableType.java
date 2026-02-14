package com.example.quora.models;

import lombok.Getter;

@Getter
public enum LikeableType {
    QUESTION("question"),
    ANSWER("answer"),
    COMMENT("comment");

    private final String value;
    LikeableType(String val){
        this.value = val;
    }

}
