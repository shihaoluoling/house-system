package com.example.admin.center.manual.model;

import com.example.admin.center.model.HotelOrdersDetail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: Order
 * @ProjectName Second-order-center
 * @Description: 小程序订单
 * @date Created in
 * @Version: $
 */
public class Order {
    private Integer orderId;//订单id
    private String orderCode;//订单编号
    private String orderStatus;//订单状态
    private BigDecimal sumMoney;//总价
    private LocalDateTime modifTime;//修改时间
    private List<HotelOrdersDetail> hotelOrdersDetails;//订单详情

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public LocalDateTime getModifTime() {
        return modifTime;
    }

    public void setModifTime(LocalDateTime modifTime) {
        this.modifTime = modifTime;
    }

    public List<HotelOrdersDetail> getHotelOrdersDetails() {
        return hotelOrdersDetails;
    }

    public void setHotelOrdersDetails(List<HotelOrdersDetail> hotelOrdersDetails) {
        this.hotelOrdersDetails = hotelOrdersDetails;
    }
}
