<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>制造费用明细</title>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>wagesPayable/userGetAll.do?productId="+proId;
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">制造费用明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>制造费用明细</h2>
	 	</div>
	 	
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="<%=basePath %>manufacturingCost/userUpdate.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info">序号</th>
						<th class="info">项目名称</th>
						<th class="info">报价前2年</th>
						<th class="info">报价前1年</th>
						<th class="info">报价当年</th>
						<th class="info">审核核准数</th>
						<th class="info">备   注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="mc" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" value="${mc.id }" name="manufacturingCosts['${vs.index }'].id" />${vs.index+1 }</td>
						<td class="tc">${mc.projectName }</td>
						<td class="tc">${mc.tyaQuoteprice }</td>
						<td class="tc">${mc.oyaQuoteprice }</td>
						<td class="tc">${mc.newQuoteprice }</td>
						<td class="tc"><input type="text" value='${mc.auditApproval }' name="manufacturingCosts['${vs.index }'].auditApproval"></td>
						<td class="tc">${mc.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="submit">下一步</button>
		   </div>
	 	 </div>
	 	 </form> 
  </div>
  
  </body>
</html>
