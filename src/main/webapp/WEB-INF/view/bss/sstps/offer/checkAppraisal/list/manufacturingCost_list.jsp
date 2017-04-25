<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="../../../../../common.jsp"%>
    
    <title>制造费用明细</title>
	

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/wagesPayable/userGetAllCheck.do?productId="+proId;
}

$(function() {
	var totalRow0 = 0;
    var totalRow1 = 0;
    var totalRow2 = 0;
    var totalRow3 = 0;
    var totalRow4 = 0;
    $("#table1 tr:not(:last)").each(function() {
      $(this).find("td:eq(2)").each(function() {
    	  if($(this).text()!=""){
    		  totalRow0 += parseFloat($(this).text());
    	  }
      });
      $(this).find("td:eq(3)").each(function() {
    	  if($(this).text()!=""){
    		  totalRow1 += parseFloat($(this).text());
    	  }
      });
      $(this).find("td:eq(4)").each(function() {
    	  if($(this).text()!=""){
    		  totalRow2 += parseFloat($(this).text());
    	  }
      });
      $(this).find("td:eq(5)").each(function() {
    	  if($(this).text()!=""){
    		  totalRow3 += parseFloat($(this).text());
    	  }
      });
      /* $(this).find("td:eq(6)").each(function() {
    	  if($(this.firstChild).val()!=""){
    		  totalRow4 += parseFloat($(this.firstChild).val());
    	  }
      }); */
    });

      $("#total0").val(totalRow0.toFixed(2));
      $("#total1").val(totalRow1.toFixed(2));
      $("#total2").val(totalRow2.toFixed(2));
      $("#total3").val(totalRow3.toFixed(2));
     /*  $("#total4").val(totalRow4.toFixed(2)); */
  });
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">制造费用明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>制造费用明细</h2>
	 	</div>
	 	
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="${pageContext.request.contextPath}/manufacturingCost/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th class="info">序号</th>
						<th class="info">项目名称</th>
						<th class="info">报价前2年</th>
						<th class="info">报价前1年</th>
						<th class="info">报价当年</th>
						<th class="info">审核核准数</th>
						<!-- <th class="info">复审核准数</th> -->
						<th class="info">备   注</th>
					</tr>
				<c:forEach items="${list}" var="mc" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" value="${mc.id }" name="listManu[${vs.index }].id" />${vs.index+1 }</td>
						<td class="tc">${mc.projectName }</td>
						<td class="tc">${mc.tyaQuoteprice }</td>
						<td class="tc">${mc.oyaQuoteprice }</td>
						<td class="tc">${mc.newQuoteprice }</td>
						<td class="tc">${mc.auditApproval }</td>
						<%-- <td class="tc"><input type="text"  class='m0 p0  border0 w100' value='${mc.checkApproval }' name="listManu[${vs.index }].checkApproval"></td> --%>
						<td class="tc">${mc.remark }</td>
					</tr>
				</c:forEach>
				<tr>
              <td class="tc" colspan="2">总计：</td>
              <td class="tc"><input type="text" id="total0" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total1" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total3" class="border0 tc w50 m0" readonly="readonly"></td>
              <!-- <td class="tc"><input type="text" id="total4" class="border0 tc w50 m0" readonly="readonly"></td> -->
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
