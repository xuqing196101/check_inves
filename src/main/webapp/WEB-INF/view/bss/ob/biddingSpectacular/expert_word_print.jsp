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
	<table>
		<tbody>
			<tr>
				<td colspan="6" align="center"><br/>${ obProject.name }<br/>竞价结果信息表<br/><br/></td>
			</tr> 
			<tr>
				<td>项目名称</td>
				<td colspan="5">${ obProject.name }</td>
			</tr>
			<c:forEach items="${ obProductInfoList }" var="pInfo" varStatus="vs">
				<tr>
				  <c:if test="${ vs.index==0 }">
					  <td rowspan="${ obProductInfoList.size() }" class="tc" class="info">产品名称</td>
				  </c:if>
				  <td>${ pInfo.obProduct.name }</td>
				  <td>数量</td>
				  <td>${ pInfo.purchaseCount }</td>
				  <td>预算</td>
				  <td>${ pInfo.limitedPrice }</td>
				</tr>
			</c:forEach>
			<tr>
				<td>需求部门</td>
				<td>${ orgName }</td>
			</tr>
			<tr>
				<td>运杂费</td>
				<td>${ obProject.transportFees }</td>
			</tr> 
			<tr>
				<td>交货地点</td>
				<td>${ obProject.deliveryAddress }</td>
				<td>交货时间</td>
				<td><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:ss:mm"/></td>
			</tr> 
			<tr>
				<td colspan="6" style="border: none;" >
					<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
						<tbody>
							<tr>
								<td>名次</td>
								<td>供应商名称</td>
								<td>自报单价（元）</td>
		  						<td>成交单价（元）</td>
		  						<td>成交数量</td>
		  						<td>成交总价（元）</td>
								<td>操作状态</td>
							</tr>
							<c:forEach items="${info.list }" var="result" varStatus="vs">
								<tr>
							  		<td>${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
							  		<td>${result.supplier.supplier.supplierName }</td>
							  		<td>${result.countOfferPrice}</td>
							  		<td>${countOfferPricebyOne}</td>
							  		<td>${result.countresultCount}</td>
							  		<td>${result.countTotalAmount }</td>
							  		<td>
							  			<c:if test="${result.status == 0}">未确认</c:if>
							  			<c:if test="${result.status == 1}">已确认</c:if>
							  		</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="6">供应商确认中标数量总量为${chengjiao}，预定采购数量为${count}，剩余采购数量为${count-chengjiao}.</td>
			</tr>
		</tbody>
		</table>
	</div>
</body>
</html>