package com.example.admin.center.service;

import com.example.admin.center.manual.model.FormItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shihao
 * @Title: FormService
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public interface FormService {
    /**
     *查询表单拥有的表单项
     * formId 表单id
     * */
    List<FormItem> selectFormItem(@Param("formId")Integer formId);
}
