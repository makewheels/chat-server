package com.eg.chatserver.app;

import com.eg.chatserver.common.Result;
import com.eg.chatserver.utils.Constants;
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

    @PostMapping("getLatestAppInfo")
    @ApiOperation(value = "获取最新版本信息")
    public Result<LatestAppInfoResponse> getLatestAppInfo() {
        LatestAppInfoResponse latestAppInfoResponse = new LatestAppInfoResponse();
        latestAppInfoResponse.setVersionCode(2);
        latestAppInfoResponse.setVersionName("1.0.1");
        latestAppInfoResponse.setIsForceUpdate(false);
        latestAppInfoResponse.setApkDownloadUrl(Constants.ALIYUN.OSS_PREFIX_CDN + "apk/app-debug.apk");
        return Result.ok(latestAppInfoResponse);
    }
}
