package com.eg.chatserver.user;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.redis.RedisKey;
import com.eg.chatserver.redis.RedisService;
import com.eg.chatserver.redis.RedisTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author makewheels
 * @Time 2021.01.30 12:55:18
 */
@Service
public class UserRedisService {
    @Resource
    private RedisService redisService;

    /**
     * 检查redis里是否有该loginToken
     *
     * @param loginToken
     * @return
     */
    public boolean isLoginTokenExist(String loginToken) {
        String loginTokenKey = RedisKey.getLoginTokenKey(loginToken);
        return redisService.hasKey(loginTokenKey);
    }

    /**
     * 根据loginToken把user存入redis
     *
     * @param loginToken
     * @param user
     */
    public void setUserByLoginToken(String loginToken, User user) {
        String loginTokenKey = RedisKey.getLoginTokenKey(loginToken);
        redisService.set(loginTokenKey, JSON.toJSONString(user), RedisTime.ONE_HOUR);
    }

    /**
     * 根据loginToken获取redis里的user对象
     *
     * @param loginToken
     * @return
     */
    public User getUserByLoginToken(String loginToken) {
        String loginTokenKey = RedisKey.getLoginTokenKey(loginToken);
        String json = (String) redisService.get(loginTokenKey);
        return JSON.parseObject(json, User.class);
    }

    /**
     * 根据loginToken删除redis里的user对象
     *
     * @param loginToken
     */
    public void deleteUserByLoginToken(String loginToken) {
        String loginTokenKey = RedisKey.getLoginTokenKey(loginToken);
        redisService.del(loginTokenKey);
    }
}
