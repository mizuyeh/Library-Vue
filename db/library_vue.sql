/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.7.33 : Database - library_vue
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library_vue` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `library_vue`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(32) NOT NULL COMMENT '登录名',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `gender` int(11) NOT NULL DEFAULT '1' COMMENT '性别(1:男 0：女)',
  `identity_code` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(80) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `status` tinyint(1) DEFAULT '1' COMMENT '用户状态(1:正常 0:停用)',
  `file_name` varchar(255) DEFAULT NULL COMMENT '用户头像文件',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`login_name`,`user_name`,`password`,`gender`,`identity_code`,`email`,`mobile`,`status`,`file_name`,`create_time`,`update_time`,`remark`) values (1001,'admin','admin','123456',1,'345672456356374562','admin@qq.com','14455668835',1,'avatar1.jpg','2021-03-04 18:00:00','2021-03-04 18:00:00','超级管理员');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
