package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.*;
import com.example.user.center.manual.*;
import com.example.user.center.manual.model.RatioCount;
import com.example.user.center.model.*;
import com.example.user.center.service.RatioCountService;
import com.google.common.collect.Lists;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shihao
 * @Title: DeskController
 * @ProjectName Second-order-center
 * @Description: 前台数据
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Desk")
@CrossOrigin
public class DeskController {
    //板块
    @Autowired
    private HousePlateMapper housePlateMapper;
    //行政区域
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
    //楼盘
    @Autowired
    private HousePremisesMapper housePremisesMapper;
    //户型
    @Autowired
    private HouseTypeMapper houseTypeMapper;
    //
    @Autowired
    private RatioCountService ratioCountService;
    //产品标签
    @Autowired
    private HousePremisesLabelMapper housePremisesLabelMapper;
    //项目
    @Autowired
    private HouseProjectMapper houseProjectMapper;
    //
    @Autowired
    private HousePremisesPictureMapper housePremisesPictureMapper;
    @ApiOperation(value = "区域分析板块查询", notes = "区域分析板块查询")
    @RequestMapping(value = "/selectPlate", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectLabel() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePlateExample housePlateExample = new HousePlateExample();
        housePlateExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        List<HousePlate> housePlates =
        housePlateMapper.selectByExample(housePlateExample);
        return builder.body(ResponseUtils.getResponseBody(housePlates));
    }
    @ApiOperation(value = "区域分析板块详情查询", notes = "区域分析板块详情查询")
    @RequestMapping(value = "/selectPlateDetails", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectPlateDetails(Integer plateId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HousePlate housePlate = housePlateMapper.selectByPrimaryKey(plateId);
        SelectPlateDesk selectPlateDesk = new SelectPlateDesk();
        selectPlateDesk.setPlateId(housePlate.getId());
        selectPlateDesk.setPlateName(housePlate.getPlateName());
        selectPlateDesk.setAdministrativeId(housePlate.getAdministrativeId());
        //查询行政区
        HouseAdministrative houseAdministrative =
                houseAdministrativeMapper.selectByPrimaryKey(housePlate.getAdministrativeId());
        selectPlateDesk.setAdministrative(houseAdministrative.getAdministrativeName());
        //板块地址
        selectPlateDesk.setAddress(housePlate.getAddress());
        //板块标签
        HousePlateLabelExample housePlateLabelExample = new HousePlateLabelExample();
        housePlateLabelExample.createCriteria().andPlateIdEqualTo(housePlate.getId())
                .andIsDeletedEqualTo((byte) 0);
        List<HousePlateLabel> housePlateLabels =
                housePlateLabelMapper.selectByExample(housePlateLabelExample);
        if (housePlateLabels.size()!=0){
            Set<Integer> labels = housePlateLabels.stream()
                    .map(HousePlateLabel::getId).collect(Collectors.toSet());
            HouseLabelExample houseLabelExample = new HouseLabelExample();
            houseLabelExample.createCriteria()
                    .andIdIn(Lists.newArrayList(labels))
                    .andIsDeletedEqualTo((byte) 0);
            List<HouseLabel> houseLabels =
                    houseLabelMapper.selectByExample(houseLabelExample);
        List<String> pLabels = houseLabels.stream()
                .map(HouseLabel::getLabelName).collect(Collectors.toList());
            selectPlateDesk.setLabel(pLabels);
        }
        //区域优势---
        selectPlateDesk.setAdvantage(housePlate.getAdvantage());
        //置业均价----
        selectPlateDesk.setAveragePrice(housePlate.getAveragePrice());
        //区域发展信息-----
        selectPlateDesk.setDevelopMessage(housePlate.getDevelopMessage());
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
                    .andLandIdIn(Lists.newArrayList(landIds))
            .andIsDeletedEqualTo((byte) 0);
            List<HousePremises> housePremises =
                    housePremisesMapper.selectByExample(housePremisesExample);
            if (housePremises.size() != 0){ //-------
                //已有楼盘 houses-----
                List<String> houses = housePremises.stream()
                        .map(HousePremises::getPremisesName).collect(Collectors.toList());
                selectPlateDesk.setHouses(houses);
                //楼盘均价
//            楼盘所有价格-------
                Double moneys = housePremises.stream()
                        .mapToDouble(a->a.getPrice().doubleValue())
                        .sum();
                Double money = moneys/housePremises.size();//平均
                BigDecimal d1TobigDe = new BigDecimal(money);
                selectPlateDesk.setHousesPrice(d1TobigDe);
                //楼盘ids
                Set<Integer> premisesIds = housePremises.stream()
                        .map(HousePremises::getId).collect(Collectors.toSet());
                //---楼盘所有标签
                HousePremisesLabelExample housePremisesLabelExample = new HousePremisesLabelExample();
                housePremisesLabelExample.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andPremisesIdIn(Lists.newArrayList(premisesIds));
                List<HousePremisesLabel> housePremisesLabels =
                        housePremisesLabelMapper.selectByExample(housePremisesLabelExample);
                //标签不为空
                if (housePremisesLabels.size() !=0){
                    Set<Integer> prLabelId = housePremisesLabels.stream()
                            .map(HousePremisesLabel::getLabelId).collect(Collectors.toSet());
                    HouseLabelExample houseLabelExample = new HouseLabelExample();
                    houseLabelExample.createCriteria()
                            .andIdIn(Lists.newArrayList(prLabelId))
                            .andIsDeletedEqualTo((byte) 0);
                    List<HouseLabel> prLabels =
                            houseLabelMapper.selectByExample(houseLabelExample);
                    List<String> prLabel = prLabels.stream()
                            .map(HouseLabel::getLabelName).collect(Collectors.toList());
                    //----
                    selectPlateDesk.setLabelProduct(prLabel);
                }


                //取所有容积率
//                    List<Double> ratios = housePremises.stream()
//                            .map(HousePremises::getPlotRatio).collect(Collectors.toList());
                //---最大容积率
                Optional<HousePremises> ratioMax = housePremises.stream().filter(a->Optional.ofNullable(a.getPlotRatio()).isPresent())
                        .max(Comparator.comparing(HousePremises::getPlotRatio));
                selectPlateDesk.setPlotRatioMax(Optional.ofNullable(ratioMax.get().getPlotRatio()).orElse(Double.valueOf(0)));
                //----最小容积率
                Optional<HousePremises> ratioMin = housePremises.stream().filter(a->Optional.ofNullable(a.getPlotRatio()).isPresent())
                        .min(Comparator.comparing(HousePremises::getPlotRatio));
                selectPlateDesk.setPlotRatioMin(Optional.ofNullable(ratioMin.get().getPlotRatio()).orElse(Double.valueOf(0)));
                //所有容积率和出现次数
                List<RatioCount> ratioCounts = ratioCountService.count(Lists.newArrayList(premisesIds));
                Double ratio = Optional.ofNullable(ratioCounts.get(0).getPlotRatio()).orElse(Double.valueOf(0));
                //-----最多出现容积率,如果有多个占取第一个
                selectPlateDesk.setPlotRatioMuch(ratio);
                //查询寻所有户型
                HouseTypeExample houseTypeExample = new HouseTypeExample();
                houseTypeExample.createCriteria()
                        .andPremisesIdIn(Lists.newArrayList(premisesIds))
                .andIsDeletedEqualTo((byte) 0);
                List<HouseType> houseTypes =
                        houseTypeMapper.selectByExample(houseTypeExample);
                if (houseTypes.size()!=0){//------------
                    //计算总供应套数
                    Integer sumSupply = houseTypes.stream()
                            .mapToInt(HouseType::getSupply).sum();
                    selectPlateDesk.setSupply(sumSupply);
                    //计算总成交套数
                    Integer sumTransaction = houseTypes.stream()
                            .mapToInt(HouseType::getTransaction).sum();
                    selectPlateDesk.setTransaction(sumTransaction);
                    //供求比
                    selectPlateDesk.setRatio(sumSupply.doubleValue()/sumTransaction.doubleValue());
                }

            }

        }
        return builder.body(ResponseUtils.getResponseBody(selectPlateDesk));
    }

