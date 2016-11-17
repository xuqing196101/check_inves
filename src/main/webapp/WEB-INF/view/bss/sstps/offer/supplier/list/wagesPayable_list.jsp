<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>应付工资明细</title>
	

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/burningPower/view.do?proId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/manufacturingCost/view.do?proId="+proId;
}

$(function(){ 
	var totalRow1 = 0;
	var totalRow2 = 0;
	var totalRow3 = 0;
	var totalRow4 = 0;
	var totalRow5 = 0;
	$("#table1 tr").each(function() { 
		$(this).find("td:eq(8)").each(function(){ 
			totalRow1 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(13)").each(function(){ 
			totalRow2 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(18)").each(function(){ 
			totalRow3 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(22)").each(function(){ 
			totalRow4 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(27)").each(function(){ 
			totalRow5 += parseFloat($(this).text()); 
		});
		
	}); 
	if(totalRow1!=null){
		$("#total1").html(totalRow1);
	}
	if(totalRow2!=null){
		$("#total2").html(totalRow2);
	}
	if(totalRow3!=null){
		$("#total3").html(totalRow3);
	}
	if(totalRow4!=null){
		$("#total4").html(totalRow4);
	}
	if(totalRow5!=null){
		$("#total5").html(totalRow5);
	}
	
}); 

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">供应商报价</a></li><li><a href="javascript:void(0)">应付工资明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>应付工资明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">部门</th>
						<th rowspan="2" class="info">一级项目</th>
						<th rowspan="2" class="info">二级项目</th>
						<th colspan="5" class="info">报价前2年</th>
						<th colspan="5" class="info">报价前1年</th>
						<th colspan="5" class="info">报价当年</th>
						<th colspan="5" class="info">审核核准数</th>
						<th colspan="5" class="info">复核核准数</th>
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">基本生产人员</th>
						<th class="info">车间管理人员</th>
						<th class="info">管理人员</th>
						<th class="info">其他人员</th>
						<th class="info">合计</th>
						<th class="info">基本生产人员</th>
						<th class="info">车间管理人员</th>
						<th class="info">管理人员</th>
						<th class="info">其他人员</th>
						<th class="info">合计</th>
						<th class="info">基本生产人员</th>
						<th class="info">车间管理人员</th>
						<th class="info">管理人员</th>
						<th class="info">其他人员</th>
						<th class="info">合计</th>
						<th class="info">基本生产人员</th>
						<th class="info">车间管理人员</th>
						<th class="info">管理人员</th>
						<th class="info">其他人员</th>
						<th class="info">合计</th>
						<th class="info">基本生产人员</th>
						<th class="info">车间管理人员</th>
						<th class="info">管理人员</th>
						<th class="info">其他人员</th>
						<th class="info">合计</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="wp" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" name="" value="${wp.id }" />${vs.index+1 }</td>
						<td class="tc">${wp.department }</td>
						<td class="tc">${wp.firsetProduct }</td>
						<td class="tc">${wp.secondProduct }</td>
						
						<td class="tc">${wp.tyaProduceUser }</td>
						<td class="tc">${wp.tyaWorkshopUser }</td>
						<td class="tc">${wp.tyaManageUser }</td>
						<td class="tc">${wp.tyaOtherUser }</td>
						<td class="tc">${wp.tyaTotal }</td>
						
						<td class="tc">${wp.oyaProduceUser }</td>
						<td class="tc">${wp.oyaWorkshopUser }</td>
						<td class="tc">${wp.oyaManageUser }</td>
						<td class="tc">${wp.oyaOtherUser }</td>
						<td class="tc">${wp.oyaTotal }</td>
						
						<td class="tc">${wp.newProduceUser }</td>
						<td class="tc">${wp.newWorkshopUser }</td>
						<td class="tc">${wp.newManageUser }</td>
						<td class="tc">${wp.newOtherUser }</td>
						<td class="tc">${wp.newTotal }</td>
						
						<td class="tc">${wp.approvedProduceUser }</td>
						<td class="tc">${wp.approvedWorkshopUser }</td>
						<td class="tc">${wp.approvedManageUser }</td>
						<td class="tc">${wp.approvedOtherUser }</td>
						<td class="tc">${wp.approvedTotal }</td>
						
						<td class="tc">${wp.checkProduceUser }</td>
						<td class="tc">${wp.checkWorkshopUser }</td>
						<td class="tc">${wp.checkManageUser }</td>
						<td class="tc">${wp.checkOtherUser }</td>
						<td class="tc">${wp.checkTotal }</td>
						
						<td>${wp.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
				<thead>
					<tr>
					 	<td class="tc" colspan="4">总计：</td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total1"></td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total2"></td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total3"></td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total4"></td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total5"></td>
					 	<td ></td>
					 </tr>
				 </thead> 	
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
