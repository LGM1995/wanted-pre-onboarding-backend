package com.task.model.board.dto;

import com.task.util.annotation.CommonCheck;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequest {

    @CommonCheck
    private String title;

    @CommonCheck
    private String content;

    @Builder
    public BoardRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
