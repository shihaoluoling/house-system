package com.example.admin.center.manual.Enum;

public enum ItemType {
    /**
     * 单选框
     * */
    ODD("odd"),
    /**
     * 多选框
     * */
    MORE("more"),
    /**
     * 文本框
     */
    TEXT("text");

    private String login;
    ItemType(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }
}
