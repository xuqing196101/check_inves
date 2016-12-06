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
		   <li><a href="#"> 首页</a></li><li><a href="#">栏目管理</a></li><li class="active"><a href="#">栏目详情</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
     <div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">版块详情</a></li>
            </ul>
	        <div class="tab-content padding-top-20 over_hideen">
	        <div class="tab-pane fade active in" id="tab-1">
	        <h2 class="count_flow jbxx">基本信息</h2>
	        <table class="table table-bordered">
	        <tbody>
	        <tr>
	            <td class="bggrey" >栏目名称：</td>
	            <td colspan="3">${articletype.name}</td>
	        </tr>
	        <tr>
	            <td class="bggrey" >栏目编码：</td>
	            <td>${articletype.code}</td>
	            <td class="bggrey" >创建人：</td>
	            <td>${articletype.creater.relName}</td>
	        </tr>
	        <tr>
	            <td class="bggrey" >创建时间：</td>
	            <td>${articletype.createdAt}</td>
	            <td class="bggrey" >更新时间：</td>
	            <td>${articletype.updatedAt}</td>
	        </tr>
	        </tbody>
	        </table>
            <h2 class="count_flow jbxx">栏目介绍</h2>	 			
			    <div class="col-md-12 col-sm-12 col-cs-12 p0">
				<textarea  class="h130 col-md-12 col-sm-12 col-xs-12" readonly="readonly">${articletype.describe}</textarea>		
				</div>			
	<!-- 底部按钮 -->			          
  <div  class="col-md-12 col-sm-12 col-xs-12 tc mt10">
    <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  </div>
     </div>
     </div>
  </body>
</html>
