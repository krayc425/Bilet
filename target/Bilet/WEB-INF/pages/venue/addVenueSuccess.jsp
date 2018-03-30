<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/3/29
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet 场馆申请结果</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">返回</a>
        </div>
    </div>
</nav>

<body>
<div class="container">
    <h1>Bilet 场馆申请结果</h1>
    <br/>
    <div class="alert alert-success">
        <a href="#" class="close" data-dismiss="alert">
            &times;
        </a>
        申请成功！您的场馆识别号为：<strong>${venue.vid}</strong>，请登录后查看审核情况。
    </div>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>