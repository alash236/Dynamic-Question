package com.example.quiz15.vo.answer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class AddAnswer {

    @Valid
    @NotEmpty(message = "答案表格不能為空")
    private List<AnswerVo> answerList;

    public List<AnswerVo> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerVo> answerList) {
        this.answerList = answerList;
    }
}
