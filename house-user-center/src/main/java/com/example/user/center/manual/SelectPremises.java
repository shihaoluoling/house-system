package com.example.user.center.manual;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author shihao
 * @Title: SelectPremises
 * @ProjectName Second-order-center
 * @Description: 楼盘查询
 * @date Created in
 * @Version: $
 */
public class SelectPremises {
    private Integer premisesId;//楼盘id
    private String premisesName;//楼盘名称
    private String administrativeName;//所属区域名称
    private String plateName;//所属板块名称
    private String projectName;//所属项目名称
    private List<String> sumFiles;//楼盘总图
    private List<String> facadeFiles;//楼房立面图
    private Map<Object,String> labels;//标签
    private BigDecimal price;//楼盘价格
    private String landName;//所属土地名称
    private Integer landId;//所属土地id
    private Double siteArea;//用地面积
    private Double ratio;//容积率
    private Double architectureArea;//建筑面积
    private LocalDateTime openingTime;//开盘时间
    private String developersName;//开发商
    private Integer households;//总户数
    private String propertyName;//物业名称

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

    public String getAdministrativeName() {
        return administrativeName;
    }

    public void setAdministrativeName(String administrativeName) {
        this.administrativeName = administrativeName;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getSumFiles() {
        return sumFiles;
    }

    public void setSumFiles(List<String> sumFiles) {
        this.sumFiles = sumFiles;
    }

    public List<String> getFacadeFiles() {
        return facadeFiles;
    }

    public void setFacadeFiles(List<String> facadeFiles) {
        this.facadeFiles = facadeFiles;
    }

    public Map<Object, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<Object, String> labels) {
        this.labels = labels;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
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

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public String getDevelopersName() {
        return developersName;
    }

    public void setDevelopersName(String developersName) {
        this.developersName = developersName;
    }

    public Integer getHouseholds() {
        return households;
    }

    public void setHouseholds(Integer households) {
        this.households = households;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
