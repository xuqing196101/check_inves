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
<script type="text/javascript">
var index;
function cancel(){
   layer.close(index);
}
function openWindow(){
	index = layer.open({
          type: 1, //page层
          area: ['700px', '300px'],
          title: '新增初审项',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '250px'],
          shadeClose: true,
          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
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
        closeBtn: 1,
        content:'<%=basePath %>auditTemplat/toEditFirstAudit.html?id='+id
      		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
	 });
}
function remove(id){
	layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
 			layer.close(index);
 			$.ajax({
 				url:"<%=basePath%>auditTemplat/deleteFirstAudit.html?ids="+id,
 				//data:{"id":id},
 				//type:"post",
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
	$("#form1").submit();
}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	   <div class="headline-v2">
	   		<h2>模板信息</h2>
	   </div>
   </div>
   <!-- 新增窗口 -->
<div class="container clear margin-top-30" id="package" >
	<form action="" method="post" >
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>初审项模板名称:</th><td>${templat.name }</td>
        <th>初审项模板类型:</th><td>${templat.kind}</td>
        <th>创建人:</th><td>${templat.creater}</td>
      </tr>
     </thead>
    </table>
  </form>
</div>
<div class="container">
	   <div class="headline-v2">
	   		<h2>初审项信息</h2>
	   </div>
   </div>
   <div class="container clear margin-top-30" id="package">
  <form action="">
  <input type="button" value="添加初审项" onclick="openWindow();" class="btn btn-windows ht_add"/>
    <table class="table table-bordered table-condensed">
    <thead>
      <tr>
        <th class="info">初审项名称</th>
        <th class="info">初审项类型</th>
        <th class="info">创建人</th>
        <th class="info">操作</th>
      </tr>
     </thead>
      <c:forEach items="${list }" var="l" varStatus="vs">
      <thead>
       <tr>
        <td align="center">${l.name }</td>
        <td align="center">${l.kind }</td>
        <td align="center">${l.creater }</td>
        <td align="center" width="200px;">
          <input type="button" value="修改" class="btn btn-windows edit" onclick="edit('${l.id}');">
          <input type="button" value="删除" class="btn btn-windows delete" onclick="remove('${l.id}');">
        </td>
      </tr>
      </thead>
      </c:forEach>
    </table>
  </form>
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
</div>

<div id="openWindow"  style="display: none;">
	<form action="<%=basePath %>auditTemplat/saveFirstAudit.html" method="post" id="form1">
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>初审项名称:</th><td><input type="text" id="name" maxlength="30" name="name" ></td>
        <th>要求类型:</th><td><input type="radio" name="kind" value="商务" >商务&nbsp;<input type="radio" name="kind" id="kind" value="技术" >技术</td>
        <th>创建人:</th><td><input name="creater" id="creater" maxlength="10" type="text" value="${sessionScope.loginUser.relName}"></td>
      </tr>
      <input type="hidden" name="templatId" value="${templat.id }">
     </thead>
    </table>
    <input type="button"  value="添加" onclick="submit1();"   class="btn btn-windows add"/>
    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
  </form>
</div>
</body>
</html>