<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/28
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <script type="text/javascript">
        var error = "${param.error}";
        if(error!=""){
            alert(error);
        }
    </script>
</head>
<body>
<h3>用户登录</h3>
<form action="<%=request.getContextPath()%>/login.ht" method="post">
    <input name="username" type="text" value=""/>
    <input name="password" type="password" value=""/>
    <input name="rememberMe" type="checkbox">
    <input type="submit" value="保存"/>
</form>
</body>
</html>
