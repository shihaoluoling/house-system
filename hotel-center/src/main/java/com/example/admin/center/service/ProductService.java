package com.example.admin.center.service;

import com.example.admin.center.manual.model.CartProduct;
import com.example.admin.center.model.HotelProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {
    /**
     *查询购物车商品
     * cartCode 购物车编号
     * */
    List<CartProduct> selectCart(@Param("cartCode")String cartCode);
    /**
     *查询可预购商品
     *  cartCode 购物车编号
     * */
    List<HotelProduct> selectProduct(@Param("cartCode")String cartCode);
}
