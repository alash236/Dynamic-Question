package com.example.quiz15.dao.user;

import com.example.quiz15.entity.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,String> {

    @Modifying
    @Transactional
    @Query(value = "insert into user (name,password,email,phone,age) values " +
            "(:name,:password,:email,:phone,:age)",nativeQuery = true)
    public void addUser(String name,String password,String email,String phone,int age);

    @Modifying
    @Transactional
    @Query(value = "update user set " +
            "name = if(:name is null, name, :name), " +
            "password = if(:password is null, password, :password), " +
            "phone = if(:phone is null, phone, :phone), " +
            "age = if(:age is null, age, :age) " +
            "where email = :email",nativeQuery = true)
    public void updateUser(String name,String password,String email,String phone,int age);

    @Query(value = "select count(email) from user where email = :email",nativeQuery = true)
    public int getUser(String email);

    @Query(value = "select * from user where email = :email",nativeQuery = true)
    public User searchUserByEmail(String email);

    @Query(value = "select * from user",nativeQuery = true)
    public List<User> searchAllUser();
}
