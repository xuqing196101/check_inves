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
	function openWindow(){
		 layer.open({
	          type: 1, //page层
	          area: ['700px', '300px'],
	          title: '手动添加初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['220px', '250px'],
	          shadeClose: true,
	          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
	function remove(id){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"<%=basePath%>expert/remove.html",
	 				data:{"id":id},
	 				type:"post",
	 	       		success:function(){
	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
	 		       		}, 1000);
	 	       		},
	 	       		error: function(){
	 					layer.msg("删除失败",{offset: ['222px', '390px']});
	 				}
	 	       	});
	 		});
		
	}
	function edit(id){
		 layer.open({
	          type: 2, //page层
	          area: ['700px', '300px'],
	          title: '修改初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['220px', '250px'],
	          shadeClose: true,
	          content:'<%=basePath %>firstAudit/toEdit.html?id='+id
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
</script>
</head>
<body>
<h1>01、项目分包信息</h1>
<table class="table table-bordered table-condensed">
  <tr>
    <th>第一包</th>
  </tr>
  <tr>
    <th>序号</th>
    <th>采购品目</th>
    <th>名称</th>
    <th>单位</th>
    <th>数量</th>
  </tr>
</table>
<table class="table table-bordered table-condensed">
  <tr>
    <th>第二包</th>
  </tr>
  <tr>
    <th>序号</th>
    <th>采购品目</th>
    <th>名称</th>
    <th>单位</th>
    <th>数量</th>
  </tr>
</table>
<h1>02、初审项定义</h1>
<div>
  <form action="">
  <input type="button" value="关联初审项模板"/>
  <input type="button" value="手动添加初审项" onclick="openWindow();"/>
    <table class="table table-bordered table-condensed">
      <tr>
        <th>初审项名称</th>
        <th>要求类型</th>
        <th>操作</th>
      </tr>
      <c:forEach items="${list }" var="l" varStatus="vs">
       <tr>
        <td align="center">${l.name }</td>
        <td align="center">${l.kind }</td>
        <td align="center" width="200px;">
          <input type="button" value="修改" class="btn btn-windows edit" onclick="edit('${l.id}');">
          <input type="button" value="删除" class="btn btn-windows delete" onclick="remove'${l.id}');">
        </td>
      </tr>
      </c:forEach>
    </table>
  </form>
</div>
<div id="openWindow" style="display: none;">
	<form action="<%=basePath %>firstAudit/add.html" method="post" id="form1">
     <table class="table table-bordered table-condensed">
      <tr>
        <th>初审项名称:</th><td><input type="text" name="name" ></td>
        <th>要求类型:</th><td><input type="checkbox" name="kind" value="商务" >商务&nbsp;<input type="checkbox" name="kind" value="技术" >技术</td>
        <th>创建人:</th><td><input name="creater" type="text" ></td>
      </tr>
      <tr>
      <input type="hidden" name="projectId" value="">
      <input type="submit"  value="添加"  class="btn btn-windows add"/>
      </tr>
    </table>
  </form>
</div>
</body>
</html>