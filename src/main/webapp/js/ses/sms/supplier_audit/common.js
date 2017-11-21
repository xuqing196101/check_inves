// 删除左右两端的空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 获取旧的审核记录
function getOldAudit(auditData) {
	var result = null;
	$.ajax({
		url : globalPath + "/supplierAudit/ajaxOldAudit.do",
		type : "post",
		dataType : "json",
		data : auditData,
		async : false,
		success : function(data) {
			result = data;
		}
	});
	return result;
}

// 获取旧的审核记录（多条）
function getOldAuditMuti(auditData) {
	var result = null;
	$.ajax({
		url : globalPath + "/supplierAudit/ajaxOldAuditMuti.do",
		type : "post",
		dataType : "json",
		contentType : "application/json",
		data : auditData,
		async : false,
		success : function(data) {
			result = data;
		}
	});
	return result;
}

// 撤销审核记录
function cancelAudit(auditData) {
	var bool = false;
	$.ajax({
		url : globalPath + "/supplierAudit/cancelAudit.do",
		type : "post",
		dataType : "json",
		data : auditData,
		async : false,
		success : function(result) {
			if (result && result.status == 500) {
				bool = true;
				layer.msg('撤销成功！');
			}
		}
	});
	return bool;
}

// 撤销审核记录（多条）
function cancelAuditMuti(auditData) {
	var bool = false;
	$.ajax({
		url : globalPath + "/supplierAudit/cancelAuditMuti.do",
		type : "post",
		dataType : "json",
		contentType : "application/json",
		data : auditData,
		async : false,
		success : function(result) {
			if (result && result.status == 500) {
				bool = true;
				layer.msg('撤销成功！');
			}
		}
	});
	return bool;
}

// 执行审核
(function($) {
	$.fn.doAudit = function(options) {
		options = $.fn.extend({}, $.fn.doAudit.defaults, options);
		// 只有审核的状态能审核
		if (isAudit && options) {
			if (options.funcBeforeAudit && options.funcBeforeAudit() == false) {
				return;
			}
			var auditData = options.auditData;
			// 判断：新审核/可再次审核/不可再次审核
			// 获取旧的审核记录
			var result = getOldAudit(auditData);
			if (result && result.status == 0) {
				layer.msg('该条信息已审核过并退回过！');
				return;
			}
			var defaultVal = "";
			var promptOptions = {
				title : '请填写不通过的理由：',
				value : defaultVal,
				formType : 2,
				// offset : '100px',
				maxlength : '100'
			};
			if (result && result.status == 1 && result.data) {
				defaultVal = result.data.suggest;
				promptOptions.value = defaultVal;
				promptOptions.btn = [ '确定', '撤销', '取消' ];
				promptOptions.btn2 = function(index) {
					var bool = cancelAudit(auditData);
					if (bool && options.funcAfterCancelAudit) {
						options.funcAfterCancelAudit();
					}
				};
				promptOptions.btn3 = function(index) {
					layer.close(index);
				};
			}
			layer.prompt(promptOptions, function(value, index, elem) {
				var text = trim(value);
				if (text != null && text != "") {
					auditData.suggest = text;
					$.ajax({
						url : globalPath + "/supplierAudit/auditReasons.do",
						type : "post",
						dataType : "json",
						data : auditData,
						success : function(result) {
							if (result.status == "503") {
								layer.msg('该条信息已审核过并退回过！', {
									shift : 6, // 动画类型
									offset : '100px'
								});
							}
							if (result.status == "500") {
								if (result.data == "add") {
									layer.msg('审核成功！', {
										shift : 6, // 动画类型
										offset : '100px'
									});
									if(options.funcAfterAddAudit){
										options.funcAfterAddAudit();
									}
								}
								if (result.data == "update") {
									layer.msg('修改理由成功！', {
										shift : 6, // 动画类型
										offset : '100px'
									});
								}
							}
						}
					});
					layer.close(index);
				} else {
					layer.msg('不能为空！', {
						offset : '100px'
					});
				}
			});
		}
	};
	$.fn.doAudit.defaults = {
		auditData : {
			supplierId : supplierId,
			auditType : null,
			auditField : null,
			auditFieldName : null,
			auditContent : null
		},
		funcBeforeAudit : null,
		funcAfterAddAudit : null,
		funcAfterCancelAudit : null
	};
})(jQuery);

// 显示修改前的信息
function showModify(_this, modifyType, beforeField) {
	$.ajax({
		url : globalPath + "/supplierAudit/showModify.do",
		data : {
			supplierId : supplierId,
			beforeField : beforeField,
			modifyType : modifyType
		},
		async : false,
		success : function(result) {
			layer.tips("修改前:" + result, _this, {
				tips : 3
			});
		}
	});
}
//显示修改前的信息
function showModifyList(_this, modifyType, beforeField, relationId, listType) {
	$.ajax({
		url : globalPath + "/supplierAudit/showModify.do",
		data : {
			supplierId : supplierId,
			beforeField : beforeField,
			modifyType : modifyType,
			relationId : relationId,
			listType : listType
		},
		async : false,
		success : function(result) {
			layer.tips("修改前:" + result, _this, {
				tips : 3
			});
		}
	});
}

// 暂存
function tempAudit() {
	$.ajax({
		url : globalPath + "/supplierAudit/temporaryAudit.do",
		dataType : "json",
		data : {
			supplierId : supplierId
		},
		success : function(result) {
			layer.msg(result, {
				offset : [ '100px' ]
			});
		},
		error : function() {
			layer.msg("暂存失败", {
				offset : [ '100px' ]
			});
		}
	});
}

// 步骤跳转
function toStep(step){
  	$("#reverse_of_"+step).click();
}