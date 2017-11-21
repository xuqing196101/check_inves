<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page contentType="application/vnd.ms-word;charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
  <div style="width:100%;" class = "Section1">
   <div style="display: block;text-align: center;"><h2>评审记录</h2></div>
  <!-- 表格开始-->
  <div>
  <div >
     <c:forEach items="${selectList}" var="lis">
             <div >
             <h4>评审人员：${lis.expert.relName}</h4>
             <c:forEach items="${supplierList}" var="sul">
          <table align="center" style="border:1px solid #dddddd; border-collapse: collapse;width:100%;margin-top:20px;" >
        <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
               <th  rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:5%;">评审项目</th>
              <th rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:10%;">评审指标</th>
              <th  rowspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:5%;">标准分值</th>
              <c:forEach items="${sul.lists}" var="supplier">
              <th  colspan="2" style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:<c:if test="${fn:length(sul.lists)==1}">80</c:if><c:if test="${fn:length(sul.lists)!=1}">${8/fn:length(sul.lists)*10}</c:if>%;">${supplier.suppliers.supplierName}</th>
            </c:forEach>
        </tr>
        <tr>
        <c:forEach items="${sul.lists}" var="supplier">
                  <th style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:<c:if test="${fn:length(sul.lists)==1}">40</c:if><c:if test="${fn:length(sul.lists)!=1}">${8/fn:length(sul.lists)*5}</c:if>%;">参数</th>
                  <th style="background-color:#f7f7f7;border: 1px solid #ddd;padding: 5px 10px;width:<c:if test="${fn:length(sul.lists)==1}">40</c:if><c:if test="${fn:length(sul.lists)!=1}">${8/fn:length(sul.lists)*5}</c:if>%;">得分</th>
                </c:forEach>
        </tr>
          <c:forEach items="${lis.markTerms}" var="markTerm">
            <c:if test="${markTerm.checkedPrice!=1}">
            <c:forEach items="${lis.scoreModels}" var="score" varStatus="vs">
              <c:if test="${score.markTerm.pid eq markTerm.id}">
                <tr>
                  <!-- 所属模型 -->
                  <c:set var="model" value=""/>
                  <c:if test="${score.typeName == 0}"><c:set var="model" value="模型一A"/></c:if>
                  <c:if test="${score.typeName == 1}"><c:set var="model" value="模型二"/></c:if>
                <c:if test="${score.typeName == 2}"><c:set var="model" value="模型三"/></c:if>
                <c:if test="${score.typeName == 3}"><c:set var="model" value="模型四 A"/></c:if>
                  <c:if test="${score.typeName == 4}"><c:set var="model" value="模型五"/></c:if>
                  <c:if test="${score.typeName == 5}"><c:set var="model" value="模型六"/></c:if>
                  <c:if test="${score.typeName == 6}"><c:set var="model" value="模型七"/></c:if>
                <c:if test="${score.typeName == 7}"><c:set var="model" value="模型八"/></c:if>
                <c:if test="${score.typeName == 8}"><c:set var="model" value="模型一B"/></c:if>
                <c:if test="${score.typeName == 9}"><c:set var="model" value="模型四B"/></c:if>
                <c:if test="${score.count!=0}">
                <td style="border: 1px solid #ddd;padding: 5px 10px;" rowspan="${score.count}" >${markTerm.name}</td>
                </c:if>
                  
                  <td style="border: 1px solid #ddd;padding: 5px 10px;">
                      ${score.name}
                  </td>
                <td style="border: 1px solid #ddd;padding: 5px 10px; text-align: center;">${score.standardScore}</td>
                <c:forEach items="${sul.lists}" var="supplier">
                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;">
                   <c:forEach items="${lis.expertScores}" var="sco">
                      <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">
                           ${sco.expertValue }
                       </c:if>
                    </c:forEach>
                </td>
                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;">
                  <span><c:forEach items="${lis.expertScores}" var="sco">
                      <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><font color="red" class="f18">${sco.score}</font></c:if>
                    </c:forEach></span>
                </td>
                </c:forEach>
              </tr>
            </c:if>
          </c:forEach>
          </c:if>
         </c:forEach>
         <tr>
          <td style="border: 1px solid #ddd;padding: 5px 10px;">合计</td>
          <td style="border: 1px solid #ddd;padding: 5px 10px;">--</td>
          <td style="border: 1px solid #ddd;padding: 5px 10px;">--</td>
          <c:forEach items="${sul.lists}" var="supplier">
              <td style="border: 1px solid #ddd;padding: 5px 10px;"></td>
              <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;"  >
                <span>
                  <c:set var="sum_score" value="0"/>
                  <c:forEach items="${lis.expertScores}" var="sco">
                    <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id}">
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
         </c:forEach>
         </div>
  </div>
</div>
</body>
</html>
