<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

<title>..</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
function start(id){
            var attach = $("input[name='attach']").val();
            if(!attach){
                layer.alert("请上传凭证",{offset: ['50px','90px'], shade:0.01});
            }else{
             layer.confirm('您确认要启动项目吗?',{
                offset: ['50px','90px'],
                shade:0.01,
                btn:['是','否'],
                },function(){
                    $("#att").submit();
                },function(){
                    var index=parent.layer.getFrameIndex(window.name);
                     parent.layer.close(index);
                }
                    
            ); 
            }
               
}
function cancel(){
     var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
     
}
</script>  
</head>

<body>
    <form id="att" action="<%=basePath%>project/start.html" id="myForm"
        method="post" name="form1" class="simple" target="_parent" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${project.id}"/>
        <span class="f14 fl">上传项目批文：</span>
        <div class="fl" id="uploadAttach" >
          <input id="pic" type="file" class="toinline" name="attach"/>
        </div>
        <br/><br/><br/>
        <span class="f14 fl">项目负责人：</span>
        <div class="fl" id="name" >
          <select name="principal" style="width:100px" id="select">
       <option selected="selected" value="">-请选择-</option>
            <c:forEach items="${purchaseInfo}" var="info">
                            <option value="${info.relName}">${info.relName}</option>
            </c:forEach> 
         </select> 
        </div>
        <br/><br/><br/>
     <a class="btn btn-windows save" onclick="start('${project.id}');">确认</a>
         <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
    </form>
</body>
</html>
