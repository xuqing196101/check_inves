/** 分页* */
function listPage(pages, total, startRow, endRow, pageNum) {
	laypage({
		cont : $("#pagediv"), // 容器。值支持id名、原生dom对象，jquery对象,
		pages : pages, // 总页数
		skin : '#2c9fA6', // 加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip : true, // 是否开启跳页
		total : total,
		startRow : startRow,
		endRow : endRow,
		groups : pages >= 3 ? 3 : pages, // 连续显示分页数
		curr : function() { // 通过url获取当前页，也可以同上（pages）方式获取
			return pageNum;
		}(),
		jump : function(e, first) { // 触发分页后的回调
			if (!first) { // 一定要加此判断，否则初始时会无限刷新
				$("#page").val(e.curr);
				findSupplier();
			}
		}
	});
}
/*******************************************************************************
 * 根据目录 查询供应商
 ******************************************************************************/
function findSupplier() {
	if (!$("#supplierTypeId").val()) {
		layer.msg("请选择目录");
	} else if (!$("#categoryIds").val()) {
		layer.msg("请先选择品目");
	} else {
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ],
			offset : [ '45%', '53%' ]
		});
		$.ajax({
			type : "POST",
			url : globalPath + "/supplierQuery/ajaxSupplierData.do",
			data : $("#form1").serializeArray(),
			success : function(obj) {
				if (obj.status == 500 || obj.status == 501) {
					listPage(obj.data.pages, obj.data.total, obj.data.startRow,
							obj.data.endRow, obj.data.pageNum);
					showData(obj.data.list);
					if (obj.status == 501) {
						layer.msg(obj.msg);
					}
				} else {
					layer.msg(obj.msg);
				}
				layer.close(index);
			},
			error : function(data) {
				layer.msg("请求异常!");
				layer.close(index);
			},
		});
	}
}
function resetQuery() {
	$("#supplierName").val("");
	$("#armyBusinessName").val("");
	$("#categoryIds").val("");
	$("#supplierTypeId").val("");
	$("#form1").submit();
	
}
function info(supplierId) {
	window.location.href = globalPath +"/supplierQuery/essential.do?judge=2&supplierId="+ supplierId;
}
// 封装填充 数据
function showData(obj) {
	$("#tb1 tbody").empty();
	$(obj).each(
			function(index, item) {
				$("#tb1 tbody").append(
						" <tr>" + "<td class=\"tc\">" + (index + 1) + "</td>"
								+ "<td class=\"pl20\">"
								+ "<a href=\"javascript:void(0);\" onclick=\"info('" + item.id+ "')\">" + item.supplierName + "</a>"
								+ "</td>" + "<td class=\"tc\">" + item.grade
								+ "</td>" + "<td class=\"tl\">"
								+ item.armyBusinessName + "</td>"
								+ "<td class=\"tc\">"
								+ item.armyBuinessTelephone + "</td>"
								+ "<td class=\"tl\">" + item.orgName + "</td>"
								+ "</tr>");
			});
}
