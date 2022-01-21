create database 数据库;

use 数据库;

CREATE TABLE `map` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `code` varchar(999) DEFAULT NULL COMMENT 'created tiem',
  `name` varchar(999) DEFAULT NULL COMMENT 'updated tiem',
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3379 DEFAULT CHARSET=utf8
