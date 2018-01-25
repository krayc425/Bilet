# Bilet    

南京大学软件学院 2017-2018 学年**J2EE 与中间件**大作业

# 要求

* 个人完成（JavaEE技术）
* 检查内容：
    * 项目演示（15%，功能点）
    * 代码 + 简单文档（架构设计、类的设计、数据库设计、其他）（15%）
* 检查时间：最后 3 周
    * 2018.3.19 开始，按学号顺序
    * 每次课 35 人，课堂未演示到的同学，在当周到我办公室（909）演示
* **注意：如雷同，则成绩/人数**

# 需求

## 会员

* 注册
    * 邮箱，验证后可登录
* 会员资格取消
    * 会员取消资格（即停止）
    * 停止：不可恢复，但不删除数据
* 会员级别
    * 根据消费金额
        * 设置不同级别，享受不同优惠
        * 获取积分，积分可兑换为优惠券
* 修改会员信息
* 预订，规则：
    * “选座购买”，每单限 6 张
    * “立即购买（不选座）”，每单限 20 张，演出前 2 周开票/配票；如果配票不成功，退款
        * 注：配票规则，请同学们自定义
    * 下单成功后，需在 15 分钟内完成支付，未支付成功的订单，将在下单 15 分钟后系统自动取消
    * 支付：模拟网上银行，支付宝等（数据库：账号和余额）
* 退订，规则：
    * 按不同期限，退不同比例金额
        * 具体规则自定义
* 查看订单状态
* 查看本人统计信息
    * 预订/退订/消费等
    
## 场馆

* 注册申请
    * 编码：7 位识别码，系统自动分配，用于登录
    * 场馆信息：地点，座位情况等
    * 注册申请/修改场馆信息
        * 需 Bilet 经理审批
* 发布计划（未来一个时间段）
    * 时间，价格（按座位设不同价位），类型（音乐会、舞蹈、话剧、体育比赛等），简单描述等
* 现场购票
    * 会员优惠；非会员原价
* 检票登记
* 查看本场馆统计信息
    * 预订/退订/财务等

## Bilet 经理

* 审批各场馆注册/修改场馆信息的申请
* 结算
    * 将会员支付结算给各店
        * 比例自行设计
* 查看 Bilet 统计信息
    * 各场馆统计
    * 会员统计
    * Bilet 财务情况
    * 采用图表显示（可选）

