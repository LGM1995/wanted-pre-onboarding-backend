package com.task.infrastructure;

import com.task.model.board.Board;
import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByBoardByCreateDateDesc(Pageable pageable);
}
