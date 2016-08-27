/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : apidb

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-08-27 09:53:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
`id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`codeName`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`codeValue`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=2007

;

-- ----------------------------
-- Records of code
-- ----------------------------
BEGIN;
INSERT INTO `code` VALUES ('1', '100101', '缺少apiKey'), ('2', '100102', 'apiKey与用户不匹配'), ('3', '100103', '无效的apiKey'), ('4', '100104', '缺少参数 : '), ('5', '100105', '查询次数达到上限'), ('6', '100106', '服务器内部错误'), ('7', '100107', '缺少userID'), ('1001', '101001', '缺少service或service格式不正确'), ('2000', '102000', '查询成功'), ('2001', '102001', '查询失败'), ('2002', '102002', '请求参数错误'), ('2003', '102003', '没有此身份证'), ('2004', '102004', '身份证号错误'), ('2005', '102005', '姓名或者身份证号码不正确'), ('2006', '102006', '未查询到数据');
COMMIT;

-- ----------------------------
-- Table structure for d_m_sfzxx
-- ----------------------------
DROP TABLE IF EXISTS `d_m_sfzxx`;
CREATE TABLE `d_m_sfzxx` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`idCardNo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证' ,
`returnTyp`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回值类型' ,
`ret_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名' ,
`ret_idCardNo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号' ,
`ret_status`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否一致' ,
`attr1`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr2`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr3`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr4`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr5`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`localApiID`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=11

;

-- ----------------------------
-- Records of d_m_sfzxx
-- ----------------------------
BEGIN;
INSERT INTO `d_m_sfzxx` VALUES ('10', '王梓', '120103198603292638', '2', '王梓', '120103198603292638', '一致', null, null, null, null, null, '1');
COMMIT;

