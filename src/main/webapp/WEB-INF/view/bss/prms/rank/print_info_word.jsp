<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>


<%@page contentType="application/vnd.ms-word;charset=utf-8"%>

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

//对中文文件名编码 

/* fileName = URLEncoder.encode(fileName, "utf-8"); 

byte[] yte = fileName.getBytes("GB2312"); 

String unicoStr = new String(yte, "utf-8");

response.setHeader("Content-disposition", "attachment; filename=" + unicoStr);   */   

%>

</head>
<body>

<div style="width:85%;margin:auto;" class = "Section1">
    <div align="center" style="display: block;background: #fff;padding: 1px 10px;margin: 10px 0 10px 20px;border-left: 4px solid #2c9fa6;">
   		<h2>${pack.name}供应商排名</h2>
   	</div>
   	
  	<c:forEach items="${supplierList}" var="extensions" >
  	<table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;" colspan="0" rowspan="0">
  	<thead>
   		  <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
   		  <th colspan="2" width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">专家/供应商</th>
   		 
   		  <c:forEach items="${extensions.supplierList}" var="supplier" varStatus="vs">
   		          <c:if test="${supplier.packages eq pack.id}">
	                <th width="180" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
	   		      ${supplier.suppliers.supplierName}
	   		        </th>
                  </c:if>	   		    		    
   		  </c:forEach>
   		   <c:if test="${fn:length(extensions.supplierList)<8}">
		        <c:forEach begin="1" end="${8-fn:length(extensions.supplierList)}"  step="1" varStatus="i">
		            <th width="180" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">
		            </th>
		         </c:forEach>
		  </c:if>
		  </tr>
		  <c:set value="0" var="number"></c:set>
   		  <c:forEach items="${expertList}" var="expert">
                <c:if test="${expert.packageId eq pack.id}">
                <c:set value="${number+1}" var="number"></c:set>
                  <tr>
                  	<c:if test="${expert.count != 0 and number == 1}">
                     <td rowspan="${expert.count}" width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">${expert.reviewTypeId}</td>
                     </c:if>
                    <td width="120" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">${expert.expert.relName}</td>
                    <c:forEach items="${extensions.supplierList}" var="supplier">
                  	  <c:if test="${supplier.packages eq pack.id}">
	                    <td width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
	                      <c:forEach items="${expertScoreList}" var="score">
	                        <c:if test="${score.packageId eq pack.id and score.supplierId eq supplier.suppliers.id and score.expertId eq expert.expert.id}">
	                          ${score.score}
	                        </c:if>
	                      </c:forEach>
	                    </td>
                      </c:if>
                    </c:forEach>
                  </tr>
                </c:if>
              </c:forEach>
              <%-- <tr>
                <td style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">总分</td>
                <c:forEach items="${extensions.supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td  width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id && rank.supplierId eq supplier.suppliers.id}">
	                      ${rank.econScore}(经济)+${rank.techScore}(技术)=${rank.sumScore}
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
              </tr>
              <tr>
                <td style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">排名</td>
                <c:forEach items="${extensions.supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td  width="120" style="border: 1px solid #ddd;padding: 5px 10px;">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and (rank.reviewResult == null or rank.reviewResult eq '')}">
	                      ${rank.rank}
	                    </c:if>
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and rank.reviewResult != null and rank.reviewResult ne ''}">
	                      <c:set var="num2" value="0" scope="page"></c:set>
		         			<c:forEach var="msg" items="${fn:split(rank.reviewResult, '_')}">
				                <c:set var="num2" value="${num2+1}" scope="page"></c:set>                               
		                        <c:if test="${num2 eq '3' }">
		                        	${msg}
		                        </c:if>
		                    </c:forEach>
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
              </tr> --%>
   	</table>
  	
  	</c:forEach>
</div>

</body>

</html>