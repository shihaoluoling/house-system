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
import java.util.*;

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
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> check() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        double[] a = {5,10};
        double[] b = {5,25};
        double[] c = {30,25};
        double[] d = {30,10};
        List<double[]> abcd = new ArrayList<>();
        abcd.add(a);
        abcd.add(b);
        abcd.add(c);
        abcd.add(d);
        List<double[]> efgh = new ArrayList<>();
        double[] e = {5,30};
        double[] f = {5,45};
        double[] g = {20,45};
        double[] h = {20,30};
        efgh.add(e);
        efgh.add(f);
        efgh.add(g);
        efgh.add(h);


        return builder.body(ResponseUtils.getResponseBody(sides(efgh)));
    }
    public double[] sides(List<double[]> abcd){
        double[] side = {0,0,0,0};
        //z 距离
        double z = 0;
        for (int i = 0; i<abcd.size();i++){
            if (i!=3){
                double x1 = abcd.get(i)[0];
                double y1 = abcd.get(i)[1];

                double x2 = abcd.get(i+1)[0];
                double y2 = abcd.get(i+1)[1];
                double x = x1-x2;
                double y = y1-y2;
                x =  x < 0 ? -x : x;
                y =  y < 0 ? -y : y;
                //todo 求距离
                z = Math.sqrt((x*x+y*y));
                side[i]= z;
            } else {
                double x1 = abcd.get(i)[0];
                double y1 = abcd.get(i)[1];

                double x2 = abcd.get(0)[0];
                double y2 = abcd.get(0)[1];

                double x = x1-x2;
                double y = y1-y2;
                x =  x < 0 ? -x : x;
                y =  y < 0 ? -y : y;
                //todo 求距离
                z = Math.sqrt((x*x+y*y));
                side[i]= z;
            }
        }
        return side;
    }
}
