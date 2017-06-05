<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="../../../../../common.jsp"%>
    
    <title>专项费用明细</title>
	
<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/outsourcingCon/userGetAllCheck.do?productId="+proId;
}


jQuery.fn.rowspan = function(colIdx) { //把td相同的数据行合并
	  return this.each(function(){
  	  var that;
  	  $('tr', this).each(function(row) {
	    	  $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
		    	  if (that!=null && $(this).html() == $(that).html()) {
			    	  rowspan = $(that).attr("rowSpan");
			    	  if (rowspan == undefined) {
			    	  	$(that).attr("rowSpan",1);
			    	  	rowspan = $(that).attr("rowSpan");
			    	  }
			    	  rowspan = Number(rowspan)+1;
			    	  $(that).attr("rowSpan",rowspan);
			    	  $(this).hide(); 
			    	  } else {
			    	  	that = this;
			    	  }
		    	  });
	    	  });
  	  });
	 }
function moneys(obj){
	var money=0;
	var prevMoney=0;
	if($(obj).val()!=""){
		money=parseFloat($(obj).val());
	}
	if($(obj).parent().prev().prev().prev().text()!=""){
		prevMoney=parseFloat($(obj).parent().prev().prev().prev().text());
	}
	$(obj).parent().next().children(":first").val((prevMoney-money).toFixed(2));
}
$(document).ready(function() {
	$("#table1").rowspan(1);
	var totalRow = 0;
	  var total = 0;
	  var total3 = 0;
	  var total4 = 0;
	  $('#table1 tr:not(:last)').each(function() {
	    $(this).find('td:eq(9)').each(function() {
	  	  if($(this).text()!=""){
	  		  totalRow += parseFloat($(this).text());
	  	  }
	      
	    });
	    $(this).find('td:eq(10)').each(function() {
	  	  if($(this).text()!=""){
	  	    total += parseFloat($(this).text());
	  	  }
	      });
	    $(this).find('td:eq(11)').each(function() {
	    	  if($(this).text()!=""){
	    		  total3 += parseFloat($(this).text());
	    	  }
	        });
	    /* $(this).find('td:eq(13)').each(function() {
	    	  if($(this.firstChild).val()!=""){
	    		  total4 += parseFloat($(this.firstChild).val());
	    	  }
	        }); */
	  });
	  $('#total').text(totalRow.toFixed(2));
	  $('#total2').text(total.toFixed(2));
	  $('#total3').text(total3.toFixed(2));
	 /*  $('#total5').text(total4.toFixed(2));
	  $('#total6').text((total-total4).toFixed(2)); */
	  $('#total4').text((parseFloat($('#total2').text())-parseFloat($('#total3').text())).toFixed(2));
})
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
			  </li>
			  <li>
				  <a href="javascript:void(0);"> 保障作业</a>
			  </li>
			  <li>
				  <a href="javascript:void(0);"> 单一来源审价</a>
			  </li>
			  <li>
				  <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/checkList.html')">审价人员复审</a>
			  </li>
			  <li><a href="javascript:void(0)">专项费用明细</a></li>
		  </ul>
		  <div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>专项费用明细</h2>
	 	</div>
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="${pageContext.request.contextPath}/specialCost/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="col-md-12 col-xs-12 col-sm-12">
			
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th class="info">序号</th>
						<th class="info">项目名称</th>
						<th class="info">项目明细</th>
						<th class="info">名称</th>
						<th class="info">规格型号</th>
						<th class="info">计量单位</th>
						<th class="info">数量(消耗使用)</th>
						<th class="info">单价</th>
						<th class="info">金额</th>
						<th class="info">分摊数量</th>
						<th class="info">单位产品分摊额</th>
						<th class="info">核准金额</th>
						<th class="info">核减金额</th>
						<!-- <th class="info">复核准金额</th>
						<th class="info">复核减金额</th> -->
						<th class="info">备   注</th>
					</tr>
				<c:set value="" var="id"></c:set>
	            <c:set value="" var="value"></c:set>
	            <c:set value="" var="num"></c:set>
				<c:forEach items="${list}" var="sc" varStatus="vs">
					<c:if test="${sc.parentId=='0'}">
		              <c:set value="${sc.projectName }" var="value"></c:set>
		              <c:set value="${sc.id }" var="id"></c:set>
		              <c:set value="${sc.serialNumber }" var="num"></c:set>
	                </c:if>
	                <c:if test="${sc.parentId!='0'}">
					<tr>
						<td class="tc"><input type="hidden" value="${sc.id }" name="listSpec[${vs.index }].id"  />${vs.index+1 }</td>
						<td class="tl">${value }</td>
						<td class="tl">${sc.productDetal }</td>
						<td class="tl">${sc.name }</td>
						<td class="tl">${sc.norm }</td>
						<td class="tc">${sc.measuringUnit }</td>
						<td class="tc">${sc.amount }</td>
						<td class="tr">${sc.price }</td>
						<td class="tr">${sc.money }</td>
						<td class="tc">${sc.proportionAmout }</td>
						<td class="tr">${sc.proportionPrice }</td>
						<td class="tr">${sc.approvedMoney }</td>
						<td class="tr">${sc.subtractMoney }</td>
						<%-- <td class="tc"><input type="text" class='m0 p0  border0 w80' value='${sc.checkApprovedMoney }' name="listSpec[${vs.index }].checkApprovedMoney" onblur="moneys(this)"></td>
						<td class="tc"><input type="text" class='m0 p0  border0 w80' value='${sc.checkMoney }' name="listSpec[${vs.index }].checkMoney" readonly="readonly"></td> --%>
						<td class="tl">${sc.remark }</td>
					</tr>
					</c:if>
				</c:forEach>
				<tr id="totalRow">
	              <td class="tc" colspan="9">总计：</td>
	              <td class="tc" id="total" ></td>
	              <td id="total2" class="tr"></td>
	              <td id="total3" class="tr"></td>
	              <td id="total4" class="tr"></td>
	             <!--  <td id="total5"></td>
	              <td id="total6"></td> -->
	              <td></td>
            	</tr>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="submit">下一步</button>
		   </div>
	 	 </div>
	 	</form> 
  </div>
  
  </body>
</html>
