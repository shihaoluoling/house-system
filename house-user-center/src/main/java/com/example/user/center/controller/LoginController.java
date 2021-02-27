package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseAuthMapper;
import com.example.user.center.dao.HouseSystemMapper;
import com.example.user.center.dao.HouseUserMapper;
import com.example.user.center.manual.Login;
import com.example.user.center.manual.SelectUser;
import com.example.user.center.manual.UserType;
import com.example.user.center.model.*;
import com.example.user.utils.Message;
import com.house.common.utils.Encrypt;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shihao
 * @Title: LoginController
 * @ProjectName Second-order-center
 * @Description: 登录
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/Login")
@Api
public class LoginController {
    @Autowired
    private HouseAuthMapper houseAuthMapper;
    @Autowired
    JedisPool jedisPool;
    //用户
    @Autowired
    private HouseUserMapper houseUserMapper;
    @Autowired
    HouseSystemMapper houseSystemMapper;
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录")
    @RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> adminLogin(@RequestParam String userName, @RequestParam String password, HttpServletResponse response) throws JSONException, IOException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HouseAuthExample houseAuthExample = new HouseAuthExample();
        houseAuthExample.createCriteria().andAuthTypeEqualTo(Login.ADMIN.getPaymentTypeName())
                .andAuthKeyEqualTo(userName)
                .andAuthStatusEqualTo(String.valueOf(0))
                .andIsDeletedEqualTo((byte) 0);
        List<HouseAuth> houseAuths =
                houseAuthMapper.selectByExample(houseAuthExample);
        BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
        boolean a = bcp.matches(password,houseAuths.get(0).getPassWord());
        if (a){
            Encrypt encrypt = new Encrypt();
            String token = encrypt.getToken(true, houseAuths.get(0).getUserId(), "boss", 0);
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("userId",houseAuths.get(0).getUserId());
            map.put("name",userName);
            return builder.body(ResponseUtils.getResponseBody(map));
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "账号或者密码错误");
            return builder.body(ResponseUtils.getResponseBody(1));
        }
    }
    @RequestMapping(path = "/code", method = RequestMethod.POST)
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> code(@RequestParam String phone) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (!StringUtils.isEmpty(phone)) {
            String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
            Pattern p = Pattern.compile(s2);
            Matcher m = p.matcher(phone);
            boolean b = m.matches();
            if (b) {
                Integer code = Message.sendSms(phone);
                Jedis jedis = new Jedis();
                jedis = jedisPool.getResource();
                jedis.set(phone+"house", String.valueOf(code));
                jedis.expire(phone, 300);
                if (jedis != null) {
                    jedis.close();
                }
                return builder.body(ResponseUtils.getResponseBody(code));
            }
            return builder.body(ResponseUtils.getResponseBody("手机号有误"));
        } else {
            return builder.body(ResponseUtils.getResponseBody("请输入手机号"));
        }
    }
    //
    @RequestMapping(value = "/phoneLogin", method = RequestMethod.GET)
    @ApiOperation(value = "手机号登录", notes = "手机号登录")
    public ResponseEntity<JSONObject> phoneLogin(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(name = "phone") String phone,
                                            @RequestParam(name = "passwd") String passwd) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if(StringUtils.isEmpty(phone)) {
            return builder.body(ResponseUtils.getResponseBody("手机号为null"));
        }
        HouseAuthExample example = new HouseAuthExample();
        example.createCriteria().andAuthKeyEqualTo(phone)
        .andIsDeletedEqualTo((byte) 0)
                .andAuthTypeEqualTo(Login.PHONE.getPaymentTypeName())
        .andAuthStatusEqualTo(String.valueOf(0));
        List<HouseAuth> list = houseAuthMapper.selectByExample(example);
        if(list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody(-1));
        }else {
            Jedis jedis = new Jedis();
            jedis = jedisPool.getResource();
            String code = jedis.get(phone+"house");
            System.out.println("code+++++++++++"+code);
            System.out.println("passwd+++++++++++"+passwd);
            if(jedis!=null) {
                jedis.close();
            }
            if(StringUtils.isEmpty(passwd)) {
                return builder.body(ResponseUtils.getResponseBody("验证码为null"));
            }else {
                if(passwd.equals(code)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("userId", list.get(0).getUserId());
                    Encrypt encrypt = new Encrypt();
                    String token = encrypt.getToken(true, list.get(0).getUserId(), "boss", 0);
                    map.put("token",token);
                    HouseAuthExample example1 = new HouseAuthExample();
                    example1.createCriteria()
                            .andUserIdEqualTo(list.get(0).getUserId())
                            .andAuthTypeEqualTo(Login.ADMIN.getPaymentTypeName())
                            .andIsDeletedEqualTo((byte) 0)
                            .andAuthStatusEqualTo(String.valueOf(0));
                    List<HouseAuth> list1 = houseAuthMapper.selectByExample(example1);
                    map.put("name",list1.get(0).getAuthKey());
                    return builder.body(ResponseUtils.getResponseBody(map));
                }else {
                    return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
                }
            }
        }
    }

    @RequestMapping(value = "/phonePassword", method = RequestMethod.POST)
    @ApiOperation(value = "手机号找回密码", notes = "手机号找回密码")
    public ResponseEntity<JSONObject> phonePassword(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(name = "phone") String phone,
                                            @RequestParam(name = "newPasswd") String newPasswd,
                                            @RequestParam(name = "passwd") String passwd) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if(StringUtils.isEmpty(phone)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "手机号为null");
            return builder.body(ResponseUtils.getResponseBody("手机号为null"));
        }
        HouseAuthExample example = new HouseAuthExample();
        example.createCriteria().andAuthKeyEqualTo(phone)
                .andAuthTypeEqualTo(Login.PHONE.getPaymentTypeName())
                .andIsDeletedEqualTo((byte) 0)
        .andAuthStatusEqualTo(String.valueOf(0));
        List<HouseAuth> list = houseAuthMapper.selectByExample(example);
        if(list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody(-1));
        }else {
            Jedis jedis = new Jedis();
            jedis = jedisPool.getResource();
            String code = jedis.get(phone+"house");
            System.out.println("code+++++++++++"+code);
            System.out.println("passwd+++++++++++"+passwd);
            if(jedis!=null) {
                jedis.close();
            }
            if(StringUtils.isEmpty(passwd)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "验证码为null");
                return builder.body(ResponseUtils.getResponseBody("验证码为null"));
            }else {
                if(passwd.equals(code)) {
                    HouseAuthExample example1 = new HouseAuthExample();
                    example1.createCriteria()
                            .andUserIdEqualTo(list.get(0).getUserId())
                            .andAuthTypeEqualTo(Login.ADMIN.getPaymentTypeName())
                            .andIsDeletedEqualTo((byte) 0)
                            .andAuthStatusEqualTo(String.valueOf(0));
                    List<HouseAuth> list1 = houseAuthMapper.selectByExample(example1);
                    HouseAuth houseAuth = list1.get(0);
                    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
                    houseAuth.setPassWord(bcryptPasswordEncoder.encode(newPasswd));
                    houseAuthMapper.updateByPrimaryKeySelective(houseAuth);
                    return builder.body(ResponseUtils.getResponseBody(0));
                }else {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "验证码不正确");
                    return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
                }
            }
        }
    }

    //
    @RequestMapping(value = "/addLogin", method = RequestMethod.POST)
    @ApiOperation(value = "创建账号", notes = "创建账号")
    public ResponseEntity<JSONObject> addLogin(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestParam(name = "phone") String phone,
                                                    @RequestParam(name = "userName") String userName,
                                                    @RequestParam(name = "password") String password) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        //检查手机号是否存在
        HouseAuthExample example = new HouseAuthExample();
        example.createCriteria().andAuthKeyEqualTo(phone)
                .andAuthTypeEqualTo(Login.PHONE.getPaymentTypeName())
                .andIsDeletedEqualTo((byte) 0);
        List<HouseAuth> list = houseAuthMapper.selectByExample(example);
        if(!list.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "手机号存在");
            return builder.body(ResponseUtils.getResponseBody("手机号存在"));
        }

        //检查用户名是否存在
        HouseAuthExample houseAuthExample = new HouseAuthExample();
        houseAuthExample.createCriteria().andAuthTypeEqualTo(Login.ADMIN.getPaymentTypeName())
                .andAuthKeyEqualTo(userName)
                .andAuthStatusEqualTo(String.valueOf(0))
                .andIsDeletedEqualTo((byte) 0);
        List<HouseAuth> houseAuths =
                houseAuthMapper.selectByExample(houseAuthExample);
        if(!houseAuths.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "用户名存在");
            return builder.body(ResponseUtils.getResponseBody("用户名存在"));
        }
        HouseUser houseUser = new HouseUser();
        houseUser.setPhone(phone);
        houseUser.setUserType(UserType.ADMIN.getPaymentTypeName());
        houseUser.setUserStatus((byte) 0);
        houseUser.setCreateDate(LocalDateTime.now());
        houseUser.setModifyDate(LocalDateTime.now());
        houseUser.setIsDeleted((byte) 0);
        houseUserMapper.insertSelective(houseUser);
        HouseAuth houseAuth = new HouseAuth();
        houseAuth.setUserId(houseUser.getId());
        houseAuth.setAuthType(Login.PHONE.getPaymentTypeName());
        houseAuth.setAuthKey(phone);
        houseAuth.setAuthStatus(String.valueOf(0));
        houseAuth.setCreateDate(LocalDateTime.now());
        houseAuth.setModifyDate(LocalDateTime.now());
        houseAuth.setIsDeleted((byte) 0);
        houseAuthMapper.insertSelective(houseAuth);
        houseAuth.setAuthKey(userName);
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        houseAuth.setPassWord(bcryptPasswordEncoder.encode(password));
        houseAuth.setAuthType(Login.ADMIN.getPaymentTypeName());
        houseAuthMapper.insertSelective(houseAuth);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    //
    @RequestMapping(value = "/forbid", method = RequestMethod.POST)
    @ApiOperation(value = "禁用", notes = "禁用")
    public ResponseEntity<JSONObject> forbid(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(name = "userId") Integer userId
                                             ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HouseUser houseUser = new HouseUser();
        houseUser.setId(userId);
        houseUser.setUserStatus((byte) 1);
        houseUser.setModifyDate(LocalDateTime.now());
        houseUserMapper.updateByPrimaryKeySelective(houseUser);
        HouseAuthExample houseAuthExample = new HouseAuthExample();
        houseAuthExample.createCriteria().andUserIdEqualTo(userId)
                .andIsDeletedEqualTo((byte) 0);
        List<HouseAuth> houseAuths = houseAuthMapper.selectByExample(houseAuthExample);
        String a;
        if ("0".equals(houseAuths.get(0).getAuthStatus())){
            a = "1";
        } else {
            a = "0";
        }
        houseAuths.forEach(houseAuth -> {
            houseAuth.setAuthStatus(a);
            houseAuthMapper.updateByPrimaryKeySelective(houseAuth);
        });
        return builder.body(ResponseUtils.getResponseBody(0));
    }


    //
    @RequestMapping(value = "/system", method = RequestMethod.POST)
    @ApiOperation(value = "系统设置", notes = "系统设置")
    public ResponseEntity<JSONObject> system(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(name = "name") String name,
                                             @RequestParam(name = "login") String login,
                                             @RequestParam(name = "BottomCopyright") String BottomCopyright
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HouseSystem houseSystem = new HouseSystem();
        houseSystem.setId(1);
        houseSystem.setSystemName(name);
        houseSystem.setLogo(login);
        houseSystem.setBottomCopyright(BottomCopyright);
        houseSystem.setModifyDate(LocalDateTime.now());
        houseSystem.setIsDeleted((byte) 0);
        houseSystemMapper.updateByPrimaryKeySelective(houseSystem);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @RequestMapping(value = "/systemSelect", method = RequestMethod.POST)
    @ApiOperation(value = "系统设置查詢", notes = "系统设置查詢")
    public ResponseEntity<JSONObject> systemSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HouseSystemExample houseSystemExample = new HouseSystemExample();
        houseSystemExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0);
        List<HouseSystem> houseSystems = houseSystemMapper.selectByExample(houseSystemExample);
        return builder.body(ResponseUtils.getResponseBody(houseSystems));
    }
    @RequestMapping(value = "/loginSelect", method = RequestMethod.GET)
    @ApiOperation(value = "登录查询", notes = "登录查询")
    public ResponseEntity<JSONObject> loginSelect(
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HouseUserExample houseUserExample = new HouseUserExample();
        houseUserExample.createCriteria().andUserTypeEqualTo(UserType.ADMIN.getPaymentTypeName())
                .andIsDeletedEqualTo((byte) 0);
        List<HouseUser> houseUsers =
                houseUserMapper.selectByExample(houseUserExample);
        List<SelectUser> selectUsers = new ArrayList<>();
        houseUsers.forEach(houseUser -> {
            SelectUser selectUser = new SelectUser();
            selectUser.setUserId(houseUser.getId());
            selectUser.setEmail(houseUser.getEmail());
            HouseAuthExample houseAuthExample = new HouseAuthExample();
            houseAuthExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andAuthTypeEqualTo(Login.PHONE.getPaymentTypeName())
                    .andUserIdEqualTo(houseUser.getId());

            List<HouseAuth> houseAuth = houseAuthMapper.selectByExample(houseAuthExample);
            if (houseAuth.size() !=0){
                selectUser.setPhone(houseAuth.get(0).getAuthKey());
                selectUser.setState(houseAuth.get(0).getAuthStatus());
            }
            houseAuthExample.clear();
            houseAuthExample.createCriteria()
                    .andIsDeletedEqualTo((byte) 0)
                    .andAuthTypeEqualTo(Login.ADMIN.getPaymentTypeName())
                    .andUserIdEqualTo(houseUser.getId());
            houseAuth = houseAuthMapper.selectByExample(houseAuthExample);
            if (houseAuth.size() !=0){
                selectUser.setUserName(houseAuth.get(0).getAuthKey());
            }
            selectUsers.add(selectUser);
        });
        return builder.body(ResponseUtils.getResponseBody(selectUsers));
    }

    @RequestMapping(value = "/loginDelete", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户登录", notes = "删除登录")
    public ResponseEntity<JSONObject> loginDelete(
            Integer userId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();

        //用户
        HouseUser houseUser = new HouseUser();
        houseUser.setId(userId);
        houseUser.setModifyDate(LocalDateTime.now());
        houseUser.setIsDeleted((byte) 1);
        houseUserMapper.updateByPrimaryKeySelective(houseUser);
        //授权
        HouseAuthExample houseAuthExample = new HouseAuthExample();
        houseAuthExample.createCriteria().andUserIdEqualTo(userId)
                .andIsDeletedEqualTo((byte) 0);
        HouseAuth houseAuth = new HouseAuth();
        houseAuth.setIsDeleted((byte) 1);
        houseAuth.setModifyDate(LocalDateTime.now());
        houseAuthMapper.updateByExampleSelective(houseAuth,houseAuthExample);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
