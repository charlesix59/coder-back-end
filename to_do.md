- [x] 【指派给徐文涛】这是一个样例，请组长负责所有指派后端的任务 <mark>2022.10.31</mark>
- [x] 【指派给所有人】下载git并clone项目，并成功运行
- [x] 【指派給所有人】新建名為coder的數據庫，運行sql文件，並通過navicat查看是否創建成功
- [x] 【指派給徐文濤】請組長根據navicat中的數據庫表完成數據字典的設計
- [x] 【指派给所有人】參照我寫的pojo中的User文件，完成其他幾個實體類
- [x] 【指派给所有人】参照我写的mapper中的文件和Service中文件，编写Hr、Administer与Company、Profile与UserJob这几个模块的Mapper与Service类
- [x] 【指派给王矽蕤】将所有的controller写好注释并生成接口文档
- [x] 【指派给所有人】sql文件重新生成了一份，确保你的sql文件处于最新
- [x] 【指派给王矽蕤】具体条件见#信息搜集词条
- [x] 【指派给所有人】修改控制器返回值的格式见<mark>#数据格式</mark> 词条
- [x] 【指派给所有人】修改控制器的参数，数据格式见<mark>#数据格式</mark> 词条
- [x] 【指派给王矽蕤】完成AdministerController类中的三个方法
- [x] 【指派给所有人】合作完成`hrController`中除了`findPassword`和`quitLogin`之外的其他方法
- [x] 【指派给王矽蕤】保证项目能跑起来
- [x] 【指派给王矽蕤】下载postman
- [x] 【指派给王矽蕤】测试hr的register接口能不能正藏访问和正常返回
- [x] 【指派给王矽蕤】测试hr的add job接口能不能正常访问和正常返回，前端数据格式见<mark>#参数</mark>词条
- [x] 【指派给王矽蕤】测试hr的modifyJob接口能不能正常访问和正常返回，前端数据格式与上一条相同
- [x] 【指派给王矽蕤】测试hr的modifyApplyState接口能不能正常访问和正常返回，前端数据格式见<mark>#参数</mark>词条
- [ ] 【指派给徐文涛】完成admin的前端界面，`AdminController`如果不能满足需要可以随意更改，其他后端文件更改前先在群里发言讨论
- [x] 【指派给王矽蕤】完成后端节流方案，进行请求限制，对登录、注册等接口进行请求限制

## 数据格式
controller中的所有方法都应该添加`@ResponseBody`注解，
并统一返回Map<String,Object>类型，
然后返回值的规范为：

```json
{
  "success": "必须，返回是否查询成功",
  "data": "如果返回值中有实体类（List），则放在data属性中"
}
```


所有的参数应该添加`@RequestBody`注解，然后参数规范为：
> 如果可以封装为实体类的，直接写实体类
> 
> 如果不能封装为实体类，则需要写成 `Map<String,Object>`

## 参数
addJob方法的前端参数
```json
{
  "name": "jobName",
  "description": "jobDescription",
  "recruit_number": 100,
  "experience_requirement": "一年",
  "education_requirement": "大专",
  "first_categories":"后端",
  "second_categories": "JAVA",
  "tag": "后端开发"
}
```
后端需求：补全其他数据并加入数据库


modifyApplyState的前端参数

```json
{
  "user_id": 1,
  "job_id": 1,
  "state": "pass"
}
```
state的各个参数见[readme](README.md)