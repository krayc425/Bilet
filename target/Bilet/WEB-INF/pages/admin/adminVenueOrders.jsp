<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/27
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet 活动订单</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/admin/venues/events/${venue.vid}">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 活动订单</h1>
    <hr/>
    <c:if test="${empty orders}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>没有订单</a>
        </div>
    </c:if>
    <c:if test="${!empty orders}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>订单号</th>
                <th>下单时间</th>
                <th>座位数</th>
                <th>活动名称</th>
                <th>订单状态</th>
                <th>订单类型</th>
                <th>订单总额</th>
                <th>优惠券</th>
                <th>网站分成</th>
            </tr>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.oid}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.orderTime}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.seatNumber}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.eventByEid.name}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.status}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.type}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.totalAmount}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${order.coupon}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>
                        <c:if test="${order.status.equals('已检票')}">
                            ${order.totalAmount * 0.2}
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>
