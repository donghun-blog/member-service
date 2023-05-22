package me.donghun.memberservice.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    MEMBER_NAME_EMPTY("회원 이름이 비었습니다."),
    MEMBER_INTRODUCE_EMPTY("자기소개가 비었습니다."),
    MEMBER_EMAIL_EMPTY("회원 이메일 비었습니다."),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다."),
    MEMBER_AVATAR_EMPTY("이미지 경로가 비었습니다."),
    MEMBER_AVATAR_UPLOAD_FAIL("이미지 업로에 실패하였습니다."),
    MEMBER_AVATAR_EXTENSION_NOT_SUPPORT("지원하지 않는 확장자입니다."),
    ;


    private final String message;
}
