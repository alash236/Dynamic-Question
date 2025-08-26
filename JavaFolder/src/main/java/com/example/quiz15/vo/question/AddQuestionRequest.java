package com.example.quiz15.vo.question;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static com.example.quiz15.constants.question.QuestionResMessageCode.*;

public class AddQuestionRequest {

    @NotBlank(message = TITLE_NAME_ERROR)
    private String name;

    @NotBlank(message = TITLE_TEXT_ERROR)
    private String text;

    @NotNull(message = TITLE_START_TIME_ERROR)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_Time;

    @NotNull(message = TITLE_END_TIME_ERROR)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_Time;

    private boolean publish;

    @Valid
    @NotEmpty(message = "問卷問題錯誤")
    private List<QuizVo> quizList;

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
        return start_Time;
    }

    public void setStart_Time(LocalDate start_Time) {
        this.start_Time = start_Time;
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

    public List<QuizVo> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<QuizVo> quizList) {
        this.quizList = quizList;
    }
}
