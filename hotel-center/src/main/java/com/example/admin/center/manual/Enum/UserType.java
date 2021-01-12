package com.example.admin.center.manual.Enum;

public enum UserType {
    //小程序微信登录
    WECHART("wechart"),
    //后台登录
    AUTH("auth");

    private String authType;
    UserType(String authType) {
        this.authType = authType;
    }

    public String getAuthType() {
        return this.authType;
    }
}
