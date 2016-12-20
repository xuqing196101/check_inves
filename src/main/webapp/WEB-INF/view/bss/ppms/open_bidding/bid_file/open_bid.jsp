<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/view/common.jsp"%>
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
								minutes=parseInt(minutes);
								if(minutes<1){
									var second=(bidTime-60*60*24*1000*day)/1000;
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
        <input type="hidden" id="projectId" value="${project.id }" />
        <h2 class="list_title">项目名称：${project.name } 
        	项目编号：${project.projectNumber }
        	开标倒计时：<span id="showTime"></span></h2>
    		<table class="table table-bordered table-condensed mt5">
             <c:forEach items="${packageList }" var="pack" varStatus="p">
               <div class="col-md-6 col-sm-6 col-xs-12 p0">
                 <span class="f16 b">包名:</span>
                 <span class="f14 blue">${pack.name }</span>
               </div>
               <input type="hidden" value="${pack.id }"/>
               <table class="table table-bordered table-condensed table-hover table-striped">
                 <thead>
                   <tr>
                     <th class="info w50">序号</th>
                     <th class="info">需求部门</th>
                     <th class="info">物资名称</th>
                     <th class="info">规格型号</th>
                     <th class="info">质量技术标准</th>
                     <th class="info">计量单位</th>
                     <th class="info">采购数量</th>
                     <th class="info">单价（元）</th>
                     <th class="info">预算金额（万元）</th>
                     <th class="info">交货期限</th>
                     <th class="info">采购方式建议</th>
                     <th class="info">供应商名称</th>
                     <c:if test="${pack.isImport==1 }">
                       <th class="info">是否申请办理免税</th>
                       <th class="info">物资用途（进口）</th>
                       <th class="info">使用单位（进口）</th>
                     </c:if>
                     <th class="info">备注</th>
                   </tr>
		         </thead>
		         <c:forEach items="${pack.projectDetails}" var="obj">
		           <tr>
		             <td class="tc w50">${obj.serialNumber}</td>
		             <td class="tc">${obj.department}</td>
		             <td class="tc">${obj.goodsName}</td>
		             <td class="tc">${obj.stand}</td>
		             <td class="tc">${obj.qualitStand}</td>
		             <td class="tc">${obj.item}</td>
		             <td class="tc">${obj.purchaseCount}</td>
		             <td class="tc">${obj.price}</td>
		             <td class="tc">${obj.budget}</td>
		             <td class="tc">${obj.deliverDate}</td>
		             <td class="tc">
		               <c:forEach items="${kind}" var="kind" >
		                 <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
		               </c:forEach>
		             </td>
		             <td class="tc">${obj.supplier}</td>
		             <c:if test="${pack.isImport==1 }">
		               <td class="tc">${obj.isFreeTax}</td>
		               <td class="tc">${obj.goodsUse}</td>
		               <td class="tc">${obj.useUnit}</td>
		             </c:if>
		             <td class="tc">${obj.memo}</td>
		           </tr>
		         </c:forEach> 
		      </table>
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
		  <th class="info">解密状态</th>
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
</body>
</html>
