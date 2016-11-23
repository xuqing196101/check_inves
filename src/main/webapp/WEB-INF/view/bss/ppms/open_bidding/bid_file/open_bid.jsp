<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
	function show(){
		$("div").removeClass("disNon");
	}
	 $(function(){
	   if('${date}'>0){
	 	setInterval("showTime()", 1000);
	   } else {
	   	$("#showTime").text("已开标");
	   }
	 });
		function showTime(){
		    var projectId=$("#projectId").val();
		   $.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/showTime.do",
			type:"post",
			data:{projectId:projectId},
			success:function(data){
					var bidTime=data;
					if(bidTime<0){
						$("#showTime").text("已开标");
						//clearInterval("showTiem()");
					}else{
						var day=bidTime/(60*60*24*1000);
						day=parseInt(day);
						if(day<1){
						    day=0;
							var hour=bidTime/(60*60*1000);
							hour=parseInt(hour);
							if(hour<1){
							    hour=0;
								var minutes=bidTime/(60*1000);
								minutes=parseInt(minutes);
								if(minutes<1){
									var second=bidTime/1000;
									second=parseInt(second);
								}else{
								    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
								    second=parseInt(second);
								};
							}else{
								var minutes=(bidTime-hour*60*60*1000-60*60*24*1000*day)/(60*1000);
								var second=0;
								minutes=parseInt(minutes);
								if(minutes<1){
								    minutes=0;
									second=(bidTime-hour*60*60*1000-60*60*24*1000*day)/1000;
									second=parseInt(second);
								}else{
								    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
								    second=parseInt(second);
								};
							};
						}else{
							var hour=(bidTime-60*60*24*1000*day)/(60*60*1000);
							hour=parseInt(hour);
							if(hour<1){
							    hour=0;
								var minutes=(bidTime-60*60*24*1000*day)/(60*1000);
								if(minutes<1){
									var second=(bidTime-60*60*24*1000*day)/1000;
									second=parseInt(second);
								};
							}else{
								var minutes=(bidTime-hour*60*60*1000-60*60*24*1000*day)/(60*1000);
								var second=0;
								minutes=parseInt(minutes);
								if(minutes<1){
								    minutes=0;
									second=(bidTime-hour*60*60*1000-60*60*24*1000*day)/1000;
									second=parseInt(second);
								}else{
								    second=(bidTime-hour*60*60*1000-60*60*24*1000*day-minutes*60*1000)/1000;
								    second=parseInt(second);
								};
							};
						};
						var time=day+"天"+hour+"时"+minutes+"分"+second+"秒";
						$("#showTime").text(time);
					};
					}
			}); 
		};
</script>
</head>
<body>
<!-- 表格开始-->  
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <input type="hidden" id="projectId" value="${project.id }" />
        <span><b>项目名称</b>：${project.name }</span>  
        <span><b>项目编号</b>：${project.projectNumber }</span>
        <span><b>开标倒计时</b>：<span id="showTime"></span></span>
        <table class="table table-striped table-bordered table-hover tc padding-top-5">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">名称</th>
		  <th class="info">单位</th>
		  <th class="info">数量</th>
		  <th class="info">预算</th>
		</tr>
		</thead>
		<c:forEach items="${listPd }" var="lpd" varStatus="vs">
			<tr>
			    <td>${vs.index+1 }</td>
				<td>${lpd.goodsName }</td>
				<td>${lpd.item }</td>
				<td>${lpd.purchaseCount }</td>
				<td>${lpd.budget }</td>
			</tr>
		</c:forEach> 
        </table>
        
        <b>报价供应商</b>
        <table id="supplier" class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">联系人</th>
		  <th class="info">联系电话</th>
		  <th class="info">投标文件状态</th>
		  <th class="info">揭秘状态</th>
		</tr>
		</thead>
		<c:forEach items="${listSupplier }" var="ls" varStatus="vs">
			<tr>
			    <td>${vs.index+1 }</td>
				<td>${ls.supplierName }</td>
				<td>${ls.contactName }</td>
				<td>${ls.mobile }</td>
				<td>${ls.bidFinish }</td>
				<td></td>
			</tr>
		</c:forEach> 
        </table>
     </div>
   </div>
</body>
</html>
