package com.example.admin.center.manual.JSON;

/**
 * @author shihao
 * @Title: SelectStatistics
 * @ProjectName Second-order-center
 * @Description: 统计查询
 * @date Created in
 * @Version: $
 */
public class SelectStatistics {
    /**
     * 账号总数
     * */
    private long accountNum;
    /**
     * 用户总数
     * */
    private long userNum;
    /**
     * 游戏总数
     * */
    private long gameNum;
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

    public long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(long accountNum) {
        this.accountNum = accountNum;
    }

    public long getUserNum() {
        return userNum;
    }

    public void setUserNum(long userNum) {
        this.userNum = userNum;
    }

    public long getGameNum() {
        return gameNum;
    }

    public void setGameNum(long gameNum) {
        this.gameNum = gameNum;
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

    @Override
    public String toString() {
        return "SelectStatistics{" +
                "accountNum=" + accountNum +
                ", userNum=" + userNum +
                ", gameNum=" + gameNum +
                ", pendingNum=" + pendingNum +
                ", processedNum=" + processedNum +
                ", completeNum=" + completeNum +
                '}';
    }
}
