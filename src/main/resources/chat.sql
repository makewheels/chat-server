/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.31-log : Database - chat
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS */`chat` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `chat`;

/*Table structure for table `conversation` */

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

/*Table structure for table `file` */

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `file_id`           varchar(64)     DEFAULT NULL,
    `size`              bigint(20)      DEFAULT NULL COMMENT '文件大小',
    `original_name`     varchar(255)    DEFAULT NULL COMMENT '原始文件名',
    `extension`         varchar(255)    DEFAULT NULL COMMENT '拓展名',
    `bucket_name`       varchar(500)    DEFAULT NULL COMMENT 'bucket名',
    `object_name`       varchar(500)    DEFAULT NULL COMMENT '对象名',
    `mime_type`         varchar(64)     DEFAULT NULL,
    `etag`              varchar(64)     DEFAULT NULL COMMENT 'md5',
    `oss_url`           varchar(500)    DEFAULT NULL COMMENT '对象存储url',
    `image_preview_url` varchar(500)    DEFAULT NULL COMMENT '预览图地址',
    `audio_duration`    int(11)         DEFAULT NULL COMMENT '语音时长',
    `create_time`       timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `file_id` (`file_id`),
    KEY `etag` (`etag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

/*Table structure for table `person_message` */

DROP TABLE IF EXISTS `person_message`;

CREATE TABLE `person_message`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `message_id`        varchar(64)     DEFAULT NULL,
    `from_user_id`      varchar(64)     DEFAULT NULL,
    `to_user_id`        varchar(64)     DEFAULT NULL,
    `conversation_id`   varchar(64)     DEFAULT NULL COMMENT '会话id',
    `message_type`      varchar(255)    DEFAULT NULL COMMENT '消息类型',
    `content`           text,
    `url`               varchar(500)    DEFAULT NULL,
    `image_preview_url` varchar(500)    DEFAULT NULL COMMENT '图片预览',
    `is_arrive`         bit(1)          DEFAULT NULL COMMENT '是否已送达',
    `arrive_time`       timestamp  NULL DEFAULT NULL COMMENT '送达时间',
    `is_read`           bit(1)          DEFAULT NULL COMMENT '是否已读',
    `read_time`         timestamp  NULL DEFAULT NULL COMMENT '已读时间',
    `create_time`       timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `message_id` (`message_id`),
    KEY `from_user_id` (`from_user_id`),
    KEY `to_user_id` (`to_user_id`),
    KEY `conversation_id` (`conversation_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

/*Table structure for table `user` */

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

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
