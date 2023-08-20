package com.ohdab.core.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    // 600 - classroom
    NO_CLASSROOM(600, "존재하지 않는 교실입니다."),
    NO_GRADE(601, "존재하지 않는 학년입니다."),
    NO_STUDENT(602, "존재하지 않는 학생입니다."),
    NO_TEACHER(603, "존재하지 않는 선생님입니다."),

    // 700 - member
    ALREADY_WITHDRAWL(700, "이미 탈퇴한 계정입니다."),
    DUPLICATED_MEMBER(701, "이미 존재하는 회원입니다."),
    NO_MEMBER(702, "존재하지 않는 회원입니다."),
    MEMBER_CONTENT_OVERFLOW(703, "최대 글자수를 초과했습니다."),
    NO_AUTHORITY(704, "권한정보가 누락되었습니다."),

    // 800 - mistakeNote
    NO_MISTAKE_NOTE(800, "존재하지 않는 오답노트입니다."),
    NO_NUMBERS_WRONG_N_TIMES(801, ""),
    NUMBER_IS_OUT_OF_RANGE(802, "N번 이상 틀린 문제가 없습니다."),
    MISTAKE_NUMBERS_SIZE(803, "기록 가능한 문제수를 초과했습니다."),
    MISTAKE_NOTE_IS_EMPTY(804, "해당 교재에 대한 오답노트가 없습니다."),

    // 900 - workbook
    WORKBOOK_CONTENT_OVERFLOW(900, "최대 글자수를 초과했습니다."),
    DUPLICATED_WORKBOOK(901, "이미 존재하는 교재입니다."),
    INVALID_WORKBOOK_NUMBER_RANGE(902, "허용 범위를 벗어난 문제 번호입니다."),
    NO_WORKBOOK(903, "존재하지 않는 교재입니다."),

    // 1000 - null
    IS_NULL(1000, "필수 입력값이 누락되었습니다."),
    ;

    private int errorCode;
    private String message;

    ExceptionEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
