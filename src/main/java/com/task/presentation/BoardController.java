package com.task.presentation;

import com.task.application.BoardService;
import com.task.model.board.dto.BoardRequest;
import com.task.model.board.dto.BoardResponse;
import com.task.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> post(@AuthenticationPrincipal MemberDetails memberDetails,
                                              @RequestBody BoardRequest boardRequest) {
        return ResponseEntity.ok(boardService.post(memberDetails.getMember().getId(), boardRequest));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(@AuthenticationPrincipal MemberDetails memberDetails,
                                                @PathVariable Long boardId,
                                                @RequestBody BoardRequest boardRequest) {
        return ResponseEntity.ok(boardService.update(memberDetails.getMember().getId(), boardId, boardRequest));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardResponse> delete(@AuthenticationPrincipal MemberDetails memberDetails,
                                                @PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.delete(memberDetails.getMember().getId(), boardId));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> detail(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findById(boardId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BoardResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(boardService.findAll(pageable));
    }
}
