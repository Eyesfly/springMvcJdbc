<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/25
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
<jsp:include page="/common/common.jsp"></jsp:include>
    <script type="text/javascript">
        var toolbar = [
        <shiro:hasPermission name="/user/create">
        {
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
                parent.ajaxLoading();
                var obj=new Object();
                $.post("<%=request.getContextPath()%>/user/create.ht",obj,
                        function (data, textStatus){
                            parent.openHTMLSelf('新增用户信息',data);
                        }, "html");
            }
        },
        </shiro:hasPermission>
        <shiro:hasPermission name="/user/create">
        {
            text:'删除',
            iconCls:'icon-cut',
            handler:function(){
                var deleteRows=$('#user-table').datagrid('getSelections');
                if(deleteRows!=null && deleteRows.length>0){
                    $.messager.confirm('确认信息', '确认要删除吗？', function(r){
                        if (r){
                            var obj=new Object();
                            var s = '';
                            for(var i=0; i<deleteRows.length; i++){
                                if (s != '') s += ',';
                                s += deleteRows[i].id;
                            }
                            obj.fileIds=s;
                            $.post("<%=request.getContextPath()%>/user/deleteAll.ht",obj,
                                    function (data, textStatus){
                                        if(data.result){
                                            $.messager.show({
                                                title:'信息',
                                                msg:'删除成功！',
                                                timeout:4000,
                                                showType:'slide'
                                            });
                                            $('#user-table').datagrid('reload');
                                        }else{
                                            $.messager.alert('信息',data.message,'error');
                                        }
                                    }, "json");
                        }
                    });
                }



            }
        }
        </shiro:hasPermission>
    ];
    </script>
</head>
<body>


<table id="user-table" class="easyui-datagrid" title="用户信息列表" data-options="fit:true,rownumbers:true,method:'post',toolbar:toolbar" pagination="true"
       url="<%=request.getContextPath()%>/user/userJson.ht" style="width:100%;height:100%;">
    <thead>
    <tr>
        <th field="checked"	checkbox="true" >全选</th>
        <th data-options="field:'id',width:80"> ID</th>
        <th data-options="field:'uname',width:100">uname</th>
        <th data-options="field:'unumber',width:80,align:'right'">unumber</th>

    </tr>
    </thead>
</table>






<%--<table border="1">
    <tr>
        <th>ID</th>
        <th>UNAME</th>
        <th>UNUMBER</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${userInfos}" var="obj">
            <tr>
                <td>${obj.id}</td>
                <td>${obj.uname}</td>
                <td>${obj.unumber}</td>
                <td><a href="<%=request.getContextPath()%>/user/delete/${obj.id}.ht">删除</a> &nbsp;&nbsp;| &nbsp;&nbsp;<a href="<%=request.getContextPath()%>/user/update/${obj.id}.ht">编辑</a></td>
            </tr>
    </c:forEach>
</table>--%>
</body>
</html>
