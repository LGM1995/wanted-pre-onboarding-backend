package com.task.application;

import com.task.exception.ErrorCodeMessage;
import com.task.exception.TaskException;
import com.task.infrastructure.BoardRepository;
import com.task.model.board.Board;
import com.task.model.board.dto.BoardRequest;
import com.task.model.board.dto.BoardResponse;
import com.task.model.board.enums.BoardStatus;
import com.task.model.board.mapper.BoardMapper;
import com.task.security.MemberDetails;
import java.awt.print.Pageable;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponse post(BoardRequest boardRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        Board board = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .boardStatus(BoardStatus.POSTING)
                .member(memberDetails.getMember())
                .build();

        return BoardMapper.toResponse(boardRepository.save(board));
    }

    public BoardResponse findById(Long id) {
        return BoardMapper.toResponse(boardRepository.findById(id)
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND)));
    }

    public BoardResponse update(Long id, BoardRequest boardRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND));

        if (memberDetails.getMember() == board.getMember()) {
            board.update(boardRequest.getTitle(), boardRequest.getContent());
        } else {
            throw new TaskException(ErrorCodeMessage.BOARD_OWNER);
        }
        return BoardMapper.toResponse(board);
    }

    public BoardResponse delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND));

        if (memberDetails.getMember() == board.getMember()) {
            board.delete();
        } else {
            throw new TaskException(ErrorCodeMessage.BOARD_OWNER);
        }
        return BoardMapper.toResponse(board);
    }

    public List<BoardResponse> findByBoardByCreateDateDesc(Pageable pageable) {
        Page<Board> boards = boardRepository.findByBoardByCreateDateDesc(pageable);
        return boards.stream()
                .map(BoardMapper::toResponse)
                .collect(Collectors.toList());
    }
}
