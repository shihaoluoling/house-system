package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.*;
import com.example.user.center.manual.Login;
import com.example.user.center.manual.SelectPlate;
import com.example.user.center.model.*;
import com.google.common.collect.Lists;
import com.house.common.utils.Decrypt;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shihao
 * @Title: PlateController
 * @ProjectName Second-order-center
 * @Description: 区域板块
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/Plate")
@Api
public class PlateController {
@Autowired
private HousePlateMapper housePlateMapper;
//行政区
    @Autowired
    private HouseAdministrativeMapper houseAdministrativeMapper;
    //板块标签
    @Autowired
    private HousePlateLabelMapper housePlateLabelMapper;
    //标签
    @Autowired
    private HouseLabelMapper houseLabelMapper;
    //土地
    @Autowired
    private HouseLandMapper houseLandMapper;
@Autowired
    private HousePremisesMapper housePremisesMapper;
//户型
    @Autowired
    private HouseTypeMapper houseTypeMapper;
    @ApiOperation(value = "添加板块", notes = "添加板块")
    @RequestMapping(value = "/addPlate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "plateName", value = "板块名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "administrativeId", value = "行政区id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "plateAddress", value = "板块地址", required = true, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "plateLabelId", value = "板块标签id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "advantage", value = "区域优势", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "averagePrice", value = "置业均价", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "developMessage", value = "区域发展信息", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addPlate(
            @RequestParam(name = "plateName") String plateName,
            @RequestParam(name = "administrativeId") Integer administrativeId,
            @RequestParam(name = "plateAddress") String plateAddress,
            @RequestParam(name = "plateLabelId") Integer[] plateLabelId,
            @RequestParam(name = "advantage") String advantage,
            @RequestParam(name = "averagePrice") BigDecimal averagePrice,
            @RequestParam(name = "developMessage") String developMessage,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePlate housePlate = new HousePlate();
        housePlate.setPlateName(plateName);
        housePlate.setAdministrativeId(administrativeId);
        housePlate.setAddress(plateAddress);
        housePlate.setAdvantage(advantage);
        housePlate.setAveragePrice(averagePrice);
        housePlate.setDevelopMessage(developMessage);
        housePlate.setCreateDate(LocalDateTime.now());
        housePlate.setModifyDate(LocalDateTime.now());
        housePlate.setIsDeleted((byte) 0);
        housePlateMapper.insertSelective(housePlate);
        if (plateLabelId.length != 0){
            for (Integer LabelId:plateLabelId){
                HousePlateLabel housePlateLabel = new HousePlateLabel();
                housePlateLabel.setLabelId(LabelId);
                housePlateLabel.setPlateId(housePlate.getId());
                housePlateLabel.setState(String.valueOf(0));
                housePlateLabel.setCreateDate(LocalDateTime.now());
                housePlateLabel.setModifyDate(LocalDateTime.now());
                housePlateLabel.setIsDeleted((byte) 0);
                housePlateLabelMapper.insertSelective(housePlateLabel);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    //
    @ApiOperation(value = "修改板块", notes = "修改板块")
    @RequestMapping(value = "/updatePlate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "plateName", value = "板块名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "administrativeId", value = "行政区id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "plateId", value = "板块id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "plateAddress", value = "板块地址", required = true, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "plateLabelId", value = "板块标签id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "advantage", value = "区域优势", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "averagePrice", value = "置业均价", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "developMessage", value = "区域发展信息", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updatePlate(
            @RequestParam(name = "plateName") String plateName,
            @RequestParam(name = "administrativeId") Integer administrativeId,
            @RequestParam(name = "plateId") Integer plateId,
            @RequestParam(name = "plateAddress") String plateAddress,
            @RequestParam(name = "plateLabelId") Integer[] plateLabelId,
            @RequestParam(name = "advantage") String advantage,
            @RequestParam(name = "averagePrice") BigDecimal averagePrice,
            @RequestParam(name = "developMessage") String developMessage,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePlate housePlate = new HousePlate();
        housePlate.setId(plateId);
        housePlate.setPlateName(plateName);
        housePlate.setAdministrativeId(administrativeId);
        housePlate.setAddress(plateAddress);
        housePlate.setAdvantage(advantage);
        housePlate.setAveragePrice(averagePrice);
        housePlate.setDevelopMessage(developMessage);
        housePlate.setModifyDate(LocalDateTime.now());
        housePlateMapper.updateByPrimaryKeySelective(housePlate);
//先删除所有标签,没有保留记录意义直接删除
        HousePlateLabelExample housePlateLabelExample = new HousePlateLabelExample();
        housePlateLabelExample.createCriteria()
                .andPlateIdEqualTo(housePlate.getId())
                .andIsDeletedEqualTo((byte) 0);
        housePlateLabelMapper.deleteByExample(housePlateLabelExample);
                //再添加标签
        if (plateLabelId.length != 0){
            for (Integer LabelId:plateLabelId){
                HousePlateLabel housePlateLabel = new HousePlateLabel();
                housePlateLabel.setLabelId(LabelId);
                housePlateLabel.setPlateId(housePlate.getId());
                housePlateLabel.setState(String.valueOf(0));
                housePlateLabel.setCreateDate(LocalDateTime.now());
                housePlateLabel.setModifyDate(LocalDateTime.now());
                housePlateLabel.setIsDeleted((byte) 0);
                housePlateLabelMapper.insertSelective(housePlateLabel);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    //

    @ApiOperation(value = "删除板块", notes = "删除板块")
    @RequestMapping(value = "/deletedPlate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "plateId", value = "板块id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deletedPlate(
            @RequestParam(name = "plateId") Integer plateId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePlate housePlate = new HousePlate();
        housePlate.setId(plateId);
        housePlate.setIsDeleted((byte) 1);
        housePlateMapper.updateByPrimaryKeySelective(housePlate);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    //
    @ApiOperation(value = "查询板块", notes = "查询板块")
    @RequestMapping(value = "/selectPlate", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectPlate(
//            Integer start,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePlateExample housePlateExample = new HousePlateExample();
//        housePlateExample.setOrderByClause("id limit " + start + ","+2);
        housePlateExample.createCriteria().andIsDeletedEqualTo((byte) 0);
//        RowBounds rowBounds = new RowBounds(start, 20); //　每次查询20条
//        housePlateMapper.selectByExampleAndRowBounds(housePlateExample,rowBounds);
        List<HousePlate> housePlates =
        housePlateMapper.selectByExample(housePlateExample);
        List<SelectPlate> selectPlates = new ArrayList<>();
        housePlates.forEach(housePlate -> {
            SelectPlate selectPlate = new SelectPlate();
            selectPlate.setPlateId(housePlate.getId());
            selectPlate.setPlateName(housePlate.getPlateName());
            selectPlate.setAdministrativeId(housePlate.getAdministrativeId());
            //查询行政区
            HouseAdministrative houseAdministrative =
            houseAdministrativeMapper.selectByPrimaryKey(housePlate.getAdministrativeId());
            selectPlate.setAdministrative(houseAdministrative.getAdministrativeName());
            //板块地址
            selectPlate.setAddress(housePlate.getAddress());
            //标签
            HousePlateLabelExample housePlateLabelExample = new HousePlateLabelExample();
            housePlateLabelExample.createCriteria().andPlateIdEqualTo(housePlate.getId())
                    .andIsDeletedEqualTo((byte) 0);
            List<HousePlateLabel> housePlateLabels =
            housePlateLabelMapper.selectByExample(housePlateLabelExample);
            Set<Integer> labels = housePlateLabels.stream()
                    .map(HousePlateLabel::getId).collect(Collectors.toSet());
            HouseLabelExample houseLabelExample = new HouseLabelExample();
            houseLabelExample.createCriteria()
                    .andIdIn(Lists.newArrayList(labels))
            .andIsDeletedEqualTo((byte) 0);
            List<HouseLabel> houseLabels =
                    houseLabelMapper.selectByExample(houseLabelExample);
            Map<Object,String> map = new HashMap<>();
            houseLabels.forEach(houseLabel -> {
                map.put(houseLabel.getId().toString(),houseLabel.getLabelName());
            });
            selectPlate.setLabel(map);
            //区域优势
            selectPlate.setAdvantage(housePlate.getAdvantage());
            //置业均价
            selectPlate.setAveragePrice(housePlate.getAveragePrice());
            //区域发展信息
            selectPlate.setDevelopMessage(housePlate.getDevelopMessage());
//供应套数
//区域板块查询土地
            HouseLandExample houseLandExample = new HouseLandExample();
            houseLandExample.createCriteria().andPlateIdEqualTo(housePlate.getId())
                    .andIsDeletedEqualTo((byte) 0);
            List<HouseLand> houseLands =
                    houseLandMapper.selectByExample(houseLandExample);
            //土地ids
            Set<Integer> landIds = houseLands.stream()
                    .map(HouseLand::getId).collect(Collectors.toSet());
            if (landIds.size() != 0){//---------------------
                //土地查询所有楼盘
                HousePremisesExample housePremisesExample = new HousePremisesExample();
                housePremisesExample.createCriteria()
                        .andLandIdIn(Lists.newArrayList(landIds));
                List<HousePremises> housePremises =
                        housePremisesMapper.selectByExample(housePremisesExample);
                if (housePremises.size() != 0){ //-------
                    //已有楼盘 houses
                    List<String> houses = housePremises.stream()
                            .map(HousePremises::getPremisesName).collect(Collectors.toList());
                    selectPlate.setHouses(houses);
                    //楼盘均价
//            楼盘所有价格
                    Double moneys = housePremises.stream()
                            .mapToDouble(a->a.getPrice().doubleValue())
                            .sum();
                    Double money = moneys/housePremises.size();//平均
                    BigDecimal d1TobigDe = new BigDecimal(money);
                    selectPlate.setHousesPrice(d1TobigDe);
                    //楼盘ids
                    Set<Integer> premisesIds = housePremises.stream()
                            .map(HousePremises::getId).collect(Collectors.toSet());
                    //查询寻所有户型
                    HouseTypeExample houseTypeExample = new HouseTypeExample();
                    houseTypeExample.createCriteria()
                            .andPremisesIdIn(Lists.newArrayList(premisesIds));
                    List<HouseType> houseTypes =
                            houseTypeMapper.selectByExample(houseTypeExample);
                    if (houseTypes.size()!=0){//------------
                        //计算总供应套数
                        Integer sumSupply = houseTypes.stream()
                                .mapToInt(HouseType::getSupply).sum();
                        selectPlate.setSupply(sumSupply);
                        //计算总成交套数
                        Integer sumTransaction = houseTypes.stream()
                                .mapToInt(HouseType::getTransaction).sum();
                        selectPlate.setTransaction(sumTransaction);
                        //供求比
                        selectPlate.setRatio(sumSupply.doubleValue()/sumTransaction.doubleValue());
                    }

                }

            }


            selectPlates.add(selectPlate);
        });
        return builder.body(ResponseUtils.getResponseBody(selectPlates));
    }
}
