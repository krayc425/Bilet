<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/26
  Time: 13:50
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
    <title>Bilet 座位管理</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/venue/show/${vid}">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 座位管理</h1>
    <hr/>
    <a href="/venue/${vid}/seats/add" type="button" class="btn btn-primary btn-sm">添加</a>
    <hr/>
    <c:if test="${empty seats}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>没有座位，请<a
                href="/venue/${vid}/seats/add" type="button" class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>
    <c:if test="${!empty seats}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>座位 ID</th>
                <th>名称</th>
                <th>数量</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${seats}" var="seat">
                <tr>
                    <td>${seat.sid}</td>
                    <td>${seat.name}</td>
                    <td>${seat.number}</td>
                    <td>
                        <a href="/venue/${vid}/seats/update/${seat.sid}" type="button"
                           class="btn btn-sm btn-warning">修改</a>
                        <a href="/venue/${vid}/seats/delete/${seat.sid}" type="button"
                           class="btn btn-sm btn-danger">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>
