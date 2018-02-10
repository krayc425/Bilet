<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/25
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet 用户管理</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/admin/adminHome">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 用户管理</h1>
    <hr/>
    <!-- 如果用户列表为空 -->
    <c:if test="${empty memberList}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>没有会员
        </div>
    </c:if>
    <!-- 如果用户列表非空 -->
    <c:if test="${!empty memberList}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>会员 ID</th>
                <th>邮箱</th>
                <th>累积积分</th>
                <th>可兑换积分</th>
                <th>等级</th>
                <th>邮箱状态</th>
                <th>账户状态</th>
                <th>银行账号</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${memberList}" var="member">
                <tr>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.mid}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.email}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.totalPoint}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.currentPoint}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.level}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.isEmailPassed}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.isTerminated}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>${member.bankAccount}</td>
                    <td rowspan=$row_host1 style='vertical-align: middle;'>
                        <a href="/admin/members/orders/${member.mid}" type="button"
                           class="btn btn-sm btn-primary">订单查看</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>