<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="../../../../../common.jsp"%>
    
    <title>期间费用明细</title>
	

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/manufacturingCost/userGetAllCheck.do?productId="+proId;
}

$(document).ready(function() {
    var totalRow = 0;
    var totalRow2 = 0;
    var totalRow3 = 0;
    var totalRow0 = 0;
    var totalRow4 = 0;
    $('#table1 tr:not(:last)').each(function() {
    	 $(this).find('td:eq(2)').each(function() {
       	  if($(this).text()!=""){
               totalRow0 += parseFloat($(this).text());
       	  }
         });
         $(this).find('td:eq(3)').each(function() {
       	  if($(this).text()!=""){
                 totalRow += parseFloat($(this).text());
         	  }
         });
         $(this).find('td:eq(4)').each(function() {
       	  if($(this).text()!=""){
                 totalRow2 += parseFloat($(this).text());
         	  }
         });
         $(this).find('td:eq(5)').each(function() {
       	  if($(this).text()!=""){
                 totalRow3 += parseFloat($(this).text());
         	  }
         });
         /* $(this).find('td:eq(6)').each(function() {
          	  if($(this.firstChild).val()!=""){
                    totalRow4 += parseFloat($(this.firstChild).val());
            	  }
            }); */
      
    });
    $('#total0').val(totalRow0.toFixed(2));
    $('#total').val(totalRow.toFixed(2));
    $('#total2').val(totalRow2.toFixed(2));
    $('#total3').val(totalRow3.toFixed(2));
    /* $('#total4').val(totalRow4.toFixed(2)); */
  });
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
				<li><a href="javascript:void(0)">期间费用明细</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>期间费用明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
		<form action="${pageContext.request.contextPath}/periodCost/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table id="table1" class="table table-bordered table-condensed">
					<tr>
						<th class="info w50">序号</th>
						<th class="info" width="25%">项目名称</th>
						<th class="info" width="10%">报价前2年</th>
						<th class="info" width="10%">报价前1年</th>
						<th class="info" width="10%">报价当年</th>
						<th class="info" width="10%">审核核准数</th>
						<!-- <th class="info">复审核准数</th> -->
						<th class="info">备   注</th>
					</tr>
				<c:forEach items="${list}" var="pc" varStatus="vs">
					<tr>
						<td class="tc"><input  type="hidden" value="${pc.id }" name="listPerio[${vs.index }].id" />${pc.serialNumber }</td>
						<td class="tl">${pc.projectName }</td>
						<td class="tr">${pc.tyaQuoteprice }</td>
						<td class="tr">${pc.oyaQuoteprice }</td>
						<td class="tr">${pc.newQuoteprice }</td>
						<td class="tr">${pc.auditApproval }</td>
						<%-- <td class="tc"><input type="text" class='m0 p0  border0 w100'  value='${pc.checkApproval }' name="listPerio[${vs.index }].checkApproval"></td> --%>
						<td class="tl">${pc.remark }</td>
					</tr>
				</c:forEach>
				<tr>
              <td class="tc" colspan="2">总计：</td>
              <td class="tc"><input type="text" id="total0" class="border0 tc w100 mb0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total" class="border0 tc w100 mb0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w100 mb0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total3" class="border0 tc w100 mb0" readonly="readonly"></td>
              <!-- <td class="tc"><input type="text" id="total4" class="border0 tc w100 mb0" readonly="readonly"></td> -->
              <td></td>
            </tr>
			</table>
		</div>
		<div class="mt20 tc col-md-12 col-sm-12 col-xs-12">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="submit">下一步</button>
		</div>
	 </form> 
  </div>
  
  </body>
</html>
