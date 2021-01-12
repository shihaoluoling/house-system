package com.example.admin.center.manual.model;

import lombok.Data;

/**
 * @author shihao
 * @Title: Area
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
    @Data
    public class Area {
        private String code;
        private String name;

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
}

