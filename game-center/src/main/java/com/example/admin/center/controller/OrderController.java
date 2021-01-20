package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameOrdersMapper;
import com.example.admin.center.dao.GameUserMapper;
import com.example.admin.center.manual.Enum.OrderType;
import com.example.admin.center.manual.JSON.SelectOrders;
import com.example.admin.center.model.GameOrders;
import com.example.admin.center.model.GameOrdersExample;
import com.example.admin.center.model.GameSlideshow;
import com.example.admin.center.model.GameUser;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shihao
 * @Title: OrderController
 * @ProjectName Second-order-center
 * @Description: 订单
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Order")
@CrossOrigin
public class OrderController {
    /**
     * 生成订单号
     */
    private static final FastDateFormat PATTERN = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(1);
    private static ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<>();
    private static String getC(String lock) {
        //回收清理
        threadLocal.remove();
        lock = Objects.isNull(lock) ? UUID.randomUUID().toString() : lock;
        // 取系统当前时间作为订单号前半部分
        StringBuilder builder = new StringBuilder(PATTERN.format(Instant.now().toEpochMilli()));
        // HASH-CODE
        builder.append(Math.abs(lock.hashCode()));
        // 自增顺序
        builder.append(ATOMIC_INTEGER.getAndIncrement());
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }
    @Autowired
    private GameOrdersMapper gameOrdersMapper;
    @Autowired
    private GameUserMapper gameUserMapper;
    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "orderDetail", value = "订单详情", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addOrder(Integer userId
            , String orderDetail,
                                                   HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameOrders gameOrders = new GameOrders();
        gameOrders.setOrderCode(getC(UUID.randomUUID().toString()));
        gameOrders.setOrderDetail(orderDetail);
        GameUser gameUser = gameUserMapper.selectByPrimaryKey(userId);
        String json = JSONObject.toJSONString(gameUser);
        gameOrders.setUserinfo(json);
        gameOrders.setOrderStatus(OrderType.PENDING.getOrderType());
        gameOrders.setUserId(userId);
        gameOrders.setCreateTime(LocalDateTime.now());
        gameOrders.setModifyTime(LocalDateTime.now());
        gameOrders.setIsDeleted((byte) 0);
        gameOrdersMapper.insertSelective(gameOrders);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderCode", value = "订单编号", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "originalStatus", value = "原始订单状态", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "changeStatus", value = "改变到的订单状态", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updateOrder(String orderCode
            , String originalStatus
            , String changeStatus
            , HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameOrdersExample gameOrdersExample = new GameOrdersExample();
        gameOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andOrderCodeEqualTo(orderCode)
                .andOrderStatusEqualTo(originalStatus);
        List<GameOrders> gameOrders = gameOrdersMapper.selectByExampleWithBLOBs(gameOrdersExample);
        if (gameOrders.size()!=0){
            GameOrders gameOrders1 = gameOrders.get(0);
            gameOrders1.setOrderStatus(changeStatus);
            gameOrders1.setModifyTime(LocalDateTime.now());
            gameOrdersMapper.updateByPrimaryKeySelective(gameOrders1);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "订单查询", notes = "订单查询")
    @RequestMapping(value = "/selectOrder", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectOrder(
            Integer userId,
            Integer start,
                                                  Integer num,
                                                  String condition
            , HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameOrdersExample gameOrdersExample = new GameOrdersExample();
        GameOrdersExample.Criteria criteria = gameOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        if (condition != null){
            criteria.andUserinfoLike("%"+ condition +"%");
        }
        if (userId !=null){
            criteria.andUserIdEqualTo(userId);
        }
        //总数
        long nums = gameOrdersMapper.countByExample(gameOrdersExample);
        SelectOrders selectOrders = new SelectOrders();
        selectOrders.setNums(nums);
        if (start!=null && num!=null){
            //mysql 从0开始算数据,前端从1开始
            start -= 1;
            //转化成分页从第start开始,num条
            start = start*10;
            gameOrdersExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        List<GameOrders> gameOrders = gameOrdersMapper.selectByExampleWithBLOBs(gameOrdersExample);
        selectOrders.setGameOrders(gameOrders);
        return builder.body(ResponseUtils.getResponseBody(selectOrders));
    }

    @ApiOperation(value = "删除订单", notes = "删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> deleteOrder(Integer orderId,
                                               HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameOrders gameOrders = new GameOrders();
        gameOrders.setIsDeleted((byte) 1);
        gameOrders.setModifyTime(LocalDateTime.now());
        gameOrders.setId(orderId);
        gameOrdersMapper.updateByPrimaryKeySelective(gameOrders);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
