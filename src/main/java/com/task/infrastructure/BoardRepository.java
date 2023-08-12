package com.task.infrastructure;

import com.task.model.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByOrderByCreateDateDesc(Pageable pageable);
}
