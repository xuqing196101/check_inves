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

String fileName = "专家咨询委员会.doc"; 

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
   		<h2>专家咨询委员会1</h2>
   	</div>
   	<div style="margin-top: 10px!important;text-align: center;">
   		<h2>${project.name}--${pack.name}</h2>
   	</div>
  	<c:forEach items="${saleTenderList}" var="extensions" >
  	<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
  	<thead>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <th width="120" rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">评审内容/供应商</th>
   		  <c:set var="suppliers" value="0" />
   		  <c:forEach items="${extensions.supplierList}" var="supplier" varStatus="vs">
   		  	<c:set var="suppliers" value="${suppliers+1}" />
	   		    <th width="120" colspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
	   		      ${supplier.suppliers.supplierName}
	   		    </th>
   		  </c:forEach>
   		  <c:if test="${fn:length(extensions.supplierList)<4}">
		        <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th width="120" colspan="2" style="border: 1px solid #ddd;padding: 5px 10px;">
		            </th>
		         </c:forEach>
		  </c:if>
   		  </tr>
   		  <tr>
		      	<c:forEach items="${extensions.supplierList}" var="supplier" varStatus="vs">
		        	<th style="text-align: center;background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" >合格</th>
		        	<th style="text-align: center;background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">不合格</th>
		        </c:forEach>
		        <c:if test="${fn:length(extensions.supplierList)<4}">
		        <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th style="text-align: center;background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" ></th>
		        	<th style="text-align: center;background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;"></th>
		         </c:forEach>
		  		 </c:if>
		      </tr>
   		</thead>
   	  <c:forEach items="${dds}" var="d">
   			<tr  style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   			  <td width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" colspan="(suppliers+1)*2">
   				<b>${d.name}</b>
   			  </td>
   			  <c:if test="${fn:length(extensions.supplierList)<4}">
		        <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <td width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;" colspan="(suppliers+1)*2">
   			  		</td>
		         </c:forEach>
		  	  </c:if>
   			</tr>
   			<c:forEach items="${firstAudits}" var="first" varStatus="vs">
		      	<c:if test="${first.kind == d.id}">
			      	<tr>
			      	  <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">${first.name}</td>
			      	  <c:forEach items="${extensions.supplierList}" var="supplier" varStatus="v">
			      	  <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
			      	   <c:set value="0" var="isPassNum"/>
	                   <c:forEach items="${reviewFirstAudits}" var="rfa">
  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==0}">
  	  	  	 					<c:set value="${isPassNum+1}" var="isPassNum"/>
  	  	  	 				</c:if>
  	  	  	 			</c:forEach>
  	  	  	 			${isPassNum}人
		                </td>
		                <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
		  	  	  	 			<c:set value="0" var="noPassNum"/>
		  	  	  	 			<c:forEach items="${reviewFirstAudits}" var="rfa">
		  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==1}">
		  	  	  	 					<c:set value="${noPassNum+1}" var="noPassNum"/>
		  	  	  	 				</c:if>
		  	  	  	 			</c:forEach>
		  	  	  	 			${noPassNum}人
		  	  	  	 		</td>
		              </c:forEach>
		              <c:if test="${fn:length(extensions.supplierList)<4}">
		                 <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		                 <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
		                 </td>
		                 <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
		                 </td>
		                 </c:forEach>
		              </c:if> 
			      	</tr>
		      	</c:if>
            </c:forEach> 
   		</c:forEach>
   		<tr>
	  	  	  <th style="text-align: center;">评审结果</th>
	  	  	  <c:if test="${flag != '1' }">
		  	  		  <c:forEach items="${extensions.supplierList}" var="st" varStatus="i">
			        	<td style="text-align: center;" colspan="2">
			        		<input type="radio" name="checkName${i.index}" value="${st.id},100" ><span class="green">合格</span>
			        		<input type="radio" name="checkName${i.index}" value="${st.id},0" ><span class="red">不合格</span>
			        	</td>
		        	  </c:forEach>
		        	  <c:if test="${fn:length(extensions.supplierList)<4}">
		                 <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		                 <input type="radio" name="checkName${i.index}" value="${st.id},100" ><span class="green"></span>
			        		<input type="radio" name="checkName${i.index}" value="${st.id},0" ><span class="red"></span>
		                 </c:forEach>
		              </c:if>
        	  </c:if>
        	  <c:if test="${flag == '1' }">
        	  	  <c:forEach items="${extensions.supplierList}" var="st" varStatus="i">
		        	<td style="text-align: center;" colspan="2">
		        		<c:if test="${st.economicScore == 100 && st.technologyScore == 100 }">
		        			<span style="color: green;">合格</span>
		        		</c:if>
		        		<c:if test="${st.economicScore == 0 && st.technologyScore == 0 }">
		        			<span style="color: red;">不合格</span>
		        		</c:if>
		        	</td>
	        	  </c:forEach>
	        	  <c:if test="${fn:length(extensions.supplierList)<4}">
	                 <c:forEach begin="1" end="${4-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
	                 <td style="text-align: center;" colspan="2"></td>
	                 </c:forEach>
	              </c:if> 
        	  </c:if>
	  	  </tr>
   	</table>
  	
  	</c:forEach>
</div>

</body>

</html>