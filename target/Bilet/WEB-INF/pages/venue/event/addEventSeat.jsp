<%--
  Created by IntelliJ IDEA.
  User: Kray
  Date: 2018/1/27
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bilet 添加座位</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/assets/bootstrap-3.3.7-dist/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/venue/${venue.vid}/events/${event.eid}/seats">返回</a>
        </div>
    </div>
</nav>

<div class="container">
    <h1>Bilet 添加活动座位</h1>
    <hr/>

    <%@include file="../../displayError.jsp" %>

    <form:form action="/venue/${venue.vid}/events/${event.eid}/seats/addPost" method="post"
               role="form">
        <div class="form-group">
            <label>选择一种座位</label>
            <select class="form-control" name="seatId" id="seatId">
                <c:forEach items="${eventSeats}" var="seat">
                    <option value="${seat.sid}">
                            ${seat.name} - 最多 ${seat.number} 个
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="number">数量</label>
            <input type="number" max=max class="form-control" id="number"
                   name="number"
                   placeholder="请输入座位数量" required/>
        </div>
        <div class="form-group">
            <label for="number">价格</label>
            <input type="number" min="0" class="form-control" id="price" name="price" placeholder="请输入座位价格" required/>
        </div>
        <hr/>
        <b>注意：一旦提交座位情况，将不得更改。请谨慎小心提交！</b>
        <hr/>
        <button type="submit" class="btn btn-sm btn-success">提交</button>
    </form:form>
</div>

<script>
    var max = 0;
    $("#seatId").onselect = function () {
        max = value;
        alert(max);
    }
</script>

<%@include file="../../jsFile.jsp" %>
</body>
</html>