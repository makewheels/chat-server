package com.eg.chatserver.oss;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("oss")
@Slf4j
@Api(tags = "Oss Controller")
public class OssController {
    @Resource
    private OssService ossService;

    @PostMapping("/getCredential")
    @ApiOperation(value = "获取临时凭证")
    public Result<Void> getCredential(HttpServletRequest request) {
        OssCredential credential = ossService.getCredential(900, "*");
        System.out.println(JSON.toJSONString(credential));
        return Result.ok();
    }

}
