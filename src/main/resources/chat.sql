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

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`                    bigint(20) NOT NULL AUTO_INCREMENT,
    `uuid`                  varchar(255)    DEFAULT NULL,
    `login_name`            varchar(255)    DEFAULT NULL COMMENT '登陆名',
    `password`              varchar(255)    DEFAULT NULL,
    `jpush_registration_id` varchar(255)    DEFAULT NULL COMMENT '极光注册id',
    `head_image_url`        varchar(1000)   DEFAULT NULL COMMENT '头像地址',
    `login_token`           varchar(255)    DEFAULT NULL COMMENT '自动登陆',
    `nickname`              varchar(255)    DEFAULT NULL COMMENT '昵称',
    `create_time`           timestamp  NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
