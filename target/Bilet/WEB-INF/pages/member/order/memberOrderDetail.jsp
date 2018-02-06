<%@ page import="com.krayc.model.SeatEntity" %><%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/27
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet 订单详情</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/member/order/${member.mid}">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 订单详情</h1>
    <hr/>

    <%@include file="../../displayError.jsp" %>

    <div class="form-group">
        <label>基本信息</label>
        <table class="table table-bordered table-striped">
            <tr>
                <th>时间</th>
                <th>活动名称</th>
                <th>订单状态</th>
            </tr>
            <tr>
                <td>${order.orderTime}</td>
                <td>${order.eventByEid.name}</td>
                <td>${order.status}</td>
            </tr>
        </table>
    </div>

    <div class="form-group">
        <label>座位信息</label>
        <table class="table table-bordered table-striped">
            <tr>
                <th>名称</th>
                <th>价格</th>
            </tr>
            <c:forEach items="${seats}" var="orderEventSeat">
                <tr>
                    <td>${orderEventSeat.eventSeatByEsid.seat.name} ${orderEventSeat.seatNumber} 座</td>
                    <td>${orderEventSeat.eventSeatByEsid.price}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>

<%@include file="../../jsFile.jsp" %>
</body>
</html>