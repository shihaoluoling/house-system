package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameUserBookMapper;
import com.example.admin.center.model.GameUserBook;
import com.example.admin.center.model.GameUserBookExample;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shihao
 * @Title: BookController
 * @ProjectName Second-order-center
 * @Description: 通讯录
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Book")
@CrossOrigin
public class BookController {
    @Autowired
    private GameUserBookMapper gameUserBookMapper;

    @ApiOperation(value = "存通讯录", notes = "存通讯录")
    @RequestMapping(value = "/addUserBook", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addUserBook(
            String bookMessage,
            Integer userId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameUserBookExample gameUserBookExample = new GameUserBookExample();
        gameUserBookExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andUserIdEqualTo(userId);
        List<GameUserBook> gameUserBooks = gameUserBookMapper.selectByExample(gameUserBookExample);
        if (gameUserBooks.size()==0){
            GameUserBook gameUserBook = new GameUserBook();
            gameUserBook.setBookMessage(bookMessage);
            gameUserBook.setUserId(userId);
            gameUserBook.setCreateDate(LocalDateTime.now());
            gameUserBook.setModifyDate(LocalDateTime.now());
            gameUserBook.setIsDeleted((byte) 0);
            gameUserBookMapper.insertSelective(gameUserBook);
        } else {
            GameUserBook gameUserBook = gameUserBooks.get(0);
            gameUserBook.setId(gameUserBooks.get(0).getId());
            gameUserBook.setBookMessage(bookMessage);
            gameUserBook.setModifyDate(LocalDateTime.now());
            gameUserBookMapper.updateByPrimaryKeySelective(gameUserBook);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "查询通讯录", notes = "查询通讯录")
    @RequestMapping(value = "/selectUserBook", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectUserBook(
            Integer userId,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameUserBookExample gameUserBookExample = new GameUserBookExample();
        gameUserBookExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andIsDeletedEqualTo((byte) 0);
        List<GameUserBook> gameUserBooks = gameUserBookMapper.selectByExampleWithBLOBs(gameUserBookExample);
        if (gameUserBooks.size() !=0){
            return builder.body(ResponseUtils.getResponseBody(gameUserBooks.get(0)));
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
