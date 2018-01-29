<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Bilet 用户详情</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h1>Bilet 用户详情</h1>
    <hr/>
    <h2>欢迎您！${member.email}</h2>
    <hr/>
    <a href="#" type="button" class="btn btn-primary btn-sm">订单历史</a>
    <a href="/member/update/${member.mid}" type="button" class="btn btn-warning btn-sm">修改信息</a>
    <hr/>
    <table class="table table-bordered table-striped">
        <tr>
            <th>邮箱</th>
            <td>${member.email}</td>
        </tr>
        <tr>
            <th>银行账户</th>
            <td>${member.bankAccount}</td>
        </tr>
        <tr>
            <th>积分</th>
            <td>${member.point}</td>
        </tr>
        <tr>
            <th>等级</th>
            <td>${member.level}</td>
        </tr>
        <tr>
            <th>邮箱状态</th>
            <td>${member.isEmailPassed}</td>
        </tr>
        <tr>
            <th>账户状态</th>
            <td>${member.isTerminated}</td>
        </tr>
    </table>
    <c:if test="${!empty events}">
        <hr/>
        <h2>演出列表</h2>
        <table class="table table-bordered table-striped">
            <tr>
                <th>活动 ID</th>
                <th>名称</th>
                <th>类别</th>
                <th>时间</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${events}" var="event">
                <tr>
                    <td>${event.eid}</td>
                    <td>${event.name}</td>
                    <td>${event.eventTypeEntity.name}</td>
                    <td>${event.time}</td>
                    <td>${event.description}</td>
                    <td>
                        <a class="btn btn-primary" href="#" role="button">购票</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <hr/>
    <a href="/member/logout" type="button" class="btn btn-danger btn-sm">登出</a>
</div>
<%@include file="../jsFile.jsp" %>
</body>
</html>