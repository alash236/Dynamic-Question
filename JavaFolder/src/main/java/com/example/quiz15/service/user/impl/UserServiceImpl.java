package com.example.quiz15.service.user.impl;

import com.example.quiz15.constants.User.UserResMessageCodeEnum;
import com.example.quiz15.dao.user.UserDao;
import com.example.quiz15.entity.user.User;
import com.example.quiz15.service.user.ifs.UserService;
import com.example.quiz15.vo.user.AddUserRequest;
import com.example.quiz15.vo.user.LoginUserRequest;
import com.example.quiz15.vo.user.UpdateUserRequest;
import com.example.quiz15.vo.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    UserDao userDao;

    @Override
    public UserResponse addUser(AddUserRequest addUserRequest) {

        //檢查User資訊是否存在
        if(userDao.getUser(addUserRequest.getEmail())==1){
            return new UserResponse(UserResMessageCodeEnum.EMAIL_EXISTS.getCode(),
                    UserResMessageCodeEnum.EMAIL_EXISTS.getMessage());
        }

        try {
            userDao.addUser(
                    addUserRequest.getName(),
                    encoder.encode(addUserRequest.getPassword()),
                    addUserRequest.getEmail(),
                    addUserRequest.getPhone(),
                    addUserRequest.getAge());
            return new UserResponse(UserResMessageCodeEnum.SUCCESS_ADD.getCode(),
                    UserResMessageCodeEnum.SUCCESS_ADD.getMessage());
        } catch (Exception e) {
            return new UserResponse(400,e.getMessage());
        }
    }


    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = userDao.searchUserByEmail(updateUserRequest.getEmail());
        if(user==null){
            return new UserResponse(UserResMessageCodeEnum.DATA_NULL.getCode(),
                    UserResMessageCodeEnum.DATA_NULL.getMessage());
        }

        try {
            if(!encoder.matches(updateUserRequest.getPassword(), user.getPassword())){
                return new UserResponse(UserResMessageCodeEnum.PASSWORD_MISMATCH.getCode(),
                        UserResMessageCodeEnum.PASSWORD_MISMATCH.getMessage()
                );
            }
            userDao.updateUser(
                    updateUserRequest.getName(),
                    encoder.encode(updateUserRequest.getNew_password()),
                    updateUserRequest.getEmail(),
                    updateUserRequest.getPhone(),
                    updateUserRequest.getAge());
            return new UserResponse(UserResMessageCodeEnum.SUCCESS_UPDATE.getCode(),
                    UserResMessageCodeEnum.SUCCESS_UPDATE.getMessage());
        } catch (Exception e) {
            return new UserResponse(400,e.getMessage());
        }
    }

    @Override
    public UserResponse login(LoginUserRequest loginUserRequest) {

        User user = userDao.searchUserByEmail(loginUserRequest.getEmail());
        if(user==null){
            return new UserResponse(UserResMessageCodeEnum.LOGIN_FAIL.getCode(),
                    UserResMessageCodeEnum.LOGIN_FAIL.getMessage());
        }

        try {
            if(!encoder.matches(loginUserRequest.getPassword(), user.getPassword())){
                return new UserResponse(UserResMessageCodeEnum.PASSWORD_MISMATCH.getCode(),
                        UserResMessageCodeEnum.PASSWORD_MISMATCH.getMessage()
                );
            }
            return new UserResponse(UserResMessageCodeEnum.SUCCESS_LOGIN.getCode(),
                    UserResMessageCodeEnum.SUCCESS_LOGIN.getMessage() +
                    " 你好 " + user.getName(),user);
        } catch (Exception e) {
            return new UserResponse(400,e.getMessage());
        }
    }


    @Override
    public UserResponse searchAllUser() {
        List<User> user = userDao.searchAllUser();
        for(User item:user){
            if(item == null){
                return new UserResponse(UserResMessageCodeEnum.LOGIN_FAIL.getCode(),
                        UserResMessageCodeEnum.LOGIN_FAIL.getMessage());
            }
        }
        return new UserResponse(UserResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                UserResMessageCodeEnum.SUCCESS_SEARCH.getMessage(),user);
    }

    @Override
    public UserResponse searchUser(String email) {
        User user = userDao.searchUserByEmail(email);
        if(user==null){
            return new UserResponse(UserResMessageCodeEnum.LOGIN_FAIL.getCode(),
                    UserResMessageCodeEnum.LOGIN_FAIL.getMessage());
        }
        return new UserResponse(UserResMessageCodeEnum.SUCCESS_SEARCH.getCode(),
                UserResMessageCodeEnum.SUCCESS_SEARCH.getMessage(),user);
    }
}
