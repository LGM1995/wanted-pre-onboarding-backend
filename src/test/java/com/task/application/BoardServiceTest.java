package com.task.application;

import com.task.exception.ErrorCodeMessage;
import com.task.exception.TaskException;
import com.task.infrastructure.BoardRepository;
import com.task.infrastructure.MemberRepository;
import com.task.model.board.Board;
import com.task.model.board.dto.BoardRequest;
import com.task.model.board.dto.BoardResponse;
import com.task.model.board.enums.BoardStatus;
import com.task.model.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

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

    @BeforeEach
    void createMember() {
        String password = "test1234!";
        String encodingPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email("test@test.com")
                .password(encodingPassword)
                .build();
        memberRepository.save(member);

        UserDetails userDetails = customMemberDetailsService.loadUserByUsername(member.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        Member unauthorized = Member.builder()
                .email("unauthorized@test.com")
                .password(encodingPassword)
                .build();
        memberRepository.save(unauthorized);

    }

    @Test
    @DisplayName("Success Posting Board")
    void Success_Posting() {
        Member member = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(member.getId(), BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        assertEquals(title, boardResponse.getTitle());

        assertEquals(member.getEmail(), boardResponse.getWriter());
    }

    @Test
    @DisplayName("Success Detail")
    @Transactional
    void Success_Detail() {
        Member member = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(member.getId(), BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        BoardResponse target = boardService.findById(boardResponse.getId());

        assertEquals(target.getId(), boardResponse.getId());
    }

    @Test
    @DisplayName("Success Update")
    @Transactional
    void Success_Update() {

        Member member = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(member.getId(), BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        BoardRequest boardRequest = BoardRequest.builder()
                .title("test")
                .content("test content")
                .build();

        BoardResponse updateResponse = boardService.update(member.getId(), boardResponse.getId(), boardRequest);

        assertEquals(updateResponse.getTitle(), "test");
    }

    @Test
    @DisplayName("Unauthorized Update")
    @Transactional
    void Unauthorized_Update() {

        Member member = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(member.getId(), BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        Member unauthorized = memberRepository.findByEmail("unauthorized@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        BoardRequest boardRequest = BoardRequest.builder()
                .title("update")
                .content("update content")
                .build();


        assertThrows(TaskException.class, () -> boardService.update(unauthorized.getId(), boardResponse.getId(), boardRequest));
    }

    @Test
    @DisplayName("Success Delete")
    @Transactional
    void Success_Delete() {

        Member member = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));


        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(member.getId(), BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        boardService.delete(member.getId(), boardResponse.getId());

        Board target = boardRepository.findById(boardResponse.getId()).orElseThrow(() -> new TaskException(ErrorCodeMessage.BOARD_NOT_FOUND));

        assertEquals(target.getBoardStatus(), BoardStatus.DELETE);
    }

    @Test
    @DisplayName("Unauthorized Delete")
    @Transactional
    void Unauthorized_Delete() {

        Member member = memberRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));

        String title = "제목";
        String content = "내용";

        BoardResponse boardResponse = boardService.post(member.getId(), BoardRequest.builder()
                .title(title)
                .content(content)
                .build());

        Member unauthorized = memberRepository.findByEmail("unauthorized@test.com")
                .orElseThrow(() -> new TaskException(ErrorCodeMessage.MEMBER_NOT_FOUND));


        assertThrows(TaskException.class, () -> boardService.delete(unauthorized.getId(), boardResponse.getId()));
    }
}
