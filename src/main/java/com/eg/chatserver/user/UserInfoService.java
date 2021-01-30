package com.eg.chatserver.user;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.UserExample;
import com.eg.chatserver.bean.mapper.UserMapper;
import org.springframework.stereotype.Service;

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

    /**
     * 根据登录名精准查询用户
     *
     * @param loginName
     * @return
     */
    public User searchUserByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        return userAccountService.findSingleUserByExample(userExample);
    }
}
