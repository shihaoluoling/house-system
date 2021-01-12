package com.example.admin.center.manual.JSON;

/**
 * @author shihao
 * @Title: SelectStatistics
 * @ProjectName Second-order-center
 * @Description: 统计
 * @date Created in
 * @Version: $
 */
public class SelectStatistics {
    /**
     * 商品数量
     * */
    private long productNum;
    /**
     * 用户总数
     * */
    private long userNum;
    /**
     * 预约总数
     * */
    private long orderNum;
    /**
     * 待处理
     * */
    private long pendingNum;
    /**
     * 已跟进
     * */
    private long processedNum;
    /**
     * 完成
     * */
    private long completeNum;

    public long getProductNum() {
        return productNum;
    }

    public void setProductNum(long productNum) {
        this.productNum = productNum;
    }

    public long getUserNum() {
        return userNum;
    }

    public void setUserNum(long userNum) {
        this.userNum = userNum;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public long getPendingNum() {
        return pendingNum;
    }

    public void setPendingNum(long pendingNum) {
        this.pendingNum = pendingNum;
    }

    public long getProcessedNum() {
        return processedNum;
    }

    public void setProcessedNum(long processedNum) {
        this.processedNum = processedNum;
    }

    public long getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(long completeNum) {
        this.completeNum = completeNum;
    }
}
