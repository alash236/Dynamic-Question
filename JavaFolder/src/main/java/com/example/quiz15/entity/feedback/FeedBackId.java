package com.example.quiz15.entity.feedback;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeedBackId implements Serializable {
    private int question_id;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
