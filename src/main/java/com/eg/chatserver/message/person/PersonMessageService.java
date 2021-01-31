package com.eg.chatserver.message.person;

import com.eg.chatserver.bean.mapper.PersonMessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author makewheels
 * @Time 2021.01.31 12:50:48
 */
@Service
public class PersonMessageService {
    @Resource
    private PersonMessageMapper personMessageMapper;
}
