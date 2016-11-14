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
	<jsp:include page="backend_common.jsp"></jsp:include>	
  </head>
  
  <body>
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
