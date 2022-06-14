计划文档
======

计划文档包含 **版本计划** 和 **开发进度** 两大类。

---

## 版本计划

主要版本的开发计划。

```mermaid
gantt 
    title Milestone Plan 
    dateFormat yyyy-MM-dd
    section basic
    v0.0.1 -- v0.x.x :active, s1, 2022-05-24, 220d
    section design
    v1.0.0 -- v1.x.x : s2, after s1, 30d
    section refactor
    v2.0.0 -- v2.x.x : s3, after s2, 30d
```

| 版本编号                 | 关键功能                             | 时间节点               |
|----------------------|----------------------------------|--------------------|
| `v0.0.1` -- `v0.x.x` | 基础功能，具体参考开发文档                    | 预计 2022 年内完成       |
| `v1.0.0` -- `v1.x.x` | 内容设计，具体参考开发文档                    | 等待基础功能完成           |
| `v2.0.0` -- `v2.x.x` | 架构重构：前后端分离<br>`Angular` or `Vue` | 等待 `v1.x.x` 版本发布之后 |

## 开发进度

主要展示当前进度，并以倒序方式陈列已完成的进度总结。

```mermaid
gantt
    title Dev Progress
    section Current
    data dict :active, s1, 2022-05-24, 7d
    menu : s2, after s1, 10d
    acl : s3, after s2, 10d
    admin account : s4, after s3, 3d
    init process : s5, after s4, 10d
    select script : s6, after s5, 1d
    select scriptRole : s7, after s6, 1d
    create actor : s8, after s7, 3d
    into stage : s9, after s8, 3d
    section 2019 - 2022 Finished
    create environment :done, f1, 2019-09-04, 2019-09-04
    game account(register and login) :done, f2, 2019-09-05, 2019-09-09
    home page :done, f3, 2019-09-10, 2020-10-20    
    refactor part :done, f4, 2020-10-21, 2022-01-13
    test all :done, f5, 2022-01-14, 2022-03-11
    session :done, f6, 2022-03-12, 2022-04-06
    complete account :done, f7, 2022-04-07, 2022-04-08
    bookmark :done, f8, 2022-04-09, 2022-04-12
    change structure :done, f9, 2022-04-13, 2022-04-17
```

### 当前进度

- [ ] 数据字典设计
- [ ] 菜单设计
- [ ] 权限设计
- [ ] 管理员账号
- [ ] 初始化流程
- [ ] 选择剧本、选择角色、创建演员、进入舞台

### 2019-2022 年已完成

- [x] 创建环境
- [x] 游戏账号：注册、登录
- [x] 模仿地狱之门首页：背景图轮播
- [x] 重构部分
- [x] 测试整体
- [x] 完善会话——需要观察
- [x] 完善账号：身份证绑定、uid 登录
- [x] 书签功能
- [x] 调整结构