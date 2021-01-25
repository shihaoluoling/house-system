package com.example.admin.center.manual.model;

import com.example.admin.center.model.GameUserBook;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: User
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class User {
    /**
     * 用户id
     * */
    private Integer userId;
    /**
     * 邮箱
     * */
    private String email;
    /**
     * 电话
     * */
    private String phone;
    /**
     * 昵称
     * */
    private String nickName;
    /**
     * 真实姓名
     * */
    private String realName;
    /**
     * 性别
     * */
    private Byte sex;
    /**
     * 生日
     * */
    private LocalDateTime birthDay;
    /**
     * 用户状态
     * */
    private Byte userStatus;
    /**
     * 用户类型
     * */
    private String userType;
    /**
     * 头像
     * */
    private String file;
    /**
     * 通讯录
     * */
    private List<GameUserBook> gameUserBooks;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<GameUserBook> getGameUserBooks() {
        return gameUserBooks;
    }

    public void setGameUserBooks(List<GameUserBook> gameUserBooks) {
        this.gameUserBooks = gameUserBooks;
    }
}
