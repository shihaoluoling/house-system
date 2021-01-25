package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameCenterMapper;
import com.example.admin.center.manual.JSON.SelectGame;
import com.example.admin.center.model.GameCenter;
import com.example.admin.center.model.GameCenterExample;
import com.example.admin.center.model.GameFormItem;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: GameController
 * @ProjectName Second-order-center
 * @Description: 游戏
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Game")
@CrossOrigin
public class GameController {
    @Autowired
    private GameCenterMapper gameCenterMapper;

    @ApiOperation(value = "添加游戏", notes = "添加游戏")
    @RequestMapping(value = "/addGame", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "游戏名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "file", value = "文件", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "formId", value = "表单id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addGame(String name
            , String file
            , Integer formId,
                                                  HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameCenter gameCenter = new GameCenter();
        gameCenter.setName(name);
        gameCenter.setFile(file);
        gameCenter.setFormId(formId);
        gameCenter.setCreateTime(LocalDateTime.now());
        gameCenter.setModifyTime(LocalDateTime.now());
        gameCenter.setIsDeleted((short) 0);
        gameCenterMapper.insertSelective(gameCenter);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改游戏", notes = "修改游戏")
    @RequestMapping(value = "/updateGame", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", value = "游戏名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "file", value = "文件", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "formId", value = "表单id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "游戏id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateGame(String name
            , String file
            , Integer formId
            , Integer id
            ,HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameCenter gameCenter = new GameCenter();
        gameCenter.setId(id);
        gameCenter.setName(name);
        gameCenter.setFile(file);
        gameCenter.setFormId(formId);
        gameCenter.setModifyTime(LocalDateTime.now());
        gameCenterMapper.updateByPrimaryKeySelective(gameCenter);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除游戏", notes = "删除游戏")
    @RequestMapping(value = "/deleteGame", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "游戏id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteGame(Integer id
            ,HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameCenter gameCenter = new GameCenter();
        gameCenter.setIsDeleted((short) 1);
        gameCenter.setId(id);
        gameCenter.setModifyTime(LocalDateTime.now());
        gameCenterMapper.updateByPrimaryKeySelective(gameCenter);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询游戏", notes = "查询游戏")
    @RequestMapping(value = "/selectGame", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectGame(            Integer start,
                                                             Integer num
            ,HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameCenterExample gameCenterExample = new GameCenterExample();
        gameCenterExample.createCriteria()
                .andIsDeletedEqualTo((short) 0);
        long nums = gameCenterMapper.countByExample(gameCenterExample);
        SelectGame selectGame = new SelectGame();
        selectGame.setNums(nums);
        if (start!=null && num!=null){
            //mysql 从0开始算数据,前端从1开始
            start -= 1;
            //转化成分页从第start开始,num条
            start = start*num;
            gameCenterExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        List<GameCenter> selectGameList = gameCenterMapper.selectByExample(gameCenterExample);
        selectGame.setGameCenters(selectGameList);
        return builder.body(ResponseUtils.getResponseBody(selectGame));
    }
}
