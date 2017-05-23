<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
	<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
function show(style){
  	window.location.href="${pageContext.request.contextPath}/pageStyle/"+style+".html";
}
</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${ pageContext.request.contextPath }/" target="_blank"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:jumppage('${ pageContext.request.contextPath }/pageStyle/list.html')">页面样式列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的页面开始-->
    <div class="container">
     <div class="headline-v2">
     	<h2>页面样式列表</h2>
     </div>
<!-- 表格开始-->  
  <div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">样式名称</th>
		</tr>
		</thead>
			<tr>
			    <td class="tc">1</td>
				<td class="tc"><a onclick="show('details')" class="pointer">上下结构表单页面</a></td>
			</tr>
			<tr>
			    <td class="tc">2</td>
				<td class="tc"><a onclick="show('order')" class="pointer">列表页面</a></td>
			</tr>
			<tr>
			    <td class="tc">3</td>
				<td class="tc"><a onclick="show('show_order')" class="pointer">详情页面</a></td>
			</tr>
			<tr>
			    <td class="tc">4</td>
				<td class="tc"><a onclick="show('table_special')" class="pointer">左右结构表格页面</a></td>
			</tr>
			<tr>
			    <td class="tc">5</td>
				<td class="tc"><a onclick="show('manage')" class="pointer">左右布局页面</a></td>
			</tr>
			<tr>
			    <td class="tc">6</td>
				<td class="tc"><a onclick="show('evaluation')" class="pointer">实施页面</a></td>
			</tr>
			<tr>
			    <td class="tc">7</td>
				<td class="tc"><a onclick="show('backbottom')" class="pointer">后台主页</a></td>
			</tr>
			<tr>
			    <td class="tc">8</td>
				<td class="tc"><a onclick="show('left')" class="pointer">投标左侧页面</a></td>
			</tr>
			<tr>
			    <td class="tc">9</td>
				<td class="tc"><a onclick="show('tab')" class="pointer">切换标签页面</a></td>
			</tr>
			<tr>
			    <td class="tc">10</td>
				<td class="tc"><a onclick="show('openLayer')" class="pointer">弹出框页面</a></td>
			</tr>
			<tr>
			    <td class="tc">11</td>
				<td class="tc"><a onclick="show('backhead')" class="pointer">后台头部</a></td>
			</tr>
        </table>
     </div>
   </div>
</body>
</html>
