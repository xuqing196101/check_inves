<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购档案查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			
		})
	</script>

  </head>
  
  <body>
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购档案查询</a></li>
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
	  		计划下达时间:<input type="text" id="planTime" class="mt10"/>
	  		预算年度:<input type="text" id="planTime" class="mt10"/>
	  		采购机构:<input type="text" id="planTime" class="mt10"/>
	  		采购方式:<input type="text" id="planTime" class="mt10"/>
	  		产品名称:<input type="text" id="planTime" class="mt10"/>
	  		供应商名称:<input type="text" id="planTime" class="mt10"/>
	  		<button class="btn" type="button" onclick="queryResult()">查询</button>
	  		<button class="btn" type="button" onclick="resetResult()">重置</button>
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
						<th>采购文件上报时间</th>
						<th>采购文件批复时间</th>
						<th>合同草案上报时间</th>
						<th>合同草案批复时间</th>
						<th>正式合同上报时间</th>
						<th>正式合同批复时间</th>
						<th>首件检验和出厂验收时间</th>
						<th>发运和结算时间</th>
						<th>状态</th>
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
