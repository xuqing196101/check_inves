<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>采购档案列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function(){
			/**laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${archiveList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${archiveList.total}",
			    startRow: "${archiveList.startRow}",
			    endRow: "${archiveList.endRow}",
			    groups: "${archiveList.pages}">=5?5:"${archiveList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            
			        }
			    }
			});*/
		})
	
	</script>
  </head>
  
  <body>
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">成绩管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>查询条件</h2>
	   </div>
   </div>
   
   
   <div class="container">
  		<div class="border1 col-md-12 ml30">
	  		档案名称:<input type="text" id="name" class="mt10"/>
	  		档案编号:<input type="text" id="archiveCode" class="mt10"/>
	  		合同编号:<input type="text" id="contractCode" class="mt10"/>
	  		计划文号:<input type="text" id="planCode" class="mt10"/>
	  		<button class="btn" type="button" onclick="queryResult()">查询</button>
	  		<button class="btn" type="button" onclick="resetResult()">重置</button>
  		</div>
  	</div>
  	
  	<div class="container">
	   <div class="headline-v2">
	   		<h2>档案列表</h2>
	   </div>
   	</div>
  	
	  	<!-- 按钮开始-->
	   <div class="container">
	   		<div class="col-md-12">
			    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
			    <button class="btn btn-windows pl13" type="button" onclick="enter()">录入</button>
				<button class="btn btn-windows pl13" type="button" onclick="audit()">审核</button>
			    <button class="btn btn-windows pl13" type="button" onclick="placeFile()">归档</button>
			</div>
	    </div>
  	
  	<div class="container">
  		<div class="content padding-left-25 padding-right-25 padding-top-5">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th>序号</th>
						<th>档案名称</th>
						<th>档案编号</th>
						<th>合同编号</th>
						<th>计划文号</th>
						<th>计划下达时间</th>
						<th>预算年度</th>
						<th>采购机构</th>
						<th>采购方式</th>
						<th>产品名称</th>
						<th>供应商名称</th>
					</tr>
				</thead>
				<tbody>
					<%--<c:forEach items="${archiveList.list }" var="result" varStatus="vs">
						<tr>
							<td class="tc">${(vs.index+1)+(archiveList.pageNum-1)*(archiveList.pageSize)}</td>
							
						</tr>
					</c:forEach>
				--%></tbody>
			</table>
		</div>
		<div id="pageDiv" align="right"></div>
  	</div>
  </body>
</html>
