package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.HotelProductMapper;
import com.example.admin.center.manual.JSON.SelectProduct;
import com.example.admin.center.model.HotelProduct;
import com.example.admin.center.model.HotelProductExample;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: ProductController
 * @ProjectName Second-order-center
 * @Description: 商品
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Product")
@CrossOrigin
public class ProductController {
    @Autowired
    private HotelProductMapper hotelProductMapper;
    @ApiOperation(value = "添加商品", notes = "添加商品")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "商品名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "商品价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "subscribeQuantity", value = "预约最大数量", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "quantity", value = "总数", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addProduct(String name, BigDecimal price, Integer subscribeQuantity
            , Integer quantity) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelProduct hotelProduct = new HotelProduct();
        hotelProduct.setProductName(name);
        hotelProduct.setPrice(price);
        hotelProduct.setSubscribeQuantity(subscribeQuantity);
        hotelProduct.setQuantity(quantity);
        hotelProduct.setCreateTime(LocalDateTime.now());
        hotelProduct.setModifyTime(LocalDateTime.now());
        hotelProduct.setIsDeleted((short) 0);
        hotelProductMapper.insertSelective(hotelProduct);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改商品", notes = "修改商品")
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "商品名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "商品价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "subscribeQuantity", value = "预约最大数量", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "quantity", value = "总数", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateProduct(Integer productId,String name, BigDecimal price, Integer subscribeQuantity
            , Integer quantity) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelProduct hotelProduct = new HotelProduct();
        hotelProduct.setId(productId);
        hotelProduct.setProductName(name);
        hotelProduct.setPrice(price);
        hotelProduct.setSubscribeQuantity(subscribeQuantity);
        hotelProduct.setQuantity(quantity);
        hotelProduct.setModifyTime(LocalDateTime.now());
        hotelProductMapper.updateByPrimaryKeySelective(hotelProduct);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteProduct(Integer productId
            ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelProduct hotelProduct = new HotelProduct();
        hotelProduct.setId(productId);
        hotelProduct.setIsDeleted((short) 1);
        hotelProductMapper.updateByPrimaryKeySelective(hotelProduct);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "后台查询商品", notes = "后台查询商品")
    @RequestMapping(value = "/selectProduct", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectProduct(
            Integer start,
            Integer num
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelProductExample hotelProductExample = new HotelProductExample();
        hotelProductExample.createCriteria()
                .andIsDeletedEqualTo((short) 0);
        //满足条件总条数
        long nums = hotelProductMapper.countByExample(hotelProductExample);
        SelectProduct selectProduct = new SelectProduct();
        selectProduct.setNums(nums);
        if (start!=null && num!=null){
            start -= 1;
            start = start*10;
            hotelProductExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        List<HotelProduct> hotelProducts =
                hotelProductMapper.selectByExample(hotelProductExample);
        selectProduct.setHotelProducts(hotelProducts);
        return builder.body(ResponseUtils.getResponseBody(selectProduct));
    }
}
