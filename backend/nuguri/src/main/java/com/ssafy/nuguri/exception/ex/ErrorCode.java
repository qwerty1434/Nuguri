package com.ssafy.nuguri.exception.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {


    // 400
    NOT_EQUAL_PASSWORD(400, "비밀번호가 서로 일치하지 않습니다"),

    // 401
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 RefreshToken 입니다."),
    INVALID_ACCESS_TOKEN(401, "유효하지 않은 accessToken 입니다."),

    // 403
    FILE_UPLOAD_ERROR(403, "파일 업로드에 실패하였습니다"),

    // 404 NOT FOUND 잘못된 리소스 접근
    MEMBER_NOT_FOUND(404, "존재하지 않은 회원 ID 입니다."),
    MEMBER_EMAIL_NOT_FOUND(404, "존재하지 않은 이메일입니다."),
    DEAL_NOT_FOUND(404, "존재하지 않는 중고거래입니다."),
    DEAL_FAVORITE_NOT_FOUND(404, "존재하지 않는 중고거래 즐겨찾기입니다."),
    CATEGORY_NOT_FOUND(404, "존재하지 않는 카테고리입니다."),
    BASEADDRESS_NOT_FOUND(404, "존재하지 않는 지역입니다."),
    DEAL_HISTORY_NOT_FOUND(404, "존재하지 않는 중고거래 기록입니다."),
    HOBBY_NOT_FOUND(404, "존재하지 않은 취미 방입니다."),

    CHATROOM_NOT_FOUND(404, "존재하지 않는 채팅방입니다."),
    SSE_NOT_FOUND(404, "존재하지 않은 SSE입니다"),
    ALARM_NOT_FOUND(404, "존재하지 않은 알림입니다"),

    //406 취미방 조건 제한
    FULL_HOBBY_ERROR(406,"취미방 정원이 초과했습니다"),
    AGE_LIMIT_ERROR(406,"참여할 수 없는 나이의 취미방입니다"),
    SEX_LIMIT_ERROR(406,"참여할 수 없는 성별의 취미방입니다"),
    END_HOBBY_ERROR(406,"만료된 취미방입니다"),
    DIFF_ADDRESS_ERROR(406,"본인의 주소에서 참여할 수 없는 취미방입니다"),


    //409 CONFLICT 중복된 리소스
    ALREADY_SAVED_MEMBER(409, "이미 가입되어 있는 회원입니다."),
    ALREADY_USED_NICKNAME(409, "이미 사용중인 닉네임입니다."),
    ALREADY_USED_DEAL_HISTORY(409, "이미 채팅 신청한 중고거래입니다."),
    ALREADY_USED_HOBBY_HISTORY(409,"이미 신청한 취미방입니다.");

    private final int status;
    private final String message;

    }
