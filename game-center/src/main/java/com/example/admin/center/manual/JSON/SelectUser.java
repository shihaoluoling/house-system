package com.example.admin.center.manual.JSON;

import com.example.admin.center.manual.model.User;
import com.example.admin.center.model.GameUser;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectUser
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectUser {
    /**
     * 总数
     */
    private long nums;
    /**
     *
     *
     */
    private List<User> Users;

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }
}
