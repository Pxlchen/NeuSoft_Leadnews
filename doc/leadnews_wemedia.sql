/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : leadnews_wemedia

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 14/09/2023 10:18:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wm_channel
-- ----------------------------
DROP TABLE IF EXISTS `wm_channel`;
CREATE TABLE `wm_channel`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '频道名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '频道描述',
  `is_default` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否默认频道',
  `status` tinyint(1) UNSIGNED NULL DEFAULT NULL,
  `ord` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '默认排序',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '频道信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_channel
-- ----------------------------
INSERT INTO `wm_channel` VALUES (1, '数字媒体与设计学院', '本科', 1, 1, 1, '2021-04-18 12:25:30');
INSERT INTO `wm_channel` VALUES (2, '商务管理学院', '本科', 1, 1, 4, '2021-04-18 10:55:41');
INSERT INTO `wm_channel` VALUES (3, '外国语学院', '本科', 1, 1, 5, '2021-04-18 10:55:41');
INSERT INTO `wm_channel` VALUES (4, '计算机学院', '本科', 1, 1, 6, '2021-04-18 10:55:41');
INSERT INTO `wm_channel` VALUES (5, '国际教育学院', '本科', 1, 1, 7, '2021-04-18 10:55:41');
INSERT INTO `wm_channel` VALUES (6, '其他', '其他', 1, 1, 12, '2021-04-18 10:55:41');

