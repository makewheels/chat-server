package com.eg.chatserver.oss;

import com.alibaba.fastjson.JSON;
import com.eg.chatserver.common.ErrorCode;
import com.eg.chatserver.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("oss")
@Slf4j
public class OssController {
    @Resource
    private OssService ossService;

    @RequestMapping("aliyunCallback")
    public Result<Void> aliyunCallback(
            HttpServletRequest request, HttpServletResponse response,
            @RequestBody String body) {
        boolean check = ossService.checkCallback(request, body);
        if (!check) {
            log.error("aliyun oss callback check fail: {}", body);
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return Result.error(ErrorCode.FAIL);
        }
        // {"bucket":"chat-oss-bucket",
        // "object":"rubbish/1612272492048.jpg",
        // "etag":"F0F18C2C66AE1DD512BDCD4366F76DA3",
        // "size":9,
        // "mimeType":"image/jpeg",
        // "imageInfo.height":,
        // "imageInfo.width":,
        // "imageInfo.format":null}

        //老狗阿里云回调，生活不能自理，给他替换一下，再解析
        body = body.replace(":,", ":null,");
        body = body.replace("imageInfo.height", "imageInfoHeight");
        body = body.replace("imageInfo.width", "imageInfoWidth");
        body = body.replace("imageInfo.format", "imageInfoFormat");
        CallbackRequest callbackRequest = JSON.parseObject(body, CallbackRequest.class);
        return ossService.aliyunCallback(callbackRequest);
    }

}
