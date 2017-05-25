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
<title>新增服务热线</title>

</head>
<body onload="open();">
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)">首页</a></li>
				<li><a href="javascript:void(0)">信息服务</a></li>
				<li><a href="javascript:void(0)">热线电话</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 新增服务热线 -->
	<div class="container container_box">
			<form action="${pageContext.request.contextPath }/serviceHotline/add.html" method="post" class="mb0">
			  <h2 class="list_title">添加服务热线</h2>
				<ul class="ul_list">
				  <!-- 服务内容 -->
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
          	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>服务内容</span>
          	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
            	<input class="" name="servicecontent" type="text" value="${serviceHotline.servicecontent }">
          		<div class="star_red">${error_content }</div>
          	</div>
	        </li>
	        <!-- 联系电话 -->
				  <li class="col-md-3 col-sm-6 col-xs-12 pl15"   >
	        	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>联系电话</span>
	        	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
            	<input class="" name="contactphonenumber" type="text" value="${serviceHotline.contactphonenumber }">
              <div class="star_red">${error_phone }</div>
           	</div>
	        </li>	
				</ul>
			<div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
				<button type="submit" class="btn">提交</button>
				<button class="btn btn-windows back" type="button"
						onclick="window.location.href = '${pageContext.request.contextPath}/serviceHotline/list.html'">返回</button>
			</div>
			</form>
		</div>
</body>
</html>