<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/28
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<input type="hidden" id="tips"/>
<form id="infoForm" action="<%=request.getContextPath()%>/user/saveUserInfo.ht" method="post">
    <input name="uname" type="text" value=""/>
    <input name="unumber" type="text" value=""/>
    <input type="button" onclick="infoFormOk('查看用户列表');" value="保存"/>
</form>
</body>
</html>
