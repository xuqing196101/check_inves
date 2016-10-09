<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商列表</title>

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
			pages: "${listSuppliers.pages}", //总页数
			skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip: true, //是否开启跳页
			total: "${listSuppliers.total}",
			startRow: "${listSuppliers.startRow}",
			endRow: "${listSuppliers.endRow}",
			groups: "${listSuppliers.pages}">=5?5:"${listSuppliers.pages}", //连续显示分页数
			curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
			    return page ? page[1] : 1;
			}(), 
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplier(0);
				}
			}
		});
		
		$("input[name='id']").click(function(index) {
			var id = $(this).val();
			var supplierName = $(this).parents("tr").find("td").eq(2).text();
			$("#supplier_id_input_id").val(id);
			$("#supplier_name_input_id").val(supplierName);
		});
	});
	function searchSupplier(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	function resetForm() {
		$("input[name='supplierName']").val("");
	}
	function checkSupplier() {
		var size = $(":radio:checked").size();
		if (!size) {
			layer.msg("请勾选一条记录 !", {
				offset : '150px',
			});
			return;
		}
		$("#check_form_id").submit();		
	}
</script>

</head>

<body>
	<div class="wrapper">
		<div class="container">
			<div style="padding-left: 20px;">
				<ul class="demand_list list-unstyled">
					<li class="fl"><label class="fl mt10">供应商名称：</label> 
						<form id="search_form_id" class="w500" action="${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html" method="get">
							<span><input type="text" class="mb0 mt5" name="supplierName" value="${supplierName}" /></span>
							<input type="hidden" name="page" />
							<input type="button" class="btn btn_back ml10 mt6" value="查询" onclick="searchSupplier(1)" />
							<input type="button" class="btn btn_back ml10 mt6" value="重置" onclick="resetForm()" />
						</form>					
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container margin-top-5">
			<div class="content padding-left-25 padding-right-25 padding-top-5">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="info w50">选择</th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">联系人电话</th>
							<th class="info">联系人手机</th>
							<th class="info">公司地址</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="vs">
							<tr>
								<td class="tc"><input name="id" type="radio" value="${supplier.id}"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${supplier.supplierName}</td>
								<td class="tc">${supplier.contactName}</td>
								<td class="tc">${supplier.contactTelephone}</td>
								<td class="tc">${supplier.address}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
			<div class="col-md-12">
				<div class="tc mb10">
					<a class="btn btn-windows save" onclick="checkSupplier()">选择</a>
					<a target="_parent" class="btn btn-windows reset" href="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html">返回</a>
				</div>
			</div>
			<form target="_parent" id="check_form_id" action="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html" method="post">
				<input id="supplier_id_input_id" type="hidden" name="id" />
				<input id="supplier_name_input_id" type="hidden" name="supplierName" />
			</form>
		</div>
	</div>
</body>
</html>