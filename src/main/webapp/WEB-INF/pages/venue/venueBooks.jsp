<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/27
  Time: 14:30
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
    <title>Bilet 财务管理</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/venue/show/${venue.vid}">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 财务管理</h1>
    <hr/>
    <c:if test="${empty books}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>没有账单
        </div>
    </c:if>
    <c:if test="${!empty books}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>名称</th>
                <th>金额</th>
                <th>是否已确认</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.event}</td>
                    <td>${book.amount}</td>
                    <td>${book.isConfirmed}</td>
                    <td>
                        <a class="btn btn-primary" href="/venue/${venue.vid}/events/${book.eid}/orders"
                           role="button">订单管理</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>
