package com.example.quiz15.constants.answer;

import java.util.List;

public enum AnswerResMessageCodeEnum {

    SUCCESS_ANSWER(200,"答案新增成功!!!"),
    SUCCESS_SEARCH(200,"查詢成功"),
    EMPTY_ERROR("無此資料!!!");

    private  int code;
    private  String message;

    AnswerResMessageCodeEnum(String message) {
        this.message = message;
    }

    AnswerResMessageCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
