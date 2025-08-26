package com.example.quiz15.constants.User;

public enum UserResMessageCodeEnum {
    SUCCESS_ADD(200,"新增成功!!!"),
    SUCCESS_LOGIN(200,"登入成功!!!"),
    SUCCESS_UPDATE(200,"更新成功!!!"),
    SUCCESS_SEARCH(200,"查詢成功"),
    EMAIL_EXISTS(400,"信箱已存在!!!"),
    DATA_NULL(404,"查無此資料!!!"),
    LOGIN_FAIL(404,"找不到信箱資訊!!!"),
    PASSWORD_MISMATCH(400,"密碼錯誤 請重新輸入!!!");

    private int code;
    private String message;


    UserResMessageCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
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
}
