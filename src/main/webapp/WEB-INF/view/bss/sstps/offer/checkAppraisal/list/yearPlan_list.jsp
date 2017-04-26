<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="../../../../../common.jsp"%>
    
    <title>年度计划任务总工时明细</title>
	

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/periodCost/userGetAllCheck.do?productId="+proId;
}
/* 
function nextStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/productQuota/userGetAllCheck.do?productId="+proId;
} */
function totalHours(obj,type){
	  var hourUnit=0;
	  var investAcount=0;
	  if(type==1){
		  if($(obj).val()!=""){
			  hourUnit=parseFloat($(obj).val());
	      }
		  if($(obj).parent().next().children(":first").val()!=""){
			  investAcount=parseFloat($(obj).parent().next().children(":first").val());
		  }
		  $(obj).parent().next().next().children(":first").val((hourUnit*investAcount).toFixed(2));
	  }else{
		  if($(obj).val()!=""){
			  investAcount=parseFloat($(obj).val());
		  }
		  if($(obj).parent().prev().children(":first").val()!=""){
			  hourUnit=parseFloat($(obj).parent().prev().children(":first").val());
		  }
		  $(obj).parent().next().children(":first").val((investAcount*hourUnit).toFixed(2));
		  
	  }
	  
}
$(document).ready(function() {
    var totalRow4 = 0;
    var totalRow2 = 0;
    var totalRow3 = 0;
    var totalRow5 = 0;
    $('#table1 tr:not(:last)').each(function() {
      $(this).find('td:eq(5)').each(function() {
    	  if($(this).text()!=""){
    		  totalRow2 += parseFloat($(this).text());
    	  }
      });
      $(this).find('td:eq(8)').each(function() {
    	  if($(this).text()!=""){
    		  totalRow3 += parseFloat($(this).text());
    	  }
      });
      $(this).find('td:eq(11)').each(function() {
    	  if($(this).text()!=""){
    		  totalRow4 += parseFloat($(this).text());
    	  }
      });
     /*  $(this).find('td:eq(14)').each(function() {
    	  if($(this.firstChild).val()!=""){
    		  totalRow5 += parseFloat($(this.firstChild).val());
    	  }
      }); */
    });
    $('#total2').val(totalRow2.toFixed(2));
    $('#total3').val(totalRow3.toFixed(2));
    $('#total4').val(totalRow4.toFixed(2));
   /*  $('#total5').val(totalRow5.toFixed(2)); */

  });
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">年度计划任务总工时明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>年度计划任务总工时明细</h2>
	 	</div>
	 	
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	<form action="${pageContext.request.contextPath}/yearPlan/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="content table_box over_auto table_wrap">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">产品或任务名称</th>
						<th rowspan="2" class="info">计量单位</th>
						<th colspan="3" class="info">报价前1年</th>
						<th colspan="3" class="info">报价当年</th>
						<th colspan="3" class="info">单位产品工时审核核定数</th>
						<!-- <th colspan="3" class="info">单位产品工时复审核定数</th> -->
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
						<!-- <th class="info">单位产品定额工时</th>
						<th class="info">投产数量</th>
						<th class="info">工时合计</th> -->
					</tr>
				<c:forEach items="${list}" var="yp" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" name="listYear[${vs.index }].id" value="${yp.id }" />${yp.serialNumber }</td>
						<td class="tc">${yp.projectName }</td>
						<td class="tc">${yp.measuringUnit }</td>
						
						<td class="tc">${yp.oyaHourUnit }</td>
						<td class="tc">${yp.oyaInvestAcount }</td>
						<td class="tc">${yp.oyaHourTotal }</td>
						
						<td class="tc">${yp.newHourUnit }</td>
						<td class="tc">${yp.newInvestAcount }</td>
						<td class="tc">${yp.newHourTotal }</td>
						
						<td class="tc">${yp.approvedHourUnit }</td>
						<td class="tc">${yp.approvedInvestAcount }</td>
						<td class="tc">${yp.approvedHourTotal }</td>
						
						<%-- <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].checkHourUnit'  value='${yp.checkHourUnit }'  onblur="totalHours(this,'1')"></td>
						<td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].checkInvestAcount'  value='${yp.checkInvestAcount }'  onblur="totalHours(this,'2')"></td>
						<td class="tc"><input type='text' class='m0 p0  border0 w100' name='listYear[${vs.index }].checkHourTotal'  value='${yp.checkHourTotal }'  readonly="readonly"></td>
						 --%>
						<td class="tc">${yp.remark }</td>
					</tr>
				</c:forEach>
				<tr id="totalRow">
	              <td colspan="5" class="tc">总计：</td>
	              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td>
	              <td colspan="2"></td>
	              <td class="tc"><input type="text" id="total3" class="border0 tc w50 m0" readonly="readonly"></td>
	              <td colspan="2"></td>
	              <td class="tc"><input type="text" id="total4" class="border0 tc w50 m0" readonly="readonly"></td>
	              <!-- <td colspan="2"></td>
	              <td class="tc"><input type="text" id="total5" class="border0 tc w50 m0" readonly="readonly"></td> -->
	              <td ></td>
	            </tr>
				
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="submit" >下一步</button>
		   </div>
	 	 </div>
	 	 </form>
  </div>
  
  </body>
</html>
