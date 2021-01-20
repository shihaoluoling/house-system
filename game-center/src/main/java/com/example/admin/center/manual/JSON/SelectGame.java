package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.GameCenter;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectGame
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectGame {
    /**
     * 总数
     */
    private long nums;
    /**
     *
     *
     */
    private List<GameCenter> gameCenters;

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<GameCenter> getGameCenters() {
        return gameCenters;
    }

    public void setGameCenters(List<GameCenter> gameCenters) {
        this.gameCenters = gameCenters;
    }
}
