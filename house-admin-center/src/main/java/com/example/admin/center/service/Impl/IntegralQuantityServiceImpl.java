package com.example.product.center.service.Impl;

import com.example.product.center.manual.dao.IntegralQuantitysMapper;
import com.example.product.center.service.IntegralQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author shihao
 * @Title: IntegralQuantityServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class IntegralQuantityServiceImpl implements IntegralQuantityService {
    @Autowired
    private IntegralQuantitysMapper integralQuantityServiceMapper;
    @Override
    public Integer updateQuantity(Integer integralId,Integer quantity) {
        return integralQuantityServiceMapper.updateQuantity(integralId,quantity);
    }

    /**
     *
     * @param exemptCommission
     * @param userId
     * @param storeId
     * @return修改额度免手续费
     */
    @Override
    public Integer updateExemptCommission(Integer exemptCommission, Integer userId, Integer storeId) {
        return integralQuantityServiceMapper.updateExemptCommission(exemptCommission,userId,storeId);
    }

    @Override
    public Integer updateBalance(String balanceType, Integer balance, Integer userId, Integer storeId) {
        return integralQuantityServiceMapper.updateBalance(balanceType,balance,userId,storeId);
    }

    @Override
    public Integer addBalance(String balanceType, Integer balance, Integer userId, Integer storeId) {
        return integralQuantityServiceMapper.addBalance(balanceType,balance,userId,storeId);
    }
}
