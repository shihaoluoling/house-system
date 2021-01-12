package com.example.admin.center.manual.Enum;

public enum OrderType {
    //待处理
    PENDING("pending"),
    //已跟进
    PROCESSED("processed"),
    //完成
    COMPLETE("complete");

    private String orderType;
    OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return this.orderType;
    }
}
