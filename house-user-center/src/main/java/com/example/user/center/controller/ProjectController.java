package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseProjectMapper;
import com.example.user.center.model.HousePlate;
import com.example.user.center.model.HouseProject;
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

/**
 * @author shihao
 * @Title: ProjectController
 * @ProjectName Second-order-center
 * @Description: 项目
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/Login")
@Api
public class ProjectController {
//项目
    @Autowired
    private HouseProjectMapper houseProjectMapper;

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "添加项目", notes = "添加项目")
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectName", value = "项目名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "developersName", value = "开发商名称", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> addProject(
            @RequestParam(name = "projectName") String projectName,
            @RequestParam(name = "developersName") String developersName,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseProject houseProject  = new HouseProject();
        houseProject.setProjectName(projectName);
        houseProject.setDevelopersName(developersName);
        houseProject.setCreateDate(LocalDateTime.now());
        houseProject.setModifyDate(LocalDateTime.now());
        houseProject.setIsDeleted((byte) 0);
        houseProjectMapper.insertSelective(houseProject);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "修改项目", notes = "修改项目")
    @RequestMapping(value = "/updateProject", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectName", value = "项目名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "developersName", value = "开发商名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateProject(
            @RequestParam(name = "projectName") String projectName,
            @RequestParam(name = "developersName") String developersName,
            @RequestParam(name = "projectId") Integer projectId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseProject houseProject  = new HouseProject();
        houseProject.setId(projectId);
        houseProject.setProjectName(projectName);
        houseProject.setDevelopersName(developersName);
        houseProject.setModifyDate(LocalDateTime.now());
        houseProjectMapper.insertSelective(houseProject);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    //
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "删除项目", notes = "删除项目")
    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectId", value = "项目id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteProject(
            @RequestParam(name = "projectId") Integer projectId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseProject houseProject  = new HouseProject();
        houseProject.setId(projectId);
        houseProject.setIsDeleted((byte) 1);
        houseProject.setModifyDate(LocalDateTime.now());
        houseProjectMapper.updateByPrimaryKeySelective(houseProject);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
