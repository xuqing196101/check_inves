<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商黑名单列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	$(function() {
		laypage({
		 	cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages: "${listSupplierBlacklists.pages}", //总页数
			skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip: true, //是否开启跳页
			total: "${listSupplierBlacklists.total}",
			startRow: "${listSupplierBlacklists.startRow}",
			endRow: "${listSupplierBlacklists.endRow}",
			groups: "${listSupplierBlacklists.pages}">=5?5:"${listSupplierBlacklists.pages}", //连续显示分页数
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			    var page = location.search.match(/page=(\d+)/);
			    return page ? page[1] : 1;
			}(), 
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplierBlacklist(0);
				}
			}
		});	
	});
	function searchSupplierBlacklist(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	function resetForm() {
		$("input[name='supplierName']").val("");
		$("input[name='startTime']").val("");
		$("input[name='endTime']").val("");
	}
	
	function editSupplierBlacklist() {
		var size = $(":radio:checked").size();
		if (!size) {
			layer.msg("请选择一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var id = $(":radio:checked").val();
		$("input[name='supplierBlacklistId']").val(id);
		$("#edit_form_id").submit();
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a></li>
					<li><a href="#">业务管理</a></li>
					<li><a href="#">供应商</a></li>
					<li class="active"><a href="#">供应商黑名单</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>供应商黑名单列表</h2>
			</div>
		</div>


		<!-- 表格开始-->
		<div class="container">
			<div class="col-md-8">
				<button class="btn btn-windows add" type="button" onclick="location='${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html'">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="editSupplierBlacklist()">修改</button>
			</div>
		</div>
		<div class="container">
			<div class="p10_25">
				<form id="search_form_id" class="padding-10 border1 mb0" action="${pageContext.request.contextPath}/supplier_blacklist/list_blacklist.html" method="post">
					<input name="page" type="hidden" />
					<ul class="demand_list">
						<li class="fl">
							<label class="fl mt5">供应商名称：</label>
							<span><input name="supplierName" type="text" value="${supplierName}" /></span>
						</li>
						<li class="fl">
							<label class="fl mt5">起始时间：</label>
							<span><input type="text" name="startTime" readonly="readonly" onClick="WdatePicker()" value="${startTime}" /></span>
						</li>
						<li class="fl">
							<label class="fl mt5">终止时间：</label>
							<span><input name="endTime" type="text" readonly="readonly" onClick="WdatePicker()" value="${endTime}" /></span>
						</li>
						<li class="fl mt1">
							<button type="button" onclick="searchSupplierBlacklist(1)" class="btn">查询</button>
							<button onclick="resetForm()" class="btn" type="button">重置</button>
						</li>
					</ul>
					<div class="clear"></div>
				</form>
			</div>
		</div>
		

		<div class="container">
			<div class="content padding-left-25 padding-right-25 padding-top-5">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="info w50">选择</th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">起始时间</th>
							<th class="info">结束时间</th>
							<th class="info">处罚类型</th>
							<th class="info">发布类型</th>
							<th class="info">状态</th>
							<th class="info">列入黑名单原因</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSupplierBlacklists.list}" var="supplierBlacklist" varStatus="vs">
							<tr>
								<td class="tc"><input id="${supplierBlacklist.supplierId}" name="id" value="${supplierBlacklist.id}" type="radio"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${supplierBlacklist.supplierName}</td>
								<td class="tc"><fmt:formatDate value="${supplierBlacklist.startTime}" pattern="yyyy-MM-dd"/></td>
								<td class="tc"><fmt:formatDate value="${supplierBlacklist.endTime}" pattern="yyyy-MM-dd"/></td>
								<td class="tc">
									<c:if test="${supplierBlacklist.punishType == 0}">警告</c:if>
									<c:if test="${supplierBlacklist.punishType == 1}">不得参与采购活动</c:if>
								</td>
								<td class="tc">
									<c:if test="${supplierBlacklist.releaseType == 0}">
										内外网发布
									</c:if>
									<c:if test="${supplierBlacklist.releaseType == 1}">
										内网发布
									</c:if>
									<c:if test="${supplierBlacklist.releaseType == 2}">
										外网发布
									</c:if>
								</td>
								<td class="tc">
									<c:if test="${supplierBlacklist.status == 0}">
										处罚中
									</c:if>
									<c:if test="${supplierBlacklist.status == 1}">
										过期
									</c:if>
								</td>
								<td class="tc">${supplierBlacklist.reason}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
	
	<form id="edit_form_id" action="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html" method="post">
		<input name="supplierBlacklistId" type="hidden" />
	</form>
</body>
</html>
