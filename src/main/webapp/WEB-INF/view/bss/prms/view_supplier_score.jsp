<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>供应商评分详情</title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>
<jsp:include page="../../common.jsp"></jsp:include>	
<script type="text/javascript">
	function backUp(){
		$("#tab-6").load("${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId=${packageId}&projectId=${projectId}");
	}
</script>
</head>
<body>
  <!-- 我的订单页面开始-->
  <div class="container">
  <div class="headline-v2">
    <h2>${supplier.supplierName }</h2>
  </div>
  <div align="right">
	<button class="btn" onclick="window.print();" type="button">打印</button>
  </div>
  <!-- 表格开始-->
  <div>
	        <table class="table table-bordered table-condensed mt5" id="table2" style="overflow: hidden;word-spacing: keep-all;" >
			  <tr>
			    <th colspan="4"></th>
			    <c:forEach items="${expertList}" var="expert">
			      <th>${expert.relName}</th>
			    </c:forEach>
			  </tr>
			  <tr>
			   	  	  <th>评审项目</th>
			   	      <th>评审指标</th>
			   	      <th>指标模型</th>
			   	      <th>标准分值</th>
			   	  	  <c:forEach items="${expertList}" var="expert">
     		        	<th>评审得分</th>
	    		  	  </c:forEach>
			   		</tr>
			    <c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			    	      <td class="tc w100" rowspan="${score.count}" <c:if test="${score.count eq '0' or score.count == 0}">style="display: none"</c:if> >${markTerm.name}</td>
			    	      <td class=""><a href="javascript:void();" title="${score.reviewContent}">${score.name}</a></td>
			 	  		  <td class="">
			 	    	    <c:if test="${score.typeName == 0}">模型一</c:if>
			 	            <c:if test="${score.typeName == 1}">模型二</c:if>
				 	        <c:if test="${score.typeName == 2}">模型三</c:if>
				 	        <c:if test="${score.typeName == 3}">模型四</c:if>
				 	        <c:if test="${score.typeName == 4}">模型五</c:if>
				 	        <c:if test="${score.typeName == 5}">模型六</c:if>
				 	        <c:if test="${score.typeName == 6}">模型七</c:if>
				 	        <c:if test="${score.typeName == 7}">模型八</c:if>
				 	      </td>
				 	      <td class="tc">${score.standardScore}</td>
				 	      <c:forEach items="${expertList}" var="expert">
					 	    <c:set var="expertScore" value=""/>
					 	    <c:forEach items="${scores}" var="sco">
					 	      <c:if test="${sco.packageId eq packageId and sco.expertId eq expert.id and sco.supplierId eq supplierId and sco.scoreModelId eq score.id}"><c:set var="expertScore" value="${sco.score}"/></c:if>
					 	    </c:forEach>
					 	    <td class="tc">
					 	      <span>${expertScore}<c:if test="${expertScore eq '' and expertScore ne '0'}">未评分</c:if></span>
					 	    </td>
				 	      </c:forEach>
					    </tr> 
					  </c:if>
			        </c:forEach>
			    </c:forEach>
			</table>
			<div class="tc">
			  <%--<input class="btn btn-windows back" value="返回" type="button" onclick="backUp()">--%>
			</div>
		  </div>
   </div>
</body>
</html>
