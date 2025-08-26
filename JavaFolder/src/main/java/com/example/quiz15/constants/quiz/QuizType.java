package com.example.quiz15.constants.quiz;

public enum QuizType {
    SINGLE("single"),
    MULTI("multiple"),
    TEXT("text");

    private String type;

    QuizType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
