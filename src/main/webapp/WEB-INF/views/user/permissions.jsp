<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/31
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/common.jsp"></jsp:include>
</head>
<body>
<div class="easyui-panel" style="padding:5px">
    <ul id="tt" class="easyui-tree" data-options="url:'<%=request.getContextPath()%>/role/permissionsJson.ht',method:'post',animate:true,checkbox:true,fit:true"></ul>
</div>
</body>
</html>
