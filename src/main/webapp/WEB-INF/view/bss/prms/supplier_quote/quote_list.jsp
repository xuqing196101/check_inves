<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
	//查看供应商报价
	 function supplierView(supplierId){
	    var projectId=$("#projectId").val();
		location.href="${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="+projectId+"&supplierId="+supplierId;
	 }
  </script>
  <body>
  	<div class="container">
	    <div class="headline-v2">
	     	<h2>供应商报价信息</h2>
	    </div>
	   	<div class="content table_box">
	   		<input type="hidden" id="projectId" value="${projectId}">
	    	<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
				<tr>
				  <th class="w50 info">序号</th>
				  <th class="info">供应商名称</th>
				  <th class="info">报价</th>
				  <th class="info">操作</th>
				</tr>
				</thead>
				<c:forEach items="${supplierList }" var="supplier" varStatus="vs">
			       <tr>
			        <td class="tc w30">${vs.count } </td>
			        <td class="tc">${supplier.suppliers.supplierName } </td>
			        <td>
			        	<table>
			        	  <c:forEach items="${supplier.money}" var="sm" varStatus="vs">
			        		<tr>
			        			<td class="tc">${sm.packageName}： </td>
			        		    <td class="tl w100">(${sm.totalMoney})</td>
			        		    <td class="tl w230">${sm.upperName }</td>
			        		</tr>
			              </c:forEach>
			        	</table>
			        </td>
			        <td class="tc w100">
			          <input class="btn" type="button" value="查看" onclick="supplierView('${supplier.suppliers.id}')">
			        </td>
			      </tr>
	      	  </c:forEach>
			</table>
	    </div>
    </div>
  </body>
</html>
