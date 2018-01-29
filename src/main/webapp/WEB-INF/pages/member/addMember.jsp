<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/25
  Time: 14:53
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
    <title>Bilet 会员注册</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 会员注册</h1>
    <hr/>
    <form:form action="/member/addPost" method="post" role="form">
        <div class="form-group">
            <label for="email">邮箱</label>
            <input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱" required/>
        </div>
        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" required/>
        </div>
        <div class="form-group">
            <label for="bankAccount">银行账户</label>
            <input type="text" class="form-control" id="bankAccount" name="bankAccount" placeholder="请输入银行账户" required/>
        </div>
        <button type="submit" class="btn btn-sm btn-success">注册</button>
    </form:form>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>