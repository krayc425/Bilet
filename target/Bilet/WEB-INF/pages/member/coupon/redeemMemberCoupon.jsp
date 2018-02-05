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
    <title>Bilet 兑换优惠券</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/member/coupon/${member.mid}">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 兑换优惠券</h1>
    <hr/>
    <h2>我的积分：${member.currentPoint}</h2>
    <c:if test="${!empty allCoupons}">
        <hr/>
        <h2>可兑换优惠券列表</h2>
        <table class="table table-bordered table-striped">
            <tr>
                <th>优惠券名称</th>
                <th>至少需要积分</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${allCoupons}" var="coupon">
                <tr>
                    <td>${coupon.name}</td>
                    <td>${coupon.point}</td>
                    <td>
                        <c:if test="${member.currentPoint >= coupon.point}">
                            <a class="btn btn-primary" href="/member/coupon/${member.mid}/redeem/${coupon.cid}"
                               role="button">兑换</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<%@include file="../../jsFile.jsp" %>
</body>
</html>