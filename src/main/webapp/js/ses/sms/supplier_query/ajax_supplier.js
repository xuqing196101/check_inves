$(function(){
	addButtonProject();
});
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
function addButton(){
	$("#addButton").empty();
	$("#addButton").append("<input class=\"btn fl mt1\" onclick=\"findSupplier(3)\" type=\"button\" value=\"查询\"> "
			+" <input type=\"button\" class=\"btn fl mt1\" onclick=\"resetQuery()\" value=\"重置\">"
			+" <input type=\"button\" class=\"btn fl mt1\" onclick=\"resetAgain()\" value=\"重新计算\">");
}
function addButtonProject(){
	$("#addButton").empty();
	$("#addButton").append("<input class=\"btn fl mt1\" onclick=\"findSupplier()\" type=\"button\" value=\"查询\"> "
			+" <input type=\"button\" class=\"btn fl mt1\" onclick=\"resetQuery()\" value=\"重置\">"
			);
}
/*******************************************************************************
 * 根据目录 查询供应商
 ******************************************************************************/
function findSupplier(level) {
	var supplierTypeName=$("#itemTypeName").val();
    if (level == 3 && supplierTypeName != null && supplierTypeName !='' && supplierTypeName !='工程'){
    	addButton();
    }else{
    	addButtonProject();
    }
    
	if (!$("#supplierTypeId").val()) {
		layer.msg("请选择目录");
	} /*else if (!$("#categoryIds").val()) {
		layer.msg("请先选择品目");
	}*/else{
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
					showData(obj.data);
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

//重新计算
function resetAgain(){
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
			url : globalPath + "/supplierQuery/againSupplierData.do",
			data : $("#form1").serializeArray(),
			success : function(obj) {
					layer.msg(obj.msg);
				layer.close(index);
				$("#page").val(1);
				findSupplier(3);
			},
			error : function(data) {
				layer.msg("请求异常!");
				layer.close(index);
			},
		});
	}
}

//全部重新计算
function resetAllAgain(){
	layer.confirm('确定要重新计算所有物资、服务品目下供应商的等级吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			var index = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
			$.ajax({
				type : "POST",
				url : globalPath + "/supplierQuery/countAllCategorySupplierLevel.do",
				data : $("#form1").serializeArray(),
				success : function(obj) {
					layer.msg(obj.msg);
					layer.close(index);
				},
				error : function(data) {
					layer.msg("请求异常!");
					layer.close(index);
				},
			});
		}, function(){
		  
		});
		
}

function resetQuery() {
	$("#supplierName").val("");
	$("#armyBusinessName").val("");
	$("#supplierLevel").val("");
	$("#categoryIds").val("");
	$("#supplierTypeId").val("");
	$("#page").val(1);
	$("#itemTypeName").val("");
}
function info(supplierId) {
	window.open(globalPath +"/supplierQuery/essential.do?judge=2&supplierId="+ supplierId);
}
function empty(){
	addButtonProject();
	$("#tb1 tbody").empty();
}
// 封装填充 数据
function showData(obj) {
	$("#tb1 tbody").empty();
	$(obj.list).each(
			function(index, item) {
				var level = "";
				if (item.supplierLevelName != null) {
					level = item.supplierLevelName;
				}
				var armyBusinessName = "";
				if (item.armyBusinessName != null) {
					armyBusinessName = item.armyBusinessName;
				}
				var armyBuinessTelephone = "";
				if (item.armyBuinessTelephone != null) {
					armyBuinessTelephone = item.armyBuinessTelephone;
				}
				var orgName = "";
				if (item.orgName != null) {
					orgName = item.orgName;
				}
				$("#tb1 tbody").append(
						" <tr>" + "<td class=\"tc\">" + ((index+1)+(obj.pageNum-1)*(obj.pageSize))+ "</td>"
								+ "<td class=\"pl20\">"
								+ "<a href=\"javascript:void(0);\" onclick=\"info('" + item.supplierId+ "')\">" + item.supplierName + "</a>"
								+ "</td>" + "<td class=\"tc\">" + level
								+ "</td>" + "<td class=\"tl\">"
								+ armyBusinessName + "</td>"
								+ "<td class=\"tc\">"
								+ armyBuinessTelephone + "</td>"
								+ "<td class=\"tl\">" + orgName + "</td>"
								+ "</tr>");
			});
}
