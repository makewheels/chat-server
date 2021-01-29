package com.eg.chatserver.user;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.UserExample;
import com.eg.chatserver.bean.mapper.UserMapper;
import com.eg.chatserver.common.ErrorCodeEnum;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.register.RegisterRequest;
import com.eg.chatserver.user.register.RegisterResponse;
import com.eg.chatserver.utils.UuidUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

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
        user.setHeadImageUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3008468278,1590155304&fm=26&gp=0.jpg");
        //生成loginToken
        String loginToken = "loginToken" + UuidUtil.getRandomUUid() + RandomStringUtils.randomAlphanumeric(8);
        user.setLoginToken(loginToken);
        //保存用户
        userMapper.insert(user);
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
            return Result.error(ErrorCodeEnum.REGISTER_LOGIN_NAME_ALREADY_EXISTS);
        }
        //如果不存在，执行注册，插入数据库，返回正确信息
        User user = registerSaveUser(registerRequest);
        RegisterResponse registerResponse = getRegisterResponse(user);
        return Result.ok(registerResponse);
    }


}
