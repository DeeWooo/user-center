#用户中心


- uc-application为控制器，提供restapi和job
- uc-user为权限和用户核心服务
- uc-equity为会员卡服务
- uc-integral为积分服务
- uc-others为其他暂放在用户中心的服务，比如customerInfo
- uc-common为公共模块
 
 
#启动
- 用户中心为独立服务，通过uc-applicatio中的UserCenterApplication启动


#打包方式
- 打包
   
   ````
   mvn clean package -pl uc-application -am -P dev -Dmaven.test.skip=true -U

- 启动
