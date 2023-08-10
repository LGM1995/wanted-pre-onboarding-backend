package com.task.model.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequest {

    private String title;

    private String content;

    @Builder
    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
