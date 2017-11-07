<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	function back() {
		$("#tab-3").load("${pageContext.request.contextPath}/adPackageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
	}
</script>
</head>
<body>
<div id="showDiv" class="clear">
	<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
		<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
			<div class="col-md-12 col-xs-12 col-sm-12 p0">
			 	<h2  onclick="ycDiv(this,'${vsKey.index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 	<span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
			 	</h2>		 	
	        </div>
	        <div class="p0${vsKey.index} w100p clear">
			<table class="table table-bordered table-condensed mt5">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info w200">供应商名称</th>
						<th class="info w100">总价(万元)</th>
						<th class="info">交货期限</th>
						<th class="info w100">状态</th>
						<th class="info w100">放弃原因</th>
				    </tr>
				</thead>
				<c:set value="0" var = "index"> </c:set>
			<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:if test="${not empty treemapValue.total or treemapValue.isRemoved eq '放弃报价' }">
					<c:set value="${index + 1}" var = "index"> </c:set>
					<tr>
					    <td class="tc w50">${index}</td>
					    <td class="tl">${treemapValue.suppliers.supplierName}</td>
					    <td class="tr">${treemapValue.total}</td>
					    <td class="tc">${treemapValue.deliveryTime }</td>
					    <td class="tc">${treemapValue.isRemoved}</td>
						<td class="tc">${treemapValue.removedReason}</td>
				    </tr>
				</c:if>
			</c:forEach>
			</table>
			</div>
		</c:forEach>
	</c:forEach>
	<div class="col-md-12 tc">
		<input class="btn btn-windows reset" value="返回" type="button" onclick="back()">
	</div>
</div>
</body>
</html>
