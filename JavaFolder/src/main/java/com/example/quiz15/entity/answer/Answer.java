package com.example.quiz15.entity.answer;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@IdClass(value = AnswerId.class)
public class Answer {
    @Id
    @Column(name = "question_id")
    private int question_id;

    @Id
    @Column(name = "quiz_id")
    private int quiz_id;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "answeroption")
    private String answeroption;

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

    public String getAnsweroption() {
        return answeroption;
    }

    public void setAnsweroption(String answeroption) {
        this.answeroption = answeroption;
    }


}
