package com.task.model.board.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardStatus {
    POSTING("게시"),
    BAN("금지"),
    DELETE("삭제");

    private String boardStatus;
}
