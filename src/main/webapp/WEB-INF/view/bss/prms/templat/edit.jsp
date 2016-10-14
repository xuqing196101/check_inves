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
          title: '新增模板',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '250px'],
          shadeClose: true,
          content:$('#package') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
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
	var id2=[]; 
	$('input[name="isOpen"]:checked').each(function(){ 
		id2.push($(this).val());
	}); 
	if(id2.length==0){
		layer.tips("请选择一个", "#isOpen");
		return ;
	}
	var id3=[]; 
	$('input[name="isUse"]:checked').each(function(){ 
		id3.push($(this).val());
	}); 
	if(id3.length==0){
		layer.tips("请选择一个", "#isUse");
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
	   		<h2>修改模板</h2>
	   </div>
   </div>
   <!-- 新增窗口 -->
<div class="container clear margin-top-30" id="package" >
	<form action="<%=basePath %>auditTemplat/edit.html" method="post" id="form1">
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>初审项模板名称:</th><td><input type="text" id="name" maxlength="30" name="name" value="${templat.name }"></td>
        <th>初审项模板类型:</th><td><input type="radio" name="kind" value="商务" <c:if test="${fn:contains(templat.kind,'商务')}">checked="true"</c:if> >商务&nbsp;<input type="radio" <c:if test="${fn:contains(templat.kind,'技术')}">checked="true"</c:if> name="kind" id="kind" value="技术" >技术</td>
        <th>创建人:</th><td><input name="creater" id="creater" maxlength="10" type="text" value="${sessionScope.loginUser.relName}" ></td>
        <th>是否公开:</th><td><input name="isOpen" maxlength="10" type="radio" value="0" <c:if test="${templat.isOpen eq '0' }">checked="true"</c:if> >公开&nbsp;<input name="isOpen" id="isOpen" type="radio" value="1" <c:if test="${templat.isOpen eq '1' }">checked="true"</c:if> >私有</td>
        <th>是否可用:</th><td><input name="isUse"  maxlength="10" type="radio" <c:if test="${templat.isUse eq '0' }">checked="true"</c:if>  value="0" >可用&nbsp;<input name="isUse" id="isUse"  type="radio" value="1" <c:if test="${templat.isUse eq '1' }">checked="true"</c:if>>不可用</td>
      </tr>
      <input type="hidden" name="userId" value="${sessionScope.loginUser.id }">
      <input type="hidden" name="id" value="${templat.id }">
     </thead>
    </table>
    <input type="button"  value="修改" onclick="submit1();" class="btn btn-windows edit"/>
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </form>
</div>
</body>
</html>