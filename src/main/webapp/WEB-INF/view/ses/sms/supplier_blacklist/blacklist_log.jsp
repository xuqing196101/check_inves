<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

<title>供应商黑名单记录表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	$(function() {
		var supplierId = "${supplierId}";
		laypage({
		 	cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages: "${listBlacklistLogs.pages}", //总页数
			skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip: true, //是否开启跳页
			total: "${listBlacklistLogs.total}",
			startRow: "${listBlacklistLogs.startRow}",
			endRow: "${listBlacklistLogs.endRow}",
			groups: "${listBlacklistLogs.pages}">=5?5:"${listBlacklistLogs.pages}", //连续显示分页数
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			    var page = location.search.match(/page=(\d+)/);
			    return page ? page[1] : 1;
			}(), 
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					location.href = '${pageContext.request.contextPath}/blacklist_log/list.html?page=' + e.curr + '&supplierId=' + supplierId;
				}
			}
		});	
	});
</script>

</head>

<body>
	<div class="container">
		<!-- 我的订单页面开始-->
		<div class="headline-v2">
				<h2>供应商黑名单记录表</h2>
		</div>

		<div class="content table_box">
            <table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info" width="25%">供应商名称</th>
							<th class="info" width="12%">起始时间</th>
							<th class="info" width="10%">期限</th>
							<th class="info" width="12%">结束时间</th>
							<th class="info" width="15%">处罚类型</th>
							<th class="info">列入黑名单原因</th>
						</tr>
					</thead>
					<tbody id="black_tbody_id">
						<c:forEach items="${listBlacklistLogs.list}" var="log" varStatus="vs">
							<tr class="hand">
								<td class="tc">${vs.index + 1}</td>
								<td class="tl">${log.supplierName}</td>
								<td class="tc"><fmt:formatDate value="${log.startTime}" pattern="yyyy-MM-dd"/></td>
								<td class="tc">
									<c:if test="${log.term == 3}">3个月</c:if>
									<c:if test="${log.term == 6}">6个月</c:if>
									<c:if test="${log.term == 12}">1年</c:if>
									<c:if test="${log.term == 24}">2年</c:if>
									<c:if test="${log.term == 36}">3年</c:if>
									<c:if test="${log.term == 0}">永久</c:if>
								</td>
								<td class="tc">
									<fmt:formatDate value="${ log.endTime }" pattern="yyyy-MM-dd" />
								</td>
								<td class="tl">
									<c:if test="${log.punishType == 0}">警告</c:if>
									<c:if test="${log.punishType == 1}">不得参与采购活动</c:if>
								</td>
								<td class="tc" title="${ log.reason }">
									<c:if test="${log.reason.length() > 10}">
									 	${log.reason.substring(0,9)}...
                    				 </c:if>  
									 <c:if test="${log.reason.length() <= 10}">
									 	${log.reason}
                    				</c:if>  
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	
	<form id="edit_form_id" action="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html" method="post">
		<input name="supplierBlacklistId" type="hidden" />
	</form>
</body>
</html>
