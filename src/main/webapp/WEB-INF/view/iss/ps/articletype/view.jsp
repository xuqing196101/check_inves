<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看栏目类型</title>
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
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">栏目管理</a></li><li class="active"><a href="#">栏目查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container container_box">
    <div>
	   <h2 class="list_title">查看栏目</h2>
	   <ul class="ul_list">
	   		   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">栏目名称：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 p0">
		        <input class="input_group"  type="text" value = '${articletype.name}' readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
               <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">栏目编码：</span>
               <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="input_group"  type="text" value = '${articletype.code}' readonly="readonly">
               </div>
             </li>

			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">创建人：</span>				 	
	  			<div class="input-append input_group col-sm-12 col-xs-12 p0">
		        <input class="input_group"  type="text" value = '${articletype.creater.relName}' readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">创建时间：</span>				 	
	  			<div class="input-append input_group col-sm-12 col-xs-12 p0">
		         <input class="input_group"  type="text" value = "<fmt:formatDate value='${articletype.createdAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />" readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">更新时间：</span>				 	
               <div class="input-append input_group col-sm-12 col-xs-12 p0">
		         <input class="input_group"  type="text" value = "<fmt:formatDate value='${articletype.updatedAt}' pattern="yyyy年MM月dd日  HH:mm:ss" />" readonly="readonly">
		       </div>
			 </li>
			<li class="col-md-12 col-sm-12 col-xs-12 ">	  	 			
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">栏目介绍：</span>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
				<textarea  class="h130 col-md-12 col-sm-12 col-xs-12" readonly="readonly">${articletype.describe}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 col-sm-12 col-xs-12 tc mt20">
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
     </div>
     </div>
  </body>
</html>
