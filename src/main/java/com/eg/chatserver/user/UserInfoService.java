package com.eg.chatserver.user;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.UserExample;
import com.eg.chatserver.bean.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * 用户信息相关service
 *
 * @Author makewheels
 * @Time 2021.01.30 13:08:39
 */
@Service
public class UserInfoService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private UserRedisService userRedisService;

    @PostMapping("searchUserByLoginName")
    @ApiOperation(value = "根据登录名精准搜索用户")
    public User searchUserByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        return userAccountService.findSingleUserByExample(userExample);
    }
}
