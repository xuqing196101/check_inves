<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'view.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
  	//返回
  	function goBack(){
  		var projectId = $("#projectId").val();
  		var flowDefineId = $("#flowDefineId").val();
  		window.location.href="${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId;
  	}
  	//查看专家对所有供应商的初审明细
	function viewByExpert(obj, packageId, projectId, flowDefineId){
		var expertId = $('input:radio[name="firstAuditByExpert"]:checked').val();
		if (typeof(expertId) == "undefined") {
			 layer.msg("请选择一名评委的初审记录",{offset: "100px", shade:0.01});
		}
		if (typeof(expertId) != "undefined") {
			window.location.href="${pageContext.request.contextPath}/packageExpert/viewByExpert.html?id="+expertId+"&packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+flowDefineId;
		}
	}
  </script>
  <body>
  	<div class="container">
	    <div class="headline-v2">
	     	<h2>${pack.name}初审查看</h2>
	    </div>
	    <div class="col-md-12 pl20 mt10">
		    <button class="btn" onclick="window.print();" type="button">打印</button>
	   	</div>
	   	<input type="hidden" id="projectId" value="${projectId}">
	   	<input type="hidden" id="flowDefineId" value="${flowDefineId}">
	   	<div class="content table_box">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
  		      <thead>
		      <tr>
		        <th class="info">评委/供应商</th>
		        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
		        	<th class="info">${supplier.suppliers.supplierName }</th>
		        </c:forEach>
		        <th class="tc w30"><button class="btn" onclick="viewByExpert(this,'${packageId}','${projectId}','${flowDefineId}');" type="button">查看明细</button></th>
		      </tr>
		      </thead>
		      <c:forEach items="${packExpertExtList}" var="ext" varStatus="vs">
			       <tr>
			        <td class="tc">${ext.expert.relName } </td>
			        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
				        	<td class="tc">
				        	  <c:forEach items="${supplierExtList }" var="supplierExt">
				        	  	<c:if test="${supplierExt.supplierId eq supplier.suppliers.id && ext.expert.id eq supplierExt.expertId}">
				        	  	${supplierExt.suppIsPass }
				        	  	</c:if>
				        	  </c:forEach>
				        	</td>
		            </c:forEach>
		            <td class="tc"><input type="radio" name="firstAuditByExpert" value="${ext.expert.id}"></td>
			      </tr>
	      	 </c:forEach>
	      	 	<tr>
	      	 		<td class="tc"><button class="btn" onclick="" type="button">查看明细</button></td>
	      	 		 <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
				       	<td class="tc w30"><input  type="radio" /></td>
				     </c:forEach>
				    <td></td>
		      	 </tr>
  		  </table>
  		</div>
  		<div class="col-md-12 pl20 mt10 tc">
		    <button class="btn btn-windows back" onclick="goBack();" type="button">返回</button>
	   	</div>
  	</div>
  </body>
</html>
