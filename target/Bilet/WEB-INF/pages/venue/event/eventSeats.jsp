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
    <title>Bilet 活动座位管理</title>
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
    <h1>Bilet 活动座位管理</h1>
    <hr/>
    <c:if test="${eventSeats.size() != venue.seatsByVid.size()}">
        <a href="/venue/${venue.vid}/events/${event.eid}/seats/add" type="button" class="btn btn-primary btn-sm">添加</a>
        <hr/>
    </c:if>
    <c:if test="${empty eventSeats}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>没有活动座位，请<a
                href="/venue/${venue.vid}/events/${event.eid}/seats/add" type="button"
                class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>
    <c:if test="${!empty eventSeats}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>名称</th>
                <th>最大数量</th>
                <th>数量</th>
                <th>价格</th>
            </tr>
            <c:forEach items="${eventSeats}" var="eventSeat">
                <tr>
                    <td>${eventSeat.seat.name}</td>
                    <td>${eventSeat.seat.number}</td>
                    <td>${eventSeat.number}</td>
                    <td>${eventSeat.price}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../../jsFile.jsp" %>
</body>
</html>
