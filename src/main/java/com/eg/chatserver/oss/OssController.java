package com.eg.chatserver.oss;

import com.eg.chatserver.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/generatePresignedUrl")
    @ApiOperation(value = "generatePresignedUrl")
    public Result<Void> generatePresignedUrl() {
        String object = ossService.generatePreSignedUrl(
                "audio/user1a9a80964bc44183b72d36742742aa7d/file735cfe0246994ead96664ed1825aa022.wav",
                30L * 60L * 1000L);
        System.out.println(object);
        return Result.ok();
    }
}
