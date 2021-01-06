package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.*;
import com.example.user.center.manual.*;
import com.example.user.center.model.*;
import com.google.common.collect.Lists;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shihao
 * @Title: PremisesController
 * @ProjectName Second-order-center
 * @Description: 楼盘管理
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Premises")
@CrossOrigin
public class PremisesController {
    @Autowired
    private HousePremisesMapper housePremisesMapper;
    //楼盘标签
    @Autowired
    private HousePremisesLabelMapper housePremisesLabelMapper;
    //楼盘图
    @Autowired
    private HousePremisesPictureMapper housePremisesPictureMapper;
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
    //标签
    @Autowired
    private HouseLabelMapper houseLabelMapper;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @ApiOperation(value = "添加楼盘", notes = "添加楼盘")
    @RequestMapping(value = "/addPremises", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "premisesName", value = "楼盘名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "landId", value = "土地id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "楼盘价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "developersName", value = "开发商名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "households", value = "总户数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "propertyName", value = "物业名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "siteArea", value = "用地面积", required = true, type = "double"),
            @ApiImplicitParam(paramType = "query", name = "architectureArea", value = "建筑面积", required = true, type = "double"),
            @ApiImplicitParam(paramType = "query", name = "plotRatio", value = "容积率", required = true, type = "double"),
//            @ApiImplicitParam(paramType = "query", name = "openingTime", value = "开盘时间", required = true, type = "Date"),
    })
    public ResponseEntity<JSONObject> addPremises(
            @RequestParam(name = "premisesName") String premisesName,
            @RequestParam(name = "landId") Integer landId,
            @RequestParam(name = "price") BigDecimal price,
            @RequestParam(name = "developersName") String developersName,
            @RequestParam(name = "households") Integer households,
            @RequestParam(name = "propertyName") String propertyName,
            @RequestParam(name = "siteArea") Double siteArea,
            @RequestParam(name = "architectureArea") Double architectureArea,
            @RequestParam(name = "plotRatio") Double plotRatio,
            @RequestParam(name = "plateLabelId") Integer[] plateLabelId,//标签
            @RequestParam(name = "houseSunPicture") String[] houseSunPicture,//楼房总图
            @RequestParam(name = "houseFacadePicture") String[] houseFacadePicture,//楼房立面图
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date openingTime,//开盘时间
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePremises housePremises = new HousePremises();
        ZoneId zoneId = ZoneId.systemDefault();
        housePremises.setOpeningTime(LocalDateTime.ofInstant(openingTime.toInstant(), zoneId));
        housePremises.setPremisesName(premisesName);
        housePremises.setLandId(landId);
        housePremises.setPrice(price);
        housePremises.setDevelopersName(developersName);
        housePremises.setHouseholds(households);
        housePremises.setPropertyName(propertyName);
        housePremises.setSiteArea(siteArea);
        housePremises.setArchitectureArea(architectureArea);
        housePremises.setPlotRatio(plotRatio);
        housePremises.setCreateDate(LocalDateTime.now());
        housePremises.setModifyDate(LocalDateTime.now());
        housePremises.setIsDeleted((byte) 0);
        housePremisesMapper.insertSelective(housePremises);
        //标签
        if (plateLabelId.length != 0){
            for (Integer labelId : plateLabelId){
                HousePremisesLabel housePremisesLabel = new HousePremisesLabel();
                housePremisesLabel.setLabelId(labelId);
                housePremisesLabel.setPremisesId(housePremises.getId());
                housePremisesLabel.setState(String.valueOf(0));
                housePremisesLabel.setCreateDate(LocalDateTime.now());
                housePremisesLabel.setModifyDate(LocalDateTime.now());
                housePremisesLabel.setIsDeleted((byte) 0);
                housePremisesLabelMapper.insertSelective(housePremisesLabel);
            }
        }
        //楼房图
        if (houseSunPicture.length != 0){
            for (String PictureFile : houseSunPicture){
                HousePremisesPicture housePremisesPicture = new HousePremisesPicture();
                housePremisesPicture.setState(String.valueOf(0));
                housePremisesPicture.setPremisesId(housePremises.getId());
                housePremisesPicture.setFile(PictureFile);
                housePremisesPicture.setType(PictureEnum.SUM.getPaymentTypeName());
                housePremisesPicture.setCreateDate(LocalDateTime.now());
                housePremisesPicture.setModifyDate(LocalDateTime.now());
                housePremisesPicture.setIsDeleted((byte) 0);
                housePremisesPictureMapper.insertSelective(housePremisesPicture);
            }
        }
        //立面图
        if (houseFacadePicture.length != 0){
            for (String PictureFile : houseFacadePicture){
                HousePremisesPicture housePremisesPicture = new HousePremisesPicture();
                housePremisesPicture.setState(String.valueOf(0));
                housePremisesPicture.setPremisesId(housePremises.getId());
                housePremisesPicture.setFile(PictureFile);
                housePremisesPicture.setType(PictureEnum.FACADE.getPaymentTypeName());
                housePremisesPicture.setCreateDate(LocalDateTime.now());
                housePremisesPicture.setModifyDate(LocalDateTime.now());
                housePremisesPicture.setIsDeleted((byte) 0);
                housePremisesPictureMapper.insertSelective(housePremisesPicture);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "编辑楼盘", notes = "编辑楼盘")
    @RequestMapping(value = "/updatePremises", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "premisesName", value = "楼盘名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "premisesId", value = "楼盘id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "landId", value = "土地id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "楼盘价格", required = true, type = "BigDecimal"),
            @ApiImplicitParam(paramType = "query", name = "developersName", value = "开发商名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "households", value = "总户数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "propertyName", value = "物业名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "siteArea", value = "用地面积", required = true, type = "double"),
            @ApiImplicitParam(paramType = "query", name = "architectureArea", value = "建筑面积", required = true, type = "double"),
            @ApiImplicitParam(paramType = "query", name = "plotRatio", value = "容积率", required = true, type = "double"),
//            @ApiImplicitParam(paramType = "query", name = "openingTime", value = "开盘时间", required = true, type = "Date"),
    })
    public ResponseEntity<JSONObject> updatePremises(
            @RequestParam(name = "premisesName") String premisesName,
            @RequestParam(name = "premisesId") Integer premisesId,
            @RequestParam(name = "landId") Integer landId,
            @RequestParam(name = "price") BigDecimal price,
            @RequestParam(name = "developersName") String developersName,
            @RequestParam(name = "households") Integer households,
            @RequestParam(name = "propertyName") String propertyName,
            @RequestParam(name = "siteArea") Double siteArea,
            @RequestParam(name = "architectureArea") Double architectureArea,
            @RequestParam(name = "plotRatio") Double plotRatio,
            @RequestParam(name = "plateLabelId") Integer[] plateLabelId,//标签
            @RequestParam(name = "houseSunPicture") String[] houseSunPicture,//楼房总图
            @RequestParam(name = "houseFacadePicture") String[] houseFacadePicture,//楼房立面图
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePremises housePremises = new HousePremises();
        housePremises.setId(premisesId);
        housePremises.setPremisesName(premisesName);
        housePremises.setLandId(landId);
        housePremises.setPrice(price);
        housePremises.setDevelopersName(developersName);
        housePremises.setHouseholds(households);
        housePremises.setPropertyName(propertyName);
        housePremises.setSiteArea(siteArea);
        housePremises.setArchitectureArea(architectureArea);
        housePremises.setPlotRatio(plotRatio);
        housePremises.setModifyDate(LocalDateTime.now());
        housePremisesMapper.updateByPrimaryKeySelective(housePremises);
        //标签
        HousePremisesLabelExample housePremisesLabelExample = new HousePremisesLabelExample();
        housePremisesLabelExample.createCriteria()
                .andPremisesIdEqualTo(housePremises.getId())
                .andIsDeletedEqualTo((byte) 0);
        housePremisesLabelMapper.deleteByExample(housePremisesLabelExample);
        if (plateLabelId.length != 0){
            for (Integer labelId : plateLabelId){
                HousePremisesLabel housePremisesLabel = new HousePremisesLabel();
                housePremisesLabel.setLabelId(labelId);
                housePremisesLabel.setPremisesId(housePremises.getId());
                housePremisesLabel.setState(String.valueOf(0));
                housePremisesLabel.setCreateDate(LocalDateTime.now());
                housePremisesLabel.setModifyDate(LocalDateTime.now());
                housePremisesLabel.setIsDeleted((byte) 0);
                housePremisesLabelMapper.insertSelective(housePremisesLabel);
            }
        }
        //楼房图
        if (houseSunPicture.length != 0){
            for (String PictureFile : houseSunPicture){
                HousePremisesPicture housePremisesPicture = new HousePremisesPicture();
                housePremisesPicture.setState(String.valueOf(0));
                housePremisesPicture.setPremisesId(housePremises.getId());
                housePremisesPicture.setFile(PictureFile);
                housePremisesPicture.setType(PictureEnum.SUM.getPaymentTypeName());
                housePremisesPicture.setCreateDate(LocalDateTime.now());
                housePremisesPicture.setModifyDate(LocalDateTime.now());
                housePremisesPicture.setIsDeleted((byte) 0);
                housePremisesPictureMapper.insertSelective(housePremisesPicture);
            }
        }
        //立面图
        if (houseFacadePicture.length != 0){
            for (String PictureFile : houseFacadePicture){
                HousePremisesPicture housePremisesPicture = new HousePremisesPicture();
                housePremisesPicture.setState(String.valueOf(0));
                housePremisesPicture.setPremisesId(housePremises.getId());
                housePremisesPicture.setFile(PictureFile);
                housePremisesPicture.setType(PictureEnum.FACADE.getPaymentTypeName());
                housePremisesPicture.setCreateDate(LocalDateTime.now());
                housePremisesPicture.setModifyDate(LocalDateTime.now());
                housePremisesPicture.setIsDeleted((byte) 0);
                housePremisesPictureMapper.insertSelective(housePremisesPicture);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除楼盘", notes = "删除楼盘")
    @RequestMapping(value = "/deletePremises", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "premisesId", value = "楼盘id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deletePremises(
            @RequestParam(name = "premisesId") Integer premisesId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePremises housePremises = new HousePremises();
        housePremises.setId(premisesId);
        housePremises.setIsDeleted((byte) 1);
        housePremisesMapper.updateByPrimaryKeySelective(housePremises);
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
            HousePremisesPictureExample housePremisesPictureExample = new HousePremisesPictureExample();
            housePremisesPictureExample.createCriteria()
            .andFileEqualTo(file)
            .andIsDeletedEqualTo((byte) 0);
            HousePremisesPicture housePremisesPicture = new HousePremisesPicture();
            housePremisesPicture.setIsDeleted((byte) 1);
            housePremisesPicture.setModifyDate(LocalDateTime.now());
            housePremisesPictureMapper.updateByExampleSelective(housePremisesPicture,housePremisesPictureExample);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询楼盘信息", notes = "查询楼盘信息")
    @RequestMapping(value = "/selectPremises", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectPremises(
            Integer projectId,//项目id
            Integer plateId,//板块id
            String premisesName,//楼盘名称
            Integer adminId,//区域id
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePremisesExample housePremisesExample = new HousePremisesExample();
        HousePremisesExample.Criteria criteria =
        housePremisesExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        if (premisesName != null){
            criteria.andPremisesNameLike("%"+premisesName+"%");
        }
        List<HousePremises> housePremises =
                housePremisesMapper.selectByExample(housePremisesExample);
        List<SelectPremises> selectPremises = new ArrayList<>();
//        housePremises.forEach(housePremises1 -> {
            for (HousePremises housePremises1:housePremises){
            //housePremises1
            SelectPremises selectPremises1 = new SelectPremises();
            selectPremises1.setPremisesId(housePremises1.getId());
            selectPremises1.setPremisesName(housePremises1.getPremisesName());
            //土地
            HouseLand houseLand = houseLandMapper.selectByPrimaryKey(housePremises1.getLandId());
            selectPremises1.setLandId(houseLand.getId());
            selectPremises1.setLandName(houseLand.getLandName());
            ////所属板块名称
            HousePlate housePlate = housePlateMapper.selectByPrimaryKey(houseLand.getPlateId());
            selectPremises1.setPlateName(housePlate.getPlateName());
            selectPremises1.setPlateId(housePlate.getId());
            //所属项目名称
            HouseProject houseProject = houseProjectMapper.selectByPrimaryKey(houseLand.getProjectId());
            selectPremises1.setProjectName(houseProject.getProjectName());
            selectPremises1.setProjectId(houseProject.getId());
            //所属区域名称
            HouseAdministrative houseAdministrative = houseAdministrativeMapper.selectByPrimaryKey(housePlate.getAdministrativeId());
            selectPremises1.setAdministrativeName(houseAdministrative.getAdministrativeName());
            selectPremises1.setAdministrativeId(houseAdministrative.getId());
            //楼盘总图
            HousePremisesPictureExample housePremisesPictureExample = new HousePremisesPictureExample();
            housePremisesPictureExample.createCriteria()
                    .andTypeEqualTo(PictureEnum.SUM.getPaymentTypeName())
                    .andIsDeletedEqualTo((byte) 0)
            .andPremisesIdEqualTo(housePremises1.getId());
            List<HousePremisesPicture> housePremisesPictures =
                    housePremisesPictureMapper.selectByExample(housePremisesPictureExample);
            if (housePremisesPictures.size()!=0){
                List<String> sunFiles = housePremisesPictures.stream()
                        .map(HousePremisesPicture::getFile).collect(Collectors.toList());
                selectPremises1.setSumFiles(sunFiles);
            }
            //楼房立面图
            housePremisesPictureExample.clear();
            housePremisesPictureExample.createCriteria()
                    .andTypeEqualTo(PictureEnum.FACADE.getPaymentTypeName())
                    .andIsDeletedEqualTo((byte) 0)
                    .andPremisesIdEqualTo(housePremises1.getId());
            List<HousePremisesPicture> housePremisesPictures1 =
                    housePremisesPictureMapper.selectByExample(housePremisesPictureExample);
            if (housePremisesPictures1.size()!=0){
                List<String> facadeFiles = housePremisesPictures1.stream()
                        .map(HousePremisesPicture::getFile).collect(Collectors.toList());
                selectPremises1.setFacadeFiles(facadeFiles);
            }

            //标签
            HousePremisesLabelExample housePremisesLabelExample = new HousePremisesLabelExample();
            housePremisesLabelExample.createCriteria().andIsDeletedEqualTo((byte) 0)
                    .andPremisesIdEqualTo(housePremises1.getId());
            List<HousePremisesLabel> housePremisesLabels =
                    housePremisesLabelMapper.selectByExample(housePremisesLabelExample);
                //拿出所有标签id
            Set<Integer> labelIds = housePremisesLabels.stream()
                    .map(HousePremisesLabel::getLabelId).collect(Collectors.toSet());
            //查询所有标签
            if (!labelIds.isEmpty()){
                HouseLabelExample houseLabelExample = new HouseLabelExample();
                houseLabelExample.createCriteria().andIdIn(Lists.newArrayList(labelIds));
                List<HouseLabel> houseLabels = houseLabelMapper.selectByExample(houseLabelExample);
                Map<Object,String> map = new HashMap<>();
                houseLabels.forEach(houseLabel -> {
                    map.put(houseLabel.getId().toString(),houseLabel.getLabelName());
                });
                selectPremises1.setLabels(map);
            }
            //楼盘价格
            selectPremises1.setPrice(housePremises1.getPrice());
            //用地面积
            selectPremises1.setSiteArea(housePremises1.getSiteArea());
            //建筑面积
            selectPremises1.setArchitectureArea(housePremises1.getArchitectureArea());
            //容积率
            BigDecimal bg = new BigDecimal(housePremises1.getArchitectureArea()/housePremises1.getSiteArea());
            selectPremises1.setRatio(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //开盘时间
            selectPremises1.setOpeningTime(housePremises1.getOpeningTime());
            //开发商
            selectPremises1.setDevelopersName(housePremises1.getDevelopersName());
            //总户数
            selectPremises1.setHouseholds(housePremises1.getHouseholds());
            //物业名称
            selectPremises1.setPropertyName(housePremises1.getPropertyName());
            selectPremises.add(selectPremises1);
        }
        if (adminId != null){
            selectPremises = selectPremises.stream()
                    .filter(a->a.getAdministrativeId().equals(adminId)).collect(Collectors.toList());
        }
        if (projectId != null){//项目筛选
            selectPremises = selectPremises.stream()
                    .filter(a->a.getProjectId().equals(projectId)).collect(Collectors.toList());
        }
        if (plateId != null){//板块筛选
            selectPremises = selectPremises.stream()
                    .filter(a->a.getPlateId().equals(plateId)).collect(Collectors.toList());
        }
        return builder.body(ResponseUtils.getResponseBody(selectPremises));
    }
}
