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

<style>

@page
    {mso-page-border-surround-header:no;
    mso-page-border-surround-footer:no;}
@page Section1
    {size:841.9pt 595.3pt;
    mso-page-orientation:landscape;
    margin:89.85pt 72.0pt 89.85pt 72.0pt;
    mso-header-margin:42.55pt;
    mso-footer-margin:49.6pt;
    mso-paper-source:0;
    layout-grid:15.6pt;}
div.Section1
    {page:Section1;}

</style>


<%

String fileName = "评审结果"; 

//对中文文件名编码 
String UserAgent = request.getHeader("USER-AGENT").toLowerCase();  
String tem="";
  if (UserAgent != null) {  
      if (UserAgent.indexOf("msie") >= 0)  
           tem="IE";  
      if (UserAgent.indexOf("firefox") >= 0)  
          tem= "FF";  
      if (UserAgent.indexOf("safari") >= 0)  
          tem= "SF";  
  }
  if ("FF".equals(tem)) {  
      // 针对火狐浏览器处理方式不一样了  
      fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1") + ".doc";  
  }else{
  fileName = URLEncoder.encode(fileName, "UTF-8")+ ".doc";
  }    
//对中文文件名编码 
response.setHeader("Content-disposition", "attachment; filename=" + fileName);     
%>

</head>

<body>
<div style="width:85%;margin:auto;" class = "Section1">
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
		   <c:if test="${fn:length(extensions.supplierList)<8}">
		        <c:forEach begin="1" end="${8-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th width="120"  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
		            </th>
		         </c:forEach>
		  </c:if>
   		  </tr>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <c:forEach items="${extensions.supplierList}" var="supplier">
		   	   <th style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">得分</th>
	   	  </c:forEach>
	   	  <c:if test="${fn:length(extensions.supplierList)<8}">
		        <c:forEach begin="1" end="${8-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
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
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.supplierId and sco.scoreModelId eq score.id}"><font color="red" >${sco.score}</font></c:if>
					 	        </c:forEach>
					 	    </td>
				 	      </c:forEach>
				 	     
				 	    </tr>
				 	  </c:if>
				 	</c:forEach>
				 </c:forEach>
		<tr>
		 	<td style="border: 1px solid #ddd;padding: 5px 10px;">合计</td>
		 	<td style="border: 1px solid #ddd;padding: 5px 10px;">--</td>
		 	<td style="border: 1px solid #ddd;padding: 5px 10px;">--</td>
		 	<c:forEach items="${extensions.supplierList}" var="supplier">
		      <td style="border: 1px solid #ddd;padding: 5px 10px;">
		      	<input type="hidden" name="${supplier.supplierId}_total"/>
		      	<span>
		      		<c:set var="sum_score" value="0"/>
		      		<c:forEach items="${scores}" var="sco">
		 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.supplierId}">
		 	          	<c:set var="sum_score" value="${sum_score+sco.score}"/>
		 	          </c:if>
		 	        </c:forEach>
		 	        <font color="red" class="f18">${sum_score}</font>
		 	        <c:set var="sum_score" value="0"/>
		      	</span>
		      </td>
		    </c:forEach>
		 </tr>		 
   	</table>
  	
  	</c:forEach>
</div>

</body>

</html>