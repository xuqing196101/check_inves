<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>  
    <head>  
<jsp:include page="/WEB-INF/view/common.jsp"/> 
 
 <script type="text/javascript">
 
 function aadd(obj){
		 var id=null;
		 $.ajax({
			 url:"${pageContext.request.contextPath}/purchaser/getId.html",
			 type:"post",
			 
			 success:function(data){
		 			id=data;
		 			var tr=$(obj).parent().parent().parent();
		 			$(tr).children(":first").children(":first").val(data);
		 			
		 			var  s=$("#count").val();
			      	s++;
			      	$("#count").val(s);
			        var trs = $(obj).parent().parent().parent();
			        $(trs).after("<tr><td class='tc'><input style='border: 0px;' type='hidden' name='list["+s+"].id' />"+
			        "<input style='border: 0px;' type='text' name='list["+s+"].seq' /><input style='border: 0px;' value='"+id+"' type='hidden' name='list["+s+"].parentId' /></td>"+
				       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+ 
				     	"<td class='tc'><div style='width:160px;'> <input class='btn' name='dyadds' type='button' onclick='aadd(this)' title='添加子节点'  value='+'>"+
						 "<input class='btn' name='delt' type='button' onclick='same(this)'title='添加同级点'  value='+'>"+
					 	" <input class='btn' name='delt' type='button' onclick='news(this)' title='新明细'  value='+'></div> </td>"+  
			        +"<tr/>");
			        
			 },error:function(){
				 
			 }
		 });
		 
		  
	  
  }
 
 function same(obj){
	 var tr=$(obj).parent().parent().parent();
		var id=$(tr).children(":first").children(":last").val();
		
		
			var  s=$("#count").val();
		   	s++;
		   	$("#count").val(s);
		     var trs = $(obj).parent().parent().parent();
		     $(trs).after("<tr><td class='tc'><input style='border: 0px;' type='hidden' name='list["+s+"].id' />"+
		     "<input style='border: 0px;' type='text' name='list["+s+"].seq' /><input style='border: 0px;' value='"+id+"' type='hidden' name='list["+s+"].parentId' /></td>"+
			       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
			       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
			       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
			       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
			       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+ 
			     	"<td class='tc'><div style='width:160px;'> <input class='btn' name='dyadds' type='button' onclick='aadd(this)' title='添加子节点'  value='+'>"+
					 "<input class='btn' name='delt' type='button' onclick='same(this)' title='添加同级点'  value='+'>"+
				 	" <input class='btn' name='delt' type='button' onclick='news(this)' title='新明细' value='+'></div> </td>"+  
		     +"<tr/>");
	  
  }
 
 
 
 function news(obj){
		 var id=null;
		 $.ajax({
			 url:"${pageContext.request.contextPath}/purchaser/getId.html",
			 type:"post",
			 
			 success:function(data){
		 			var  s=$("#count").val();
			      	s++;
			      	$("#count").val(s);
			        var trs = $(obj).parent().parent().parent();
			        $(trs).after("<tr><td class='tc'><input style='border: 0px;' type='text' value='"+data+"' name='list["+s+"].id' />"+
			        "<input style='border: 0px;' type='text' name='list["+s+"].seq' /></td>"+
				       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+ 
				     	"<td class='tc'><div style='width:160px;'> <input class='btn' name='dyadds' type='button' onclick='aadd(this)' title='添加子节点' value='+'>"+
						 "<input class='btn' name='delt' type='button' title='添加同级节点' onclick='same(this)' value='+'>"+
					 	" <input class='btn' name='delt' type='button' title='新明细' onclick='news(this)' value='+'></div> </td>"+  
			        +"<tr/>");
			        
			 },error:function(){
				 
			 }
		 });
		 
		  
	  
  }
 
 function incr(){
	  $("#add_form").submit();   
 }
 </script>
 
 
 </head>  
  
 <body>  
 
 	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购需求明细录入</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	
	
  <div class="container clear margin-top-30" id="add_div" >
	
	

		<form id="add_form" action="${pageContext.request.contextPath}/purchaser/adddetail.html" method="post">
			<input type="hidden" name="planName" value="${planName }">
			<input type="hidden" name="planNo" value="${planNo }">
			<input type="hidden" name="type" value="${type }">
			<table class="table table-bordered table-condensed mt5" >
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info">需求部门</th>
						<th class="info">物资类别及物种名称</th>
						<th class="info">规格型号</th>
						<th class="info">质量技术标准（技术参数）</th>
						<th class="info">计量单位</th>
						<th class="info">采购数量</th>
						<th class="info">单位（元）</th>
						<th class="info">预算金额（万元）</th>
						<th class="info">交货期限</th>
						<th class="info">采购方式建议</th>
						<th class="info">供应商名称</th>
						<th class="info">是否申请办理免税</th>
						<th class="info">物资用途（仅进口）</th>
						<th class="info">使用单位（仅进口）</th>
						<th class="info">备注</th>
						<th class="info">操作</th>
					</tr>
				</thead>

				<tr>
					<td class="tc w50">
					<input style="border: 0px;" type="hidden" name="list[0].id" id="purid" value="">
					<input style="border: 0px;width: 50px;" type="text" name="list[0].seq" value="">
					</td>
					<td><input style="border: 0px;" type="text" name="list[0].department" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[0].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[0].stand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[0].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].item" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].price" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].budget" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[0].deliverDate" value=""></td>
					<td><input style="border: 0px;width: 60px;"  type="text" name="list[0].purchaseType" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;width: 60px;"  type="text" name="list[0].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[0].memo" value=""></td>
						<td class="tc">
						<div style="width: 160px">
							 <input class="btn" name="dyadds" type="button" onclick="aadd(this)" title="添加子节点" value="+">
							 <input class="btn" name="delt" type="button" onclick="same(this)" title="添加同级点" value="+">
						 	 <input class="btn" name="delt" type="button" onclick="news(this)" title="新增一个任务" value="+">
						 	 
						 </div>
						</td>
				</tr>
			</table>
			<input class="btn btn-windows save" type="button" onclick="incr()" value="提交">
			<input class="btn padding-left-20 padding-right-20 btn_back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">

		</form>
	</div>

  
  	<input type="hidden" id="count" value="0">
</body>
</html>