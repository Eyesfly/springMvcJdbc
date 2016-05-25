/*
创建用户表*/
CREATE TABLE SYS_USER(
  ID NUMBER(10) PRIMARY KEY ,
  USERNAME VARCHAR(50) UNIQUE,
  PASSWORD VARCHAR(100),
  IS_LOCKED CHAR(1)
);
ALTER TABLE SYS_USER ADD SALT VARCHAR(50);
COMMENT ON TABLE SYS_USER IS '用户表';
COMMENT ON COLUMN SYS_USER.USERNAME IS '用户名';
COMMENT ON COLUMN SYS_USER.PASSWORD IS '密码';
COMMENT ON COLUMN SYS_USER.SALT IS '盐值';
COMMENT ON COLUMN SYS_USER.IS_LOCKED IS '是否锁定';
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,IS_LOCKED) VALUES (SEQ_TEST.NEXTVAL,'abc','123',1);
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,IS_LOCKED) VALUES (SEQ_TEST.NEXTVAL,'abc','123',1);
CREATE TABLE SYS_RESOURCE(
  ID NUMBER(10) PRIMARY KEY ,
  NAME VARCHAR(50),
  URL VARCHAR(250),
  TYPE VARCHAR(50),
  PRIORITY NUMBER(3),
  PARENT_ID NUMBER(10),
  PERMISSION VARCHAR(250),
  IS_AVAILABLE CHAR(1)
);
INSERT INTO SYS_RESOURCE(ID,NAME,URL,TYPE,PARENT_ID,PERMISSION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'用户管理','','menu','','/user/*',1);
INSERT INTO SYS_RESOURCE(ID,NAME,URL,TYPE,PARENT_ID,PERMISSION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'新增','','','','/user/create',1);
INSERT INTO SYS_RESOURCE(ID,NAME,URL,TYPE,PARENT_ID,PERMISSION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'修改','','','','/user/update',1);
INSERT INTO SYS_RESOURCE(ID,NAME,URL,TYPE,PARENT_ID,PERMISSION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'删除','','','','/user/delete',1);
INSERT INTO SYS_RESOURCE(ID,NAME,URL,TYPE,PARENT_ID,PERMISSION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'查看','','','','/user/view',1);
COMMENT ON TABLE SYS_RESOURCE IS '资源表';
COMMENT ON COLUMN SYS_RESOURCE.NAME IS '资源名称';
COMMENT ON COLUMN SYS_RESOURCE.TYPE IS '资源类型';
COMMENT ON COLUMN SYS_RESOURCE.PRIORITY IS '资源排序';
COMMENT ON COLUMN SYS_RESOURCE.URL IS '资源路径';
COMMENT ON COLUMN SYS_RESOURCE.PERMISSION IS '资源权限';
COMMENT ON COLUMN SYS_RESOURCE.IS_AVAILABLE IS '是否启用';

CREATE TABLE SYS_ROLE(
  ID NUMBER(10) PRIMARY KEY ,
  ROLE_NAME VARCHAR(250),
  DESCRIPTION VARCHAR(500),
  IS_AVAILABLE CHAR(1)
);
INSERT INTO SYS_ROLE(ID,ROLE_NAME,DESCRIPTION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'ROLE_USER','普通用户',1);
INSERT INTO SYS_ROLE(ID,ROLE_NAME,DESCRIPTION,IS_AVAILABLE) VALUES (SEQ_TEST.NEXTVAL,'ROLE_MANAGER','管理员',1);
COMMENT ON TABLE SYS_ROLE IS '角色表';
COMMENT ON COLUMN SYS_ROLE.ROLE_NAME IS '角色名称';
COMMENT ON COLUMN SYS_ROLE.DESCRIPTION IS '角色描述';
COMMENT ON COLUMN SYS_ROLE.IS_AVAILABLE IS '是否启用';

CREATE TABLE SYS_ROLE_USER(
  ID NUMBER(10) PRIMARY KEY,
  USER_ID NUMBER(10),
  ROLE_ID NUMBER(10),
  FOREIGN KEY(ROLE_ID) REFERENCES SYS_ROLE(ID),
  FOREIGN KEY(USER_ID) REFERENCES SYS_USER(ID)
);
COMMENT ON TABLE SYS_ROLE_USER IS '用户角色表';
COMMENT ON COLUMN SYS_ROLE_USER.USER_ID IS '用户ID';
COMMENT ON COLUMN SYS_ROLE_USER.ROLE_ID IS '角色ID';

CREATE TABLE SYS_ROLE_RESOURCE(
   ID NUMBER(10) PRIMARY KEY,
   ROLE_ID NUMBER(10),
   RESOURCE_ID NUMBER(10),
   FOREIGN KEY(ROLE_ID) REFERENCES SYS_ROLE(ID),
   FOREIGN KEY(RESOURCE_ID) REFERENCES SYS_RESOURCE(ID)
);
COMMENT ON TABLE SYS_ROLE_RESOURCE IS '角色资源表';
COMMENT ON COLUMN SYS_ROLE_RESOURCE.RESOURCE_ID IS '资源ID';
COMMENT ON COLUMN SYS_ROLE_RESOURCE.ROLE_ID IS '角色ID';