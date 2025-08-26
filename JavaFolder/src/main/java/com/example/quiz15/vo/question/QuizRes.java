package com.example.quiz15.vo.question;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

import static com.example.quiz15.constants.question.QuestionResMessageCode.*;


public class QuizRes {

    @Min(value = 1,message = "問卷標題錯誤")
    private int question_id;

    @Min(value = 1,message = QUIZ_ID_ERROR)
    private int quiz_id;

    @NotBlank(message = QUIZ_NAME_ERROR)
    private String name;

    @NotBlank(message = QUIZ_TYPE_ERROR)
    private String type;

    @NotEmpty(message = QUIZ_LIST_ERROR)
    private List<String> option;

    private boolean is_required;

    ObjectMapper mapper = new ObjectMapper();

    public QuizRes(int quiz_id, String name, String type, Byte is_required, String option) throws JsonProcessingException {
        this.quiz_id = quiz_id;
        this.name = name;
        this.type = type;
        this.is_required = (is_required != null && is_required == 1); // 转为 boolean
        this.option = mapper.readValue(option, new TypeReference<List<String>>(){});
    }

    public QuizRes(int question_id,int quiz_id, String name, String type, Byte is_required, String option) throws JsonProcessingException {
        this.question_id = question_id;
        this.quiz_id = quiz_id;
        this.name = name;
        this.type = type;
        this.is_required = (is_required != null && is_required == 1); // 转为 boolean
        this.option = mapper.readValue(option, new TypeReference<List<String>>(){});
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
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

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public boolean isIs_required() {
        return is_required;
    }

    public void setIs_required(boolean is_required) {
        this.is_required = is_required;
    }
}

