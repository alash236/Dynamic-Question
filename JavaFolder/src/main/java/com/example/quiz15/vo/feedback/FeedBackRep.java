package com.example.quiz15.vo.feedback;

import com.example.quiz15.entity.feedback.FeedBack;

import java.util.List;

public class FeedBackRep {
    int code;
    String message;
    private List<FeedBack> allFeedBack;

    public FeedBackRep(List<FeedBack> allFeedBack, int code, String message) {
        this.allFeedBack = allFeedBack;
        this.code = code;
        this.message = message;
    }

    public FeedBackRep(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public List<FeedBack> getAllFeedBack() {
        return allFeedBack;
    }

    public void setAllFeedBack(List<FeedBack> allFeedBack) {
        this.allFeedBack = allFeedBack;
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
