package com.example.user.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.*;
import com.example.user.center.manual.SelectType;
import com.example.user.center.manual.TypePictureEnum;
import com.example.user.center.manual.model.HomeType;
import com.example.user.center.model.*;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shihao
 * @Title: TypeController
 * @ProjectName Second-order-center
 * @Description: 户型
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Type")
@CrossOrigin
public class TypeController {
    @Autowired
    private HouseTypeMapper houseTypeMapper;
    @Autowired
    private HouseTypeConstituteGroupMapper houseTypeConstituteGroupMapper;

    //图
    @Autowired
    private HouseTypePictureMapper houseTypePictureMapper;

    @Autowired
    private HousePremisesMapper housePremisesMapper;
    //土地
    @Autowired
    private HouseLandMapper houseLandMapper;
    //项目
    @Autowired
    private HouseProjectMapper houseProjectMapper;
    //板块
    @Autowired
    private HousePlateMapper housePlateMapper;
    //新政区域
    @Autowired
    private HouseAdministrativeMapper houseAdministrativeMapper;
    //房间组成
    @Autowired
    private HouseTypeConstituteMapper houseTypeConstituteMapper;
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "添加户型", notes = "添加户型")
    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "houseName", value = "户型名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "premisesId", value = "楼盘id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "area", value = "户型面积", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "supply", value = "供应套数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "transaction", value = "成交套数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "masterWide", value = "起居室面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "southWide", value = "南向总面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "livingWide", value = "起居室面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "guestWide", value = "客卧面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "constitute", value = "组成", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addType(
            @RequestParam(name = "houseName") String houseName,
            @RequestParam(name = "premisesId") Integer premisesId,
            @RequestParam(name = "area") Double area,
            @RequestParam(name = "supply") Integer supply,
            @RequestParam(name = "transaction") Integer transaction,
            @RequestParam(name = "southWide") Double southWide,
            @RequestParam(name = "masterWide") Double masterWide,
            @RequestParam(name = "livingWide") Double livingWide,
            @RequestParam(name = "guestWide") Double guestWide,
            @RequestParam(name = "constitute") String constitute,
            @RequestParam(name = "houseFiles") String[] houseFiles,//户型图
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);


        HouseType houseType = new HouseType();
        houseType.setPremisesId(premisesId);
        houseType.setHouseName(houseName);
        houseType.setArea(area);
        houseType.setSupply(supply);
        houseType.setTransaction(transaction);
        houseType.setSouthWide(southWide);
        houseType.setMasterWide(masterWide);
        houseType.setLivingWide(livingWide);
        houseType.setGuestWide(guestWide);
        houseType.setCreateDate(LocalDateTime.now());
        houseType.setModifyDate(LocalDateTime.now());
        houseType.setIsDeleted((byte) 0);
        houseTypeMapper.insertSelective(houseType);
        Map mapTypes = JSON.parseObject(constitute);
        //constituteId 房间组成id, value对应参数值
        for (Object obj : mapTypes.keySet()){
            System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
            HouseTypeConstituteGroup houseTypeConstituteGroup = new HouseTypeConstituteGroup();
            houseTypeConstituteGroup.setConstituteId(Integer.parseInt(obj.toString()));
            houseTypeConstituteGroup.setTypeId(houseType.getId());
            houseTypeConstituteGroup.setValue(Double.valueOf(mapTypes.get(obj).toString()));
            houseTypeConstituteGroup.setCreateDate(LocalDateTime.now());
            houseTypeConstituteGroup.setModifyDate(LocalDateTime.now());
            houseTypeConstituteGroup.setIsDeleted((byte) 0);
            houseTypeConstituteGroupMapper.insertSelective(houseTypeConstituteGroup);
//            houseTypeConstituteGroup.set
        }
        if (houseFiles.length != 0){
            for (String file : houseFiles){
                HouseTypePicture houseTypePicture = new HouseTypePicture();
                houseTypePicture.setFile(file);
                houseTypePicture.setState(String.valueOf(0));
                houseTypePicture.setTypeId(houseType.getId());
                houseTypePicture.setCreateDate(LocalDateTime.now());
                houseTypePicture.setModifyDate(LocalDateTime.now());
                houseTypePicture.setIsDeleted((byte) 0);
                houseTypePicture.setType(TypePictureEnum.TYPE.getPaymentTypeName());
                houseTypePictureMapper.insertSelective(houseTypePicture);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "修改户型", notes = "修改户型")
    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "houseName", value = "户型名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "houseId", value = "户型id", required = true, type = "houseId"),
            @ApiImplicitParam(paramType = "query", name = "premisesId", value = "楼盘id", required = true, type = "houseId"),
            @ApiImplicitParam(paramType = "query", name = "area", value = "户型面积", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "supply", value = "供应套数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "transaction", value = "成交套数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "masterWide", value = "起居室面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "southWide", value = "南向总面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "livingWide", value = "起居室面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "guestWide", value = "客卧面宽", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "constitute", value = "组成", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> updateType(
            @RequestParam(name = "houseName") String houseName,
            @RequestParam(name = "houseId") Integer houseId,
            @RequestParam(name = "premisesId") Integer premisesId,
            @RequestParam(name = "area") Double area,
            @RequestParam(name = "supply") Integer supply,
            @RequestParam(name = "transaction") Integer transaction,
            @RequestParam(name = "southWide") Double southWide,
            @RequestParam(name = "masterWide") Double masterWide,
            @RequestParam(name = "livingWide") Double livingWide,
            @RequestParam(name = "guestWide") Double guestWide,
            String constitute,
            @RequestParam(name = "houseFiles") String[] houseFiles,//户型图
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        HouseType houseType = new HouseType();
        houseType.setPremisesId(premisesId);
        houseType.setId(houseId);
        houseType.setHouseName(houseName);
        houseType.setArea(area);
        houseType.setSupply(supply);
        houseType.setTransaction(transaction);
        houseType.setSouthWide(southWide);
        houseType.setMasterWide(masterWide);
        houseType.setLivingWide(livingWide);
        houseType.setGuestWide(guestWide);
        houseType.setCreateDate(LocalDateTime.now());
        houseType.setModifyDate(LocalDateTime.now());
        houseType.setIsDeleted((byte) 0);
        houseTypeMapper.updateByPrimaryKeySelective(houseType);
        if (constitute != null){
            Map mapTypes = JSON.parseObject(constitute);
            //constituteId 房间组成id, value对应参数值
            for (Object obj : mapTypes.keySet()){
                System.out.println("key为："+obj+"值为："+mapTypes.get(obj));
                HouseTypeConstituteGroupExample houseTypeConstituteGroupExample = new HouseTypeConstituteGroupExample();
                houseTypeConstituteGroupExample.createCriteria()
                        .andConstituteIdEqualTo(Integer.parseInt(obj.toString()))
                        .andIsDeletedEqualTo((byte) 0)
                        .andTypeIdEqualTo(houseType.getId());
                HouseTypeConstituteGroup houseTypeConstituteGroup = new HouseTypeConstituteGroup();
                houseTypeConstituteGroup.setConstituteId(Integer.parseInt(obj.toString()));
                houseTypeConstituteGroup.setTypeId(houseType.getId());
                houseTypeConstituteGroup.setValue(Double.valueOf(mapTypes.get(obj).toString()));
                houseTypeConstituteGroup.setModifyDate(LocalDateTime.now());
                houseTypeConstituteGroupMapper.updateByExampleSelective(houseTypeConstituteGroup,houseTypeConstituteGroupExample);
//            houseTypeConstituteGroup.set
            }
        }
        if (houseFiles.length != 0){
            for (String file : houseFiles){
                HouseTypePicture houseTypePicture = new HouseTypePicture();
                houseTypePicture.setFile(file);
                houseTypePicture.setState(String.valueOf(0));
                houseTypePicture.setTypeId(houseType.getId());
                houseTypePicture.setCreateDate(LocalDateTime.now());
                houseTypePicture.setModifyDate(LocalDateTime.now());
                houseTypePicture.setIsDeleted((byte) 0);
                houseTypePicture.setType(TypePictureEnum.TYPE.getPaymentTypeName());
                houseTypePictureMapper.insertSelective(houseTypePicture);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "删除户型", notes = "删除户型")
    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "houseId", value = "户型id", required = true, type = "houseId"),
    })
    public ResponseEntity<JSONObject> deleteType(
            @RequestParam(name = "houseId") Integer houseId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseType houseType = new HouseType();
        houseType.setId(houseId);
        houseType.setModifyDate(LocalDateTime.now());
        houseType.setIsDeleted((byte) 1);
        houseTypeMapper.updateByPrimaryKeySelective(houseType);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "删除图片", notes = "删除图片")
    @RequestMapping(value = "/deletePicture", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "Files", value = "图片", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> deletePicture(
            @RequestParam(name = "Files") String[] Files,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        for (String file : Files){
            HouseTypePictureExample houseTypePictureExample = new HouseTypePictureExample();
            houseTypePictureExample.createCriteria()
                    .andFileEqualTo(file)
                    .andIsDeletedEqualTo((byte) 0);
            HouseTypePicture houseTypePicture = new HouseTypePicture();
            houseTypePicture.setIsDeleted((byte) 1);
            houseTypePicture.setModifyDate(LocalDateTime.now());
            houseTypePictureMapper.updateByExampleSelective(houseTypePicture,houseTypePictureExample);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询户型信息", notes = "查询户型信息")
    @RequestMapping(value = "/selectType", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectType(
            Integer projectId,//项目id
            Integer plateId,//板块id
            String typeName,//户型名称
            Integer adminId,//区域id
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTypeExample houseTypeExample = new HouseTypeExample();
        houseTypeExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        List<HouseType> houseTypes =
                houseTypeMapper.selectByExample(houseTypeExample);
        List<SelectType> selectTypes = new ArrayList<>();
        //开始循环
//        houseTypes.forEach(houseType -> {
            for (HouseType houseType:houseTypes){
            SelectType selectType = new SelectType();
            selectType.setTypeId(houseType.getId());
            selectType.setTypeName(houseType.getHouseName());
            selectType.setPremisesId(houseType.getPremisesId());
            HousePremises housePremises = housePremisesMapper.selectByPrimaryKey(houseType.getPremisesId());
            selectType.setPremisesName(housePremises.getPremisesName());
            //土地
            HouseLand houseLand = houseLandMapper.selectByPrimaryKey(housePremises.getLandId());
            selectType.setLandName(houseLand.getLandName());
            //项目
            HouseProject houseProject = houseProjectMapper.selectByPrimaryKey(houseLand.getProjectId());
            selectType.setProjectName(houseProject.getProjectName());
            selectType.setProjectId(houseProject.getId());
            //板块
            HousePlate housePlate = housePlateMapper.selectByPrimaryKey(houseLand.getPlateId());
            selectType.setPlateName(housePlate.getPlateName());
            selectType.setPlateId(housePlate.getId());
            //行政区域
            HouseAdministrative houseAdministrative = houseAdministrativeMapper.selectByPrimaryKey(housePlate.getAdministrativeId());
            selectType.setAdministrativeName(houseAdministrative.getAdministrativeName());
            selectType.setAdministrativeId(houseAdministrative.getId());
            //图片
            HouseTypePictureExample houseTypePictureExample = new HouseTypePictureExample();
            houseTypePictureExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andTypeEqualTo(TypePictureEnum.TYPE.getPaymentTypeName())
                    .andTypeIdEqualTo(houseType.getId());
            List<HouseTypePicture> houseTypePictures = houseTypePictureMapper.selectByExample(houseTypePictureExample);
            if (houseTypePictures.size()!=0){
                List<String> files = houseTypePictures.stream()
                        .map(HouseTypePicture::getFile).collect(Collectors.toList());
                selectType.setFiles(files);
            }
            //户型面积
            selectType.setArea(houseType.getArea());
            //供应套数
            selectType.setSupply(houseType.getSupply());
            //成交套数
            selectType.setTransaction(houseType.getTransaction());
            //南向总面宽
            selectType.setSouthWide(houseType.getSouthWide());
            //起居室面宽
            selectType.setLivingWide(houseType.getLivingWide());
            //主卧面宽
            selectType.setMasterWide(houseType.getMasterWide());
            //客卧面宽
            selectType.setGuestWide(houseType.getGuestWide());
            //房间组成
            //查询有哪些组成
HouseTypeConstituteGroupExample houseTypeConstituteGroupExample = new HouseTypeConstituteGroupExample();
            houseTypeConstituteGroupExample.createCriteria()
                    .andTypeIdEqualTo(houseType.getId())
                    .andIsDeletedEqualTo((byte) 0);
            //设置参数值
            List<HouseTypeConstituteGroup> houseTypeConstituteGroups =
                    houseTypeConstituteGroupMapper.selectByExample(houseTypeConstituteGroupExample);
            if (houseTypeConstituteGroups.size()!=0){
//                Map<String,HouseTypeConstituteGroup> map = new HashMap<>();
                List<HomeType> homeTypes = new ArrayList<>();
                houseTypeConstituteGroups.forEach(houseTypeConstituteGroup -> {
                    HomeType homeType = new HomeType();
                    HouseTypeConstitute houseTypeConstitute =
                            houseTypeConstituteMapper.selectByPrimaryKey(houseTypeConstituteGroup.getConstituteId());
                    homeType.setTypeId(houseTypeConstituteGroup.getTypeId());
                    homeType.setConstituteId(houseTypeConstituteGroup.getConstituteId());
                    homeType.setValue(houseTypeConstituteGroup.getValue());
                    if (houseTypeConstitute!=null){
                        homeType.setTypeName(houseTypeConstitute.getConstituteName());
                    }
                    homeTypes.add(homeType);
//                    map.put(houseTypeConstitute.getConstituteName(),houseTypeConstituteGroup.getValue());
                });
                selectType.setConstitute(homeTypes);
            }
            //供求比
            selectType.setRatio(houseType.getTransaction().doubleValue()/houseType.getSupply().doubleValue());
            selectTypes.add(selectType);
        }
        if (adminId != null){
            selectTypes = selectTypes.stream()
                    .filter(a->a.getAdministrativeId().equals(adminId)).collect(Collectors.toList());
        }
        if (projectId != null){//项目筛选
            selectTypes = selectTypes.stream()
                    .filter(a->a.getProjectId().equals(projectId)).collect(Collectors.toList());
        }
        if (plateId != null){//板块筛选
            selectTypes = selectTypes.stream()
                    .filter(a->a.getPlateId().equals(plateId)).collect(Collectors.toList());
        }
        return builder.body(ResponseUtils.getResponseBody(selectTypes));
    }
}
