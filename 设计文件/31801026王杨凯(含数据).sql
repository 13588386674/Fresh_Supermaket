/*
Navicat MySQL Data Transfer

Source Server         : shiyan1
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : freshsupermaket

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-07-13 10:51:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` varchar(20) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `connect_person` varchar(20) DEFAULT NULL,
  `connect_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FK_juzhu` (`customer_id`),
  CONSTRAINT `FK_juzhu` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', '1', '1', '1', '1', '1', '1', '1');
INSERT INTO `address` VALUES ('6', '12', 'dawdawd', 'dawd', 'adw', 'dadw', 'dawd', '12');
INSERT INTO `address` VALUES ('8', 'wyk', '浙江省', '杭州市', '拱墅区', '明德', '老李', '18888888888');
INSERT INTO `address` VALUES ('9', 'wyk', '浙江省', '杭州市', '拱墅区', '明德', '老刘', '17777777777');
INSERT INTO `address` VALUES ('11', 'test1', 'dwad', 'wadawdwad', 'dadwa', 'dawd', 'dad', '12');
INSERT INTO `address` VALUES ('12', 'test2notvip', 'dada', 'da', 'da', 'da', 'da', '123');

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  `suitable_amount` double DEFAULT NULL,
  `credit_amount` double DEFAULT NULL,
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` varchar(20) NOT NULL,
  `customer_name` varchar(50) DEFAULT NULL,
  `customer_sex` varchar(10) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `customer_number` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `member_finish_date` datetime DEFAULT NULL,
  `member` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '123', '男', '123', '123', '123', '123', '2020-07-07 20:32:33', '2020-07-30 14:18:53', '0');
INSERT INTO `customer` VALUES ('12', '12', '男', '12', '12', '12', '12', '2020-07-07 14:26:59', null, '0');
INSERT INTO `customer` VALUES ('888', 'sxjie', '男', '123456', '1234567890', '123143151', 'zhejiang', '2020-07-12 16:06:02', '2022-07-12 16:06:02', '1');
INSERT INTO `customer` VALUES ('test1', 'wu', '男', '123456', '13588386674', '1132', 'hangzhou', '2020-07-13 10:10:16', '2022-07-13 10:10:16', '1');
INSERT INTO `customer` VALUES ('test2notvip', 'wu', '男', '123456', '13588386674', '13588386674', '1222', '2020-07-13 10:10:47', '2020-07-13 10:10:47', '1');
INSERT INTO `customer` VALUES ('user1', 'w', '男', '123456', '1', '1', '1', '2020-07-12 14:10:18', null, '0');
INSERT INTO `customer` VALUES ('wyk', '王杨凯', '男', '000412', '13588386674', '13588386674', '杭州', '2020-07-10 16:16:25', '2020-10-20 10:20:30', '1');

-- ----------------------------
-- Table structure for fullandgood
-- ----------------------------
DROP TABLE IF EXISTS `fullandgood`;
CREATE TABLE `fullandgood` (
  `good_id` int(11) NOT NULL,
  `full_reduction_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`good_id`,`full_reduction_id`),
  KEY `Fk_FullAndGood2` (`full_reduction_id`),
  CONSTRAINT `FK_FullAndGood` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`),
  CONSTRAINT `Fk_FullAndGood2` FOREIGN KEY (`full_reduction_id`) REFERENCES `fullinformation` (`full_reduction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fullandgood
-- ----------------------------
INSERT INTO `fullandgood` VALUES ('1', '3', '2020-10-20 10:20:30', '2022-10-20 10:20:30');

-- ----------------------------
-- Table structure for fullinformation
-- ----------------------------
DROP TABLE IF EXISTS `fullinformation`;
CREATE TABLE `fullinformation` (
  `full_reduction_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(100) DEFAULT NULL,
  `suitable_good_quantity` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`full_reduction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fullinformation
-- ----------------------------
INSERT INTO `fullinformation` VALUES ('1', '满折1号', '4', '0.8', '2020-07-09 11:47:27', '2020-07-30 11:47:29');
INSERT INTO `fullinformation` VALUES ('2', '满折2号', '6', '0.7', '2020-07-09 11:47:56', '2020-07-22 11:48:00');
INSERT INTO `fullinformation` VALUES ('3', '香蕉、带鱼满3个打八折', '3', '0.8', '2020-07-11 10:11:20', '2020-07-31 10:11:24');

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `good_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) DEFAULT NULL,
  `good_name` varchar(30) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `member_price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `specification` varchar(200) DEFAULT NULL,
  `detail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`good_id`),
  KEY `FK_fenlei` (`type_id`),
  CONSTRAINT `FK_fenlei` FOREIGN KEY (`type_id`) REFERENCES `goodtype` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES ('1', '1', '牛肉', '10', '9.5', '187', 'wdadw', 'dawdaw');
INSERT INTO `good` VALUES ('3', '2', '西瓜', '3', '2.3', '2369', 'dad', 'a');
INSERT INTO `good` VALUES ('4', '1', '冬瓜', '2', '2', '7', '1w', 'ww');
INSERT INTO `good` VALUES ('5', '2', '葡萄', '10.2', '8.5', '922', 'wu', '无');
INSERT INTO `good` VALUES ('6', '4', '带鱼', '1000', '900', '970', '', '');
INSERT INTO `good` VALUES ('7', '2', '香蕉', '50', '40', '489', null, null);
INSERT INTO `good` VALUES ('8', '2', '橘子', '80', '60', '62', null, null);
INSERT INTO `good` VALUES ('9', '4', '龙虾', '1000', '900', '95', 'wu', 'wu');
INSERT INTO `good` VALUES ('10', '2', '苹果', '30', '30', '468', 'wu', 'wu');

-- ----------------------------
-- Table structure for goodandrecipe
-- ----------------------------
DROP TABLE IF EXISTS `goodandrecipe`;
CREATE TABLE `goodandrecipe` (
  `good_id` int(11) NOT NULL,
  `recipe_id` int(11) NOT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`good_id`,`recipe_id`),
  KEY `FK_GoodAndRecipe2` (`recipe_id`),
  CONSTRAINT `FK_GoodAndRecipe` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`),
  CONSTRAINT `FK_GoodAndRecipe2` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodandrecipe
-- ----------------------------
INSERT INTO `goodandrecipe` VALUES ('1', '1', 'wu');

-- ----------------------------
-- Table structure for goodpurchase
-- ----------------------------
DROP TABLE IF EXISTS `goodpurchase`;
CREATE TABLE `goodpurchase` (
  `purchase_id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `FK_goumai` (`good_id`),
  CONSTRAINT `FK_goumai` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodpurchase
-- ----------------------------
INSERT INTO `goodpurchase` VALUES ('1', '1', '1000', '入库');
INSERT INTO `goodpurchase` VALUES ('2', '1', '1000', '入库');
INSERT INTO `goodpurchase` VALUES ('3', '3', '500', '入库');
INSERT INTO `goodpurchase` VALUES ('4', '1', '100', '在途');
INSERT INTO `goodpurchase` VALUES ('5', '3', '1000', '入库');
INSERT INTO `goodpurchase` VALUES ('6', '1', '2', '在途');
INSERT INTO `goodpurchase` VALUES ('7', '1', '2', '入库');
INSERT INTO `goodpurchase` VALUES ('9', '1', '3', '在途');

-- ----------------------------
-- Table structure for goodtype
-- ----------------------------
DROP TABLE IF EXISTS `goodtype`;
CREATE TABLE `goodtype` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(30) DEFAULT NULL,
  `detail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodtype
-- ----------------------------
INSERT INTO `goodtype` VALUES ('1', '生鲜', '11');
INSERT INTO `goodtype` VALUES ('2', '水果', '22');
INSERT INTO `goodtype` VALUES ('3', '酒水', '33');
INSERT INTO `goodtype` VALUES ('4', '海鲜', null);

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail` (
  `order_id` int(11) NOT NULL,
  `good_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`order_id`,`good_id`),
  KEY `FK_OrderDetail2` (`good_id`),
  CONSTRAINT `FK_OrderDetai` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_OrderDetail2` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES ('33', '9', '1', '900', null);
INSERT INTO `orderdetail` VALUES ('34', '9', '1', '1000', null);
INSERT INTO `orderdetail` VALUES ('37', '1', '3', '9.5', null);
INSERT INTO `orderdetail` VALUES ('37', '3', '1', '2.3', null);
INSERT INTO `orderdetail` VALUES ('38', '1', '3', '9.5', null);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) DEFAULT NULL,
  `customer_id` varchar(20) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `original_price` double DEFAULT NULL,
  `settlement_price` double DEFAULT NULL,
  `receive_time` datetime DEFAULT NULL,
  `order_state` varchar(30) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `evaluate_date` datetime DEFAULT NULL,
  `star` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK_xiadan` (`customer_id`),
  KEY `FK_shiyong` (`coupon_id`),
  KEY `Fk_dizhi` (`address_id`),
  CONSTRAINT `FK_shiyong` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`coupon_id`),
  CONSTRAINT `FK_xiadan` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `Fk_dizhi` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('33', null, 'test1', '11', '900', '900', '2020-07-14 10:15:12', '配送中', null, null, null);
INSERT INTO `orders` VALUES ('34', null, 'test2notvip', '12', '1000', '1000', '2020-07-14 10:16:22', '配送中', null, null, null);
INSERT INTO `orders` VALUES ('37', null, 'wyk', '9', '30.8', '25.1', '2020-07-14 10:28:22', '配送中', null, null, null);
INSERT INTO `orders` VALUES ('38', null, 'wyk', '9', '28.5', '22.8', '2020-07-14 10:44:52', '配送中', null, null, null);

-- ----------------------------
-- Table structure for recipe
-- ----------------------------
DROP TABLE IF EXISTS `recipe`;
CREATE TABLE `recipe` (
  `recipe_id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_name` varchar(30) DEFAULT NULL,
  `recipe_material` varchar(200) DEFAULT NULL,
  `steps` varchar(200) DEFAULT NULL,
  `detail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recipe
-- ----------------------------
INSERT INTO `recipe` VALUES ('1', '黄金菜谱', '暂无', '暂无', '暂未');

-- ----------------------------
-- Table structure for systemuser
-- ----------------------------
DROP TABLE IF EXISTS `systemuser`;
CREATE TABLE `systemuser` (
  `system_user_id` varchar(20) NOT NULL,
  `system_user_name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`system_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemuser
-- ----------------------------
INSERT INTO `systemuser` VALUES ('admin', '超级管理员', 'admin');

-- ----------------------------
-- Table structure for timepromotion
-- ----------------------------
DROP TABLE IF EXISTS `timepromotion`;
CREATE TABLE `timepromotion` (
  `promotion_id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `start_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`promotion_id`),
  KEY `FK_canyu` (`good_id`),
  CONSTRAINT `FK_canyu` FOREIGN KEY (`good_id`) REFERENCES `good` (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of timepromotion
-- ----------------------------
INSERT INTO `timepromotion` VALUES ('1', '10', '20', '3', '2020-06-30 10:18:26', '2020-07-08 10:18:32');
INSERT INTO `timepromotion` VALUES ('2', '9', '980', '98', '2020-07-13 10:19:17', '2020-07-16 10:19:20');
