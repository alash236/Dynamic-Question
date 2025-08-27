package com.example.quiz15.vo.answer;

import java.util.List;

public class AnswerResponse {
    private int code;
    private String message;
    List<AnswerRes> allAnswer;

    public AnswerResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AnswerResponse(int code,String message,List<AnswerRes> allAnswer) {
        this.code = code;
        this.message = message;
        this.allAnswer = allAnswer;
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

    public List<AnswerRes> getAllAnswer() {
        return allAnswer;
    }

    public void setAllAnswer(List<AnswerRes> allAnswer) {
        this.allAnswer = allAnswer;
    }
}
