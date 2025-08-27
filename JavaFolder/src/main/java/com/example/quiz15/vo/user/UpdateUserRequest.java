package com.example.quiz15.vo.user;

import com.example.quiz15.entity.user.User;
import jakarta.validation.constraints.NotBlank;

import static com.example.quiz15.constants.User.UserResMessageCode.PASSWORD_ERROR;

public class UpdateUserRequest extends User {
    @NotBlank(message = PASSWORD_ERROR)
    private String new_password;

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
