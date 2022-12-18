# 码农招聘网站 开发者版
_文档版本 v1.0.2_
## 碎碎念
虽然gitee不太好用但是念在他在国内，开发阶段先用gitee
## 关于网站
为啥叫coder呢，是为了细化一下功能，做全行业的招聘的话需要的素材太多，所以我们只作码农的招聘这一点
虽然凡凡姐还没有发详细的需求，等她发了详细的需求我们再说好吧
## 开发
我将需要进行的任务放在一个[to_do](./to_do.md)文件中，你们每完成一个任务需要将任务打对勾并写上完成时间
组长负责下发任务

**迭代序列如下：**
- [x] 构建数据库
- [x] 搭建spring boot项目
- [x] 完成entity包
- [x] 完成mapper
- [x] 完成service包
- [x] 完成controller包
- [x] 实现jwt
- [x] 实现邮件验证功能
- [x] 实现邮件提醒功能
- [x] 文件上传功能
- [x] 分页功能
- [x] redis缓存
- [x] 密码加密
- [x] 重新配置redis
- [x] 配置下载功能
- [ ] ……
- [ ] docker打包
- [x] 部署
## 文件結構
我將一些非源代碼的文件扔在了assets裏面，其中包括sql文件、數據字典與項目將需求
## 一些资料
mp CRUD文档 https://baomidou.com/pages/49cc81/

mp 条件构造器 https://baomidou.com/pages/10c804/#abstractwrapper

## 部分状态说明
user_job 表，state有如下几种状态
- 已申请 （applying）
- 通过（pass）
- 未通过（refer）
