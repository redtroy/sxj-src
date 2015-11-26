/*
Navicat MySQL Data Transfer

Source Server         : 12
Source Server Version : 50619
Source Host           : 192.168.1.218:3306
Source Database       : sxj-supervisor

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2015-04-01 15:32:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `M_SYS_AREA`
-- ----------------------------
DROP TABLE IF EXISTS `M_SYS_AREA`;
CREATE TABLE `M_SYS_AREA` (
  `ID` varchar(32) NOT NULL COMMENT '主键标识',
  `NAME` varchar(50) DEFAULT NULL COMMENT '地域名称',
  `PARENT_ID` varchar(32) DEFAULT NULL COMMENT '父ID',
  `LEVEL` int(1) DEFAULT NULL COMMENT '级别',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of M_SYS_AREA
-- ----------------------------
INSERT INTO `M_SYS_AREA` VALUES ('32', '江苏省', '0', '1');
INSERT INTO `M_SYS_AREA` VALUES ('3201', '南京市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3202', '无锡市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3203', '徐州市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3204', '常州市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3205', '苏州市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3206', '南通市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3207', '连云港市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3208', '淮安市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3209', '盐城市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3210', '扬州市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3211', '镇江市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3212', '泰州市', '32', '2');
INSERT INTO `M_SYS_AREA` VALUES ('3213', '宿迁市', '32', '2');
