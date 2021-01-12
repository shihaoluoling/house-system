package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.HotelUser;

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
     * nums 满足条件总数
     * */
    private Long nums;
    /**
     * 商品集合
     * */
    private List<HotelUser> hotelUsers;

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public List<HotelUser> getHotelUsers() {
        return hotelUsers;
    }

    public void setHotelUsers(List<HotelUser> hotelUsers) {
        this.hotelUsers = hotelUsers;
    }
}
