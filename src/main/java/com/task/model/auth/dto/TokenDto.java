package com.task.model.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class TokenDto {

    private String accessToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
