package com.example.user.center.manual;

import java.util.List;
import java.util.Map;

/**
 * @author shihao
 * @Title: SelectType
 * @ProjectName Second-order-center
 * @Description: 查询户型信息
 * @date Created in
 * @Version: $
 */
public class SelectType {
    private Integer typeId;//户型id
    private String typeName;//户型名称
    private Integer premisesId;//楼盘id
    private String premisesName;//楼盘名称
    private String projectName;//所属项目名称
    private Integer projectId;//所属项目id
    private String administrativeName;//所属区域名称
    private Integer administrativeId;//所属区域id
    private String plateName;//所属板块名称
    private Integer plateId;//所属板块id
    private String landName;//所属土地名称
    private List<String> files;//图片
    private Double area;//户型面积
    private Integer supply;//供应套数
    private Integer transaction;//成交套数
    private Double southWide;//南向总面宽
    private Double livingWide;//起居室面宽
    private Double masterWide;//主卧面宽
    private Double guestWide;//客卧面宽
    private Map constitute;//房间组成
    private Double ratio;//供求比

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
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

    public Double getSouthWide() {
        return southWide;
    }

    public void setSouthWide(Double southWide) {
        this.southWide = southWide;
    }

    public Double getLivingWide() {
        return livingWide;
    }

    public void setLivingWide(Double livingWide) {
        this.livingWide = livingWide;
    }

    public Double getMasterWide() {
        return masterWide;
    }

    public void setMasterWide(Double masterWide) {
        this.masterWide = masterWide;
    }

    public Double getGuestWide() {
        return guestWide;
    }

    public void setGuestWide(Double guestWide) {
        this.guestWide = guestWide;
    }

    public Map getConstitute() {
        return constitute;
    }

    public void setConstitute(Map constitute) {
        this.constitute = constitute;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getAdministrativeId() {
        return administrativeId;
    }

    public void setAdministrativeId(Integer administrativeId) {
        this.administrativeId = administrativeId;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }
}
