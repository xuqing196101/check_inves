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

<base href="${pageContext.request.contextPath}/">

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

String fileName = "评审结果.doc"; 

//对中文文件名编码 

fileName = URLEncoder.encode(fileName, "utf-8"); 

byte[] yte = fileName.getBytes("GB2312"); 

String unicoStr = new String(yte, "utf-8");

response.setHeader("Content-disposition", "attachment; filename=" + unicoStr);     

%>

</head>

<body>

<div style="width:85%;margin:auto;">
    <div style="display: block;background: #fff;padding: 1px 10px;margin: 10px 0 10px 20px;border-left: 4px solid #2c9fa6;">
   		<h2>检查数据</h2>
   	</div>
   	<div align="center" style="margin-top: 10px!important;text-align: center;">
   		<h2>${project.name}--${pack.name}</h2>
   	</div>
  	<h4>评审人员：${expert.relName}</h4>
  	<c:forEach items="${extension}" var="extensions" >
  	<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
  	<thead>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">资格性和符合性检查项</th>
   		  <c:set var="suppliers" value="0" />
   		  <c:forEach items="${extensions.supplierList}" var="supplier" varStatus="vs">
   		  	<c:if test="${fn:contains(supplier.packages,extensions.packageId)}">
	   		    <c:set var="suppliers" value="${suppliers+1}" />
	   		    <th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
	   		      ${supplier.suppliers.supplierName}
	   		    </th>
   		    </c:if>
   		  </c:forEach>
   		  <c:if test="${fn:length(extensions.supplierList)<4}">
		        <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
		            </th>
		         </c:forEach>
		  </c:if>
   		  </tr>
   		</thead>
   		<c:forEach items="${dds}" var="d">
   			<tr  style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   			  <td width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" colspan="${suppliers+1}">
   				<b>${d.name}</b>
   			  </td>
   			</tr>
   			<c:forEach items="${extensions.firstAuditList }" var="first" varStatus="vs">
		      	<c:if test="${first.kind == d.id}">
			      	<tr>
			      	  <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${first.name}</td>
			      	  <c:forEach items="${extensions.supplierList}" var="supplier" varStatus="v">
			      	  	 <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
			      	  
	                   <c:if test="${fn:contains(supplier.packages,extensions.packageId)}">
			                    <c:forEach items="${reviewFirstAuditList }" var="r" >
			                      <c:if test="${isSubmit == 0 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId}">暂无</c:if>
			                      <c:if test="${isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId && r.isPass==0 }">合格</c:if>
			                      <c:if test="${isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId && r.isPass==1 }">
			                    	  <div class="red">不合格</div>
			                    	  理由：${r.rejectReason}
			                      </c:if>
			                    </c:forEach>
		                </c:if> 
		                </td>
		              </c:forEach>
		              <c:if test="${fn:length(extensions.supplierList)<4}">
		                 <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		                 <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
		                 </td>
		                 </c:forEach>
		              </c:if>
		              </td>
			      	</tr>
		      	</c:if>
            </c:forEach>
   		</c:forEach>
   	</table>
  	
  	</c:forEach>
   <%--  <table style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
   		<thead>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">资格性和符合性检查项</th>
   		  <c:set var="suppliers" value="0" />
   		  <c:forEach items="${extension.supplierList}" var="supplier" varStatus="vs">
   		  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
	   		    <c:set var="suppliers" value="${suppliers+1}" />
	   		    <th width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
	   		      ${supplier.suppliers.supplierName}+${suppliers}
	   		    </th>
   		    </c:if>
   		  </c:forEach>
   		  </tr>
   		</thead>
   		<c:forEach items="${dds}" var="d">
   			<tr  style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   			  <td width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" colspan="${suppliers+1}">
   				<b>${d.name}</b>
   			  </td>
   			</tr>
   			<c:forEach items="${extension.firstAuditList }" var="first" varStatus="vs">
		      	<c:if test="${first.kind == d.id}">
			      	<tr>
			      	  <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${first.name}</td>
			      	  <c:forEach items="${extension.supplierList}" var="supplier" varStatus="v">
			      	  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
			                <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
			                    <c:forEach items="${reviewFirstAuditList }" var="r" >
			                      <c:if test="${isSubmit == 0 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId}">暂无</c:if>
			                      <c:if test="${isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId && r.isPass==0 }">合格</c:if>
			                      <c:if test="${isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expertId && r.isPass==1 }">
			                    	  <div class="red">不合格</div>
			                    	  理由：${r.rejectReason}
			                      </c:if>
			                    </c:forEach>
			                </td>
		                </c:if>
		              </c:forEach>
			      	</tr>
		      	</c:if>
            </c:forEach>
   		</c:forEach>
   </table> --%>
   <h4>专家签名：</h4>
</div>

</body>

</html>