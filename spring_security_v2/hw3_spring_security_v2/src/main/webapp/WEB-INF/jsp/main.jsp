
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        您是超级管理员可以管理信息。
    </sec:authorize>
    <br/>
    <sec:authorize access="hasRole('ROLE_USER')">
        您是普通用户只能查看信息。
    </sec:authorize>
</body>
</html>
