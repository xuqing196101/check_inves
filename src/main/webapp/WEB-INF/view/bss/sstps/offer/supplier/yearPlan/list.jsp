<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>年度计划任务总工时明细</title>

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
		window.location.href="<%=basePath%>yearPlan/edit.do?id="+id+"&proId="+proId;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择修改的内容",{offset: ['222px', '390px'], shade:0.01});
	}
}

function add(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>yearPlan/add.html?proId="+proId;
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
			window.location.href="<%=basePath%>yearPlan/delete.html?proId="+proId+"&ids="+ids;
		});
	}else{
		layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
	}
}

function onStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>periodCost/select.do?proId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	var total = $("#total3").val();
	window.location.href="<%=basePath%>productQuota/select.do?proId="+proId+"&total="+total;
}


$(document).ready(function() { 
	var totalRow = 0 ;
	var totalRow2 = 0;
	var totalRow3 = 0;
	$('#table1 tr').each(function() { 
		$(this).find('td:eq(7)').each(function(){ 
			totalRow += parseFloat($(this).text()); 
		}); 
		$(this).find('td:eq(10)').each(function(){ 
			totalRow2 += parseFloat($(this).text()); 
		});
		$(this).find('td:eq(13)').each(function(){ 
			totalRow3 += parseFloat($(this).text()); 
		});
	}); 
	$('#total1').val(totalRow);
	$('#total2').val(totalRow2);
	$('#total3').val(totalRow3);
	
}); 

</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">年度计划任务总工时明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>年度计划任务总工时明细</h2>
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
						<th rowspan="2" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
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
						<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${yp.id }" /></td>
						<td class="tc">${vs.index+1 }</td>
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
				<thead>
					 <tr id="totalRow">
					 	<td colspan="5" class="tc">总计：</td>
					 	<td colspan="2" ></td>
					 	<td class="tc"><input type="text" id="total1" class="border0 tc w50" readonly="readonly"></td>
					 	<td colspan="2" ></td>
					 	<td class="tc"><input type="text" id="total2" class="border0 tc w50" readonly="readonly"></td>
					 	<td colspan="2" ></td>
					 	<td class="tc"><input type="text" id="total3" class="border0 tc w50" readonly="readonly"></td>
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
