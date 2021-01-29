package com.eg.chatserver.user;

import com.eg.chatserver.bean.User;
import com.eg.chatserver.bean.mapper.UserMapper;
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
     * @param user 之所以传user，可能后面可以做，加盐的hash。但目前只用到了password
     * @return
     */
    public String getPasswordHash(User user) {
        return DigestUtils.md5Hex(user.getPassword());
    }

    /**
     * 注册
     *
     * @param loginName
     * @param password
     * @return
     */
    public User register(String loginName, String password) {
        User user = new User();
        user.setLoginName(loginName);
        //刚注册时，先把昵称也给成loginName
        user.setNickname(loginName);
        user.setUuid("user" + UuidUtil.getRandomUUid());
        //给密码hash
        user.setPassword(getPasswordHash(user));
        user.setCreateTime(new Date());
        //生成loginToken
        String loginToken = "loginToken" + UuidUtil.getRandomUUid() + RandomStringUtils.random(8);
        user.setLoginToken(loginToken);
        //保存用户
        userMapper.insert(user);
        return user;
    }
}
