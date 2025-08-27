package com.example.quiz15.constants.question;

public enum QuestionResMessageCodeEnum {
    SUCCESS_ADD(200,"新增問卷成功!!!"),
    SUCCESS_UPDATE(200,"更新問卷成功!!!"),
    SUCCESS_DELETE(200,"刪除問卷成功!!!"),
    SUCCESS_SEARCH(200,"查詢成功!!!"),
    SEARCH_FAIL(404,"查無此資料"),
    START_TIME_ERROR("開始日期不能大於結束日期或是小於今天!!!"),
    END_TIME_ERROR("結束日期不能小於開始日期或是小於今天!!!"),
    TYPE_ERROR("題型設定錯誤!!!"),
    OPTION_SIZE_ERROR("題目類型不是文字題時選項必須大於等於2!!!"),
    OPTION_TEXT_ERROR("題目類型是文字題時選項必須為空!!!");


    private int code;
    private String message;

    QuestionResMessageCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    QuestionResMessageCodeEnum(String message){
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
