-- 创建数据库
CREATE DATABASE IF NOT EXISTS school DEFAULT CHARSET utf8mb4 ;
-- 使用数据库
use school;
-- 初始化
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- 创建课程表
CREATE TABLE IF NOT EXISTS `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `cteacher` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;
-- 创建选修表
CREATE TABLE IF NOT EXISTS `sc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sno` int NOT NULL,
  `cno` int NOT NULL,
  `grat` int DEFAULT NULL,
  `need` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;
-- 创建学生表
CREATE TABLE IF NOT EXISTS `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `ssex` varchar(2) NOT NULL,
  `sage` int NOT NULL,
  `class` varchar(100) NOT NULL,
  `sdept` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;
-- 创建教师表
CREATE TABLE IF NOT EXISTS `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `ssex` varchar(2) NOT NULL,
  `sage` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;
-- 创建用户表
CREATE TABLE IF NOT EXISTS `userinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `passwd` varchar(100) NOT NULL,
  `utype` int NOT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;


-- 导入数据
INSERT INTO
  course(id, name, cteacher)
VALUES(1, '经济学', 'CC'),(2, '物理学', 'dd');

INSERT INTO
  sc(id, sno, cno, grat, need)
VALUES(1, 1, 1, 999, 1),(2, 2, 2, 999, 1);

INSERT INTO
  student(id, username, ssex, sage, class, sdept)
VALUES(1, 'AA', '男', 20, '一班', '工学部')
,(2, 'BB', '女', 20, '一班', '工学部');

INSERT INTO
  teacher(id, username, ssex, sage)
VALUES(1, 'cc', '男', 20),(2, 'dd', '女', 20);

INSERT INTO
  userinfo(id, username, passwd, utype, uid)
VALUES(1,'admin','098f6bcd4621d373cade4e832627b4f6',1,0),
  (2, '1', '098f6bcd4621d373cade4e832627b4f6', 1, 0),
  (3, '2', '098f6bcd4621d373cade4e832627b4f6', 2, 1),
  (4, '3', '098f6bcd4621d373cade4e832627b4f6', 3, 1),
  (5, '4', '098f6bcd4621d373cade4e832627b4f6', 2, 2),
  (6, '5', '098f6bcd4621d373cade4e832627b4f6', 3, 2);