<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    
    <title>检查汇总表</title>
    
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
    function printResult(projectId,packageId){
  	   window.location.href="${pageContext.request.contextPath}/adPackageExpert/printTotalWord.html?projectId="+projectId+"&packageId="+packageId;
    }
  </script>
  <body>
  	<div class="container">
    	<div class="mt5 mb5 fr">
		    <button class="btn" onclick="printResult('${project.id}','${pack.id}');" type="button">打印汇总表</button>
	   	</div>
	   	<div class="headline-v2">
	   		<h2>资格性符合性检查汇总数据</h2>
	   	</div>
	   	<div class="mt10 tc">
	   		<h2>${project.name}--${pack.name}</h2>
	   	</div>
	   	<div class="content table_box over_scroll">
	  	<table id="tabId" class="table table-bordered table-condensed table-hover table-striped  p0 m_resize_table_width">
 		  <thead>
		      <tr>
		        <th class="info" width = "120">评审内容/供应商</th>
		        <c:set var="suppliers" value="0" />
		        <c:forEach items="${saleTenderList}" var="supplier" varStatus="vs">
		        	<c:set var="suppliers" value="${suppliers+1}" />
		        	<th class="info" width = "120">${supplier.suppliers.supplierName}</th>
		        </c:forEach>
		      </tr>
	      </thead>
	      <tbody id="content">
	      <c:forEach items="${dds}" var="d">
		  	  <tr><td class="info" colspan="${suppliers+1}"><b>${d.name}</b></td></tr>
		  	  <c:forEach items="${firstAudits}" var="first" varStatus="vs">
		  	  	  <c:if test="${first.kind == d.id}">
		  	  	  	 <tr>
		  	  	  	 	<td class="w260"><a href="javascript:void(0);" title="${first.content}">${first.name}</a></td>
		  	  	  	 	<c:forEach items="${saleTenderList}" var="supplier">
		  	  	  	 		<td class="tc">
		  	  	  	 			<c:forEach items="${reviewFirstAudits}" var="rfa">
		  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==0}">
		  	  	  	 					合格
		  	  	  	 				</c:if>
		  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==1}">
		  	  	  	 					<div class='red'>不合格</div>
		  	  	  	 				</c:if>
		  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==2}">
		  	  	  	 					<div class='orange'>暂无</div>
		  	  	  	 				</c:if>
		  	  	  	 			</c:forEach>
		  	  	  	 		</td>
		  	  	  	 	</c:forEach>
		  	  	  	 </tr>
		  	  	  </c:if>
		  	  </c:forEach>
		  </c:forEach>
	     </tbody>
  		</table>
  		<h4>专家签名：</h4>
  	  </div>
  	</div>
  	
  	<script type="text/javascript">
		function resize_table_width() {
	        $('.m_resize_table_width').each(function () {
	            var table_width = 0;
	            var parent_width = $(this).parent().width();
	            $(this).find('thead th').each(function () {
	            	if(typeof($(this).attr('width')) != 'undefined') {
	            		table_width +=  parseInt($(this).attr('width'));
		            }
	            });
	            if (table_width > parent_width) {
		            $(this).css({
		                width: table_width,
		                maxWidth: table_width
		            });
	            }
	        });
	    }
	    $(function () {
	        resize_table_width();
	    });
	   	</script>
  </body>
</html>
