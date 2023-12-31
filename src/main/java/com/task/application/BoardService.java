package com.task.application;

import com.task.exception.ErrorCodeMessage;
import com.task.exception.TaskException;
import com.task.infrastructure.BoardRepository;
import com.task.infrastructure.MemberRepository;
import com.task.model.board.Board;
import com.task.model.board.dto.BoardRequest;
import com.task.model.board.dto.BoardResponse;
import com.task.model.board.enums.BoardStatus;
import com.task.model.board.mapper.BoardMapper;
import com.task.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public BoardResponse post(Long memberId, BoardRequest boardRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        return BoardMapper.toResponse(boardRepository.save(Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .boardStatus(BoardStatus.POSTING)
                .member(member)
                .build()));
    }

    public BoardResponse findById(Long id) {
        return BoardMapper.toResponse(boardRepository.findById(id)
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND)));
    }

    public BoardResponse update(Long memberId, Long id, BoardRequest boardRequest) {
        Board board = checkBoardOwner(memberId, id);
        board.update(boardRequest.getTitle(), boardRequest.getContent());
        boardRepository.save(board);
        return BoardMapper.toResponse(board);
    }

    public BoardResponse delete(Long memberId, Long id) {
        Board board = checkBoardOwner(memberId, id);
        board.delete();
        boardRepository.save(board);
        return BoardMapper.toResponse(board);

    }

    public Page<BoardResponse> findAll(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByOrderByCreateDateDesc(pageable);
        return new PageImpl<>(boards.stream()
                .map(BoardMapper::toResponse)
                .collect(Collectors.toList()));
    }

    public Board checkBoardOwner(Long memberId, Long boardId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND));

        if (member != board.getMember()) {
            throw new TaskException(ErrorCodeMessage.BOARD_OWNER);
        }

        return board;
    }

}
