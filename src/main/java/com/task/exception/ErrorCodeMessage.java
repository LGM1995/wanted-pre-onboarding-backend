package com.task.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeMessage {

    BAD_REQUEST(400, "잘못된 값 입니다."),
    DUPLICATION_EMAIL(400, "가입된 이메일 계정입니다."),
    NOT_VALID_PASSWORD(400, "비밀번호는 8자 이상입니다."),
    INVALID_LOGIN(400, "이메일과 비밀번호를 확인해주세요"),
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    BOARD_OWNER(400, "본인의 게시글만 가능합니다."),
    BOARD_NOT_FOUND(404, "존재하지 않는 게시글입니다."),

    UN_AUTHORIZED(401, "잘못된 인증입니다.");

    private final int status;
    private final String message;

    ErrorCodeMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }
}