package com.eg.chatserver.app;

import com.eg.chatserver.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author makewheels
 * @Time 2021.01.30 17:07:12
 */
@RestController
@RequestMapping("app")
@Slf4j
@Api(tags = "App Controller")
public class AppController {

    @PostMapping("getLatestInfo")
    @ApiOperation(value = "获取最新版本信息")
    public Result<LatestAppInfoResponse> getLatestAppInfo() {
        LatestAppInfoResponse latestAppInfoResponse = new LatestAppInfoResponse();
        latestAppInfoResponse.setVersionCode(5);
        latestAppInfoResponse.setVersionName("1.0.4");
        latestAppInfoResponse.setIsForceUpdate(false);
        latestAppInfoResponse.setApkDownloadUrl("https://oss-chat.java8.icu/apk/alpha/Chat-5-1.0.4.apk");
        latestAppInfoResponse.setApkSize(7894642L);
        return Result.ok(latestAppInfoResponse);
    }

}
