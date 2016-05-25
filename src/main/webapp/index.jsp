<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="common/common.jsp"></jsp:include>
    <script type="text/javascript">
        function mainAddTab(title, url){
            if ($('#layoutMainTabs').tabs('exists', title)){
                $('#layoutMainTabs').tabs('select', title);
                var tabs=$('#layoutMainTabs');
                var tab=tabs.tabs('getTab',title);
                var content = '<iframe scrolling="yes" frameborder="0" name="'+title+'-Frame" id="'+title+'-Frame" src="'+url+'" style="width:100%;height:97%;scrolling:yes;overflow:scroll;overflow-y:hidden;overflow-x:hidden;"></iframe>';
                $('#layoutMainTabs').tabs('update', {
                    tab: tab,
                    options:{
                        title:title,
                        content:content
                    }
                });
            } else {
                var content = '<iframe scrolling="yes" frameborder="0" name="'+title+'-Frame" id="'+title+'-Frame" src="'+url+'" style="width:100%;height:97%;scrolling:yes;overflow:scroll;overflow-y:hidden;overflow-x:hidden;"></iframe>';
                $('#layoutMainTabs').tabs('add',{
                    title:title,
                    content:content,
                    closable:true,
                    icon:"icon-add"
                });
            }
        }
        function openHTMLSelf(title,html){
            if(title=='')
                title='&nbsp;';
            var heightO= 200;
            if(title!='加载中...'){
                heightO=heightO+20;
            }
            if(heightO<400) heightO=200;
                $("#htmlDiv").css("height",heightO+"px")  ;
                $('#htmlDiv').html(html);
                $('#html-dialog').dialog({
                    width: 500,
                    top: 10,
                    title:title,
                    onClose:function(){
                    }
                });
                if ($('#htmlDiv a.easyui-linkbutton').length > 0 )  {
                    $('#htmlDiv a.easyui-linkbutton').linkbutton({});
                }
                if ($('#htmlDiv .easyui-validatebox').length > 0 )  {
                    $('#htmlDiv .easyui-validatebox').validatebox({});
                }
                if ($('#htmlDiv .easyui-numberbox').length > 0 )  {
                    $('#htmlDiv .easyui-numberbox').numberbox({});
                }
                if ( $('#htmlDiv .easyui-combobox').length > 0 )  {
                    $('#htmlDiv .easyui-combobox').combobox({});
                }
                if ($('#htmlDiv .easyui-tabs').length > 0 )  {
                    $('#htmlDiv .easyui-tabs').tabs({});
                }
                if ($('#htmlDiv .easyui-tree').length > 0 )  {
                    $('#htmlDiv .easyui-tree').tree({});
                }
                if ($('#htmlDiv .easyui-window').length > 0 )  {
                    $('#htmlDiv .easyui-window').window({});
                }
                if ($('#htmlDiv .easyui-dialog').length > 0 )  {
                    $('#htmlDiv .easyui-dialog').dialog({});
                }
                ajaxLoadEnd();
                $('#html-dialog').dialog('open');
        }


        function ajaxLoading(){
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(document).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\" style='z-index: 1000;position: fixed'></div>").html($("#tips").val()).appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
        }
        function ajaxLoadEnd(){
            $(".datagrid-mask").remove();
            $(".datagrid-mask-msg").remove();
        }


        function infoFormOk(frameId){
            var url=$('#infoForm').attr("action");
            $('#infoForm').form('submit',{
                url:url,
                onSubmit: function(){
                    var isValid = $(this).form('validate');
                    if (!isValid){
                        //$.messager.progress('close');	// hide progress bar while the form is invalid
                        $('#htmlDiv .errors').html("")  ;
                        $('#htmlDiv .easyui-linkbutton').show();
                    }
                    return isValid;	// return false will stop the form submission
                },
                success:function(data){
                    var result=$.trim(data).split(':');
                    if(result[0]=='true'){
                        $.messager.alert('成功','操作成功','success');
                        $('#html-dialog').dialog({
                            onBeforeClose:function(){
                                return true;
                            }
                        });
                        $('#html-dialog').window("close");
                        $('#html-dialog').dialog({
                            onBeforeClose:function(){
                                return window.confirm('信息未保存，确认关闭吗?');
                            }
                        });
                        var e=document.getElementById(frameId+"-Frame").contentWindow;
                       e.location.reload(true)
                    }
                    else{
                        $('#htmlDiv .errors').html(result[1])  ;
                        $('#htmlDiv').scrollTop(0)   ;
                        $('#htmlDiv .easyui-linkbutton').show();
                    }
                }
            });
        }
    </script>
</head>
<body>
<div id="cc" class="easyui-layout" data-options="fit:true" style="width:100%;height:100%;">
    <div data-options="region:'north'" style="height:50px">
        <shiro:hasRole name="ROLE_MANAGER">manager</shiro:hasRole>
        <shiro:hasRole name="ROLE_USER">user</shiro:hasRole>
        <a onclick="mainAddTab('查看用户列表','<%=request.getContextPath()%>/user/list.ht');" href="#">查看用户列表</a>
        <a href="<%=request.getContextPath()%>/login/logout.ht">退出</a>
    </div>
    <div data-options="region:'south',split:true" style="height:50px;"></div>
    <div data-options="region:'west',split:true" title="" style="width:100px;">

        <a onclick="mainAddTab('查看用户列表','<%=request.getContextPath()%>/user/list.ht');" href="#">查看用户列表</a><br/>
        <a onclick="mainAddTab('权限管理','<%=request.getContextPath()%>/role/permission.ht');" href="#">权限管理</a>
    </div>
    <div data-options="region:'center',title:''">
        <div class="easyui-tabs" id="layoutMainTabs" fit="true" border="false" >
            <%--<div title="欢迎您使用数据库管理系统" icon="icon-reload" style="overflow:hidden;padding:5px;">

            </div>--%>
        </div>
    </div>


    <div id="html-dialog" class="easyui-dialog" draggable="false" style="width:800px;height:auto;" maximized="false"
         maximizable="false" modal="true" closed="true">
        <div id="htmlDiv" align="center" style="padding:0 0;overflow-y:scroll;height:auto;"></div>

    </div>
</div>

</body>
</html>
