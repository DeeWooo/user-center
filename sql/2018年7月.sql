-- 三方授权基本信息

CREATE TABLE user_oauth_weixin
(
  id          INT AUTO_INCREMENT  COMMENT '自增id'    PRIMARY KEY,
  unionid     VARCHAR(30)  NULL  COMMENT '微信unionId',
  openid      VARCHAR(30)  NULL  COMMENT '微信openid',
  nickname    VARCHAR(50)  NULL  COMMENT '微信昵称',
  headimgurl  VARCHAR(200) NULL  COMMENT '微信头像',
  version     int    COMMENT '乐观锁',
  create_time DATETIME     NULL  COMMENT '创建时间',
  update_time DATETIME     NULL  COMMENT '更新时间',
  user_id INT(11) NULL COMMENT '对应user表中的user_id'
)
  COMMENT '第三方登录微信用户'  ENGINE = InnoDB;


CREATE TABLE user_oauth_weibo
(
    id INT PRIMARY KEY COMMENT '自增id' AUTO_INCREMENT,
    user_id int(11) COMMENT 'users_info.user_id',
    uid bigint(64) COMMENT 'weibo的uid',
    idstr varchar(100) COMMENT '字符串型的用户UID',
    screen_name varchar(20) COMMENT '用户昵称',
    name varchar(20) COMMENT '友好显示名称',
    profile_image_url varchar(200) COMMENT '用户头像地址（中图），50×50像素',
    avatar_large varchar(200) COMMENT '用户头像地址（大图），180×180像素',
    avatar_hd varchar(200) COMMENT '用户头像地址（高清），高清头像原图',
    gender varchar(1) COMMENT '性别，m：男、f：女、n：未知',
    version int COMMENT '乐观锁',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间'
);
ALTER TABLE user_oauth_weibo COMMENT = '三方授权微博基础信息表';

CREATE TABLE user_oauth_qq
(
    id INT PRIMARY KEY COMMENT '自增id' AUTO_INCREMENT,
    user_id int(11) COMMENT 'users_info表中的用户id',
    open_id varchar(100) COMMENT 'openid',
    nickname varchar(50) COMMENT '用户昵称',
    figureurl varchar(200) COMMENT '大小为30×30像素的QQ空间头像URL。',
    figureurl1 varchar(200) COMMENT '大小为50×50像素的QQ空间头像URL。',
    figureurl2 varchar(200) COMMENT '大小为100×100像素的QQ空间头像URL。',
    figureurl_qq1 varchar(200) COMMENT '大小为40×40像素的QQ头像URL。',
    figureurl_qq2 varchar(200) COMMENT '大小为100×100像素的QQ头像URL。若没有100×100像素的QQ头像，则返回大小为40×40像素的QQ头像URL。',
    version int COMMENT '乐观锁',
    create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间'
);
ALTER TABLE user_oauth_qq COMMENT = '三方授权QQ基础信息表';

--  旧三方登录数据处理
insert  into user_oauth_qq(user_id, open_id, nickname, figureurl,version,create_time,update_time)
  SELECT u.user_id,u.qq_openid,u.nickname,u.avatar_url,1,now(),now() from users_info u
where u.qq_openid is not  null and u.qq_openid !=''
;

insert into user_oauth_weixin ( openid, nickname, headimgurl, create_time, update_time, user_id,version)
  select u.wx_openid,u.nickname,u.avatar_url,now(),now(),u.user_id,1 from users_info u
where u.wx_openid is not null  and u.wx_openid !=''
;

insert  into user_oauth_weibo (user_id, uid, idstr, screen_name, name, profile_image_url, version, create_time, update_time)
  select u.user_id,u.weibo_openid+0,
    u.weibo_openid,u.nickname,u.nickname,u.avatar_url,1,now(),now()
  from users_info u
where u.weibo_openid is not null  and u.weibo_openid !='' and (u.weibo_openid REGEXP '[^0-9.]')=0
;