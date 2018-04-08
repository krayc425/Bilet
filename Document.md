# Bilet 文档 

**141210026 宋奎熹**

# 一、数据库设计

## 1、ER图

![](https://ws1.sinaimg.cn/large/006tKfTcly1fpyc5bw8i3j314r15c0zr.jpg)

## 2、数据库表

### Admin

| 字段 | 含义 |  外键关联表及属性 |
| --- | --- | --- |
|  `username` varchar(255) DEFAULT NULL | 管理员用户名|
|  `password` varchar(255) NOT NULL | 管理员密码|
|  `aid` int(11) NOT NULL AUTO_INCREMENT|管理员 ID|

### AdminBook

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`abid` int(11) NOT NULL AUTO_INCREMENT | 管理员账本 ID|
|  `eid` int(11) NOT NULL | 对应活动 ID| `Event` (`eid`)|
|  `vid` int(11) NOT NULL | 对应场馆 ID| `Venue` (`vid`)|
|  `amount` double NOT NULL DEFAULT '0' | 账本金额
|  `isConfirmed` tinyint(1) NOT NULL DEFAULT '0'|是否已经结算|

### BankAccount

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`bankAccount` varchar(255) NOT NULL|银行账户|
|  `balance` double NOT NULL DEFAULT '0'|账户余额|
|  `bid` int(11) NOT NULL AUTO_INCREMENT|账户 ID|

### Coupon

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`cid` int(11) NOT NULL AUTO_INCREMENT|优惠券 ID|
|  `discount` double NOT NULL DEFAULT '0'|打折百分比（0~1 间的小数）|
|  `name` varchar(255) NOT NULL  | 优惠券名称|
|  `point` int(11) NOT NULL DEFAULT '0'|优惠券需要积分数|

### Event

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|  `eid` int(11) NOT NULL AUTO_INCREMENT | 活动 ID|
|  `name` varchar(255) NOT NULL|活动名称|
|  `type` int(11) NOT NULL DEFAULT '0'|活动类型|`EventType` (`etid`)|
|  `description` varchar(255) NOT NULL|活动描述|
|  `time` datetime NOT NULL|活动时间|
|  `vid` int(11) NOT NULL|活动场馆 ID|`Venue` (`vid`)|

### EventSeat

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|  `eid` int(11) NOT NULL|活动 ID|`Event` (`eid`)|
|  `sid` int(11) NOT NULL|座位 ID|`Seat` (`sid`)|
|  `price` int(11) NOT NULL DEFAULT '0'|活动座位价格|
|  `esid` int(11) NOT NULL AUTO_INCREMENT|活动座位 ID|
|  `number` int(11) NOT NULL DEFAULT '0'|最大个数|

### EventType

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|  `etid` int(11) NOT NULL AUTO_INCREMENT|活动类型 ID|
|  `name` varchar(255) NOT NULL|活动类型名称|

### Level

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`lid` int(11) NOT NULL AUTO_INCREMENT|等级 ID|
|  `minimumPoint` int(11) NOT NULL|等级最少积分数|
|  `description` varchar(255) NOT NULL|等级名称|
|  `discount` double NOT NULL|等级折扣（0~1 间小数）|

### Member

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
| `mid` int(11) NOT NULL AUTO_INCREMENT | 会员 ID|
|  `email` varchar(255) NOT NULL | 会员邮箱|
|  `password` varchar(255) NOT NULL|会员密码|
|  `bankAccount` varchar(255) NOT NULL|会员银行账户|
|  `totalPoint` int(11) NOT NULL DEFAULT '0'|会员总积分|
|  `isTerminated` tinyint(1) NOT NULL DEFAULT '0'|会员是否已经取消资格|
|  `isEmailPassed` tinyint(1) NOT NULL DEFAULT '0'|会员时候已经通过审核|
|  `currentPoint` int(11) NOT NULL DEFAULT '0'|会员当前积分|

### MemberBook

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`mbid` int(11) NOT NULL AUTO_INCREMENT|会员账本 ID|
|  `mid` int(11) NOT NULL|会员 ID|`Member` (`mid`)|
|  `oid` int(11) DEFAULT NULL|订单 ID|`Order` (`oid`)|
|  `amount` double DEFAULT '0'|金额|
|  `type` int(11) NOT NULL|账本类型|
|  `time` datetime NOT NULL|记录时间|

### MemberCoupon

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`mid` int(11) NOT NULL|优惠券所属会员 ID|`Member` (`mid`)|
| `cid` int(11) NOT NULL|优惠券 ID|`Coupon` (`cid`)|
|  `mcid` int(11) NOT NULL AUTO_INCREMENT|会员优惠券 ID|
|  `usageNum` int(11) NOT NULL DEFAULT '0'|是否已经使用|
|  `time` datetime NOT NULL|获得时间|

### Order

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
| `oid` int(11) NOT NULL AUTO_INCREMENT | 订单 ID|
|  `orderTime` datetime NOT NULL|下单时间|
|  `mid` int(11) NOT NULL|下单会员 ID|`Member` (`mid`)|
|  `eid` int(11) NOT NULL|订单活动 ID|`Event` (`eid`)|
|  `mcid` int(11) DEFAULT NULL|订单会有优惠券 ID|`MemberCoupon` (`mcid`)|
|  `status` int(11) NOT NULL|订单状态|
|  `type` int(11) DEFAULT '0'|订单支付类型|

### OrderEventSeat

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`oesid` int(11) NOT NULL AUTO_INCREMENT|订单活动座位 ID|
|  `oid` int(11) NOT NULL|所属订单 ID|`Order` (`oid`)|
|  `esid` int(11) NOT NULL|对应活动座位 ID|`EventSeat` (`esid`)|
|  `seatNumber` int(11) NOT NULL DEFAULT '0'|座位数|
|  `isValid` int(11) DEFAULT '1'|是否有效|

### Seat

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`sid` int(11) NOT NULL AUTO_INCREMENT|座位 ID|
|  `name` varchar(255) NOT NULL|座位名称|
|  `number` int(11) NOT NULL DEFAULT '0'|座位最大数量|
|  `vid` int(11) NOT NULL|所属场馆 ID|`Venue` (`vid`)|

### Venue

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`vid` int(11) NOT NULL AUTO_INCREMENT|场馆 ID|
|  `address` varchar(255) NOT NULL|场馆地址|
|  `isPassed` tinyint(1) NOT NULL DEFAULT '0'|是否通过审核|
|  `name` varchar(255) NOT NULL|场馆名称|
|  `password` varchar(255) NOT NULL|登录密码|

### VenueBook

| 字段 | 含义 | 外键关联表及属性 |
| --- | --- | --- |
|`vbid` int(11) NOT NULL AUTO_INCREMENT|场馆账本 ID|
|  `vid` int(11) NOT NULL|对应场馆 ID|`Venue` (`vid`)|
|  `eid` int(11) NOT NULL|对应活动 ID|`Event` (`eid`)|
|  `amount` double NOT NULL DEFAULT '0'|账本金额|
|  `isConfirmed` tinyint(1) NOT NULL DEFAULT '0'|是否已经结算|

# 二、架构设计

## 1、工程的项目结构截图

![](https://ws1.sinaimg.cn/large/006tKfTcly1fpyc3ygsrkj30em0k4wfz.jpg)

## 2、是否使用框架

* 使用 SpringMVC + Spring + JPA。
* Spring MVC 管理路由和整体架构，Controller 上拦截界面请求，自动注入逻辑层进行处理，逻辑层注入数据层服务。
* 数据层使用 JPARepository 进行数据库操作。

## 3、前端页面是否使用框架

* 使用 Bootstrap 框架

# 三、类设计

## 1、各包的类、名称及职责

### controller

| 类名 | 职责 | 
| --- | --- | 
|AdminController|管理员界面控制|
|BaseController|控制器基类|
|MainController|主界面控制器|
|MemberController|会员控制器|
|MemberCouponController|会员优惠券控制器|
|MemberOrderController|会员订单控制器|
|VenueController|场馆控制器|

### model

| 类名 | 职责 | 
| --- | --- | 
|AdminBookEntity|管理员账本实体数据类|
|AdminEntity|管理员实体数据类|
|BankAccountEntity|银行账号实体数据类|
|CouponEntity|优惠券实体数据类|
|EventEntity|活动实体数据类|
|EventSeatEntity|活动座位实体数据类|
|EventTypeEntity|活动类型实体数据类|
|LevelEntity|会员等级实体数据类|
|MemberBookEntity|会员账本实体数据类|
|MemberCouponEntity|会员优惠券实体数据类|
|MemberEntity|会员实体数据类|
|OrderEntity|订单实体数据类|
|OrderEventSeatEntity|订单中的活动座位实体数据类|
|SeatEntity|场馆座位实体数据类|
|VenueBookEntity|场馆账本实体数据类|
|VenueEntity|场馆实体数据类|

### repository

| 类名 | 职责 | 
| --- | --- |
|AdminBookRepository|操作管理员账本的 JPA 类|
|AdminRepository|操作管理员的 JPA 类|
|BankAccountRepository|操作银行账户的 JPA 类|
|CouponRepository|操作优惠券的 JPA 类|
|EventRepository|操作活动的 JPA 类|
|EventSeatRepository|操作活动座位的 JPA 类|
|EventTypeRepository|操作活动类型的 JPA 类|
|LevelRepository|操作会员等级的 JPA 类|
|MemberBookRepository|操作会员账本的 JPA 类|
|MemberCouponRepository|操作会员优惠券的 JPA 类|
|MemberRepository|操作会员的 JPA 类|
|OrderEventSeatRepository|操作订单中的活动座位的 JPA 类|
|OrderRepository|操作订单的 JPA 类|
|SeatRepository|操作座位的 JPA 类|
|VenueBookRepository|操作场馆账本的 JPA 类|
|VenueRepository|操作场馆的 JPA 类|

### service

| 类名 | 职责 | 
| --- | --- |
|AdminService|管理员账本查询、相关操作等的逻辑层接口|
|BookService|管理员、会员、场馆账本增删改查的逻辑层接口|
|CouponService|优惠券增删改查、兑换的逻辑层接口|
|EventService|活动的增删改查、找活动座位的逻辑层接口|
|LevelService|查询会员等级的逻辑层接口|
|MemberService|会员的增删改查、登录、取消、充值等的逻辑层接口|
|OrderService|订单的增删改查、改变状态的逻辑层接口|
|SeatService|座位的增删改查的逻辑层接口|
|VenueService|场馆的增删改查、登录的逻辑层接口|

### impl

分别是以上 Service 接口的实现，共 9 个。

### util

| 类名 | 职责 | 
| --- | --- |
|DateFormatter|格式化输入输出活动时间的工具类|
|LoginStatus|登录状态的枚举类|
|OrderStatus|订单状态的枚举类|
|OrderType|订单类型的枚举类|
|PayType|支付类型的枚举类|

### vo

| 类名 | 职责 | 
| --- | --- |
|AdminBookVO|管理员账本的数据封装类|
|BankAccountVO|银行账户的数据封装类|
|EventSeatVO|活动座位的数据封装类|
|EventVO|活动的数据封装类|
|MemberBookVO|会员账本的数据封装类|
|MemberCouponVO|会员优惠券的数据封装类|
|MemberInfoVO|会员信息展示的数据封装类|
|MemberUpdateVO|会员修改信息展示的数据封装类|
|MessageVO|展示消息的数据封装类|
|OrderEventSeatVO|订单活动座位的数据封装类|
|OrderVO|订单的数据封装类|
|SeatVO|座位的数据封装类|
|VenueBookVO|场馆账本的数据封装类|
|VenueInfoVO|场馆信息的数据封装类|
|VenueUpdateVO|场馆修改信息展示的数据封装类|

## 2、前端的各页面、名称及功能

### 通用

| 名称 | 功能 | 
| --- | --- |
|displayError.jsp|展示错误信息|
|index.jsp|主页|
|jsFile.jsp|包含一些 js 文件|

### admin

| 名称 | 功能 | 
| --- | --- |
|adminBooks.jsp|管理员账本页面|
|adminDetail.jsp|管理员信息页面|
|adminEvents.jsp|管理员查看活动页面|
|adminMemberOrders.jsp|管理员查看会员订单页面|
|adminMembers.jsp|管理员查看会员页面|
|adminVenueEvents.jsp|管理员查看场馆活动页面|
|adminVenueOrders.jsp|管理员查看场馆订单页面|
|adminVenuePass.jsp|管理员审核场馆页面|
|adminVenues.jsp|管理员查看场馆页面|
|loginAdmin.jsp|管理员登录页面|

### member

| 名称 | 功能 | 
| --- | --- |
|addMember.jsp|会员注册页面|
|memberCouponDetail.jsp|会员查看优惠券展示页面|
|redeemMemberCoupon.jsp|会员兑换优惠券页面|
|loginMember.jsp|会员登录页面|
|memberBooks.jsp|会员查看账本页面|
|memberCharge.jsp|会员充值页面|
|memberDetail.jsp|会员查看详细信息页面|
|memberOrderChooseSeat.jsp|会员下订单（选座）页面|
|memberOrderDetail.jsp|会员查看订单信息页面|
|memberOrderRandomSeat.jsp|会员下订单（不选座）页面|
|memberOrders.jsp|会员查看订单列表页面|
|updateMember.jsp|修改会员信息页面|

### venue

| 名称 | 功能 | 
| --- | --- |
|addVenue.jsp|场馆注册页面|
|addVenueSuccess.jsp|场馆注册成功页面|
|addEvent.jsp|增加活动页面|
|addEventSeat.jsp|增加活动座位页面|
|eventChooseSeat.jsp|活动现场购票页面|
|eventOrders.jsp|活动订单页面|
|eventSeats.jsp|活动座位页面|
|loginVenue.jsp|场馆登录页面|
|addSeat.jsp|增加座位页面|
|updateSeat.jsp|修改座位页面|
|updateVenue.jsp|修改场馆信息页面|
|venueBooks.jsp|场馆查看账本页面|
|venueDetail.jsp|场馆查看详细信息页面|
|venueEvents.jsp|场馆查看活动页面|
|venueSeats.jsp|场馆查看座位页面|

# 四、其他

## 1、开发环境（数据库，服务器等）

* 数据库环境

![](https://ws3.sinaimg.cn/large/006tKfTcly1fpyc46n8bgj311w0qkwk4.jpg)

* 服务器

![](https://ws3.sinaimg.cn/large/006tKfTcly1fpyc4bgorxj31kw158k2d.jpg)

* IDE

![](https://ws3.sinaimg.cn/large/006tKfTcly1fpyk6d71urj311g0o41kx.jpg)

## 2、开发心得体会

这是我第一次完整写一个 J2EE 项目，也是第一次自己完成 Web 页面，并完成前后端的对接。过程充满挑战但也充满乐趣，尤其是对 SpringMVC、JPA 等技术的使用，更加地得心应手。不过这过程中也暴露了一些问题，比如数据库可能建表有些冗余，JPA 中外键的映射可能没有配置好，所以导致页面加载速度过慢。这些都是在以后的开发中需要避免的。

