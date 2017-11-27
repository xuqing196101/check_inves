<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
  
<%

String fileName = "供应商排名"; 
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
</head>
  <body>
    <h2 style="text-align: center;">供应商排名</h2>
	  <div style="width:100%;margin:auto;" class = "Section1">
	    <c:forEach items="${supplierList}" var="sup">
	       <table>
		  	  <tr><td></td></tr>
		  	  <tr><td></td></tr>
		  	  <tr><td></td></tr>
		  	</table>
          <c:if test="${'PBFF_JZJF' eq pack.bidMethodTypeName}">
         		<c:set var="isDone" value="0" scope="page"></c:set>
         		<c:forEach items="${sup.lists}" var="supplier">
                  <c:if test="${isDone ne '1' && supplier.packages eq pack.id}">
	                                                基准价：<fmt:formatNumber type="number" value="${supplier.jzjf.benchmarkPrice}" pattern="0.0000" maxFractionDigits="4"/>   
	                                                浮动比例：<fmt:formatNumber type="number" value="${supplier.jzjf.floatingRatio}" pattern="0.00" maxFractionDigits="2"/>%     
	                                                中标参考价：<fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4"/>     
	                                                有效平均报价：<fmt:formatNumber type="number" value="${supplier.jzjf.effectiveAverageQuotation}" pattern="0.0000" maxFractionDigits="4"/>     
                  	<c:set var="isDone" value="1" scope="page"></c:set>
                  </c:if>
                </c:forEach>
          </c:if>
          <c:if test="${'OPEN_ZHPFF' eq pack.bidMethodTypeName}">
           	<div style="clear: both;"></div>
         		<c:set var="isDone1" value="0" scope="page"></c:set>
         		<c:forEach items="${sup.lists}" var="supplier">
                  <c:if test="${isDone1 ne '1' && supplier.packages eq pack.id}">
         			<c:set var="num1" value="0" scope="page"></c:set>
         			<c:forEach var="msg" items="${fn:split(supplier.reviewResult, '_')}">
                        <c:if test="${num1 eq '1' }">
		                                                有效经济技术平均分（不含价格因素）：<fmt:formatNumber type="number" value="${msg}" pattern="0.00" maxFractionDigits="2"/>     
		                <c:set var="num1" value="2" scope="page"></c:set>                               
                        </c:if>
                        <c:if test="${num1 eq '0' }">
		                                                有效平均报价：<fmt:formatNumber type="number" value="${msg}" pattern="0.0000" maxFractionDigits="4"/>     
		                <c:set var="num1" value="1" scope="page"></c:set>                               
                        </c:if>
                    </c:forEach>
                  	<c:set var="isDone1" value="1" scope="page"></c:set>
                  </c:if>
                </c:forEach>
           
          </c:if>
          <c:if test="${'OPEN_ZHPFF' ne pack.bidMethodTypeName}">
          </c:if>
          <div >
            <table align="center" style="border:1px solid #dddddd; border-collapse: collapse;margin: auto;width:100%;" colspan="0" rowspan="0">
              <tr style="box-sizing: border-box; border:1px solid #dddddd; border-radius: 0px !important;">
                <td style="border: 1px solid #ddd;padding: 5px 10px; text-align: center; width: 7%;"  rowspan="2">分类</td>
                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;width: 7%;" rowspan="2">评委名称</td>
                <c:forEach items="${sup.lists}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;width:<c:if test="${fn:length(sup.lists)==1}">86</c:if><c:if test="${fn:length(sup.lists)!=1}">${8.6/fn:length(sup.lists)*10}</c:if>%;" colspan="2">${supplier.suppliers.supplierName}</td>
                  </c:if>
                </c:forEach>
              </tr>
              <tr>
              <c:forEach items="${sup.lists}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">检查结果</td>
                  </c:if>
                </c:forEach>
              </tr>
              <!-- 综合评分法 -->
          	  <c:if test="${'OPEN_ZHPFF' eq pack.bidMethodTypeName}">
          	     <tr>
          	      <td colspan="2" style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;">报价得分</td>
	          	     <c:forEach items="${sup.lists}" var="supplier">
		                  <c:if test="${supplier.packages eq pack.id}">
		                  	  <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2" id="price_${supplier.suppliers.id}_${pack.id}">
		                  	  <c:forEach items="${scorePrice}" var="price">
		                  	    <c:if test="${price.packageId eq pack.id and price.supplierId eq supplier.suppliers.id}">
		                          <fmt:formatNumber value="${price.score}" pattern="0.00"></fmt:formatNumber>  
		                        </c:if>
		                  	  </c:forEach>
		                  	  </td>
		                   </c:if>
		               </c:forEach>
          	     </tr>
          	  
          	  
	              <c:forEach items="${expertList}" var="expert">
	                <c:if test="${expert.packageId eq pack.id}">
	                  <tr>
	                    <c:if test="${expert.count!=0}">
	                    <td style="border: 1px solid #ddd;padding: 5px 10px; " rowspan="${expert.count}"  >${expert.reviewTypeId}</td>
	                    </c:if>
	                    <td style="border: 1px solid #ddd;padding: 5px 10px; ">${expert.expert.relName}</td>
	                    <c:forEach items="${sup.lists}" var="supplier">
	                  	  <c:if test="${supplier.packages eq pack.id}">
		                    <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">
		                      <c:forEach items="${expertScoreList}" var="score">
		                        <c:if test="${score.packageId eq pack.id and score.supplierId eq supplier.suppliers.id and score.expertId eq expert.expert.id}">
		                          <fmt:formatNumber value="${score.score}" pattern="0.00"></fmt:formatNumber> 
		                        </c:if>
		                      </c:forEach>
		                    </td>
	                      </c:if>
	                    </c:forEach>
	                  </tr>
	                </c:if>
	              </c:forEach>
	              <tr>
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">总分</td>
	                <c:forEach items="${sup.lists}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2" id="score_${supplier.suppliers.id}_${pack.id}">
		                  <c:forEach items="${rankList}" var="rank">
		                    <c:if test="${rank.packageId eq pack.id && rank.supplierId eq supplier.suppliers.id}">
		                       <c:if test="${rank.econScore!=0&&rank.techScore!=0&&rank.sumScore!=0&&rank.econScore!=null&&rank.techScore!=null}">
		                         <fmt:formatNumber value="${rank.priceScore}" pattern="0.00"></fmt:formatNumber>(价格)+<fmt:formatNumber value="${rank.econScore}" pattern="0.00"></fmt:formatNumber>(经济)+<fmt:formatNumber value="${rank.techScore}" pattern="0.00"></fmt:formatNumber>(技术)=<fmt:formatNumber value="${rank.sumScore}" pattern="0.00"></fmt:formatNumber>
		                       </c:if>
		                    </c:if>
		                  </c:forEach>
		                </td>
	                  </c:if>
	                </c:forEach>
	              </tr>
          	  </c:if>
              <!-- 最低价法 -->
              <c:if test="${'PBFF_ZDJF' eq pack.bidMethodTypeName}">
          	  	  <c:forEach items="${expertList}" var="expert">
	                <c:if test="${expert.packageId eq pack.id}">
	                  <tr>
	                  <c:if test="${expert.count!=0}">
	                    <td style="border: 1px solid #ddd;padding: 5px 10px; " rowspan="${expert.count}"  >${expert.reviewTypeId}</td>
	                    </c:if>
	                    <td style="border: 1px solid #ddd;padding: 5px 10px;">${expert.expert.relName}</td>
	                    <c:forEach items="${sup.lists}" var="supplier">
	                  	  <c:if test="${supplier.packages eq pack.id}">
		                    <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">
		                      	符合
		                    </td>
	                      </c:if>
	                    </c:forEach>
	                  </tr>
	                </c:if>
	              </c:forEach>
          	  	  <tr>
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">报价</td>
	                <c:forEach items="${sup.lists}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">
		                	<fmt:formatNumber type="number" value="${fn:substringBefore(supplier.reviewResult,'_')}" pattern="0.0000" maxFractionDigits="4"/>
		                </td>
	                  </c:if>
	                </c:forEach>
	              </tr>
	              <tr>
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">总结</td>
	                <c:forEach items="${sup.lists}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">
		                  	符合
		                </td>
	                  </c:if>
	                </c:forEach>
	              </tr>
          	  </c:if>
          	  <!-- 基准价法 -->
          	  <c:if test="${'PBFF_JZJF' eq pack.bidMethodTypeName}">
          	  	  <tr>
          	  	  	<td style="border: 1px solid #ddd;padding: 5px 10px; text-align: center;">报价</td>
          	  	  	<td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;">差价（与中标参考价的差价）</td>
          	  	  	<c:forEach items="${sup.lists}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;">
		                  	<fmt:formatNumber type="number" value="${supplier.jzjf.supplierPrice}" pattern="0.0000" maxFractionDigits="4"/>
		                </td>
		                <td style="border: 1px solid #ddd;padding: 5px 10px;">
		                	<c:if test="${supplier.jzjf.supplierPrice >= supplier.jzjf.bidPrice}">
			                	<fmt:formatNumber type="number" value="${supplier.jzjf.supplierPrice - supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4"/>
		                	</c:if>
		                	<c:if test="${supplier.jzjf.supplierPrice <= supplier.jzjf.bidPrice}">
			                	<fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice - supplier.jzjf.supplierPrice}" pattern="0.0000" maxFractionDigits="4"/>
		                	</c:if>
		                </td>
	                  </c:if>
	                </c:forEach>
          	  	  </tr>
          	  	  <tr>
          	  	  	<td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">总结</td>
          	  	  	<c:forEach items="${sup.lists}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">符合</td>
		              </c:if>
		            </c:forEach>
          	  	  </tr>
          	  </c:if>
              <tr>
                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">排名</td>
                <c:if test="${'PBFF_JZJF' eq pack.bidMethodTypeName}">
                	<c:forEach items="${sup.lists}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">
		                	${supplier.jzjf.rank}
		                </td>
	                  </c:if>
	                </c:forEach>
                </c:if>
                <c:if test="${'OPEN_ZHPFF' eq pack.bidMethodTypeName}">
                <c:forEach items="${sup.lists}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2" id="rank_${supplier.suppliers.id}_${pack.id}">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and (rank.reviewResult == null or rank.reviewResult eq '')}">
	                      <c:if test="${rank.rank!=0}">
	                      ${rank.rank}
	                      </c:if>
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
                </c:if>
                <c:if test="${'PBFF_ZDJF' eq pack.bidMethodTypeName}">
                <c:forEach items="${sup.lists}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td style="border: 1px solid #ddd;padding: 5px 10px;text-align: center;" colspan="2">
	                  ${fn:substringAfter(supplier.reviewResult,"_")}
	                </td>
                  </c:if>
                </c:forEach>
                </c:if>
              </tr>
			</table>
			</div>
		 </c:forEach>
      </div>
     
  </body>
</html>
