package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.HotelOrdersMapper;
import com.example.admin.center.dao.HotelProductMapper;
import com.example.admin.center.dao.HotelUserMapper;
import com.example.admin.center.manual.Enum.OrderType;
import com.example.admin.center.manual.Enum.UserType;
import com.example.admin.center.manual.JSON.SelectProduct;
import com.example.admin.center.manual.JSON.SelectStatistics;
import com.example.admin.center.model.*;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author shihao
 * @Title: StatisticsController
 * @ProjectName Second-order-center
 * @Description: 统计
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Statistics")
@CrossOrigin
public class StatisticsController {
    @Autowired
    private HotelProductMapper hotelProductMapper;
    @Autowired
    private HotelUserMapper hotelUserMapper;
    @Autowired
    private HotelOrdersMapper hotelOrdersMapper;
    @ApiOperation(value = "统计", notes = "统计")
    @RequestMapping(value = "/selectStatistics", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectStatistics(
            Date stateTime,Date endTime
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        SelectStatistics selectStatistics = new SelectStatistics();
        HotelProductExample hotelProductExample = new HotelProductExample();
        hotelProductExample.createCriteria()
                .andIsDeletedEqualTo((short) 0);
        //商品数量
        long productNum =
                hotelProductMapper.countByExample(hotelProductExample);
        selectStatistics.setProductNum(productNum);
        //用户总数
        HotelUserExample hotelUserExample = new HotelUserExample();
        hotelUserExample.createCriteria()
                .andUserTypeEqualTo(UserType.WECHART.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        long userNum =
                hotelUserMapper.countByExample(hotelUserExample);
        selectStatistics.setUserNum(userNum);
        //预约总数
        HotelOrdersExample hotelOrdersExample = new HotelOrdersExample();
        hotelOrdersExample.createCriteria()
                .andIdNotEqualTo(0);
        long orderNum =
                hotelOrdersMapper.countByExample(hotelOrdersExample);
        selectStatistics.setOrderNum(orderNum);
        hotelOrdersExample.clear();
        //待处理
        HotelOrdersExample.Criteria criteria1 = hotelOrdersExample.createCriteria()
                .andOrderStatusEqualTo(OrderType.PENDING.getOrderType());
        if (stateTime != null && endTime!= null){
            ZoneId zoneId = ZoneId.systemDefault();
            criteria1.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.ofInstant(stateTime.toInstant(), zoneId))
                    .andCreateTimeLessThanOrEqualTo(LocalDateTime.ofInstant(endTime.toInstant(), zoneId));
        }
        long pendingNum = hotelOrdersMapper.countByExample(hotelOrdersExample);
        selectStatistics.setPendingNum(pendingNum);
        //已跟进
        hotelOrdersExample.clear();
        HotelOrdersExample.Criteria criteria2 = hotelOrdersExample.createCriteria()
                .andOrderStatusEqualTo(OrderType.PROCESSED.getOrderType());
        if (stateTime != null && endTime!= null){
            ZoneId zoneId = ZoneId.systemDefault();
            criteria2.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.ofInstant(stateTime.toInstant(), zoneId))
                    .andCreateTimeLessThanOrEqualTo(LocalDateTime.ofInstant(endTime.toInstant(), zoneId));
        }
        long processedNum = hotelOrdersMapper.countByExample(hotelOrdersExample);
        selectStatistics.setProcessedNum(processedNum);
        //完成
        hotelOrdersExample.clear();
        HotelOrdersExample.Criteria criteria3 = hotelOrdersExample.createCriteria()
                .andOrderStatusEqualTo(OrderType.COMPLETE.getOrderType());
        if (stateTime != null && endTime!= null){
            ZoneId zoneId = ZoneId.systemDefault();
            criteria3.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.ofInstant(stateTime.toInstant(), zoneId))
                    .andCreateTimeLessThanOrEqualTo(LocalDateTime.ofInstant(endTime.toInstant(), zoneId));
        }
        long completeNum = hotelOrdersMapper.countByExample(hotelOrdersExample);
        selectStatistics.setCompleteNum(completeNum);
        return builder.body(ResponseUtils.getResponseBody(selectStatistics));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
