# 省疾控

## 项目模块说明

| **模块** | **模块名** | **说明** |
| --- | --- | --- |
| boot | 启动类 | 管理后台、公共平台，调用各个微服务 |
| core | 核心业务 | 异常处理、枚举字典、工具类 |
| service | 子模块 | 系统模块、管培模块、质控模块|
| doc | 文档 | 相关文档|

## 开发注意事项

* 本项目所有请求均为`POST`，`Content-Type`为`application/json;charset=utf-8`
* 本项目统一响应格式`Content-Type`为`application/json;charset=utf-8`，响应类为`ResponseData`
* 本项目接口命名规定，业务接口uri遵循 `端/模块/业务/操作`例如 添加系统用户接口：`/sys/userinfo/add`，`controller`层方法命名与接口操作一致，`service`
  层方法就按照`mybatis-plus`接口命名, 以下为基础操作命名，其他业务操作自行命名：

| **操作名** | **说明** |
| --- | --- |
| page | 分页列表 |
| list | 列表 |
| detail | 根据id查询单条数据 |
| create | 新增|
| update | 修改|
| delete | 删除，批量删除ids|
| tree | 树结构数据|

* 各个业务模块代码对应各个表，包名作如下规定

| **包名** | **说明** | 
| --- | --- |
| service | 服务接口 |
| service.impl | 服务接口实现 |
| entity | 数据库对应的实体对象 |
| mapper | mybatis的mapper接口，本项目规定必须加`@Mapper`注解|
| param | 存放前端传参数据对象，必须继承`CommonParam`类，分页对象使用`CommonParam#page`创建|
| dto | 返回业务数据对象，区别于entity|
| controller | 接口层，只放在`api`模块|
| enums | 枚举值，和当前业务相关的枚举，比如状态、类型|


