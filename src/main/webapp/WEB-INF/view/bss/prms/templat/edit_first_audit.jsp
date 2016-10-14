<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初审项定义</title>
<script type="text/javascript">

	function cancel(){
	    var index=parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	    
	}
	function submit1(){
		var name = $("#name").val();
		if(!name){
			layer.tips("请填写名称", "#name");
			return ;
		}
		var id=[]; 
		$('input[name="kind"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==0){
			layer.tips("请选择类型", "#kind");
			return ;
		}
		
		var creater = $("#creater").val();
		if(!creater){
			layer.tips("请填写名称", "#creater");
			return ;
		}
		var index=parent.layer.getFrameIndex(window.name);
		$.ajax({
			url:"<%=basePath %>auditTemplat/editFirstAudit.html",
			data:$("#form1").serialize(),
			type:"post",
			success:function(){
				parent.location.reload();
			},
			error:function(){
				layer.msg("更新失败",{offset: ['222px', '390px']});
			}
		});
		
	}
</script>
</head>
<body>
<div>
	<form action="<%=basePath %>firstAudit/edit.html" method="post" id="form1">
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>初审项名称:</th><td><input type="text" name="name" id="name" value="${temitem.name }"></td>
        <th>要求类型:</th><td><input type="radio" name="kind" <c:if test="${fn:contains(temitem.kind,'商务')}">checked="true"</c:if> value="商务" >商务&nbsp;<input type="radio" id="kind" name="kind"<c:if test="${fn:contains(temitem.kind,'技术')}">checked="true"</c:if> value="技术" >技术</td>
        <th>创建人:</th><td><input name="creater" id="creater" type="text" value="${temitem.creater }"></td>
      </tr>
      <tr>
      <input type="hidden" name="templatId" value="${temitem.templatId }">
      <input type="hidden" name="id" value="${temitem.id }">
      <input type="hidden" name="createdAt" value="<fmt:formatDate value='${temitem.createdAt}' pattern="yyyy-MM-dd" />">
      </tr>
     <thead>
    </table>
      <input type="button"  value="修改" onclick="submit1();"  class="btn btn-windows edit"/>
      <input type="button"  value="取消"  class="btn btn-windows cancel" onclick="cancel();"/>
  </form>
</div>
</body>
</html>