package com.task.model.board.mapper;

import com.task.model.board.Board;
import com.task.model.board.dto.BoardResponse;

public class BoardMapper {

    public static BoardResponse toResponse(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .status(board.getBoardStatus().getBoardStatus())
                .writer(board.getMember().getEmail())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
