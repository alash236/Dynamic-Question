package com.example.quiz15.controller.answer;


import com.example.quiz15.service.answer.ifs.AnswerService;
import com.example.quiz15.vo.answer.AddAnswer;
import com.example.quiz15.vo.answer.AnswerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @GetMapping("/search/answer/{question_id}/{username}")
    public ResponseEntity<AnswerResponse> searchSingleAnswer(@PathVariable int question_id,
                                                             @PathVariable String username){
        return ResponseEntity.ok(answerService.searchSingleAnswer(question_id,username));
    }

    @GetMapping("/search/Allanswer")
    public ResponseEntity<AnswerResponse> searchAllAnswer(){
        return ResponseEntity.ok(answerService.searchAllAnswer());
    }

    @GetMapping("/search/answer/{question_id}")
    public ResponseEntity<AnswerResponse> searchAnswer(@PathVariable int question_id){
        return ResponseEntity.ok(answerService.searchAnswer(question_id));
    }

    @PostMapping("/add/answer")
    public ResponseEntity<AnswerResponse> addAnswer(@Valid @RequestBody AddAnswer addAnswer) throws Exception {
        return ResponseEntity.ok(answerService.addAnswer(addAnswer));
    }

    @DeleteMapping("/delete/answer/{question_id}")
    public ResponseEntity<AnswerResponse> deleteAnswer(@PathVariable int question_id){
        return ResponseEntity.ok(answerService.deleteAnswer(question_id));
    }
}
