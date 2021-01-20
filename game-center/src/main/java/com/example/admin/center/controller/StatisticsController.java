package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameCenterMapper;
import com.example.admin.center.dao.GameOrdersMapper;
import com.example.admin.center.dao.GameUserMapper;
import com.example.admin.center.manual.Enum.OrderType;
import com.example.admin.center.manual.Enum.UserType;
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

import javax.servlet.http.HttpServletResponse;
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
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @Autowired
    private GameOrdersMapper gameOrdersMapper;
    @Autowired
    private GameCenterMapper gameCenterMapper;
    @Autowired
    private GameUserMapper gameUserMapper;
    @ApiOperation(value = "统计", notes = "统计")
    @RequestMapping(value = "/selectStatistics", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectStatistics(
            Date stateTime,Date endTime,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        SelectStatistics selectStatistics = new SelectStatistics();
        GameOrdersExample gameOrdersExample = new GameOrdersExample();
        gameOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        //账号总数
        long accountNum = gameOrdersMapper.countByExample(gameOrdersExample);
        selectStatistics.setAccountNum(accountNum);
        GameUserExample gameUserExample = new GameUserExample();
        gameUserExample.createCriteria()
                .andUserTypeEqualTo(UserType.WECHART.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        //用户总数
        long userNum = gameUserMapper.countByExample(gameUserExample);
        selectStatistics.setUserNum(userNum);
        GameCenterExample gameCenterExample = new GameCenterExample();
        gameCenterExample.createCriteria()
                .andIsDeletedEqualTo((short) 0);
        //游戏总数
        long gameNum = gameCenterMapper.countByExample(gameCenterExample);
        selectStatistics.setGameNum(gameNum);
//待处理
        gameOrdersExample.clear();
        GameOrdersExample.Criteria criteria1 = gameOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andOrderStatusEqualTo(OrderType.PENDING.getOrderType());
        if (stateTime != null && endTime!= null){
            ZoneId zoneId = ZoneId.systemDefault();
            criteria1.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.ofInstant(stateTime.toInstant(), zoneId))
                    .andCreateTimeLessThanOrEqualTo(LocalDateTime.ofInstant(endTime.toInstant(), zoneId));
        }
        //
        long pendingNum = gameOrdersMapper.countByExample(gameOrdersExample);
        selectStatistics.setPendingNum(pendingNum);
        //跟进中
        gameOrdersExample.clear();
        GameOrdersExample.Criteria criteria2 = gameOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andOrderStatusEqualTo(OrderType.PROCESSED.getOrderType());
        if (stateTime != null && endTime!= null){
            ZoneId zoneId = ZoneId.systemDefault();
            criteria2.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.ofInstant(stateTime.toInstant(), zoneId))
                    .andCreateTimeLessThanOrEqualTo(LocalDateTime.ofInstant(endTime.toInstant(), zoneId));
        }
        //
        long processedNum = gameOrdersMapper.countByExample(gameOrdersExample);
        selectStatistics.setProcessedNum(processedNum);
        //完成
        gameOrdersExample.clear();
        GameOrdersExample.Criteria criteria3 = gameOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andOrderStatusEqualTo(OrderType.COMPLETE.getOrderType());
        if (stateTime != null && endTime!= null){
            ZoneId zoneId = ZoneId.systemDefault();
            criteria3.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.ofInstant(stateTime.toInstant(), zoneId))
                    .andCreateTimeLessThanOrEqualTo(LocalDateTime.ofInstant(endTime.toInstant(), zoneId));
        }
        long completeNum = gameOrdersMapper.countByExample(gameOrdersExample);
        selectStatistics.setCompleteNum(completeNum);
        return builder.body(ResponseUtils.getResponseBody(selectStatistics));
    }
}
