package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.HotelOrders;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectOrders
 * @ProjectName Second-order-center
 * @Description: 订单查询
 * @date Created in
 * @Version: $
 */
public class SelectOrders {
    /**
     * nums 满足条件总数
     * */
    private Long nums;
    /**
     * 订单集合
     * */
    private List<HotelOrders> hotelOrders;

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public List<HotelOrders> getHotelOrders() {
        return hotelOrders;
    }

    public void setHotelOrders(List<HotelOrders> hotelOrders) {
        this.hotelOrders = hotelOrders;
    }
}
