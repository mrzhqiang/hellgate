开发文档
======

这里是鬼门关的开发文档，用来记录 **系统架构** 和 **设计思路**。

------

# 1. 系统架构

系统的第一阶段为 **前后端一体化** 架构，后续再决定是否迁移到 **前后端分离** 的架构。

## 1.1 主要框架

系统的主要框架以 `Spring Boot` 为主，通过 `Webjars` 引入前端静态资源。

|框架|备注|
|---|---|
|[Spring Boot](https://spring.io/projects/spring-boot)|核心框架|
|[Spring Security](https://spring.io/projects/spring-security)|安全认证|
|[Spring Data Redis](https://spring.io/projects/spring-data-redis)|Redis 缓存|
|[Spring Session Redis](https://spring.io/projects/spring-session-data-redis)|Redis 会话|
|[Spring Data JPA](https://spring.io/projects/spring-data-jpa)|关系型数据库 ORM|
|[MySQL Connector Java](https://dev.mysql.com/doc/connector-j/8.0/en/)|MySQL 驱动 -- 生产可选|
|[Sqlite JDBC](https://github.com/xerial/sqlite-jdbc)|Sqlite3 驱动 -- 测试可选|
|[Thymeleaf](https://www.thymeleaf.org/)|Web 渲染模板|
|[Spring Validation](https://beanvalidation.org/)|参数校验|
|[Lombok](https://projectlombok.org/)|便捷方法|
|[UserAgentUtils](https://www.bitwalker.eu/software/user-agent-utils)|用户代理解析|
|[Kaptcha](https://github.com/mrzhqiang/kaptcha-spring-boot-starter)|验证码|
|[Helper](https://github.com/mrzhqiang/helper)|辅助方法|
|[Geoip2](https://dev.maxmind.com/geoip?lang=en)|IP 转地理位置|
|[Rxjava](https://github.com/ReactiveX/RxJava)|可观察序列的异步调用框架|
|[Okhttp](https://github.com/square/okhttp)|最好用的 HTTP 客户端|
|[Retrofit](https://github.com/square/retrofit)|声明式 RESTful|
|[Webjars Locator](https://github.com/mwanji/webjars-locator)|前端资源加载器（免版本号）|
|[Webjars Bootstrap](https://github.com/webjars/bootstrap)|HTML + CSS|
|[Webjars Datatables](https://github.com/webjars/datatables)|JS 数据表格及相关扩展和插件|
|[Webjars Swiper](https://github.com/webjars/swiper)|动态刷新图片|
|[Webjars Jquery](https://github.com/webjars/jquery)|兼容式 JS 框架|
|[Webjars Font-awesome](https://github.com/webjars/font-awesome)|免费图标库|
|[Webjars Html5shiv](https://github.com/webjars/html5shiv)|IE 开启 HTML5|

## 1.2 项目结构

系统的项目结构，以 `Maven` 结构为主，同时建立 `docs` 目录用于 `Jekyll` 的项目站点渲染。

```
├─docs                                  ——文档目录
├─hellgate-admin                        ——后台管理模块
├─├─src                                 ——源代码目录
├─├─├─main
├─├─├─├─java
├─├─├─├─├─hellgate.admin
├─├─├─├─├─├─config                      ——系统配置
├─├─├─├─├─├─controller                  ——控制器
├─├─├─├─├─├─HellGateAdminApplication    ——系统启动类
├─├─├─├─resources
├─├─├─├─├─i18n                          ——国际化资源目录
├─├─├─├─├─static                        ——前端静态资源目录
├─├─├─├─├─templates                     ——前端模板代码目录
├─├─├─├─├─application.yml               ——基础的配置文件
├─├─├─├─├─application-dev.yml           ——开发环境配置文件
├─├─├─├─├─application-prod.yml          ——生产环境配置文件
├─├─├─test                              ——单元测试目录
├─hellgate-api                          ——游戏接口模块
├─├─src                                 ——源代码目录
├─├─├─main
├─├─├─├─java
├─├─├─├─├─hellgate.api
├─├─├─├─├─├─config                      ——系统配置
├─├─├─├─├─├─controller                  ——控制器
├─├─├─├─├─├─HellGateApiApplication      ——系统启动类
├─├─├─├─resources
├─├─├─├─├─i18n                          ——国际化资源目录
├─├─├─├─├─static                        ——前端静态资源目录
├─├─├─├─├─templates                     ——前端模板代码目录
├─├─├─├─├─app.key                       ——JWT RSA 私钥文件
├─├─├─├─├─app.pub                       ——JWT RSA 公钥文件
├─├─├─├─├─application.yml               ——基础的配置文件
├─├─├─├─├─application-dev.yml           ——开发环境配置文件
├─├─├─├─├─application-prod.yml          ——生产环境配置文件
├─├─├─test                              ——单元测试目录
├─hellgate-common                       ——公共模块
├─├─src                                 ——源代码目录
├─├─├─main
├─├─├─├─java
├─├─├─├─├─hellgate.common
├─├─├─├─├─├─annotation                  ——自定义注解
├─├─├─├─├─├─config                      ——系统配置
├─├─├─├─├─├─domain                      ——数据库领域
├─├─├─├─├─├─exception                   ——异常处理
├─├─├─├─├─├─session                     ——会话
├─├─├─├─├─├─third                       ——第三方 API
├─├─├─├─├─├─util                        ——工具集合
├─├─├─├─resources
├─├─├─├─├─application.yml               ——基础的配置文件
├─├─├─├─├─GeoLite2-City.mmdb            ——IP 映射的城市数据库文件
├─hellgate-dependencies                 ——依赖管理模块
├─hellgate-hub                          ——账号中心模块
├─├─src                                 ——源代码目录
├─├─├─main
├─├─├─├─java
├─├─├─├─├─hellgate.hub
├─├─├─├─├─├─account                     ——账号
├─├─├─├─├─├─bookmark                    ——书签
├─├─├─├─├─├─config                      ——配置项
├─├─├─├─├─├─HellGateHubApplication      ——系统启动类
├─├─├─├─resources
├─├─├─├─├─i18n                          ——国际化资源目录
├─├─├─├─├─static                        ——前端静态资源目录
├─├─├─├─├─templates                     ——前端模板代码目录
├─├─├─├─├─application.yml               ——基础的配置文件
├─├─├─├─├─application-dev.yml           ——开发环境配置文件
├─├─├─├─├─application-prod.yml          ——生产环境配置文件
├─├─├─test                              ——单元测试目录
├─hellgate-parent                       ——父模块
├─.gitignore
└─pom.xml
```

# 2. 设计思路

系统的第一阶段为基础功能的设计，以及搭建一个简单的游戏框架。

后续将以管理后台的游戏设计功能为主，同时提供游戏运行时的生命周期事件和钩子。

## 2.1 基础功能

- Admin 模块
  - [ ] 初始化：管理后台的管理员账号
  - [ ] 管理后台（持续完善）
- Hub 模块
  - [x] 门户：背景图轮播
  - [x] 账号：注册、登录
  - [ ] 剧本：不同的剧本属于不同的游戏内容，当然也可以是同一个剧本两个舞台演绎
- Api 模块
  - [ ] 舞台：展示剧本包含的内容
  - [ ] 职业：一转（战士、魔法师、弓箭手），二转（未完待续）
  - [ ] 地图：世界地图、副本地图、活动地图等等
  - [ ] 剧情：周期主题、一次性主线和支线、日常循环
  - [ ] 玩家：创建游戏角色、选择职业、进入地图、开始剧情
  - [ ] 道具：除装备之外的各种消耗品或任务用品
  - [ ] 装备：装饰、服饰、武器、防具等等
  - [ ] 放置挂机：解放双手、按期签到即可免费享用

### 2.1.1 初始化

系统在首次启动时，会执行 `data.sql` 文件，自动添加以下数据表的数据：

- `sys_init`、`sys_init_step` 初始化表、初始化步骤表
  - 初始化管理员：通过填写管理员表单（两次密码）生成 admin 管理员账号
  - （未完待续）
- `sys_dict` 数据字典表
  - 性别
  - 默认日期格式
  - 默认时间格式
  - 默认日期时间格式
  - 逻辑状态：0 否 1 是
  - 开关状态：0 关闭 1 开启
  - 激活状态：0 禁止 1 激活
  - 启用状态：0 停用 1 启用
  - 事件状态：0 未执行 1 已完成
  - 任务状态：0 默认 1 进行中 2 完成 3 失败

数据建模：`sys_init` 

|字段名|注释|类型|长度|相关描述|
|---|---|---|---|---|
|id|ID|bigint| |主键、自增|
|name|名称|varchar|32|非空、唯一|
|status|状态|smallint|1|0 默认 1 完成|
|sort|排序|integer|10|从 0 开始、升序|

数据建模：`sys_init_step` 

|字段名|注释|类型|长度|相关描述|
|---|---|---|---|---|
|id|ID|bigint| |主键、自增|
|init_id|初始化 ID|bigint| |非空、外键关联、多对一|
|name|名称|varchar|32|非空、页面标题|
|path|模板路径|varchar|64|导航到表单页面|
|action|表单地址|varchar|255|后端接口接收表单数据|
|status|状态|smallint|1|0 未开始（默认）<br>1 进行中<br>2 已完成<br>3 失败|
|tips|提示|varchar|255|失败提示，帮助解决问题。<br>一般是 `i18n` 消息 key 值|
|sort|排序|integer|10|从 0 开始、升序|

功能逻辑：[初始化流程思维导图](/docs/init.puml)

* 准备进入管理后台首页
  * 第一步 检测初始化表中是否存在未完成项目
    * 存在 执行第二步
    * 不存在 跳过初始化流程 进入管理后台首页
  * 第二步 检测未完成项目中是否存在未完成任务
    * 存在 执行第三步
    * 不存在 设置当前初始化为已完成 回到第一步
  * 第三步 根据未完成的初始化任务 设置对应的 ViewModel 实例返回
  * 第四步 根据用户提交的初始化数据 执行对应初始化逻辑
  * 第五步 设置当前任务编号为已完成 回到第一步

### 2.1.2 管理后台

管理后台不应该影响核心功能的运行，它属于系统的附属品，是用来进行日常管理和设计内容的平台。

功能设计：

- [ ] 系统管理（一阶段）
  - [ ] 菜单管理
    - [ ] 菜单列表
    - [ ] 接口列表
  - [ ] 数据管理
    - [ ] 数据字典 DataDict
    - [ ] 缓存数据
  - [ ] 日志管理
    - [ ] 系统日志 SystemLog
    - [ ] 操作日志 ActionLog
    - [ ] 游戏日志 GameLog
- [ ] 账号管理（一阶段）
  - [ ] 用户列表
  - [ ] 角色列表
  - [ ] 会话列表
- [ ] 设计中心（二阶段）
  - [ ] 地图设计
  - [ ] 剧情设计
  - [ ] 职业设计
  - [ ] 属性设计
  - [ ] 装备设计
  - [ ] 道具设计
  - [ ] 活动设计
- [ ] 游戏管理（二阶段）
  - [ ] 商城管理
  - [ ] 拍卖管理
  - [ ] 玩家管理
  - [ ] （参考传奇后台）

(未完待续..)