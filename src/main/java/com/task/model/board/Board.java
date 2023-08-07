package com.task.model.board;

import com.task.model.board.enums.BoardStatus;
import com.task.model.global.BaseEntity;
import com.task.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

}
