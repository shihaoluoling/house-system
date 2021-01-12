package com.example.admin.center.manual.JSON;

import com.example.admin.center.model.HotelProduct;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectProduct
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectProduct {

    /**
     * nums 满足条件总数
     * */
    private Long nums;
    /**
     * 商品集合
     * */
    private List<HotelProduct> hotelProducts;

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public List<HotelProduct> getHotelProducts() {
        return hotelProducts;
    }

    public void setHotelProducts(List<HotelProduct> hotelProducts) {
        this.hotelProducts = hotelProducts;
    }
}
