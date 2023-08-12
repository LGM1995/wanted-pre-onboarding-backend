package com.task.model.board;

import com.task.model.board.enums.BoardStatus;
import com.task.model.global.BaseEntity;
import com.task.model.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public Board(String title, String content, BoardStatus boardStatus, Member member) {
        this.title = title;
        this.content = content;
        this.boardStatus = boardStatus;
        this.member = member;
    }

    public void update(String title, String content) {
        if (StringUtils.hasText(title)) {
            this.title = title;
        }
        if (StringUtils.hasText(content)) {
            this.content = content;
        }
    }

    public void delete() {
        this.boardStatus = BoardStatus.DELETE;
    }
}
