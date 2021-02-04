package com.example.user.center.manual;


/**
 * @author MACHENIKE
 */

public enum CategoryIsText {
    /**
     * 分类下有文档
     */
    YEX(0),
    /**分类下没有有文档
     *
     */
    NO(1);
    private int status;
    CategoryIsText(int status) {
        this.status = status;
    }
    public int getStatus() {
        return this.status;
    }
}
