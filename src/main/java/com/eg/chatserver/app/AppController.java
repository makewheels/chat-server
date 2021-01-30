package com.eg.chatserver.app;

import com.eg.chatserver.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author makewheels
 * @Time 2021.01.30 17:07:12
 */
@RestController
@RequestMapping("app")
@Slf4j
public class AppController {

    @RequestMapping("checkVersion")
    public Result<Void> checkVersion(@RequestParam String version) {
        log.info("version: {}", version);
        return Result.ok();
    }
}
