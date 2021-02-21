package com.eg.chatserver.user.bean.phone;

import lombok.Data;

/**
 * 修改手机号，缓存在redis里的对象
 *
 * @Author makewheels
 * @Time 2021.02.22 00:10:37
 */
@Data
public class ModifyPhoneRedis {
    private String newPhone;
    private String verificationCode;
}
