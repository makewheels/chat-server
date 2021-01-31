package com.eg.chatserver.message.person;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:50:11
 */
@RestController
@RequestMapping("message/person")
public class PersonMessageController {
    @Resource
    private PersonMessageService personMessageService;
}
