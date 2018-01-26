<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/25
  Time: 15:08
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
    <title>Bilet 场馆详情</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Bilet 场馆详情</h1>
    <hr/>
    <table class="table table-bordered table-striped">
        <tr>
            <th>识别码</th>
            <td>${user.vid}</td>
        </tr>
        <tr>
            <th>名称</th>
            <td>${user.name}</td>
        </tr>
        <tr>
            <th>地址</th>
            <td>${user.address}</td>
        </tr>
        <tr>
            <th>密码</th>
            <td>${user.password}</td>
        </tr>
        <tr>
            <th>是否通过</th>
            <td>${user.isPassed}</td>
        </tr>
        <tr>
            <th>座位</th>
            <td>
                <p>${user.seatsByVid.size()} 个</p>
                <a class="btn btn-default" href="/venue/${user.vid}/seats" role="button">查看详情</a>
            </td>
        </tr>
    </table>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/assets/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>