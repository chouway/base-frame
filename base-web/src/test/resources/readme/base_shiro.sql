/*
Navicat PGSQL Data Transfer

Source Server         : 本地postgre 数据库连接
Source Server Version : 90401
Source Host           : localhost:5432
Source Database       : base_shiro
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90401
File Encoding         : 65001

Date: 2016-11-03 10:08:47
*/


-- ----------------------------
-- Table structure for roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS "roles_permissions";
CREATE TABLE "roles_permissions" (
"id" varchar(32) COLLATE "default" NOT NULL,
"role_name" varchar(100) COLLATE "default",
"permission" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS "user_roles";
CREATE TABLE "user_roles" (
"id" varchar(32) COLLATE "default" NOT NULL,
"user_name" varchar(32) COLLATE "default",
"role_name" varchar(32) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
"id" varchar(32) COLLATE "default" NOT NULL,
"username" varchar(100) COLLATE "default",
"password" varchar(100) COLLATE "default",
"password_salt" varchar(100) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO "users" VALUES ('test_0', 'zhang', '123', null);
COMMIT;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table roles_permissions
-- ----------------------------
ALTER TABLE "roles_permissions" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_roles
-- ----------------------------
ALTER TABLE "user_roles" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "users" ADD PRIMARY KEY ("id");
