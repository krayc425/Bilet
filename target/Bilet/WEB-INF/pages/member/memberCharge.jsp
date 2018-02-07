<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/25
  Time: 22:54
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
    <title>Bilet 充值</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/member/show/${member.mid}">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 充值</h1>
    <hr/>

    <form:form action="/member/chargePost/${member.mid}" method="get" role="form">
        <div class="form-group">
            <label>充值账号</label>
            <p>${bankAccount.bankAccount}</p>
        </div>
        <div class="form-group">
            <label>余额</label>
            <p>${bankAccount.balance}</p>
        </div>
        <div class="form-group">
            <label for="chargeAmount">充值金额</label>
            <input type="number" min="0" class="form-control" id="chargeAmount" name="chargeAmount" step="0.01"
                   placeholder="请输入充值金额" required/>
        </div>
        <hr/>
        <button type="submit" class="btn btn-sm btn-success">充值</button>
    </form:form>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>