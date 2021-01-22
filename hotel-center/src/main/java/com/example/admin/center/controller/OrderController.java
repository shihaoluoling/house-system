package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.*;
import com.example.admin.center.manual.Enum.OrderType;
import com.example.admin.center.manual.JSON.MiniSelectOrder;
import com.example.admin.center.manual.JSON.SelectOrders;
import com.example.admin.center.manual.JSON.SelectProduct;
import com.example.admin.center.manual.model.CartProduct;
import com.example.admin.center.manual.model.Order;
import com.example.admin.center.model.*;
import com.example.admin.center.service.ProductService;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
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
    @Autowired
    private HotelCartMapper hotelCartMapper;
@Autowired
private ProductService productService;
@Autowired
private HotelOrdersMapper hotelOrdersMapper;
@Autowired
private HotelOrdersDetailMapper hotelOrdersDetailMapper;
@Autowired
private HotelUserMapper hotelUserMapper;
@Autowired
private HotelProductMapper hotelProductMapper;
    /**
     * 生成购物车码
     * */
private String createCartCode() {
    return UUID.randomUUID().toString();
}
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
    /**
     *
     * 购物车库存校验
     */

    private List<Integer> check(Integer[] productsId){
        List<Integer> productIds = new ArrayList<>();
        for (Integer productId:productsId){
            HotelProduct hotelProduct = hotelProductMapper.selectByPrimaryKey(productId);
            if ((hotelProduct.getQuantity()-1)<0){
                productIds.add(productId);
            }
        }
        return productIds;
    }
    @ApiOperation(value = "首次加入购物车", notes = "首次加入购物车")
    @RequestMapping(value = "/createCart", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> createCart(Integer[] productsId,Integer userId,HttpServletResponse response) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //库存校验
        List<Integer> p = check(productsId);
        if (p.size() != 0){
            return builder.body(ResponseUtils.getResponseBody(p));
        }

        String code = "";
        if (productsId.length != 0){
            code = createCartCode();
            for (Integer productId:productsId){
                HotelCart hotelCart = new HotelCart();
                hotelCart.setCartCode(code);
                hotelCart.setProductId(productId);
                hotelCart.setQuantity(1);
                hotelCart.setUserId(userId);
                hotelCart.setCreateDate(LocalDateTime.now());
                hotelCart.setModifyDate(LocalDateTime.now());
                hotelCart.setIsDeleted((byte) 0);
                hotelCartMapper.insertSelective(hotelCart);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(code));
    }
    @ApiOperation(value = "二次加入购物车", notes = "二次加入购物车o")
    @RequestMapping(value = "/secondCreateCart", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> secondCreateCart(Integer[] productsId,String cartCode,Integer userId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //库存校验
        List<Integer> p = check(productsId);
        if (p.size() != 0){
            return builder.body(ResponseUtils.getResponseBody(p));
        }
        if (productsId.length != 0){
            for (Integer productId:productsId){
                HotelCartExample hotelCartExample = new HotelCartExample();
                hotelCartExample.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andProductIdEqualTo(productId)
                        .andCartCodeEqualTo(cartCode);
                List<HotelCart> hotelCarts = hotelCartMapper.selectByExample(hotelCartExample);
                //如果购物车有此商品 购物车商品＋1
                if (hotelCarts.size()!=0){
                    HotelCart hotelCart = hotelCarts.get(0);
                    hotelCart.setQuantity(hotelCart.getQuantity()+1);
                    hotelCart.setModifyDate(LocalDateTime.now());
                    hotelCartMapper.updateByPrimaryKeySelective(hotelCart);
                } else {
                    HotelCart hotelCart = new HotelCart();
                    hotelCart.setCartCode(cartCode);
                    hotelCart.setProductId(productId);
                    hotelCart.setQuantity(1);
                    hotelCart.setUserId(userId);
                    hotelCart.setCreateDate(LocalDateTime.now());
                    hotelCart.setModifyDate(LocalDateTime.now());
                    hotelCart.setIsDeleted((byte) 0);
                    hotelCartMapper.insertSelective(hotelCart);
                }
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "修改购物车", notes = "修改购物车")
    @RequestMapping(value = "/updateCart", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> updateCart(Integer productsId,
                                                 String cartCode,
                                                 Integer quantity,
                                                 HttpServletResponse response) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelCartExample hotelCartExample = new HotelCartExample();
        hotelCartExample.createCriteria()
                .andCartCodeEqualTo(cartCode)
                .andProductIdEqualTo(productsId)
                .andIsDeletedEqualTo((byte) 0);
        List<HotelCart> hotelCarts =
        hotelCartMapper.selectByExample(hotelCartExample);
        if (hotelCarts.size() != 0){
            HotelCart hotelCart = new HotelCart();
            hotelCart.setId(hotelCarts.get(0).getId());
            hotelCart.setQuantity(quantity);
            hotelCart.setModifyDate(LocalDateTime.now());
            hotelCartMapper.updateByPrimaryKeySelective(hotelCart);
        } else {
            response.sendError(1000,"商品不存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除购物车商品", notes = "删除购物车")
    @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteCart(Integer productsId,
                                                 String cartCode,
                                                 HttpServletResponse response) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelCartExample hotelCartExample = new HotelCartExample();
        hotelCartExample.createCriteria()
                .andCartCodeEqualTo(cartCode)
                .andProductIdEqualTo(productsId)
                .andIsDeletedEqualTo((byte) 0);
        List<HotelCart> hotelCarts =
                hotelCartMapper.selectByExample(hotelCartExample);
        if (hotelCarts.size() != 0){
            HotelCart hotelCart = new HotelCart();
            hotelCart.setId(hotelCarts.get(0).getId());
            hotelCart.setIsDeleted((byte) 1);
            hotelCart.setModifyDate(LocalDateTime.now());
            hotelCartMapper.updateByPrimaryKeySelective(hotelCart);
        } else {
            response.sendError(1000,"商品不存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "查询购物车商品", notes = "查询购物车商品")
    @RequestMapping(value = "/selectCart", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectCart(
                                                 String cartCode) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(productService.selectCart(cartCode)));
    }

    @ApiOperation(value = "小程序查询可预定商品", notes = "小程序查询可预定商品")
    @RequestMapping(value = "/selectProduct", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectProduct(
            String cartCode) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (cartCode != null){
            SelectProduct selectProducts = new SelectProduct();

            List<HotelProduct> hotelProducts = productService.selectProduct(cartCode);
            selectProducts.setNums((long) hotelProducts.size());
            selectProducts.setHotelProducts(hotelProducts);
            return builder.body(ResponseUtils.getResponseBody(selectProducts));
        }
        return builder.body(ResponseUtils.getResponseBody(0));

    }

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cartCode", value = "购物车编号", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> createOrder(String cartCode,HttpServletResponse response) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        //计算总额
        List<CartProduct> cartProducts = new ArrayList<>();
        //用户信息
        HotelUser hotelUser = new HotelUser();
        synchronized (this){
            cartProducts = productService.selectCart(cartCode);
            if (cartProducts.size()==0){
                response.sendError(1002,"购物车为空");
                return builder.body(ResponseUtils.getResponseBody(1));
            }
            hotelUser = hotelUserMapper.selectByPrimaryKey(cartProducts.get(0).getUserId());
            //判断用户提交的上一个订单是否已经处理,未处理不能提交
            HotelOrdersExample hotelOrdersExample = new HotelOrdersExample();
            System.out.println(hotelUser.getId());
            hotelOrdersExample.createCriteria().andUserIdEqualTo(hotelUser.getId())
            .andIsDeletedEqualTo((byte) 0);
            hotelOrdersExample.setOrderByClause("id asc");
            List<HotelOrders> hotelOrders = hotelOrdersMapper.selectByExample(hotelOrdersExample);
            if (hotelOrders.size() !=0){
                System.out.println(hotelOrders.get(hotelOrders.size()-1).getOrderStatus());
                if (!hotelOrders.get(hotelOrders.size()-1).getOrderStatus().equals(OrderType.COMPLETE.getOrderType())
                        &&!hotelOrders.get(hotelOrders.size()-1).getOrderStatus().equals(OrderType.REJECT.getOrderType())){
                    response.sendError(1001,"上次提交的订单还未处理");
                    return builder.body(ResponseUtils.getResponseBody(1));
                }
            }


//            cartProducts.forEach(cartProduct -> {
            //库存校验
                for (CartProduct cartProduct:cartProducts){
                HotelProduct hotelProduct = hotelProductMapper.selectByPrimaryKey(cartProduct.getProductId());
                if ((hotelProduct.getQuantity()-cartProduct.getQuantity())<0){
                    response.sendError(1000,"库存不足");
                    return builder.body(ResponseUtils.getResponseBody(cartProduct.getProductId()));
                }
            }
            //占时扣减库存,二开优化
            for (CartProduct cartProduct:cartProducts){
                HotelProduct hotelProduct = hotelProductMapper.selectByPrimaryKey(cartProduct.getProductId());
                hotelProduct.setId(cartProduct.getProductId());
                hotelProduct.setQuantity(hotelProduct.getQuantity()-cartProduct.getQuantity());
                hotelProduct.setModifyTime(LocalDateTime.now());
                hotelProductMapper.updateByPrimaryKeySelective(hotelProduct);
            }
        }
        if (cartProducts.size() != 0){
            double amount = cartProducts.stream()
                    .mapToDouble(a->Double.valueOf(a.getQuantity()) * a.getPrice().doubleValue())
                    .sum();
            //创建订单
            HotelOrders hotelOrders = new HotelOrders();
            hotelOrders.setOrderCode(getC(UUID.randomUUID().toString()));
            hotelOrders.setUserId(cartProducts.get(0).getUserId());
            hotelOrders.setOrderStatus(OrderType.PENDING.getOrderType());
            hotelOrders.setModifyTime(LocalDateTime.now());
            hotelOrders.setCreateTime(LocalDateTime.now());
            hotelOrders.setIsDeleted((byte) 0);
            hotelOrders.setAmount(new BigDecimal(amount));
            //用户信息
            String userBuilder = (hotelUser.getNickName() == null ? "name" : hotelUser.getRealName()) +
                    "+" +
                    (hotelUser.getSex() == null ? "sex" : (hotelUser.getSex() == 0 ? "女" : "男")) +
                    "+" +
                    (hotelUser.getPhone() == null ? "phone" : hotelUser.getPhone()) +
                    "+" + (hotelUser.getIdentityRegion() == null ? "region" : hotelUser.getIdentityRegion()) +
                    ";";
            hotelOrders.setLastModifier(userBuilder);
            hotelOrdersMapper.insertSelective(hotelOrders);
            //创建订单详情
            StringBuilder stringBuilder = new StringBuilder();
            cartProducts.forEach(cartProduct -> {
                stringBuilder.append(cartProduct.getProductId())
                             .append("+")
                             .append(cartProduct.getProductName())
                             .append("*")
                             .append(cartProduct.getQuantity())
                             .append(";");
                HotelOrdersDetail hotelOrdersDetail = new HotelOrdersDetail();
                hotelOrdersDetail.setOrderId(hotelOrders.getId());
                hotelOrdersDetail.setProductId(cartProduct.getProductId());
                hotelOrdersDetail.setQuantity(cartProduct.getQuantity());
                hotelOrdersDetail.setCreateTime(LocalDateTime.now());
                hotelOrdersDetail.setModifyTime(LocalDateTime.now());
                hotelOrdersDetail.setIsDeleted((byte) 0);
                HotelProduct hotelProduct = hotelProductMapper.selectByPrimaryKey(cartProduct.getProductId());
                String json = JSONObject.toJSONString(hotelProduct);
                hotelOrdersDetail.setHfDesc(json);
                hotelOrdersDetailMapper.insertSelective(hotelOrdersDetail);
            });
            hotelOrders.setHfRemark(stringBuilder.toString());
            hotelOrdersMapper.updateByPrimaryKeySelective(hotelOrders);
        }

        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "订单查询", notes = "订单查询")
    @RequestMapping(value = "/selectOrder", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectOrder(
            Integer userId,
            Integer start,
            Integer num,
            String condition) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelOrdersExample hotelOrdersExample = new HotelOrdersExample();
        HotelOrdersExample.Criteria criteria = hotelOrdersExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        hotelOrdersExample.setOrderByClause("modify_time desc");
        if (condition != null){
            criteria.andLastModifierLike("%"+ condition +"%");
        }
        if (userId !=null){
            criteria.andUserIdEqualTo(userId);
        }
        SelectOrders selectOrders = new SelectOrders();
        //总数
        long nums = hotelOrdersMapper.countByExample(hotelOrdersExample);
        selectOrders.setNums(nums);
        //
        if (start!=null && num!=null){
            //mysql 从0开始算数据,前端从1开始
            start -= 1;
            //转化成分页从第start开始,num条
            start = start*10;
            hotelOrdersExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        //
        List<HotelOrders> hotelOrders =
                hotelOrdersMapper.selectByExample(hotelOrdersExample);
        selectOrders.setHotelOrders(hotelOrders);
        return builder.body(ResponseUtils.getResponseBody(selectOrders));

    }

    @ApiOperation(value = "修改订单状态", notes = "修改订单状态")
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderCode", value = "订单编号", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "originalStatus", value = "原始订单状态", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "changeStatus", value = "改变到的订单状态", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updateOrder(String orderCode,String originalStatus,String changeStatus) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelOrdersExample hotelOrdersExample = new HotelOrdersExample();
        hotelOrdersExample.createCriteria()
                .andOrderCodeEqualTo(orderCode)
                .andOrderStatusEqualTo(originalStatus)
                .andIsDeletedEqualTo((byte) 0);
        List<HotelOrders> hotelOrders =
                hotelOrdersMapper.selectByExample(hotelOrdersExample);
        if (hotelOrders.size()!=0){
            HotelOrders hotelOrders1 = hotelOrders.get(0);
            hotelOrders1.setOrderStatus(changeStatus);
            hotelOrders1.setModifyTime(LocalDateTime.now());
            hotelOrdersMapper.updateByPrimaryKeySelective(hotelOrders1);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除订单", notes = "删除订单")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteOrder(Integer orderId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelOrders hotelOrders = new HotelOrders();
        hotelOrders.setId(orderId);
        hotelOrders.setIsDeleted((byte) 1);
        hotelOrders.setModifyTime(LocalDateTime.now());
        hotelOrdersMapper.updateByPrimaryKeySelective(hotelOrders);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//    @RequestMapping(value = "/getcort1")
//    public String send() {
//        String context = "order_ok " + new Date();
//        System.out.println("Sender : " + context);
//// 调用 发送消息的方法
//        //convertAndSend 发送消息返回为空
//        //convertSendAndReceive发送消息 可接收回复
//        Object a = rabbitTemplate.convertSendAndReceive("myQueue3", context);
//        System.out.println("收到"+a);
//        return "创建订单成功";
//    }

//    @RabbitListener(queues ="myQueue3")
//    @RabbitHandler
//    @ApiOperation(value = "压力测试", notes = "压力测试")
//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
//    public  String test(String message) throws Exception {
//        System.out.println("收到了消息:"+message);
//        HotelProduct hotelProduct = hotelProductMapper.selectByPrimaryKey(1);
//        if (hotelProduct.getQuantity()>0){
//            hotelProduct.setQuantity(hotelProduct.getQuantity() - 1);
//            System.out.println("剩余库存："+(hotelProductMapper.selectByPrimaryKey(1).getQuantity()));
//            hotelProductMapper.updateByPrimaryKeySelective(hotelProduct);
//            return "ok";
//        } else {
//            System.out.println("库存不足");
//            return "no";
//        }
////            if (productService.quantity()>0){
////                System.out.println("成功,剩余库存:"+hotelProductMapper.selectByPrimaryKey(1).getQuantity());
////            } else {
////                System.out.println("库存不足");
////            }
//
//    }

//    @RabbitListener(queues ="myQueue3")
//    @RabbitHandler
//    @ApiOperation(value = "压力测试", notes = "压力测试")
//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
//    public String test() throws Exception {
//        System.out.println("创建订单");
////        synchronized("LOCK"){
////            HotelProduct hotelProduct = hotelProductMapper.selectByPrimaryKey(1);
////            if (hotelProduct.getQuantity()>0){
////                hotelProduct.setQuantity(hotelProduct.getQuantity() - 1);
////                System.out.println("剩余库存："+(hotelProductMapper.selectByPrimaryKey(1).getQuantity()));
////                hotelProductMapper.updateByPrimaryKeySelective(hotelProduct);
////                return "ok";
////            } else {
////                System.out.println("库存不足");
////                return "no";
////            }
////        }
//        if (productService.quantity() > 0) {
//            System.out.println("成功,剩余库存:" + hotelProductMapper.selectByPrimaryKey(1).getQuantity());
//            return "ok";
//        } else {
//            System.out.println("库存不足");
//            return "no";
//        }
//
//    }

@ApiOperation(value = "小程序订单查询", notes = "小程序订单查询")
@RequestMapping(value = "/miniSelectOrder", method = RequestMethod.GET)
public ResponseEntity<JSONObject> miniSelectOrder(
        Integer userId,
        Integer start,
        Integer num) throws Exception {
    ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
    HotelOrdersExample hotelOrdersExample = new HotelOrdersExample();
    HotelOrdersExample.Criteria criteria = hotelOrdersExample.createCriteria()
            .andIsDeletedEqualTo((byte) 0);
    hotelOrdersExample.setOrderByClause("modify_time desc");
    if (userId !=null){
        criteria.andUserIdEqualTo(userId);
    }
    MiniSelectOrder selectOrders = new MiniSelectOrder();
    //总数
    long nums = hotelOrdersMapper.countByExample(hotelOrdersExample);
    selectOrders.setNums(nums);
    //
    if (start!=null && num!=null){
        start -= 1;
        hotelOrdersExample.setOrderByClause("id limit " + start + ","  + num);
    }
    //
    List<HotelOrders> hotelOrders =
            hotelOrdersMapper.selectByExample(hotelOrdersExample);
    List<Order> orders = new ArrayList<>();
    hotelOrders.forEach(hotelOrders1 -> {
        Order order = new Order();
        order.setOrderId(hotelOrders1.getId());
        order.setOrderCode(hotelOrders1.getOrderCode());
        order.setModifTime(hotelOrders1.getModifyTime());
        order.setOrderStatus(hotelOrders1.getOrderStatus());
        order.setSumMoney(hotelOrders1.getAmount());
        HotelOrdersDetailExample hotelOrdersDetailExample = new HotelOrdersDetailExample();
        hotelOrdersDetailExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andOrderIdEqualTo(hotelOrders1.getId());
        List<HotelOrdersDetail> hotelOrdersDetails
                 = hotelOrdersDetailMapper.selectByExample(hotelOrdersDetailExample);
        order.setHotelOrdersDetails(hotelOrdersDetails);
        orders.add(order);
    });
    selectOrders.setOrders(orders);
    return builder.body(ResponseUtils.getResponseBody(selectOrders));

}
}
