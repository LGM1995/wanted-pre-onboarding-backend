package com.task.application;

import com.task.exception.ErrorCodeMessage;
import com.task.exception.TaskException;
import com.task.infrastructure.BoardRepository;
import com.task.infrastructure.MemberRepository;
import com.task.model.auth.dto.Login;
import com.task.model.board.Board;
import com.task.model.board.dto.BoardRequest;
import com.task.model.board.dto.BoardResponse;
import com.task.model.board.enums.BoardStatus;
import com.task.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;

    @AfterEach
    void clear() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("Success Posting Board")
    void Success_Posting() {
        String email = "test@test.com";
        String password = "test1234!";
        String encodingPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodingPassword)
                .build();
        memberRepository.save(member);

        Login login = new Login("test@test.com", password);

        UserDetails userDetails = customMemberDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        assertEquals(title, boardResponse.getTitle());

        assertEquals(email, boardResponse.getWriter());
    }

    @Test
    @DisplayName("Success Detail")
    @Transactional
    void Success_Detail() {

        Board board = Board.builder()
                .boardStatus(BoardStatus.POSTING)
                .title("제목")
                .content("내용")
                .member(memberRepository.save(Member.builder()
                        .email("test@test.com")
                        .password("test1234!").build()))
                .build();

        boardRepository.save(board);

        Board target = boardRepository.findById(board.getId()).orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND));

        assertEquals(board.getMember().getEmail(), target.getMember().getEmail());
    }
}
