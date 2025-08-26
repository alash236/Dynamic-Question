package com.example.quiz15.entity.quiz;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz")
@IdClass(value = QuizId.class)
public class Quiz {

    @Id
    @Column(name = "quiz_id")
    private int quiz_id;

    @Id
    @Column(name = "question_id")
    private int question_id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "option")
    private String option;

    @Column(name = "is_required")
    private boolean is_required;

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isIs_required() {
        return is_required;
    }

    public void setIs_required(boolean is_required) {
        this.is_required = is_required;
    }
}
