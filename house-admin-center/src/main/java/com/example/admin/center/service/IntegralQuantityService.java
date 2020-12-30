package com.example.product.center.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author shihao
 * @Title: IntegralQuantityService
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public interface IntegralQuantityService {
    Integer updateQuantity(@Param("integralId")Integer integralId,@Param("quantity") Integer quantity);
    /**
     * 修改额度
     */
    Integer updateExemptCommission(@Param("exemptCommission")Integer exemptCommission,@Param("userId")Integer userId,@Param("storeId")Integer storeId);

    /**
     *
     * @param balanceType 余额类型
     * @param balance 扣减数量
     * param
     * @return
     * 扣减用户积分余额
     */
    Integer updateBalance(@Param("balanceType")String balanceType, @Param("balance") Integer balance,@Param("userId")Integer userId,@Param("storeId")Integer storeId);
    /**
     *
     * @param balanceType 余额类型
     * @param balance 扣减数量
     * param
     * @return
     * 增加用户积分余额
     */
    Integer addBalance(@Param("balanceType")String balanceType, @Param("balance") Integer balance,@Param("userId")Integer userId,@Param("storeId")Integer storeId);
}
