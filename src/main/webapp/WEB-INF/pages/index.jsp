<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet Homepage</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Bilet</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right" id="bs-example-navbar-collapse-1">
            <button type="button" class="btn btn-default navbar-btn">登录</button>
            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">注册<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="/member/add">会员注册</a></li>
                    <li><a href="#">场馆注册</a></li>
                </ul>
            </div>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

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
                <li><a href="#">场馆注册</a></li>
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