package com.task.model.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private Long id;

    private String title;

    private String content;

    private String status;

    private String writer;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @Builder
    public BoardResponse(Long id, String title, String content, String status, String writer, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.writer = writer;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
