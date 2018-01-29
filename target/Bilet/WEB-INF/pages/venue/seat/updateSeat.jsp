<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/25
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet 更新座位</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/venue/${vid}/seats">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 更新座位</h1>
    <hr/>
    <form:form action="/venue/${vid}/seats/updatePost/${seat.sid}" method="post" role="form">
        <div class="form-group">
            <label for="name">名字</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="请输入名字" value="${seat.name}"/>
        </div>
        <div class="form-group">
            <label for="number">数量</label>
            <input type="text" class="form-control" id="number" name="number"
                   placeholder="请输入数量" value="${seat.number}"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-sm btn-success">提交</button>
        </div>
    </form:form>
</div>
<%@include file="../../jsFile.jsp" %>
</body>
</html>