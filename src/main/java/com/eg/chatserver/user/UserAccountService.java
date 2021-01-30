package com.eg.chatserver.user;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.UserExample;
import com.eg.chatserver.bean.mapper.UserMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.login.LoginRequest;
import com.eg.chatserver.user.login.LoginResponse;
import com.eg.chatserver.user.register.RegisterRequest;
import com.eg.chatserver.user.register.RegisterResponse;
import com.eg.chatserver.utils.Constants;
import com.eg.chatserver.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户账号相关service
 */
@Service
@Slf4j
public class UserAccountService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRedisService userRedisService;

    /**
     * 获取密码hash
     *
     * @param password
     * @return
     */
    public String getPasswordHash(String password) {
        return DigestUtils.md5Hex(password);
    }

    /**
     * 检查登录名是否已经存在
     *
     * @param loginName
     * @return
     */
    public boolean checkLoginNameExist(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        long count = userMapper.countByExample(userExample);
        return count >= 1;
    }

    /**
     * 生成loginToken
     *
     * @return
     */
    private String generateLoginToken() {
        return "loginToken" + UuidUtil.getRandomUUid()
                + RandomStringUtils.randomAlphanumeric(8);
    }

    /**
     * 注册时保存用户
     *
     * @param registerRequest
     * @return
     */
    private User registerSaveUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setLoginName(registerRequest.getLoginName());
        //刚注册时，先把昵称也给成loginName
        user.setNickname(registerRequest.getLoginName());
        user.setUserId("user" + UuidUtil.getRandomUUid());
        //给密码hash
        user.setPassword(getPasswordHash(registerRequest.getPassword()));
        user.setCreateTime(new Date());
        user.setHeadImageUrl(Constants.DEFAULT_HEAD_IMAGE_URL);
        //生成loginToken
        String loginToken = generateLoginToken();
        user.setLoginToken(loginToken);
        //保存用户
        userMapper.insert(user);
        log.info("new user registered: " + JSON.toJSONString(user));
        return user;
    }

    /**
     * 获取注册的返回值
     *
     * @param user
     * @return
     */
    private RegisterResponse getRegisterResponse(User user) {
        //封装参数返回
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserId(user.getUserId());
        registerResponse.setLoginName(user.getLoginName());
        registerResponse.setHeadImageUrl(user.getHeadImageUrl());
        registerResponse.setLoginToken(user.getLoginToken());
        return registerResponse;
    }

    /**
     * 注册
     *
     * @param registerRequest
     * @return
     */
    public Result<RegisterResponse> register(RegisterRequest registerRequest) {
        String loginName = registerRequest.getLoginName();
        //注册前先判断登录名是否已经存在
        boolean loginNameExist = checkLoginNameExist(loginName);
        //如果存在返回错误信息
        if (loginNameExist) {
            log.info("register fail, because of login name exist {}"
                    + JSON.toJSONString(registerRequest));
            return Result.error(ErrorCode.REGISTER_LOGIN_NAME_ALREADY_EXISTS);
        }
        //如果不存在，执行注册，插入数据库，返回正确信息
        User user = registerSaveUser(registerRequest);
        //然后为用户自动登录一次，把user放到redis里
        userRedisService.setUserByLoginToken(user.getLoginToken(), user);
        //返回注册结果
        RegisterResponse registerResponse = getRegisterResponse(user);
        return Result.ok(registerResponse);
    }

    /**
     * 根据example查询单用户
     *
     * @param userExample
     * @return
     */
    private User findSingleUserByExample(UserExample userExample) {
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 根据loginToken查数据库获取user
     *
     * @param loginToken
     * @return
     */
    public User findUserByLoginTokenFromSql(String loginToken) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginTokenEqualTo(loginToken);
        return findSingleUserByExample(userExample);
    }

    /**
     * 根据登录名和密码从数据库中查找用户
     *
     * @param loginName
     * @param password
     * @return
     */
    private User findUserByLoginNameAndPassword(String loginName, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andLoginNameEqualTo(loginName)
                .andPasswordEqualTo(password);
        return findSingleUserByExample(userExample);
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    public Result<LoginResponse> login(LoginRequest loginRequest) {
        String loginName = loginRequest.getLoginName();
        String password = loginRequest.getPassword();
        //别忘了要给password hash
        password = getPasswordHash(password);
        //去数据库，根据登录名密码查用户
        User user = findUserByLoginNameAndPassword(loginName, password);
        //如果没找到，说明用户名和密码不匹配
        if (user == null) {
            log.error("login fail, loginRequest = {}", JSON.toJSONString(loginRequest));
            return Result.error(ErrorCode.LOGIN_LOGIN_NAME_PASSWORD_WRONG);
        } else {
            //如果找到了该用户
            //把他之前redis里的loginToken删掉
            userRedisService.deleteUserByLoginToken(user.getLoginToken());
            //生成新的loginToken
            String newLoginToken = generateLoginToken();
            user.setLoginToken(newLoginToken);
            //更新mysql
            userMapper.updateByPrimaryKey(user);
            //存入redis
            userRedisService.setUserByLoginToken(newLoginToken, user);
            log.info("login success, user = {}", JSON.toJSONString(user));
            //返回
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setLoginName(user.getLoginName());
            loginResponse.setUserId(user.getUserId());
            loginResponse.setHeadImageUrl(user.getHeadImageUrl());
            loginResponse.setLoginToken(newLoginToken);
            return Result.ok(loginResponse);
        }
    }

}
