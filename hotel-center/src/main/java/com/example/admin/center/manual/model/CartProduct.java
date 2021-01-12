package com.example.admin.center.manual.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: CartProduct
 * @ProjectName Second-order-center
 * @Description: 购物车商品查询
 * @date Created in
 * @Version: $
 */
public class CartProduct {
    /**
     * id 购物车id
     * */
    private Integer id;
    /**
     * cartCode 购物车编号
     * */
    private String cartCode;
    /**
     * productId 商品id
     * */
    private Integer productId;
    /**
     * quantity 购物车数量
     * */
    private Integer quantity;
    /**
     * userId 用户id
     * */
    private Integer userId;
    /**
     * modifyDate 购物车创建时间
     * */
    private LocalDateTime modifyDate;
    /**
     * productName 商品名称
     * */
    private String productName;
    /**
     * subscribeQuantity 一次最多添加数量
     * */
    private Integer subscribeQuantity;
    /**
     * productQuantity 商品数量
     * */
    private Integer productQuantity;
    /**
     * price 商品价格
     * */
    private BigDecimal price;
    /**
     * productDesc 商品描述
     * */
    private String productDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCartCode() {
        return cartCode;
    }

    public void setCartCode(String cartCode) {
        this.cartCode = cartCode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSubscribeQuantity() {
        return subscribeQuantity;
    }

    public void setSubscribeQuantity(Integer subscribeQuantity) {
        this.subscribeQuantity = subscribeQuantity;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
