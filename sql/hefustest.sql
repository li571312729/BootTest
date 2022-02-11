/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : hefustest

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-08-20 16:55:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for project_information
-- ----------------------------
DROP TABLE IF EXISTS `project_information`;
CREATE TABLE `project_information` (
  `project_id` int(64) NOT NULL AUTO_INCREMENT COMMENT '项目编号',
  `project_name` varchar(40) DEFAULT NULL COMMENT '项目名称',
  `project_type` varchar(40) DEFAULT NULL COMMENT '项目类型',
  `project_cost` varchar(40) DEFAULT NULL COMMENT '项目造价(万)',
  `project_aera` varchar(40) DEFAULT NULL COMMENT '项目面积(㎡)',
  `plan_date` datetime DEFAULT NULL COMMENT '计划施工日期',
  `plan_fin_date` datetime DEFAULT NULL COMMENT '计划竣工日期',
  `is_vaild` int(2) DEFAULT NULL COMMENT '校验',
  `cons_unit` varchar(40) DEFAULT NULL COMMENT '施工单位',
  `devp_unit` varchar(40) DEFAULT NULL COMMENT '建设单位',
  `version_unit` varchar(40) DEFAULT NULL COMMENT '监理单位',
  `design_unit` varchar(40) DEFAULT NULL COMMENT '设计单位',
  `survey_unit` varchar(40) DEFAULT NULL COMMENT '勘察单位',
  `project_ad` varchar(40) DEFAULT NULL COMMENT '项目所在地',
  `province` varchar(40) DEFAULT NULL COMMENT '省份',
  `project_thum_ad` varchar(40) DEFAULT NULL COMMENT '项目地址简写',
  `project_map` varchar(64) DEFAULT NULL COMMENT '地图坐标',
  `project_info` varchar(256) DEFAULT NULL COMMENT '项目详情',
  `figure` varchar(128) DEFAULT NULL COMMENT '项目全景图',
  `project_completion` varchar(40) DEFAULT NULL COMMENT '未监测;监测中;监测完成',
  `project_mananger` varchar(40) DEFAULT NULL COMMENT '项目经理',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`project_id`),
  KEY `idx_project_name` (`project_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_information
-- ----------------------------

-- ----------------------------
-- Table structure for sys_project_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_project_user`;
CREATE TABLE `sys_project_user` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT COMMENT '项目员工id',
  `project_id` int(64) DEFAULT NULL COMMENT '项目id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '员工id',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_project_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(64) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '职务名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '职务标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_valid` int(8) DEFAULT '1' COMMENT '状态: 0->已删除;1->有效 默认为1',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '项目管理员', 'manager', '超级管理员；在平台上创建项目、添加项目内反光背心设备、添加项目内用户；佩戴设备现场巡查；', '2020-05-27 10:45:00', '2020-07-21 10:00:36', '1');
INSERT INTO `sys_role` VALUES ('2', '安全员', 'safetyOfficer', '管理员；平台上查看项目信息、巡查告警记录，后台设备、用户信息，编辑告警记录；佩戴设备现场巡查；', '2020-05-27 10:45:41', '2020-07-16 15:05:52', '1');
INSERT INTO `sys_role` VALUES ('3', '其他用户', 'user', '普通用户；在平台上查看项目信息、巡查告警记录；', '2020-08-04 14:00:39', '2020-08-04 14:00:42', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `web_dept_id` int(10) DEFAULT NULL COMMENT '部门编号',
  `employee_work_num` varchar(32) DEFAULT NULL COMMENT '工号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `company` varchar(64) DEFAULT NULL COMMENT '所在公司',
  `position` varchar(32) DEFAULT NULL COMMENT '职务',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  KEY `index_web_dept_id` (`web_dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1291675597029842947 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2', 'wangwu', 'zhangsan', '/hoQ5FdsnbC0Dia5/6OOpQ==', '3', 'nn0003', '', '15894632424', '1', '', '', '2020-05-27 16:49:02', '2020-08-05 19:06:49');
INSERT INTO `sys_user` VALUES ('1291675597029842946', 'baili', null, '/hoQ5FdsnbC0Dia5/6OOpQ==', null, null, null, '15894632423', '1', null, null, '2020-08-07 18:00:35', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(64) DEFAULT NULL COMMENT '职务ID 1-超级管理员 2-管理员 3-普通用户',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) USING BTREE,
  KEY `index_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1291676721434677251 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1291676090602954753', '1291675597029842946', '1');
INSERT INTO `sys_user_role` VALUES ('1291676721434677250', '2', '2');

-- ----------------------------
-- Table structure for web_dept
-- ----------------------------
DROP TABLE IF EXISTS `web_dept`;
CREATE TABLE `web_dept` (
  `web_dept_id` int(64) NOT NULL AUTO_INCREMENT COMMENT '部门主键',
  `web_dept_name` varchar(32) DEFAULT NULL COMMENT '部门名称',
  `web_dept_remark` varchar(64) DEFAULT NULL COMMENT '部门备注',
  `dept_flag` tinyint(2) DEFAULT NULL COMMENT '0项目外1项目内',
  `is_valid` tinyint(2) DEFAULT NULL COMMENT '有效值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`web_dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of web_dept
-- ----------------------------
INSERT INTO `web_dept` VALUES ('1', '项目经理', '负责整个工程的全面管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('2', '项目副经理', '辅助负责整个工程的管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('3', '技术部', '技术支持', '1', '1', '2020-05-27 14:04:45', '2020-07-24 15:07:39');
INSERT INTO `web_dept` VALUES ('5', '施工部', '负责施工现场组织管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('6', '质量部', '负责施工质量管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('7', '安全部', '负责施工安全管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('8', '物资部', '负责物资管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('9', '商务部', '负责施工造价管理', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('11', '木工组', '负责模板工程施工', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('12', '钢筋工组', '负责钢筋工程施工', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('14', '电工组', '负责现场电力、电焊施工', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('16', '装修工组', '负责装修工程施工', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('17', '设备安装工组', '负责水暖电管道、设备安装施工', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('18', '机械工组', '负责现场机械设备操作、管理工作', '1', '1', '2020-05-27 14:04:45', '2020-05-27 14:04:45');
INSERT INTO `web_dept` VALUES ('19', '荷福安建', '建设公司', '0', '1', '2020-05-27 14:34:58', '2020-06-23 17:02:54');
INSERT INTO `web_dept` VALUES ('20', '平安', '保险公司', '0', '1', '2020-05-27 16:05:35', '2020-05-27 16:05:37');
INSERT INTO `web_dept` VALUES ('21', '前端开发部', 'Java开发', '0', '1', '2020-06-02 14:17:34', '2020-06-24 13:54:48');
INSERT INTO `web_dept` VALUES ('26', '荷福', null, '0', '1', '2020-06-23 17:07:09', '2020-06-24 13:53:58');
INSERT INTO `web_dept` VALUES ('37', '测试部', null, '0', '1', '2020-06-29 14:51:55', '2020-06-29 14:51:59');
INSERT INTO `web_dept` VALUES ('38', '荷福集团', null, '0', '1', '2020-07-06 17:19:43', '2020-07-06 17:19:43');
INSERT INTO `web_dept` VALUES ('40', '安保部', '保障安全', '1', '1', '2020-07-15 11:29:34', '2020-07-24 15:07:21');

-- ----------------------------
-- Table structure for web_menu
-- ----------------------------
DROP TABLE IF EXISTS `web_menu`;
CREATE TABLE `web_menu` (
  `web_menu_id` int(64) NOT NULL AUTO_INCREMENT COMMENT '资源id主键',
  `name` varchar(64) DEFAULT NULL COMMENT '资源名字',
  `parent_id` int(64) DEFAULT NULL COMMENT '父资源id',
  `component` varchar(32) DEFAULT NULL COMMENT '前端组件',
  `path` varchar(128) DEFAULT NULL COMMENT '资源路径',
  `perms` varchar(128) DEFAULT NULL COMMENT '权限标识',
  `menu_type` tinyint(2) DEFAULT NULL COMMENT '资源类型1模块 2 按钮',
  `is_valid` tinyint(2) DEFAULT NULL COMMENT '有效值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`web_menu_id`),
  KEY `index_web_menu_id` (`web_menu_id`) USING BTREE,
  KEY `index_menu_type` (`menu_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of web_menu
-- ----------------------------
INSERT INTO `web_menu` VALUES ('1', '项目模块', '0', 'Project', '/project', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('11', '项目概况', '1', 'ProjectOverview', '/projectOverview', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('12', '物联网监测&预测', '1', 'DataShow', '/dataShow', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('13', '行为识别', '1', 'AiMonitor', '/aiMonitor', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('14', '光谱模块', '1', 'Spectrum', '/spectrum', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('15', '5G网络模块', '1', 'Gfive', '/gFive', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('16', '专家资料', '1', 'Expert', '/expert', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('17', '疫情防控', '1', 'Illness', '/illness', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('151', '5G基站', '15', 'GfivePoint', '/gFivePoint', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('152', '5Gwifi', '15', 'GfiveWifi', '/gFiveWifi', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('171', '智慧测温&行为异常', '17', 'Action', '/action', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('172', '监测数据', '17', 'IllData', '/illData', null, '1', '1', null, null, null);
INSERT INTO `web_menu` VALUES ('173', '趋势预测', '17', 'Future', '/future', null, '1', '1', null, null, null);

-- ----------------------------
-- Table structure for web_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `web_role_menu`;
CREATE TABLE `web_role_menu` (
  `role_menu_id` bigint(8) NOT NULL AUTO_INCREMENT COMMENT '角色资源关联表主键',
  `role_id` int(64) DEFAULT NULL COMMENT '角色id',
  `web_menu_id` int(64) DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`role_menu_id`),
  KEY `index_role_id` (`role_id`) USING BTREE,
  KEY `index_menu_id` (`web_menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of web_role_menu
-- ----------------------------
INSERT INTO `web_role_menu` VALUES ('159', '1', '1');
INSERT INTO `web_role_menu` VALUES ('160', '1', '2');
INSERT INTO `web_role_menu` VALUES ('161', '1', '11');
INSERT INTO `web_role_menu` VALUES ('162', '1', '12');
INSERT INTO `web_role_menu` VALUES ('163', '1', '13');
INSERT INTO `web_role_menu` VALUES ('164', '1', '14');
INSERT INTO `web_role_menu` VALUES ('165', '1', '15');
INSERT INTO `web_role_menu` VALUES ('166', '1', '16');
INSERT INTO `web_role_menu` VALUES ('167', '1', '17');
INSERT INTO `web_role_menu` VALUES ('168', '1', '151');
INSERT INTO `web_role_menu` VALUES ('169', '1', '152');
INSERT INTO `web_role_menu` VALUES ('170', '1', '171');
INSERT INTO `web_role_menu` VALUES ('171', '1', '172');
INSERT INTO `web_role_menu` VALUES ('172', '1', '173');
INSERT INTO `web_role_menu` VALUES ('173', '1', '21');
INSERT INTO `web_role_menu` VALUES ('174', '1', '22');
INSERT INTO `web_role_menu` VALUES ('175', '2', '11');
INSERT INTO `web_role_menu` VALUES ('176', '2', '12');
INSERT INTO `web_role_menu` VALUES ('177', '2', '21');
INSERT INTO `web_role_menu` VALUES ('178', '3', '11');
INSERT INTO `web_role_menu` VALUES ('179', '3', '12');
INSERT INTO `web_role_menu` VALUES ('180', '3', '11');
INSERT INTO `web_role_menu` VALUES ('181', '3', '12');
INSERT INTO `web_role_menu` VALUES ('182', '3', '11');
INSERT INTO `web_role_menu` VALUES ('183', '3', '12');
