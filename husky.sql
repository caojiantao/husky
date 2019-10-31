/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : husky

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 31/10/2019 18:40:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `system_dictionary`;
CREATE TABLE `system_dictionary`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(12) NULL DEFAULT NULL,
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `order` int(5) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(12) NULL DEFAULT 0,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `href` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `icon_class` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `component_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `weight` int(5) NULL DEFAULT 0,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (1, 0, '系统管理', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (2, 1, '权限管理', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (3, 2, '菜单管理', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (4, 2, '角色管理', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (5, 2, '用户管理', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (6, 1, '定时任务', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (7, 6, '任务列表', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (8, 6, '任务日志', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (9, 1, '数据字典', NULL, NULL, NULL, 2, NULL, NULL);
INSERT INTO `system_menu` VALUES (10, 9, '字典集', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `system_menu` VALUES (11, 9, '字典值', NULL, NULL, NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for system_quartz
-- ----------------------------
DROP TABLE IF EXISTS `system_quartz`;
CREATE TABLE `system_quartz`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `cron_expression` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `job_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS `system_quartz_log`;
CREATE TABLE `system_quartz_log`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `quartz_id` int(12) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `extras` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `status` tinyint(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '管理员', '2019-10-31 18:36:06', '2019-10-31 18:36:10');

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` int(12) NULL DEFAULT NULL,
  `menu_id` int(12) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (1, 1, 1, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (2, 1, 2, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (3, 1, 3, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (5, 1, 5, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (6, 1, 6, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (7, 1, 7, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (8, 1, 8, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (9, 1, 9, '2019-10-31 18:36:24', '2019-10-31 18:36:27');
INSERT INTO `system_role_menu` VALUES (10, 1, 10, '2019-10-31 18:36:24', '2019-10-31 18:36:27');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'caojiantao', 'e10adc3949ba59abbe56e057f20f883e', '叫我宫城大人', '2019-10-24 18:27:54', '2019-10-24 18:27:54');

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `id` int(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(12) NULL DEFAULT NULL,
  `role_id` int(12) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1, 1, '2019-10-31 18:36:24', '2019-10-31 18:36:27');

SET FOREIGN_KEY_CHECKS = 1;
