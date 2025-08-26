package com.example.quiz15.dao.question;

import com.example.quiz15.entity.question.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into question (name,text,start_Time,end_Time,publish) " +
            "values (:name,:text,:start_Time,:end_Time,:publish)",nativeQuery = true)
    public void addQuestion(String name, String text,LocalDate start_Time,LocalDate end_Time,boolean publish);

    @Modifying
    @Transactional
    @Query(value = "update question set " +
            "name = case when :name is null then name else :name end, " +
            "text = case when :text is null then text else :text end, " +
            "start_Time = case when start_Time is null then start_Time else :start_Time end, " +
            "end_Time = case when end_Time is null then end_Time else :end_Time end, " +
            "publish = case when publish is null then publish else :publish end " +
            "where id = :id",nativeQuery = true)
    public void updateQuestion(int id,String name,String text,LocalDate start_Time,LocalDate end_Time,boolean publish);

    @Modifying
    @Transactional
    @Query(value = "delete from question where id = :id",nativeQuery = true)
    public int delete(int id);

    @Query(value = "select * from question",nativeQuery = true)
    public List<Question> getAllQuestion();

    @Query(value = "select * from question where id = :id",nativeQuery = true)
    public Question getQuestion(int id);

    @Query(value ="select * from question where publish is true",nativeQuery = true)
    public List<Question> getAllPublish();
}
