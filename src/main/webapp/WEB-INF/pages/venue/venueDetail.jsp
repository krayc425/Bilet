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
    <a href="/venue/${venue.vid}/events" type="button" class="btn btn-default btn-sm">管理活动</a>
    <a href="/venue/update/${venue.vid}" type="button" class="btn btn-warning btn-sm">修改场馆信息</a>
    <hr/>
    <table class="table table-bordered table-striped">
        <tr>
            <th>识别码</th>
            <td>${venue.vid}</td>
        </tr>
        <tr>
            <th>名称</th>
            <td>${venue.name}</td>
        </tr>
        <tr>
            <th>地址</th>
            <td>${venue.address}</td>
        </tr>
        <tr>
            <th>密码</th>
            <td>${venue.password}</td>
        </tr>
        <tr>
            <th>是否通过</th>
            <td>${venue.isPassed}</td>
        </tr>
        <tr>
            <th>座位</th>
            <td>
                <p>${venue.seatsByVid.size()} 个</p>
                <a class="btn btn-default" href="/venue/${venue.vid}/seats" role="button">查看详情</a>
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