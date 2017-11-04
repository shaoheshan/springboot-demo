**医院端项目技术栈** 
- 实现前后端分离，通过Authorization进行数据交互，前端不用关注后端技术
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 页面交互使用Vue2.x，前后端分离提高开发效率
- 全方位的日志输出，可通过日志快速定位问题、排查性能
- 基础代码自动生成，可在线生成entity、xml、dao、service、html、js、sql代码
- 引入quartz定时任务，可动态完成任务的Crud、暂停、恢复及日志查看等功能
- 引入API模板，根据Authorization作为登录令牌，方便API接口开发
- 引入Hibernate Validator校验框架，实现后端校验
- 引入swagger文档支持，方便编写API接口文档
- 全局异常处理机制(BizException and GlobalsExceptionHandler)
- 引入lombok注解，消除一些必须有但显得很臃肿的Java代码（http://blog.csdn.net/linbilin_/article/details/50604936）
<br> 

**数据权限设计思想** 
- 管理员管理、角色管理
- 菜单管理、定时任务、参数管理、系统日志
- 业务功能，按照用户数据权限，查询、操作数据
<br> 

**项目结构** 
```
vhsc-api4
├─doc  医院端库初始化SQL语句(菜单、默认管理员、基础业务数据等)
│
│ 
├─modules 业务功能模块
│
│
├─platform 平台模块
│  ├─api API接口平台支撑
│  └─config 公共模块
│  │ ├─common 公共模块
│  │   ├─aspect 系统日志
│  │   ├─exception 异常处理
│  │   ├─validator 后台校验
│  │   └─xss XSS过滤
│  ├─job 定时任务模块
│  ├─oss 文件服务模块
│  └─sys 权限模块
│ 
├─HospitalApplication 项目启动类
│  
├──resources 
│  ├─cfg 配置管理
│  ├─mapper SQL对应的XML文件
│  ├─static 第三方库、插件等静态资源
│  └─views  项目静态页面

```
<br> 

**研发常见FAQ：** 
- 前端需对数据某个字段进行排序，应该如何处理？<br />
  排序完全交给前端主动控制，只需要传参数即可，&sidx=id&order=desc，表示按id进行倒序查询
- 如何记录日志 <br />
  类中加入注解@Slf4j即可
- 如何在Ctrl层自动校验数据 <br />
  ValidatorUtils.validateEntity(obj);
- eclipse控制台彩色日志输出
  eclipse安装ANSI Console插件
- 如何进行分页，或者如果不分页
  分页参数（page=1、limit=20），不传此参数则默认查第一页，每页20条记录，如果想查询全部数据，则设置为-1



<br />
<br />
<br />
 
**技术选型：** 
- 核心框架：Spring Boot 1.5
- 安全框架：Apache Shiro 1.3
- 视图框架：Spring MVC 4.3
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.3
- 数据库连接池：Druid 1.0
- 日志管理：SLF4J 1.7.x、logback 1.1.x
- 页面交互：Vue2.x 
<br> 

 **本地部署**
- 通过git下载源码
- 创建数据库supplychain_hosp_2，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行HospitalApplication.java，则可启动项目
- 项目访问路径： http://localhost:8080
- 账号密码：admin/admin
- Swagger路径：http://localhost:8080/swagger/index.html 或登陆系统后在系统管理中有接口管理模块

<br>
