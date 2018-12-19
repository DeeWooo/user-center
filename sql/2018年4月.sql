ALTER TABLE roles_info ADD create_time DATETIME NULL COMMENT '创建时间';
ALTER TABLE roles_info ADD scope VARCHAR(50) NULL COMMENT '作用域';
ALTER TABLE user_roles_relations ADD create_time DATETIME NULL COMMENT '创建时间';

-- 创建认证师角色
INSERT INTO roles_info(
  role_code, role_name,  update_time, description,  create_time, scope)
VALUES(
  5000,'认证师',now(),'认证师',now(),'2boss'
)
;

ALTER TABLE agent_superior_info ADD user_id INT(11) NULL COMMENT '对应users_info表中的user_id';
ALTER TABLE agent_superior_info ADD create_time DATETIME NULL COMMENT '创建时间';
ALTER TABLE agent_superior_info ADD update_time DATETIME NULL COMMENT '更新时间';

ALTER TABLE users_info ADD channel VARCHAR(50) NULL COMMENT '注册渠道';


-- 创建经纪人角色
DELETE FROM roles_info WHERE role_code=2000;
insert into roles_info (role_code, role_name,  update_time, description,  create_time, scope)
VALUES
  (2000,'二手房经纪人',now(),'二手房经纪人',now(),'2boss');
-- 初始化经纪人角色信息
INSERT INTO user_roles_relations(role_id, user_id, update_time, create_time)
select (select id from roles_info WHERE role_code=2000),
  user_id, now(),now()
from users_info where user_type in (1,3)
;


-- 增加account属性
ALTER TABLE account ADD create_time DATETIME NULL COMMENT '创建时间';
ALTER TABLE account ADD scope VARCHAR(50) NULL COMMENT '账户作用域';
ALTER TABLE account ADD version INT NULL COMMENT '乐观锁';

-- 增加经纪人属性
ALTER TABLE agent_superior_info ADD share_code CHAR(7) NULL COMMENT '分享码';
ALTER TABLE agent_superior_info ADD invite_code CHAR(7) NULL COMMENT '邀请码';

-- 完善角色信息
update roles_info set scope='2boss' where id in (1,2);

-- 用户权益表
create table user_interests
(
	id int auto_increment comment '自增主键'		primary key,
	user_id int not null comment '用户id',
	role_id int not null comment '角色id',
	account_type varchar(20) null comment '权益类型',
	account_property varchar(20) null comment '权益属性',
	start_at datetime null comment '生效时间',
	expire_at datetime null comment '失效时间',
	create_time datetime null comment '创建时间',
	update_time datetime null comment '更新时间',
	version int null comment '乐观锁',
	deleted TINYINT(1) NULL COMMENT '数据是否被删除'
)
comment '用户权益表' engine=InnoDB
;

--  认证师工作区域表
CREATE TABLE user_certifier_workdistrict
(
    id INT(11) PRIMARY KEY NOT NULL COMMENT '自增主键' AUTO_INCREMENT,
    user_id INT(11) COMMENT '用户id',
    city_id INT NOT NULL COMMENT '工作区域所属城市',
    district_id INT NOT NULL COMMENT '工作区域id',
    version INT COMMENT '乐观锁',
    deleted TINYINT(1) NULL COMMENT '数据是否被删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
);
ALTER TABLE user_certifier_workdistrict COMMENT = '认证师工作区域表';

-- 对应角色最后登录时间
ALTER TABLE user_roles_relations ADD last_login_date DATETIME NULL COMMENT '最后登录时间';

-- 用户推送标签表
CREATE TABLE user_pushtag
(
    id INT(11) PRIMARY KEY NOT NULL COMMENT '自增id' AUTO_INCREMENT,
    user_id INT(11) COMMENT '对应user表中的user_id',
    pushtag VARCHAR(20) COMMENT '推送标签',
    deleted TINYINT(1) COMMENT '是否删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    tag_type VARCHAR(20) COMMENT 'tag类型',
    version INT NULL COMMENT '乐观锁'
);
ALTER TABLE user_pushtag COMMENT = '用户推送标签表';