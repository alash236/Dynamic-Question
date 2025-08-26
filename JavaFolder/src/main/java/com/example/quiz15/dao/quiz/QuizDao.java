package com.example.quiz15.dao.quiz;

import com.example.quiz15.entity.quiz.Quiz;
import com.example.quiz15.vo.question.QuizRes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into quiz (quiz_id,name,type,is_required,`option`,question_id) " +
            " values (:quiz_id,:name,:type,:is_required,:option,:question_id)",nativeQuery = true)
    public void addQuiz(int quiz_id,int question_id,String name,String type,boolean is_required,
                       String option);

    @Modifying
    @Transactional
    @Query(value = "delete from quiz where question_id = :question_id",nativeQuery = true)
    public void deleteQuiz(int question_id);

    @Query(value = "SELECT LAST_INSERT_ID();",nativeQuery = true)
    public int getLatestQuizId();

    @Query(value = "select quiz_id,name,type,is_required,`option` from quiz where question_id = :question_id",nativeQuery = true)
    public List<QuizRes> searchQuiz(int question_id);

    @Query(value = "select * from quiz",nativeQuery = true)
    public List<QuizRes> searchAllQuiz();
}
