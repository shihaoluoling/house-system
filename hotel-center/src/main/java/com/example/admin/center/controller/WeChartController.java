package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.config.WxLoginConfig;
import com.example.admin.center.dao.HotelAuthMapper;
import com.example.admin.center.dao.HotelProtocolMapper;
import com.example.admin.center.dao.HotelUserMapper;
import com.example.admin.center.manual.Enum.UserType;
import com.example.admin.center.manual.JSON.SelectAuth;
import com.example.admin.center.manual.JSON.SelectUser;
import com.example.admin.center.model.*;
import com.house.common.utils.Encrypt;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author shihao
 * @Title: WeChartController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/WeChart")
@CrossOrigin
public class WeChartController {
    @Autowired
    private HotelAuthMapper hotelAuthMapper;
    @Autowired
    private HotelUserMapper hotelUserMapper;
    @Autowired
    private HotelProtocolMapper hotelProtocolMapper;
    @ApiOperation(value = "添加后台登录", notes = "添加后台登录")
    @RequestMapping(value = "/addLogin", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addLogin(String username,String password,
                                               HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //验证用户名是否存在
        HotelAuthExample hotelAuthExample = new HotelAuthExample();
        hotelAuthExample.createCriteria()
                .andAuthKeyEqualTo(username)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.AUTH.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        List<HotelAuth> hotelAuths =
                hotelAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() != 0){
            response.sendError(1000,"用户名存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            HotelUser hotelUser = new HotelUser();
            hotelUser.setRealName(username);
            hotelUser.setCreateDate(LocalDateTime.now());
            hotelUser.setModifyDate(LocalDateTime.now());
            hotelUser.setIsDeleted((byte) 0);
            hotelUser.setUserType(UserType.AUTH.getAuthType());
            hotelUserMapper.insertSelective(hotelUser);
            HotelAuth hotelAuth = new HotelAuth();
            hotelAuth.setUserId(hotelUser.getId());
            hotelAuth.setAuthKey(username);
            hotelAuth.setAuthStatus((byte) 0);
            hotelAuth.setAuthType(WxLoginConfig.AuthType.AUTH.getAuthType());
            hotelAuth.setCreateDate(LocalDateTime.now());
            hotelAuth.setModifyDate(LocalDateTime.now());
            hotelAuth.setIsDeleted((byte) 0);
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            hotelAuth.setPassword(bcryptPasswordEncoder.encode(password));
            hotelAuthMapper.insertSelective(hotelAuth);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "修改后台登录", notes = "修改后台登录")
    @RequestMapping(value = "/updateLogin", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "AuthId", value = "授权id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateLogin(String username
            ,Integer AuthId
            ,String password,
                                               HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //验证用户名是否存在
        HotelAuthExample hotelAuthExample = new HotelAuthExample();
        hotelAuthExample.createCriteria()
                .andIdEqualTo(AuthId)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.AUTH.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        List<HotelAuth> hotelAuths =
                hotelAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() == 0){
            response.sendError(1000,"用户不存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            HotelAuth hotelAuth = new HotelAuth();
            hotelAuth.setId(AuthId);
            hotelAuth.setAuthKey(username);
            hotelAuth.setModifyDate(LocalDateTime.now());
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            hotelAuth.setPassword(bcryptPasswordEncoder.encode(password));
            hotelAuthMapper.updateByPrimaryKeySelective(hotelAuth);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "删除后台登录", notes = "删除后台登录")
    @RequestMapping(value = "/deleteLogin", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "AuthId", value = "授权id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteLogin(
            Integer AuthId,
                                                  HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //验证用户名是否存在
        HotelAuthExample hotelAuthExample = new HotelAuthExample();
        hotelAuthExample.createCriteria()
                .andIdEqualTo(AuthId)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.AUTH.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        List<HotelAuth> hotelAuths =
                hotelAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() == 0){
            response.sendError(1000,"用户不存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            HotelAuth hotelAuth = new HotelAuth();
            hotelAuth.setModifyDate(LocalDateTime.now());
            hotelAuth.setId(AuthId);
            hotelAuth.setIsDeleted((byte) 1);
            hotelAuthMapper.updateByPrimaryKeySelective(hotelAuth);
            HotelUser hotelUser = new HotelUser();
            hotelUser.setId(hotelAuths.get(0).getUserId());
            hotelUser.setModifyDate(LocalDateTime.now());
            hotelUser.setIsDeleted((byte) 1);
            hotelUserMapper.updateByPrimaryKeySelective(hotelUser);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "后台查询登录", notes = "后台查询登录")
    @RequestMapping(value = "/selectAuth", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectAuth(
            Integer start,
            Integer num
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        SelectAuth selectAuths = new SelectAuth();
        HotelAuthExample hotelAuthExample = new HotelAuthExample();
        hotelAuthExample.createCriteria()
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.AUTH.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        //总数
        Long nums = hotelAuthMapper.countByExample(hotelAuthExample);
        selectAuths.setNums(nums);
        if (start!=null && num!=null) {
            start -= 1;
            start = start*10;
            hotelAuthExample.setOrderByClause("id desc limit " + start + "," + num);
        }
        List<HotelAuth> hotelAuths =
                hotelAuthMapper.selectByExample(hotelAuthExample);
        selectAuths.setHotelAuths(hotelAuths);
        return builder.body(ResponseUtils.getResponseBody(selectAuths));
    }
    @ApiOperation(value = "小程序用户查询", notes = "小程序用户查询")
    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectUser(
            Integer start,
            Integer num
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        SelectUser selectUser = new SelectUser();
        HotelUserExample hotelUserExample = new HotelUserExample();
        hotelUserExample.createCriteria()
                .andUserTypeEqualTo(UserType.WECHART.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        //总数
        Long nums = hotelUserMapper.countByExample(hotelUserExample);
        selectUser.setNums(nums);
        if (start!=null && num!=null) {
            start -= 1;
            start = start*10;
            hotelUserExample.setOrderByClause("id desc limit " + start + "," + num);
        }
        List<HotelUser> hotelUsers =
                hotelUserMapper.selectByExample(hotelUserExample);
        selectUser.setHotelUsers(hotelUsers);
        return builder.body(ResponseUtils.getResponseBody(selectUser));
    }
    @ApiOperation(value = "登录后台", notes = "登录后台")
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> Login(
            String username,
            String password,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //验证用户名是否存在
        HotelAuthExample hotelAuthExample = new HotelAuthExample();
        hotelAuthExample.createCriteria()
                .andAuthKeyEqualTo(username)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.AUTH.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        List<HotelAuth> hotelAuths =
                hotelAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() == 0){
            response.sendError(1000,"用户名或密码错误");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
            boolean a = bcp.matches(password,hotelAuths.get(0).getPassword());
            if (a){
                Encrypt encrypt = new Encrypt();
                String token = encrypt.getToken(true, hotelAuths.get(0).getUserId(), "username", 0);
                Map<String,Object> map = new HashMap<>(5);
                map.put("token",token);
                map.put("userId",hotelAuths.get(0).getUserId());
                map.put("name",username);
                return builder.body(ResponseUtils.getResponseBody(map));
            }else {
                response.sendError(1000,"用户名或密码错误");
                return builder.body(ResponseUtils.getResponseBody(1));
            }
        }
    }


    @RequestMapping(path = "/wechart", method = RequestMethod.GET)
    @ApiOperation(value = "微信登录小程序", notes = "微信登录")
    public ResponseEntity<JSONObject> wxLogin(@RequestParam(value = "code", required = false) String code,
                                              @RequestParam(value = "appName", required = false) String appName,
                                              @RequestParam(value = "encryptedData", required = false) String encryptedData,
                                              @RequestParam(value = "iv", required = false) String iv, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>(10);
        JSONObject SessionKeyOpenId = WxLoginConfig.getSessionKeyOrOpenId(code, appName);
        System.out.println(SessionKeyOpenId);
        String openid = (String) SessionKeyOpenId.get("openid");
        String sessionKey = String.valueOf(SessionKeyOpenId.get("session_key"));
        System.out.println(openid+"我是openId");
        HotelAuthExample hotelAuthExample = new HotelAuthExample();
        hotelAuthExample.createCriteria().andAuthKeyEqualTo(openid)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        List<HotelAuth> auths = hotelAuthMapper.selectByExample(hotelAuthExample);
        HotelUser hotelUser = CollectionUtils.isEmpty(auths) ? register(openid, sessionKey, encryptedData, iv) : hotelUserMapper.selectByPrimaryKey(auths.get(0).getUserId());

        String skey = UUID.randomUUID().toString();
//        String skey_redis = String.valueOf(redisTemplate.opsForValue().get(openid));
//        if (!StringUtils.isEmpty(skey_redis)) {
//            redisTemplate.delete(skey_redis);
//            skey = UUID.randomUUID().toString();
//        }

        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", openid);
        sessionObj.put("sessionKey", sessionKey);
//        redisTemplate.opsForValue().set(skey, sessionObj.toJSONString());
//        redisTemplate.opsForValue().set(openid, skey);
        Encrypt encrypt = new Encrypt();
        String token = encrypt.getToken(true, hotelUser.getId(), "user", 0);
        response.addHeader("token", token);
        map.put("skey", skey);
        map.put("userId", hotelUser.getId());
        map.put("user", hotelUser);
        map.put("token", token);

        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(map));
    }

    private HotelUser register(String openid, String sessionKey, String encryptedData, String iv)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidParameterSpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        JSONObject userInfo = WxLoginConfig.parseWechart(encryptedData, sessionKey, iv);
        System.out.println(userInfo + "信息啊");
        HotelUser hotelUser = new HotelUser();

        HotelAuthExample example = new HotelAuthExample();
        example.createCriteria().andAuthKeyEqualTo(openid)
                .andIsDeletedEqualTo((byte) 0);
        List<HotelAuth> list = hotelAuthMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            hotelUser.setAddress(userInfo.getString("country") + " " + userInfo.getString("province") + " " + userInfo.getString("city"));
            hotelUser.setFile(userInfo.getString("avatarUrl"));
            hotelUser.setPhone(userInfo.getString("phone"));
            System.out.println(userInfo);
            hotelUser.setCreateDate(LocalDateTime.now());
            hotelUser.setUserType(UserType.WECHART.getAuthType());
            hotelUser.setModifyDate(LocalDateTime.now());
            hotelUser.setIsDeleted((byte) 0);
            hotelUser.setRegion(userInfo.getString("province"));
            hotelUser.setUserStatus((byte) 0);
            hotelUser.setNickName(userInfo.getString("nickName"));
            hotelUserMapper.insertSelective(hotelUser);
        } else {
            hotelUser = hotelUserMapper.selectByPrimaryKey(list.get(0).getUserId());
        }

        HotelAuth record = new HotelAuth();
        record.setAuthKey(openid);
        record.setAuthStatus(Byte.valueOf("0"));
        record.setAuthType(WxLoginConfig.AuthType.WECHART.getAuthType());
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());
        record.setUserId(hotelUser.getId());
        record.setIsDeleted((byte) 0);
        hotelAuthMapper.insert(record);
        return hotelUser;
    }

    @ApiOperation(value = "用户信息查询", notes = "用户信息查询")
    @RequestMapping(value = "/selectUserDetails", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectUserDetails(
            Integer userId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelUser hotelUser = hotelUserMapper.selectByPrimaryKey(userId);
        return builder.body(ResponseUtils.getResponseBody(hotelUser));
    }

    @ApiOperation(value = "用户信息修改", notes = "用户信息修改")
    @RequestMapping(value = "/updateUserDetails", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "性別", required = true, type = "Byte"),
            @ApiImplicitParam(paramType = "query", name = "realName", value = "姓名", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "birthDay", value = "生日", required = true, type = "Date"),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "region", value = "身份证地区", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "address", value = "身份证住址", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updateUserDetails(
            Integer userId,
            Byte sex,
            String realName,
            Date birthDay,
            String phone,
            String region,
            String address
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelUser hotelUser = new HotelUser();
        hotelUser.setId(userId);
        hotelUser.setModifyDate(LocalDateTime.now());
        hotelUser.setRealName(realName);
        hotelUser.setSex(sex);
        hotelUser.setPhone(phone);
        hotelUser.setIdentityRegion(region);
//        hotelUser.setRegion(region);
        hotelUser.setIdentityAddress(address);
//        hotelUser.setAddress(address);
        ZoneId zoneId = ZoneId.systemDefault();
        hotelUser.setBirthDay(LocalDateTime.ofInstant(birthDay.toInstant(), zoneId));
        hotelUserMapper.updateByPrimaryKeySelective(hotelUser);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "用戶协议", notes = "用戶協議")
    @RequestMapping(value = "/text", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "text", value = "文本", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> text(
            String text
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelProtocolExample hotelProtocolExample = new HotelProtocolExample();
        hotelProtocolExample.createCriteria()
                .andIdDeletedEqualTo((byte) 0);
        List<HotelProtocol> hotelProtocols =
                hotelProtocolMapper.selectByExampleWithBLOBs(hotelProtocolExample);
        if (hotelProtocols.size() == 0){
            HotelProtocol hotelProtocol = new HotelProtocol();
            hotelProtocol.setProtocol(text);
            hotelProtocol.setCreateDate(LocalDateTime.now());
            hotelProtocol.setModifyDate(LocalDateTime.now());
            hotelProtocol.setIdDeleted((byte) 0);
            hotelProtocolMapper.insertSelective(hotelProtocol);
        } else {
            HotelProtocol hotelProtocol = hotelProtocols.get(0);
            hotelProtocol.setModifyDate(LocalDateTime.now());
            hotelProtocol.setProtocol(text);
            hotelProtocolMapper.updateByPrimaryKeyWithBLOBs(hotelProtocol);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询用戶协议", notes = "查询用戶协议")
    @RequestMapping(value = "/selectText", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectText(
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HotelProtocolExample hotelProtocolExample = new HotelProtocolExample();
        hotelProtocolExample.createCriteria()
                .andIdDeletedEqualTo((byte) 0);
        List<HotelProtocol> hotelProtocols =
                hotelProtocolMapper.selectByExampleWithBLOBs(hotelProtocolExample);
        HotelProtocol hotelProtocol = new HotelProtocol();
        if (hotelProtocols.size() != 0){
            hotelProtocol = hotelProtocols.get(0);
        }
        return builder.body(ResponseUtils.getResponseBody(hotelProtocol));
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
