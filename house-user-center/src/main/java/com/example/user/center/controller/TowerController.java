package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseLibraryMapper;
import com.example.user.center.dao.HouseTowerLibraryMapper;
import com.example.user.center.dao.HouseTowerNoMapper;
import com.example.user.center.model.*;
import com.google.common.collect.Lists;
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

import javax.servlet.http.HttpServletResponse;
import java.nio.file.OpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @ApiOperation(value = "新建楼号", notes = "新建楼号")
    @RequestMapping(value = "/addTower", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "premisesId", value = "所属楼盘id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "towerNo", value = "楼号名称", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addTower(Integer premisesId,String towerNo) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseTowerNo houseTower = new HouseTowerNo();
        houseTower.setPremisesId(premisesId);
        houseTower.setTowerNo(towerNo);
        houseTower.setCreateDate(LocalDateTime.now());
        houseTower.setModifyDate(LocalDateTime.now());
        houseTower.setIsDeleted((byte) 0);
        houseTowerNoMapper.insertSelective(houseTower);
        return builder.body(ResponseUtils.getResponseBody(0));
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
        public ResponseEntity<JSONObject> towerLibrary(Integer[] libraryIds,Integer towerId,
                                                       HttpServletResponse response) throws Exception{
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
                if(libraryIds.length!=0){
                    HouseTowerLibraryExample houseTowerLibraryExample = new HouseTowerLibraryExample();
                    houseTowerLibraryExample.createCriteria()
                            .andIsDeletedEqualTo((byte) 0)
                            .andLibraryIdIn(Lists.newArrayList(libraryIds))
                            .andTowerNoIdEqualTo(towerId);
                    List<HouseTowerLibrary> houseTowerLibraries =
                            houseTowerLibraryMapper.selectByExample(houseTowerLibraryExample);
                    if (houseTowerLibraries.size()!=0){
                        response.sendError(501,"此楼号已经有此库了");
                        return builder.body(ResponseUtils.getResponseBody(1));
                    } else {
                        //添加
                            for (Integer libraryId : libraryIds){
                                HouseTowerLibrary houseTowerLibrary = new HouseTowerLibrary();
                                houseTowerLibrary.setLibraryId(libraryId);
                                houseTowerLibrary.setTowerNoId(towerId);
                                houseTowerLibrary.setCreateDate(LocalDateTime.now());
                                houseTowerLibrary.setModifyDate(LocalDateTime.now());
                                houseTowerLibrary.setIsDeleted((byte) 0);
                                houseTowerLibraryMapper.insertSelective(houseTowerLibrary);
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
}
