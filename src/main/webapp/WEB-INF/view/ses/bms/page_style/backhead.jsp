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
	<jsp:include page="backend_common.jsp"></jsp:include>	
</head>
  
<body>
<div class="wrapper">
	<div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30 mt10">
              <a href="">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/backend/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
			    <li class="jczc">
				 <a href"#">
				  <div class="top_icon jczc_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_01.png"/>
				  --%></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li class="ywjc">
				<a href="javascript:void(0);">
				  <div class="top_icon ywjc_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_02.png"/>
				  --%></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li class="bzzy">
				<a href="javascript:void(0);">
				  <div class="top_icon bzzy_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_03.png"/>
				  
				  --%></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li class="xxfw">
				<a href="javascript:void(0);">
				  <div class="top_icon xxfw_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_04.png"/>--%></div>
				  <span>信息服务</span>
				 </a>
				</li>
			   <li class="dropdown zchj">
			     	<a aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="">
				  		<div class="top_icon zchj_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_05.png"/>--%></div>
				  		<span>支撑环境</span>
				 	</a>
					<ul class="dropdown-menu">
                   		<li class="line-block">
                   			<a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>后台管理</a>
                   			<ul class="dropdown-menuson dropdown-menu">
                   				<li><a href="#" target="_blank" class="son-menu son-three"><span class="mr5">◇</span>用户管理</a></li>
                   			</ul>
                   		</li>
               		</ul>
				</li>
			    <li class="pzpz">
				<a href="javascript:void(0);">
				  <div class="top_icon pzpz_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_06.png"/>--%></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li class="htsy">
				<a href="javascript:void(0);">
				  <div class="top_icon htsy_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_07.png"/>--%></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li class="aqtc">
				<a href="javascript:void(0);">
				  <div class="top_icon aqtc_icon"><%--<img src="<%=basePath%>public/ZHH/images/top_08.png"/>--%></div>
				  <span>安全退出</span>
				 </a>
				</li>
				
			  </ul>
			</div>
    </div>
	</div>
	</div>
   </div>
</div>
		 <div class="mt10">
	   <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
</body>
</html>
