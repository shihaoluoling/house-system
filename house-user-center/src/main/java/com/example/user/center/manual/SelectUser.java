package com.example.user.center.manual;

/**
 * @author shihao
 * @Title: SelectUser
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectUser {
    private Integer userId;
    private String phone;
    private String userName;
    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
