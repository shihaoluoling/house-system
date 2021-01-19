package com.example.admin.center.manual.Enum;

/**
 * @author shihao
 * @Title: AuthLogin
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public enum  AuthLogin {
    /**
     * 前台登录
     * */
    APP("app"),
    /**
     * 后台登录
     * */
    ADMIN("admin");

    private String login;
    AuthLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }
}
