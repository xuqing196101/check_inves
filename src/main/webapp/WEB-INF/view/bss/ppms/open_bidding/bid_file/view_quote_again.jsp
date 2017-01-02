<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	function back(){
		$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
	}
</script>
</head>
<body>
<div id="showDiv" class="clear">
<c:set value="1" var ="count"></c:set>
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <h2 onclick="ycDiv(this,'${index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 <span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
			 </h2>
        </div>
        <div class="p0${index}">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">总价(万元)</th>
					<th class="info">交货期限</th>
					<th class="info">是否到场</th>
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:set value="${count+1 }" var="index"></c:set>
				<tr>
				    <td class="tc w50">${vs.index+1}</td>
				    <td class="tc">${treemapValue.suppliers.supplierName}</td>
					<td class="tc">${treemapValue.total}</td>
			    	<td class="tc">${treemapValue.deliveryTime }</td>
					<td class="tc">
							<c:if test="${treemapValue.isTurnUp ==2 }">已到场</c:if>
							<c:if test="${treemapValue.isTurnUp ==1 }">未到场</c:if>
					</td>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
		<div class="col-md-12 tc">
			<input class="btn btn-windows reset" value="返回" type="button" onclick="history.go(-1)">
		</div>
</div>
</body>
</html>
