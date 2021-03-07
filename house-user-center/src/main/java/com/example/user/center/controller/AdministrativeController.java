package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseAdministrativeMapper;
import com.example.user.center.model.HouseAdministrative;
import com.example.user.center.model.HouseAdministrativeExample;
import com.example.user.center.model.HouseType;
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
 * @Title: AdministrativeController
 * @ProjectName Second-order-center
 * @Description: 区域
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Administrative")
@CrossOrigin
public class AdministrativeController {
    @Autowired
    private HouseAdministrativeMapper houseAdministrativeMapper;

    @ApiOperation(value = "添加区域", notes = "添加区域")
    @RequestMapping(value = "/addAdministrative", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addAdministrative(String name) throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseAdministrative houseAdministrative = new HouseAdministrative();
        houseAdministrative.setAdministrativeName(name);
        houseAdministrative.setCreateDate(LocalDateTime.now());
        houseAdministrative.setModifyDate(LocalDateTime.now());
        houseAdministrative.setIsDeleted((byte) 0);
        houseAdministrativeMapper.insertSelective(houseAdministrative);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "修改区域", notes = "修改区域")
    @RequestMapping(value = "/updateAdministrative", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> updateAdministrative(Integer id,String name) throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseAdministrative houseAdministrative = new HouseAdministrative();
        houseAdministrative.setId(id);
        houseAdministrative.setAdministrativeName(name);
        houseAdministrative.setModifyDate(LocalDateTime.now());
        houseAdministrativeMapper.updateByPrimaryKeySelective(houseAdministrative);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "删除区域", notes = "删除区域")
    @RequestMapping(value = "/deleteAdministrative", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteAdministrative(Integer id) throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseAdministrative houseAdministrative = new HouseAdministrative();
        houseAdministrative.setId(id);
        houseAdministrative.setIsDeleted((byte) 1);
        houseAdministrative.setModifyDate(LocalDateTime.now());
        houseAdministrativeMapper.updateByPrimaryKeySelective(houseAdministrative);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询区域", notes = "查询区域")
    @RequestMapping(value = "/selectAdministrative", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectAdministrative() throws Exception {
        //constituteId 户型组成id
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseAdministrativeExample houseTypeConstituteExample = new HouseAdministrativeExample();
        houseTypeConstituteExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        return builder.body(ResponseUtils.getResponseBody(houseAdministrativeMapper.selectByExample(houseTypeConstituteExample)));
    }
}
