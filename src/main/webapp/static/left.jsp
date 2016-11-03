<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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

    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <!--导航js-->
    <script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
  </head>
  
  <body>
    <div class="wrapper">
 
        <!--=== Content Part ===-->
        <div class="container content height-350">
            <div class="row">
                <!-- Begin Content -->
                     <div class="col-md-3 md-margin-bottom-40">
	                   <div class="tag-box tag-box-v3">
					   <div class="light_main">
					    <div class="light_list">
						 投标函      <input type="button" class="btn fr" value="绑定指标"/>
						</div>
					    <ul class="light_box"> 
						  <li>
						    <span class="light_desc">法人代表...</span>
							<div class="shanchu light_icon"><a href="">删除</a></div>
							<div class="dinwei light_icon"><a href="">定位</a></div>
						  </li>
						</ul>
	                  </div>
					   <div class="light_main">
					    <div class="light_list">
						 企业法人营业      <input type="button" class="btn fr" value="绑定指标"/>
						</div>
					    <ul class="light_box"> 
						  <li>
						    <span class="light_desc">法人代表...</span>
							<div class="shanchu light_icon"><a href="">删除</a></div>
							<div class="dinwei light_icon"><a href="">定位</a></div>
						  </li>
						</ul>
	                  </div>
            </div>   
		  </div>
		</div>
  </body>
</html>
