package com.example.admin.center.manual.Enum;

public enum Login {
    /**
     * //手机号登录
     * */
    PHONE("phone"),
    /**
     * //账号密码登录
     * */
    ADMIN("admin");

    private String login;
    Login(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }
}
