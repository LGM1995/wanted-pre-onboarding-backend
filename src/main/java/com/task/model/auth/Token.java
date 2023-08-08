package com.task.model.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Token {

    private String accessToken;

    @Builder
    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
}
