CREATE DATABASE IF NOT EXISTS `chat` DEFAULT CHARACTER SET utf8mb4;
USE `chat`;

DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `conversation_id`      varchar(64)     DEFAULT NULL,
    `user_id`              varchar(64)     DEFAULT NULL COMMENT '这是谁的会话',
    `target_id`            varchar(64)     DEFAULT NULL COMMENT '目标id，可以是人，可以是群',
    `type`                 varchar(255)    DEFAULT NULL COMMENT '类型，标记是群，还是人，甚至是系统消息',
    `title`                varchar(255)    DEFAULT NULL COMMENT '标题',
    `message_count`        int(11)         DEFAULT NULL COMMENT '总消息数量',
    `unread_message_count` int(11)         DEFAULT NULL COMMENT '未读消息数量',
    `update_time`          timestamp  NULL DEFAULT NULL,
    `create_time`          timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `conversation_id` (`conversation_id`),
    KEY `user_id` (`user_id`),
    KEY `target_id` (`target_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `person_message`;
CREATE TABLE `person_message`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `message_id`       varchar(64)     DEFAULT NULL,
    `from_user_id`     varchar(64)     DEFAULT NULL,
    `to_user_id`       varchar(64)     DEFAULT NULL,
    `conversation_id`  varchar(64)     DEFAULT NULL COMMENT '会话id',
    `file_id`          varchar(64)     DEFAULT NULL COMMENT '文件id，只针对非文本消息，而且一条消息只能有一个文件',
    `message_type`     varchar(255)    DEFAULT NULL COMMENT '消息类型',
    `is_upload_finish` bit(1)          DEFAULT NULL COMMENT '标记是否上传完成，仅用于文件类型消息',
    `content`          text            DEFAULT NULL COMMENT '消息内容，只针对文本消息',
    `is_arrive`        bit(1)          DEFAULT NULL COMMENT '是否已送达',
    `arrive_time`      timestamp  NULL DEFAULT NULL COMMENT '送达时间',
    `is_read`          bit(1)          DEFAULT NULL COMMENT '是否已读',
    `read_time`        timestamp  NULL DEFAULT NULL COMMENT '已读时间',
    `create_time`      timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `message_id` (`message_id`),
    KEY `from_user_id` (`from_user_id`),
    KEY `to_user_id` (`to_user_id`),
    KEY `conversation_id` (`conversation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `file_id`           varchar(64)     DEFAULT NULL,
    `type`              varchar(64)     DEFAULT NULL COMMENT '文件类型，例如：image,audio,video',
    `size`              bigint(20)      DEFAULT NULL COMMENT '文件大小',
    `original_name`     varchar(255)    DEFAULT NULL COMMENT '原始文件名',
    `extension`         varchar(255)    DEFAULT NULL COMMENT '拓展名',
    `bucket_name`       varchar(500)    DEFAULT NULL COMMENT 'bucket名',
    `object_name`       varchar(500)    DEFAULT NULL COMMENT '对象名',
    `image_width`       int(11)         DEFAULT NULL COMMENT '图片宽度',
    `image_height`      int(11)         DEFAULT NULL COMMENT '图片高度',
    `md5`               varchar(64)     DEFAULT NULL COMMENT '客户端传来的md5',
    `oss_url`           varchar(500)    DEFAULT NULL COMMENT '对象存储直接访问url',
    `cdn_url`           varchar(500)    DEFAULT NULL COMMENT 'CDN url',
    `image_preview_url` varchar(500)    DEFAULT NULL COMMENT '预览图地址',
    `duration`          bigint(20)      DEFAULT NULL COMMENT '语音或视频时长，单位毫秒',
    `create_time`       timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `file_id` (`file_id`),
    KEY `md5` (`md5`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                    bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`               varchar(64)     DEFAULT NULL,
    `login_name`            varchar(255)    DEFAULT NULL COMMENT '登陆名',
    `password`              varchar(64)     DEFAULT NULL,
    `login_token`           varchar(255)    DEFAULT NULL COMMENT '自动登陆',
    `jpush_registration_id` varchar(64)     DEFAULT NULL COMMENT '极光注册id',
    `head_image_url`        varchar(500)    DEFAULT NULL COMMENT '头像地址',
    `nickname`              varchar(255)    DEFAULT NULL COMMENT '昵称',
    `phone`                 varchar(255)    DEFAULT NULL COMMENT '手机',
    `create_time`           timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;