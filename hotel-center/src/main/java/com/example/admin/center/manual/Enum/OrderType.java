package com.example.admin.center.manual.Enum;

/**
 * @author MACHENIKE
 */

public enum OrderType {
    //待处理
    PENDING("pending"),
    //已跟进
    PROCESSED("processed"),
    //完成
    COMPLETE("complete"),
    //驳回
    REJECT("reject");

    private String orderType;
    OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return this.orderType;
    }
}
