package com.example.admin.center.manual.JSON;

import com.example.admin.center.manual.model.Order;

import java.util.List;

/**
 * @author shihao
 * @Title: MiniSelectOrder
 * @ProjectName Second-order-center
 * @Description:小程序查询订单
 * @date Created in
 * @Version: $
 */
public class MiniSelectOrder {
    /**
     * nums 满足条件总数
     * */
    private Long nums;

    /**
     *
     * */
    private List<Order> orders;

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
