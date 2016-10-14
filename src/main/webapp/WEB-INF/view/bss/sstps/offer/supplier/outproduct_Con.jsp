<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>外购成品件消耗定额明细</title>
	
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
		window.location.href="<%=basePath%>outproductCon/edit.do?id="+id+"&proId="+proId;
	}else if(id.length>1){
		layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	}else{
		layer.alert("请选择修改的内容",{offset: ['222px', '390px'], shade:0.01});
	}
}

function add(){
	var proId = $("#proId").val();
	window.location.href="<%=basePath%>outproductCon/add.html?proId="+proId;
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
			window.location.href="<%=basePath%>outproductCon/delete.html?proId="+proId+"&ids="+ids;
		});
	}else{
		layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
	}
}

function onPage(){
	var productId = $("#proId").val();
	window.location.href="<%=basePath%>offer/selectProductInfo.do?productId="+productId;
}

function nextPage(){
	var proId = $("#proId").val();
}

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
	 	
	 	<div class="col-md-8 mt10">
	   		<button class="btn btn-windows add" type="button" onclick="add()">添加</button>
	   		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
	   		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
		</div>
		
   </div>
	
	<input type="text" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
				<tobdy>
					<tr>
						<th rowspan="2"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
						<th rowspan="2">序号</th>
						<th rowspan="2">成品件名称</th>
						<th rowspan="2">规格型号</th>
						<th rowspan="2">图纸位置号(代号)</th>
						<th colspan="5">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
						<th colspan="3">消耗定额审核核准数（含税金额）</th>
						<th rowspan="2">供货单位</th>
						<th rowspan="2">备   注</th>
					</tr>
					<tr>
						<th>单位</th>
						<th>单件重</th>
						<th>重量小计</th>
						<th>单价</th>
						<th>金额</th>
						<th>单位</th>
						<th>单价</th>
						<th>金额</th>
					</tr>
				</tobdy>
				<c:forEach items="${list}" var="out" varStatus="vs">
					<tr>
						<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${out.id }" /></td>
						<td>${vs.index+1 }</td>
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
						<td class="tc">${out.supplyUnit }</td>
						<td class="tc">${out.remark }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn btn-windows " type="button" onclick="onPage()">上一页</button>
		    <button class="btn btn-windows " type="button" onclick="nextPage()">下一页</button>
		   </div>
	 	 </div>
	 	 
  </div>
  
  </body>
</html>
