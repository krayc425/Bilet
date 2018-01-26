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
    <title>SpringMVC Demo 更新用户</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <![endif]-->
</head>
<body>
<div class="container">
    <h1>Bilet 更新用户信息</h1>
    <hr/>
    <form:form action="/admin/members/updateP" method="post" role="form">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" class="form-control" id="email" name="email" placeholder="Enter Email:" value="${user.email}"/>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="text" class="form-control" id="password" name="password" placeholder="Enter Password:" value="${user.password}"/>
        </div>
        <div class="form-group">
            <label for="bankAccount">Bank Account:</label>
            <input type="text" class="form-control" id="bankAccount" name="bankAccount"
                   placeholder="Enter Bank Account:" value="${user.bankAccount}"/>
        </div>
        <!-- 把 id 一并写入 userP 中 -->
        <input type="hidden" id="mid" name="mid" value="${user.mid}"/>
        <div class="form-group">
            <label for="mid"></label>
            <button type="submit" class="btn btn-sm btn-success">提交</button>
        </div>
    </form:form>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/assets/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>