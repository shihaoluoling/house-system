package com.example.user.center.manual.model;

/**
 * @author shihao
 * @Title: Tower
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class Tower {
    /**
     * 楼号id
     */
    private Integer towerId;
    /**
     * 楼号
     */
    private String towerNo;
    /**
     * 楼号楼盘id
     */
    private Integer premisesId;
    /**
     * 同步数据的楼号id
     */
    private Integer synchronizationNo;

    public Integer getTowerId() {
        return towerId;
    }

    public void setTowerId(Integer towerId) {
        this.towerId = towerId;
    }

    public String getTowerNo() {
        return towerNo;
    }

    public void setTowerNo(String towerNo) {
        this.towerNo = towerNo;
    }

    public Integer getPremisesId() {
        return premisesId;
    }

    public void setPremisesId(Integer premisesId) {
        this.premisesId = premisesId;
    }

    public Integer getSynchronizationNo() {
        return synchronizationNo;
    }

    public void setSynchronizationNo(Integer synchronizationNo) {
        this.synchronizationNo = synchronizationNo;
    }
}
