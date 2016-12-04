/** 自动勾选 select */
function autoSelected(id, v) {
	if (v) {
		$("#" + id).find("option").each(function() {
			var value = $(this).val();
			if(value == v) {
				$(this).prop("selected", true);
			} else {
				$(this).prop("selected", false);
			}
		});
	}
}

/** 重置表单 */
function resetForm(id) {
	$("#" + id).find(":text").val("");
	$("#" + id).find("select").each(function() {
		$(this).find("option").eq(0).prop("selected", true);
	});
}

/** 全选 */
function checkAll(ele, id) {
	var flag = $(ele).prop("checked");
	$("#" + id).find(":checkbox").prop("checked", flag);
}

/**
 *	判断 checkbox 是否勾选,
 * 	type = true 可以勾选多条, type = false 只能勾选一条,
 * 	msg 为错误消息提示,
 * 	返回 ids 和 size
 */
function isEmpty(id, type, msg) {
	var ids = "";
	var e = $("#" + id).find(":checkbox:checked");
	var size = e.size();
	if (type) {
		if (!size) {
			if (msg) {
				layer.msg(msg, {
					offset : '300px',
				});
			}
			return false;
		}
	} else {
		if (size != 1) {
			if (msg) {
				layer.msg(msg, {
					offset : '300px',
				});
			}
			return false;
		}
	}
	e.each(function(index) {
		if (ids) {
			ids += ",";
		}
			ids += $(this).val();
	});
	var result = {
			ids : ids,
			size : size
	};
	return result;
}
