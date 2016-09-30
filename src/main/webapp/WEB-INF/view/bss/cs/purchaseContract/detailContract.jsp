<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>合同基本信息修改页</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
    <script type="text/javascript">
    	function next(){
    		var ids = "${ids}";
    		window.location.href="<%=basePath%>purchaseContract/createTextContract.html?ids="+ids;
    	}
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购合同管理</a></li><li class="active"><a href="#">合同明细信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<%--<form action="<%=basePath %>pqinfo/save.html" method="post">
   		--%><div class="headline-v2">
   			<h2>明细信息</h2>
   		</div>
   		<div class="container clear">
   		<div class="p10_25">
      	<table class="table table-bordered table-condensed mt5">
	      <thead>
			<tr>
				<th class="info w50">序号</th>
				<th class="info">编号</th>
				<th class="info">物资名称</th>
				<th class="info">品牌商标</th>
				<th class="info">规格型号</th>
				<th class="info">计量单位</th>
				<th class="info">数量</th>
				<th class="info">单价(元)</th>
				<th class="info">合计金额(元)</th>
				<th class="info">交付时间</th>
				<th class="info">备注</th>
			</tr>
		</thead>
		<c:forEach items="${requList}" var="reque" varStatus="vs">
			<tr>
				<td class="tc">${(vs.index+1)}</td>
				<td class="tc">${reque.planNo}</td>
				<td class="tc">${reque.goodsName}</td>
				<td class="tc"></td>
				<td class="tc">${stand}</td>
				<td class="tc">${item}</td>
				<td class="tc">${purchaseCount}</td>
				<td class="tc">${price}</td>
				<td class="tc">${budget}</td>
				<td class="tc">${deliverDate}</td>
				<td class="tc">${memo}</td>
			</tr>
   		</c:forEach>
	</table>
     </div>
    </div>
  		<div  class="col-md-12 tc mt20">
   			<button class="btn btn-windows save" onclick="next()">下一步</button>
   			<button class="btn btn-windows git" onclick="history.go(-1)" type="button">取消</button>
  		</div>
  	<%--</form>
 --%></div>
</body>
</html>
