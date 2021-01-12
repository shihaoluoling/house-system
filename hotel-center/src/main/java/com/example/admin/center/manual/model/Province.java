package com.example.admin.center.manual.model;

import lombok.Data;

import java.util.List;

/**
 * @author shihao
 * @Title: Province
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Data
public class Province {
    private String code;
    private String name;
    private List<City> cityList;

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

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