-- ----------------------------
-- Table structure for wm_fans_statistics
-- ----------------------------
DROP TABLE IF EXISTS `wm_fans_statistics`;
CREATE TABLE `wm_fans_statistics`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '主账号ID',
  `article` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '子账号ID',
  `read_count` int(11) UNSIGNED NULL DEFAULT NULL,
  `comment` int(11) UNSIGNED NULL DEFAULT NULL,
  `follow` int(11) UNSIGNED NULL DEFAULT NULL,
  `collection` int(11) UNSIGNED NULL DEFAULT NULL,
  `forward` int(11) UNSIGNED NULL DEFAULT NULL,
  `likes` int(11) UNSIGNED NULL DEFAULT NULL,
  `unlikes` int(11) UNSIGNED NULL DEFAULT NULL,
  `unfollow` int(11) UNSIGNED NULL DEFAULT NULL,
  `burst` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_time` date NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_id_time`(`user_id`, `created_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自媒体粉丝数据统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_fans_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for wm_material
-- ----------------------------
DROP TABLE IF EXISTS `wm_material`;
CREATE TABLE `wm_material`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '自媒体用户ID',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '素材类型\r\n            0 图片\r\n            1 视频',
  `is_collection` int(1) NULL DEFAULT NULL COMMENT '是否收藏',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自媒体图文素材信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_material
-- ----------------------------
INSERT INTO `wm_material` VALUES (4, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/af9ece59e96c416d847e30a739d2a17e.jpg', 0, 1, '2023-09-13 21:29:10');
INSERT INTO `wm_material` VALUES (5, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/83bb1a67c1d44bdd8b1eaf47a1dbe521.jpg', 0, 1, '2023-09-13 21:29:23');
INSERT INTO `wm_material` VALUES (6, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/ffc723b6baee48c1a5128615dc56ef40.jpg', 0, 0, '2023-09-13 21:29:57');
INSERT INTO `wm_material` VALUES (7, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/daf92f143d1842e7aa740fdc7090d3a7.jpg', 0, 0, '2023-09-13 21:30:23');
INSERT INTO `wm_material` VALUES (8, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/24621c04fe7949b9bffd099b157875d9.png', 0, 0, '2023-09-13 21:30:30');
INSERT INTO `wm_material` VALUES (9, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/33be911ea4934f06b7164667d4ba865b.jpg', 0, 1, '2023-09-13 21:46:55');
INSERT INTO `wm_material` VALUES (10, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/4ed07fd214f64653958d38bdda5bc20d.png', 0, 0, '2023-09-13 22:30:49');
INSERT INTO `wm_material` VALUES (11, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/afe93f2df3e64248a844ca07f71005b1.jpg', 0, 0, '2023-09-13 22:35:26');
INSERT INTO `wm_material` VALUES (12, 1102, 'http://192.168.200.130:9000/leadnews/2023/09/13/e850123f209f435ba1f1ff8379aa3795.png', 0, 0, '2023-09-13 22:36:18');

-- ----------------------------
-- Table structure for wm_news
-- ----------------------------
DROP TABLE IF EXISTS `wm_news`;
CREATE TABLE `wm_news`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '自媒体用户ID',
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '图文内容',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '文章布局\r\n            0 无图文章\r\n            1 单图文章\r\n            3 多图文章',
  `channel_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '图文频道ID',
  `labels` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `submited_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `status` tinyint(2) UNSIGNED NULL DEFAULT NULL COMMENT '当前状态\r\n            0 草稿\r\n            1 提交（待审核）\r\n            2 审核失败\r\n            3 人工审核\r\n            4 人工审核通过\r\n            8 审核通过（待发布）\r\n            9 已发布',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '定时发布时间，不定时则为空',
  `reason` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '拒绝理由',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布库文章ID',
  `images` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '//图片用逗号分隔',
  `enable` tinyint(1) UNSIGNED NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6242 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自媒体图文内容信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_news
-- ----------------------------
INSERT INTO `wm_news` VALUES (6239, 1102, '这个暑假，他们为乡村学子“筑梦”', '[{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/leadnews/2023/09/13/33be911ea4934f06b7164667d4ba865b.jpg\"},{\"type\":\"text\",\"value\":\"又到一年开学季。今年暑假，为全面贯彻党的二十大提出“完善覆盖全学段学生资助体系”的要求，积极响应国家资助和助学贷款政策下乡行的号召，加大广东省国家资助和助学贷款政策的宣传力度，让国家资助政策更加家喻户晓、深入人心，真正惠及到每一名家庭经济困难学生，巩固教育脱贫攻坚成果，发挥“扶智”“扶志”的重要作用，广东省教育厅开展了“国家资助和助学贷款政策下乡行”活动。来自全省139所高校共343支下乡行队伍的几千名大学生志愿者走进乡村，将资助政策带入各地贫困家庭，助力更多乡村学子圆求学之梦。\"},{\"type\":\"text\",\"value\":\"请输入文章内容...\"}]', 1, NULL, '下乡行活动', '2023-09-13 22:28:21', '2023-09-13 22:28:21', 1, NULL, NULL, NULL, 'http://192.168.200.130:9000/leadnews/2023/09/13/83bb1a67c1d44bdd8b1eaf47a1dbe521.jpg', 1);
INSERT INTO `wm_news` VALUES (6240, 1102, '我校美术类最低录取排位大幅提升', '[{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/leadnews/2023/09/13/4ed07fd214f64653958d38bdda5bc20d.png\"},{\"type\":\"text\",\"value\":\"广东省本科批次艺术类录取已投档，我校4个美术类专业开档即满，各专业录取最低分数如下：\"},{\"type\":\"text\",\"value\":\"请输入文章内容...\"}]', 1, NULL, '投档排位', '2023-09-13 22:32:21', '2023-09-13 22:32:21', 1, NULL, NULL, NULL, 'http://192.168.200.130:9000/leadnews/2023/09/13/24621c04fe7949b9bffd099b157875d9.png', 1);
INSERT INTO `wm_news` VALUES (6241, 1102, '刚刚，我们收到了一封信...', '[{\"type\":\"text\",\"value\":\"广东东软学院文化艺术展开展以来，受到广大师生的喜欢，刚刚我校更收到了一封观众来信，信中讲述自己的观展感受，从观众视角对几位参展画家作品进行赏析，在此分享给广大师生们，望能从中收获些许启发，打开艺术鉴赏之门。\"},{\"type\":\"image\",\"value\":\"http://192.168.200.130:9000/leadnews/2023/09/13/e850123f209f435ba1f1ff8379aa3795.png\"},{\"type\":\"text\",\"value\":\"请输入文章内容...\"}]', 1, NULL, '文化艺术展', '2023-09-13 22:36:38', '2023-09-13 22:36:38', 0, NULL, NULL, NULL, 'http://192.168.200.130:9000/leadnews/2023/09/13/afe93f2df3e64248a844ca07f71005b1.jpg', 1);

-- ----------------------------
-- Table structure for wm_news_material
-- ----------------------------
DROP TABLE IF EXISTS `wm_news_material`;
CREATE TABLE `wm_news_material`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `material_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '素材ID',
  `news_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '图文ID',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '引用类型\r\n            0 内容引用\r\n            1 主图引用',
  `ord` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '引用排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 291 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自媒体图文引用素材信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_news_material
-- ----------------------------
INSERT INTO `wm_news_material` VALUES (263, 61, 6231, 0, 0);
INSERT INTO `wm_news_material` VALUES (264, 61, 6231, 1, 0);
INSERT INTO `wm_news_material` VALUES (265, 57, 6230, 0, 0);
INSERT INTO `wm_news_material` VALUES (266, 61, 6230, 0, 1);
INSERT INTO `wm_news_material` VALUES (267, 61, 6230, 1, 0);
INSERT INTO `wm_news_material` VALUES (268, 58, 6229, 0, 0);
INSERT INTO `wm_news_material` VALUES (269, 58, 6229, 1, 0);
INSERT INTO `wm_news_material` VALUES (270, 62, 6228, 0, 0);
INSERT INTO `wm_news_material` VALUES (271, 62, 6228, 1, 0);
INSERT INTO `wm_news_material` VALUES (272, 66, 6227, 0, 0);
INSERT INTO `wm_news_material` VALUES (273, 66, 6227, 1, 0);
INSERT INTO `wm_news_material` VALUES (274, 57, 6226, 0, 0);
INSERT INTO `wm_news_material` VALUES (275, 64, 6226, 0, 1);
INSERT INTO `wm_news_material` VALUES (276, 65, 6226, 1, 0);
INSERT INTO `wm_news_material` VALUES (277, 64, 6226, 1, 1);
INSERT INTO `wm_news_material` VALUES (278, 57, 6226, 1, 2);
INSERT INTO `wm_news_material` VALUES (279, 65, 6225, 0, 0);
INSERT INTO `wm_news_material` VALUES (280, 65, 6225, 1, 0);
INSERT INTO `wm_news_material` VALUES (287, 9, 6239, 0, 0);
INSERT INTO `wm_news_material` VALUES (288, 5, 6239, 1, 0);
INSERT INTO `wm_news_material` VALUES (289, 10, 6240, 0, 0);
INSERT INTO `wm_news_material` VALUES (290, 8, 6240, 1, 0);

-- ----------------------------
-- Table structure for wm_news_statistics
-- ----------------------------
DROP TABLE IF EXISTS `wm_news_statistics`;
CREATE TABLE `wm_news_statistics`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '主账号ID',
  `article` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '子账号ID',
  `read_count` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '阅读量',
  `comment` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '评论量',
  `follow` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '关注量',
  `collection` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '收藏量',
  `forward` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '转发量',
  `likes` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '点赞量',
  `unlikes` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '不喜欢',
  `unfollow` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '取消关注量',
  `burst` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_time` date NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_id_time`(`user_id`, `created_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自媒体图文数据统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_news_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for wm_user
-- ----------------------------
DROP TABLE IF EXISTS `wm_user`;
CREATE TABLE `wm_user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ap_user_id` int(11) NULL DEFAULT NULL,
  `ap_author_id` int(11) NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录用户名',
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '盐',
  `nickname` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `location` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '归属地',
  `phone` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(11) UNSIGNED NULL DEFAULT NULL COMMENT '状态\r\n            0 暂时不可用\r\n            1 永久不可用\r\n            9 正常可用',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '账号类型\r\n            0 个人 \r\n            1 企业\r\n            2 子账号',
  `score` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '运营评分',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1120 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '自媒体用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wm_user
-- ----------------------------
INSERT INTO `wm_user` VALUES (1100, NULL, NULL, 'zhangsan', 'ab8c7c1e66a164ab6891b927550ea39a', 'abc', '小张', NULL, NULL, '13588996789', 1, NULL, NULL, NULL, '2020-02-17 23:51:15', '2020-02-17 23:51:18');
INSERT INTO `wm_user` VALUES (1101, NULL, NULL, 'lisi', 'a6ecab0c246bbc87926e0fba442cc014', 'def', '小李', NULL, NULL, '13234567656', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wm_user` VALUES (1102, NULL, NULL, 'admin', 'a66abb5684c45962d887564f08346e8d', '123456', '管理', NULL, NULL, '13234567657', 1, NULL, NULL, NULL, NULL, '2020-03-14 09:35:13');
INSERT INTO `wm_user` VALUES (1118, NULL, NULL, 'lisi1', '123', '123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wm_user` VALUES (1119, NULL, NULL, 'shaseng', '1234', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
