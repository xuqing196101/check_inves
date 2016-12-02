<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>年度计划任务总工时明细</title>
	

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/periodCost/userGetAllCheck.do?productId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/productQuota/userGetAllCheck.do?productId="+proId;
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">年度计划任务总工时明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>年度计划任务总工时明细</h2>
	 	</div>
	 	
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">项目名称</th>
						<th rowspan="2" class="info">产品单位</th>
						<th rowspan="2" class="info">计量单位</th>
						<th colspan="3" class="info">报价前2年</th>
						<th colspan="3" class="info">报价前1年</th>
						<th colspan="3" class="info">报价当年</th>
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">单位产品定额工时</th>
						<th class="info">投产数量</th>
						<th class="info">工时合计</th>
						<th class="info">单位产品定额工时</th>
						<th class="info">投产数量</th>
						<th class="info">工时合计</th>
						<th class="info">单位产品定额工时</th>
						<th class="info">投产数量</th>
						<th class="info">工时合计</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="yp" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" name="chkItem" value="${yp.id }" />${vs.index+1 }</td>
						<td class="tc">${yp.projectName }</td>
						<td class="tc">${yp.productName }</td>
						<td class="tc">${yp.measuringUnit }</td>
						
						<td class="tc">${yp.tyaHourUnit }</td>
						<td class="tc">${yp.tyaInvestAcount }</td>
						<td class="tc">${yp.tyaHourTotal }</td>
						
						<td class="tc">${yp.oyaHourUnit }</td>
						<td class="tc">${yp.oyaInvestAcount }</td>
						<td class="tc">${yp.oyaHourTotal }</td>
						
						<td class="tc">${yp.newHourUnit }</td>
						<td class="tc">${yp.newInvestAcount }</td>
						<td class="tc">${yp.newHourTotal }</td>
						
						<td class="tc">${yp.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="button" onclick="nextStep()">下一步</button>
		   </div>
	 	 </div>
	 	 
  </div>
  
  </body>
</html>
