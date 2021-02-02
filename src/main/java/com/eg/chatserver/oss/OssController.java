package com.eg.chatserver.oss;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("oss")
public class OssController {
    @Resource
    private OssService ossService;

    @RequestMapping("aliyunCallback")
    public String callback(@RequestBody CallbackRequest callbackRequest,
                           HttpServletRequest request, HttpServletResponse response) {
        boolean check = ossService.checkCallback(request, callbackRequest);
        if (!check) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
        }
        System.out.println(check);
        System.out.println(JSON.toJSONString(callbackRequest));
        return "{\"a\":\"a=d&b=d%d=bbw\"}";
    }


}
