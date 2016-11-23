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
</script>
</head>
<body>
<!-- 表格开始-->  
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
       <!--  <button class="btn btn-windows git tr" onclick="show()">唱标</button> -->
        <div>
        <c:forEach items="${listQuoteList }" var="listQuote" varStatus="vs">
        <h3 class="tc">报价一览表</h3>
        <h4 >投标人全称：${listQuote[0].supplier.supplierName }</h4>
        <table class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">货物名称</th>
		  <th class="info">品牌</th>
		  <th class="info">规格型号</th>
		  <th class="info">计量单位</th>
		  <th class="info">数量</th>
		  <th class="info">单价(含税)</th>
		  <th class="info">金额(含税)</th>
		  <th class="info">交货时间</th>
		  <th class="info">备注</th>
		</tr>
		</thead>
		<c:forEach items="${listQuote }" var="lq" varStatus="vs">
			<tr>
			    <td>${vs.index+1 }</td>
				<td>${lq.projectDetail.goodsName }</td>
				<td>${lq.projectDetail.brand }</td>
				<td>${lq.projectDetail.stand }</td>
				<td>${lq.projectDetail.item }</td>
				<td>${lq.projectDetail.purchaseCount }</td>
				<td>${lq.quotePrice }</td>
				<td>${lq.total }</td>
				<td><fmt:formatDate value="${lq.deliveryTime }" pattern="YYYY-MM-dd" /></td>
				<td>${lq.remark }</td>
			</tr>
		</c:forEach> 
		   <tr>
				<td colspan="2"><b>合计</b></td>
				<td colspan="2">投标总价</td>
				<td colspan="3">${listQuote[0].totalMoneyNames }</td>
				<td>(小写)¥：</td>
				<td colspan="2">${listQuote[0].totalMoney }</td>
			</tr>
        </table>
        </c:forEach>
        </div>
     </div>
   </div>
</body>
</html>
