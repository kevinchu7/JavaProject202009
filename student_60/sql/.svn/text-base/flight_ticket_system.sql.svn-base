/*
Navicat MySQL Data Transfer

Source Server         : conn1
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : flight_ticket_system

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2020-09-22 11:45:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `admin_name` varchar(50) NOT NULL COMMENT '管理员名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `id_card_no` varchar(18) NOT NULL COMMENT '身份证号',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `index_admin_id_card_no` (`id_card_no`) USING BTREE COMMENT 'id_card_no唯一标识'
) ENGINE=InnoDB AUTO_INCREMENT=3002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('3001', 'admin', 'admin', '320121199812251335', 'kevin', '2020-09-17 15:10:17');

-- ----------------------------
-- Table structure for t_cabin
-- ----------------------------
DROP TABLE IF EXISTS `t_cabin`;
CREATE TABLE `t_cabin` (
  `cabin_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '舱型id',
  `cabin_name` varchar(20) NOT NULL COMMENT '舱型名称',
  `cabin_seats` int(10) NOT NULL COMMENT '座位总数',
  `seat_lines` int(10) NOT NULL COMMENT '座位行数',
  `one_line_seats` int(10) NOT NULL COMMENT '单行座位数',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`cabin_id`),
  UNIQUE KEY `cabin_name` (`cabin_name`) USING BTREE COMMENT 'cabin_name唯一性标识'
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cabin
-- ----------------------------
INSERT INTO `t_cabin` VALUES ('201', '头等舱-t1', '12', '3', '4', '2020-09-16 10:19:12');
INSERT INTO `t_cabin` VALUES ('202', '商务舱-t1', '30', '5', '6', '2020-09-16 10:19:09');
INSERT INTO `t_cabin` VALUES ('203', '经济舱-t1', '50', '5', '10', '2020-09-16 10:19:08');
INSERT INTO `t_cabin` VALUES ('204', '头等舱-t3', '4', '2', '2', '2020-09-16 09:31:01');
INSERT INTO `t_cabin` VALUES ('205', '商务舱-t3', '6', '2', '3', '2020-09-16 09:31:27');
INSERT INTO `t_cabin` VALUES ('206', '经济舱-t3', '8', '2', '4', '2020-09-16 09:31:56');

-- ----------------------------
-- Table structure for t_flight
-- ----------------------------
DROP TABLE IF EXISTS `t_flight`;
CREATE TABLE `t_flight` (
  `flight_id` varchar(10) NOT NULL COMMENT '航班id',
  `flight_name` varchar(50) NOT NULL COMMENT '航班名称',
  `take_off_time` datetime NOT NULL COMMENT '起飞时间',
  `arrive_time` datetime NOT NULL COMMENT '到达时间',
  `start_place` varchar(50) NOT NULL COMMENT '出发地',
  `end_place` varchar(50) NOT NULL COMMENT '目的地',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  `effect_status` int(1) NOT NULL DEFAULT '1' COMMENT '有效状态',
  PRIMARY KEY (`flight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_flight
-- ----------------------------

-- ----------------------------
-- Table structure for t_flight_cabin_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_flight_cabin_relation`;
CREATE TABLE `t_flight_cabin_relation` (
  `flight_id` varchar(10) NOT NULL COMMENT '航班id',
  `cabin_id` int(5) NOT NULL COMMENT '舱型id',
  `price` decimal(10,2) NOT NULL COMMENT '座位单价',
  `rest_tickets` int(10) NOT NULL COMMENT '余票',
  `t_create_time` datetime NOT NULL COMMENT ' 创建时间',
  `effect_status` int(1) NOT NULL DEFAULT '1' COMMENT '有效状态',
  PRIMARY KEY (`flight_id`,`cabin_id`),
  KEY `fk_relation_cabin_id` (`cabin_id`),
  CONSTRAINT `fk_relation_cabin_id` FOREIGN KEY (`cabin_id`) REFERENCES `t_cabin` (`cabin_id`),
  CONSTRAINT `fk_relation_flight_id` FOREIGN KEY (`flight_id`) REFERENCES `t_flight` (`flight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_flight_cabin_relation
-- ----------------------------

-- ----------------------------
-- Table structure for t_flight_seat
-- ----------------------------
DROP TABLE IF EXISTS `t_flight_seat`;
CREATE TABLE `t_flight_seat` (
  `seat_id` varchar(50) NOT NULL COMMENT '座位id',
  `cabin_id` int(5) NOT NULL COMMENT '舱型id',
  `flight_id` varchar(10) NOT NULL COMMENT '航班id',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  `sold_status` int(1) NOT NULL DEFAULT '0' COMMENT '售出状态',
  `effect_status` int(1) NOT NULL DEFAULT '1' COMMENT '有效状态',
  PRIMARY KEY (`seat_id`),
  KEY `fk_seat_cabin_id` (`cabin_id`),
  KEY `fk_seat_flight_id` (`flight_id`),
  CONSTRAINT `fk_seat_cabin_id` FOREIGN KEY (`cabin_id`) REFERENCES `t_cabin` (`cabin_id`),
  CONSTRAINT `fk_seat_flight_id` FOREIGN KEY (`flight_id`) REFERENCES `t_flight` (`flight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_flight_seat
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `flight_id` varchar(10) NOT NULL COMMENT '航班id',
  `start_place` varchar(50) NOT NULL COMMENT '出发地',
  `end_place` varchar(50) NOT NULL COMMENT '目的地',
  `take_off_time` datetime NOT NULL COMMENT '起飞时间',
  `cabin_id` int(5) NOT NULL COMMENT '舱位等级',
  `seat_id` varchar(50) NOT NULL COMMENT '座位id',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `real_name` varchar(50) NOT NULL COMMENT '姓名',
  `id_card_no` varchar(18) NOT NULL COMMENT '身份证',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  `effect_status` int(1) NOT NULL DEFAULT '1' COMMENT '有效状态',
  PRIMARY KEY (`order_id`),
  KEY `fk_order_flight_id` (`flight_id`),
  KEY `fk_order_id_card_no` (`id_card_no`),
  KEY `fk_order_seat_id` (`seat_id`),
  KEY `fk_order_cabin_id` (`cabin_id`),
  CONSTRAINT `fk_order_cabin_id` FOREIGN KEY (`cabin_id`) REFERENCES `t_cabin` (`cabin_id`),
  CONSTRAINT `fk_order_id_card_no` FOREIGN KEY (`id_card_no`) REFERENCES `t_user` (`id_card_no`),
  CONSTRAINT `fk_order_seat_id` FOREIGN KEY (`seat_id`) REFERENCES `t_flight_seat` (`seat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2020005 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `id_card_no` varchar(18) NOT NULL COMMENT '身份证号',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `sex` char(2) NOT NULL COMMENT '性别',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `t_create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `index_user_id_card_no` (`id_card_no`) USING BTREE COMMENT 'id_card_no唯一标识',
  UNIQUE KEY `index_user_name` (`user_name`) COMMENT '用户名唯一标识'
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1001', 'jack123', 'jack123', '320121199812251337', 'jack', '男', '13814049715', '2020-09-16 18:33:31');
INSERT INTO `t_user` VALUES ('1002', 'zhangsan', '123456', '320121199812251336', 'zhangsan', '男', '13814049712', '2020-09-17 09:52:25');
INSERT INTO `t_user` VALUES ('1003', 'leo123', '666666', '320121199812251338', 'leo', '男', '13914049716', '2020-09-17 11:40:05');
INSERT INTO `t_user` VALUES ('1004', 'xiaohu', '123456', '320121199909161234', 'xiaohu', '男', '13814047531', '2020-09-17 15:26:34');
INSERT INTO `t_user` VALUES ('1005', 'Bob123', '123456', '320121199505061225', '鲍勃', '男', '13016951855', '2020-09-21 09:39:19');
SET FOREIGN_KEY_CHECKS=1;
