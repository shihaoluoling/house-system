package com.example.admin.center.manual.model;

import lombok.Data;

import java.util.List;

/**
 * @author shihao
 * @Title: City
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Data
public class City {
    private String code;
    private String name;
    private List<Area> areaList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
