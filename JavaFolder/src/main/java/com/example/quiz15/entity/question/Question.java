package com.example.quiz15.entity.question;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "start_Time")
    private LocalDate startTime;

    @Column(name = "end_Time")
    private LocalDate end_Time;

    @Column(name = "publish")
    private boolean publish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getStart_Time() {
        return startTime;
    }

    public void setStart_Time(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEnd_Time() {
        return end_Time;
    }

    public void setEnd_Time(LocalDate end_Time) {
        this.end_Time = end_Time;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
