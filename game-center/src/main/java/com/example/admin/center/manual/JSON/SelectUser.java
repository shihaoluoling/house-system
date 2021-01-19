package com.example.admin.center.manual.JSON;

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
    private List<GameUser> gameUsers;

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<GameUser> getGameUsers() {
        return gameUsers;
    }

    public void setGameUsers(List<GameUser> gameUsers) {
        this.gameUsers = gameUsers;
    }
}
