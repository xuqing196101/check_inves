<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
<%@ include file="../../../common.jsp"%>

<script type="text/javascript">
	$(function() {
		laypage({
			cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${listSupplierCreditCtnts.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${listSupplierCreditCtnts.total}",
			startRow : "${listSupplierCreditCtnts.startRow}",
			endRow : "${listSupplierCreditCtnts.endRow}",
			groups : "${listSupplierCreditCtnts.pages}" >= 5 ? 5 : "${listSupplierCreditCtnts.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					//location.href = '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html?page=' + e.curr;
					//alert(e.curr);
					$("input[name='page']").val(e.curr);
					searchSupplierCredit(0);
				}
			}
		});
	});

	function addCreditCtnt() {
		var supplierCreditId = $("input[name='supplierCreditId']").val();
		window.location.href = "${pageContext.request.contextPath}/supplier_credit_ctnt/add_credit_ctnt.html?supplierCreditId=" + supplierCreditId;
	}

	function editSupplierCreditCtnt() {
		var checkbox = $("input[name='checkbox']:checked");
		if (checkbox.size() != 1) {
			layer.msg("请勾选一条记录 !", {
				offset : '50px',
			});
			return;
		}
		var id = checkbox.val();
		var name = checkbox.parents("tr").find("td").eq(2).text();
		name = $.trim(name);
		var score = checkbox.parents("tr").find("td").eq(3).text();
		score = $.trim(score);
		var supplierCreditId = $("input[name='supplierCreditId']").val();
		window.location.href = "${pageContext.request.contextPath}/supplier_credit_ctnt/add_credit_ctnt.html?id=" + id + "&name=" + name + "&score=" + score + "&supplierCreditId=" + supplierCreditId;
	}

	function checkAll(ele) {
		var flag = $(ele).prop("checked");
		$("input[name='checkbox']").each(function() {
			$(this).prop("checked", flag);
		});
	}
	function searchSupplierCredit(sign) {
		if (sign) {
			$("input[name='page']").val(1);
		}
		$("#search_form_id").submit();
	}
	
	
	function deleteCreditCtnt() {
		var checkbox = $("input[name='checkbox']:checked");
		var supplierCreditId = $("input[name='supplierCreditId']").val();
		if (checkbox.size() == 0) {
			layer.msg("请至少勾选一条记录 !", {
				offset : '50px',
			});
			return;
		}
		var ids = "";
		var count = 0;
		checkbox.each(function(index) {
			var value = $(this).val();
			if (index > 0) {
				ids += ",";
			}
			ids += value;
			count ++;
		});
		layer.confirm('已勾选' + count + '条, 确认删除 ？', {
			offset : '50px',
		},function(index) {
			window.location.href = "${pageContext.request.contextPath}/supplier_credit_ctnt/delete.html?ids=" + ids + "&supplierCreditId=" + supplierCreditId;
			layer.close(index);
		});
	}
	
	function resetForm() {
		$("input[name='name']").val("");
	}
	
</script>

</head>

<body>
	<div class="container">
		<input type="hidden" name="supplierCreditId" value="${supplierCreditId}">
		<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="addCreditCtnt()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="editSupplierCreditCtnt()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="deleteCreditCtnt()">删除</button>
			</div>
		
		<div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th class="info w50"><input type="checkbox" onchange="checkAll(this)"></th>
							<th class="info w50">序号</th>
							<th class="info">诚信内容名称</th>
							<th class="info">分数</th>
							<th class="info">诚信形式</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSupplierCreditCtnts.list}" var="ctnt" varStatus="vs">
							<tr>
								<td class="tc"><input name="checkbox" type="checkbox" value="${ctnt.id}"></td>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${ctnt.name}</td>
								<td class="tc">${ctnt.score}</td>
								<td class="tc">${ctnt.supplierCreditName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			<div id="pagediv" align="right"></div>
		</div>
	</div>
</body>
</html>
