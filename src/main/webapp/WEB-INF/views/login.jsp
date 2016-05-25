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

        var error = "${error}";
        if(error!=""){
            alert(error);
        }

    </script>
</head>
<body>
<h3>用户登录</h3>
<form id="login" action="<%=request.getContextPath()%>/login/customSecurityCheck.ht" method="post">
    <input name="username" type="text" value=""/>
    <input name="password" type="password" value=""/>
    <label>验证码：
        <input name="code" type="text" maxlength="4" class="chknumber_input" />
    </label>
    <img src="<%=request.getContextPath()%>/login/code.ht" onclick="reloadImg()" id="code" width="85" height="30" style="margin-bottom: -3px"/>
    <input name="rememberMe" type="checkbox">
    <input type="submit" value="保存"/>
    <input type="button" value="注册" onclick="location.href='<%=request.getContextPath()%>/login/register.ht'"/>
</form>
</body>
<script type="text/javascript">
    function reloadImg(){
        document.getElementById("code").src='<%=request.getContextPath()%>/login/code.ht?' + Math.floor(Math.random()*100);
     //   $(this).hide().attr('src', '<%=request.getContextPath()%>/login/code.ht?' + Math.floor(Math.random()*100) ).fadeIn();
    }
</script>
</html>
