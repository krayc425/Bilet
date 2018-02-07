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
            <a class="navbar-brand" href="/venue/${venue.vid}/events">返回</a>
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
                <th>时间</th>
                <th>订票人</th>
                <th>座位数</th>
                <th>活动名称</th>
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.orderTime}</td>
                    <td>${order.memberEmail}</td>
                    <td>${order.seatNumber}</td>
                    <td>${order.eventByEid.name}</td>
                    <td>${order.status}</td>
                    <td>
                        <c:if test="${order.status.equals('等待检票')}">
                            <a class="btn btn-success"
                               href="/venue/${venue.vid}/events/${event.eid}/orders/${order.oid}/confirm"
                               role="button">确认检票</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../../jsFile.jsp" %>
</body>
</html>
