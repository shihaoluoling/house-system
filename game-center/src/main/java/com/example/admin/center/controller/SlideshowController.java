package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameSlideshowMapper;
import com.example.admin.center.model.GameCenter;
import com.example.admin.center.model.GameSlideshow;
import com.example.admin.center.model.GameSlideshowExample;
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
 * @Title: SlideshowController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Slideshow")
@CrossOrigin
public class SlideshowController {
    @Autowired
    private GameSlideshowMapper gameSlideshowMapper;
    @ApiOperation(value = "添加轮播图", notes = "添加轮播图")
    @RequestMapping(value = "/addSlideshow", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "file", value = "文件", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "link", value = "跳转", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addSlideshow(String file
            , String link,
                                              HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameSlideshow gameSlideshow = new GameSlideshow();
        gameSlideshow.setFile(file);
        gameSlideshow.setLink(link);
        gameSlideshow.setCreateDate(LocalDateTime.now());
        gameSlideshow.setModifyDate(LocalDateTime.now());
        gameSlideshow.setIsDeleted((byte) 0);
        gameSlideshowMapper.insertSelective(gameSlideshow);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除轮播图", notes = "删除轮播图")
    @RequestMapping(value = "/deleteSlideshow", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "轮播图id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteSlideshow(Integer id,
                                                   HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameSlideshow gameSlideshow = new GameSlideshow();
        gameSlideshow.setId(id);
        gameSlideshow.setModifyDate(LocalDateTime.now());
        gameSlideshow.setIsDeleted((byte) 1);
        gameSlideshowMapper.updateByPrimaryKeySelective(gameSlideshow);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "查询轮播图", notes = "查询轮播图")
    @RequestMapping(value = "/selectSlideshow", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectSlideshow(
                                                      HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameSlideshowExample gameSlideshowExample = new GameSlideshowExample();
        gameSlideshowExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        List<GameSlideshow> gameSlideshows = gameSlideshowMapper.selectByExample(gameSlideshowExample);
        return builder.body(ResponseUtils.getResponseBody(gameSlideshows));
    }
}
