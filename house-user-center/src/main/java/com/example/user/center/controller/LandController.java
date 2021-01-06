package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.*;
import com.example.user.center.manual.SelectLand;
import com.example.user.center.model.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    //项目
    @Autowired
    private HouseProjectMapper houseProjectMapper;
    //板块
    @Autowired
    private HousePlateMapper housePlateMapper;
//区域
    @Autowired
    private HouseAdministrativeMapper houseAdministrativeMapper;
    //楼盘
    @Autowired
    private HousePremisesMapper housePremisesMapper;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @ApiOperation(value = "添加土地", notes = "添加土地")
    @RequestMapping(value = "/addLand", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
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

    @ApiOperation(value = "查询土地信息", notes = "查询土地信息")
    @RequestMapping(value = "/selectLand", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectLand(
            Integer projectId,//项目id
            Integer plateId,//板块id
            String landName,//土地名称
            Integer adminId,//区域id
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLandExample houseLandExample = new HouseLandExample();
        HouseLandExample.Criteria criteria =
        houseLandExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        if (projectId != null){//项目筛选
            criteria.andProjectIdEqualTo(projectId);
        }
        if (plateId != null){//板块筛选
            criteria.andPlateIdEqualTo(plateId);
        }
        if (landName != null){//土地名称筛选
            criteria.andLandNameLike("%"+landName+"%");
        }
        List<HouseLand> houseLands =
                houseLandMapper.selectByExample(houseLandExample);
        List<SelectLand> selectLands = new ArrayList<>();
        //houseLands
        houseLands.forEach(houseLand -> {
            //houseLand
            SelectLand selectLand = new SelectLand();
            selectLand.setLandId(houseLand.getId());
            //土地名称
            selectLand.setLandName(houseLand.getLandName());
            //土地地址
            selectLand.setLandAddress(houseLand.getLandAddress());
            //所属项目id
            selectLand.setProjectId(houseLand.getProjectId());
            //所属项目名称
            HouseProject houseProject = houseProjectMapper.selectByPrimaryKey(houseLand.getProjectId());
            selectLand.setProjectName(houseProject.getProjectName());
            //所属板块id
            selectLand.setPlateId(houseLand.getPlateId());
            //所属板块名称
            HousePlate housePlate =
            housePlateMapper.selectByPrimaryKey(houseLand.getPlateId());
            selectLand.setPlateName(housePlate.getPlateName());
            //所属区域名称
            HouseAdministrative houseAdministrative =
            houseAdministrativeMapper.selectByPrimaryKey(housePlate.getAdministrativeId());
            selectLand.setAdministrativeName(houseAdministrative.getAdministrativeName());
            selectLand.setAdministrativeId(houseAdministrative.getId());
            //建筑密度
            selectLand.setDensity(houseLand.getDensity());
            //成交日期
            selectLand.setSucceedTime(houseLand.getSucceedTime());
            //成交价格
            selectLand.setTransactionPrice(houseLand.getTransactionPrice());
            //起拍价格
            selectLand.setStartingPrice(houseLand.getStartingPrice());
            //受让方
            selectLand.setTransfer(houseLand.getTransfer());
            //备注
            selectLand.setRemark(houseLand.getRemark());
        /**
         * 楼盘查询
 */

            HousePremisesExample housePremisesExample = new HousePremisesExample();
            housePremisesExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andLandIdEqualTo(houseLand.getId());
            List<HousePremises> housePremises = housePremisesMapper.selectByExample(housePremisesExample);
            //楼盘
            if (housePremises.size()!=0){
                List<String> PremisesName =  housePremises.stream()
                        .map(HousePremises::getPremisesName).collect(Collectors.toList());
                selectLand.setPremises(PremisesName);
                //用地总面积面积 楼盘相加
                Double siteArea = housePremises.stream()
                        .mapToDouble(HousePremises::getSiteArea).sum();
                selectLand.setSiteArea(siteArea);
                //建筑面积 楼盘相加
                Double architectureArea = housePremises.stream()
                        .mapToDouble(HousePremises::getArchitectureArea).sum();
                selectLand.setArchitectureArea(architectureArea);
                //容积率  建筑面积 / 用地面积
                if (architectureArea!=0 && siteArea!=0){
                    BigDecimal bg = new BigDecimal(architectureArea/siteArea);
                    selectLand.setRatio(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                } else {
                    selectLand.setRatio(Double.valueOf(0));
                }
            }
            //溢价率 土地成交价 / 土地初始起拍价格

            //楼面价 土地成交价格 / （用地面积 * 容积率 ） 或 土地成交价 /  建筑面积
//            selectLand.setAccommodation();
            selectLands.add(selectLand);
        });
        if (adminId != null){
            List<SelectLand> selectLands1 = selectLands.stream()
                    .filter(a-> a.getAdministrativeId().equals(adminId)).collect(Collectors.toList());
            return builder.body(ResponseUtils.getResponseBody(selectLands1));
        }
        return builder.body(ResponseUtils.getResponseBody(selectLands));
    }
}
