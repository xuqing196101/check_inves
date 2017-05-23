<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="backend_common.jsp"></jsp:include>	
  </head>
  
  <body>
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">投标左侧页面</a></li> 
		</ul>
	  </div>
   </div>
            <div class="row">
            <div class="ml20 mt10">
	    		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
            </div>
                <!-- Begin Content -->
                     <div class="col-md-3 col-sm-4 col-xs-12 md-margin-bottom-40 mt10">
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
