package com.eg.chatserver.user;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.UserExample;
import com.eg.chatserver.bean.mapper.UserMapper;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import com.eg.chatserver.user.bean.ModifyPasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息相关service
 *
 * @Author makewheels
 * @Time 2021.01.30 13:08:39
 */
@Slf4j
@Service
public class UserInfoService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private UserRedisService userRedisService;

    /**
     * 根据登录名精准搜索用户
     *
     * @param loginName
     * @return
     */
    public User searchUserByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        return userAccountService.findSingleUserByExample(userExample);
    }

    /**
     * 修改昵称
     *
     * @param user
     * @param newNickname
     * @return
     */
    public Result<Void> modifyNickname(User user, String newNickname) {
        //TODO 修改昵称，打日志

        //删除用户缓存
        userRedisService.deleteUserCache(user);
        return Result.ok();
    }

    /**
     * 修改密码
     *
     * @param user
     * @param modifyPasswordRequest
     * @return
     */
    public Result<Void> modifyPassword(User user, ModifyPasswordRequest modifyPasswordRequest) {
        //拿到新老密码hash
        String oldPassword = userAccountService.getPasswordHash(modifyPasswordRequest.getOldPassword());
        String newPassword = userAccountService.getPasswordHash(modifyPasswordRequest.getNewPassword());
        //先校验老密码是否正确，如果不正确，返回失败
        if (!user.getPassword().equals(oldPassword)) {
            log.warn("modify password, old password wrong, user: {}", JSON.toJSONString(user));
            return Result.error(ErrorCode.MODIFY_PASSWORD_OLD_PASSWORD_WRONG);
        }
        //老密码校验通过，开始修改
        //TODO 修改密码，打日志

        //删除用户缓存
        userRedisService.deleteUserCache(user);
        //修改密码成功，返回success
        return Result.ok();
    }
}
