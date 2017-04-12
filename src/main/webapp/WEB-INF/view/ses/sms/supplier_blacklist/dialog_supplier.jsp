<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
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
		var id = $("#supplier_id_input_id").val();
		var supplierName = $("#supplier_name_input_id").val();
		parent.document.getElementById("suppi").value=id;  
        parent.document.getElementById("suppllier_name_input_id").value=supplierName;
        parent.layer.closeAll();
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!-- 查询条件 -->
		<div class="container">
			<div class="search_detail ml0">
				<form id="search_form_id" action="${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html" method="get">
					<input name="page" type="hidden" />
					<ul class="demand_list">
						<li class="fl">
							<label class="fl">供应商名称：</label>
							<span><input name="supplierName" type="text" value="${supplierName}" /></span>
						</li>
						<li class="fl mt1">
							<input type="button" onclick="searchSupplier(1)" class="btn" value="查询" />
							<input onclick="resetForm()" class="btn" type="button" value="重置" />
						</li>
					</ul>
					<div class="clear"></div>
				</form>
			</div>
		</div>

		<div class="container margin-top-5">
			<div class="content padding-top-5">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="info w50">选择</th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">联系人电话</th>
							<th class="info">联系人手机</th>
							<th class="info">所在地区</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="vs">
							<tr>
								<td class="tc"><input name="id" type="radio" value="${supplier.id}"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="pl20">${supplier.supplierName}</td>
								<td class="tc">${supplier.contactMobile}</td>
								<td class="tc">${supplier.mobile}</td>
								<td class="pl20">${supplier.area.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div>
			<div class="col-md-12">
				<div class="tc mb10">
					<a class="btn btn-windows save" onclick="checkSupplier()">选择</a>
					<a target="_parent" class="btn btn-windows back" href="${pageContext.request.contextPath}/supplier_blacklist/add_supplier.html">返回</a>
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