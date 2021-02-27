package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseExploitMapper;
import com.example.user.center.dao.HouseLandMapper;
import com.example.user.center.dao.HousePremisesMapper;
import com.example.user.center.dao.HouseProjectMapper;
import com.example.user.center.manual.SelectProject;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//土地
    @Autowired
    private HouseLandMapper houseLandMapper;
@Autowired
    private HousePremisesMapper housePremisesMapper;
    @Autowired
    private HouseExploitMapper houseExploitMapper;
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiOperation(value = "添加项目", notes = "添加项目")
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "projectName", value = "项目名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "developersName", value = "开发商名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "exploitId", value = "开发商id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addProject(
            @RequestParam(name = "projectName") String projectName,
            @RequestParam(name = "developersName") String developersName,
            @RequestParam(name = "exploitId") Integer exploitId,
            @RequestParam(name = "remark") String remark,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseProject houseProject  = new HouseProject();
        houseProject.setExploitId(exploitId);
        houseProject.setProjectName(projectName);
        houseProject.setDevelopersName(developersName);
        houseProject.setCreateDate(LocalDateTime.now());
        houseProject.setModifyDate(LocalDateTime.now());
        houseProject.setIsDeleted((byte) 0);
        houseProject.setRemark(remark);
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
            @RequestParam(name = "exploitId") Integer exploitId,
            @RequestParam(name = "remark") String remark,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseProject houseProject  = new HouseProject();
        houseProject.setExploitId(exploitId);
        houseProject.setId(projectId);
        houseProject.setProjectName(projectName);
        houseProject.setDevelopersName(developersName);
        houseProject.setModifyDate(LocalDateTime.now());
        houseProject.setRemark(remark);
        houseProjectMapper.updateByPrimaryKeySelective(houseProject);
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

    @ApiOperation(value = "查询项目信息", notes = "查询项目信息")
    @RequestMapping(value = "/selectProject", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectProject(
            String projectName,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseProjectExample houseProjectExample = new HouseProjectExample();
        HouseProjectExample.Criteria criteria =
        houseProjectExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        if (projectName != null){
            criteria.andProjectNameLike("%"+projectName+"%");
        }
        List<HouseProject> houseProjects =
                houseProjectMapper.selectByExample(houseProjectExample);
        List<SelectProject> selectProjects = new ArrayList<>();
        houseProjects.forEach(houseProject -> {
            SelectProject selectProject = new SelectProject();
            selectProject.setRemark(houseProject.getRemark());
            selectProject.setExploitid(houseProject.getExploitId());
            if (houseProject.getExploitId()!=null){
                HouseExploit houseExploit = houseExploitMapper.selectByPrimaryKey(houseProject.getExploitId());
                selectProject.setExploitName(houseExploit.getExploitName());
            }
            selectProject.setProjectName(houseProject.getProjectName());
            selectProject.setProjectId(houseProject.getId());
            //土地
            HouseLandExample houseLandExample = new HouseLandExample();
            houseLandExample.createCriteria().andIsDeletedEqualTo((byte) 0)
                    .andProjectIdEqualTo(houseProject.getId());
            List<HouseLand> houseLands =
                    houseLandMapper.selectByExample(houseLandExample);
            //土地
            if (houseLands.size()!=0){
                //受让方
//                List<String> developers = houseLands.stream().map(HouseLand::getTransfer).collect(Collectors.toList());
//                selectProject.setDevelopersName(developers);
                Map<String, List<String>> map = new HashMap<>();
                houseLands.forEach(houseLand -> {
                    //某一个土地下的所有楼盘
                    HousePremisesExample housePremisesExample = new HousePremisesExample();
                    housePremisesExample.createCriteria()
                            .andIsDeletedEqualTo((byte) 0)
                            .andLandIdEqualTo(houseLand.getId());
                    List<HousePremises> housePremises = housePremisesMapper.selectByExample(housePremisesExample);
                    //楼盘筛选
                    if (housePremises.size()!=0){
                        //开发商名称
                        List<String> developers1 = housePremises.stream().map(HousePremises::getDevelopersName).collect(Collectors.toList());
                        selectProject.setDevelopersName(developers1);
                        List<String> PremisesName =  housePremises.stream()
                                .map(HousePremises::getPremisesName).collect(Collectors.toList());
                        map.put(houseLand.getLandName(),PremisesName);
                    }
                });
                selectProject.setLandType(map);
            }

            selectProjects.add(selectProject);
        });
        return builder.body(ResponseUtils.getResponseBody(selectProjects));
    }
}
