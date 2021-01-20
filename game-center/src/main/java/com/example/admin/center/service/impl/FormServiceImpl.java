package com.example.admin.center.service.impl;

import com.example.admin.center.manual.dao.FormMapper;
import com.example.admin.center.manual.model.FormItem;
import com.example.admin.center.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shihao
 * @Title: FormServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private FormMapper formMapper;

    @Override
    public List<FormItem> selectFormItem(Integer formId) {
        return formMapper.selectFormItem(formId);
    }
}
