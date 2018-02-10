<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/27
  Time: 20:03
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
    <title>Bilet 立即购票</title>
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
    <h1>Bilet 立即购票</h1>

    <%@include file="../../displayError.jsp" %>

    <hr/>
    <h2>活动名称：${event.name}</h2>
    <hr/>
    <c:if test="${!empty eventSeats}">
        <form:form action="/member/order/${member.mid}/event/${event.eid}/randomSeatPost" method="get" role="form">

            <div class="form-group">
                <label for="eventSeatId">选择一种座位</label>
                <select class="form-control" name="eventSeatId" id="eventSeatId">
                    <c:forEach items="${eventSeats}" var="eventSeat">
                        <option value="${eventSeat.esid}">
                                ${eventSeat.seatName}，${eventSeat.price} 元，剩余 ${eventSeat.number} 个
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="eventSeatNumber">购买数量</label>
                <input class="form-control" type="number" min="0" max="20"
                       name="eventSeatNumber"
                       id="eventSeatNumber" value="0"/>
            </div>

            <c:if test="${!empty coupons}">
                <div class="form-group">
                    <label>优惠券</label>
                    <select class="form-control" name="memberCouponCid">
                        <option>选择一张优惠券</option>
                        <c:forEach items="${coupons}" var="memberCoupon">
                            <option value="${memberCoupon.mcid}">
                                    ${memberCoupon.couponByCid.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </c:if>

            <hr/>
            <button type="submit" class="btn btn-sm btn-success">提交</button>
        </form:form>
    </c:if>
</div>
<%@include file="../../jsFile.jsp" %>
</body>
</html>
