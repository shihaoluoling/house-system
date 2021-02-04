package com.example.user.center.manual;

/**
 * @author shihao
 * @Title: SelectTower
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectTower {
    /**
     * 楼号id
     */
    private Integer towerId;
    /**
     * 楼房id
     */
    private Integer premisesId;
    /**
     * 楼房名称
     */
    private String premisesName;
    /**
     * 楼号
     */
    private String towerName;
    /**
     * 同步楼号id
     */
    private Integer synchronizationId;
    /**
     * 同步楼号名称
     */
    private String synchronizationName;

    public Integer getTowerId() {
        return towerId;
    }

    public void setTowerId(Integer towerId) {
        this.towerId = towerId;
    }

    public Integer getPremisesId() {
        return premisesId;
    }

    public void setPremisesId(Integer premisesId) {
        this.premisesId = premisesId;
    }

    public String getPremisesName() {
        return premisesName;
    }

    public void setPremisesName(String premisesName) {
        this.premisesName = premisesName;
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public Integer getSynchronizationId() {
        return synchronizationId;
    }

    public void setSynchronizationId(Integer synchronizationId) {
        this.synchronizationId = synchronizationId;
    }

    public String getSynchronizationName() {
        return synchronizationName;
    }

    public void setSynchronizationName(String synchronizationName) {
        this.synchronizationName = synchronizationName;
    }
}
