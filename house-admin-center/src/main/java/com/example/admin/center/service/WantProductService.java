package com.example.product.center.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shihao
 * @Title: WantProductService
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public interface WantProductService {
    /**
     * 今日点赞查询
     * @param userId
     * @param type
     * @param productId
     * @return
     */
    List<SecondProductWant> selectProductPraise(@Param("userId")Integer userId,
                                                @Param("type")String type,
                                                @Param("productId")Integer productId);
    /**
     * 今日点赞查询
     * @param userId
     * @param type
     * @param productId
     * @return
     */
    List<SecondProductWant> selectProductPraise1(@Param("userId")Integer userId,
                                                @Param("type")String type,
                                                @Param("productId")Integer productId);
}
