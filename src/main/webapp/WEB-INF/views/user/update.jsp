<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/user/saveUserInfo.ht" method="post">
    <input type="hidden" name="id" value="${userInfo.id}"/>
    <input name="uname" type="text" value="${userInfo.uname}"/>
    <input name="unumber" type="text" value="${userInfo.unumber}"/>
    <input type="submit"/>
</form>
</body>
</html>
