/*
 Navicat Premium Data Transfer

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : coder

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 01/12/2022 20:45:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administer
-- ----------------------------
DROP TABLE IF EXISTS `administer`;
CREATE TABLE `administer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(63)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of administer
-- ----------------------------

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `scale` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1-99' COMMENT '公司人數',
  `type` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `legal_person` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, '滢3-601', '4', '私营', '徐文涛');

-- ----------------------------
-- Table structure for hr
-- ----------------------------
DROP TABLE IF EXISTS `hr`;
CREATE TABLE `hr`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(63)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` tinyint(4) NULL DEFAULT NULL,
  `birthday` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hr
-- ----------------------------
INSERT INTO `hr` VALUES (1, '徐文涛', '123456789@fox.com', '12345678910', '123456', '123456', 0, '2001-07-12', 1);

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator_id` int(11) NOT NULL,
  `name` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `company_id` int(11) NOT NULL,
  `description` text  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recruit_number` int(11) NOT NULL,
  `experience_requirement` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '无',
  `education_requirement` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '无',
  `first_categories` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `second_categories` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tag` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '職位標簽',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (1, 1, '测试工程师', 1, '测试', 1, '无', '无', '测试', '测试工程师', '测试');
INSERT INTO `job` VALUES (2, 1, '测试工程师', 1, '测试，狠狠的测试', 1, '无', '无', '测试', '测试工程师', '测试');
INSERT INTO `job` VALUES (4, 1, 'hgj', 1, 'ghjfgh', 500, '经验不限', '中专/中技', '项目管理', '实施顾问', '无');

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `file_url` varchar(255)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_public` tinyint(4) NOT NULL DEFAULT 0,
  `date` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of profile
-- ----------------------------
INSERT INTO `profile` VALUES (1, 1, 'http://localhost:8080/uploadFile/2022/11/30/00c411ca-6ec9-422e-8089-c9c72c12e1e4.pdf', 1, NULL);
INSERT INTO `profile` VALUES (2, 1, 'http://localhost:8080/uploadFile/2022/11/30/b5f8a1ef-975d-46c3-b9bc-5ec33f470f6b.pdf', 1, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(63)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` tinyint(4) NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'havoc', '18694866@fox.com', '568846168', '111111', '', 1, '2022-11-24');

-- ----------------------------
-- Table structure for user_job
-- ----------------------------
DROP TABLE IF EXISTS `user_job`;
CREATE TABLE `user_job`  (
  `user_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `state` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_cancel` tinyint(4) NOT NULL,
  `reason` varchar(31)  CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_modify_date` datetime NOT NULL,
  PRIMARY KEY (`user_id`, `job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_job
-- ----------------------------
INSERT INTO `user_job` VALUES (1, 1, 'pass', 0, 'null', '2022-11-26 23:42:14');
INSERT INTO `user_job` VALUES (1, 2, 'reject', 0, 'null', '2022-11-26 23:54:00');

SET FOREIGN_KEY_CHECKS = 1;
