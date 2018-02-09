<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/26
  Time: 20:01
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
    <title>Bilet 管理员首页</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h1>Bilet 管理员首页</h1>
    <hr/>
    <h2>欢迎您！${admin.username}</h2>
    <hr/>
    <a href="/admin/venues" type="button" class="btn btn-primary btn-sm">审核场地信息</a>
    <a href="/admin/events" type="button" class="btn btn-primary btn-sm">结算活动费用</a>
    <a href="/admin/books" type="button" class="btn btn-primary btn-sm">查看财务情况</a>
    <hr/>
    <a href="/admin/logout" type="button" class="btn btn-danger btn-sm">登出</a>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>