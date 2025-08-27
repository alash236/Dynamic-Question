package com.example.quiz15.service.feedback.ifs;

import com.example.quiz15.vo.feedback.FeedBackRep;
import com.example.quiz15.vo.feedback.FeedBackRes;

public interface FeedBackService {
    public FeedBackRep addFeedBack(FeedBackRes feedBackRes);
    public FeedBackRep searchFeedBack();
    public FeedBackRep searchFeedBackEmail(String email);
    public FeedBackRep deleteFeedBack(int question_id);
}
