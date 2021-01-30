package com.eg.chatserver.user;

import com.eg.chatserver.bean.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * 用户信息相关service
 *
 * @Author makewheels
 * @Time 2021.01.30 13:08:39
 */
public class UserInfoService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRedisService userRedisService;
}
