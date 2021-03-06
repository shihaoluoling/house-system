package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.*;
import com.example.user.center.manual.CategoryIsText;
import com.example.user.center.manual.SelectLibraryCategory;
import com.example.user.center.manual.SelectTower;
import com.example.user.center.manual.TextTypeEnum;
import com.example.user.center.manual.model.Tower;
import com.example.user.center.manual.model.TowerLibrary;
import com.example.user.center.model.*;
import com.example.user.center.service.TowerService;
import com.google.common.collect.Lists;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.list.SynchronizedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.nio.file.OpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shihao
 * @Title: TowerController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/Tower")
@Api
public class TowerController {
    /**
     * @param houseLibraryMapper 库
     */
    @Autowired
    private HouseLibraryMapper houseLibraryMapper;
    /**
     * @param houseTowerNoMapper 楼号
     */
    @Autowired
    private HouseTowerNoMapper houseTowerNoMapper;
    /**
     * @param houseTowerLibraryMapper 楼号-库
     */
    @Autowired
    private HouseTowerLibraryMapper houseTowerLibraryMapper;

    /**
     * @param houseLibraryCategoryMapper 分类
     * */
    @Autowired
    HouseLibraryCategoryMapper houseLibraryCategoryMapper;
    /**
     * @param houseLibraryCategoryTextMapper 分类下的文本
     * */
    @Autowired
    private HouseLibraryCategoryTextMapper houseLibraryCategoryTextMapper;
    /**
     * @param houseTowerLibraryCategoryMapper 楼号分类
     * */
    @Autowired
    private HouseTowerLibraryCategoryMapper houseTowerLibraryCategoryMapper;

