package com.example.admin.center.manual.dao;

import com.example.admin.center.manual.model.FormItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
/**
 * 表单项查询
 * */
public interface FormMapper {
    /**
     *查询表单拥有的表单项
     * formId 表单id
     * */
    List<FormItem> selectFormItem(@Param("formId")Integer formId);
}
