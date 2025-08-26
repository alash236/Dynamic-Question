package com.example.quiz15.service.feedback.impl;

import com.example.quiz15.dao.feedback.FeedBackDao;
import com.example.quiz15.entity.feedback.FeedBack;
import com.example.quiz15.service.feedback.ifs.FeedBackService;
import com.example.quiz15.vo.feedback.FeedBackRep;
import com.example.quiz15.vo.feedback.FeedBackRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedBackImpl implements FeedBackService {

    @Autowired
    FeedBackDao feedBackDao;
    @Override
    public FeedBackRep addFeedBack(FeedBackRes feedBackRes) {
        if(feedBackRes == null) return new FeedBackRep(400,"資料錯誤");
        feedBackDao.addFeedBack(feedBackRes.getQuestion_id(),
                feedBackRes.getName(),
                feedBackRes.getEmail(),
                feedBackRes.getWritetime());
        return new FeedBackRep(200,"新增成功");
    }

    @Override
    public FeedBackRep searchFeedBack() {
        List<FeedBack> feedBack = feedBackDao.searchAllFeedBack();
        if(feedBack==null)return new FeedBackRep(404,"找不到填寫資訊");
        for(FeedBack item:feedBack){
            if(item == null)return new FeedBackRep(404,"找不到填寫資訊");
        }
        return new FeedBackRep(feedBack,200,"查詢成功");
    }

    @Override
    public FeedBackRep deleteFeedBack(int question_id) {
        feedBackDao.deleteFeedBack(question_id);
        return new FeedBackRep(200,"刪除成功!!!");
    }
}
