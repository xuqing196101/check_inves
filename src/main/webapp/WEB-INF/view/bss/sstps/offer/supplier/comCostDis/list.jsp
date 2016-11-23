<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>综合费用汇总分配计算明细</title>
	
<script type="text/javascript">
function edit(){
	var proId = $("#proId").val();
	var id=[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==1){
		window.location.href="${pageContext.request.contextPath}/periodCost/edit.do?id="+id+"&proId="+proId;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择修改的内容",{offset: ['222px', '390px'], shade:0.01});
	}
}

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/productQuota/select.do?proId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/yearPlan/select.do?proId="+proId;
}

function next(){
	var proId = $("#proId").val();
	
	$("#formID").submit();
}

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">供应商报价</a></li><li><a href="javascript:void(0)">综合费用汇总分配计算明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>综合费用汇总分配计算明细</h2>
	 	</div>
	 	
   </div>
	
	<form id="formID" name="form1" action="${pageContext.request.contextPath}/comCostDis/update.html?proId=${proId }" method="post">
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">项目名称</th>
						<th colspan="2" class="info">报价前2年</th>
						<th colspan="2" class="info">报价前1年</th>
						<th colspan="2" class="info">报价当年</th>
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
						<th class="info">发生额</th>
						<th class="info">费用率(元/小时)</th>
					</tr>
				</thead>
				
				
				<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
				<tbody>
				<c:forEach items="${list}" var="ccd" varStatus="vs">
				<c:if test="${ccd.status==0}">
					<tr>
						<td class="tc" class="w30">
							<input type="hidden" name="plccd[${(vs.index)}].id" value="${ccd.id }"/>
							<input type="hidden" name="plccd[${(vs.index)}].status" value="${ccd.status }"  />
							${vs.index+1 }
						</td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].projectName" value="${ccd.projectName }" readonly class="border0 mb0 w100"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].tyaAmount" value="${ccd.tyaAmount }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].tyaFee" value="${ccd.tyaFee }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].oyaAmout" value="${ccd.oyaAmout }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].oyaFee" value="${ccd.oyaFee }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].newAmount" value="${ccd.newAmount }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].newFee" value="${ccd.newFee }" class="w50 mb0" /></td>
						<td class="tc"><input type="text" name="plccd[${(vs.index)}].remark" value="${ccd.remark }" class="mb0" /></td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
				
			</table>
		</div>
	</div>
	
		<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="info">序号</th>
						<th class="info">项目名称</th>
						<th class="info">报价前2年</th>
						<th class="info">报价前1年</th>
						<th class="info">报价当年</th>
						<th class="info">备   注</th>
					</tr>
				</thead>
				
				<input type="hidden" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
				<tbody>
				<c:forEach items="${list}" var="ccd" varStatus="vss">
				<c:if test="${ccd.status==1 }">
					<tr>
						<td class="tc" class="w30">
							<input type="hidden" name="plccd[${(vss.index)}].id" value="${ccd.id }"/>
							<input type="hidden" name="plccd[${(vss.index)}].status" value="${ccd.status }"  />
							${vs.index+1 }
						</td>
						<td class="tc"><input type="text" name="plccd[${(vss.index)}].projectName" value="${ccd.projectName }" readonly class="border0 mb0 w120"/></td>
						<td class="tc"><input type="text" name="plccd[${(vss.index)}].tyaActual" value="${ccd.tyaActual }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vss.index)}].oyaActual" value="${ccd.oyaActual }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vss.index)}].newActual" value="${ccd.newActual }" class="w50 mb0"/></td>
						<td class="tc"><input type="text" name="plccd[${(vss.index)}].remark" value="${ccd.remark }" class="mb0" /></td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" id="button" type="button" onclick="next()" >下一步</button>
		   </div>
	 	 </div>
	 	 
  </div>
  </form>
  
  </body>
</html>
