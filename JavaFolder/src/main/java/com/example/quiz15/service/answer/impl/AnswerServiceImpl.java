package com.example.quiz15.service.answer.impl;

import com.example.quiz15.constants.answer.AnswerResMessageCodeEnum;
import com.example.quiz15.dao.answer.AnswerDao;
import com.example.quiz15.entity.answer.Answer;
import com.example.quiz15.service.answer.ifs.AnswerService;
import com.example.quiz15.vo.answer.AddAnswer;
import com.example.quiz15.vo.answer.AnswerRes;
import com.example.quiz15.vo.answer.AnswerResponse;
import com.example.quiz15.vo.answer.AnswerVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerDao answerDao;

    @Autowired
    ObjectMapper mapper;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public AnswerResponse addAnswer(AddAnswer addAnswer) throws Exception {
        try {
            List<AnswerVo> answer = addAnswer.getAnswerList();
            if(answer == null || answer.isEmpty()){
                throw new Exception(AnswerResMessageCodeEnum.EMPTY_ERROR.getMessage());
            }
            for(AnswerVo item:answer){
                answerDao.addAnswer(
                        item.getQuestion_id(),
                        item.getQuiz_id(),
                        item.getUsername(),
                        mapper.writeValueAsString(item.getAnsweroption()));
            }
            return new AnswerResponse(
                    AnswerResMessageCodeEnum.SUCCESS_ANSWER.getCode(),
                    AnswerResMessageCodeEnum.SUCCESS_ANSWER.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public AnswerResponse searchAnswer(int question_id) {
        List<Answer> answerList = answerDao.getAnswer(question_id);
        List<AnswerRes> resultList = new ArrayList<>();
        for (Answer answer : answerList) {
            try {
                AnswerRes res = new AnswerRes(
                        answer.getQuestion_id(),
                        answer.getQuiz_id(),
                        answer.getUsername(),
                        answer.getAnsweroption()

                );
                resultList.add(res);
            } catch (Exception e) {
                return new AnswerResponse(500, "資料轉換失敗: " + e.getMessage());
            }
        }
        return new AnswerResponse(AnswerResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                AnswerResMessageCodeEnum.SUCCESS_SEARCH.getMessage(), resultList);
    }

    @Override
    public AnswerResponse searchAllAnswer() {
        List<Answer> answerList = answerDao.getAllAnswer();
        List<AnswerRes> res = new ArrayList<>();

        for (Answer answer : answerList) {
            try {
                AnswerRes answerRes = new AnswerRes(
                        answer.getQuestion_id(),
                        answer.getQuiz_id(),
                        answer.getUsername(),
                        answer.getAnsweroption()
                );
                res.add(answerRes);
            } catch (Exception e) {
                return new AnswerResponse(500, "資料轉換失敗: " + e.getMessage());
            }
        }
        return new AnswerResponse(AnswerResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                AnswerResMessageCodeEnum.SUCCESS_SEARCH.getMessage(), res);
    }

    @Override
    public AnswerResponse searchSingleAnswer(int question_id, String username) {
        List<Answer> answerList = answerDao.getSingleAnswer(question_id,username);
        System.out.println(answerList.toString());
        List<AnswerRes> res = new ArrayList<>();
        for (Answer answer : answerList) {
            try {
                AnswerRes answerRes = new AnswerRes(
                        answer.getQuestion_id(),
                        answer.getQuiz_id(),
                        answer.getUsername(),
                        answer.getAnsweroption()
                );
                res.add(answerRes);
            } catch (Exception e) {
                return new AnswerResponse(500, "資料轉換失敗: " + e.getMessage());
            }
        }
        return new AnswerResponse(AnswerResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                AnswerResMessageCodeEnum.SUCCESS_SEARCH.getMessage(), res);
    }

    @Override
    public AnswerResponse deleteAnswer(int question_id) {
        answerDao.deleteAnswer(question_id);
        return new AnswerResponse(200,"刪除成功");
    }
}
