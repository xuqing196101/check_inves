<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'bid_file.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
  </head>
  
  <body>
	                     <div class="col-md-12 p0">
						   <ul class="flow_step">
						     <li class="active">
							   <a aria-expanded="true" href="#tab-1" data-toggle="tab">符合性</a>
							   <i></i>
							 </li>
							 
							 <li>
							   <a aria-expanded="false" href="#tab-2" data-toggle="tab">符合性关联</a>
							   <i></i>							  
							 </li>
						     <li>
							   <a aria-expanded="false" href="#tab-3" data-toggle="tab"> 评标细则</a>
							   <i></i>
							 </li>
							 <li>
							   <a aria-expanded="false" href="#tab-4" data-toggle="tab"> 招标文件</a>
							 </li>
						   </ul>
						 </div>
						 <div class="tab-content clear step_cont">
						 <div class="col-md-12 tab-pane active"  id="tab-1">
						  <h2 class="f16 count_flow fl"><i>01</i>符合性条款</h2>
						  <div class="fr pr15 mt10">
						    <button class="btn btn-windows delete" type="button">删除</button>
							<button class="btn btn-windows edit"  type="button">修改</button>
							<button class="btn btn-windows add" type="button">新增</button>
							
						  </div>
						  <div class="col-md-12">
						    <table class="table table-striped table-bordered table-hover ">
							  <thead>
							    <tr>
								  <th class="w30"><input type="checkbox"/></th>
								  <th>符合性名称</th>
								</tr>
							 </thead>
							 <tbody>
							    <tr>
								  <td class="w30"><input type="checkbox"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							    <tr>
								  <td class="w30"><input type="checkbox"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							  </tbody>
							</table>
						  
						  </div>
						  <h2 class="f16 count_flow fl clear"><i>02</i>资格性条款</h2>
						  <div class="fr pr15 mt10">
						    <button class="btn btn-windows add" type="button">新增</button>
							<button class="btn btn-windows edit" type="button">修改</button>
							<button class="btn btn-windows delete" type="button">删除</button>
						  </div>
						  <div class="col-md-12">
						    <table class="table table-striped table-bordered table-hover ">
							  <thead>
							    <tr>
								  <th class="w30"><input type="checkbox"/></th>
								  <th>符合性名称</th>
								</tr>
							 </thead>
							 <tbody>
							    <tr>
								  <td class="w30"><input type="checkbox"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							    <tr>
								  <td class="w30"><input type="checkbox"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							  </tbody>
							</table>
						  </div>
						 </div>
						 <div class="col-md-12 tab-pane" id="tab-2">222</div>				 
						 <div class="col-md-12 tab-pane" id="tab-3">333</div>	
						 <div class="col-md-12 tab-pane" id="tab-4">444</div>
                      </div>
  </body>
</html>
