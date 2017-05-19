<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${listSupplier.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${listSupplier.total}",
					startRow: "${listSupplier.startRow}",
					endRow: "${listSupplier.endRow}",
					groups: "${listSupplier.pages}" >= 3 ? 3 : "${listSupplier.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${listSupplier.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							/* location.href = '${pageContext.request.contextPath}/supplierQuery/ajax_supplier.do?page=' + e.curr; */
							$("#page").val(e.curr);
							$("#form1").submit();
						}
					}
				});
			});
		</script>
		<script type="text/javascript">
			 function tijiao() {
				$("#form1").submit();
			}

			function resetQuery() {
				$("#supplierName").val("");
				$("#armyBusinessName").val("");
				$("#form1").submit();
			}
		
		</script>
	</head>

	<body>
		<h2 class="search_detail">
			<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/ajax_supplier.html" method="post">
				<input type="hidden" name="page" id="page">
				<input type="hidden" name="categoryIds" value="${categoryIds }"/>
				<ul class="demand_list">
					<li>
						<label class="fl">供应商名称：</label><span><input class="w220" id="supplierName" name="supplierName" value="${supplier.supplierName }" type="text"></span>
					</li>
					<li>
						<label class="fl">联系人：</label>
						<input id="armyBusinessName" class="w220" name="armyBusinessName" value="${supplier.armyBusinessName }" type="text">
					</li>
				</ul>
				<input class="btn fl mt1" onclick="tijiao()" type="button" value="查询">
			  <button type="button" class="btn fl mt1" onclick="resetQuery()">重置</button>
			 </form>
			 <div class="clear"></div>
			</h2>
	
		 <div class="content table_box pl0">
			<table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info">供应商名称</th>
						<th class="info">供应商等级</th>
						<th class="info">联系人</th>
						<th class="info">联系人电话</th>
						<!-- <th class="info">供应商类型</th>
						<th class="info">状态</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
						<tr>
							<td class="tc">${(vs.index+1)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
							<%-- <td class="pl20">
								<a href="${pageContext.request.contextPath}/supplierQuery/essential.html?judge=2&supplierId=${list.id}">${list.supplierName }</a>
							</td> --%>
							<td class="pl20">${list.supplierName }</td>
							<td class="tc">
								<c:choose>
									<c:when test="${list.status == -1 or (list.status==5 and list.isProvisional == 1)}">
										无等级
									</c:when>
									<c:otherwise>
										${list.grade}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="tc">${list.armyBusinessName}</td>
							<td class="tc">${list.armyBuinessTelephone}</td>
							<%-- <td class="tl pl20">${list.supplierType }</td>
							<td class="tc">
								<c:if test="${list.status==1}">审核通过</c:if>
								<c:if test="${list.status==3}">审核未通过</c:if>
								<c:if test="${list.status==4}">待复核</c:if>
								<c:if test="${list.status==5 and list.isProvisional == 0}">复核通过</c:if>
								<c:if test="${list.status==6}">复核未通过</c:if>
								<c:if test="${list.status==7}">待考察</c:if>
								<c:if test="${list.status==8}">考察合格</c:if>
								<c:if test="${list.status==9}">考察不合格</c:if>
							</td> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pagediv" align="right"></div>
	</body>

</html>