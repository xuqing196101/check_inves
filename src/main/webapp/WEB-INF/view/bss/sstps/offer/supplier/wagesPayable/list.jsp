<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>应付工资明细</title>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

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
		window.location.href="<%=basePath%>wagesPayable/edit.do?id="+id+"&proId="+proId;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择修改的内容",{offset: ['222px', '390px'], shade:0.01});
	}
}

function add(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>wagesPayable/add.html?proId="+proId;
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
			window.location.href="<%=basePath%>wagesPayable/delete.html?proId="+proId+"&ids="+ids;
		});
	}else{
		layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
	}
}

function onStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>burningPower/select.do?proId="+proId;
}

function nextStep(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>manufacturingCost/select.do?proId="+proId;
}

$(function(){ 
	var totalRow1 = 0;
	var totalRow2 = 0;
	var totalRow3 = 0;
	$("#table1 tr").each(function() { 
		$(this).find("td:eq(9)").each(function(){ 
			totalRow1 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(14)").each(function(){ 
			totalRow2 += parseFloat($(this).text()); 
		});
		$(this).find("td:eq(19)").each(function(){ 
			totalRow3 += parseFloat($(this).text()); 
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
}); 
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商报价</a></li><li><a href="#">应付工资明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>应付工资明细</h2>
	 	</div>
	 	
	 	<div class="col-md-8 mt10 pl20">
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
						<th rowspan="2" class="info">部门</th>
						<th rowspan="2" class="info">一级项目</th>
						<th rowspan="2" class="info">二级项目</th>
						<th colspan="5" class="info">报价前2年</th>
						<th colspan="5" class="info">报价前1年</th>
						<th colspan="5" class="info">报价当年</th>
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
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="wp" varStatus="vs">
					<tr>
						<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${wp.id }" /></td>
						<td class="tc">${vs.index+1 }</td>
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
						
						<td class="tc">${wp.remark }</td>
					</tr>
				</c:forEach>
				</tbody>
				<thead>
					<tr>
					 	<td class="tc" colspan="5">总计：</td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total1"></td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total2"></td>
					 	<td colspan="4" ></td>
					 	<td class="tc" id="total3"></td>
					 	<td></td>
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
