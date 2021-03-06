<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>投诉页面</title>
<script type="text/javascript">
/* $(function(){
	$("#extensionId").val("bmp,pmg,jpg,gif,png");
	$("#idcad #extensionId").val("bmp,pmg,jpg,gif,png");
});
function open(){
	var chkObjs=null;
	var obj=document.getElementsByName("type");
	for (var i=0;i<obj.length;i++){ //遍历Radio 
		if(obj[i].checked){ 
		chkObjs=obj[i].value; 
		} 
	}
	if(chkObjs == 0){
		document.getElementById("idcad").style.display="none";
	}
}

function show(){
	document.getElementById("idcad").style.display="";
}
function hid(){
	document.getElementById("idcad").style.display="none";
} */
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
                <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
                <li><a href="javascript:void(0)">业务监管</a></li>
                <li><a href="javascript:void(0)">网上投诉处理</a></li>
                <li><a href="javascript:jumppage('${pageContext.request.contextPath }/onlineComplaints/complaints.html')">投诉记录查询</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 投诉页面 -->
	<div class="container container_box">
			<form action="" method="post" class="mb0">
			  <h2 class="list_title">网上投诉处理系统</h2>
			  <input name = "id" type = "text" value="${complaint.id }" style="display: none;">
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
	          	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>标题</span>
	          	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	            	<input class="" name="title" type="text" value="${complaint.title }" disabled="disabled">
	          	</div>
					</li>
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
	        	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉人名称（姓名）</span>
	        	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
            	<input class="" name="name" type="text" value="${complaint.name }" disabled="disabled">
           	</div>
	        </li>
				  <!-- 投诉人联系电话 -->
					<li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉人电话</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="" name="telephone" type="text" value="${complaint.telephone }" disabled="disabled">
						</div>
					</li>
	        <li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉人联系地址</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="" name="adress" type="text" value="${complaint.adress }" disabled="disabled">
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉人邮箱</span>
						<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
							<input class="" name="email" type="text" value="${complaint.email }" disabled="disabled">
						</div>
					</li>
				  <li class="col-md-12 col-sm-12 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉内容</span>
						<div class="col-md-12 col-sm-12 col-xs-12 p0">
							<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="不超过1000个字" name="complaintContent" disabled="disabled">${complaint.complaintContent }</textarea>
						</div>
					</li> 
				  <li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>投诉文件：</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0 ">
							<%-- <u:upload id="post_attach_up" businessId="${complaint.id }" sysKey="2" typeId="47" multiple="true" auto="true" /> --%>
							<u:show showId="post_attach_show" businessId="${complaint.id }" sysKey="2" typeId="47"/>
						</div>
					</li>
		</ul>  
		<div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
			<a class="btn btn-windows back"  href="${pageContext.request.contextPath }/onlineComplaints/complaints.html">返回</a>
		</div>
		</form>
	</div>
</body>
</html>