-- ----------------------------
-- Table structure for d_s_qlproexe
-- ----------------------------
DROP TABLE IF EXISTS `d_s_qlproexe`;
CREATE TABLE `d_s_qlproexe` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`exeName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证' ,
`isExactlySame`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pageSize`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pageIndex`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`key`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`dtype`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`returnTyp`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回值类型' ,
`returnVal`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回值' ,
`attr1`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr2`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr3`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr4`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attr5`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`localApiID`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=6

;

-- ----------------------------
-- Records of d_s_qlproexe
-- ----------------------------
BEGIN;
INSERT INTO `d_s_qlproexe` VALUES ('5', '王健林', null, null, null, null, null, '2', '{\"Result\":{\"ShiXinResult\":{\"Paging\":{\"PageSize\":10,\"PageIndex\":1,\"TotalRecords\":0},\"Items\":[]},\"ZhiXingResult\":{\"Paging\":{\"PageSize\":10,\"PageIndex\":1,\"TotalRecords\":19},\"Items\":[{\"id\":\"07d9fc336a2fe09b5bb9575849ae4984\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2010-08-06T00:00:00+08:00\",\"case_code\":\"(2010)辽中执字第00833号\",\"focusnumber\":0,\"court_name\":\"辽中县人民法院\",\"target\":\"15112\",\"case_state\":\"1\",\"dp_card\":\"21012219800308\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"73112bbce9d24f8cd43ef81eaa35c83d\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2010-08-06T00:00:00+08:00\",\"case_code\":\"(2010)辽中执字第00832号\",\"focusnumber\":0,\"court_name\":\"辽中县人民法院\",\"target\":\"30422\",\"case_state\":\"1\",\"dp_card\":\"21012219800308\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"627c28cd01a9fd3dda39b7b1f0890c0c\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2010-08-09T00:00:00+08:00\",\"case_code\":\"(2010)辽中执字第00886号\",\"focusnumber\":0,\"court_name\":\"辽中县人民法院\",\"target\":\"15112\",\"case_state\":\"1\",\"dp_card\":\"21012219800308\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"1907d9b364a3698f930f977e3353f369\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2010-08-06T00:00:00+08:00\",\"case_code\":\"(2010)辽中执字第00830号\",\"focusnumber\":0,\"court_name\":\"辽中县人民法院\",\"target\":\"7850\",\"case_state\":\"1\",\"dp_card\":\"21012219800308\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"97647e3814f4577a6bb94812550c8cdc\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2010-08-05T00:00:00+08:00\",\"case_code\":\"(2010)辽中执字第00824号\",\"focusnumber\":0,\"court_name\":\"辽中县人民法院\",\"target\":\"30422\",\"case_state\":\"1\",\"dp_card\":\"21012219800308\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"f432ea1d5fe99a696ff0cd6500e3ae86\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2014-09-02T00:00:00+08:00\",\"case_code\":\"(2014)梁法执字第00548号\",\"focusnumber\":0,\"court_name\":\"梁园区人民法院\",\"target\":\"40031\",\"case_state\":\"1\",\"dp_card\":\"4123011\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"b5caaa2f78acf5531c759763d37d02d4\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2009-04-14T00:00:00+08:00\",\"case_code\":\"(2009)杭临民执字第00900号\",\"focusnumber\":0,\"court_name\":\"临安市人民法院\",\"target\":\"281874.97\",\"case_state\":\"0\",\"dp_card\":\"\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"56ba7ba921c9d159e8b777d974e58adf\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2012-02-24T00:00:00+08:00\",\"case_code\":\"(2012)界执字第00118号\",\"focusnumber\":0,\"court_name\":\"界首市人民法院\",\"target\":\"40740.9\",\"case_state\":\"1\",\"dp_card\":\"3421011963****80812\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"55f704e628ef4ae26dfb4e9ae18757ca\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2015-08-10T00:00:00+08:00\",\"case_code\":\"(2015)洛川执字第00102号\",\"focusnumber\":0,\"court_name\":\"洛川县人民法院\",\"target\":\"212135.48\",\"case_state\":\"0\",\"dp_card\":\"/\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"},{\"id\":\"cefd01170de8e4ef48ad14a3d1c35424\",\"sourceid\":0,\"dp_name\":\"王健林\",\"reg_date\":\"2013-05-15T00:00:00+08:00\",\"case_code\":\"(2013)围执执字第00392号\",\"focusnumber\":0,\"court_name\":\"围场满族蒙古族自治县人民法院\",\"target\":\"88000\",\"case_state\":\"1\",\"dp_card\":\"39202\",\"createdate\":\"0001-01-01T00:00:00\",\"updatedate\":\"0001-01-01T00:00:00\"}]}},\"Paging\":null,\"keyword\":\"王健林\",\"Msg\":\"查询成功\",\"Code\":\"000000\"}', null, null, null, null, null, '2');
COMMIT;

-- ----------------------------
-- Table structure for localapi
-- ----------------------------
DROP TABLE IF EXISTS `localapi`;
CREATE TABLE `localapi` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`service`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`params`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数列表' ,
`returnType`  int(11) NOT NULL COMMENT '1 String\r\n2 json\r\n3 xml' ,
`returnParam`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回参数列表' ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of localapi
-- ----------------------------
BEGIN;
INSERT INTO `localapi` VALUES ('1', 'm-sfzxx', 'http://192.168.62.47:8080/ApiData/r/queryApi/get', '[{\"paramName\":\"apiKey\",\"paramType\":\"head\",\"notNull\":\"yes\"},{\"paramName\":\"idCardNo\",\"paramType\":\"url\",\"notNull\":\"yes\"},{\"paramName\":\"name\",\"paramType\":\"url\",\"notNull\":\"no\"}]', '2', 'ret_idCardNo,ret_name,ret_status'), ('2', 's-qlproexe', 'http://192.168.62.47:8080/ApiData/r/queryApi/get', '[{\"paramName\":\"exeName\",\"paramType\":\"url\",\"notNull\":\"yes\"},{\"paramName\":\"isExactlySame\",\"paramType\":\"url\",\"notNull\":\"no\"},{\"paramName\":\"pageSize\",\"paramType\":\"url\",\"notNull\":\"no\"},{\"paramName\":\"pageIndex\",\"paramType\":\"url\",\"notNull\":\"no\"},{\"paramName\":\"key\",\"paramType\":\"url\",\"notNull\":\"no\"},{\"paramName\":\"dtype\",\"paramType\":\"url\",\"notNull\":\"no\"}]', '2', '');
COMMIT;

-- ----------------------------
-- Table structure for p_baseinfo
-- ----------------------------
DROP TABLE IF EXISTS `p_baseinfo`;
CREATE TABLE `p_baseinfo` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`personID`  int(11) NULL DEFAULT NULL COMMENT 'person表ID' ,
`photo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片' ,
`tel`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号' ,
`address`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址' ,
`createUser`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateUser`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateTime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`),
UNIQUE INDEX `personIDUnique` (`personID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=9

;

-- ----------------------------
-- Records of p_baseinfo
-- ----------------------------
BEGIN;
INSERT INTO `p_baseinfo` VALUES ('7', '17', '', '15922041128', '人民银行', 'wangzi', '2016-08-12 16:32:27', 'wangzi', '2016-08-15 10:46:18'), ('8', '18', '\\ApiData\\files\\photos\\1471586292779.jpg', '', '', 'wangzi', '2016-08-19 10:53:43', 'wangzi', '2016-08-19 13:58:31');
COMMIT;

-- ----------------------------
-- Table structure for p_dishonestylist
-- ----------------------------
DROP TABLE IF EXISTS `p_dishonestylist`;
CREATE TABLE `p_dishonestylist` (
`ID`  int(11) NOT NULL ,
`court`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行法院' ,
`province`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份' ,
`caseNo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案例号' ,
`obligation`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生效法律文书确定的义务' ,
`performStatus`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '履行状况' ,
`specificBehavior`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失信行为' ,
`releaseTime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间' ,
`createTime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createUser`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateTime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateUser`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of p_dishonestylist
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for p_person
-- ----------------------------
DROP TABLE IF EXISTS `p_person`;
CREATE TABLE `p_person` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`idCardNo`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号' ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名' ,
`creditPoint`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ' 信用积分' ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=19

;

-- ----------------------------
-- Records of p_person
-- ----------------------------
BEGIN;
INSERT INTO `p_person` VALUES ('17', '120103198603292638', '王梓', null), ('18', '', '', null);
COMMIT;

-- ----------------------------
-- Table structure for p_redit
-- ----------------------------
DROP TABLE IF EXISTS `p_redit`;
CREATE TABLE `p_redit` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`personID`  int(11) NULL DEFAULT NULL COMMENT 'person表ID' ,
`contactDate`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同日期' ,
`loanDate`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '放款日期' ,
`hireDate`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '起租日' ,
`expireDate`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '到期日' ,
`loanUsed`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '贷款用途' ,
`totalAmount`  decimal(10,0) NULL DEFAULT NULL COMMENT '总金额' ,
`balance`  decimal(10,0) NULL DEFAULT NULL COMMENT '余额' ,
`status`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态' ,
`bizOccurOrg`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务发生机构' ,
`createUser`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateUser`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`updateTime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of p_redit
-- ----------------------------
BEGIN;
INSERT INTO `p_redit` VALUES ('1', '17', '1', '2', '3', '4', '5', '6', '7', '8', '中国人民银行征信中心', 'wangzi', '2016-08-15 14:07:31', null, null), ('2', '17', '1a', '2a', '3a', '4a', '5a', '6', '7', '8a', '中国人民银行征信中心', 'wangzi', '2016-08-15 14:07:31', '', '0000-00-00 00:00:00'), ('3', '17', '1b', '2b', '3b', '4b', '5b', '6', '7', '8b', '中国人民银行征信中心', 'wangzi', '2016-08-15 14:07:31', '', '0000-00-00 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`userID`  int(11) NULL DEFAULT NULL COMMENT '用户ID' ,
`apiKey`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`localApiID`  int(11) NOT NULL COMMENT '本地apiID' ,
`count`  int(11) NULL DEFAULT NULL COMMENT '剩余查询次数,-1为不限制' ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of relation
-- ----------------------------
BEGIN;
INSERT INTO `relation` VALUES ('1', '1', '123321', '1', '-1'), ('2', '1', '123321', '2', '-1');
COMMIT;

-- ----------------------------
-- Table structure for remoteapi
-- ----------------------------
DROP TABLE IF EXISTS `remoteapi`;
CREATE TABLE `remoteapi` (
`ID`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`localApiID`  int(11) NULL DEFAULT NULL ,
`service`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '远程访问service' ,
`serviceName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务中文名' ,
`url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`apiKey`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '远程访问apiKey' ,
`param`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`retParamRel`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回参数与数据表数据关系' ,
`retCode`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回code' ,
`count`  int(11) NULL DEFAULT NULL ,
`encryptKey`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密key' ,
`encryptType`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密类型' ,
`localParamRel`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '与本地api访问参数对应关系' ,
`idx`  int(11) NULL DEFAULT NULL COMMENT '多个api时查询顺序' ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=31

;

-- ----------------------------
-- Records of remoteapi
-- ----------------------------
BEGIN;
INSERT INTO `remoteapi` VALUES ('1', null, 'erriskqryyq90', '自然人金融风险预警接口--逾期90天以上', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('2', null, 'qlmessxq', '查询企业信用信息详情', 'http://www.uniocredit.com/nuapi/UService.do', null, '[    {        \"paramName\": \"keyNo\",        \"notNull\": \"yes\"    } ]', null, null, '10', null, null, null, null), ('3', '2', 'qlproexe', '失信人和被执行人信息查询', 'http://www.uniocredit.com/nuapi/UService.do', 'D37EF162524F265', '[{\"paramName\":\"service\",\"paramType\":\"service\",\"notNull\":\"yes\"},{\"paramName\":\"appid\",\"paramType\":\"apiKey\",\"notNull\":\"yes\"},{\"paramName\":\"keyword\",\"notNull\":\"yes\"},{\"paramName\":\"isExactlySame\",\"notNull\":\"no\"},{\"paramName\":\"pageSize\",\"notNull\":\"no\"},{\"paramName\":\"pageIndex\",\"notNull\":\"no\"},{\"paramName\":\"key\",\"notNull\":\"no\"},{\"paramName\":\"dtype\",\"notNull\":\"no\"}]', null, '{\"codeName\":\"Code\",\"codeValue\":\"000000\"}', '77', '4EC17A77D8A150A5D37EF162524F2658', 'ql', '[{\"keyword\":\"exeName\"},{\"isExactlySame\":\"isExactlySame\"},{\"pageSize\":\"pageSize\"},{\"pageIndex\":\"pageIndex\"},{\"key\":\"key\"},{\"dtype\":\"dtype\"}]', '0'), ('4', null, 'qlexecute', '被执行人信息', 'http://www.uniocredit.com/nuapi/UService.do', '', '[{\"paramName\":\"service\",\"paramType\":\"service\",\"notNull\":\"yes\"},{\"paramName\":\"appid\",\"paramType\":\"apiKey\",\"notNull\":\"yes\"},{\"paramName\":\"keyword\",\"notNull\":\"yes\"},{\"paramName\":\"isExactlySame\",\"notNull\":\"no\"},{\"paramName\":\"pageSize\",\"notNull\":\"no\"},{\"paramName\":\"pageIndex\",\"notNull\":\"no\"},{\"paramName\":\"key\",\"notNull\":\"no\"},{\"paramName\":\"dtype\",\"notNull\":\"no\"}]', '', '', '55', '', '', '', null), ('5', null, 'ucaccqy', '精确查询企业（法院公告，裁判文书）', 'http://www.uniocredit.com/nuapi/UService.do', null, '[    {        \"paramName\": \"datatype\",        \"notNull\": \"yes\"    },    {        \"paramName\": \"fqy\",        \"notNull\": \"yes\"    }   ]', null, null, '10', null, null, null, null), ('6', null, 'ucappeal', '涉诉-裁判文书（详情）', 'http://www.uniocredit.com/nuapi/UService.do', null, '[\r\n    {\r\n        \"paramName\": \"ssxx\",\r\n        \"notNull\": \"yes\"\r\n    }\r\n   ]', null, null, '10', null, null, null, null), ('7', null, 'dwtz', '查询公司对外投资信息', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"keyword\",\"notNull\":\"yes\"},{\"paramName\":\"pageSize\",\"notNull\":\"no\"},{\"paramName\":\"pageIndex\",\"notNull\":\"no\"}]', null, null, '85', null, null, null, null), ('8', null, 'goed', '查询公司经营异常信息', 'http://www.uniocredit.com/nuapi/UService.do', null, '[\r\n    {\r\n        \"paramName\": \"KeyNo\",\r\n        \"notNull\": \"no\"\r\n    }\r\n   ]', null, null, '10', null, null, null, null), ('9', null, 'sidd', '幕后关系图谱', 'http://www.uniocredit.com/nuapi/UService.do', null, '[\r\n    {\r\n        \"paramName\": \"searchKey\",\r\n        \"notNull\": \"yes\"\r\n    }\r\n   ]', null, null, '10', null, null, null, null), ('10', null, 'gcpd', '查询企业新闻信息（口碑舆情）', 'http://www.uniocredit.com/nuapi/UService.do', null, '[\r\n    {\r\n        \"paramName\": \"companyName\",\r\n        \"notNull\": \"yes\"\r\n    }\r\n   ]', null, null, '2', null, null, null, null), ('11', null, 'nbxx', '查询公司年报信息', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"KeyNo\",\"notNull\":\"yes\"}]', null, null, '85', null, null, null, null), ('12', null, 'erriskqryyq60', '自然人金融风险预警接口--逾期61-90天', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('13', null, 'erriskqrybad', '自然人金融风险预警接口--新增不良', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('14', null, 'erriskqryaa', '自然人金融风险预警接口--新增账户', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('15', null, 'erriskqryad', '自然人金融风险预警接口--新增失信和被执行人', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('16', null, 'entriskqrybad', '法人金融信用风险预警接口--新增不良', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('17', null, 'entriskqryyq90', '法人金融信用风险预警接口--逾期90天以上', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('18', null, 'entriskqryyq60', '法人金融信用风险预警接口--逾期61-90天', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('19', null, 'entriskqryad', '法人金融信用风险预警接口--新增失信和被执行人', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('20', null, 'erriskqrydz', '自然人金融风险预警接口--新增呆账', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{    \"paramName\": \"name\",    \"notNull\" : \"yes\"},{    \"paramName\": \"maskID\",    \"notNull\" : \"yes\"},{    \"paramName\": \"tipcode\",    \"notNull\" : \"yes\"},{    \"paramName\": \"gettime\",    \"notNull\" : \"yes\"},{    \"paramName\": \"CertType\",    \"notNull\" : \"yes\"}]', null, null, '66', null, null, null, null), ('21', null, 'lxxx', '查询公司联系信息', 'http://www.uniocredit.com/nuapi/UService.do', null, '[\r\n{\r\n\"paramName\":\"KeyNo\",\r\n\"notNull\":\"yes\"\r\n},\r\n{\r\n\"paramName\":\"dtype\",\r\n\"notNull\":\"no\"\r\n}\r\n]', null, null, '85', null, null, null, null), ('22', null, 'fzlc', '查询企业发展历程', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"KeyNo\",\"notNull\":\"yes\"},{\"paramName\":\"ent_name\",\"notNull\":\"yes\"}]', null, null, '85', null, null, null, null), ('23', null, 'fcns', '水利信息查询', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"company_name\",\"notNull\":\"yes\"}]', null, null, '85', null, null, null, null), ('24', null, 'qlcxs', '企业商标信息--查询商标', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"keyword\",\"notNull\":\"yes\"},{\"paramName\":\"searchType\",\"notNull\":\"no\"},{\"paramName\":\"intCls\",\"notNull\":\"no\"},{\"paramName\":\"pageSize\",\"notNull\":\"no\"},{\"paramName\":\"pageIndex\",\"notNull\":\"no\"},{\"paramName\":\"dtype\",\"notNull\":\"no\"}]', null, null, '85', null, null, null, null), ('25', null, 'qlapplica', '企业商标信息--查询申请人商标个数', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"ent_name\",\"notNull\":\"yes\"},{\"paramName\":\"dtype\",\"notNull\":\"no\"}]', null, null, '85', null, null, null, null), ('26', null, 'qlsqrcx', '企业商标信息--根据申请人查询商标列表', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"ent_name\",\"notNull\":\"yes\"},{\"paramName\":\"pageSize\",\"notNull\":\"no\"},{\"paramName\":\"pageIndex\",\"notNull\":\"no\"},{\"paramName\":\"dtype\",\"notNull\":\"no\"}]', null, null, '85', null, null, null, null), ('27', null, 'qlsxx', '企业商标信息--获取商标详细信息', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"paramName\":\"trademarkid\",\"notNull\":\"yes\"},{\"paramName\":\"dtype\",\"notNull\":\"no\"}]', null, null, '85', null, null, null, null), ('28', null, 'ucquanlian', '个人信用分', 'http://www.uniocredit.com/nuapi/UService.do', null, '[{\"trademarkid\":\"identityCard\",\"notNull\":\"yes\"}]', null, null, '85', null, null, null, null), ('29', '1', 'ucqiis', '身份证查询', 'http://www.uniocredit.com/nuapi/UService.do', 'D37EF162524F265', '[{\"paramName\":\"service\",\"paramType\":\"service\",\"notNull\":\"yes\"},{\"paramName\":\"appid\",\"paramType\":\"apiKey\",\"notNull\":\"yes\"},{\"paramName\":\"NAME\",\"paramType\":\"url\",\"notNull\":\"yes\"},{\"paramName\":\"IDENTITYCARD\",\"paramType\":\"url\",\"notNull\":\"yes\"}]', '{\"ret_idCardNo\":\"IDENTITYCARD\",\"ret_name\":\"NAME\",\"ret_status\":\"Result-COMPRESULT\"}', '{\"insertCondition\":{\"Result-COMPRESULT\":\"一致\"},\"codeName\":\"Code\",\"codeValue\":{\"000000\":{\"codeStatus\":\"success\",\"codeType\":\"break\",\"codeMsg\":\"成功\",\"localCode\":\"102000\"},\"100003\":{\"codeStatus\":\"error\",\"codeType\":\"break\",\"codeMsg\":\"请求参数错误\",\"localCode\":\"102002\"},\"100009\":{\"codeStatus\":\"error\",\"codeType\":\"continue\",\"codeMsg\":\"没有此身份证\",\"localCode\":\"102003\"},\"100013\":{\"codeStatus\":\"error\",\"codeType\":\"continue\",\"codeMsg\":\"身份证号错误\",\"localCode\":\"102004\"},\"100021\":{\"codeStatus\":\"error\",\"codeType\":\"continue\",\"codeMsg\":\"姓名或者身份证号码不正确\",\"localCode\":\"102005\"}}}', '90', '4EC17A77D8A150A5D37EF162524F2658', 'ql', '[{\"NAME\":\"name\"},{\"IDENTITYCARD\":\"idCardNo\"}]', '1'), ('30', '3', 'open.queryStar', '身份证查询', 'http://www.qilingyz.com/api.php', 'jdwx991230', '[{\"paramName\":\"m\",\"paramType\":\"service\",\"notNull\":\"yes\"},{\"paramName\":\"appkey\",\"paramType\":\"apiKey\",\"notNull\":\"yes\"},{\"paramName\":\"fullName\",\"paramType\":\"url\",\"notNull\":\"yes\"},{\"paramName\":\"identityCard\",\"paramType\":\"url\",\"notNull\":\"yes\"}]', '{    \"ret_status\": \"status\"}', '{\"insertCondition\":{\"status\":\"一致\"},\"codeName\":\"errno,status\",\"codeValue\":{\"1\":{\"codeStatus\":\"success\",\"codeType\":\"break\",\"codeMsg\":\"未查询到数据\",\"localCode\":\"102006\"}}}', '100', '', 'normal', '[{\"fullName\":\"name\"},{\"identityCard\":\"idCardNo\"}]', '2');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`ID`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
`userName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`compName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册公司名' ,
`compTel`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册公司电话' ,
`contactName`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人' ,
`contactTel`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人电话' ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=28

;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('27', 'wangzi', '96e79218965eb72c92a549dd5a330112', '中国人民银行征信中心', '022022123', '王梓', '15922041128');
COMMIT;

-- ----------------------------
-- Auto increment value for code
-- ----------------------------
ALTER TABLE `code` AUTO_INCREMENT=2007;

-- ----------------------------
-- Auto increment value for d_m_sfzxx
-- ----------------------------
ALTER TABLE `d_m_sfzxx` AUTO_INCREMENT=11;

-- ----------------------------
-- Auto increment value for d_s_qlproexe
-- ----------------------------
ALTER TABLE `d_s_qlproexe` AUTO_INCREMENT=6;

-- ----------------------------
-- Auto increment value for localapi
-- ----------------------------
ALTER TABLE `localapi` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for p_baseinfo
-- ----------------------------
ALTER TABLE `p_baseinfo` AUTO_INCREMENT=9;

-- ----------------------------
-- Auto increment value for p_person
-- ----------------------------
ALTER TABLE `p_person` AUTO_INCREMENT=19;

-- ----------------------------
-- Auto increment value for p_redit
-- ----------------------------
ALTER TABLE `p_redit` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for relation
-- ----------------------------
ALTER TABLE `relation` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for remoteapi
-- ----------------------------
ALTER TABLE `remoteapi` AUTO_INCREMENT=31;

-- ----------------------------
-- Auto increment value for user
-- ----------------------------
ALTER TABLE `user` AUTO_INCREMENT=28;
