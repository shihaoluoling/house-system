package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameFormItemBeMapper;
import com.example.admin.center.dao.GameFormItemMapper;
import com.example.admin.center.dao.GameFormMapper;
import com.example.admin.center.manual.Enum.AuthLogin;
import com.example.admin.center.manual.Enum.Login;
import com.example.admin.center.manual.JSON.SelectForm;
import com.example.admin.center.manual.model.FormItem;
import com.example.admin.center.model.*;
import com.example.admin.center.service.FormService;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: FormController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Form")
@CrossOrigin
public class FormController {
    @Autowired
    private GameFormMapper gameFormMapper;

    @Autowired
    private GameFormItemMapper gameFormItemMapper;

    @Autowired
    private GameFormItemBeMapper gameFormItemBeMapper;

    @Autowired
    private FormService formService;

    @ApiOperation(value = "添加表单项", notes = "添加表单项")
    @RequestMapping(value = "/addFormItem", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "表单项名称", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remake", value = "内容备注", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addFormItem(String name
            , String remake
            , String type,
                                                  HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameFormItem gameFormItem = new GameFormItem();
        gameFormItem.setName(name);
        gameFormItem.setFormRemark(remake);
        gameFormItem.setType(type);
        gameFormItem.setCreateTime(LocalDateTime.now());
        gameFormItem.setModifyTime(LocalDateTime.now());
        gameFormItem.setIsDeleted((short) 0);
        gameFormItemMapper.insertSelective(gameFormItem);
        return builder.body(ResponseUtils.getResponseBody(gameFormItem.getId()));
    }

    @ApiOperation(value = "修改表单项", notes = "修改表单项")
    @RequestMapping(value = "/updateFormItem", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "表单项名称", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "remake", value = "内容备注", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateFormItem(String name,
            Integer id
            , String remake
            , String type,
                                                  HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameFormItem gameFormItem = new GameFormItem();
        gameFormItem.setId(id);
        gameFormItem.setName(name);
        gameFormItem.setFormRemark(remake);
        gameFormItem.setType(type);
        gameFormItem.setModifyTime(LocalDateTime.now());
        gameFormItemMapper.updateByPrimaryKeySelective(gameFormItem);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除表单项", notes = "删除表单项")
    @RequestMapping(value = "/deleteFormItem", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteFormItem(
                                                     Integer id,
                                                     Integer formId,
                                                     HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        gameFormItemMapper.deleteByPrimaryKey(id);
        if (formId!=null){
            GameFormItemBeExample gameFormItemBeExample = new GameFormItemBeExample();
            gameFormItemBeExample.createCriteria()
                    .andFormIdEqualTo(formId)
                    .andFormItemIdEqualTo(id)
                    .andIsDeletedEqualTo((short) 0);

            gameFormItemBeMapper.deleteByExample(gameFormItemBeExample);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询表单项", notes = "查询表单项")
    @RequestMapping(value = "/selectItem", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectItem(
            Integer formItemId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameFormItem gameFormItem = gameFormItemMapper.selectByPrimaryKey(formItemId);
        return builder.body(ResponseUtils.getResponseBody(gameFormItem));
    }
    @ApiOperation(value = "添加表单", notes = "添加表单")
    @RequestMapping(value = "/addForm", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "表单名称", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addForm(
            String name,
            Integer[] formItems,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //新增表单
        GameForm gameForm = new GameForm();
        gameForm.setName(name);
        gameForm.setCreateTime(LocalDateTime.now());
        gameForm.setModifyTime(LocalDateTime.now());
        gameForm.setIsDeleted((short) 0);
        gameFormMapper.insertSelective(gameForm);
        //给表单加表单项
        if (formItems.length != 0){
            for (int i = 0; i<formItems.length; i++){
                GameFormItemBe gameFormItemBe = new GameFormItemBe();
                gameFormItemBe.setFormId(gameForm.getId());
                gameFormItemBe.setFormItemId(formItems[i]);
                gameFormItemBe.setSort(i);
                gameFormItemBe.setCreateTime(LocalDateTime.now());
                gameFormItemBe.setModifyTime(LocalDateTime.now());
                gameFormItemBe.setIsDeleted((short) 0);
                gameFormItemBeMapper.insertSelective(gameFormItemBe);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改表单", notes = "修改表单")
    @RequestMapping(value = "/updateForm", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "表单名称", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateForm(
            Integer formId,
            String name,
            Integer[] formItems,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //新增表单
        GameForm gameForm = new GameForm();
        gameForm.setId(formId);
        gameForm.setName(name);
        gameForm.setModifyTime(LocalDateTime.now());
        gameFormMapper.updateByPrimaryKeySelective(gameForm);
        //给表单加表单项
        if (formItems.length != 0){
            for (int i = 0; i<formItems.length; i++){
                GameFormItemBe gameFormItemBe = new GameFormItemBe();
                gameFormItemBe.setFormId(gameForm.getId());
                gameFormItemBe.setFormItemId(formItems[i]);
                gameFormItemBe.setSort(i);
                gameFormItemBe.setCreateTime(LocalDateTime.now());
                gameFormItemBe.setModifyTime(LocalDateTime.now());
                gameFormItemBe.setIsDeleted((short) 0);
                gameFormItemBeMapper.insertSelective(gameFormItemBe);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除表单", notes = "删除表单")
    @RequestMapping(value = "/deleteForm", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteForm(
            Integer formId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //新增表单
        GameForm gameForm = new GameForm();
        gameForm.setId(formId);
        gameForm.setIsDeleted((short) 1);
        gameFormMapper.updateByPrimaryKeySelective(gameForm);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "查询表单", notes = "查询表单")
    @RequestMapping(value = "/selectForm", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectForm(
            Integer start,
            Integer num,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //新增表单
        GameFormExample gameFormExample = new GameFormExample();
        gameFormExample.createCriteria()
                .andIsDeletedEqualTo((short) 0);
        //总数
        SelectForm selectForm = new SelectForm();
        long nums = gameFormMapper.countByExample(gameFormExample);
        selectForm.setNums(nums);
        if (start!=null && num!=null){
            //mysql 从0开始算数据,前端从1开始
            start -= 1;
            //转化成分页从第start开始,num条
            start = start*num;
            gameFormExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        List<GameForm> gameForms = gameFormMapper.selectByExample(gameFormExample);
        selectForm.setGameForms(gameForms);
        return builder.body(ResponseUtils.getResponseBody(selectForm));
    }

    @ApiOperation(value = "查询表单下的表单项", notes = "查询表单下的表单项")
    @RequestMapping(value = "/selectFormItem", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectFormItem(
            Integer formId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<FormItem> formItems = formService.selectFormItem(formId);
        return builder.body(ResponseUtils.getResponseBody(formItems));
    }
}
