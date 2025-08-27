package com.example.quiz15.vo.answer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.quiz15.constants.answer.AnswerResMessageCode.*;
import static com.example.quiz15.constants.answer.AnswerResMessageCode.QUESTIONID_ERROR;

public class AnswerVo {

    @NotNull(message = QUESTION_ERROR)
    @Min(value = 1,message = QUESTIONID_ERROR)
    private int question_id;

    @NotNull(message = QUIZ_ERROR)
    @Min(value = 1,message = QUIZID_ERROR)
    private int quiz_id;

    @NotBlank(message = USERNAME_ERROR)
    private String username;
    
    private List<String> answeroption;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAnsweroption() {
        return answeroption;
    }

    public void setAnsweroption(List<String> answeroption) {
        this.answeroption = answeroption;
    }

}