    @ApiOperation(value = "区域分析板块下的土地查询", notes = "区域分析板块下的土地查询")
    @RequestMapping(value = "/selectPlateLand", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectPlateLand(Integer plateId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLandExample houseLandExample = new HouseLandExample();
        houseLandExample.createCriteria()
                .andPlateIdEqualTo(plateId)
                .andIsDeletedEqualTo((byte) 0);
        List<HouseLand> houseLands = houseLandMapper.selectByExample(houseLandExample);
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
        return builder.body(ResponseUtils.getResponseBody(selectLands));
    }

    @ApiOperation(value = "区域分析板块下的楼盘查询", notes = "区域分析板块下的楼盘查询")
    @RequestMapping(value = "/selectPlatePremises", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectPlatePremises(Integer plateId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //板块查询土地
        HouseLandExample houseLandExample = new HouseLandExample();
        houseLandExample.createCriteria()
                .andPlateIdEqualTo(plateId)
                .andIsDeletedEqualTo((byte) 0);
        List<HouseLand> houseLands =
                houseLandMapper.selectByExample(houseLandExample);
        //土地ids
        Set<Integer> landIds = houseLands.stream()
                .map(HouseLand::getId).collect(Collectors.toSet());
        //土地查询楼盘
        HousePremisesExample housePremisesExample = new HousePremisesExample();
        housePremisesExample.createCriteria()
                .andLandIdIn(Lists.newArrayList(landIds))
        .andIsDeletedEqualTo((byte) 0);
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
        return builder.body(ResponseUtils.getResponseBody(selectPremises));
    }
}
