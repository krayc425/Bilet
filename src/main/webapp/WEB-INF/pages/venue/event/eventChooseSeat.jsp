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
    <title>Bilet 现场购票</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/venue/${venue.vid}/events/${event.eid}/orders">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 现场购票</h1>

    <%@include file="../../displayError.jsp" %>

    <hr/>
    <h2>活动名称：${event.name}</h2>
    <hr/>

    <div class="form-group">
        <c:if test="${!empty eventSeats}">
            <form:form action="/venue/${venue.vid}/events/${event.eid}/chooseSeatPost" method="get" role="form">
                <div class="form-group">
                    <label for="email">会员邮箱（如不是会员，则留空）</label>
                    <input type="text" class="form-control" id="email" name="email" placeholder="请输入会员邮箱"/>
                </div>
                <div class="form-group">
                    <label>选座</label>
                    <table class="table table-bordered table-striped">
                        <tr>
                            <th>名称</th>
                            <th>可选数量</th>
                            <th>价格</th>
                            <th>购买数量</th>
                        </tr>
                        <c:forEach items="${eventSeats}" var="eventSeat">
                            <tr>
                                <td>${eventSeat.seatName}</td>
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
                <hr/>
                <button type="submit" class="btn btn-sm btn-success">提交</button>
            </form:form>
        </c:if>
    </div>
</div>
<%@include file="../../jsFile.jsp" %>
</body>
</html>
