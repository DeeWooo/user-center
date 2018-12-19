--用户验证码
CREATE TABLE user_verifycode
(
      id BIGINT(11) PRIMARY KEY COMMENT '自增id' AUTO_INCREMENT,
      verify_code CHAR(4) COMMENT '4位验证码',
      uri VARCHAR(50) COMMENT '发送方标识：手机号、邮箱等',
      usefulness VARCHAR(50) COMMENT '申请校验理由',
      expire_time DATETIME COMMENT '失效时间',
      create_time DATETIME DEFAULT now() COMMENT '创建时间',
      update_time DATETIME DEFAULT now() on UPDATE now() COMMENT '更新时间'
)
COMMENT '用户验证码'   ENGINE = InnoDB;

--用户授权访问token
drop TABLE user_authorization;
CREATE TABLE user_authorization
(
      id BIGINT(11) PRIMARY KEY COMMENT '自增id' AUTO_INCREMENT,
      scope VARCHAR(20) COMMENT '授权范围（用于区分如经纪版登录，案场管理后台登录等）',
      user_id BIGINT(11) COMMENT '用户Id',
      client_id CHAR(36) COMMENT 'ubt产生的clientId，唯一标示客户端',
      access_token VARCHAR(64) COMMENT '访问token',
      access_token_expire_in BIGINT(8) COMMENT 'access_token有效期',
      access_token_expire_at DATETIME DEFAULT now() COMMENT 'access token 过期时间',
      refresh_token VARCHAR(64) COMMENT '刷新token',
      refresh_token_expire_in BIGINT(8) COMMENT 'refresh_token有效期',
      refresh_token_expire_at DATETIME DEFAULT now() COMMENT 'refresh token 过期时间',
      deleted SMALLINT DEFAULT 0 COMMENT '是否被注销，0-否，1-是',
      update_time DATETIME DEFAULT now() COMMENT '创建时间',
      update_time DATETIME DEFAULT now() on UPDATE now() COMMENT '更新时间'
)
COMMENT '用户授权访问'   ENGINE = InnoDB;