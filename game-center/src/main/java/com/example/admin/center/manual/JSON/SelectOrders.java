package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.GameOrders;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectOrders
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectOrders {
    /**
     * 总数
     */
    private long nums;
    /**
     *
     *
     */
    private List<GameOrders> gameOrders;

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public List<GameOrders> getGameOrders() {
        return gameOrders;
    }

    public void setGameOrders(List<GameOrders> gameOrders) {
        this.gameOrders = gameOrders;
    }
}
