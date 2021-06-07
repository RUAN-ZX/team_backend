/*
 Navicat Premium Data Transfer

 Source Server         : r630
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : krakens.tpddns.cn:13306
 Source Schema         : lets_team

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 07/06/2021 11:39:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorization_info
-- ----------------------------
DROP TABLE IF EXISTS `authorization_info`;
CREATE TABLE `authorization_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `auth_type` int(11) NOT NULL COMMENT '授权类型',
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权凭据',
  `secondary_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权次要凭据',
  `cipher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权密钥',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `au_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `authorization_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '竞赛主键',
  `comp_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛名称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛头像url',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛介绍文案',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '竞赛标签',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition_answer
-- ----------------------------
DROP TABLE IF EXISTS `competition_answer`;
CREATE TABLE `competition_answer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回答id',
  `author_user_id` int(11) NOT NULL COMMENT '回答者用户id',
  `question_id` int(11) NOT NULL COMMENT '提问id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答正文',
  `status` int(11) NOT NULL COMMENT '回答状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `post_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ca_author_user_id`(`author_user_id`) USING BTREE,
  INDEX `ca_question_id`(`question_id`) USING BTREE,
  CONSTRAINT `competition_answer_ibfk_1` FOREIGN KEY (`author_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `competition_answer_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `competition_question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition_experience_sharing
-- ----------------------------
DROP TABLE IF EXISTS `competition_experience_sharing`;
CREATE TABLE `competition_experience_sharing`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '竞赛经验分享id',
  `comp_id` int(11) NOT NULL COMMENT '竞赛id',
  `author_user_id` int(11) NOT NULL COMMENT '作者用户id',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '正文',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `post_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ces_comp_id`(`comp_id`) USING BTREE,
  INDEX `ces_author_user_id`(`author_user_id`) USING BTREE,
  CONSTRAINT `competition_experience_sharing_ibfk_1` FOREIGN KEY (`comp_id`) REFERENCES `competition` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `competition_experience_sharing_ibfk_2` FOREIGN KEY (`author_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition_experience_sharing_reply
-- ----------------------------
DROP TABLE IF EXISTS `competition_experience_sharing_reply`;
CREATE TABLE `competition_experience_sharing_reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目经验分享回复id',
  `author_user_id` int(11) NULL DEFAULT NULL COMMENT '作者id',
  `sharing_id` int(11) NULL DEFAULT NULL COMMENT '分享文章id',
  `reply_type` int(11) NULL DEFAULT NULL COMMENT '回复类型(直接回复or间接)',
  `reply_id` int(11) NULL DEFAULT NULL COMMENT '目标回复id',
  `reply_user_id` int(11) NULL DEFAULT NULL COMMENT '目标用户id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '回复正文',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `post_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cesr_author_user_id`(`author_user_id`) USING BTREE,
  INDEX `cesr_sharing_id`(`sharing_id`) USING BTREE,
  INDEX `cesr_reply_id`(`reply_id`) USING BTREE,
  INDEX `cesr_reply_user_id`(`reply_user_id`) USING BTREE,
  CONSTRAINT `competition_experience_sharing_reply_ibfk_1` FOREIGN KEY (`author_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `competition_experience_sharing_reply_ibfk_2` FOREIGN KEY (`sharing_id`) REFERENCES `competition_experience_sharing` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition_question
-- ----------------------------
DROP TABLE IF EXISTS `competition_question`;
CREATE TABLE `competition_question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '竞赛提问id',
  `comp_id` int(11) NOT NULL COMMENT '竞赛id',
  `author_user_id` int(11) NOT NULL COMMENT '提问者用户id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提问正文',
  `post_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cq_comp_id`(`comp_id`) USING BTREE,
  INDEX `cq_author_user_id`(`author_user_id`) USING BTREE,
  CONSTRAINT `competition_question_ibfk_1` FOREIGN KEY (`comp_id`) REFERENCES `competition` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `competition_question_ibfk_2` FOREIGN KEY (`author_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '站内信id',
  `session_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '站内信会话标识',
  `sender_user_id` int(11) NOT NULL COMMENT '发件人用户id',
  `receiver_user_id` int(11) NOT NULL COMMENT '收件人用户id',
  `message_type` int(11) NOT NULL COMMENT '站内信类型',
  `content_type` int(11) NOT NULL COMMENT '站内信正文类型',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '站内信正文',
  `sending_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '站内信发送时间',
  `status` int(2) NOT NULL COMMENT '站内信状态 未读 已读 软删除（last message代表整个会话）',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '站内信被阅读时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `msg_sender_user_id`(`sender_user_id`) USING BTREE,
  INDEX `msg_receiver_user_id`(`receiver_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receiver_user_id` int(11) NOT NULL COMMENT '收信人用户id',
  `type` int(11) NOT NULL COMMENT '通知类型',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '通知正文',
  `sending_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发送时间',
  `read_status` int(11) NOT NULL COMMENT '阅读状态',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '阅读时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `no_receiver_user_id`(`receiver_user_id`) USING BTREE,
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`receiver_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `school_id` int(11) NULL DEFAULT NULL COMMENT '学校id',
  `org_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织名称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织头像url',
  `creator_user_id` int(11) NOT NULL COMMENT '组织创建人用户id',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织介绍文案',
  `org_level` int(11) NOT NULL COMMENT '组织级别',
  `status` int(11) NOT NULL COMMENT '组织状态',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '组织创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '组织更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for organization_auditing_request
-- ----------------------------
DROP TABLE IF EXISTS `organization_auditing_request`;
CREATE TABLE `organization_auditing_request`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织审核请求id',
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `user_id` int(11) NOT NULL COMMENT '提请审核用户id',
  `org_level` int(11) NOT NULL COMMENT '组织级别',
  `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核材料url',
  `status` int(11) NOT NULL COMMENT '审核状态',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '请求创建时间',
  `auditor_user_id` int(11) NULL DEFAULT NULL COMMENT '审核人用户id',
  `auditing_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for organization_member
-- ----------------------------
DROP TABLE IF EXISTS `organization_member`;
CREATE TABLE `organization_member`  (
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `org_role_id` int(11) NULL DEFAULT NULL COMMENT '组织角色id',
  `entering_time` datetime(0) NULL DEFAULT NULL COMMENT '加入时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`org_id`, `user_id`) USING BTREE,
  INDEX `om_user_id`(`user_id`) USING BTREE,
  INDEX `om_role_id`(`org_role_id`) USING BTREE,
  CONSTRAINT `organization_member_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `organization_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `organization_member_ibfk_3` FOREIGN KEY (`org_role_id`) REFERENCES `organization_role` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for organization_role
-- ----------------------------
DROP TABLE IF EXISTS `organization_role`;
CREATE TABLE `organization_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织角色id',
  `org_role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for talent_info
-- ----------------------------
DROP TABLE IF EXISTS `talent_info`;
CREATE TABLE `talent_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '人才信息id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户标签',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人才信息简介',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '人才信息正文',
  `post_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `require_status` int(11) NOT NULL COMMENT '需求状态',
  `closed_time` datetime(0) NULL DEFAULT NULL COMMENT '关闭时间',
  `status` int(11) NOT NULL COMMENT '状态(是否关闭)',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tr_user_id`(`user_id`) USING BTREE,
  INDEX `tr_id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for talent_info_competition
-- ----------------------------
DROP TABLE IF EXISTS `talent_info_competition`;
CREATE TABLE `talent_info_competition`  (
  `requirement_id` int(11) NOT NULL COMMENT '人才信息id',
  `comp_id` int(11) NOT NULL COMMENT '竞赛id',
  PRIMARY KEY (`requirement_id`, `comp_id`) USING BTREE,
  INDEX `trc_comp_id`(`comp_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for talent_info_resume_basic
-- ----------------------------
DROP TABLE IF EXISTS `talent_info_resume_basic`;
CREATE TABLE `talent_info_resume_basic`  (
  `talent_info_id` int(11) NOT NULL COMMENT '人才信息id',
  `true_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户真实姓名',
  `school_id` int(11) NULL DEFAULT NULL COMMENT '用户学校id',
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所学专业',
  `admission_date` datetime(0) NULL DEFAULT NULL COMMENT '用户入学时间',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户标签',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户自我介绍',
  `homepage_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户个人主页',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`talent_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for talent_info_resume_honor
-- ----------------------------
DROP TABLE IF EXISTS `talent_info_resume_honor`;
CREATE TABLE `talent_info_resume_honor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `talent_info_id` int(11) NULL DEFAULT NULL COMMENT '人才信息id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '荣誉名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '荣誉类型',
  `comp_id` int(11) NULL DEFAULT NULL COMMENT '竞赛id',
  `level` int(11) NULL DEFAULT NULL COMMENT '荣誉级别',
  `acquiring_time` datetime(0) NULL DEFAULT NULL COMMENT '获得时间',
  `supporting_document_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证明文件url',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '荣誉描述',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `urrh_requirement_id`(`talent_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队id',
  `school_id` int(11) NULL DEFAULT NULL COMMENT '学校id',
  `team_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队名称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '团队头像url',
  `leader_user_id` int(11) NOT NULL COMMENT '团队负责人用户id',
  `org_id` int(11) NULL DEFAULT NULL COMMENT '团队所在机构id',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队标签',
  `level` int(11) NOT NULL COMMENT '团队级别',
  `team_type` int(11) NOT NULL COMMENT '团队类型',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队介绍',
  `status` int(11) NOT NULL COMMENT '团队状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_honor
-- ----------------------------
DROP TABLE IF EXISTS `team_honor`;
CREATE TABLE `team_honor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队荣誉id',
  `team_id` int(11) NOT NULL COMMENT '团队id',
  `honor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '荣誉名称',
  `honor_type` int(11) NOT NULL COMMENT '荣誉类型',
  `comp_id` int(11) NULL DEFAULT NULL COMMENT '关联竞赛id',
  `level` int(11) NULL DEFAULT NULL COMMENT '荣誉级别',
  `acquiring_time` datetime(0) NOT NULL COMMENT '取得时间',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_member
-- ----------------------------
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member`  (
  `team_id` int(11) NOT NULL COMMENT '团队表主键',
  `user_id` int(11) NOT NULL COMMENT '团队成员用户id',
  `duty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团队成员职责',
  `permission_level` int(11) NOT NULL COMMENT '团队成员权限等级',
  `entering_time` datetime(0) NOT NULL COMMENT '加入时间',
  `ranking` int(11) NOT NULL COMMENT '成员顺位',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`team_id`, `user_id`) USING BTREE,
  INDEX `pm_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `team_member_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `team_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_recruitment
-- ----------------------------
DROP TABLE IF EXISTS `team_recruitment`;
CREATE TABLE `team_recruitment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队招募帖主键',
  `team_id` int(11) NOT NULL COMMENT '对应团队id',
  `creator_user_id` int(11) NOT NULL COMMENT '发布者用户id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '招募需求文字说明',
  `post_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `recruit_status` int(11) NULL DEFAULT NULL COMMENT '招募状态',
  `closed_time` datetime(0) NULL DEFAULT NULL COMMENT '招募结束时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pr_project_id`(`team_id`) USING BTREE,
  CONSTRAINT `team_recruitment_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_recruitment_competition
-- ----------------------------
DROP TABLE IF EXISTS `team_recruitment_competition`;
CREATE TABLE `team_recruitment_competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队招募需求比赛id',
  `recruitment_id` int(11) NOT NULL COMMENT '团队招募需求id',
  `comp_id` int(11) NOT NULL COMMENT '竞赛id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_recruitment_detail
-- ----------------------------
DROP TABLE IF EXISTS `team_recruitment_detail`;
CREATE TABLE `team_recruitment_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队招募帖详情id',
  `recruitment_id` int(11) NOT NULL COMMENT '团队招募帖id',
  `duty` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '成员职责',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '招募要求说明',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '招募标签',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '散列后的用户密码',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `register_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '用户注册时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '用户最后一次登录时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `is_frozen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `u_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` int(11) NOT NULL COMMENT '主键',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户标签',
  `signature` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `experience` int(11) NOT NULL DEFAULT 0 COMMENT '用户经验值',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_resume_basic
-- ----------------------------
DROP TABLE IF EXISTS `user_resume_basic`;
CREATE TABLE `user_resume_basic`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `true_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户真实姓名',
  `school_id` int(11) NULL DEFAULT NULL COMMENT '用户学校id',
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所学专业',
  `admission_date` datetime(0) NULL DEFAULT NULL COMMENT '用户入学时间',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户标签',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户自我介绍',
  `homepage_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户个人主页',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `user_resume_basic_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_resume_honor
-- ----------------------------
DROP TABLE IF EXISTS `user_resume_honor`;
CREATE TABLE `user_resume_honor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `honor_type` int(11) NOT NULL COMMENT '荣誉类型',
  `honor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '荣誉名称',
  `acquiring_time` datetime(0) NULL DEFAULT NULL COMMENT '获得时间',
  `supporting_document_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证明文件url',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '荣誉描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `urh_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_resume_honor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
