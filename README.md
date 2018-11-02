# SpringBoot-Book-Store
一款以 spring-boot 结合 dubbo 为主的在线购书中心，在项目中结合了 mybatis 以及 redis 作为数据库。

# Online-Store

**Online-Store**是一个结合spring-boot的简易在线书城，它支持用户的登录与注册功能，用户能够在商城上对商品进行阅览挑选，该项目还实现了购物车功能，用户能够在登录与不登录情况下将喜欢的商品加入购物车中，删除对购物车中不喜欢的商品，清空购物车等功能，用户能够对订单中的送货地址，送货时间，商品等进行修改，**它能有效地处理多用户同时对商品的下订。**

该项目主要实现了四个服务器，1 用户服务器、2 订单服务器、3 商品服务器、4 底层服务器，该架构参考项目SpringBoot-Dubbo-Docker-Jenkins(https://github.com/Jia-Calvin/SpringBoot-Dubbo-Docker-Jenkins)。
<img src="https://github.com/Jia-Calvin/SpringBoot-Book-Store/blob/master/img-store/%E4%B9%A6%E5%9F%8E%E9%A1%B9%E7%9B%AE%E6%9E%B6%E6%9E%84.png" width="500px">

具体来说，Basis底层服务器放置了各类Entity，例如BuyerCart,User,Product实体，其他服务器需要这些实体时，只需依赖这个服务器，进行本地的调用。用户服务器，产品服务器，订单服务器利用myBatis框架与数据库交互，实际则是利用Mapper，Mapper类似于DAO，它是用户服务器与数据库的之间的交互，对调用数据库的mySql语言映射成Java语言，最后在上层的ServiceImpl中注入Mapper，将需要暴露的服务(Service)都具体实现。暴露服务之间的通信(RPC通信)是利用Dubbo框架，Zookeeper为注册中心进行通信的。


#### 实现完成页面
- 商品展示页面
<img src="https://github.com/Jia-Calvin/SpringBoot-Book_Store/blob/master/img-store/%E7%89%A9%E5%93%81%E8%AF%A6%E6%83%85%E9%A1%B5%E9%9D%A2.png" width="800px">" width="800px">

- 商品详情页面
<img src="https://github.com/Jia-Calvin/SpringBoot-Book_Store/blob/master/img-store/%E7%89%A9%E5%93%81%E8%AF%A6%E6%83%85%E9%A1%B5%E9%9D%A2.png" width="800px">


- 订单页面
<img src="https://github.com/Jia-Calvin/SpringBoot-Book_Store/blob/master/img-store/%E8%AE%A2%E5%8D%95%E9%A1%B5%E9%9D%A2.png" width="800px">


## 1 用户服务器

用户服务器对外暴露的接口包括（功能）

- 增加用户(用户注册)
- 查询用户名是否存在
- 查询用户的完整信息
- 修改密码，验证旧密码
- 删除用户
- 验证验证用户名与密码
- 修改收货地址，需要对应的收货地址id
- 新增收货地址
- 修改电话号码等

## 2 订单服务器

订单服务器对外暴露的接口包括（功能）

- 加入订单(购物车)
- 从订单除去
- 确定订单
- 修改订单其余信息
- 取消订单
- 

## 3 商品服务器

商品服务器对外暴露的接口包括（功能）

- 查询商品是否存在
- 查询获取所有在商城的商品
- 查询书本的所有类别
- 查询书本的所有作者
- 根据书名获取完整的Product实体
- 根据Id获取完整的Product实体
- 向数据库添加书本
- 删除数据库中的书本
- 设置书本的价格
- 设置书本的库存量
- 设置书本的描述
- 减少书本的库存量，减少量可设定
- 向书本添加类别
- 向书本添加作者
- 获取书本的唯一id
- 根据类别获取这个类别的所有Product实体


## 4 底层服务器

用户实体
- 用户Id 主键
- 用户名 
- 密码
- 性别
- 邮箱
- 收货地址
- 电话号码

订单实体
- ID
- 用户信息
- 商品信息
- 订单状态

商品实体
- 商品Id
- 商品名字
- 商品作者
- 商品价格
- 商品介绍
- 商品库存
- 商品描述
- 商品类别

购物车实体
- 购物列表
- 添加商品Item
- 移除商品Item

购物列表
- 商品实体Product
- 购买数量
- 是否有货


<meta http-equiv="refresh" content="1">
