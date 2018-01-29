<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kylin
  Date: 14/03/2017
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${!empty error}">
    <div class="row">
        <div class="col-md-8">
            <div class="alert alert-warning" role="alert">
                <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>${error}
            </div>
        </div>
    </div>
</c:if>
