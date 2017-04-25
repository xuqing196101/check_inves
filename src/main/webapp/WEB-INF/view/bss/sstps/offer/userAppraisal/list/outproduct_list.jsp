<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
  <head>
    <%@ include file="../../../../../common.jsp"%>
    
    <title>外购成品件消耗定额明细</title>

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/accessoriesCon/userGetAll.html?productId="+proId;
}

function workWeightTotals(obj,type){
    if(type==2){
        var num=0;
       	var price=0;
       	var prev=0;
   		if($(obj).parent().prev().children(":first").val()!=""){
   			num=parseFloat($(obj).parent().prev().children(":first").val());
   		 }
       	 if($(obj).val()!=""){
       		 price=parseFloat($(obj).val());
       	 }
       	 if($(obj).parent().prev().prev().text()!=""){
       		 prev=parseFloat($(obj).parent().prev().prev().text());
       	 }
       	  $(obj).parent().next().children(":first").val((num*price).toFixed(2));
       	$(obj).parent().next().next().children(":first").val((prev-num*price).toFixed(2));
    }else if(type==3){
    	var num=0;
       	var price=0;
       	var prev=0;
       	if($(obj).val()!=""){
       		num =parseFloat($(obj).val());
         }
       	if($(obj).parent().next().children(":first").val()){
       		price=parseFloat($(obj).parent().next().children(":first").val());
       	}
        if($(obj).parent().prev().text()!=""){
      		 prev=parseFloat($(obj).parent().prev().text());
      	 }
       	$(obj).parent().next().next().children(":first").val((num*price).toFixed(2));
       	$(obj).parent().next().next().next().children(":first").val((prev-num*price).toFixed(2));
    }
}
$(document).ready(function() {
    var totalRow = 0;
    var totalRow1 = 0;
    $('#table1 tr:not(:last)').each(function() {
      $(this).find('td:eq(8)').each(function() {
          if($(this).text()!=''){
        	  totalRow += parseFloat($(this).text());
          }
      });
      $(this).find('td:eq(11)').each(function() {
          if($(this.firstChild).val()!=''){
        	  totalRow1 += parseFloat($(this.firstChild).val());
          }
      });
      
      
    });
    $('#total').text(totalRow.toFixed(2));
    $('#total1').text(totalRow1.toFixed(2));
    $('#total2').text((parseFloat($('#total').text())-parseFloat($('#total1').text())).toFixed(2));
  });
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">单一来源审价</a></li><li><a href="javascript:void(0)">审价人员审价</a></li><li><a href="javascript:void(0)">外购成品件消耗定额明细审核</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>外购成品件消耗定额明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	<form action="${pageContext.request.contextPath}/outproductCon/userUpdate.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="content table_box over_auto table_wrap">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">成品件名称</th>
						<th rowspan="2" class="info">规格型号</th>
						<th rowspan="2" class="info">图纸位置号(代号)</th>
						<th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
						<th colspan="3" class="info">消耗定额审核核准数（含税金额）</th>
						<th rowspan="2" class="info">核减金额</th>
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
						<td>${vs.index+1 }<input type="hidden" name="listOutPro[${vs.index }].id" value="${out.id }" /></td>
						<td class="tc">${out.finishedName }</td>
						<td class="tc">${out.norm }</td>
						<td class="tc">${out.paperCode }</td>
						<td class="tc">${out.workAmout }</td>
						<td class="tc">${out.workWeight }</td>
						<td class="tc">${out.workWeightTotal }</td>
						<td class="tc">${out.workPrice }</td>
						<td class="tc">${out.workMoney }</td>
						<td class="tc"><input type='text' class='m0 p0  border0 w50' name='listOutPro[${vs.index}].consumeAmout'  value='${out.consumeAmout }'  onblur='workWeightTotals(this,"3");'></td>
						<td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listOutPro[${vs.index}].consumePrice'  value='${out.consumePrice }'  onblur='workWeightTotals(this,"2");'></td>
						<td class="tc"><input type='text' class='m0 p0  border0 w80 tr' name='listOutPro[${vs.index}].consumeMoney'  value='${out.consumeMoney }'  readonly="readonly"></td>
						<td class="tc"><input type="text" class='m0 p0  border0 w80 tr' value='${out.subtractMoney }' name="listOutPro[${vs.index }].subtractMoney"></td>
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
