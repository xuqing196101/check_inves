<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@page contentType="application/vnd.ms-word;charset=UTF-8"%>

<%

String path = request.getContextPath();

String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

<base href="${pageContext.request.contextPath}/">

<title>My JSP 'creatWord.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">

<meta http-equiv="cache-control" content="no-cache">

<meta http-equiv="expires" content="0">   

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">

<meta http-equiv="description" content="This is my page">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<!--

<link rel="stylesheet" type="text/css" href="styles.css">

-->

<%

String fileName = "投标报价一览表.doc"; 

//对中文文件名编码 

fileName = java.net.URLEncoder.encode(fileName, "UTF-8"); 
System.out.println(fileName);
byte[] yte = fileName.getBytes("iso8859-1"); 

String unicoStr = new String(yte, "utf-8");

response.setHeader("Content-disposition", "attachment; filename=" + unicoStr);     

%>
</head>
<body>
<div style="width:85%;margin:auto;">
	<c:forEach items="${listPackage}" var="listPackage" varStatus="vs">
		<div align="center" style="margin-top: 10px!important;text-align: center;">
	   		<h2>${project.name}(${pack.name})</h2>
	   	</div>
		<c:forEach items="${listPackage.suppliers}" var="suList" varStatus="vs">
			<div align="center" style="margin-top: 10px!important;text-align: center;">
		   		<h2>投标报价一览表</h2>
		   	</div>
			<h3>投标人全称：${suList.supplierName}</h3>
			<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
				<thead>
					<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
						<th width="30" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">序号</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">物资名称</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">规格型号</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">质量技术标准</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">计量单位</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">采购数量</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">单价（元）</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">小计</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">交货时间</th>
						<th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">备注</th>
					</tr>
				</thead>
				<c:set value="0" var="totalPrice"></c:set>
				<c:forEach items="${suList.quoteList}" var="quoteList" varStatus="vs">
					<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
						<td width="30" style="border: 1px solid #ddd;padding: 5px 10px;">${vs.index + 1}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.advancedDetail.goodsName}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.advancedDetail.stand}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.advancedDetail.qualitStand}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.advancedDetail.item}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.advancedDetail.purchaseCount}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.quotePrice}</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.total}</td>
						<c:set value="${totalPrice + quoteList.total}" var="totalPrice"></c:set>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.deliveryTime }</td>
						<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${quoteList.remark}</td>
					</tr>
				</c:forEach>
				<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
					<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;" colspan="2"><b>总金额(元):</b></td>
					<td width="120" style="border: 1px solid #ddd;padding: 5px 10px;" colspan="8">${totalPrice}</td>
				</tr>
			</table>
		</c:forEach>
	</c:forEach>
</div>
</body>
</html>
