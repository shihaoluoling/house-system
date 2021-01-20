package com.example.admin.center.manual.model;

/**
 * @author shihao
 * @Title: FormItem
 * @ProjectName Second-order-center
 * @Description: 表单拥有的表单项
 * @date Created in
 * @Version: $
 */
public class FormItem {
    /**
     * //表单项id
     * */
    private Integer id;
    /**
     *表单项名称
     * */
    private String name;
    /**
     *表单项类型
     * */
    private String type;
    /**
     *表单项备注
     * */
    private String formRemark;
    /**
     * //表单项排序
     * */
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormRemark() {
        return formRemark;
    }

    public void setFormRemark(String formRemark) {
        this.formRemark = formRemark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
