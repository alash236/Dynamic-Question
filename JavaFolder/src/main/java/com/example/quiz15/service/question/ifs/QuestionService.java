package com.example.quiz15.service.question.ifs;

import com.example.quiz15.vo.question.AddQuestionRequest;
import com.example.quiz15.vo.question.QuestionResponse;
import com.example.quiz15.vo.question.UpdateQuestionRequest;

public interface QuestionService {
    public QuestionResponse addQuestion(AddQuestionRequest addQuestionRequest) throws Exception;
    public QuestionResponse updateQuestion(int question_id,UpdateQuestionRequest updateQuestionRequest) throws Exception;
    public QuestionResponse deleteQuestion(int id) throws Exception;
    public QuestionResponse searchAllQuestion();
    public QuestionResponse searchQuestion(int id);
    public QuestionResponse searchQuestionQuiz(int question_id);
    public QuestionResponse searchAllPublish();
    public QuestionResponse searchAllQuiz();
}
