<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="../../../../../common.jsp"%>
    
    <title>外协加工件消耗定额明细</title>

<script type="text/javascript">
function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/outproductCon/userGetAllCheck.do?productId="+proId;
}


$(document).ready(function() {
    var totalRow = 0;
    var totalRow1 = 0;
    var totalRow2 = 0;
    $('#table1 tr:not(:last)').each(function() {
      $(this).find('td:eq(8)').each(function() {
          if($(this).text()!=''){
        	  totalRow += parseFloat($(this).text());
          }
      });
      $(this).find('td:eq(11)').each(function() {
          if($(this).text()!=''){
        	  totalRow1 += parseFloat($(this).text());
          }
      });
      /* $(this).find('td:eq(13)').each(function() {
          if($(this.firstChild).val()!=''){
        	  totalRow2 += parseFloat($(this.firstChild).val());
          }
      }); */
      
      
    });
    $('#total').text(totalRow.toFixed(2));
    $('#total1').text(totalRow1.toFixed(2));
   /*  $('#total3').text(totalRow2.toFixed(2)); */
    $('#total2').text((parseFloat($('#total').text())-parseFloat($('#total1').text())).toFixed(2));
  });
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">外协加工件消耗定额明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>外协加工件消耗定额明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="${pageContext.request.contextPath}/outsourcingCon/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">外协加工工件名称</th>
						<th rowspan="2" class="info">规格型号</th>
						<th rowspan="2" class="info">图纸位置号(代号)</th>
						<th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
						<th colspan="3" class="info">消耗定额审核核准数（含税金额）</th>
						<th rowspan="2" class="info">核减金额</th>
						<!-- <th rowspan="2" class="info">复核减金额</th> -->
						<th rowspan="2" class="info">供货单位</th>
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">数量</th>
						<th class="info">单件重</th>
						<th class="info">重量小计</th>
						<th class="info">单价</th>
						<th class="info">金额</th>
						<th class="info">数量</th>
						<th class="info">单价</th>
						<th class="info">金额</th>
					</tr>
				<c:forEach items="${list}" var="out" varStatus="vs">
					<tr>
						<td>${vs.index+1 }<input type="hidden" name="listOutSou[${vs.index+1 }].id" value="${out.id }" /></td>
						<td class="tc">${out.outsourcingName }</td>
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
						<td class="tc">${out.subtractMoney }</td>
						<%-- <td class="tc"><input type="text" value='${out.checkMoney }' class='m0 p0  border0 w80 tr' name="listOutSou[${vs.index+1 }].checkMoney"></td> --%>
						<td class="tc">${out.supplyUnit }</td>
						<td class="tc">${out.remark }</td>
					</tr>
				</c:forEach>
				<tr id="totalRow">
	              <td class="tc" colspan="4">总计：</td>
	              <td colspan="4"></td>
	              <td class="tr" id="total"></td>
	              <td colspan="2"></td>
	              <td class="tr" id="total1"></td>
	              <td class="tr" id="total2"></td>
	             <!--  <td class="tr" id="total3"></td> -->
	              <td colspan="2"></td>
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
