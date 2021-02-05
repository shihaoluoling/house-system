package com.example.user.center.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.user.center.manual.Test;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    @ApiOperation(value = "求矩形四条边的长度", notes = "求矩形四条边的长度")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> check(String coord) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        JSONArray jsonArray= JSONArray.parseArray(coord);
        List<Test> list = JSONObject.parseArray(jsonArray.toJSONString(), Test.class);
        System.out.println(list.get(0).getX());
        return builder.body(ResponseUtils.getResponseBody(sides(list)));
    }

    /**
     * 求矩形的四条边
     * @param abcd
     * @return
     */
    public double[] sides(List<Test> abcd){
        double[] side = {0,0,0,0};
        //z 距离
        double z = 0;
        for (int i = 0; i<abcd.size();i++){
            if (i!=3){
                double x1 = abcd.get(i).getX();
                double y1 = abcd.get(i).getY();

                double x2 = abcd.get(i+1).getX();
                double y2 = abcd.get(i+1).getY();
                double x = x1-x2;
                double y = y1-y2;
                x =  x < 0 ? -x : x;
                y =  y < 0 ? -y : y;
                //todo 求距离
                z = Math.sqrt((x*x+y*y));
                side[i]= z;
            } else {
                double x1 = abcd.get(i).getX();
                double y1 = abcd.get(i).getY();

                double x2 = abcd.get(0).getX();
                double y2 = abcd.get(0).getY();

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
    @ApiOperation(value = "计算矩形某一长边到另一个矩形长边的距离", notes = "A-》B")
    @RequestMapping(value = "/check1", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> check1(double Ax1,double Ax2,double Ay1,double Ay2,
                                             double Bx1,double Bx2,double By1,double By2) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //线段端点1的
        double x = distance(Ax1,Ay1,Bx1,Bx2,By1,By2);
        //线段端点二的
        double y = distance(Ax2,Ay2,Bx1,Bx2,By1,By2);
        if (x!=y){
            return builder.body(ResponseUtils.getResponseBody("两条线段不平行"));
        }
        return builder.body(ResponseUtils.getResponseBody(x));
    }
    /**
     * 求矩形端点到另一个矩形长边的距离
     * 正规矩形的情况
     * @param Ax 点x坐标
     * @param Ay 点y坐标
     *
     * @param Bx1 边长2的y坐标
     * @param Bx2 边长2的y坐标
     * @param By1 边长2的y坐标
     * @param By2 边长2的y坐标
     *
     * @return
     */
    public double distance(double Ax,double Ay,
                           double Bx1,double Bx2,double By1,double By2){
        //当x1=x2时，给斜率设一个较大值10000
        //todo 点(Ax1,Ax2) 到B线段的距离
        double k =Bx1==Bx2 ? 10000 : (By2-By1)/(Bx2-Bx1);
        double a=k;
        double b=-1;
        double c=By1-k*Bx1;
        double d=Math.abs(a*Ax+b*Ay+c)/Math.sqrt(a*a+b*b);
        double px=(b*b*Ax-a*b*Ay-a*c)/(a*a+b*b);
        double py=(a*a*Ay-a*b*Ax-b*c)/(a*a+b*b);
        System.out.println(px);
        System.out.println(py);
        //todo double精度问题转保留两位
        BigDecimal bg = new BigDecimal(d);
        d = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }
    @ApiOperation(value = "计算点到线的垂足坐标", notes = "A-》B")
    @RequestMapping(value = "/pedal", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> pedal(double Ax1,double Ax2,
                                             double Bx1,double Bx2,double By1,double By2) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        return builder.body(ResponseUtils.getResponseBody(coord(Ax1,Ax2,Bx1,Bx2,By1,By2)));
    }
    /**
     * 求点到线的垂足坐标
     *
     * @return
     */
    public Test coord(double Ax,double Ay,
                         double Bx1,double Bx2,double By1,double By2){
        //当x1=x2时，给斜率设一个较大值10000
        //todo 点(Ax1,Ax2) 到B线段的距离
        double k =Bx1==Bx2 ? 10000 : (By2-By1)/(Bx2-Bx1);
        double a=k;
        double b=-1;
        double c=By1-k*Bx1;
        double d=Math.abs(a*Ax+b*Ay+c)/Math.sqrt(a*a+b*b);
        double px=(b*b*Ax-a*b*Ay-a*c)/(a*a+b*b);
        double py=(a*a*Ay-a*b*Ax-b*c)/(a*a+b*b);
        //todo x坐标
        BigDecimal bg = new BigDecimal(px);
        px = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // todo y坐标
        BigDecimal bg1 = new BigDecimal(py);
        py = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Test test = new Test();
        System.out.println(px);
        System.out.println(py);
        test.setX(px);
        test.setY(py);
        //todo double精度问题转保留两位

        return test;
    }
}
