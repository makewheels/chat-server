package com.eg.chatserver.user;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.redis.RedisKey;
import com.eg.chatserver.redis.RedisService;
import com.eg.chatserver.redis.RedisTime;
import com.eg.chatserver.user.bean.phone.ModifyPhoneRedis;
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
        String loginTokenKey = RedisKey.loginToken(loginToken);
        return redisService.hasKey(loginTokenKey);
    }

    /**
     * 根据loginToken把user存入redis
     *
     * @param loginToken
     * @param user
     */
    public void setUserByLoginToken(String loginToken, User user) {
        String loginTokenKey = RedisKey.loginToken(loginToken);
        redisService.set(loginTokenKey, JSON.toJSONString(user), RedisTime.ONE_HOUR);
    }

    /**
     * 根据loginToken获取redis里的user对象
     *
     * @param loginToken
     * @return
     */
    public User getUserByLoginToken(String loginToken) {
        String loginTokenKey = RedisKey.loginToken(loginToken);
        String json = (String) redisService.get(loginTokenKey);
        return JSON.parseObject(json, User.class);
    }

    /**
     * 根据loginToken删除redis里的user对象
     *
     * @param loginToken
     */
    public void deleteUserByLoginToken(String loginToken) {
        String loginTokenKey = RedisKey.loginToken(loginToken);
        redisService.del(loginTokenKey);
    }


    //修改手机
    public void setModifyPhone(User user, ModifyPhoneRedis modifyPhoneRedis) {
        redisService.set(RedisKey.modifyPhone(user.getUserId()),
                JSON.toJSONString(modifyPhoneRedis), RedisTime.TEN_MINUTES);
    }

    //修改手机
    public ModifyPhoneRedis getModifyPhone(User user) {
        String json = (String) redisService.get(RedisKey.modifyPhone(user.getUserId()));
        return JSON.parseObject(json, ModifyPhoneRedis.class);
    }

    //修改手机
    public void deleteModifyPhone(User user) {
        redisService.del(RedisKey.modifyPhone(user.getUserId()));
    }

    /**
     * 删除用户redis缓存，这里要删除所有的类型
     */
    public void deleteUserCache(User user) {
        deleteUserByLoginToken(user.getLoginToken());
        deleteModifyPhone(user);
    }
}
