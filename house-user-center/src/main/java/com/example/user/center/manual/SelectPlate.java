package com.example.user.center.manual;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author shihao
 * @Title: SelectPlate
 * @ProjectName Second-order-center
 * @Description: 板块查询
 * @date Created in
 * @Version: $
 */
public class SelectPlate {
    private Integer plateId;//板块id
    private String plateName;//板块名称
    private Integer administrativeId;//行政区域id
    private String administrative;//行政区域
    private String address;//板块地址
    private Map<Object,String> label;//板块标签
    private String advantage;//区域优势
    private Integer supply;//供应套数
    private Integer transaction;//成交套数
    private Double ratio;//供求比
    private List<String> houses;//已有楼盘
    private Double plotRatioMax;//容积率最大值
    private Double plotRatioMin;//容积率最小值
    private Double plotRatioMuch;//容积率最多值
    private BigDecimal averagePrice;//置业均价
    private BigDecimal housesPrice;//楼盘均价
    private String developMessage;//区域发展信息

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Integer getAdministrativeId() {
        return administrativeId;
    }

    public void setAdministrativeId(Integer administrativeId) {
        this.administrativeId = administrativeId;
    }

    public String getAdministrative() {
        return administrative;
    }

    public void setAdministrative(String administrative) {
        this.administrative = administrative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Object, String> getLabel() {
        return label;
    }

    public void setLabel(Map<Object, String> label) {
        this.label = label;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public Integer getSupply() {
        return supply;
    }

    public void setSupply(Integer supply) {
        this.supply = supply;
    }

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public List<String> getHouses() {
        return houses;
    }

    public void setHouses(List<String> houses) {
        this.houses = houses;
    }

    public Double getPlotRatioMax() {
        return plotRatioMax;
    }

    public void setPlotRatioMax(Double plotRatioMax) {
        this.plotRatioMax = plotRatioMax;
    }

    public Double getPlotRatioMin() {
        return plotRatioMin;
    }

    public void setPlotRatioMin(Double plotRatioMin) {
        this.plotRatioMin = plotRatioMin;
    }

    public Double getPlotRatioMuch() {
        return plotRatioMuch;
    }

    public void setPlotRatioMuch(Double plotRatioMuch) {
        this.plotRatioMuch = plotRatioMuch;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getHousesPrice() {
        return housesPrice;
    }

    public void setHousesPrice(BigDecimal housesPrice) {
        this.housesPrice = housesPrice;
    }

    public String getDevelopMessage() {
        return developMessage;
    }

    public void setDevelopMessage(String developMessage) {
        this.developMessage = developMessage;
    }

}
