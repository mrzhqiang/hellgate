字典文档
=======

字典是固定数据，一般情况下不会发生变化，因此可以视为全局常量。

---

## 数据建模

由于 JPA 在设计实体的父子级嵌套时比较麻烦，即便用复合主键得以实现，后续定义一对多、多对一关系也十分棘手，因此这里拆分出来字典分组和字典项。

```mermaid
erDiagram
    DictGroup ||--o{ DictItem : contain
    DictGroup {
        bigint id
        varchar name
        varchar code
        varchar type
        varchar source
    }
    DictItem {
        bigint id
        bigint group_id
        varchar label
        varchar value
    }
```

### DictGroup

字典分组实体：

- name 名称，常用于页面展示
- code 代码，常用于查找，必须保证唯一
- type 类型，默认空值，表示数据库的字典项类型
  - API 第三方数据字典，比如城市列表、邮政编码、机构编码等
  - 系统附件，比如 Excel 表格定义的一系列数据字典
  - 类路径文件，比如内置字典项
  - 其他符合规则的字典项
- source 数据源，根据类型从数据源解析字典项数据
  - API 是 URL 路径，GET 方式访问
  - 系统附件同上，只不过调用的是本地系统接口
  - 类路径文件，类似 classpath:// 的路径
  - 其他可获取数据的源

| name  | code            | type | source |
|-------|-----------------|------|--------|
| 性别    | gender          |      |        |
| 格式化模板 | format_template |      |        |
| 序号模板  | serial_template |      |        |
| 布尔状态  | boolean_status  |      |        |
| 激活状态  | active_status   |      |        |
| 开关状态  | switch_status   |      |        |
| 启用状态  | enabled_status  |      |        |
| 事件状态  | event_status    |      |        |
| 任务状态  | task_status     |      |        |

### DictItem

字典项实体：

- group_id 字典分组id，表示字典项的类型
- label 标签，用于页面展示
- value 数值，用于查找，不要求唯一

| group（实际上是id）   | label    | value               |
|-----------------|----------|---------------------|
| gender          | 未知       | 0                   |
| gender          | 男        | 1                   |
| gender          | 女        | 2                   |
| format_template | 日期格式化    | yyyy-MM-dd          |
| format_template | 时间格式化    | HH:mm:ss            |
| format_template | 日期时间格式化  | yyyy-MM-dd HH:mm:ss |
| serial_template | 日期序号     | yyyyMMdd            |
| serial_template | 时间序号     | HHmmss              |
| serial_template | 日期时间序号   | yyyyMMddHHmmss      |
| serial_template | 日期时间毫秒序号 | yyyyMMddHHmmssSSS   |
| boolean_status  | 否        | 0                   |
| boolean_status  | 是        | 1                   |
| active_status   | 禁止       | 0                   |
| active_status   | 激活       | 1                   |
| switch_status   | 关闭       | 0                   |
| switch_status   | 开启       | 1                   |
| enabled_status  | 停用       | 0                   |
| enabled_status  | 启用       | 1                   |
| event_status    | 未执行      | 0                   |
| event_status    | 已完成      | 1                   |
| task_status     | 默认       | 0                   |
| task_status     | 预备       | 1                   |
| task_status     | 运行       | 2                   |
| task_status     | 成功       | 3                   |
| task_status     | 失败       | 4                   |