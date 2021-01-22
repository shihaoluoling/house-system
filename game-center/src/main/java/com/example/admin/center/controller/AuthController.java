package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameAuthMapper;
import com.example.admin.center.dao.GameUserMapper;
import com.example.admin.center.manual.Enum.AuthLogin;
import com.example.admin.center.manual.Enum.Login;
import com.example.admin.center.manual.Enum.UserType;
import com.example.admin.center.manual.JSON.SelectAuth;
import com.example.admin.center.manual.JSON.SelectUser;
import com.example.admin.center.model.GameAuth;
import com.example.admin.center.model.GameAuthExample;
import com.example.admin.center.model.GameUser;
import com.example.admin.center.model.GameUserExample;
import com.example.admin.center.utils.Message;
import com.house.common.utils.Encrypt;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shihao
 * @Title: AuthController
 * @ProjectName Second-order-center
 * @Description: 验证
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Auth")
@CrossOrigin
public class AuthController {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    private GameAuthMapper gameAuthMapper;

    @Autowired
    private GameUserMapper gameUserMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
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
                jedis.set(phone+"game", String.valueOf(code));
                jedis.expire(phone+"game", 300);
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
    @RequestMapping(value = "/phoneLogin", method = RequestMethod.GET)
    @ApiOperation(value = "APP手机号登录", notes = "手机号登录")
    public ResponseEntity<JSONObject> phoneLogin(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(name = "phone") String phone,
                                                 @RequestParam(name = "passwd") String passwd) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if(StringUtils.isEmpty(phone)) {
            return builder.body(ResponseUtils.getResponseBody("手机号为null"));
        }
        GameAuthExample example = new GameAuthExample();
        example.createCriteria().andAuthKeyEqualTo(phone)
                .andIsDeletedEqualTo((byte) 0)
                .andAuthTypeEqualTo(Login.PHONE.getLogin())
                .andAuthStatusEqualTo((byte) 0)
        .andEncodeTypeEqualTo(AuthLogin.APP.getLogin());
        List<GameAuth> list = gameAuthMapper.selectByExample(example);
        if(list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody(-1));
        }else {
            Jedis jedis = new Jedis();
            jedis = jedisPool.getResource();
            String code = jedis.get(phone+"game");
            System.out.println("code+++++++++++"+code);
            System.out.println("passwd+++++++++++"+passwd);
            if(jedis!=null) {
                jedis.del(phone+"game");
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
                    GameAuthExample example1 = new GameAuthExample();
                    example1.createCriteria()
                            .andUserIdEqualTo(list.get(0).getUserId())
                            .andAuthTypeEqualTo(Login.PHONE.getLogin())
                            .andIsDeletedEqualTo((byte) 0)
                            .andAuthStatusEqualTo((byte) 0);
                    List<GameAuth> list1 = gameAuthMapper.selectByExample(example1);
                    map.put("name",list1.get(0).getAuthKey());
                    return builder.body(ResponseUtils.getResponseBody(map));
                }else {
                    return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
                }
            }
        }
    }
    @ApiOperation(value = "账号密码登陆", notes = "账号密码登陆")
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "登录类型", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> Login(
            String username,
            String password,
            String type,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //验证用户名是否存在
        GameAuthExample hotelAuthExample = new GameAuthExample();
        hotelAuthExample.createCriteria()
                .andAuthKeyEqualTo(username)
                //账号密码登录
                .andAuthTypeEqualTo(Login.ADMIN.getLogin())
                .andIsDeletedEqualTo((byte) 0)
                //登录类型
        .andEncodeTypeEqualTo(type);
        List<GameAuth> hotelAuths =
                gameAuthMapper.selectByExample(hotelAuthExample);
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
        GameAuthExample hotelAuthExample = new GameAuthExample();
        hotelAuthExample.createCriteria()
                .andAuthKeyEqualTo(username)
                .andAuthTypeEqualTo(Login.ADMIN.getLogin())
                .andIsDeletedEqualTo((byte) 0)
        .andEncodeTypeEqualTo(AuthLogin.ADMIN.getLogin());
        List<GameAuth> hotelAuths =
                gameAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() != 0){
            response.sendError(1000,"用户名存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            GameUser hotelUser = new GameUser();
            hotelUser.setRealName(username);
            hotelUser.setCreateDate(LocalDateTime.now());
            hotelUser.setModifyDate(LocalDateTime.now());
            hotelUser.setIsDeleted((byte) 0);
            hotelUser.setUserType(UserType.AUTH.getAuthType());
            gameUserMapper.insertSelective(hotelUser);
            GameAuth hotelAuth = new GameAuth();
            hotelAuth.setEncodeType(AuthLogin.ADMIN.getLogin());
            hotelAuth.setUserId(hotelUser.getId());
            hotelAuth.setAuthKey(username);
            hotelAuth.setAuthStatus((byte) 0);
            hotelAuth.setAuthType(Login.ADMIN.getLogin());
            hotelAuth.setCreateDate(LocalDateTime.now());
            hotelAuth.setModifyDate(LocalDateTime.now());
            hotelAuth.setIsDeleted((byte) 0);
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            hotelAuth.setPassword(bcryptPasswordEncoder.encode(password));
            gameAuthMapper.insertSelective(hotelAuth);
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
        GameAuthExample hotelAuthExample = new GameAuthExample();
        hotelAuthExample.createCriteria()
                .andIdEqualTo(AuthId)
                .andAuthTypeEqualTo(Login.ADMIN.getLogin())
                .andIsDeletedEqualTo((byte) 0)
                .andEncodeTypeEqualTo(AuthLogin.ADMIN.getLogin());
        List<GameAuth> hotelAuths =
                gameAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() == 0){
            response.sendError(1000,"用户不存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            GameAuth hotelAuth = new GameAuth();
            hotelAuth.setId(AuthId);
            hotelAuth.setAuthKey(username);
            hotelAuth.setModifyDate(LocalDateTime.now());
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            hotelAuth.setPassword(bcryptPasswordEncoder.encode(password));
            gameAuthMapper.updateByPrimaryKeySelective(hotelAuth);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "查询后台管理登录", notes = "查询后台管理登录")
    @RequestMapping(value = "/selectLogin", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectLogin(Integer start,
                                                  Integer num,
                                                  HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameAuthExample gameAuthExample = new GameAuthExample();
        gameAuthExample.createCriteria()
                .andAuthTypeEqualTo(Login.ADMIN.getLogin())
                .andIsDeletedEqualTo((byte) 0)
                .andEncodeTypeEqualTo(AuthLogin.ADMIN.getLogin());
        SelectAuth selectAuth = new SelectAuth();
        long nums = gameAuthMapper.countByExample(gameAuthExample);
        selectAuth.setNums(nums);
        if (start!=null && num!=null){
            //mysql 从0开始算数据,前端从1开始
            start -= 1;
            //转化成分页从第start开始,num条
            start = start*10;
            gameAuthExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        List<GameAuth> gameAuths = gameAuthMapper.selectByExample(gameAuthExample);
        selectAuth.setGameAuths(gameAuths);
        return builder.body(ResponseUtils.getResponseBody(selectAuth));
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
        GameAuthExample hotelAuthExample = new GameAuthExample();
        hotelAuthExample.createCriteria()
                .andIdEqualTo(AuthId)
                .andAuthTypeEqualTo(Login.ADMIN.getLogin())
                .andIsDeletedEqualTo((byte) 0)
                .andEncodeTypeEqualTo(AuthLogin.ADMIN.getLogin());
        List<GameAuth> hotelAuths =
                gameAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() == 0){
            response.sendError(1000,"用户不存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            GameAuth hotelAuth = new GameAuth();
            hotelAuth.setModifyDate(LocalDateTime.now());
            hotelAuth.setId(AuthId);
            hotelAuth.setIsDeleted((byte) 1);
            gameAuthMapper.updateByPrimaryKeySelective(hotelAuth);
            GameUser hotelUser = new GameUser();
            hotelUser.setId(hotelAuths.get(0).getUserId());
            hotelUser.setModifyDate(LocalDateTime.now());
            hotelUser.setIsDeleted((byte) 1);
            gameUserMapper.updateByPrimaryKeySelective(hotelUser);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "realName", value = "昵称", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "file", value = "头像", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateUser(
            Integer userId,
            String realName,
            String file,
            String phone,
            String password,
            HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //用户是否存在
        GameAuthExample hotelAuthExample = new GameAuthExample();
        //手机号是否已经注册
        hotelAuthExample.clear();
        hotelAuthExample.createCriteria()
                .andUserIdNotEqualTo(userId)
                .andAuthKeyEqualTo(phone)
                .andAuthTypeEqualTo(Login.PHONE.getLogin())
                .andIsDeletedEqualTo((byte) 0)
                .andEncodeTypeEqualTo(AuthLogin.APP.getLogin());
        List<GameAuth> hotelAuths1 =
                gameAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths1.size() != 0){
            response.sendError(1000,"手机号已经注册");
            return builder.body(ResponseUtils.getResponseBody(1));
        }
        hotelAuthExample.clear();
        hotelAuthExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andIsDeletedEqualTo((byte) 0)
                .andEncodeTypeEqualTo(AuthLogin.APP.getLogin());
        GameAuth gameAuth = new GameAuth();
        gameAuth.setModifyDate(LocalDateTime.now());
        gameAuth.setAuthKey(phone);
        if (password!=null){
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            gameAuth.setPassword(bcryptPasswordEncoder.encode(password));
        }
                gameAuthMapper.updateByExampleSelective(gameAuth,hotelAuthExample);

        GameUser gameUser  = new GameUser();
        gameUser.setId(userId);
        gameUser.setPhone(phone);
        gameUser.setRealName(realName);
        gameUser.setFile(file);
        gameUserMapper.updateByPrimaryKeySelective(gameUser);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "app手机号注册", notes = "app手机号注册")
    @RequestMapping(value = "/addAppLogin", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "验证码", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addAppLogin(String phone,String password,
                                               HttpServletResponse response
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //验证用户名是否存在
        GameAuthExample hotelAuthExample = new GameAuthExample();
        hotelAuthExample.createCriteria()
                .andAuthKeyEqualTo(phone)
                .andAuthTypeEqualTo(Login.PHONE.getLogin())
                .andIsDeletedEqualTo((byte) 0)
                .andEncodeTypeEqualTo(AuthLogin.APP.getLogin());
        List<GameAuth> hotelAuths =
                gameAuthMapper.selectByExample(hotelAuthExample);
        if (hotelAuths.size() != 0){
            response.sendError(1000,"用户名存在");
            return builder.body(ResponseUtils.getResponseBody(1));
        } else {
            Jedis jedis = new Jedis();
            jedis = jedisPool.getResource();
            String code = jedis.get(phone+"game");
            System.out.println("code+++++++++++"+code);
            System.out.println("passwd+++++++++++"+password);
            if(jedis!=null) {
                jedis.del(phone+"game");
                jedis.close();
            }
            if(StringUtils.isEmpty(password)) {
                return builder.body(ResponseUtils.getResponseBody("验证码为null"));
            }else {
                if(password.equals(code)) {
                    GameUser hotelUser = new GameUser();
                    hotelUser.setPhone(phone);
                    hotelUser.setRealName(phone);
                    hotelUser.setCreateDate(LocalDateTime.now());
                    hotelUser.setModifyDate(LocalDateTime.now());
                    hotelUser.setIsDeleted((byte) 0);
                    hotelUser.setUserType(UserType.WECHART.getAuthType());
                    gameUserMapper.insertSelective(hotelUser);
                    GameAuth hotelAuth = new GameAuth();
                    hotelAuth.setEncodeType(AuthLogin.APP.getLogin());
                    hotelAuth.setUserId(hotelUser.getId());
                    hotelAuth.setAuthKey(phone);
                    hotelAuth.setAuthStatus((byte) 0);
                    hotelAuth.setAuthType(Login.ADMIN.getLogin());
                    hotelAuth.setCreateDate(LocalDateTime.now());
                    hotelAuth.setModifyDate(LocalDateTime.now());
                    hotelAuth.setIsDeleted((byte) 0);
                    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
                    hotelAuth.setPassword(bcryptPasswordEncoder.encode(String.valueOf(123456)));
                    gameAuthMapper.insertSelective(hotelAuth);
                    hotelAuth.setId(null);
                    hotelAuth.setAuthType(Login.PHONE.getLogin());
                    hotelAuth.setPassword(null);
                    hotelAuth.setAuthKey(phone);
                    gameAuthMapper.insertSelective(hotelAuth);
                    return builder.body(ResponseUtils.getResponseBody(0));
                }else {
                    return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
                }
            }
        }
    }

    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户", notes = "查询用户")
    public ResponseEntity<JSONObject> selectUser(HttpServletRequest request,Integer start,
                                                 Integer num, HttpServletResponse response,
                                                 Integer userId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        GameUserExample gameUserExample = new GameUserExample();
        GameUserExample.Criteria criteria = gameUserExample.createCriteria()
                //前台登录
                .andUserTypeEqualTo(UserType.WECHART.getAuthType())
                .andIsDeletedEqualTo((byte) 0);
        SelectUser selectUser = new SelectUser();
        if (userId!=null){
            criteria.andIdEqualTo(userId);
        }
        long nums = gameUserMapper.countByExample(gameUserExample);
        selectUser.setNums(nums);
        if (start!=null && num!=null){
            //mysql 从0开始算数据,前端从1开始
            start -= 1;
            //转化成分页从第start开始,num条
            start = start*10;
            gameUserExample.setOrderByClause("id desc limit " + start + ","  + num);
        }
        List<GameUser> gameUsers = gameUserMapper.selectByExample(gameUserExample);
        selectUser.setGameUsers(gameUsers);
        return builder.body(ResponseUtils.getResponseBody(selectUser));
    }

}
