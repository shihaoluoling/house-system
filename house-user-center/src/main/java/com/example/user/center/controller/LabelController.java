package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseLabelMapper;
import com.example.user.center.manual.LabelType;
import com.example.user.center.manual.Login;
import com.example.user.center.model.HouseFile;
import com.example.user.center.model.HouseLabel;
import com.example.user.center.model.HouseLabelExample;
import com.house.common.service.FileMangeService;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: LabelController
 * @ProjectName Second-order-center
 * @Description: 标签
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Label")
@CrossOrigin
public class LabelController {
    @Autowired
    private HouseLabelMapper houseLabelMapper;

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @RequestMapping(value = "/addLabel", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addLabel(String name,String type) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLabel houseLabel = new HouseLabel();
        houseLabel.setLabelName(name);
        houseLabel.setLabelType(LabelType.getPaymentTypeEnum(type).getPaymentTypeName());
        houseLabel.setLabelState(String.valueOf(0));
        houseLabel.setCreateDate(LocalDateTime.now());
        houseLabel.setIsDeleted((byte) 0);
        houseLabel.setModifyDate(LocalDateTime.now());
        houseLabelMapper.insertSelective(houseLabel);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改标签", notes = "修改标签")
    @RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateLabel(Integer id,String name,String type) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLabel houseLabel = new HouseLabel();
        houseLabel.setId(id);
        houseLabel.setLabelName(name);
        houseLabel.setLabelType(Login.getPaymentTypeEnum(type).getPaymentTypeName());
        houseLabel.setLabelState(String.valueOf(0));
        houseLabel.setModifyDate(LocalDateTime.now());
        houseLabelMapper.updateByPrimaryKeySelective(houseLabel);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "删除标签", notes = "删除标签")
    @RequestMapping(value = "/deleteLabel", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> deleteLabel(Integer id) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLabel houseLabel = new HouseLabel();
        houseLabel.setId(id);
        houseLabel.setIsDeleted((byte) 1);
        houseLabelMapper.updateByPrimaryKeySelective(houseLabel);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询标签", notes = "查询标签")
    @RequestMapping(value = "/selectLabel", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> selectLabel(String type) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLabelExample houseLabelExample = new HouseLabelExample();
        houseLabelExample.createCriteria().andLabelTypeEqualTo(type)
                .andIsDeletedEqualTo((byte) 0);
        return builder.body(ResponseUtils.getResponseBody(houseLabelMapper.selectByExample(houseLabelExample)));
    }
}
