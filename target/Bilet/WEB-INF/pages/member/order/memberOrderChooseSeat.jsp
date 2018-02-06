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
    <title>Bilet 选座购买</title>
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
    <h1>Bilet 选座购买</h1>

    <%@include file="../../displayError.jsp" %>

    <hr/>
    <h2>活动名称：${event.name}</h2>
    <hr/>
    <c:if test="${!empty event.eventSeats}">
        <form:form action="/member/${member.mid}/order/${event.eid}/chooseSeatPost" method="get" role="form">

            <div class="form-group">
                <label>选座</label>
                <table class="table table-bordered table-striped">
                    <tr>
                        <th>名称</th>
                        <th>可选数量</th>
                        <th>价格</th>
                        <th>购买数量</th>
                    </tr>
                    <c:forEach items="${event.eventSeats}" var="eventSeat">
                        <tr>
                            <td>${eventSeat.seat.name}</td>
                            <td>${eventSeat.number}</td>
                            <td>${eventSeat.price}</td>
                            <td>
                                <label for="eventSeatNumber${eventSeat.esid}" hidden></label>
                                <input class="form-control" type="number" min="0" max="6"
                                       name="eventSeatNumber${eventSeat.esid}"
                                       id="eventSeatNumber${eventSeat.esid}" value="0"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
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
