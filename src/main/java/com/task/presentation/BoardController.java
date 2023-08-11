package com.task.presentation;

import com.task.application.BoardService;
import com.task.model.board.dto.BoardRequest;
import com.task.model.board.dto.BoardResponse;
import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @PostMapping
    public ResponseEntity<BoardResponse> post(@RequestBody BoardRequest boardRequest) {
        return ResponseEntity.ok(boardService.post(boardRequest));
    }
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long boardId, @RequestBody BoardRequest boardRequest) {
        return ResponseEntity.ok(boardService.update(boardId, boardRequest));
    }
    @PostMapping("/{boardId}")
    public ResponseEntity<BoardResponse> delete(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.delete(boardId));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> detail(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findById(boardId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(boardService.findByBoardByCreateDateDesc(pageable));
    }
}
