package com.example.quiz15.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.example.quiz15.constants.User.UserResMessageCode.*;

public class LoginUserRequest {

    @NotBlank(message = EMAIL_ERROR)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = EMAIL_FORMAT_ERROR)
    private String email;

    @NotBlank(message = PASSWORD_ERROR)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
