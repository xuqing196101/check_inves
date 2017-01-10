<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    
    <title>符合性审查汇总表</title>
    
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
  	$(function(){ 
		var html = "<tr><th class='info'>评审结果</th>";
		var tdCount = document.getElementById("tabId").rows.item(0).cells.length;
		for ( var int = 1; int < tdCount; int++) {
			var isPass = 0;
			var notPass = 0;
			var notaudit = 0;
			$('#tabId tr').find('td').each(function(){
				if ($(this).index() == int) { // 假设要获取第一列的值
	                var v = $(this).find("input").val();
	                if (v == 2){
	                	notaudit += 1;
	                }
	                if (v == 1) {
						isPass += 1;
					}
					if(v == 0){
						notPass += 1;
					}
	            }
			});
			if (notaudit > 0) {
				html += "<th class='info'>评审未完成</th>";
			} else if (notPass > isPass) {
				html += "<th class='info'>不合格</th>";
			} else if (isPass > notPass){
				html += "<th class='info'>合格</th>";
			}
		}
		html += "</tr>";
		$("#content").append(html);
	});
  </script>
  <body>
    	<div class="mb5 fr">
		    <button class="btn" onclick="window.print();" type="button">打印汇总表</button>
	   	</div>
	  	<table id="tabId" class="table table-bordered table-condensed table-hover table-striped  p0 space_nowrap">
 		  <thead>
		      <tr>
		        <th class="info">评委/供应商</th>
		        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
		        	<th class="info">${supplier.suppliers.supplierName }</th>
		        </c:forEach>
		      </tr>
	      </thead>
	      <tbody id="content">
	      <c:forEach items="${packExpertExtList}" var="ext" varStatus="vs">
		       <tr>
		        <td class="tc">${ext.expert.relName}</td>
		        <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
		        	<td class="tc">
		        	  <c:forEach items="${supplierExtList}" var="supplierExt">
		        	  	<c:if test="${supplierExt.supplierId eq supplier.suppliers.id && ext.expert.id eq supplierExt.expertId}">
			        	  	<c:if test="${supplierExt.suppIsPass == 0}">
				        	  	<input type="hidden" value="${supplierExt.suppIsPass}">
				        	  	不合格
			        	  	</c:if>
			        	  	<c:if test="${supplierExt.suppIsPass == 1}">
				        	  	<input type="hidden" value="${supplierExt.suppIsPass}">
				        	  	合格
			        	  	</c:if>
		        	  		<c:if test="${supplierExt.suppIsPass == 2}">
				        	  	<input type="hidden" value="${supplierExt.suppIsPass}">
				        	  	未提交
			        	  	</c:if>
		        	  	</c:if>
		        	  </c:forEach>
		        	</td>
	            </c:forEach>
		      </tr>
      	 </c:forEach>
	     </tbody>
  		</table>
  </body>
</html>
