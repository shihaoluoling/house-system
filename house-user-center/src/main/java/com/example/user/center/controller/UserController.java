package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseAuthMapper;
import com.example.user.center.dao.HouseUserMapper;
import com.example.user.center.manual.Login;
import com.example.user.center.manual.SelectUser;
import com.example.user.center.manual.User;
import com.example.user.center.manual.UserType;
import com.example.user.center.model.HouseAuth;
import com.example.user.center.model.HouseAuthExample;
import com.example.user.center.model.HouseUser;
import com.example.user.center.model.HouseUserExample;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shihao
 * @Title: UserController
 * @ProjectName Second-order-center
 * @Description: 前台用户
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/User")
@CrossOrigin
public class UserController {
    @Autowired
    private HouseAuthMapper houseAuthMapper;
    @Autowired
    private HouseUserMapper houseUserMapper;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public ResponseEntity<JSONObject> addLogin(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(name = "name") String name,
                                               @RequestParam(name = "phone") String phone,
                                               @RequestParam(name = "email") String email,
                                               @RequestParam(name = "userName") String userName,
                                               @RequestParam(name = "password") String password) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (name==null || phone == null || email == null || userName == null){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "不能为空");
            return builder.body(ResponseUtils.getResponseBody("不能为空"));
        }
        if (password == null){
            password = "123456";
        }
        //检查登录账号是否存在
        HouseAuthExample example = new HouseAuthExample();
        example.createCriteria().andAuthKeyEqualTo(userName)
                .andAuthTypeEqualTo(Login.WEBADMIN.getPaymentTypeName())
                .andIsDeletedEqualTo((byte) 0);
        List<HouseAuth> list = houseAuthMapper.selectByExample(example);
        if(!list.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "登录名");
            return builder.body(ResponseUtils.getResponseBody("登录名"));
        }

        HouseUser houseUser = new HouseUser();
        houseUser.setNickName(name);
        houseUser.setEmail(email);
        houseUser.setPhone(phone);
        houseUser.setUserType(UserType.USER.getPaymentTypeName());
        houseUser.setUserStatus((byte) 0);
        houseUser.setCreateDate(LocalDateTime.now());
        houseUser.setModifyDate(LocalDateTime.now());
        houseUser.setIsDeleted((byte) 0);
        houseUserMapper.insertSelective(houseUser);
        HouseAuth houseAuth = new HouseAuth();
        houseAuth.setUserId(houseUser.getId());
        houseAuth.setAuthType(Login.WEBADMIN.getPaymentTypeName());
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        houseAuth.setPassWord(bcryptPasswordEncoder.encode(password));
        houseAuth.setAuthKey(userName);
        houseAuth.setAuthStatus(String.valueOf(0));
        houseAuth.setCreateDate(LocalDateTime.now());
        houseAuth.setModifyDate(LocalDateTime.now());
        houseAuth.setIsDeleted((byte) 0);
        houseAuthMapper.insertSelective(houseAuth);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @RequestMapping(value = "/userSelect", method = RequestMethod.GET)
    @ApiOperation(value = "用户登录查询", notes = "用户登录查询")
    public ResponseEntity<JSONObject> userSelect(
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HouseUserExample houseUserExample = new HouseUserExample();
        houseUserExample.createCriteria().andUserTypeEqualTo(UserType.USER.getPaymentTypeName())
                .andIsDeletedEqualTo((byte) 0);
        List<HouseUser> houseUsers =
                houseUserMapper.selectByExample(houseUserExample);
        List<User> users = new ArrayList<>();
        houseUsers.forEach(houseUser -> {
            User user = new User();
            user.setUserId(houseUser.getId());
            user.setEmail(houseUser.getEmail());
            user.setName(houseUser.getNickName());
            user.setPhone(houseUser.getPhone());
            HouseAuthExample houseAuthExample = new HouseAuthExample();
            houseAuthExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andAuthTypeEqualTo(Login.WEBADMIN.getPaymentTypeName())
                    .andUserIdEqualTo(houseUser.getId());

            List<HouseAuth> houseAuth = houseAuthMapper.selectByExample(houseAuthExample);
            if (houseAuth.size() !=0){
                user.setAuthId(houseAuth.get(0).getId());
                user.setUserName(houseAuth.get(0).getAuthKey());
                user.setPassword(houseAuth.get(0).getPassWord());
            }
            users.add(user);
        });
        return builder.body(ResponseUtils.getResponseBody(users));
    }
}
