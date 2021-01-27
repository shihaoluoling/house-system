package com.example.user.center.manual.model;

/**
 * @author shihao
 * @Title: HomeType
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class HomeType {
    private Integer TypeId;
    private Integer constituteId;
    private double value;
    private String typeName;

    public Integer getTypeId() {
        return TypeId;
    }

    public void setTypeId(Integer typeId) {
        TypeId = typeId;
    }

    public Integer getConstituteId() {
        return constituteId;
    }

    public void setConstituteId(Integer constituteId) {
        this.constituteId = constituteId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
