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

String fileName = "投标报价一览表"; 


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

response.setHeader("Content-disposition", "attachment; filename=" + fileName);     
    
%> 

</head>

<body>
<div style="width:100%;" class = "Section1">
	
		<div align="center" style="margin-top: 10px!important;text-align: center;">
	   		<h2>${project.name}(${pack.name})</h2>
	   		<h2>投标报价一览表</h2>
	   	</div>
	   	<c:forEach items="${list}" var="v" varStatus="vs">
	   	   <c:if test="${type!=null&&type=='JZXTP_DYLY'}">
	   	      <div align="center" style="margin-top: 10px!important;text-align: center;">第${vs.index+1}轮报价</div>
	   	   </c:if>
		<table align="center" style="width:95%;border:1px solid #dddddd; border-collapse: collapse;margin-top: 10px" colspan="0" rowspan="0">
			<thead>
				<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
					<th width="10%" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">序号</th>
					<th width="50%" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">供应商名称</th>
					<th width="20%" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">总价(万元)</th>
					<th width="20%" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;">交货期限</th>
			    </tr>
			</thead>
			<c:forEach items="${list[vs.index]}" var="qu" varStatus="quvs">
				<tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
				    <td  style="border: 1px solid #ddd;padding: 5px 10px;">${quvs.index+1 }</td>
				    <td  style="border: 1px solid #ddd;padding: 5px 10px;">${qu.supplier.supplierName}</td>
				    <td  style="border: 1px solid #ddd;padding: 5px 10px;text-align: right;">${qu.total}</td>
				    <td  style="border: 1px solid #ddd;padding: 5px 10px;">${qu.deliveryTime }</td>
			    </tr>
			</c:forEach>
		</table>
	</c:forEach>
</div>

</body>

</html>