package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseLandMapper;
import com.example.user.center.model.HouseLand;
import com.example.user.center.model.HouseLandExample;
import com.example.user.center.model.HouseProject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author shihao
 * @Title: LandController
 * @ProjectName Second-order-center
 * @Description: 土地管理
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Land")
@CrossOrigin
public class LandController {
    //土地
    @Autowired
    private HouseLandMapper houseLandMapper;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @ApiOperation(value = "添加土地", notes = "添加土地")
    @RequestMapping(value = "/addLand", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landName", value = "土地名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "landAddress", value = "土地地址", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "administrativeName", value = "行政区域名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "plateId", value = "板块id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "density", value = "建筑密度", required = true, type = "Double"),
//            @ApiImplicitParam(paramType = "query", name = "succeedTime", value = "成交日期", required = true, type = "LocalDateTime"),
            @ApiImplicitParam(paramType = "query", name = "transactionPrice", value = "成交价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "startingPrice", value = "起拍价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "transfer", value = "受让方名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addLand(
            @RequestParam(name = "landName") String landName,
            @RequestParam(name = "projectId") Integer projectId,
            @RequestParam(name = "landAddress") String landAddress,
            @RequestParam(name = "administrativeName") String administrativeName,
            @RequestParam(name = "plateId") Integer plateId,
            @RequestParam(name = "density") Double density,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date succeedTime,
            @RequestParam(name = "transactionPrice") BigDecimal transactionPrice,
            @RequestParam(name = "startingPrice") BigDecimal startingPrice,
            @RequestParam(name = "transfer") String transfer,
            @RequestParam(name = "remark") String remark,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLand houseLand = new HouseLand();
        houseLand.setLandName(landName);
        houseLand.setProjectId(projectId);
        houseLand.setLandAddress(landAddress);
        houseLand.setAdministrativeName(administrativeName);
        houseLand.setPlateId(plateId);
        houseLand.setDensity(density);
        ZoneId zoneId = ZoneId.systemDefault();
        houseLand.setSucceedTime(LocalDateTime.ofInstant(succeedTime.toInstant(), zoneId));
        houseLand.setTransactionPrice(transactionPrice);
        houseLand.setStartingPrice(startingPrice);
        houseLand.setTransfer(transfer);
        houseLand.setRemark(remark);
        houseLand.setCreateDate(LocalDateTime.now());
        houseLand.setModifyDate(LocalDateTime.now());
        houseLand.setIsDeleted((byte) 0);
        houseLandMapper.insertSelective(houseLand);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改土地", notes = "修改土地")
    @RequestMapping(value = "/updateLand", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landName", value = "土地名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "landId", value = "土地id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "landAddress", value = "土地地址", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "administrativeName", value = "行政区域名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "plateId", value = "板块id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "density", value = "建筑密度", required = true, type = "Double"),
//            @ApiImplicitParam(paramType = "query", name = "succeedTime", value = "成交日期", required = true, type = "LocalDateTime"),
            @ApiImplicitParam(paramType = "query", name = "transactionPrice", value = "成交价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "startingPrice", value = "起拍价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "transfer", value = "受让方名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "备注", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updateLand(
            @RequestParam(name = "landName") String landName,
            @RequestParam(name = "projectId") Integer projectId,
            @RequestParam(name = "landId") Integer landId,
            @RequestParam(name = "landAddress") String landAddress,
            @RequestParam(name = "administrativeName") String administrativeName,
            @RequestParam(name = "plateId") Integer plateId,
            @RequestParam(name = "density") Double density,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date succeedTime,
            @RequestParam(name = "transactionPrice") BigDecimal transactionPrice,
            @RequestParam(name = "startingPrice") BigDecimal startingPrice,
            @RequestParam(name = "transfer") String transfer,
            @RequestParam(name = "remark") String remark,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLand houseLand = new HouseLand();
        houseLand.setId(landId);
        houseLand.setLandName(landName);
        houseLand.setProjectId(projectId);
        houseLand.setLandAddress(landAddress);
        houseLand.setAdministrativeName(administrativeName);
        houseLand.setPlateId(plateId);
        houseLand.setDensity(density);
        ZoneId zoneId = ZoneId.systemDefault();
        houseLand.setSucceedTime(LocalDateTime.ofInstant(succeedTime.toInstant(), zoneId));
        houseLand.setTransactionPrice(transactionPrice);
        houseLand.setStartingPrice(startingPrice);
        houseLand.setTransfer(transfer);
        houseLand.setRemark(remark);
        houseLand.setModifyDate(LocalDateTime.now());
        houseLandMapper.updateByPrimaryKeySelective(houseLand);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除土地", notes = "删除土地")
    @RequestMapping(value = "/deleteLand", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landId", value = "土地id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteLand(
            @RequestParam(name = "landId") Integer landId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLand houseLand = new HouseLand();
        houseLand.setId(landId);
        houseLand.setIsDeleted((byte) 1);
        houseLand.setModifyDate(LocalDateTime.now());
        houseLandMapper.updateByPrimaryKeySelective(houseLand);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
