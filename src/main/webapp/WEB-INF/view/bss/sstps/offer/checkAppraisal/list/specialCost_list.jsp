<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>专项费用明细</title>
	
<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/outsourcingCon/userGetAllCheck.do?productId="+proId;
}


</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">专项费用明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>专项费用明细</h2>
	 	</div>
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="${pageContext.request.contextPath}/specialCost/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info">序号</th>
						<th class="info">项目名称</th>
						<th class="info">项目明细</th>
						<th class="info">名称</th>
						<th class="info">规格型号</th>
						<th class="info">计量单位</th>
						<th class="info">数量(消耗使用)</th>
						<th class="info">单价</th>
						<th class="info">金额</th>
						<th class="info">分摊数量</th>
						<th class="info">单位产品分摊额</th>
						<th class="info">核准金额</th>
						<th class="info">核减金额</th>
						<th class="info">复核准金额</th>
						<th class="info">复核减金额</th>
						<th class="info">备   注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="sc" varStatus="vs">
					<tr>
						<td><input type="hidden" value="${sc.id }" name="specialCosts['${vs.index }'].id"  />${vs.index+1 }</td>
						<td class="tc">${sc.projectName }</td>
						<td class="tc">${sc.productDetal }</td>
						<td class="tc">${sc.name }</td>
						<td class="tc">${sc.norm }</td>
						<td class="tc">${sc.measuringUnit }</td>
						<td class="tc">${sc.amount }</td>
						<td class="tc">${sc.price }</td>
						<td class="tc">${sc.money }</td>
						<td class="tc">${sc.proportionAmout }</td>
						<td class="tc">${sc.proportionPrice }</td>
						<td class="tc">${sc.approvedMoney }</td>
						<td class="tc">${sc.subtractMoney }</td>
						<td class="tc"><input type="text" value='${sc.checkApprovedMoney }' name="specialCosts['${vs.index }'].checkApprovedMoney"></td>
						<td class="tc"><input type="text" value='${sc.checkMoney }' name="specialCosts['${vs.index }'].checkMoney"></td>
						<td class="tc">${sc.remark }</td>
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
