$(function() {
	initData();
});

function initData() {
	var _isAudit = $("#isAudit").val();
	var _supplierId = $("#supplierId").val();
	var _supplierSt = $("#supplierSt").val();
	var _currentStep = $("#currentStep").val();
	var _sign = $("#sign").val();

	var frameWin = top.document.getElementById('iframepage').contentWindow;
	var frameDoc = top.document.getElementById('iframepage').contentWindow.document;

	isAudit = _isAudit || frameWin.isAudit;
	isAudit = (isAudit == true || isAudit == "true") ? true : false;
	supplierId = _supplierId || frameWin.supplierId;
	supplierSt = _supplierSt || frameWin.supplierSt;
	currentStep = _currentStep || frameWin.currentStep;
	sign = _sign || frameWin.sign;
}

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
		// isAudit：只有审核的状态能审核
		if (isAudit && options) {
			if (options.funcBeforeAudit && options.funcBeforeAudit() == false) {
				return;
			}

			var _this = this;// 当前审核对象
			var defaultVal = "";// 弹出框默认值
			var auditData = options.auditData;// 审核数据

			// 判断：新审核/可再次审核/不可再次审核

			// 获取是否已审核并退回过的缓存数据
			var isAudited = $(_this).attr("data-isAudited");
			// 获取旧的审核记录缓存数据
			var oldAuditMsg = $(_this).attr("data-oldAuditMsg");
			// 获取是否缓存
			var isCached = $(_this).attr("data-isCached");

			if (isCached) {
				if (isAudited) {
					layer.msg('该条信息已审核过并退回过！');
					return;
				}
				if (oldAuditMsg) {
					defaultVal = oldAuditMsg;
				}
			} else {
				var result = getOldAudit(auditData);
				if (result && result.status == 0) {
					layer.msg('该条信息已审核过并退回过！');
					$(_this).attr("data-isAudited", true);
					return;
				}
				if (result && result.status == 1 && result.data) {
					defaultVal = result.data.suggest;
					$(_this).attr("data-oldAuditMsg", defaultVal);
				}
				$(_this).attr("data-isCached", true);
			}
			var promptOptions = {
				title : '请填写不通过的理由：',
				value : defaultVal,
				formType : 2,
				// offset : '100px',
				maxlength : '100'
			};
			if (defaultVal) {
				promptOptions.value = defaultVal;
				promptOptions.btn = [ '确定', '撤销', '取消' ];
				promptOptions.btn2 = function(index) {
					var bool = cancelAudit(auditData);
					if (bool && options.funcAfterCancelAudit) {
						options.funcAfterCancelAudit();
						$(_this).removeAttr("data-isAudited");
						$(_this).removeAttr("data-oldAuditMsg");
					}
				};
				promptOptions.btn3 = function(index) {
					layer.close(index);
				};
			}
			layer.prompt(promptOptions, function(value, index, elem) {
				var text = $.trim(value);
				if (text == "") {
					$(elem).val("");
					$(elem).focus();
					return;
				}
				if (text.length > 900) {
					layer.msg('审核理由内容太长！', {
						offset : '100px'
					});
					return;
				}
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
								$(_this).attr("data-isAudited", true);
							}
							if (result.status == "500") {
								if (result.data == "add") {
									layer.msg('审核成功！', {
										shift : 6, // 动画类型
										offset : '100px'
									});
									if (options.funcAfterAddAudit) {
										options.funcAfterAddAudit();
									}
								}
								if (result.data == "update") {
									layer.msg('修改理由成功！', {
										shift : 6, // 动画类型
										offset : '100px'
									});
								}
								$(_this).attr("data-oldAuditMsg", text);
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
	initData();
	$.fn.doAudit.defaults = {
		auditData : {
			supplierId : supplierId,
			auditType : "",
			auditField : "",
			auditFieldName : "",
			auditContent : ""
		},
		funcBeforeAudit : null,
		funcAfterAddAudit : null,
		funcAfterCancelAudit : null
	};
})(jQuery);

// 显示修改前的信息
function showModify(_this, modifyType, beforeField) {
	var modifyMsg = $(_this).attr("data-modifyMsg");
	if (modifyMsg) {
		layer.tips("修改前:" + modifyMsg, _this, {
			tips : 3
		});
		return;
	}
	$.ajax({
		url : globalPath + "/supplierAudit/showModify.do",
		data : {
			supplierId : supplierId,
			beforeField : beforeField,
			modifyType : modifyType
		},
		async : false,
		success : function(result) {
			$(_this).attr("data-modifyMsg", result);
			layer.tips("修改前:" + result, _this, {
				tips : 3
			});
		}
	});
}
// 显示修改前的信息
function showModifyList(_this, modifyType, beforeField, relationId, listType) {
	var modifyMsg = $(_this).attr("data-modifyMsg");
	if (modifyMsg) {
		layer.tips("修改前:" + modifyMsg, _this, {
			tips : 3
		});
		return;
	}
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
			$(_this).attr("data-modifyMsg", result);
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
function toStep(step) {
	$("#reverse_of_" + step).click();
}