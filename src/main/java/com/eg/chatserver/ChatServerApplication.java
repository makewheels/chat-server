package com.eg.chatserver;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@MapperScan("com.eg.chatserver.bean.mapper")
@SpringBootApplication
public class ChatServerApplication {

    public static void main(String[] args) throws IOException {
//        handleEnvironment();

        SpringApplication.run(ChatServerApplication.class, args);
    }

    /**
     * 处理环境
     */
    private static void handleEnvironment() throws IOException {
        //加载环境配置文件
        File environmentFile = new File(SystemUtils.getUserHome(), "environment.properties");
        if (!environmentFile.exists()) {
            return;
        }
        List<String> lines = FileUtils.readLines(environmentFile, StandardCharsets.UTF_8);
        if (CollectionUtils.isEmpty(lines)) {
            return;
        }
        //遍历每一行
        for (String line : lines) {
            if (StringUtils.isBlank(line) || line.startsWith("#")) {
                continue;
            }
            String[] split = line.split("=");
            String key = split[0];
            String value = split[1];
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
                continue;
            }
            //拿到环境，改application.properties
            if (key.equals("environment")) {
                File applicationPropertiesFile = new ClassPathResource("application.properties").getFile();
                if (value.equals("develop")) {
                    FileUtils.writeStringToFile(applicationPropertiesFile, "spring.profiles.active=develop", StandardCharsets.UTF_8);
                } else if (value.equals("product")) {
                    FileUtils.writeStringToFile(applicationPropertiesFile, "spring.profiles.active=product", StandardCharsets.UTF_8);
                }
            }
        }
    }

}
