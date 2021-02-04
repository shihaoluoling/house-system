package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author shihao
 * @Title: TestController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/test")
@Api
public class TestController {
    @ApiOperation(value = "排查", notes = "排查")
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> check(@RequestParam(value = "boundary")String[] boundary,@RequestParam(value = "obstacle") List<Double[]> obstacle) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        System.out.println(Arrays.toString(boundary));
        System.out.println(obstacle.get(0));
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
