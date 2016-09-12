<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>采购目录更新</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

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
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_08.png"/></div>
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
		   <li><a href="#"> 首页</a><><li><a href="#">采购目录管理</a><><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	<form action="<%=basePath%>category/edit.do" method="post">
		<input type="hidden" value="${id}" name="ancestry"/>
	       <div >目录名称：<input type="text" name="name" value="${category.name }"/></div>
	       <div>父节点<input type="text" name="ancestry" value="${category.ancestry }"/></div>
	       <div>排序：<input type="text" name="orderNum" value="${category.orderNum }"/></div>
	       <div>前台展示优先级:<input type="text" name="params" value="${category.code }"/></div>
	       <div id="uploadAttach" class="clear ml160">
	 		 <div class="f14">上传附件</div>
	  		 <input id="pic" type="file" class="toinline" name="attaattach"/>
	   		 <input class="toinline" type="button" value="添加" onclick="addAttach()"/><br/>
	 		</div>
	       
	       
	       <div >描述：<input type="text" name="description" value="${category.description }"/></div>
	       <div class=""><span>是否末级</span>
	       		 <select name="isEnd">
	  	  		<option value="0">是</option>
	  	  		<option value="1">否</option> 
	  		</select>
	       </div>
	      <div class="item ml125 mt20">
			<input class="btn btn-window " name="commit" value="更新" type="submit"> 
			<input class="btn btn-window " onclick="location.href='javascript:history.go(-1);'" value="返回" type="button"/>
		</div>
		</form>
		<!--底部代码开始-->
			<div class="footer-v2 clear" id="footer-v2">
      			<div class="footer">
           		 <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
<!--/footer--> 
    </div>
  </body>
</html>
