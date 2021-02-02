package com.example.user.center.manual;

import java.util.List;

/**
 * @author shihao
 * @Title: SelectLibraryCategory
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class SelectLibraryCategory {
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 类目下是否添加了文本0是1否
     */
    private Byte isAddText;
    /**
     * 是否是最后一个类目 0是1否
     */
    private Byte isLast;
    /**
     * 下级分类
     */
    private List<SelectLibraryCategory> selectLibraryCategories;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Byte getIsAddText() {
        return isAddText;
    }

    public void setIsAddText(Byte isAddText) {
        this.isAddText = isAddText;
    }

    public Byte getIsLast() {
        return isLast;
    }

    public void setIsLast(Byte isLast) {
        this.isLast = isLast;
    }

    public List<SelectLibraryCategory> getSelectLibraryCategories() {
        return selectLibraryCategories;
    }

    public void setSelectLibraryCategories(List<SelectLibraryCategory> selectLibraryCategories) {
        this.selectLibraryCategories = selectLibraryCategories;
    }
}
