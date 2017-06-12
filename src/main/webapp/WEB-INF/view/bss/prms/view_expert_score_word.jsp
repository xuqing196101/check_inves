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
   		 <h2>${expert.relName}评审结果</h2>
   	</div>
  	<c:forEach items="${supplierList}" var="extensions" >
  	<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" >
  	<thead>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <th width="120" rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">评审项目</th>
   		  <th width="120" rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">评审指标</th>
   		  <th width="120" rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">标准分值</th>
   		  <c:forEach items="${extensions.supplierList}" var="supplier">
		       <th style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">${supplier.suppliers.supplierName}</th>
		   </c:forEach>
		   <c:if test="${fn:length(extensions.supplierList)<2}">
		        <c:forEach begin="1" end="${2-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th width="120"  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
		            </th>
		         </c:forEach>
		  </c:if>
   		  </tr>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <c:forEach items="${extensions.supplierList}" var="supplier">
		   	   <th style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">得分</th>
	   	  </c:forEach>
	   	  <c:if test="${fn:length(extensions.supplierList)<2}">
		        <c:forEach begin="1" end="${2-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
		            </th>
		         </c:forEach>
		  </c:if>
   		  </tr>
   		  
   		  
   		</thead>
   		<c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			    	     <c:if test="${score.count == 0}"></c:if>
			    	     <c:if test="${score.count != 0}">
			    	     <td    rowspan="${score.count}" style="border: 1px solid #ddd;padding: 5px 10px;" >${markTerm.name}</td>
			    	     </c:if>
			    	    
			    	      
			    	      <td width="300" style="border: 1px solid #ddd;padding: 5px 10px;" >
			    	      ${score.name}
			    	      </td>
				 	     <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${score.standardScore}</td>
				 	      <c:forEach items="${extensions.supplierList}" var="supplier">
					 	    <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
					            <c:forEach items="${scores}" var="sco">
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><font color="red" >${sco.score}</font></c:if>
					 	        </c:forEach>
					 	    </td>
				 	      </c:forEach>
				 	     
				 	    </tr>
				 	  </c:if>
				 	</c:forEach>
				 </c:forEach>
				 
   	</table>
  	
  	</c:forEach>
</div>

</body>

</html>