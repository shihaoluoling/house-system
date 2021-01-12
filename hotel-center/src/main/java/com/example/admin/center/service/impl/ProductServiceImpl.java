package com.example.admin.center.service.impl;

import com.example.admin.center.manual.dao.ProductMapper;
import com.example.admin.center.manual.model.CartProduct;
import com.example.admin.center.model.HotelProduct;
import com.example.admin.center.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shihao
 * @Title: ProductServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<CartProduct> selectCart(String cartCode) {
        return productMapper.selectCart(cartCode);
    }

    @Override
    public List<HotelProduct> selectProduct(String cartCode) {
        return productMapper.selectProduct(cartCode);
    }
}
