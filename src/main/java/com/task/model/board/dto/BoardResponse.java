package com.task.model.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Builder
    public BoardResponse(Long id, String title, String content, String status, String writer, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.writer = writer;
        this.createDate = createDate;
    }
}
