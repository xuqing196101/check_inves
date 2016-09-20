<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>待办</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div class="header-v4 header-v5">
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="${pageContext.request.contextPath}/public/ZHH/">
                 <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0 ">
              <ul class="top-v1-data padiing-0 ">
			          <li>
				          <a href="${pageContext.request.contextPath}/public/ZHH/#">
				            <div>
				              <img src="${pageContext.request.contextPath}/public/ZHH/images/top_01.png"/>  
				            </div>
				              <span>决策支持</span>
				          </a>
				        </li>
			          <li class="dropdown">
				          <a href="${pageContext.request.contextPath}/public/ZHH/#">
				       <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				<ul class="dropdown-menu">
                 <li><a href="${pageContext.request.contextPath}/public/ZHH/#" target="_blank">轿车</a></li>
                 <li><a href="${pageContext.request.contextPath}/public/ZHH/#" target="_blank">越野车</a></li>
                 <li><a href="${pageContext.request.contextPath}/public/ZHH/#" target="_blank">面包车</a></li>
                 <li><a href="${pageContext.request.contextPath}/public/ZHH/#" target="_blank">客车</a></li>
                </ul>
				</li>
			    <li>
				<a href="${pageContext.request.contextPath}/public/ZHH/#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="${pageContext.request.contextPath}/public/ZHH/#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="${pageContext.request.contextPath}/public/ZHH/#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="${pageContext.request.contextPath}/public/ZHH/#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				 <a href="${pageContext.request.contextPath}/public/ZHH/#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="${pageContext.request.contextPath}/public/ZHH/#">
				  <div><img src="${pageContext.request.contextPath}/public/ZHH/images/top_08.png"/></div>
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
<!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
		  <ul class="breadcrumb margin-left-0">
		    <li><a href="#">供应商审核</a></li><li><a href="#">我的待办</a></li>
		  </ul>
	  </div>
  </div>
   
  <div class="container mt20">
    <div class="col-md-4">
      <span onclick="location='${pageContext.request.contextPath}/supplierFsInfo/auditList.html?status=0'">供应商未审核</span>（<a class="red b">${weishenhe }</a>）
    </div>
   <div class="col-md-4">
     <span onclick="location='${pageContext.request.contextPath}/supplierFsInfo/auditList.html?status=1'">供应商已审核</span>（<a class="red b">${yishenhe }</a>）</div>
   <div class="col-md-4">
     <span onclick="location='${pageContext.request.contextPath}/supplierFsInfo/auditList.html?status=2'">供应商审核中</span>（<a class="red b">${shenhezhong }</a>）</div>
  </div>  
</body>
</html>
