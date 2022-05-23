开发文档
======

这里是鬼门关的开发文档，用来记录 **系统架构** 和 **开发规范**。

------

## 1. 系统架构

系统的第一阶段为 **前后端一体化** 架构，后续再决定是否迁移到 **前后端分离** 的架构。

### 1.1 主要框架

系统的主要框架以 `Spring Boot` 为主，通过 `Webjars` 引入前端静态资源。

| 框架                                                                           | 备注                 |
|------------------------------------------------------------------------------|--------------------|
| [Spring Boot](https://spring.io/projects/spring-boot)                        | 核心框架               |
| [Spring Security](https://spring.io/projects/spring-security)                | 安全认证               |
| [Spring Data Redis](https://spring.io/projects/spring-data-redis)            | Redis 缓存           |
| [Spring Session Redis](https://spring.io/projects/spring-session-data-redis) | Redis 会话           |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa)                | 关系型数据库 ORM         |
| [MySQL Connector Java](https://dev.mysql.com/doc/connector-j/8.0/en/)        | MySQL 驱动           |
| [Thymeleaf](https://www.thymeleaf.org/)                                      | Web 渲染模板           |
| [Spring Validation](https://beanvalidation.org/)                             | 参数校验               |
| [Lombok](https://projectlombok.org/)                                         | 便捷方法               |
| [UserAgentUtils](https://www.bitwalker.eu/software/user-agent-utils)         | 用户代理解析             |
| [Kaptcha](https://github.com/mrzhqiang/kaptcha-spring-boot-starter)          | 验证码                |
| [Helper](https://github.com/mrzhqiang/helper)                                | 辅助方法               |
| [Geoip2](https://dev.maxmind.com/geoip?lang=en)                              | IP 转地理位置           |
| [Rxjava](https://github.com/ReactiveX/RxJava)                                | 可观察序列的异步调用框架       |
| [Okhttp](https://github.com/square/okhttp)                                   | 最好用的 HTTP Java 客户端 |
| [Retrofit](https://github.com/square/retrofit)                               | 声明式 RESTful        |
| [Webjars Locator](https://github.com/mwanji/webjars-locator)                 | 前端资源加载器（免版本号）      |
| [Webjars Bootstrap](https://github.com/webjars/bootstrap)                    | HTML + CSS         |
| [Webjars Datatables](https://github.com/webjars/datatables)                  | JS 数据表格及相关扩展和插件    |
| [Webjars Swiper](https://github.com/webjars/swiper)                          | 动态刷新图片             |
| [Webjars Jquery](https://github.com/webjars/jquery)                          | 兼容式 JS 框架          |
| [Webjars Font-awesome](https://github.com/webjars/font-awesome)              | 免费图标库              |
| [Webjars Html5shiv](https://github.com/webjars/html5shiv)                    | IE 开启 HTML5        |

### 1.2 项目结构

系统的项目结构，以 `Maven` 结构为主，同时建立 `docs` 目录用于 `Jekyll` 的项目站点渲染。

```
├─docs                                  ——文档根目录
├─├─hub                                   ——账号中心文档
├─├─manage                                ——管理后台文档
├─├─server                                ——核心服务文档
├─hellgate-dependencies                 ——依赖管理
├─hellgate-project                      ——项目管理
├─├─hellgate-common                       ——公共模块（参考核心服务）
├─├─hellgate-hub                          ——账号中心（参考核心服务）
├─├─hellgate-manage                       —-管理后台（参考核心服务）
├─├─hellgate-server                       ——核心服务
├─├─├─src                                   ——源代码目录
├─├─├─├─main
├─├─├─├─├─java
├─├─├─├─├─├─hellgate.server
├─├─├─├─├─├─├─config                          ——配置包
├─├─├─├─├─├─├─[...]                           ——其他功能包
├─├─├─├─├─├─├─ServerApplication               ——启动类
├─├─├─├─├─resources
├─├─├─├─├─├─i18n                              ——国际化资源
├─├─├─├─├─├─static                            ——前端静态资源
├─├─├─├─├─├─templates                         ——前端模板
├─├─├─├─├─├─application.yml                   ——基础配置
├─├─├─├─├─├─application-dev.yml               ——开发环境配置
├─├─├─├─├─├─application-prod.yml              ——生产环境配置
├─├─├─├─test                                ——单元测试
├─.gitignore
└─pom.xml
```

## 2. 开发规范（待完善）
