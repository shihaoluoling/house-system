package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.GameAuth;
import com.example.admin.center.model.GameUser;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectAuth
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectAuth {
    /**
     * 总数
     */
    private long nums;
    /**
     *
     *
     */
    private List<GameAuth> gameAuths;

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<GameAuth> getGameAuths() {
        return gameAuths;
    }

    public void setGameAuths(List<GameAuth> gameAuths) {
        this.gameAuths = gameAuths;
    }
}
