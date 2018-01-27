<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/26
  Time: 14:07
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
    <title>Bilet 添加活动</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Bilet 添加活动</h1>
    <hr/>
    <form:form action="/venue/${venue.vid}/events/addPost" method="post" role="form">
        <div class="form-group">
            <label for="name">名字</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="请输入活动名字"/>
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <input type="text" class="form-control" id="description" name="description" placeholder="请输入活动描述"/>
        </div>
        <div class="form-group">
            <label for="type">种类</label>
            <select class="form-control" id="type" name="type">
                <c:forEach items="${eventTypes}" var="eventType">
                    <option value="${eventType.etid}">
                        ${eventType.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="time">时间（请按 yyyy-MM-dd HH:mm:ss 格式输入）</label>
            <input type="text" class="form-control" id="time" name="time" placeholder="请输入活动时间"/>
        </div>
        <button type="submit" class="btn btn-sm btn-success">提交</button>
    </form:form>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/assets/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>