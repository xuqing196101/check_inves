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
<div style="width:100%;margin:auto;">
	<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
		<div align="center" style="margin-top: 10px!important;text-align: center;">
	   		<h2>${project.name}(${pack.name})</h2>
	   		<h2>投标报价一览表</h2>
	   	</div>
		<table align="center" style="width:95%;border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
			<thead>
				<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
					<th width="50" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">序号</th>
					<th width="200" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">供应商名称</th>
					<th width="100" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">总价(万元)</th>
					<th width="150" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">交货期限</th>
			    </tr>
			</thead>
			<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
				    <td width="50" style="border: 1px solid #ddd;padding: 5px 10px;">${vs.index+1 }</td>
				    <td width="200" style="border: 1px solid #ddd;padding: 5px 10px;">${treemapValue.suppliers.supplierName}</td>
				    <td width="100" style="border: 1px solid #ddd;padding: 5px 10px;text-align: right;">${treemapValue.total}</td>
				    <td width="150" style="border: 1px solid #ddd;padding: 5px 10px;">${treemapValue.deliveryTime }</td>
			    </tr>
			</c:forEach>
		</table>
	</c:forEach>
</div>
</body>
</html>
