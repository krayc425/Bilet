/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : Bilet

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 02/09/2018 23:35:31 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Admin`
-- ----------------------------
DROP TABLE IF EXISTS `Admin`;
CREATE TABLE `Admin` (
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`aid`),
  UNIQUE KEY `Admin_aid_uindex` (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `AdminBook`
-- ----------------------------
DROP TABLE IF EXISTS `AdminBook`;
CREATE TABLE `AdminBook` (
  `abid` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `vid` int(11) NOT NULL,
  `amount` double NOT NULL DEFAULT '0',
  `isConfirmed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`abid`),
  UNIQUE KEY `AdminBook_abid_uindex` (`abid`),
  KEY `FK78B792B8DD370367` (`vid`),
  KEY `FK78B792B83ED4BF81` (`eid`),
  CONSTRAINT `FK78B792B83ED4BF81` FOREIGN KEY (`eid`) REFERENCES `Event` (`eid`),
  CONSTRAINT `FK78B792B8DD370367` FOREIGN KEY (`vid`) REFERENCES `Venue` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `BankAccount`
-- ----------------------------
DROP TABLE IF EXISTS `BankAccount`;
CREATE TABLE `BankAccount` (
  `bankAccount` varchar(255) NOT NULL,
  `balance` double NOT NULL DEFAULT '0',
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `BankAccount_bid_uindex` (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Coupon`
-- ----------------------------
DROP TABLE IF EXISTS `Coupon`;
CREATE TABLE `Coupon` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `discount` double NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `point` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`cid`),
  UNIQUE KEY `Coupon_cid_uindex` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Event`
-- ----------------------------
DROP TABLE IF EXISTS `Event`;
CREATE TABLE `Event` (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `description` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `vid` int(11) NOT NULL,
  PRIMARY KEY (`eid`),
  UNIQUE KEY `Event_eid_uindex` (`eid`),
  KEY `FK403827ADD370367` (`vid`),
  KEY `FK403827A93B28F55` (`type`),
  CONSTRAINT `FK403827A93B28F55` FOREIGN KEY (`type`) REFERENCES `EventType` (`etid`),
  CONSTRAINT `FK403827ADD370367` FOREIGN KEY (`vid`) REFERENCES `Venue` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `EventSeat`
-- ----------------------------
DROP TABLE IF EXISTS `EventSeat`;
CREATE TABLE `EventSeat` (
  `eid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `price` int(11) NOT NULL DEFAULT '0',
  `esid` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`esid`),
  UNIQUE KEY `EventSeat_esid_uindex` (`esid`),
  KEY `FK7950F79F3ED4BF81` (`eid`),
  KEY `FK7950F79FD5729E72` (`sid`),
  CONSTRAINT `FK7950F79F3ED4BF81` FOREIGN KEY (`eid`) REFERENCES `Event` (`eid`),
  CONSTRAINT `FK7950F79FD5729E72` FOREIGN KEY (`sid`) REFERENCES `Seat` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `EventType`
-- ----------------------------
DROP TABLE IF EXISTS `EventType`;
CREATE TABLE `EventType` (
  `etid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`etid`),
  UNIQUE KEY `EventType_etid_uindex` (`etid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Level`
-- ----------------------------
DROP TABLE IF EXISTS `Level`;
CREATE TABLE `Level` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `minimumPoint` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `Level_lid_uindex` (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Member`
-- ----------------------------
DROP TABLE IF EXISTS `Member`;
CREATE TABLE `Member` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `bankAccount` varchar(255) NOT NULL,
  `totalPoint` int(11) NOT NULL DEFAULT '0',
  `isTerminated` tinyint(1) NOT NULL DEFAULT '0',
  `isEmailPassed` tinyint(1) NOT NULL DEFAULT '0',
  `currentPoint` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`mid`),
  UNIQUE KEY `Member_mid_uindex` (`mid`),
  UNIQUE KEY `Member_email_uindex` (`email`),
  KEY `Member_BankAccount_bankAccount_fk` (`bankAccount`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `MemberBook`
-- ----------------------------
DROP TABLE IF EXISTS `MemberBook`;
CREATE TABLE `MemberBook` (
  `mbid` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL,
  `oid` int(11) DEFAULT NULL,
  `amount` double DEFAULT '0',
  `type` int(11) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`mbid`),
  UNIQUE KEY `MemberBook_mbid_uindex` (`mbid`),
  KEY `FK26D938C3950959C1` (`mid`),
  KEY `FK26D938C399A2A2BF` (`oid`),
  CONSTRAINT `FK26D938C3950959C1` FOREIGN KEY (`mid`) REFERENCES `Member` (`mid`),
  CONSTRAINT `FK26D938C399A2A2BF` FOREIGN KEY (`oid`) REFERENCES `Order` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `MemberCoupon`
-- ----------------------------
DROP TABLE IF EXISTS `MemberCoupon`;
CREATE TABLE `MemberCoupon` (
  `mid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `mcid` int(11) NOT NULL AUTO_INCREMENT,
  `usageNum` int(11) NOT NULL DEFAULT '0',
  `time` datetime NOT NULL,
  PRIMARY KEY (`mcid`),
  UNIQUE KEY `MemberCoupon_mcid_uindex` (`mcid`),
  KEY `FKD725C780950959C1` (`mid`),
  KEY `FKD725C780124428C3` (`cid`),
  CONSTRAINT `FKD725C780124428C3` FOREIGN KEY (`cid`) REFERENCES `Coupon` (`cid`),
  CONSTRAINT `FKD725C780950959C1` FOREIGN KEY (`mid`) REFERENCES `Member` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Order`
-- ----------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE `Order` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `orderTime` datetime NOT NULL,
  `mid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `mcid` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`oid`),
  UNIQUE KEY `Order_oid_uindex` (`oid`),
  KEY `FK48E972E3ED4BF81` (`eid`),
  KEY `FK48E972E950959C1` (`mid`),
  KEY `FK48E972E124428C3` (`mcid`),
  KEY `FK48E972E269CC7F0` (`mcid`),
  CONSTRAINT `FK48E972E269CC7F0` FOREIGN KEY (`mcid`) REFERENCES `MemberCoupon` (`mcid`),
  CONSTRAINT `FK48E972E3ED4BF81` FOREIGN KEY (`eid`) REFERENCES `Event` (`eid`),
  CONSTRAINT `FK48E972E950959C1` FOREIGN KEY (`mid`) REFERENCES `Member` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `OrderEventSeat`
-- ----------------------------
DROP TABLE IF EXISTS `OrderEventSeat`;
CREATE TABLE `OrderEventSeat` (
  `oesid` int(11) NOT NULL AUTO_INCREMENT,
  `oid` int(11) NOT NULL,
  `esid` int(11) NOT NULL,
  `seatNumber` int(11) NOT NULL DEFAULT '0',
  `isValid` int(11) DEFAULT '1',
  PRIMARY KEY (`oesid`),
  UNIQUE KEY `OrderEventSeat_oesid_uindex` (`oesid`),
  KEY `FKEB6E9431D5560EF` (`esid`),
  KEY `FKEB6E943199A2A2BF` (`oid`),
  CONSTRAINT `FKEB6E943199A2A2BF` FOREIGN KEY (`oid`) REFERENCES `Order` (`oid`),
  CONSTRAINT `FKEB6E9431D5560EF` FOREIGN KEY (`esid`) REFERENCES `EventSeat` (`esid`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Seat`
-- ----------------------------
DROP TABLE IF EXISTS `Seat`;
CREATE TABLE `Seat` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `number` int(11) NOT NULL DEFAULT '0',
  `vid` int(11) NOT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `Seat_sid_uindex` (`sid`),
  KEY `FK274225DD370367` (`vid`),
  CONSTRAINT `Seat_Venue_vid_fk` FOREIGN KEY (`vid`) REFERENCES `Venue` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `Venue`
-- ----------------------------
DROP TABLE IF EXISTS `Venue`;
CREATE TABLE `Venue` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `isPassed` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`vid`),
  UNIQUE KEY `Venue_vid_uindex` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `VenueBook`
-- ----------------------------
DROP TABLE IF EXISTS `VenueBook`;
CREATE TABLE `VenueBook` (
  `vbid` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `amount` double NOT NULL DEFAULT '0',
  `isConfirmed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vbid`),
  UNIQUE KEY `VenueBook_vbid_uindex` (`vbid`),
  KEY `FK531769D8DD370367` (`vid`),
  KEY `FK531769D83ED4BF81` (`eid`),
  CONSTRAINT `FK531769D83ED4BF81` FOREIGN KEY (`eid`) REFERENCES `Event` (`eid`),
  CONSTRAINT `FK531769D8DD370367` FOREIGN KEY (`vid`) REFERENCES `Venue` (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
