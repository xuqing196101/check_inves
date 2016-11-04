<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
  <div class="wrapper">
    <div class="container">
  <table class="table table-bordered">
			    <tbody>
				  <tr>
				    <td width="25%" class="info">单位名称：</td>
				    <td width="25%">xxxx有限公司</td>
				    <td width="25%" class="info">单位简称：</td>
				    <td width="25%">服务公司</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">曾用名：</td>
				    <td width="25%">xxxx有限公司</td>
				    <td width="25%" class="info">单位类型：</td>
				    <td width="25%">独立核算单位</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">邮政编码：</td>
				    <td width="25%">100044</td>
					<td width="25%" class="info">所在地区：</td>
				    <td width="25%" >北京</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">详细地址：</td>
				    <td width="25%">北京市西四环中路16号院8号楼</td>
				    <td width="25%" class="info">电话（总机）：</td>
				    <td width="25%">88016942</td>
				  </tr>
				  <tr>
				    <td width="25%" class="info">传真：</td>
				    <td width="25%">-</td>
				    <td width="25%" class="info"></td>
				    <td width="25%" class="info"></td>
				  </tr>
				 </tbody>
			 </table>
	</div>
  </div>

</body>
</html>
