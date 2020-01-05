SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin1
-- ----------------------------
DROP TABLE IF EXISTS `admin1`;
CREATE TABLE `admin1`  (
  `adm1Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '一级管理员ID',
  `adm1Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级管理员账号',
  `adm1Password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级管理员密码',
  PRIMARY KEY (`adm1Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin1
-- ----------------------------
INSERT INTO `admin1` VALUES (1, 'admin1', 'admin1');

-- ----------------------------
-- Table structure for admin2
-- ----------------------------
DROP TABLE IF EXISTS `admin2`;
CREATE TABLE `admin2`  (
  `adm2Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '一级管理员ID',
  `adm2Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级管理员账号',
  `adm2Password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级管理员密码',
  PRIMARY KEY (`adm2Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin2
-- ----------------------------
INSERT INTO `admin2` VALUES (1, 'admin2', 'admin2');

-- ----------------------------
-- Table structure for admin3
-- ----------------------------
DROP TABLE IF EXISTS `admin3`;
CREATE TABLE `admin3`  (
  `adm3Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '一级管理员ID',
  `adm3Name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级管理员账号',
  `adm3Password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级管理员密码',
  PRIMARY KEY (`adm3Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin3
-- ----------------------------
INSERT INTO `admin3` VALUES (1, 'admin3', 'admin3');

-- ----------------------------
-- Table structure for applyinfo
-- ----------------------------
DROP TABLE IF EXISTS `applyinfo`;
CREATE TABLE `applyinfo`  (
  `applyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `stuId` int(11) NOT NULL COMMENT '学生ID',
  `applyTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '申请时间',
  `levelId` int(11) NOT NULL COMMENT '申请级别',
  `applyState` int(1) NOT NULL COMMENT '申请状态（0：未处理 1：通过 2：未通过）',
  PRIMARY KEY (`applyId`) USING BTREE,
  INDEX `levelId`(`levelId`) USING BTREE,
  INDEX `stuId`(`stuId`) USING BTREE,
  CONSTRAINT `applyinfo_ibfk_1` FOREIGN KEY (`levelId`) REFERENCES `level` (`levelId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `applyinfo_ibfk_2` FOREIGN KEY (`stuId`) REFERENCES `student` (`stuId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of applyinfo
-- ----------------------------
INSERT INTO `applyinfo` VALUES (1, 1, '2019-12-24 00:01:44', 1, 1);

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `classId` int(11) NOT NULL COMMENT '班级ID',
  `className` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名字',
  `majorId` int(11) NOT NULL COMMENT '专业ID',
  PRIMARY KEY (`classId`) USING BTREE,
  INDEX `class_ibfk_1`(`majorId`) USING BTREE,
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`majorId`) REFERENCES `major` (`majorId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '1601班', 1);

-- ----------------------------
-- Table structure for level
-- ----------------------------
DROP TABLE IF EXISTS `level`;
CREATE TABLE `level`  (
  `levelId` int(11) NOT NULL COMMENT '级别ID',
  `levelName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '级别名',
  PRIMARY KEY (`levelId`, `levelName`) USING BTREE,
  INDEX `levelId`(`levelId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of level
-- ----------------------------
INSERT INTO `level` VALUES (1, '申请人');
INSERT INTO `level` VALUES (2, '积极分子');
INSERT INTO `level` VALUES (3, '发展对象');
INSERT INTO `level` VALUES (4, '预备党员');
INSERT INTO `level` VALUES (5, '正式党员');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `majorId` int(11) NOT NULL COMMENT '专业ID',
  `majorName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业名字',
  `pbId` int(11) NOT NULL COMMENT '党支部ID',
  PRIMARY KEY (`majorId`) USING BTREE,
  INDEX `pbId`(`pbId`) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`pbId`) REFERENCES `partybranch` (`pbId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '软件工程', 1);

-- ----------------------------
-- Table structure for partybranch
-- ----------------------------
DROP TABLE IF EXISTS `partybranch`;
CREATE TABLE `partybranch`  (
  `pbId` int(11) NOT NULL COMMENT '党支部ID',
  `pdName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '党支部名称',
  PRIMARY KEY (`pbId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of partybranch
-- ----------------------------
INSERT INTO `partybranch` VALUES (1, '党支部1');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stuId` int(8) NOT NULL COMMENT '学生学号（2016****）',
  `stuPassword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生密码',
  `stuName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生姓名',
  `stuSex` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生性别',
  `stuPhoto` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生照片路径',
  `stuOriginPlace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生籍贯',
  `classId` int(11) NOT NULL COMMENT '学生班级ID',
  `stuContactInformation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生联系方式',
  `stuAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生家庭地址',
  `stuPartyDues` int(1) NOT NULL COMMENT '学生是否缴纳党费（0：否 1：是）',
  PRIMARY KEY (`stuId`) USING BTREE,
  INDEX `classId`(`classId`) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`classId`) REFERENCES `class` (`classId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '1', '学生一', '男', '1.png', '籍贯河北省保定市', 1, '12345678900', '家庭地址河北省保定市', 1);

SET FOREIGN_KEY_CHECKS = 1;