    @Autowired
    private HousePremisesMapper housePremisesMapper;
    @Autowired
    private TowerService towerService;
    @ApiOperation(value = "新建楼号", notes = "新建楼号")
    @RequestMapping(value = "/addTower", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "premisesId", value = "所属楼盘id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "towerNo", value = "楼号名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "synchronizationNo", value = "同步数据的楼号", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "sequence", value = "序列号", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addTower(String sequence,Integer premisesId,String towerNo,Integer synchronizationNo) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTowerNo houseTower = new HouseTowerNo();
        houseTower.setSequence(sequence);
        houseTower.setPremisesId(premisesId);
        houseTower.setTowerNo(towerNo);
        houseTower.setCreateDate(LocalDateTime.now());
        houseTower.setModifyDate(LocalDateTime.now());
        houseTower.setIsDeleted((byte) 0);
        houseTower.setSynchronizationNo(synchronizationNo);
        houseTowerNoMapper.insertSelective(houseTower);
        if (synchronizationNo!=null){
            // todo同步库
            HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
            houseTowerLibraryExample.createCriteria()
                    .andTowerNoIdEqualTo(synchronizationNo)
                    .andIsDeletedEqualTo((byte) 0);
            List<HouseTowerLibrary> houseTowerLibrarys = houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
            System.out.println("tongbu:"+houseTowerLibrarys.get(0));
            Set<Integer> libraryIds = houseTowerLibrarys.stream().map(HouseTowerLibrary::getLibraryId).collect(Collectors.toSet());
            for (Integer libraryId : libraryIds){
                HouseTowerLibrary houseTowerLibrary = new HouseTowerLibrary();
                houseTowerLibrary.setIsDeleted((byte) 0);
                houseTowerLibrary.setModifyDate(LocalDateTime.now());
                houseTowerLibrary.setCreateDate(LocalDateTime.now());
                houseTowerLibrary.setIsDeleted((byte) 0);
                houseTowerLibrary.setTowerNoId(houseTower.getId());
                houseTowerLibrary.setLibraryId(libraryId);
                houseTowerLibraryMapper.insertSelective(houseTowerLibrary);
                // todo 同步楼号库分类
                HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
                houseTowerLibraryCategoryExample.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andTowerLibraryIdEqualTo(libraryId);
                List<HouseTowerLibraryCategory> houseTowerLibraryCategories = houseTowerLibraryCategoryMapper.selectByExample(houseTowerLibraryCategoryExample);
                Set<Integer> categoryIds = houseTowerLibraryCategories.stream()
                        .map(HouseTowerLibraryCategory::getLibraryCategoryId).collect(Collectors.toSet());
                // todo 添加
                if (categoryIds.size() !=0 ){
                    for (Integer category : categoryIds){
                        HouseTowerLibraryCategory houseTowerLibraryCategory = new HouseTowerLibraryCategory();
                        houseTowerLibraryCategory.setTowerLibraryId(houseTowerLibrary.getId());
                        houseTowerLibraryCategory.setLibraryCategoryId(category);
                        houseTowerLibraryCategory.setCreateDate(LocalDateTime.now());
                        houseTowerLibraryCategory.setModifyDate(LocalDateTime.now());
                        houseTowerLibraryCategory.setIsDeleted((byte) 0);
                        houseTowerLibraryCategoryMapper.insertSelective(houseTowerLibraryCategory);
                    }
                }

            }

        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除楼号", notes = "删除楼号")
    @RequestMapping(value = "/deleteTower", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "towerId", value = "楼号id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteTower(Integer towerId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTowerNo houseTowerNo = new HouseTowerNo();
        houseTowerNo.setIsDeleted((byte) 1);
        houseTowerNo.setModifyDate(LocalDateTime.now());
        houseTowerNo.setId(towerId);
        houseTowerNoMapper.updateByPrimaryKeySelective(houseTowerNo);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询", notes = "查询")
    @RequestMapping(value = "/selectTower", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectTower(
            Integer premisesId,//楼盘id
            Integer plateId,//板块id
            String typeName,//楼号名称
            String sequence,//序列号
            Integer adminId//区域id
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<Tower> houseTowerNos =
                towerService.select(premisesId,plateId,typeName,adminId,sequence);
        List<SelectTower> selectTowers = new ArrayList<>();
        houseTowerNos.forEach(houseTowerNo -> {
            SelectTower selectTower = new SelectTower();
            selectTower.setTowerId(houseTowerNo.getTowerId());
            selectTower.setTowerName(houseTowerNo.getTowerNo());
            selectTower.setPremisesId(houseTowerNo.getPremisesId());
            selectTower.setSequence(houseTowerNo.getSequence());
            if (houseTowerNo.getPremisesId()!=null){
                HousePremises housePremises = housePremisesMapper.selectByPrimaryKey(houseTowerNo.getPremisesId());
                if (housePremises!=null){
                    selectTower.setPremisesName(housePremises.getPremisesName());
                }
            }
            selectTower.setSynchronizationId(houseTowerNo.getSynchronizationNo());
            if (houseTowerNo.getSynchronizationNo()!=null){
                HouseTowerNo houseTowerNo1 =  houseTowerNoMapper.selectByPrimaryKey(houseTowerNo.getSynchronizationNo());
                selectTower.setSynchronizationName(houseTowerNo1.getTowerNo());
            }
            selectTowers.add(selectTower);
        });
        return builder.body(ResponseUtils.getResponseBody(selectTowers));
    }
    /**
     * 添加楼号的库
     * @param libraryIds 库id数组
     * @param towerId 楼号id
     */
    @ApiOperation(value = "添加楼号的库", notes = "添加楼号的库")
    @RequestMapping(value = "/towerLibrary", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "towerId", value = "楼号id", required = true, type = "Integer"),
    })
        public ResponseEntity<JSONObject> towerLibrary(Integer libraryIds,Integer towerId,
                                                       HttpServletResponse response) throws Exception{
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
                    HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
                    houseTowerLibraryExample.createCriteria()
                            .andIsDeletedEqualTo((byte) 0)
                            .andLibraryIdEqualTo(libraryIds)
                            .andTowerNoIdEqualTo(towerId);
                    List<HouseTowerLibrary> houseTowerLibraries =
                            houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
                    if (houseTowerLibraries.size()!=0){
                        response.sendError(501,"此楼号已经有此库了");
                        return builder.body(ResponseUtils.getResponseBody(1));
                    } else {
                        //TODO 添加
                                HouseTowerLibrary houseTowerLibrary = new HouseTowerLibrary();
                                houseTowerLibrary.setLibraryId(libraryIds);
                                houseTowerLibrary.setTowerNoId(towerId);
                                houseTowerLibrary.setCreateDate(LocalDateTime.now());
                                houseTowerLibrary.setModifyDate(LocalDateTime.now());
                                houseTowerLibrary.setIsDeleted((byte) 0);
                                houseTowerLibraryMapper.insertSelective(houseTowerLibrary);
                                return builder.body(ResponseUtils.getResponseBody(houseTowerLibrary.getId()));
                    }
}
    /**
     * 添加楼号的库
     * @param
     * @param towerId 楼号id
     */
    @ApiOperation(value = "查询楼号的库", notes = "查询楼号的库")
    @RequestMapping(value = "/SelectTowerLibrary", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "towerId", value = "楼号id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> SelectTowerLibrary(Integer towerId,
                                                   HttpServletResponse response) throws Exception{
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
        houseTowerLibraryExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andTowerNoIdEqualTo(towerId);
        List<HouseTowerLibrary> houseTowerLibraries = houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
        List<TowerLibrary> towerLibraries = new ArrayList<>();
        houseTowerLibraries.forEach(houseTowerLibrary -> {
            HouseLibrary houseLibrary = houseLibraryMapper.selectByPrimaryKey(houseTowerLibrary.getLibraryId());
            TowerLibrary towerLibrary = new TowerLibrary();
            towerLibrary.setId(houseLibrary.getId());
            towerLibrary.setCreateDate(houseLibrary.getCreateDate());
                    towerLibrary.setIsDeleted(houseLibrary.getIsDeleted());
            towerLibrary.setLibraryName(houseLibrary.getLibraryName());
                    towerLibrary.setModifyDate(houseLibrary.getModifyDate());
            towerLibrary.setState(houseLibrary.getState());
                    towerLibrary.setTowerLibrarys(houseTowerLibrary.getId());
            towerLibraries.add(towerLibrary);
        });
        return builder.body(ResponseUtils.getResponseBody(towerLibraries));
    }
    /**
     * 楼号的库添加分类
     * @param towerLibrarys 楼号和库的实体id
     * @param
     */
    @ApiOperation(value = "添加取消楼号的库", notes = "添加取消楼号的库")
    @RequestMapping(value = "/towerLibraryCategory", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "towerLibrarys", value = "楼号和库的实体id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> towerLibraryCategory(Integer towerLibrarys,Integer[] categoryIds,
                                                   HttpServletResponse response) throws Exception{
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (categoryIds.length !=0){
            for (Integer categoryId: categoryIds){
                HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
                houseTowerLibraryCategoryExample.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andTowerLibraryIdEqualTo(towerLibrarys)
                        .andLibraryCategoryIdEqualTo(categoryId);
                List<HouseTowerLibraryCategory> houseTowerLibraryCategories =
                        houseTowerLibraryCategoryMapper.selectByExample(houseTowerLibraryCategoryExample);
                if (houseTowerLibraryCategories.size() !=0 ){
                    houseTowerLibraryCategoryMapper.deleteByExample(houseTowerLibraryCategoryExample);
                } else {
                    HouseTowerLibraryCategory houseTowerLibraryCategory = new HouseTowerLibraryCategory();
                    houseTowerLibraryCategory.setTowerLibraryId(towerLibrarys);
                    houseTowerLibraryCategory.setLibraryCategoryId(categoryId);
                    houseTowerLibraryCategory.setCreateDate(LocalDateTime.now());
                    houseTowerLibraryCategory.setModifyDate(LocalDateTime.now());
                    houseTowerLibraryCategory.setIsDeleted((byte) 0);
                    houseTowerLibraryCategoryMapper.insertSelective(houseTowerLibraryCategory);
                }
            }


        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查找最低分类", notes = "分类")
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "分类id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> category(Integer categoryId,
                                                   HttpServletResponse response) throws Exception{
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
            HouseLibraryCategoryExample houseLibraryCategoryExample = new HouseLibraryCategoryExample();
            houseLibraryCategoryExample.createCriteria()
                    .andParentCategoryIdEqualTo(categoryId)
                    .andIsDeletedEqualTo((byte) 0)
            .andIsAddTextEqualTo((byte) 0);

        return builder.body(ResponseUtils.getResponseBody(0));
    }
    /**
     * 删除楼号的库
     * @param libraryId 库id
     * @param towerId 楼号id
     */
    @ApiOperation(value = "删除楼号的库", notes = "删除楼号的库")
    @RequestMapping(value = "/deleteTowerLibrary", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "towerId", value = "楼号id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteTowerLibrary(Integer libraryId,Integer towerId,
                                                   HttpServletResponse response) throws Exception{
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
        houseTowerLibraryExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andTowerNoIdEqualTo(towerId)
                .andLibraryIdEqualTo(libraryId);
        houseTowerLibraryMapper.deleteByExample(houseTowerLibraryExample);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "新建库", notes = "新建库")
    @RequestMapping(value = "/addLibrary", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "libraryName", value = "库名称", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addLibrary(String libraryName) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLibrary houseLibrary  = new HouseLibrary();
        houseLibrary.setLibraryName(libraryName);
        houseLibrary.setCreateDate(LocalDateTime.now());
        houseLibrary.setModifyDate(LocalDateTime.now());
        houseLibrary.setIsDeleted((byte) 0);
        houseLibraryMapper.insertSelective(houseLibrary);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改库", notes = "修改库")
    @RequestMapping(value = "/updateLibrary", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "libraryId", value = "库id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "libraryName", value = "库名称", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updateLibrary(Integer libraryId,String libraryName) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLibrary houseLibrary  = new HouseLibrary();
        houseLibrary.setId(libraryId);
        houseLibrary.setLibraryName(libraryName);
        houseLibrary.setModifyDate(LocalDateTime.now());
        houseLibraryMapper.updateByPrimaryKeySelective(houseLibrary);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除库", notes = "删除库")
    @RequestMapping(value = "/deleteLibrary", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "libraryId", value = "库id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteLibrary(Integer libraryId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLibrary houseLibrary  = new HouseLibrary();
        houseLibrary.setId(libraryId);
        houseLibrary.setIsDeleted((byte) 1);
        houseLibrary.setModifyDate(LocalDateTime.now());
        houseLibraryMapper.updateByPrimaryKeySelective(houseLibrary);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询库", notes = "查询库")
    @RequestMapping(value = "/selectLibrary", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})

    public ResponseEntity<JSONObject> selectLibrary() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseLibraryExample houseLibraryExample = new HouseLibraryExample();
        houseLibraryExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        List<HouseLibrary> houseLibraries =
                houseLibraryMapper.selectByExample(houseLibraryExample);
        return builder.body(ResponseUtils.getResponseBody(houseLibraries));
    }
    @ApiOperation(value = "查询库仓详情", notes = "查询库仓详情")
    @RequestMapping(value = "/selectLibraryDetails", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectLibraryDetails(Integer libraryId,Integer towerId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //TODO 先查询出一级分类
        HouseLibraryCategoryExample houseLibraryCategoryExample = new HouseLibraryCategoryExample();
        houseLibraryCategoryExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andLibraryIdEqualTo(libraryId)
                .andLevelEqualTo(1)
                .andParentCategoryIdEqualTo(0);
        List<HouseLibraryCategory> houseLibraryCategories =
                houseLibraryCategoryMapper.selectByExample(houseLibraryCategoryExample);
        //TODO 一级分类

        List<SelectLibraryCategory> selectLibraryCategories = new ArrayList<>();
        houseLibraryCategories.forEach(houseLibraryCategory -> {

            //TODO 一级分类信息
            SelectLibraryCategory selectLibraryCategory = new SelectLibraryCategory();
            selectLibraryCategory.setCategoryId(houseLibraryCategory.getId());
            selectLibraryCategory.setLevel(houseLibraryCategory.getLevel());
            selectLibraryCategory.setCategoryName(houseLibraryCategory.getCategoryName());
            selectLibraryCategory.setIsAddText(houseLibraryCategory.getIsAddText());
            selectLibraryCategory.setIsLast(houseLibraryCategory.getIsLast());
            // todo 楼号查询是否选中
            if (towerId !=null){
                HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
                houseTowerLibraryExample.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andTowerNoIdEqualTo(towerId)
                        .andLibraryIdEqualTo(libraryId);
                List<HouseTowerLibrary> houseTowerLibraries =
                        houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
                HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
                houseTowerLibraryCategoryExample.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andTowerLibraryIdEqualTo(houseTowerLibraries.get(0).getId())
                        .andLibraryCategoryIdEqualTo(houseLibraryCategory.getId());
                List<HouseTowerLibraryCategory> houseTowerLibraryCategories =
                        houseTowerLibraryCategoryMapper.selectByExample(houseTowerLibraryCategoryExample);
                if (houseTowerLibraryCategories.size()!=0){
                    selectLibraryCategory.setPitch(0);
                }else {
                    selectLibraryCategory.setPitch(1);
                }
            }
            //todo 分类下的文本
            if (houseLibraryCategory.getIsAddText().equals((byte) CategoryIsText.YEX.getStatus())){
                HouseLibraryCategoryTextExample houseLibraryCategoryTextExample = new HouseLibraryCategoryTextExample();
                houseLibraryCategoryTextExample.createCriteria()
                        .andLibraryCategoryIdEqualTo(houseLibraryCategory.getId())
                        .andIsDeletedEqualTo((byte) 0);
                List<HouseLibraryCategoryText> houseLibraryCategoryTexts =
                        houseLibraryCategoryTextMapper.selectByExampleWithBLOBs(houseLibraryCategoryTextExample);
                selectLibraryCategory.setHouseLibraryCategoryTexts(houseLibraryCategoryTexts);
            }

            //TODO 二级分类
            HouseLibraryCategoryExample houseLibraryCategoryExample1 = new HouseLibraryCategoryExample();
            houseLibraryCategoryExample1.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andLibraryIdEqualTo(libraryId)
                    .andLevelEqualTo(2)
                    .andParentCategoryIdEqualTo(houseLibraryCategory.getId());
            List<HouseLibraryCategory> houseLibraryCategories1 =
                    houseLibraryCategoryMapper.selectByExample(houseLibraryCategoryExample1);
            List<SelectLibraryCategory> selectLibraryCategories1 = new ArrayList<>();
            houseLibraryCategories1.forEach(houseLibraryCategory1 -> {
                //
                SelectLibraryCategory selectLibraryCategory1 = new SelectLibraryCategory();
                selectLibraryCategory1.setCategoryId(houseLibraryCategory1.getId());
                selectLibraryCategory1.setCategoryName(houseLibraryCategory1.getCategoryName());
                selectLibraryCategory1.setLevel(houseLibraryCategory1.getLevel());
                selectLibraryCategory1.setIsAddText(houseLibraryCategory1.getIsAddText());
                selectLibraryCategory1.setIsLast(houseLibraryCategory1.getIsLast());
// todo 楼号查询是否选中
                if (towerId !=null){
                    HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
                    houseTowerLibraryExample.createCriteria()
                            .andIsDeletedEqualTo((byte) 0)
                            .andTowerNoIdEqualTo(towerId)
                            .andLibraryIdEqualTo(libraryId);
                    List<HouseTowerLibrary> houseTowerLibraries =
                            houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
                    HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
                    houseTowerLibraryCategoryExample.createCriteria()
                            .andIsDeletedEqualTo((byte) 0)
                            .andTowerLibraryIdEqualTo(houseTowerLibraries.get(0).getId())
                            .andLibraryCategoryIdEqualTo(houseLibraryCategory1.getId());
                    List<HouseTowerLibraryCategory> houseTowerLibraryCategories =
                            houseTowerLibraryCategoryMapper.selectByExample(houseTowerLibraryCategoryExample);
                    if (houseTowerLibraryCategories.size()!=0){
                        selectLibraryCategory1.setPitch(0);
                    } else {
                        selectLibraryCategory1.setPitch(1);
                    }
                }
                //todo 分类下的文本
                if (houseLibraryCategory1.getIsAddText().equals((byte) CategoryIsText.YEX.getStatus())){
                    HouseLibraryCategoryTextExample houseLibraryCategoryTextExample = new HouseLibraryCategoryTextExample();
                    houseLibraryCategoryTextExample.createCriteria()
                            .andLibraryCategoryIdEqualTo(houseLibraryCategory1.getId())
                            .andIsDeletedEqualTo((byte) 0);
                    List<HouseLibraryCategoryText> houseLibraryCategoryTexts =
                            houseLibraryCategoryTextMapper.selectByExampleWithBLOBs(houseLibraryCategoryTextExample);
                    selectLibraryCategory.setHouseLibraryCategoryTexts(houseLibraryCategoryTexts);
                }
                //TODO 三级分类
                HouseLibraryCategoryExample houseLibraryCategoryExample2 = new HouseLibraryCategoryExample();
                houseLibraryCategoryExample2.createCriteria()
                        .andIsDeletedEqualTo((byte) 0)
                        .andLibraryIdEqualTo(libraryId)
                        .andLevelEqualTo(3)
                        .andParentCategoryIdEqualTo(houseLibraryCategory1.getId());
                List<HouseLibraryCategory> houseLibraryCategories2 =
                        houseLibraryCategoryMapper.selectByExample(houseLibraryCategoryExample2);
                //todo 展示分类
                List<SelectLibraryCategory> selectLibraryCategories2 = new ArrayList<>();
                houseLibraryCategories2.forEach(houseLibraryCategory2 -> {
                    //
                    SelectLibraryCategory selectLibraryCategory2 = new SelectLibraryCategory();
                    selectLibraryCategory2.setCategoryId(houseLibraryCategory2.getId());
                    selectLibraryCategory2.setCategoryName(houseLibraryCategory2.getCategoryName());
                    selectLibraryCategory2.setLevel(houseLibraryCategory2.getLevel());
                    selectLibraryCategory2.setIsAddText(houseLibraryCategory2.getIsAddText());
                    selectLibraryCategory2.setIsLast(houseLibraryCategory2.getIsLast());
// todo 楼号查询是否选中
                    if (towerId !=null){
                        HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
                        houseTowerLibraryExample.createCriteria()
                                .andIsDeletedEqualTo((byte) 0)
                                .andTowerNoIdEqualTo(towerId)
                                .andLibraryIdEqualTo(libraryId);
                        List<HouseTowerLibrary> houseTowerLibraries =
                                houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
                        HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
                        houseTowerLibraryCategoryExample.createCriteria()
                                .andIsDeletedEqualTo((byte) 0)
                                .andTowerLibraryIdEqualTo(houseTowerLibraries.get(0).getId())
                                .andLibraryCategoryIdEqualTo(houseLibraryCategory2.getId());
                        List<HouseTowerLibraryCategory> houseTowerLibraryCategories =
                                houseTowerLibraryCategoryMapper.selectByExample(houseTowerLibraryCategoryExample);
                        if (houseTowerLibraryCategories.size()!=0){
                            selectLibraryCategory2.setPitch(0);
                        } else {
                            selectLibraryCategory2.setPitch(1);
                        }
                    }
                    //todo 分类下的文本
                    if (houseLibraryCategory2.getIsAddText().equals((byte) CategoryIsText.YEX.getStatus())){
                        HouseLibraryCategoryTextExample houseLibraryCategoryTextExample = new HouseLibraryCategoryTextExample();
                        houseLibraryCategoryTextExample.createCriteria()
                                .andLibraryCategoryIdEqualTo(houseLibraryCategory2.getId())
                                .andIsDeletedEqualTo((byte) 0);
                        List<HouseLibraryCategoryText> houseLibraryCategoryTexts =
                                houseLibraryCategoryTextMapper.selectByExampleWithBLOBs(houseLibraryCategoryTextExample);
                        selectLibraryCategory.setHouseLibraryCategoryTexts(houseLibraryCategoryTexts);
                    }
                    //TODO 四级分类
                    HouseLibraryCategoryExample houseLibraryCategoryExample3 = new HouseLibraryCategoryExample();
                    houseLibraryCategoryExample3.createCriteria()
                            .andIsDeletedEqualTo((byte) 0)
                            .andLibraryIdEqualTo(libraryId)
                            .andLevelEqualTo(4)
                            .andParentCategoryIdEqualTo(houseLibraryCategory2.getId());
                    List<HouseLibraryCategory> houseLibraryCategories3 =
                            houseLibraryCategoryMapper.selectByExample(houseLibraryCategoryExample3);
                    //todo 展示分类
                    List<SelectLibraryCategory> selectLibraryCategories3 = new ArrayList<>();
                    houseLibraryCategories3.forEach(houseLibraryCategory3 -> {
                        //
                        SelectLibraryCategory selectLibraryCategory3 = new SelectLibraryCategory();
                        selectLibraryCategory3.setCategoryId(houseLibraryCategory3.getId());
                        selectLibraryCategory3.setCategoryName(houseLibraryCategory3.getCategoryName());
                        selectLibraryCategory3.setLevel(houseLibraryCategory3.getLevel());
                        selectLibraryCategory3.setIsAddText(houseLibraryCategory3.getIsAddText());
                        selectLibraryCategory3.setIsLast(houseLibraryCategory3.getIsLast());
// todo 楼号查询是否选中
                        if (towerId !=null){
                            HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
                            houseTowerLibraryExample.createCriteria()
                                    .andIsDeletedEqualTo((byte) 0)
                                    .andTowerNoIdEqualTo(towerId)
                                    .andLibraryIdEqualTo(libraryId);
                            List<HouseTowerLibrary> houseTowerLibraries =
                                    houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
                            HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
                            houseTowerLibraryCategoryExample.createCriteria()
                                    .andIsDeletedEqualTo((byte) 0)
                                    .andTowerLibraryIdEqualTo(houseTowerLibraries.get(0).getId())
                                    .andLibraryCategoryIdEqualTo(houseLibraryCategory3.getId());
                            List<HouseTowerLibraryCategory> houseTowerLibraryCategories =
                                    houseTowerLibraryCategoryMapper.selectByExample(houseTowerLibraryCategoryExample);
                            if (houseTowerLibraryCategories.size()!=0){
                                selectLibraryCategory3.setPitch(0);
                            } else {
                                selectLibraryCategory3.setPitch(1);
                            }
                        }
                        //todo 分类下的文本
                        if (houseLibraryCategory3.getIsAddText().equals((byte) CategoryIsText.YEX.getStatus())){
                            HouseLibraryCategoryTextExample houseLibraryCategoryTextExample = new HouseLibraryCategoryTextExample();
                            houseLibraryCategoryTextExample.createCriteria()
                                    .andLibraryCategoryIdEqualTo(houseLibraryCategory3.getId())
                                    .andIsDeletedEqualTo((byte) 0);
                            List<HouseLibraryCategoryText> houseLibraryCategoryTexts =
                                    houseLibraryCategoryTextMapper.selectByExampleWithBLOBs(houseLibraryCategoryTextExample);
                            selectLibraryCategory.setHouseLibraryCategoryTexts(houseLibraryCategoryTexts);
                        }
                        selectLibraryCategories3.add(selectLibraryCategory3);
                    });
                    // todo 三级
                    selectLibraryCategory2.setSelectLibraryCategories(selectLibraryCategories3);
                    selectLibraryCategories2.add(selectLibraryCategory2);
                });
                //todo 二级
                selectLibraryCategory1.setSelectLibraryCategories(selectLibraryCategories2);
                selectLibraryCategories1.add(selectLibraryCategory1);
            });
            //todo 一级
            selectLibraryCategory.setSelectLibraryCategories(selectLibraryCategories1);
            selectLibraryCategories.add(selectLibraryCategory);
        });
        return builder.body(ResponseUtils.getResponseBody(selectLibraryCategories));
    }

    @ApiOperation(value = "添加分类", notes = "添加分类")
    @RequestMapping(value = "/addLibraryaddCategory", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "libraryId", value = "库id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "categoryName", value = "类名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "parentCategoryId", value = "上级类目id 顶级传0", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "text", value = "文本或者图片", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "文本或者图片类型", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> addLibraryaddCategory(
                                                            Integer libraryId,
                                                            String categoryName,
                                                            Integer parentCategoryId,
                                                            String[] text,
                                                            String[] Ttext,
                                                            String type,
                                                            HttpServletResponse response
                                                            ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (text==null){
            text = new String[]{};
        }
        if (Ttext==null){
            Ttext = new String[]{};
        }
        if (parentCategoryId!=0){
            //todo 添加的不是一级分类
            HouseLibraryCategory houseLibraryCategory = houseLibraryCategoryMapper.selectByPrimaryKey(parentCategoryId);
            //todo 添加的分类下有文本或者图片
            if (text.length!=0||Ttext.length != 0){
                //todo 添加的上级分类有文本或者图片，不能添加下级分类
                if (houseLibraryCategory.getIsAddText() == 0){
                    response.sendError(1000,"此分类下有富文本");
                    return builder.body(ResponseUtils.getResponseBody(1));
                }
            }
            if (houseLibraryCategory.getLevel() == 4){
                response.sendError(1001,"此分类是四级分类");
                return builder.body(ResponseUtils.getResponseBody(1));
            }
            HouseLibraryCategory houseLibraryCategory1 = new HouseLibraryCategory();
            houseLibraryCategory1.setLibraryId(houseLibraryCategory.getLibraryId());
            houseLibraryCategory1.setCategoryName(categoryName);
            houseLibraryCategory1.setParentCategoryId(parentCategoryId);
            houseLibraryCategory1.setLevel(houseLibraryCategory.getLevel()+1);
            houseLibraryCategory1.setIsDeleted((byte) 0);
            houseLibraryCategory1.setCreateDate(LocalDateTime.now());
            houseLibraryCategory1.setModifyDate(LocalDateTime.now());
            // todo 1没有添加了文本 1不是最后一个类目
            if (text.length != 0||Ttext.length != 0){
                // todo 0添加了文本 0最后一个类目
                houseLibraryCategory1.setIsAddText((byte) 0);
                houseLibraryCategory1.setIsLast((byte) 0);
            } else {
                houseLibraryCategory1.setIsAddText((byte) 1);
                houseLibraryCategory1.setIsLast((byte) 1);
            }
            houseLibraryCategoryMapper.insertSelective(houseLibraryCategory1);
            //todo 类目文本
            if (text.length != 0){
                for (String a : text){
                    HouseLibraryCategoryText houseLibraryCategoryText = new HouseLibraryCategoryText();
                    houseLibraryCategoryText.setLibraryCategoryId(houseLibraryCategory1.getId());
                    houseLibraryCategoryText.setText(a);
                    houseLibraryCategoryText.setType(TextTypeEnum.TEXT.getTextTypeType());
                    houseLibraryCategoryText.setIsDeleted((byte) 0);
                    houseLibraryCategoryText.setCreateDate(LocalDateTime.now());
                    houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
                    houseLibraryCategoryTextMapper.insertSelective(houseLibraryCategoryText);
                }
            }
            //todo 文件
            if (Ttext.length != 0){
                for (String a : Ttext){
                    HouseLibraryCategoryText houseLibraryCategoryText = new HouseLibraryCategoryText();
                    houseLibraryCategoryText.setLibraryCategoryId(houseLibraryCategory1.getId());
                    houseLibraryCategoryText.setText(a);
                    houseLibraryCategoryText.setType(TextTypeEnum.PDF.getTextTypeType());
                    houseLibraryCategoryText.setIsDeleted((byte) 0);
                    houseLibraryCategoryText.setCreateDate(LocalDateTime.now());
                    houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
                    houseLibraryCategoryTextMapper.insertSelective(houseLibraryCategoryText);
                }
            }

        } else {
            //todo 如果添加的是一级分类
            HouseLibraryCategory houseLibraryCategory = new HouseLibraryCategory();
            houseLibraryCategory.setLibraryId(libraryId);
            houseLibraryCategory.setCategoryName(categoryName);
            houseLibraryCategory.setParentCategoryId(0);
            houseLibraryCategory.setLevel(1);
            houseLibraryCategory.setIsDeleted((byte) 0);
            houseLibraryCategory.setCreateDate(LocalDateTime.now());
            houseLibraryCategory.setModifyDate(LocalDateTime.now());
            if (text.length != 0||Ttext.length != 0){
                // todo 0添加了文本 0最后一个类目
                houseLibraryCategory.setIsAddText((byte) 0);
                houseLibraryCategory.setIsLast((byte) 0);
            } else {
                // todo 1没有添加了文本 1不是最后一个类目
                houseLibraryCategory.setIsAddText((byte) 1);
                houseLibraryCategory.setIsLast((byte) 0);
            }
            // todo 添加类目
            houseLibraryCategoryMapper.insertSelective(houseLibraryCategory);
            //todo 类目文本
            if (text.length != 0){
                for (String a : text){
                    HouseLibraryCategoryText houseLibraryCategoryText = new HouseLibraryCategoryText();
                    houseLibraryCategoryText.setLibraryCategoryId(houseLibraryCategory.getId());
                    houseLibraryCategoryText.setText(a);
                    houseLibraryCategoryText.setType(TextTypeEnum.TEXT.getTextTypeType());
                    houseLibraryCategoryText.setIsDeleted((byte) 0);
                    houseLibraryCategoryText.setCreateDate(LocalDateTime.now());
                    houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
                    houseLibraryCategoryTextMapper.insertSelective(houseLibraryCategoryText);
                }
            }
            //todo 文件
            if (Ttext.length != 0){
                for (String a : Ttext){
                    HouseLibraryCategoryText houseLibraryCategoryText = new HouseLibraryCategoryText();
                    houseLibraryCategoryText.setLibraryCategoryId(houseLibraryCategory.getId());
                    houseLibraryCategoryText.setText(a);
                    houseLibraryCategoryText.setType(TextTypeEnum.PDF.getTextTypeType());
                    houseLibraryCategoryText.setIsDeleted((byte) 0);
                    houseLibraryCategoryText.setCreateDate(LocalDateTime.now());
                    houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
                    houseLibraryCategoryTextMapper.insertSelective(houseLibraryCategoryText);
                }
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改分类", notes = "添加分类")
    @RequestMapping(value = "/updateLibraryCategory", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "分类id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "categoryName", value = "类名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "parentCategoryId", value = "上级类目id 顶级传0", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "text", value = "文本或者图片", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "文本或者图片类型", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> updateLibraryCategory(
            Integer categoryId,
            Integer parentCategoryId,
            String categoryName,
            String[] text,
            String[] Ttext,
            String type,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (text==null){
            text = new String[]{};
        }
        if (Ttext==null){
            Ttext = new String[]{};
        }
            // todo 是否有下级类目，有不能添加文本和图
            HouseLibraryCategoryExample houseLibraryCategoryExample = new HouseLibraryCategoryExample();
            houseLibraryCategoryExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andParentCategoryIdEqualTo(categoryId);
            List<HouseLibraryCategory> houseLibraryCategories =
                    houseLibraryCategoryMapper.selectByExample(houseLibraryCategoryExample);
            if(text.length!=0||Ttext.length!=0){
                if (houseLibraryCategories.size()!=0){
                    response.sendError(1000,"此分类下还有分类,不能添加文本");
                    return builder.body(ResponseUtils.getResponseBody(1));
                }
            }

            //todo 上级分类是否有文本,有不能加下级分类
            HouseLibraryCategory houseLibraryCategory = new HouseLibraryCategory();
            if (parentCategoryId != 0){
                HouseLibraryCategory houseLibraryCategoryParent = houseLibraryCategoryMapper.selectByPrimaryKey(parentCategoryId);
                if (houseLibraryCategoryParent.getIsAddText() != 1){
                    response.sendError(1001,"上级分类下有文本,不能添加下级分类");
                    return builder.body(ResponseUtils.getResponseBody(1));
                }
                //todo 所添加的上级分类是否是四级分类
                if (houseLibraryCategoryParent.getLevel() == 4){
                    response.sendError(1004,"四级分类下不能添加分类");
                    return builder.body(ResponseUtils.getResponseBody(1));
                }
                houseLibraryCategory.setLevel(houseLibraryCategoryParent.getLevel()+1);
            } else {
                houseLibraryCategory.setLevel(1);
            }
            //todo 修改分类名称

            houseLibraryCategory.setParentCategoryId(parentCategoryId);
            houseLibraryCategory.setId(categoryId);
            houseLibraryCategory.setCategoryName(categoryName);
            houseLibraryCategory.setModifyDate(LocalDateTime.now());

            // todo 添加文本
        if (text.length!=0){
            //todo 设置此分类是否最后一个分类
            houseLibraryCategory.setIsAddText((byte) 0);
            houseLibraryCategory.setIsLast((byte) 0);
            for (String a : text){
                HouseLibraryCategoryText houseLibraryCategoryText = new HouseLibraryCategoryText();
                houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
                houseLibraryCategoryText.setType(TextTypeEnum.TEXT.getTextTypeType());
                houseLibraryCategoryText.setText(a);
                houseLibraryCategoryText.setLibraryCategoryId(categoryId);
                houseLibraryCategoryText.setCreateDate(LocalDateTime.now());
                houseLibraryCategoryText.setIsDeleted((byte) 0);
                houseLibraryCategoryTextMapper.insertSelective(houseLibraryCategoryText);
            }
        }
        //todo 添加文件
        if (Ttext.length!=0){
            //todo 设置此分类是否最后一个分类
            houseLibraryCategory.setIsAddText((byte) 0);
            houseLibraryCategory.setIsLast((byte) 0);
            for (String a : Ttext){
                HouseLibraryCategoryText houseLibraryCategoryText = new HouseLibraryCategoryText();
                houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
                houseLibraryCategoryText.setType(TextTypeEnum.PDF.getTextTypeType());
                houseLibraryCategoryText.setText(a);
                houseLibraryCategoryText.setLibraryCategoryId(categoryId);
                houseLibraryCategoryText.setCreateDate(LocalDateTime.now());
                houseLibraryCategoryText.setIsDeleted((byte) 0);
                houseLibraryCategoryTextMapper.insertSelective(houseLibraryCategoryText);
            }
        }
        if (text.length==0&&Ttext.length==0){
            HouseLibraryCategoryTextExample houseLibraryCategoryTextExample = new HouseLibraryCategoryTextExample();
            houseLibraryCategoryTextExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andLibraryCategoryIdEqualTo(houseLibraryCategory.getId());
            long a = houseLibraryCategoryTextMapper.countByExample(houseLibraryCategoryTextExample);
            if (a==0){
                houseLibraryCategory.setIsAddText((byte) 1);
            }
        }
        houseLibraryCategoryMapper.updateByPrimaryKeySelective(houseLibraryCategory);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除分类", notes = "删除分类")
    @RequestMapping(value = "/deleteLibraryCategory", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "分类id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteLibraryCategory(
            Integer categoryId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //todo 是否有下级分类
        HouseLibraryCategoryExample houseLibraryCategoryExample = new HouseLibraryCategoryExample();
        houseLibraryCategoryExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andParentCategoryIdEqualTo(categoryId);
        long a = houseLibraryCategoryMapper.countByExample(houseLibraryCategoryExample);
        if (a!=0){
            response.sendError(1000,"此分类下还有分类,不能添加文本");
            return builder.body(ResponseUtils.getResponseBody(1));
        }
        //todo 使用的分类总数
        HouseTowerLibraryCategoryExample houseTowerLibraryCategoryExample = new HouseTowerLibraryCategoryExample();
        houseTowerLibraryCategoryExample.createCriteria()
                .andLibraryCategoryIdEqualTo(categoryId)
                .andIsDeletedEqualTo((byte) 0);
        long houseTowerLibraryCategoriesnum =
                houseTowerLibraryCategoryMapper.countByExample(houseTowerLibraryCategoryExample);
        if (houseTowerLibraryCategoriesnum!=0){
            response.sendError(1001,"此分类使用了");
            return builder.body(ResponseUtils.getResponseBody(1));
        }
        HouseLibraryCategory houseLibraryCategory = new HouseLibraryCategory();
        houseLibraryCategory.setId(categoryId);
        houseLibraryCategory.setModifyDate(LocalDateTime.now());
        houseLibraryCategory.setIsDeleted((byte) 1);
        houseLibraryCategoryMapper.updateByPrimaryKeySelective(houseLibraryCategory);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除库分类下的文本或者图片", notes = "删除库分类下的文本或者图片")
    @RequestMapping(value = "/deleteLibraryCategoryText", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "categoryTextId", value = "分类文本id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteLibraryCategoryText(
            Integer categoryTextId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //todo 删除文本
        HouseLibraryCategoryText houseLibraryCategoryText = houseLibraryCategoryTextMapper.selectByPrimaryKey(categoryTextId);
        houseLibraryCategoryText.setId(categoryTextId);
        houseLibraryCategoryText.setIsDeleted((byte) 1);
        houseLibraryCategoryText.setModifyDate(LocalDateTime.now());
        houseLibraryCategoryTextMapper.updateByPrimaryKeySelective(houseLibraryCategoryText);
        HouseLibraryCategoryTextExample houseLibraryCategoryTextExample = new HouseLibraryCategoryTextExample();
        houseLibraryCategoryTextExample.createCriteria()
                .andLibraryCategoryIdEqualTo(houseLibraryCategoryText.getLibraryCategoryId())
                .andIsDeletedEqualTo((byte) 0);
        List<HouseLibraryCategoryText> houseLibraryCategoryTexts =
                houseLibraryCategoryTextMapper.selectByExampleWithBLOBs(houseLibraryCategoryTextExample);
        //todo 修改分类
        if (houseLibraryCategoryTexts.size() == 0){
            HouseLibraryCategory houseLibraryCategory = new HouseLibraryCategory();
            houseLibraryCategory.setId(houseLibraryCategoryText.getLibraryCategoryId());
            houseLibraryCategory.setIsAddText((byte) CategoryIsText.NO.getStatus());
            houseLibraryCategory.setModifyDate(LocalDateTime.now());
            houseLibraryCategoryMapper.updateByPrimaryKeySelective(houseLibraryCategory);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
//    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
//    //或直接指定时间间隔，例如：5秒
//    //@Scheduled(fixedRate=5000)
//    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
//
//    }
}
