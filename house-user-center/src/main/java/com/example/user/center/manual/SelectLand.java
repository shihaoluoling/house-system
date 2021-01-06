package com.example.user.center.manual;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: SelectLand
 * @ProjectName Second-order-center
 * @Description: 土地查询
 * @date Created in
 * @Version: $
 */
public class SelectLand {
    private Integer landId;//土地id
    private String landName;//土地名称
    private Integer projectId;//所属项目id
    private String projectName;//所属项目名称
    private String landAddress;//土地地址
    private String administrativeName;//所属区域名称
    private Integer administrativeId;//所属区域id
    private Integer plateId;//所属板块id
    private String plateName;//所属板块名称
    private Double siteArea;//用地面积
    private Double ratio;//容积率
    private Double premium;//溢价率
    private BigDecimal accommodation;//楼面价
    private List<String> premises;//楼盘
    private Double architectureArea;//建筑面积
    private Double density;//建筑密度
    private LocalDateTime succeedTime;//成交日期
    private BigDecimal transactionPrice;//成交价格
    private BigDecimal startingPrice;//起拍价格
    private String transfer;//受让方
    private String remark;//备注

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLandAddress() {
        return landAddress;
    }

    public void setLandAddress(String landAddress) {
        this.landAddress = landAddress;
    }

    public String getAdministrativeName() {
        return administrativeName;
    }

    public void setAdministrativeName(String administrativeName) {
        this.administrativeName = administrativeName;
    }

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

    public Double getSiteArea() {
        return siteArea;
    }

    public void setSiteArea(Double siteArea) {
        this.siteArea = siteArea;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Double getArchitectureArea() {
        return architectureArea;
    }

    public void setArchitectureArea(Double architectureArea) {
        this.architectureArea = architectureArea;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public LocalDateTime getSucceedTime() {
        return succeedTime;
    }

    public void setSucceedTime(LocalDateTime succeedTime) {
        this.succeedTime = succeedTime;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public BigDecimal getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(BigDecimal accommodation) {
        this.accommodation = accommodation;
    }

    public List<String> getPremises() {
        return premises;
    }

    public void setPremises(List<String> premises) {
        this.premises = premises;
    }

    public Integer getAdministrativeId() {
        return administrativeId;
    }

    public void setAdministrativeId(Integer administrativeId) {
        this.administrativeId = administrativeId;
    }
}
