# SpringBoot-Book-Store
一款以 spring-boot 结合 dubbo 为主的在线购书中心，在项目中结合了 mybatis 以及 redis 作为数据库。

# Online-Store

**Online-Store**是一个结合spring-boot的简易在线书城，它支持用户的登录与注册功能，用户能够在商城上对商品进行阅览挑选，该项目还实现了购物车功能，用户能够在登录与不登录情况下将喜欢的商品加入购物车中，删除对购物车中不喜欢的商品，清空购物车等功能，用户能够对订单中的送货地址，送货时间，商品等进行修改，**它能有效地处理多用户同时对商品的下订。**

## 购物车功能
实现方案，参考至 https://juejin.im/entry/5ade92536fb9a07aad17267c

### 购物车的四个问题：
- 用户没登陆用户名和密码, 添加商品, 关闭浏览器再打开后，不登录用户名和密码　问：购物车商品还在吗？ 答：在
- 用户登陆了用户名密码, 添加商品, 关闭浏览器再打开后，不登录用户名和密码　问:购物车商品还在吗？ 答：不在
- 用户登陆了用户名密码, 添加商品, 关闭浏览器,然后再打开，登陆用户名和密码  问:购物车商品还在吗？ 答: 在
- 用户登陆了用户名密码, 添加商品, 关闭浏览器，别的地方打开浏览器  登陆用户名和密码 问：购物车商品还在吗？ 答: 在

#### 实现方案
- 用户没有登陆的情况下，将购物车保存在cookie中，并设置销毁时间
- 用户登陆的情况下，使用redis保存购物车，查询速度比mysql快

#### 实现具体流程图
<img src="https://github.com/Jia-Calvin/SpringBoot-Book_Store/blob/master/img-store/%E4%B9%A6%E5%9F%8E%E8%B4%AD%E7%89%A9%E8%BD%A6%E5%8A%9F%E8%83%BD%E6%B5%81%E7%A8%8B%E5%9B%BE.png" width="800px">

#### 实现完成页面
- 商品详情页面
<img src="https://github.com/Jia-Calvin/SpringBoot-Book_Store/blob/master/img-store/%E7%89%A9%E5%93%81%E8%AF%A6%E6%83%85%E9%A1%B5%E9%9D%A2.png" width="800px">

- 订单页面
<img src="https://github.com/Jia-Calvin/SpringBoot-Book_Store/blob/master/img-store/%E8%AE%A2%E5%8D%95%E9%A1%B5%E9%9D%A2.png" width="800px">


<meta http-equiv="refresh" content="1">
