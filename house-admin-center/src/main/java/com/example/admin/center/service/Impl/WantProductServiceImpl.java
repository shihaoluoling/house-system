package com.example.product.center.service.Impl;

import com.example.product.center.manual.dao.WantProductMapper;
import com.example.product.center.service.WantProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shihao
 * @Title: WantProductServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class WantProductServiceImpl implements WantProductService {
    @Autowired
    private WantProductMapper wantProductMapper;
    @Override
    public List<SecondProductWant> selectProductPraise(Integer userId, String type, Integer productId) {
        return wantProductMapper.selectProductPraise(userId,type,productId);
    }

    @Override
    public List<SecondProductWant> selectProductPraise1(Integer userId, String type, Integer productId) {
        return wantProductMapper.selectProductPraise1(userId,type,productId);
    }
}
