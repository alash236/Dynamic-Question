package com.example.quiz15.controller.feedback;


import com.example.quiz15.service.feedback.ifs.FeedBackService;
import com.example.quiz15.vo.answer.AnswerResponse;
import com.example.quiz15.vo.feedback.FeedBackRep;
import com.example.quiz15.vo.feedback.FeedBackRes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FeedBackController {

    @Autowired
    FeedBackService feedBackService;

    @PostMapping("/add/feedback")
    public ResponseEntity<FeedBackRep> addFeedBack(@Valid @RequestBody FeedBackRes feedBackRes) {
        return ResponseEntity.ok(feedBackService.addFeedBack(feedBackRes));
    }

    @GetMapping("/search/feedback")
    public ResponseEntity<FeedBackRep> searchFeedBack() {
        return ResponseEntity.ok(feedBackService.searchFeedBack());
    }

    @DeleteMapping("/delete/feedback/{question_id}")
    public ResponseEntity<FeedBackRep> deleteFeedBack(@PathVariable int question_id){
        return ResponseEntity.ok(feedBackService.deleteFeedBack(question_id));
    }

}
