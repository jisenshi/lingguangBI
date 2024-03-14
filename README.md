
# 基于ChatGPT的智能BI平台

------

区别于传统的平台,用户(数据分析者),只需要导入最原始的数据,输入想要分析的目标

(比如帮我分析一下网站的增长趋势),就能利用AI自动生成一个符合要求的图表以及结论.

可在注册登录后,在左下角签到页面查看示例,访问地址->http://149.248.16.228/

### 使用框架及技术

- Spring Boot 2.7.2 (万用java后端项目模板,快速搭建基础框架,避免写重复代码)
- MySql数据库
- MyBatis Plus数据库访问框架
- 消息队列:RabbitMQ
- AI能力(Openai API)
- Excel的上传和数据的解析(Easy Excel)
- Swagger+Knife4j项目接口文档

- Hutool工具库
- Redission 的 RateLimiter 限流及图表缓存

### 基本功能

- 用户输入分析目标,图表名称,选择图表类型后上传Excel文件进行分析,得到分析结论及可视化图表
- 支持同/异步两种分析模式,异步无需等待,可批量进行分析
- 我的图表页面可查看结果,支持根据图表名称,分析目标检索
- 支持对失败的图表自动重试,可删除图表
- 每次调用消耗积分,成功登录后,可在左下角选择签到获取积分

### 运行流程

![](BI_backend-master/doc/ansyc.png)

![](/doc/synchronization.png)

### 项目界面

![](/doc/1.png)
![](/doc/2.png)
![](/doc/3.png)
![](/doc/4.png)
