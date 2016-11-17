<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>产品工时定额明细</title>
	
<script type="text/javascript">
/** 全选全不选 */
function selectAll(){
	 var checklist = document.getElementsByName ("chkItem");
	 var checkAll = document.getElementById("checkAll");
	   if(checkAll.checked){
		   for(var i=0;i<checklist.length;i++)
		   {
		      checklist[i].checked = true;
		   } 
		 }else{
		  for(var j=0;j<checklist.length;j++)
		  {
		     checklist[j].checked = false;
		  }
	 	}
	}

/** 单选 */
function check(){
	 var count=0;
	 var checklist = document.getElementsByName ("chkItem");
	 var checkAll = document.getElementById("checkAll");
	 for(var i=0;i<checklist.length;i++){
		   if(checklist[i].checked == false){
			   checkAll.checked = false;
			   break;
		   }
		   for(var j=0;j<checklist.length;j++){
				 if(checklist[j].checked == true){
					   checkAll.checked = true;
					   count++;
				   }
			 }
	   }
}

function edit(){
	var proId = $("#proId").val();
	var id=[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==1){
		window.location.href="${pageContext.request.contextPath}/productQuota/edit.do?id="+id+"&proId="+proId;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择修改的内容",{offset: ['222px', '390px'], shade:0.01});
	}
}

function add(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/productQuota/add.html?proId="+proId;
}

function del(){
	var proId = $("#proId").val();
	var ids =[]; 
	$('input[name="chkItem"]:checked').each(function(){ 
		ids.push($(this).val()); 
	}); 
	if(ids.length>0){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
			layer.close(index);
			window.location.href="${pageContext.request.contextPath}/productQuota/delete.html?proId="+proId+"&ids="+ids;
		});
	}else{
		layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
	}
}

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/yearPlan/select.do?proId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/comCostDis/select.do?proId="+proId;
}

$(function(){ 
	var totalRow1 = 0;
	var totalRow2 = 0;
	$("#table1 tr").each(function() { 
		$(this).find("td:eq(11)").each(function(){ 
			totalRow1 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(14)").each(function(){ 
			totalRow2 += parseFloat($(this).text()); 
		});
		
	}); 
	if(totalRow1!=null){
		$("#total1").html(totalRow1);
	}
	if(totalRow2!=null){
		$("#total2").html(totalRow2);
	}
}); 

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">供应商报价</a></li><li><a href="javascript:void(0)">产品工时定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>产品工时定额明细</h2>
	 	</div>
	 	
	 	<div class="col-md-8 mt10 pl20 ml5">
	   		<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
	   		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
	   		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
		</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="3" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
						<th rowspan="3" class="info">序号</th>
						<th rowspan="3" class="info">零组部件名称</th>
						<th rowspan="3" class="info">零组部件图号</th>
						<th rowspan="3" class="info">工序名称</th>
						<th colspan="7" class="info">单位产品工时定额</th>
						<th rowspan="3" class="info">计量单位</th>
						<th rowspan="2" class="info">配套数量</th>
						<th rowspan="2" class="info">单位产品工时审核核定数</th>
						<th rowspan="3" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">准结工时</th>
						<th class="info">加工工时</th>
						<th class="info">装配工时</th>
						<th class="info">调试工时</th>
						<th class="info">试验工时</th>
						<th class="info">其他工时</th>
						<th class="info">小计</th>
					</tr>
					<tr>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
						<th class="info">报价</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="yp" varStatus="vs">
					<tr>
						<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${yp.id }" /></td>
						<td class="tc">${vs.index+1 }</td>
						<td class="tc">${yp.partsName }</td>
						<td class="tc">${yp.partsDrawingCode }</td>
						<td class="tc">${yp.processName }</td>
						
						<td class="tc">${yp.offer }</td>
						<td class="tc">${yp.processingOffer }</td>
						<td class="tc">${yp.assemblyOffer }</td>
						<td class="tc">${yp.debuggingOffer }</td>
						<td class="tc">${yp.testOffer }</td>
						<td class="tc">${yp.otherOffer }</td>
						<td class="tc">${yp.subtotalOffer }</td>
						
						<td class="tc">${yp.measuringUnit }</td>
						
						<td class="tc">${yp.assortOffer }</td>
						<td class="tc">${yp.approvedOffer }</td>
						<td class="tc">${yp.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
				<thead>
					<tr>
					 	<td class="tc" colspan="5">总计：</td>
					 	<td colspan="6" ></td>
					 	<td class="tc" id="total1"></td>
					 	<td></td>
					 	<td></td>
					 	<td class="tc" id="total2"></td>
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
