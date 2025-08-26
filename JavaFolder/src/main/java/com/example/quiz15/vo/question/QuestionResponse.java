package com.example.quiz15.vo.question;

import com.example.quiz15.entity.question.Question;
import com.example.quiz15.entity.quiz.Quiz;

import java.util.List;

public class QuestionResponse {
    private int code;
    private String message;
    private List<?> allQuestion;
    private Question question;

    public QuestionResponse(int code, Question question) {
        this.code = code;
        this.question = question;
    }

    public QuestionResponse( String message) {
        this.message = message;
    }

    public QuestionResponse(int code, List<?> allQuestion) {
        this.code = code;
        this.allQuestion = allQuestion;
    }

    public QuestionResponse(int code, String message) {
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

    public List<?> getAllQuestion() {
        return allQuestion;
    }

    public void setAllQuestion(List<?> allQuestion) {
        this.allQuestion = allQuestion;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
