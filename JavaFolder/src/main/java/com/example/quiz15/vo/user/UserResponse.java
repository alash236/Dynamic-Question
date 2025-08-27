package com.example.quiz15.vo.user;

import com.example.quiz15.entity.user.User;

import java.util.List;

public class UserResponse {

    private int code;
    private String message;
    private User User;
    private List<User> allUser;

    public UserResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public UserResponse(int code, String message,List<User> allUser) {
        this.allUser = allUser;
        this.code = code;
        this.message = message;
    }

    public UserResponse(int code, String message, User user) {
        this.code = code;
        this.message = message;
        this.User = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }

    public List<User> getAllUser() {
        return allUser;
    }

    public void setAllUser(List<User> allUser) {
        this.allUser = allUser;
    }
}
