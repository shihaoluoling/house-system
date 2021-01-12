package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.HotelAuth;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectAuth
 * @ProjectName Second-order-center
 * @Description: 后台登录查询
 * @date Created in
 * @Version: $
 */
public class SelectAuth {
    /**
     * nums 满足条件总数
     * */
    private Long nums;
    /**
     * 商品集合
     * */
    private List<HotelAuth> hotelAuths;

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public List<HotelAuth> getHotelAuths() {
        return hotelAuths;
    }

    public void setHotelAuths(List<HotelAuth> hotelAuths) {
        this.hotelAuths = hotelAuths;
    }
}
