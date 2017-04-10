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
$(function(){
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
	if(chkObjs == 0 || chkObjs == null){
		document.getElementById("idcad").style.display="none";
	}
}

function show(){
	document.getElementById("idcad").style.display="";
}
function hid(){
	document.getElementById("idcad").style.display="none";
}
</script>
</head>
<body onload="open();">
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">网上投诉</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 投诉页面 -->
	<div class="container container_box">
			<form action="${pageContext.request.contextPath }/onlineComplaints/add.html" method="post" class="mb0">
			  <h2 class="list_title">网上投诉处理系统</h2>
			  <input name = "id" type = "text" value="${complaint.id }" style="display: none;">
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12  pl15" >
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉人类型</span>
						<div class="col-md-12 mb20 col-sm-12 col-xs-12 p0">
							<div class="col-md-5 col-xs-5 col-xs-6 p0">
								<input type="radio" <c:if test="${0==complaint.type}">checked="checked"</c:if> name="type" id="PerSonTsype" value="0" onchange="hid()" class="mr5"/>单位
						    </div>
						    <div class="col-md-5 col-xs-5 col-xs-6 ">
								<input type="radio" <c:if test="${1==complaint.type}">checked="checked"</c:if> name="type" id="PerSonTsype" value="1" onchange="show()" class="mr5"/>个人
						    </div>
						    <div class="star_red">${error_type }</div>
						</div> 
				  </li>
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
	                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉人名称（姓名）</span>
	                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                             <input class="" name="name" type="text" value="${complaint.name }">
                        <div class="star_red">${error_name }</div>
                        </div>
	              </li>	
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15" >
				  	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>投诉对象</span>
				  	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                             <input class="" name="complaintObject" type="text" value="${complaint.complaintObject }">
                             <div class="star_red">${error_complaintObject }</div>
                     </div>
				  </li>
				  <li class="col-md-12 col-sm-12 col-xs-12">
	                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">投诉事项</span>
	                 <div class="col-md-12 col-sm-12 col-xs-12 p0">
                      <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="不超过1000个字" name="complaintMatter">${complaint.complaintMatter }</textarea>
                     </div>
                     <div class="star_red">${error_complaintMatter }</div>
	              </li> 
				  <li class="col-md-3 col-sm-6 col-xs-12">
	 	              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>投诉文件：</span>
				        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
				        <u:upload id="post_attach_up" businessId="${complaint.id }" sysKey="2" typeId="47" multiple="true" auto="true" />
						<u:show showId="post_attach_show" businessId="${complaint.id }" sysKey="2" typeId="47"/>
				     	<div class="cue" id = "">${error_zs1 }</div>
				     	</div>
	             </li>
	             <li class="col-md-3 col-sm-6 col-xs-12"  id = "idcad">
	 	              	<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>身份证照片：</span>
				       	<div class="input-append input_group col-sm-12 col-xs-12 p0 ">
				        <u:upload id="post_attach_ups" businessId="${complaint.id }" sysKey="2" typeId="48" multiple="true" auto="true" />
						<u:show showId="post_attach_shows" businessId="${complaint.id }" sysKey="2" typeId="48"/>
				     	<div class="cue" id = "">${error_zs2 }</div>
				     	</div>
	             </li>
			</ul>  
			<div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
				<button type="submit" class="btn">投诉</button>
				<button class="btn btn-windows back" type="button"
						onclick="window.location.href = '${pageContext.request.contextPath}/onlineComplaints/complaints.html'">返回</button>
			</div>
			</form>
		</div>
</body>
</html>