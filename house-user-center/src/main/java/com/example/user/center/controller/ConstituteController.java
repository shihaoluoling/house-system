package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseTypeConstituteMapper;
import com.example.user.center.manual.LabelType;
import com.example.user.center.model.HouseLabel;
import com.example.user.center.model.HouseTypeConstitute;
import com.example.user.center.model.HouseTypeConstituteExample;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: ConstituteController
 * @ProjectName Second-order-center
 * @Description: 户型组成
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Constitute")
@CrossOrigin
public class ConstituteController {

    @Autowired
    private HouseTypeConstituteMapper houseTypeConstituteMapper;

    @ApiOperation(value = "添加户型组成", notes = "添加户型组成")
    @RequestMapping(value = "/addConstitute", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addConstitute(String constituteName) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTypeConstitute houseTypeConstitute = new HouseTypeConstitute();
        houseTypeConstitute.setConstituteName(constituteName);
        houseTypeConstitute.setState(String.valueOf(0));
        houseTypeConstitute.setCreateDate(LocalDateTime.now());
        houseTypeConstitute.setModifyDate(LocalDateTime.now());
        houseTypeConstitute.setIsDeleted((byte) 0);
        houseTypeConstituteMapper.insertSelective(houseTypeConstitute);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "修改户型组成", notes = "修改户型组成")
    @RequestMapping(value = "/updateConstitute", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> updateConstitute(Integer constituteId,String constituteName) throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTypeConstitute houseTypeConstitute = new HouseTypeConstitute();
        houseTypeConstitute.setId(constituteId);
        houseTypeConstitute.setConstituteName(constituteName);
        houseTypeConstitute.setModifyDate(LocalDateTime.now());
        houseTypeConstituteMapper.updateByPrimaryKeySelective(houseTypeConstitute);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "删除户型组成", notes = "删除户型组成")
    @RequestMapping(value = "/deleteConstitute", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteConstitute(Integer constituteId) throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        houseTypeConstituteMapper.deleteByPrimaryKey(constituteId);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "查询户型组成", notes = "查询户型组成")
    @RequestMapping(value = "/selectConstitute", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectConstitute() throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTypeConstituteExample houseTypeConstituteExample = new HouseTypeConstituteExample();
        houseTypeConstituteExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        return builder.body(ResponseUtils.getResponseBody(houseTypeConstituteMapper.selectByExample(houseTypeConstituteExample)));
    }
}
