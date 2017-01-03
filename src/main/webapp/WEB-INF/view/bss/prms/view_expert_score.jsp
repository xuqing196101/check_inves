<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>专家评分详情</title>
<style> 
table{border-collapse:collapse;border-spacing:0px; width:100%; border:#ddd solid 0px;} 
table td{border:1px solid #ddd;height:30px; text-align:center; border-left:0px;} 
table th{ background:#f7f7f7; color:#a10333; border:#ddd solid 1px; white-space:nowrap; height:30px; border-top:0px;border-left:0px;} 
.t_left{width:35%; height:auto; float:left;border-top:1px solid #ddd;border-left:1px solid #ddd;} 
.t_r_content{width:100%; height:auto; background:#fff; overflow:auto;} 
.cl_freeze{height:auto;overflow:hidden; width:100%;}  
.t_r1{width:64.5%; height:auto; float:left;border-top:1px solid #ddd; border-right:#ddd solid 1px;} 
.t_r2{width:64.5%; height:auto; float:left;border-top:1px solid #ddd; border-right:#ddd solid 1px;} 
.t_r_t{width:100%; overflow:hidden;} 
.bordertop{ border-top:0px;} 
.t_r1 table{width:1700px;} 
.t_r2 table{width:735px;} 
.t_r_title1{width:1700px;}
.t_r_title2{width:735px;}
</style>
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
	// 控制滚动
	function controlScroll(){ 
		var a = document.getElementById("t_r_content").scrollTop; 
		var b = document.getElementById("t_r_content").scrollLeft; 
		document.getElementById("cl_freeze").scrollTop=a; 
		document.getElementById("t_r_t").scrollLeft=b; 
	} 
</script>
</head>
<body>
  <div class="container">
  <div class="">
    <h2>${expert.relName }</h2>
  </div>
  <div align="right">
	<button class="btn" onclick="window.print();" type="button">打印</button>
  </div>
  <!-- 表格开始-->
  <div>
	        <div class="t_left mt20"> 
			<div style="width:100%;"> 
	        <table class="m0" id="table2" style="overflow: hidden;word-spacing: keep-all;" >
			  <tr>
			      <th colspan="4"></th>
			  </tr>
			  <tr>
		   	  	  <th width="25%" class="tc">评审项目</th>
		   	      <th width="25%" class="tc">评审指标</th>
		   	      <th width="25%" class="tc">指标模型</th>
		   	      <th width="25%" class="tc">标准分值</th>
			  </tr>
			</table>
			</div> 
			<div class="cl_freeze" id="cl_freeze"> 
			<table>
			    <c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			    	      <td width="25%" class="tc" rowspan="${score.count}" <c:if test="${score.count eq '0' or score.count == 0}">style="display: none"</c:if> >${markTerm.name}</td>
			    	      <td width="25%" class="tc"><a href="javascript:void();" title="${score.reviewContent}">${score.name}</a></td>
			 	  		  <td width="25%" class="tc">
			 	    	    <c:if test="${score.typeName == 0}">模型一</c:if>
			 	            <c:if test="${score.typeName == 1}">模型二</c:if>
				 	        <c:if test="${score.typeName == 2}">模型三</c:if>
				 	        <c:if test="${score.typeName == 3}">模型四</c:if>
				 	        <c:if test="${score.typeName == 4}">模型五</c:if>
				 	        <c:if test="${score.typeName == 5}">模型六</c:if>
				 	        <c:if test="${score.typeName == 6}">模型七</c:if>
				 	        <c:if test="${score.typeName == 7}">模型八</c:if>
				 	      </td>
				 	      <td width="25%" class="tc">${score.standardScore}</td>
				 	    </tr>
				 	  </c:if>
				 	</c:forEach>
				 </c:forEach>
			</table>	    
		  </div>
		  </div>
		  <div class="<c:if test="${size > 4}">t_r1</c:if> <c:if test="${size <= 4}">t_r2</c:if> mt20"> 
			<div class="t_one">
			<div class="t_r_t" id="t_r_t"> 
			<div class="<c:if test="${size > 4}">t_r_title1</c:if> <c:if test="${size <= 4}">t_r_title2</c:if>"> 
			<table>
				<tr>
			      <c:forEach items="${supplierList}" var="supplier">
				      <th class="tc"  width="${length1}">${supplier.suppliers.supplierName}</th>
				    </c:forEach>
			  	</tr>
			  	<tr>
			  		<c:forEach items="${supplierList}" var="supplier">
		   		        <th class="tc" width="${length1}">评审得分</th>
	   		  	  	</c:forEach>
			  	</tr>
		  	</table> 
			</div> 
			</div> 
		  <div class="t_r_content" id="t_r_content" onscroll="controlScroll()"> 
			<table> 
			<c:forEach items="${markTermList}" var="markTerm">
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
				 	      <c:forEach items="${supplierList}" var="supplier">
					 	    <c:set var="expertScore" value=""/>
					 	    <c:forEach items="${scores}" var="sco">
					 	      <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><c:set var="expertScore" value="${sco.score}"/></c:if>
					 	    </c:forEach>
					 	    <td class="tc" width="${length1}">
					 	      <span>${expertScore}<c:if test="${expertScore eq '' and expertScore ne '0'}">未评分</c:if></span>
					 	    </td>
				 	      </c:forEach>
					    </tr> 
					  </c:if>
			        </c:forEach>
			    </c:forEach>
			</table> 
			</div>
			</div> 
		</div>
			<div class="tc">
			  <%--<input class="btn btn-windows back" value="返回" type="button" onclick="backUp()">--%>
		    </div>
		  </div>
   </div>
</body>
</html>
