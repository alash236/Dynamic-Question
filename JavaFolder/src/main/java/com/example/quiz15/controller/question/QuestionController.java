package com.example.quiz15.controller.question;

import com.example.quiz15.service.question.ifs.QuestionService;
import com.example.quiz15.vo.question.AddQuestionRequest;
import com.example.quiz15.vo.question.QuestionResponse;
import com.example.quiz15.vo.question.UpdateQuestionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/searchAllPublish")
    public ResponseEntity<QuestionResponse> searchAllPublish(){
        return ResponseEntity.ok(questionService.searchAllPublish());
    }

    @GetMapping("/searchAllquestion")
    public ResponseEntity<QuestionResponse> searchAllquestion(){
        return ResponseEntity.ok(questionService.searchAllQuestion());
    }

    @GetMapping("/searchAllquiz")
    public ResponseEntity<QuestionResponse> searchAllquiz(){
        return ResponseEntity.ok(questionService.searchAllQuiz());
    }

    @GetMapping("/searchQuestion/{id}")
    public ResponseEntity<QuestionResponse> searchQuestion(@PathVariable int id){
        return ResponseEntity.ok(questionService.searchQuestion(id));
    }

    @GetMapping("/searchQuiz/{question_id}")
    public ResponseEntity<QuestionResponse> searchQuiz(@PathVariable int question_id){
        return ResponseEntity.ok(questionService.searchQuestionQuiz(question_id));
    }

    @PostMapping("/add/question")
    public ResponseEntity<QuestionResponse> addquestion(@Valid  @RequestBody AddQuestionRequest addquestionRequest) throws Exception{
        return ResponseEntity.ok(questionService.addQuestion(addquestionRequest));
    }

    @PutMapping("/update/question/{question_id}")
    public ResponseEntity<QuestionResponse> updatequestion(@PathVariable int question_id,@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest) throws Exception {
        return ResponseEntity.ok(questionService.updateQuestion(question_id,updateQuestionRequest));
    }

    @DeleteMapping("/delete/question/{id}")
    public ResponseEntity<QuestionResponse> deletequestion(@PathVariable int id) throws Exception{
        return ResponseEntity.ok(questionService.deleteQuestion(id));
    }


}
