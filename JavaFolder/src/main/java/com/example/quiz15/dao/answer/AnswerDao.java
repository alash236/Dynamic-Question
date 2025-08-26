package com.example.quiz15.dao.answer;

import com.example.quiz15.entity.answer.Answer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerDao extends JpaRepository<Answer,Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into answer (question_id,quiz_id,username,answeroption) values " +
            "(:question_id,:quiz_id,:username,:answeroption)",nativeQuery = true)
    public void addAnswer(int question_id, int quiz_id, String username, String answeroption);

    @Query(value = "select * from answer",nativeQuery = true)
    public List<Answer> getAllAnswer();

    @Query(value = "select * from answer where question_id = :question_id and username = :username",nativeQuery = true)
    public List<Answer> getSingleAnswer(int question_id,String username);

    @Query(value = "select * from answer where question_id = :question_id",nativeQuery = true)
    public List<Answer> getAnswer(int question_id);

    @Modifying
    @Transactional
    @Query(value ="delete from answer where question_id = :question_id" ,nativeQuery = true)
    public void deleteAnswer(int question_id);
}
