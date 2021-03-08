package com.eg.chatserver.oss;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("oss")
@Slf4j
@Api(tags = "Oss Controller")
public class OssController {
    @Resource
    private OssService ossService;

}
