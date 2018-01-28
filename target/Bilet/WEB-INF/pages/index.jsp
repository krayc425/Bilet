<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<div class="jumbotron">
    <div class="container">
        <h1>Hello, Bilet!</h1>
        <p>只是另一个购票网站而已</p>
        <p>
        <div class="btn-group">
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">注册<span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="/member/add">会员注册</a></li>
                <li><a href="/venue/add">场馆申请</a></li>
            </ul>
        </div>
        <div class="btn-group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">登录<span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="/member/login">会员登录</a></li>
                <li><a href="/venue/login">场馆登录</a></li>
                <li><a href="/admin/login">管理员登录</a></li>
            </ul>
        </div>
        </p>
    </div>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/assets/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>