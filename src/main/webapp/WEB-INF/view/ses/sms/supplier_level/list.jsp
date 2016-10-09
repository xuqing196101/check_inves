<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商诚信列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${listSuppliers.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${listSuppliers.total}",
			startRow : "${listSuppliers.startRow}",
			endRow : "${listSuppliers.endRow}",
			groups : "${listSuppliers.pages}" >= 5 ? 5 : "${listSuppliers.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplierLevel(0);
				}
			}
		});
	});

	function changeScore() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '300px',
			});
			return;
		}
		var id = checkbox.val();
		var supplierName = checkbox.parents("tr").find("td").eq(2).text();
		supplierName = $.trim(supplierName);
		layer.open({
			type : 2,
			title : '添加形式名称',
			skin : 'layui-layer-rim', //加上边框
			area : [ '700px', '370px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : '${pageContext.request.contextPath}/supplier_level/change_score.html?id=' + id + '&supplierName=' + supplierName, //url
			closeBtn : 1, //不显示关闭按钮
		});
	}

	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	
	function searchSupplierLevel(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	
	function resetForm() {
		$("input[name='supplierName']").val("");
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
					<li><a href="#">供应商诚信管理</a></li>
					<li class="active"><a href="#">供应商诚信列表</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>供应商诚信列表</h2>
			</div>
		</div>


		<!-- 表格开始-->
		<div class="container">
			<div class="col-md-8">
				<button class="btn btn-windows edit" type="button" onclick="changeScore()">加/减分</button>
			</div>
		</div>
		
		<div class="container">
			<div class="p10_25">
				<form id="search_form_id" class="padding-10 border1 mb0" action="${pageContext.request.contextPath}/supplier_level/list.html" method="get">
					<input name="page" type="hidden" />
					<ul class="demand_list">
						<li class="fl">
							<label class="fl mt5">供应商名称：</label>
							<span><input type="text" name="supplierName" value="${supplierName}"/></span>
						</li>
						<li class="fl">
							<label class="fl mt5">等级：</label>
							<span>
								<select name="level">
									<option selected="selected" value="">全部</option>
									<option value="1">一星级</option>
									<option value="2">二星级</option>
									<option value="3">三星级</option>
									<option value="4">四星级</option>
									<option value="5">五星级</option>
								</select>
							</span>
						</li>
						<li class="fl mt1">
							<button type="button" onclick="searchSupplierLevel(1)" class="btn">查询</button>
							<button onclick="resetForm()" class="btn" type="button">重置</button>
						</li>
					</ul>
					<div class="clear"></div>
				</form>
			</div>
		</div>
		
		<div class="container margin-top-5">
			<div class="content padding-left-25 padding-right-25 padding-top-5">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">企业等级</th>
							<th class="info">分数</th>
							<th class="info">企业类型</th>
							<th class="info">企业性质</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="vs">
							<tr>
								<td class="tc"><input name="checkbox" type="checkbox" value="${supplier.id}"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${supplier.supplierName}</td>
								<td class="tc">${supplier.level}</td>
								<td class="tc">${supplier.score}</td>
								<td class="tc">
									<c:forEach items="${supplier.listSupplierTypeRelates}" var="relate">
										${relate.supplierTypeName}
									</c:forEach>
								</td>
								<td class="tc">${supplier.businessType}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
</body>
</html>
