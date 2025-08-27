package com.example.quiz15.service.answer.ifs;

import com.example.quiz15.vo.answer.AddAnswer;
import com.example.quiz15.vo.answer.AnswerResponse;
import org.springframework.stereotype.Service;

public interface AnswerService {

    public AnswerResponse addAnswer(AddAnswer addAnswer) throws Exception;

    public AnswerResponse searchAnswer(int question_id);

    public AnswerResponse searchAllAnswer();

    public AnswerResponse searchSingleAnswer(int question_id,String username);

    public AnswerResponse deleteAnswer(int question_id);

    public AnswerResponse deleteAnswerFromMail(int question_id,String username);

    public AnswerResponse searchEmailAnswer(String username);
}
