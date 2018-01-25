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
<div class="container">
    <h1>Bilet -用户管理</h1>
    <hr/>
    <h3>所有用户 <a href="/admin/members/add" type="button" class="btn btn-primary btn-sm">添加</a></h3>
    <!-- 如果用户列表为空 -->
    <c:if test="${empty userList}">
        <div class="alert alert-warning" role="alert">
            <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Member 为空，请<a href="/admin/members/add"
                                                                                              type="button"
                                                                                              class="btn btn-primary btn-sm">添加</a>
        </div>
    </c:if>
    <!-- 如果用户列表非空 -->
    <c:if test="${!empty userList}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>ID</th>
                <th>邮箱</th>
                <th>密码</th>
                <th>银行账号</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${userList}" var="member">
                <tr>
                    <td>${member.mid}</td>
                    <td>${member.email}</td>
                    <td>${member.password}</td>
                    <td>${member.bankAccount}</td>
                    <td>
                        <a href="/admin/members/show/${member.mid}" type="button" class="btn btn-sm btn-success">详情</a>
                        <a href="/admin/members/update/${member.mid}" type="button" class="btn btn-sm btn-warning">修改</a>
                        <a href="/admin/members/delete/${member.mid}" type="button" class="btn btn-sm btn-danger">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/assets/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/assets/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</body>
</html>