<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>外购成品件消耗定额明细</title>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>offerProduct/view.do?proId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>outsourcingCon/view.do?proId="+proId;
}

$(document).ready(function() { 
	 
	var totalRow = 0 ;
	var totalRow2 = 0;
	var totalRow3 = 0;
	var totalRow4 = 0;
	$('#table1 tr').each(function() { 
		$(this).find('td:eq(8)').each(function(){ 
			totalRow += parseFloat($(this).text()); 
		}); 
		$(this).find('td:eq(11)').each(function(){ 
			totalRow2 += parseFloat($(this).text()); 
		});
		$(this).find('td:eq(12)').each(function(){ 
			totalRow3 += parseFloat($(this).text()); 
		});
		$(this).find('td:eq(13)').each(function(){ 
			totalRow4 += parseFloat($(this).text()); 
		});
	}); 
	$('#total').html(totalRow);
	$('#total2').html(totalRow2);
	$('#total3').html(totalRow3);
	$('#total4').html(totalRow4);
	
}); 
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">外购成品件消耗定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>外购成品件消耗定额明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">成品件名称</th>
						<th rowspan="2" class="info">规格型号</th>
						<th rowspan="2" class="info">图纸位置号(代号)</th>
						<th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
						<th colspan="3" class="info">消耗定额审核核准数（含税金额）</th>
						<th rowspan="2" class="info">核减金额</th>
						<th rowspan="2" class="info">复核金额</th>
						<th rowspan="2" class="info">供货单位</th>
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">单位</th>
						<th class="info">单件重</th>
						<th class="info">重量小计</th>
						<th class="info">单价</th>
						<th class="info">金额</th>
						<th class="info">单位</th>
						<th class="info">单价</th>
						<th class="info">金额</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="out" varStatus="vs">
					<tr>
						<td>${vs.index+1 }<input type="hidden" name="chkItem" value="${out.id }" /></td>
						<td class="tc">${out.finishedName }</td>
						<td class="tc">${out.norm }</td>
						<td class="tc">${out.paperCode }</td>
						<td class="tc">${out.workAmout }</td>
						<td class="tc">${out.workWeight }</td>
						<td class="tc">${out.workWeightTotal }</td>
						<td class="tc">${out.workPrice }</td>
						<td class="tc">${out.workMoney }</td>
						<td class="tc">${out.consumeAmout }</td>
						<td class="tc">${out.consumePrice }</td>
						<td class="tc">${out.consumeMoney }</td>
						<td class="tc">${out.subtractMoney }</td>
						<td class="tc">${out.checkMoney }</td>
						<td class="tc">${out.supplyUnit }</td>
						<td class="tc">${out.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
				<thead>
					 <tr id="totalRow">
					 	<td class="tc" colspan="4">总计：</td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total"></td>
					 	<td colspan="2" ></td>
					 	<td class="tc" id="total2"></td>
					 	<td class="tc" id="total3"></td>
					 	<td class="tc" id="total4"></td>
					 	<td colspan="2" ></td>
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
