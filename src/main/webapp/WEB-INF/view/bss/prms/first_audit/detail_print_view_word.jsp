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

String fileName = "资格性符合性检查数据"; 
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

<div style="width:100%;margin:auto;" class = "Section1">
    
   	<div align="center" style="margin-top: 10px!important;text-align: center;">
   		<h2>${project.name}--${pack.name}</h2>
   	</div>
  	
  	<c:forEach items="${extensions}" var="extension"  varStatus="p" >
  	<h4>评审人员：${extension[0].expert.relName}</h4>
  	   <c:forEach items="${extension}" var="ext" >
  	   <table>
  	  <tr><td></td></tr>
  	  <tr><td></td></tr>
  	  <tr><td></td></tr>
  	</table>
  	<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;width:100%;" colspan="0" rowspan="0">
  	<thead>
   		  <th  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:20%;">资格性和符合性检查项</th>
   		  <c:set var="suppliers" value="0" />
   		  <c:forEach items="${ext.supplierList}" var="supplier" varStatus="vs">
   		  	<c:if test="${fn:contains(supplier.packages,ext.packageId)}">
	   		    <c:set var="suppliers" value="${suppliers+1}" />
	   		    <th  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:<c:if test="${fn:length(ext.supplierList)==1}">80</c:if><c:if test="${fn:length(ext.supplierList)!=1}">${8/fn:length(ext.supplierList)*10}</c:if>%;">
	   		      ${supplier.suppliers.supplierName}
	   		    </th>
   		    </c:if>
   		  </c:forEach>
   		  <%-- <c:if test="${fn:length(ext.supplierList)<8}">
		        <c:forEach begin="1" end="${8-fn:length(ext.supplierList)}"  step="1" varStatus="i">
		            <th  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:10%;">
		            </th>
		         </c:forEach>
		  </c:if> --%>
   		  </tr>
   		</thead>
   		 <c:forEach items="${ext.dds}" var="d">
   			<tr  style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   			  <td  style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" colspan="${suppliers+1}">
   				<b>${d.name}</b>
   			  </td>
   			</tr>
   			<c:forEach items="${ext.firstAuditList }" var="first" varStatus="vs">
		      	<c:if test="${first.kind eq d.id}">
			      	<tr>
			      	  <td  style="border: 1px solid #ddd;padding: 5px 10px;width:20%;">${first.content}</td>
			      	  <c:forEach items="${ext.supplierList}" var="supplier" varStatus="v">
			      	  	 <td  style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;">
			      	  
	                   <c:if test="${fn:contains(supplier.packages,ext.packageId)}">
			                    <c:forEach items="${reviewFirstAuditList }" var="r" >                
			                      <c:if test="${ext.isSubmit == 0 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq ext.expert.id}">暂无</c:if>
			                      <c:if test="${ext.isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq ext.expert.id && r.isPass==0 }">合格</c:if>
			                      <c:if test="${ext.isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq ext.expert.id && r.isPass==1 }">
			                    	  <div class="red">不合格</div>
			                    	  理由：${r.rejectReason}
			                      </c:if>
			                    </c:forEach>
		                </c:if> 
		                </td>
		              </c:forEach>
		             <%--  <c:if test="${fn:length(ext.supplierList)<8}">
		                 <c:forEach begin="1" end="${8-fn:length(ext.supplierList)}"  step="1" varStatus="i">
		                 <td style="border: 1px solid #ddd;padding: 5px 10px;width:10%;">
		                 </td>
		                 </c:forEach>
		              </c:if> --%>
		           
			      	</tr>
		      	</c:if>
            </c:forEach> 
   		</c:forEach>
   	</table>
  	
  	   </c:forEach>
   <h4>专家签名：</h4>
   </c:forEach>
</div>

</body>

</html>