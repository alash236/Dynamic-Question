package com.example.quiz15.service.user.ifs;

import com.example.quiz15.vo.user.AddUserRequest;
import com.example.quiz15.vo.user.LoginUserRequest;
import com.example.quiz15.vo.user.UpdateUserRequest;
import com.example.quiz15.vo.user.UserResponse;

public interface UserService {
    public UserResponse addUser(AddUserRequest addUserRequest);

    public UserResponse login(LoginUserRequest loginUserRequest);

    public UserResponse updateUser(UpdateUserRequest updateUserRequest);

    public UserResponse searchAllUser();

    public UserResponse searchUser(String email);
}
