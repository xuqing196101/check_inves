<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@page contentType="application/vnd.ms-word;charset=GBK"%>
<%
String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

<base href="<%=basePath%>">

<title>My JSP 'creatWord.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">

<meta http-equiv="expires" content="0">   

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<meta http-equiv="description" content="This is my page">

<!--

<link rel="stylesheet" type="text/css" href="styles.css">

-->

<%

String fileName = "竞价结果信息表.doc";

//对中文文件名编码 

fileName = URLEncoder.encode(fileName, "utf-8"); 

byte[] yte = fileName.getBytes("GB2312"); 

String unicoStr = new String(yte, "utf-8");

response.setHeader("Content-disposition", "attachment; filename=" + unicoStr);     

%>
<style type="text/css">
	td{ text-align:center;}
	.table-a table{border:1px solid #F00} 
</style>
</head>

<body>
<div class="table-a" style="width:85%;margin:auto;">
	<table class="table table-bordered mt10">
	    <tbody>
	      <tr>
			<td class="tc" colspan="6" align="center"><br/><b>${ obProject.name }</b><br/>竞价结果信息表<br/><br/></td>
		  </tr> 
		  <tr>
		    <td class="info"><b>竞价项目编号</b></td>
		    <td>${ obProject.projectNumber }</td>
		    <td class="info"><b>竞价项目名称</b></td>
		    <td>${ obProject.name }</td>
		  </tr>
		  <tr>
		    <td class="info"><b>交货地点</b></td>
		    <td>${ obProject.deliveryAddress }</td>
		     <td class="info"><b>交货时间</b></td>
		    <td><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  </tr>
		  <tr>
		    <td class="info"><b>运杂费</b></td>
		    <td>${ transportFees }</td>
		    <c:if test="">
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ empty obProject.transportFeesPrice }">
			    <td colspan="2"></td>
		    </c:if>
		    <c:if test="${ !empty obProject.transportFeesPrice }">
			    <td class="info"><b>运杂费用（元）</b></td>
			    <td>${obProject.transportFeesPrice}</td>
		    </c:if>
		  </tr>
		  <tr>
		    <td class="info"><b>需求单位</b></td>
		    <td>${ demandUnit }</td>
		    <td class="info"><b>采购机构</b></td>
		    <td>${ orgName }</td>
		  </tr>
		  <tr>
		    <td class="info"><b>需求联系人</b></td>
		    <td>${ obProject.contactName }</td>
		    <td class="info"><b>采购联系人：</b></td>
		    <td>${ obProject.orgContactName }</td>
		  </tr>
		  <tr>
		    <td class="info"><b>需求联系电话</b></td>
		    <td>${ obProject.contactTel }</td>
		    <td class="info"><b>采购联系电话</b></td>
		    <td>${ obProject.orgContactTel }</td>
		  </tr>
		</tbody>
	</table>
	
	<c:forEach items="${listres}" var="supplier" varStatus="pi">
	 <ul class="ul_list">
	  <li class="col-md-3 col-sm-6 col-xs-12">
	  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">供应商名称：${supplier.supplier.supplierName}</span>
	   </div>
	  </li>
	   <li class="col-md-3 col-sm-6 col-xs-12">
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  <span class="fl block">排名：</span><span>第${supplier.ranking}名</span>
	  </div>
	  </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	  		<span class="fl block">成交比例：</span><span>
	  		<c:if test="${supplier.status == -1 || supplier.status == 0}">0%</c:if>
	  		<c:if test="${supplier.status == 1 || supplier.status == 2 }">${supplier.proportion}%</c:if></span>
	  	</div>
	  </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
  			<div
  				class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
  				<span class="fl block">状态：</span>
  				<span>
  					
					<c:if test="${supplier.status == -1 && supplier.proportion == 0}">未中标</c:if>
					<c:if test="${supplier.status == -1 && supplier.proportion != 0}">未确认，已放弃</c:if>
					<c:if test="${supplier.status == 0}">未接受，已放弃</c:if>
					<c:if test="${supplier.status == 1 || supplier.status == 2 }">已接受</c:if>
				</span>
  			</div>
	   </li>

    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info" width="30%">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交单价（元）</th>
		  <th class="info">成交总价（万元）</th>
		</tr>
		</thead>
		
		<!-- 成交的 -->
		<c:if test="${supplier.status == 1 || supplier.status == 2}">
		<c:forEach items="${supplier.obResultSubtabulation}" var="product">
			<c:if test="${product.supplierId == supplier.supplierId}">
				<c:set value="${ total + product.totalMoney }" var = "total" ></c:set>
			</c:if>
		</c:forEach>
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc">${total }</td>
			</tr>
			<c:forEach items="${supplier.obResultSubtabulation}" var="product" varStatus="va">
				<c:if test="${product.supplierId == supplier.supplierId}">
					<tr>
						<td class="tc">${va.index+1 }</td>
			  			<td class="tc">${product.product.name }</td>
			  			<td class="tc">${product.resultNumber }</td>
						<td class="tc">
							<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
						</td>
			  			<td class="tc">
			  				<fmt:formatNumber value='${product.dealMoney }' pattern='#,##,###.00'/>
			  			</td>
			  			<td class="tc">${product.totalMoney }</td>
			  		</tr>
				</c:if>
			</c:forEach>
			<c:set value="0" var = "total" ></c:set>
		</c:if>
		
		<!-- 放弃的 -->
		<c:if test="${supplier.status == -1}">
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc">${total }</td>
			</tr>
			<c:forEach items="${supplier.OBResultsInfo}" var="product" varStatus="va">
				<c:if test="${product.supplierId == supplier.supplierId}">
				<tr>
					<td class="tc">${va.index+1 }</td>
			  		<td class="tc">${product.obProduct.name }</td>
			  		<td class="tc">0</td>
					<td class="tc">
						<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
					</td>
			  		<td class="tc"></td>
			  		<td class="tc"></td>
			  	</tr>
				</c:if>
			</c:forEach>
		</c:if>
		
		<!--  -->
		<c:if test="${supplier.status == 0}">
			<tr>
			  <td class="tc"></td>
			  <td class="tc" colspan="4">合计</td>
			  <td class="tc"></td>
			</tr>
			<c:forEach items="${supplier.OBResultsInfo}" var="product" varStatus="va">
				<c:if test="${product.supplierId == supplier.supplierId}">
				<tr>
					<td class="tc">${va.index+1 }</td>
			  		<td class="tc">${product.obProduct.name }</td>
			  		<td class="tc">0</td>
					<td class="tc">
						<fmt:formatNumber value='${product.myOfferMoney }' pattern='#,##,###.00'/>
					</td>
			  		<td class="tc"></td>
			  		<td class="tc">0</td>
			  	</tr>
				</c:if>
			</c:forEach>
		</c:if>
	</table>
	</ul>
	</c:forEach>
	
	<div align="center">
		<span><font size="3">供应商确认中标比例为<b>${countProportion }%</b>，未中标比例为<b>${100 - countProportion }%</b>.</font></span>
	</div>
	
	</div>
</body>
</html>