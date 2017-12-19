$().ready(function() {
	$("#basic_info_form_id").validForm();
});

$(function() {
	var error = $("#error").val();
	if (error == "formError") {
		layer.msg("提交失败，请仔细检查所填信息！");
	}
	if (error == "financeNotPass") {
		layer.msg("近3年加权平均净资产不满足注册要求！");
	}

	// loadRootArea();
	/*autoSelected("business_select_id", "${currSupplier.businessType}");
	autoSelected("nature_select_id", "${currSupplier.businessNature}");
	autoSelected("overseas_branch_select_id", "${currSupplier.overseasBranch}");
	autoSelected("isHavingConCert", "${currSupplier.isHavingConCert}");*/

	var supplierSt = $("#supplierSt").val();
	if (supplierSt == 7) {
		showReason();
	}

	toTempSave();// 实时保存
	
	// controlForm();
	readOnlyForm();// 表单只读
	
	checkCharLimit('businessScope', 'limit_char_businessScope', 1000);// 经营范围
	checkCharLimit('purchaseExperience', 'limit_char_purchaseExperience', 1000);// 参加政府或军队采购经历
	checkCharLimit('description', 'limit_char_description', 1000);// 公司简介
	// 境外分支生产经营范围
	$("textarea[name^='branchList'][name$='businessSope']").each(function() {
		checkCharLimit($(this).attr("id"), $(this).next().children().attr("id"), 1000);
	});
	
	checkStockholdersID();// 股东ID号校验
	
	sessionStorage.locationA = true;
	sessionStorage.index = 1;

});

// 异步加载地区下拉框
function loadAreaSelect(_this, appendTarget){
	var id = $(_this).val();
	if (id == "") {
		$(appendTarget).empty();
		$(appendTarget).append('<option value="" >请选择</option>');
	}
	if (id) {
		$.ajax({
			url : globalPath + "/basicData/ajaxAreaData.do",
			type : "post",
			dataType : "json",
			data : {
				pid : id
			},
			success : function(result) {
				if(result){
					var html = "";
					for ( var i = 0; i < result.length; i++) {
						html += "<option value='" + result[i].id + "'>"
								+ result[i].name + "</option>";
					}
					$(appendTarget).empty();
					$(appendTarget).append('<option value="" >请选择</option>');
					$(appendTarget).append(html);
				}
			}
		});
	}
}

// 异步加载国家下拉框
function loadCountrySelect(_this, appendTarget){
	var id = $(_this).val();
	if (id == "") {
		$(appendTarget).empty();
		$(appendTarget).append('<option value="" >请选择</option>');
	}
	if (id) {
		$.ajax({
			url : globalPath + "/basicData/ajaxCnrData.do",
			type : "post",
			dataType : "json",
			data : {
				continentId : id
			},
			success : function(result) {
				if(result){
					var html = "";
					for ( var i = 0; i < result.length; i++) {
						html += "<option value='" + result[i].nationId + "'>"
						+ result[i].nationName + "</option>";
					}
					$(appendTarget).empty();
					$(appendTarget).append('<option value="" >请选择</option>');
					$(appendTarget).append(html);
				}
			}
		});
	}
}

/** 全选 */
function checkAll(ele, id) {
	var checked = $(ele).prop("checked");
	$("#" + id).find("input:checkbox").each(function(index) {
		$(this).prop("checked", checked);
	});
}

/** 保存基本信息 */
function saveBasicInfo(obj) {
	var supplierId = $("input[name='id']").val();
	var msg = "";
	var flag = true;

	$("#address_list_body").find("input[type='text']").each(
			function(index, element) {
				if (element.value.trim().length <= 0) {
					msg = "地址信息不能为空！";
					flag = false;
				}
			});
	if ($("#overseas_branch_select_id").val() == "1") {
		// 非空校验
		$("#branch_list").find("input[type='text']").each(
				function(index, element) {
					if (element.value.trim().length <= 0) {
						msg = "境外信息不能为空！";
						flag = false;
					}
				});
		// 非空校验
		$(".cBranchName").each(function(index, element) {
			if (element.value.trim().length <= 0) {
				msg = "境外分支机构名称不能为空！";
				flag = false;
			}
		});
		$(".cOverseas").each(function(index, element) {
			if (element.value.trim().length <= 0) {
				msg = "境外分支所属国家（地区）不能为空！";
				flag = false;
			}
		});
		$(".cDetailAdddress").each(function(index, element) {
			if (element.value.trim().length <= 0) {
				msg = "境外分支详细地址不能为空！";
				flag = false;
			}
		});
		$(".cPrdArea").each(function(index, element) {
			if (element.value.trim().length <= 0) {
				msg = "境外分支生产经营范围不能为空！";
				flag = false;
			}
		});
	}
	// 非空校验
	$("#financeInfo").find("input[type='text']").each(function(index, element) {
		if (element.value.trim().length <= 0) {
			msg = "近三年财务信息不能为空！";
			flag = false;
		}
	});
	// 事务所联系方式格式校验
	$("#financeInfo").find("input[name$='telephone']").each(
			function(index, element) {
				if (element.value.trim().length <= 0) {
					msg = "近三年财务信息不能为空！";
					flag = false;
				}
			});
	// 出资人（股东）信息比例之和要大于50%(old)
	// 如果数量不超过10个，那占比必须100%，如果数量超过10个，那占比必须高于50%
	var proportionTotal = 0;// 出资比例之和
	var stockholderCount = 0;// 股东数量
	$("input[name^='listSupplierStockholder'][name$='proportion']").each(
			function() {
				proportionTotal += parseFloat($(this).val() || 0);
				stockholderCount++;
			});
	proportionTotal = proportionTotal.toFixed(2);
	if (proportionTotal != 0 && stockholderCount != 0) {
		if (stockholderCount >= 10 && proportionTotal < 50.00) {
			msg = "出资人10个或以上，出资比例之和要高于50%！";
			flag = false;
		}
		if (stockholderCount < 10 && proportionTotal != 100.00) {
			msg = "出资人不超过10个，出资比例之和必须为100%！";
			flag = false;
		}
		if (proportionTotal > 100.00) {
			msg = "出资比例总和不能超过100%！";
			flag = false;
		}
	}

	// 校验信用代码
	var creditCodeValue = $("#creditCode").val();
	if ($.trim(creditCodeValue) == "") {
		msg = "信用代码不能为空!";
		flag = false;
	}
	if (!checkCreditCode($("#creditCode").val())) {
		return false;
	}

	if (flag) {
		$("input[name='flag']").val(obj);
		// 提交的时候表单域设置成可编辑
		// enableForm();
		$("#basic_info_form_id").submit();
	} else {
		layer.msg(msg, {
			offset : '300px'
		});
	}
}

/** 暂存 */
function temporarySave() {
	$("input[name='flag']").val("");
	// 提交的时候表单域设置成可编辑
	// enableForm();
	$.ajax({
		url : globalPath + "/supplier/temporarySave.do",
		type : "post",
		data : $("#basic_info_form_id").serializeArray(),
		contextType : "application/x-www-form-urlencoded",
		success : function(msg) {
			// controlForm();
			if (msg == 'ok') {
				layer.msg('暂存成功', {
					offset : '300px'
				});
				$(":checkbox").removeAttr("isAdd");
			} else if (msg == 'failed') {
				layer.msg('暂存失败', {
					offset : '300px'
				});
			} else {
				/*
				 * layer.msg('暂存失败，请仔细检查所填信息！', { offset: '300px' });
				 */
				tempSaveValidation(msg);
			}
		},
		error : function() {
			// controlForm();
			layer.msg('暂存失败', {
				offset : '300px'
			});
		}
	});
}

/** 无提示实时保存 */
function tempSave() {
	$("input[name='flag']").val("");
	// enableForm();
	var saveMsg = layer.msg("暂存中，请稍候......", {
		time : 0,
		shade : [ 0.1, '#fff' ]
	});
	/*
	 * var loading = layer.load(1, { shade: [0.1,'#fff'] //0.1透明度的白色背景 });
	 */
	$.ajax({
		url : globalPath + "/supplier/temporarySave.do",
		type : "post",
		async : false,
		data : $("#basic_info_form_id").serializeArray(),
		contextType : "application/x-www-form-urlencoded",
		success : function(msg) {
			// controlForm();
			$("#name_span").val("");// 名称校验标识初始化
			tempSaveValidation(msg);
			layer.close(saveMsg);
			$(":checkbox").removeAttr("isAdd");
		},
		error : function() {
			// controlForm();
			layer.msg('暂存失败！', {
				offset : '300px'
			});
			layer.close(saveMsg);
		}
	});
}

function toTempSave(obj) {
	// 指定字段实时保存
	var target = $(".txtTempSave");
	if (obj) {
		target = $(obj);
	}
	if (target) {
		target.focus(function() {
			$(this).attr("data-oval", $(this).val());
		}).blur(function() {
			var oldVal = $(this).attr("data-oval"); // 获取原值
			var newVal = $(this).val(); // 获取当前值
			if (newVal && $.trim(newVal) != "" && oldVal != newVal) {
				tempSave();
			}
		});
	}
}

function tempSaveValidation(msg) {
	if (msg == "financeNotPass") {
		layer.msg('近3年加权平均净资产不足100万元，不满足注册要求！', {
			offset : '300px'
		});
	}
	if (msg == "creditCodeExists") {
		// $("input[name='creditCode']").val("");
		$("input[name='creditCode']").focus();
		layer.msg('统一社会信用代码已存在，请重新填写！', {
			offset : '300px'
		});
	}
	if (msg == "disabled_180") {
		// $("input[name='creditCode']").val("");
		$("input[name='creditCode']").focus();
		layer.msg('统一社会信用代码在180天内禁止再次注册，请重新填写！', {
			offset : '300px'
		});
	}
	if (msg == "errIdentity") {
		layer.msg('统一社会信用代码或身份证号重复，请重新填写！', {
			offset : '300px'
		});
	}
	if (msg == "supplierNameExists") {
		// $("#supplierName_input_id").val("");
		$("#supplierName_input_id").focus();
		layer.msg('供应商名称已存在，请重新填写！', {
			offset : '300px'
		});
	}
}

function addStockholder() {

	var stocIndex = $("#stockIndex").val();
	var supplierId = $("input[name='id']").val();
	var id;
	$.ajax({
		url : globalPath + "/supplier/getUUID.do",
		async : false,
		success : function(data) {
			id = data;
		}
	});

	$("#stockholder_list_tbody_id").append(
		"<tr>" + "<td class='tc'><input type='checkbox' value='"
		+ id
		+ "' isAdd='true' /><input type='hidden' name='listSupplierStockholders["
		+ stocIndex
		+ "].id' value='"
		+ id
		+ "'><input type='hidden' style='border:0px;' name='listSupplierStockholders["
		+ stocIndex
		+ "].supplierId' value="
		+ supplierId
		+ ">"
		+ "</td>"
		+ "<td class='tc'>"
		+ "<select class='w100p border0' name='listSupplierStockholders["
		+ stocIndex
		+ "].nature' onchange='onchangeNature(this.value,"
		+ stocIndex
		+ ")'>"
		+ "<option value='1'>法人</option>"
		+ "<option value='2'>自然人</option>"
		+ "</select>"
		+ "</td>"
		+ "<td class='tc'><input type='text' style='border:0px;' maxlength='50' name='listSupplierStockholders["
		+ stocIndex
		+ "].name' value=''> </td>"
		+ "<td class='tc'>"
		+ "<select class='w100p border0' name='listSupplierStockholders["
		+ stocIndex
		+ "].identityType'>"
		+ "<option value='1'>统一社会信用代码</option>"
		+ "<option value='2'>其他</option>"
		+ "</select>"
		+ "</td>"
		+ "<td class='tc'><input type='text' style='border:0px;' name='listSupplierStockholders["
		+ stocIndex
		+ "].identity' maxlength='18' onkeyup='validateIdentity(this)' onchange='validateIdentity(this)' value=''> </td>"
		+ "<td class='tc'><input type='text' style='border:0px;' name='listSupplierStockholders["
		+ stocIndex
		+ "].shares' value='' onblur='validateMoney(this.value, 4, false)'></td>"
		+ "<td class='tc'><input type='text' style='border:0px;' class='proportion_vali txtTempSave' name='listSupplierStockholders["
		+ stocIndex
		+ "].proportion' value='' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\" onblur=\"validatePercentage2(this.value)\"> </td>"
		+ "</tr>");

	checkStockholdersID("input[name='listSupplierStockholders[" + stocIndex
			+ "].identity']");
	toTempSave("input[name='listSupplierStockholders[" + stocIndex
			+ "].proportion']");

	stocIndex++;
	$("#stockIndex").val(stocIndex);
}

function validateIdentity(obj) {
	$(obj).val($(obj).val().replace(/[^\d|a-zA-Z]/g, ''));
}

function addAfterSaleDep() {

	var afterSaleIndex = $("#afterSaleIndex").val();
	var supplierId = $("input[name='id']").val();
	var id;
	$.ajax({
		url : globalPath + "/supplier/getUUID.do",
		async : false,
		success : function(data) {
			id = data;
		}
	});
	var _onkeyup = "value=value.replace(/[^\\d-]/g,\"\")";
	$("#afterSaleDep_list_tbody_id").append(
		"<tr>" + "<td class='tc'><input type='checkbox' value='"
		+ id
		+ "' isAdd='true' /><input type='hidden' name='listSupplierAfterSaleDep["
		+ afterSaleIndex
		+ "].id' value='"
		+ id
		+ "'><input type='hidden' style='border:0px;' name='listSupplierAfterSaleDep["
		+ afterSaleIndex
		+ "].supplierId' value="
		+ supplierId
		+ ">"
		+ "</td>"
		+ "<td class='tc'><input type='text' style='border:0px;' name='listSupplierAfterSaleDep["
		+ afterSaleIndex
		+ "].name' maxlength='90' value=''> </td>"
		+ "<td class='tc'> <div class='w120 fl'> <select class='w100p border0' name='listSupplierAfterSaleDep["
		+ afterSaleIndex
		+ "].type'>"
		+ "<option value='1'>自营</option>"
		+ "<option value='2'>合作</option>"
		+ "</select></div> </td>"
		+ "<td class='tc'><input type='text' style='border:0px;' name='listSupplierAfterSaleDep["
		+ afterSaleIndex
		+ "].address' maxlength='30' value=''> </td>"
		+ "<td class='tc'> <input type='text' style='border:0px;' name='listSupplierAfterSaleDep["
		+ afterSaleIndex
		+ "].leadName' maxlength='20' value=''></td>"
		+ "<td class='tc'> <input type='text' style='border:0px;' onkeyup='"
		+ _onkeyup + "' name='listSupplierAfterSaleDep["
		+ afterSaleIndex + "].mobile' value=''> </td>"
		+ "</tr>");

	afterSaleIndex++;
	$("#afterSaleIndex").val(afterSaleIndex);

}

function delAfterSaleDep() {
	var all = $("#afterSaleDep_list_tbody_id").find(":checkbox");
	var checkboxs = $("#afterSaleDep_list_tbody_id").find(":checkbox:checked");

	if (checkboxs.length == all.length) {
		layer.msg("售后服务机构请至少保留一条信息！");
		return;
	}

	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs);
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm("确认删除？", {
			offset : '200px',
			scrollbar : false,
			btn : [ '确定', '取消' ]
		// 按钮
		}, function(index) {
			var afterSaleDepIds = "";
			$(checkboxs).each(function(n) {
				var isAdd = $(this).attr("isAdd");
				if (isAdd) {
					var tr = $(this).parent().parent();
					$(tr).remove();
				} else {
					if (n > 0) {
						afterSaleDepIds += ",";
					}
					afterSaleDepIds += $(this).val();
				}
			});

			if (afterSaleDepIds != "") {
				$.ajax({
					url : globalPath + "/supplier/deleteAfterSaleDep.do",
					async : false,
					type : "POST",
					data : {
						"ids" : afterSaleDepIds,
					},
					success : function(data) {
						if (data == "ok") {
							layer.msg("删除成功！", {
								offset : '300px'
							});
							$(checkboxs).each(function(index) {
								var tr = $(this).parent().parent();
								$(tr).remove();
							});
						}
						if (data == "fail") {
							layer.msg("删除失败！", {
								offset : '300px'
							});
						}
					},
					error : function() {
						layer.msg("删除失败！");
					}
				});
			}
			layer.close(index);
		}, function(index) {
			layer.close(index);
		});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function delStockholder() {
	var all = $("#stockholder_list_tbody_id").find(":checkbox");
	var checkboxs = $("#stockholder_list_tbody_id").find(":checkbox:checked");

	if (checkboxs.length == all.length) {
		layer.msg("出资人（股东）信息请至少保留一条！");
		return;
	}

	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs);
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		// 如果数量不超过10个，那占比必须100%，如果数量超过10个，那占比必须高于50%
		var proportionTotal = 0;// 出资比例之和
		var stockholderCount = 0;// 股东数量
		$("input[name^='listSupplierStockholder'][name$='proportion']").each(
				function() {
					if (!($(this).parent().parent().find(":checkbox")
							.is(":checked"))) {
						proportionTotal += parseFloat($(this).val() || 0);
						stockholderCount++;
					}
				});
		var confirmMsg = "确认删除？";
		proportionTotal = proportionTotal.toFixed(2);
		if (proportionTotal != 0 && stockholderCount != 0) {
			if (stockholderCount >= 10 && proportionTotal < 50.00) {
				confirmMsg = "出资人10个或以上，出资比例之和要高于50%！确认删除？";
			}
			if (stockholderCount < 10 && proportionTotal != 100.00) {
				confirmMsg = "出资人不超过10个，出资比例之和必须为100%！确认删除？";
			}
		}

		layer.confirm(confirmMsg, {
			offset : '200px',
			scrollbar : false,
			btn : [ '确定', '取消' ]
		// 按钮
		}, function(index) {
			var stockholderIds = "";
			var supplierId = $("input[name='id']").val();
			$(checkboxs).each(function(n) {
				var isAdd = $(this).attr("isAdd");
				if (isAdd) {
					var tr = $(this).parent().parent();
					$(tr).remove();
				} else {
					if (n > 0) {
						stockholderIds += ",";
					}
					stockholderIds += $(this).val();
				}
			});

			if (stockholderIds != "") {
				$.ajax({
					url : globalPath
							+ "/supplier_stockholder/delete_stockholder.do",
					async : false,
					type : "POST",
					data : {
						"ids" : stockholderIds,
					},
					success : function(data) {
						if (data == "ok") {
							layer.msg("删除成功！", {
								offset : '300px'
							});
							$(checkboxs).each(function(index) {
								var tr = $(this).parent().parent();
								$(tr).remove();
							});
						}
						if (data == "fail") {
							layer.msg("删除失败！", {
								offset : '300px'
							});
						}
					},
					error : function() {
						layer.msg("删除失败！");
					}
				});
			}
			layer.close(index);
		}, function(index) {
			layer.close(index);
		});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function autoSelected(id, v) {
	if (v) {
		$("#" + id).val(v);
		/*
		 * $("#" + id).find("option").each(function() { var value =
		 * $(this).val(); if(value == v) { $(this).prop("selected", true); }
		 * else { $(this).prop("selected", false); } });
		 */
	}
}

function showReason() {
	var supplierId = $("#supplierId").val();
	var left = document.body.clientWidth - 500;
	var top = window.screen.availHeight / 2 - 150;
	layer.open({
		type : 2,
		title : '审核反馈',
		closeBtn : 0, // 不显示关闭按钮
		skin : 'layui-layer-lan', // 加上边框
		area : [ '500px', '300px' ], // 宽高
		offset : [ top, left ],
		shade : 0,
		maxmin : true,
		shift : 2,
		content : globalPath
				+ '/supplierAudit/showReasonsList.html?&auditType=basic_page,finance_page,stockholder_page'
				+ '&jsp=dialog_basic_reason' + '&supplierId='
				+ supplierId, // url
	});
}

// 去除请选择选项
function removeOption(obj) {
	$(obj).find("option").each(function(i, element) {
		if (element.value == "") {
			$(element).remove();
		}
	});
}

// 控制保密证书的显示与隐藏
function onchangeBearch(_this) {
	if ($(_this).val() == '0') {
		$("#bearchCertDiv").hide();
	} else {
		$("#bearchCertDiv").show();
		init_web_upload();
	}
}

//境外分支下拉事件
function onchangeBranch(_this) {
	if ($(_this).val() == "1") {
		$("#branch_list_body").show();
	} else {
		$("#branch_list_body").hide();
	}
}

function addAddress() {
	var ind = $("#addressNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addAddress.do",
		async : false,
		dataType : "html",
		data : {
			"ind" : ind
		},
		success : function(data) {
			$("#address_list_tbody_id").append(data);
			init_web_upload();
			ind++;
			$("#addressNumber").val(ind);
		}
	});
}
function delAddress(obj, id) {
	var all = $("#address_list_tbody_id").find(":checkbox");
	var checkboxs = $("#address_list_tbody_id").find(":checkbox:checked");

	if (checkboxs.length == all.length) {
		layer.msg("生产或经营地址请至少保留一条信息！");
		return;
	}

	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs);
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm("确认删除？", {
			offset : '200px',
			scrollbar : false,
			btn : [ '确定', '取消' ]
		// 按钮
		}, function(index) {
			var addressIds = "";
			$(checkboxs).each(function(n) {
				var isAdd = $(this).attr("isAdd");
				if (isAdd) {
					var tr = $(this).parent().parent();
					$(tr).remove();
				} else {
					if (n > 0) {
						addressIds += ",";
					}
					addressIds += $(this).val();
				}
			});

			if (addressIds != "") {
				$.ajax({
					url : globalPath + "/supplier/delAddress.do",
					async : false,
					type : "POST",
					data : {
						"ids" : addressIds
					},
					success : function(data) {
						if (data == "ok") {
							layer.msg("删除成功！", {
								offset : '300px'
							});
							$(checkboxs).each(function(index) {
								var tr = $(this).parent().parent();
								$(tr).remove();
							});
						}
						if (data == "fail") {
							layer.msg("删除失败！", {
								offset : '300px'
							});
						}
					},
					error : function() {
						layer.msg("删除失败！", {
							offset : '300px'
						});
					}
				});
			}
			layer.close(index);
		}, function(index) {
			layer.close(index);
		});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function addBranch(obj) {
	var branId = "";
	$.ajax({
		url : globalPath + "/supplier/getId.do",
		type : "post",
		success : function(data) {
			branId = data;
			var li = $(obj).parent().parent().next();
			var branchIndex = $("#branchIndex").val();
			$(li).after(
				"<li name='branch' class='col-md-3 col-sm-6 col-xs-12'>"
				+ " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>机构名称</span>"
				+ " <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"
				+
				// " <input type='hidden'
				// name='branchList[" + inde +
				// "].id' value='"+branId+"' />" +
				" <input class ='cBranchName' type='text' name='branchList["
				+ branchIndex
				+ "].organizationName' id='sup_branchName'  value='' / >"
				+ " <span class='add-on cur_point'>i</span>"
				+ " </div>"
				+ " </li>"
//				+ " <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>"
//				+ " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>所在国家（地区）</span>"
//				+ " <div class='select_common col-md-12 col-sm-12 col-xs-12 input_group p0'>"
//				+ " <select  class ='cOverseas' name='branchList["
//				+ branchIndex
//				+ "].country'>"
//				+ " <option value=''>请选择</option>"
//				+ " </select>"
//				+ " </div>"
//				+ " </li>"
				+ " <li name='branch'  class='col-md-4 col-sm-6 col-xs-12'>"
				+ " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>所在国家（地区）</span>"
				+ " <div class='select_common col-md-12 col-sm-12 col-xs-12 input_group p0'>"
				+ " <div class='col-md-3 col-xs-5 col-sm-5 mr5 p0'>"
				+ " <select class='cOverseas' id='select_continent_"
				+ branchIndex
				+ "' onchange=\"loadCountrySelect(this,'#select_country_"
				+ branchIndex
				+ "');\">"
				+ " <option value=''>请选择</option>"
				+ " </select>"
				+ " </div>"
				+ " <div class='col-md-8 col-xs-5 col-sm-5 mr5 p0'>"
				+ " <select class='cOverseas' id='select_country_"
				+ branchIndex
				+ "' name='branchList["
				+ branchIndex
				+ "].country'>"
				+ " <option value=''>请选择</option>"
				+ " </select>"
				+ " </div>"
				+ " </div>"
				+ " </li>"
				+

				" <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>"
				+ " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>详细地址</span>"
				+ " <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"
				+ " <input  class ='cDetailAdddress' type='text' name='branchList["
				+ branchIndex
				+ "].detailAddress'  id='sup_branchAddress' value='' / >"
				+ " <span class='add-on cur_point'>i</span>"
				+ " </div>"
				+ " </li>"
				+

				" <li name='branch'  class='col-md-2 col-sm-6 col-xs-12'>"
				+ " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>"
				+ " <div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>"
				+ " <input type='button' onclick='addBranch(this)' class='btn list_btn' value='十'/>"
				+ " <input type='button' onclick='delBranch(this)'class='btn list_btn' value='一'/>"
				+ " <input type='hidden' name='branchList["
				+ branchIndex
				+ "].id'   value='"
				+ branId
				+ "' />"
				+ " </div>"
				+ " </li>"
				+

				"<li name='branch'  class='col-md-12 col-xs-12 col-sm-12 mb25'>"
				+ " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>* </i>生产经营范围</span>"
				+ " <div class='col-md-12 col-xs-12 col-sm-12 p0'>"
				+ " <textarea class='cPrdArea col-md-12 col-xs-12 col-sm-12 h80' maxlength='1000' onkeyup=\"checkCharLimit('branchbusinessSope_"
				+ branchIndex
				+ "','limit_char_branchbusinessSope_"
				+ branchIndex
				+ "',1000);if(value.length==1000){layer.msg('字数过多，不可超过1000字！')}\" id='branchbusinessSope_"
				+ branchIndex
				+ "' name='branchList["
				+ branchIndex
				+ "].businessSope'></textarea>"
				+ " <span class='sm_tip fr'>还可输入 <span id='limit_char_branchbusinessSope_"
				+ branchIndex
				+ "'>1000</span> 个字</span>"
				+ " </div>" + " </li>");
			branchIndex++;
			$("#branchIndex").val(branchIndex);
			//appendBranchCountry(branchIndex-1);
			appendBranchContinent(branchIndex-1);
		}
	});
}

var countryData = [];// 缓存分支国家数据
function appendBranchCountry(branchIndex){
	var target = $("select[name='branchList[" + branchIndex + "].country']");
	if(countryData && countryData.length > 0){
		for ( var i = 0, len = countryData.length; i < len; i++) {
			target.append("<option value='" + countryData[i].id + "'>"
					+ countryData[i].name + "</option>");
		}
		return;
	}
	$.ajax({
		url : globalPath + "/basicData/ajaxDicData.do",
		type : "post",
		dataType : "json",
		data : {
			kind : 24
		},
		success : function(data) {
			if (data) {
				for ( var i = 0, len = data.length; i < len; i++) {
					target.append("<option value='" + data[i].id + "'>"
							+ data[i].name + "</option>");
				}
				countryData = data;
			}
		}
	});
}

var continentData = [];// 缓存分支国家所属洲数据
function appendBranchContinent(branchIndex){
	var target = $("#select_continent_" + branchIndex);
	if(continentData && continentData.length > 0){
		for ( var i = 0, len = countryData.length; i < len; i++) {
			target.append("<option value='" + countryData[i].id + "'>"
					+ continentData[i].name + "</option>");
		}
		return;
	}
	$.ajax({
		url : globalPath + "/basicData/ajaxDicData.do",
		type : "post",
		dataType : "json",
		data : {
			kind : 66
		},
		success : function(data) {
			if (data) {
				for ( var i = 0, len = data.length; i < len; i++) {
					target.append("<option value='" + data[i].id + "'>"
							+ data[i].name + "</option>");
				}
				continentData = data;
			}
		}
	});
}

function delBranch(obj) {
	// 退回修改状态
	var currSupplierSt = $("#supplierSt").val();
	var audit = $("#audit").val();
	if (currSupplierSt == '2') {
		var thisLi = $(obj).parents("li[name='branch']");
		var branchId = thisLi.find("input[name^='branchList'][name$='id']");
		branchId = branchId.val();
		if (audit.indexOf("organizationName_" + branchId) < 0
				&& audit.indexOf("countryName_" + branchId) < 0
				&& audit.indexOf("detailAddress_" + branchId) < 0) {
			layer.msg("审核通过项不能删除!");
			return;
		}
	}

	var btnCount = 0;
	$("#branch_list_body").find("input[type='button']").each(function() {
		btnCount++;
	});
	if (btnCount == 2) {
		layer.msg("境外分支信息必须至少保留一个!", {
			offset : '300px'
		});
	} else {
		var li = $(obj).parent().parent().next();
		var pre = $(obj).parent().parent().prev();
		$(li).remove();
		$(pre).prev().prev().remove();
		$(pre).prev().remove();
		$(pre).remove();
		$(obj).parent().parent().remove();
	}
	var id = $(obj).next().val();
	if (id) {
		$.ajax({
			url : globalPath + "/supplier/deleteBranch.do",
			type : "post",
			data : {
				"id" : id
			},
			success : function(data) {

			}
		});
	}
}

// 选中营业期限
function checkYyqx(obj) {
	var currSupplierSt = $("#supplierSt").val();
	var audit = $("#audit").val();
	if (currSupplierSt == '2'
			&& audit.indexOf('businessStartDate') < 0) {
		return false;
	}
	var ch = $(obj).is(":checked");
	if (ch) {
		$(obj).val("1");
		$("#expireDate").val("");
		$("#expireDate").attr("value", "");
	} else {
		$(obj).val("0");
	}
}

// 控制营业期限
function controlExpireDate() {
	var currSupplierSt = $("#supplierSt").val();
	var audit = $("#audit").val();
	if (currSupplierSt == '2'
			&& audit.indexOf('businessStartDate') < 0) {
		return;
	}
	var branchName = $("input[name='branchName']").val();
	if (branchName != '1') {
		WdatePicker();
	}
}

function controlForm() {
	// 如果供应商状态是退回修改，控制表单域的编辑与不可编辑
	var currSupplierSt = $("#supplierSt").val();
	// alert(currSupplierSt);
	if (currSupplierSt == '2') {
		$("input[type='text'],select,textarea").attr('disabled', true);
		// enableForm();// 调试用代码
		// $("input[type='text'],select,textarea").removeAttr('readonly');//调试用代码
		$("input[type='text'],select,textarea").each(function() {
			// 或者$(this).attr("style").indexOf("border: 1px
			// solid #ef0000;") > 0
			// 或者$(this).css("border") == '1px solid rgb(239, 0,
			// 0)'
			if ($(this).css("border-top-color") == 'rgb(255, 0, 0)'
				|| $(this).css("border-bottom-color") == 'rgb(255, 0, 0)'
				|| $(this).css("border-left-color") == 'rgb(255, 0, 0)'
				|| $(this).css("border-right-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-top-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-bottom-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-left-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-right-color") == 'rgb(255, 0, 0)') {
				$(this).attr('disabled', false);
			}
		});
		/*
		 * $("select").change(function(){ this.selectedIndex=this.defaultIndex;
		 * });
		 */
		// 营业期限复选框
		var audit = $("#audit").val();
		if (audit.indexOf('businessStartDate') < 0) {
			$("input[type='checkbox'][name='branchName']").attr('disabled',
					true);
		}
		// 营业期限选择器
		if ($("input[type='checkbox'][name='branchName']").val() == "1") {
			$("#expireDate").attr('disabled', true);
		}
	}
}

// 表单可编辑
function enableForm() {
	var currSupplierSt = $("#supplierSt").val();
	if (currSupplierSt == '2') {
		$("input[type='text'],input[type='checkbox'],select,textarea").attr(
				'disabled', false);
	}
}

// 表单只读
function readOnlyForm() {
	// 如果供应商状态是退回修改，控制表单域的编辑与不可编辑
	var currSupplierSt = $("#supplierSt").val();
	if (currSupplierSt == '2') {
		// $("input[type='text'],textarea").attr('readonly', 'readonly');
		$("input[type='text'],textarea").each(function() {
			if (boolColor(this)) {
				$(this).removeAttr('readonly');
			} else {
				$(this).attr('readonly', 'readonly');
			}
		});
		// 营业期限
		$("#expireDate").attr('readonly', 'readonly');

		$("select")
//		.not("#stockholder_list_tbody_id select")// 特殊处理出资人信息
//		.not("#address_list_tbody_id select")// 特殊处理生产经营地址
		.focus(function() {
			if (!boolColor(this)) {
				this.defaultIndex = this.selectedIndex;
				$(this).removeAttr("onchange");
			}
		}).change(function() {
			if (!boolColor(this)) {
				this.selectedIndex = this.defaultIndex;
			}
		});
	}
	// 特殊处理出资人信息
//	$("#stockholder_list_tbody_id input").removeAttr('readonly');
	// 特殊处理生产经营地址
//	$("#address_list_tbody_id input").removeAttr('readonly');
	
	// readonly属性去掉blur事件
	$("input[type='text'][readonly='readonly'],textarea[readonly='readonly']").removeAttr("onblur");
}

function boolColor(_this) {
	var boolColor = $(_this).css("border-top-color") == 'rgb(255, 0, 0)'
			|| $(_this).css("border-bottom-color") == 'rgb(255, 0, 0)'
			|| $(_this).css("border-left-color") == 'rgb(255, 0, 0)'
			|| $(_this).css("border-right-color") == 'rgb(255, 0, 0)'
			|| $(_this).parents("td").css("border-top-color") == 'rgb(255, 0, 0)'
			|| $(_this).parents("td").css("border-bottom-color") == 'rgb(255, 0, 0)'
			|| $(_this).parents("td").css("border-left-color") == 'rgb(255, 0, 0)'
			|| $(_this).parents("td").css("border-right-color") == 'rgb(255, 0, 0)';
	return boolColor;
}

// 审核通过的项不能删除(列表)
function checkIsDelForTuihui(checkedObjs) {
	var currSupplierSt = $("#supplierSt").val();
	var audit = $("#audit").val();
	if (currSupplierSt == '2') {
		var isDel = true;
		$(checkedObjs).each(function(index) {
			if (audit.indexOf($(this).val()) < 0) {
				isDel = false;
				return false;
			}
		});
		return isDel;
	}
	return true;
}

// 校验身份证号码
function checkIdCard(val){
	var result = validateIdCard(val);
	if(result != "success"){
		layer.msg(result);
		return false;
	}
}

// 统一社会信用代码校验
// 18位数字或18位数字+字母
function checkCreditCode(creditCodeValue){
	if(creditCodeValue != ""){
		var bool = false;
		if(/[0-9]{18}/.test(creditCodeValue)){// 18位全数字
			bool = true;
		}
		if(/^([a-zA-Z0-9]){18}$/.test(creditCodeValue)){// 18位数字+字母
			if(/^([a-zA-Z])+$/.test(creditCodeValue)){// 全字母
				bool = false;
			}else{
				bool = true;
			}
		}
		if(!bool){
			var msg = "信用代码18位，请按照实际社会信用代码填写！";
			layer.msg(msg);
		}
		return bool;
	}
}

// 校验出资人统一社会信用代码或身份证号码
function checkStockholdersID(obj){
	var target = $("input[name^='listSupplierStockholders'][name$='identity']");
	if(obj){
		target = $(obj);
	}
	target.blur(function(){
		var index = $(this).attr("name").replace("listSupplierStockholders[", "").replace("].identity", "");
		var nature = $("select[name='listSupplierStockholders["+index+"].nature'").val();
		var identityType = $("select[name='listSupplierStockholders["+index+"].identityType'").val();
		// 如果是法人
		if(nature == 1 && identityType == 1){
			checkCreditCode(this.value);
		}
		// 如果是自然人
		if(nature == 2 && identityType == 1){
			checkIdCard(this.value);
		}
	});
	$("select[name^='listSupplierStockholders'][name$='identityType']").each(function(){
		setIdentityMaxLength(this);
	});
	$("select[name^='listSupplierStockholders'][name$='identityType']").change(function(){
		setIdentityMaxLength(this);
	});
}

function setIdentityMaxLength(_this){
	var index = $(_this).attr("name").replace("listSupplierStockholders[", "").replace("].identityType", "");
	var identity = $("input[name='listSupplierStockholders["+index+"].identity']");
	if($(_this).val() == 1){
		identity.attr("maxlength", 18);
	}
	if($(_this).val() == 2){
		identity.attr("maxlength", 60);
	}
}

// 出资人性质下拉事件
function onchangeNature(value, index){
	var selIdentityType = $("select[name='listSupplierStockholders["+index+"].identityType']");
	if(value == 1){
		selIdentityType.html("");
		selIdentityType.append("<option value='1'>统一社会信用代码</option>");
		selIdentityType.append("<option value='2'>其他</option>");
	}
	if(value == 2){
		selIdentityType.html("");
		selIdentityType.append("<option value='1'>居民二代身份证</option>");
		selIdentityType.append("<option value='2'>其他</option>");
	}
	$("input[name='listSupplierStockholders["+index+"].identity']").attr("maxlength", 18);
}

//核对字符长度
function checkCharLimit(inputId, countId, limit) {
	var inputVal = $("#" + inputId).val();
	var inputLen = inputVal ? inputVal.length : 0;
	$("#" + countId).text(limit - inputLen);
}

// ********************以下为注释不用代码********************

/*var infotd;
var filetd;

function addFinance(obj, year) {
	infotd = $(obj).parent().next().children(":first").children(":last");
	filetd = $(obj).parent().next().children(":last").children(":last");
	var supplierId = $("input[name='id']").val();
	if (!supplierId) {
		layer.msg("请暂存供应商基本信息！", {
			offset : '300px',
		});
	} else {
		layer.open({
			type : 2,
			title : '添加供应商财务信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '650px', '420px' ], // 宽高
			offset : '100px',
			scrollbar : false,
			content : globalPath
					+ '/supplier_finance/add_finance.html?&supplierId='
					+ supplierId + '&sign=1&&year=' + year, // url
			closeBtn : 1, // 不显示关闭按钮
		});
	}
}

function delFinance() {
	var checkboxs = $("#finance_list_tbody_id").find(":checkbox:checked");
	var financeIds = "";
	var supplierId = $("input[name='id']").val();
	$(checkboxs).each(function(index) {
		if (index > 0) {
			financeIds += ",";
		}
		financeIds += $(this).val();
	});
	var size = checkboxs.length;
	if (size > 0) {
		layer.confirm("已勾选" + size + "条记录, 确定删除！", {
			offset : '200px',
			scrollbar : false,
		}, function(index) {
			window.location.href = globalPath
					+ "/supplier_finance/delete_finance.html?financeIds="
					+ financeIds + "&supplierId=" + supplierId;
			layer.close(index);

		});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}*/

// 加载地区根节点
/*function loadRootArea() {
	$.ajax({
		url : globalPath + "/area/find_root_area.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			var html = "";
			html += "<option value=''>请选择</option>";
			for ( var i = 0; i < result.length; i++) {
				html += "<option id='" + result[i].id + "' value='"
						+ result[i].id + "'>" + result[i].name + "</option>";
			}
			$("#root_area_select_id").append(html);

			// 自动选中
			var rootArea = $("#supplierAddr").val();
			if (rootArea)
				rootArea = rootArea.split(",")[0];
			if (rootArea) {
				autoSelected("root_area_select_id", rootArea);
				loadChildren();
			}
		}
	});
}*/

// 加载地区子节点
/*function loadChildren(obj) {
	var id = $(obj).val();
	if (id == "") {
		var select = $(obj).parent().next().children();
		$(select).empty();
	}
	if (id) {
		$.ajax({
			url : globalPath + "/area/find_area_by_parent_id.do",
			type : "post",
			dataType : "json",
			data : {
				id : id
			},
			success : function(result) {
				var html = "";
				for ( var i = 0; i < result.length; i++) {
					html += "<option value='" + result[i].id + "'>"
							+ result[i].name + "</option>";
				}
				var select = $(obj).parent().next().children();
				$(select).empty();
				$(select).append(html);
			}
		});
	}
}*/

// 下载文件
/*function downloadFile(obj) {
	var id = $(obj).parent().children(":last").val();
	var key = 1;
	var form = $("<form>");
	form.attr('style', 'display:none');
	form.attr('method', 'post');
	form.attr('action', globalPath + '/file/download.html?id=' + id + '&key='
			+ key);
	$('body').append(form);
	form.submit();
}*/

/*function checknums(obj) {
var vals = $(obj).val();
if (vals != "") {
	var reg = /^\d+\.?\d*$/;
	if (!reg.test(vals)) {
		$(obj).val("");
		$("#err_fund").text("数字非法");
		// 解决多提示信息显示问题
		$(obj).nextAll().last().html("");
	} else {
		$("#err_fund").text();
		$("#err_fund").empty();
	}
}
}*/

// 对于金额的小数判断
/*function checkNumsSale(obj, nonNum) {
	var _val = $(obj).val();
	if (_val != "" && nonNum != 3) {// 如果可以为负数的话设置3;净资产总额不进行负数校验
		if (parseInt(_val) < 0) {
			$(obj).val("");
			layer.msg("请输入正确的金额,非负数保留4位小数", {
				offset : '300px'
			});
			return false;
		}
	}
	if (_val.indexOf('.') != -1) {
		var reg = /\d+\.\d{0,4}?$/;
		if (!reg.test(_val)) {
			$(obj).val("");
			if (nonNum == 3) {
				layer.msg("请输入正确的金额,保留4位小数", {
					offset : '300px'
				});
			} else {
				layer.msg("请输入正确的金额,非负数保留4位小数", {
					offset : '300px'
				});
			}
		}
	} else {
		if (!positiveRegular(_val)) {
			$(obj).val("");
			if (nonNum == 3) {
				layer.msg("请输入正确的金额,保留4位小数", {
					offset : '300px'
				});
			} else {
				layer.msg("请输入正确的金额,非负数保留4位小数", {
					offset : '300px'
				});
			}
		}
	}
}*/

// 比例校验
/*function loadProportion() {
	$(".proportion_vali").focus(function() {
		$(this).attr("data-oval", $(this).val()); //将当前值存入自定义属性
	}).blur(function() {
		var oldVal = ($(this).attr("data-oval")); //获取原值
		var newVal = ($(this).val()); //获取当前值
		if (oldVal != newVal) {
			var _val = $(this).val();
			if (_val.indexOf('.') != -1) {
				if (parseFloat(_val) > 100) {
					$(this).val("");
					layer.msg("请输入正确的比例数据格式,不能超过100", {
						offset : '300px'
					});
				} else {
					var reg = /\d+\.\d{0,2}?$/;
					if (!reg.test(_val)) {
						$(this).val("");
						layer.msg("请输入正确的金额,保留两位小数", {
							offset : '300px'
						});
					}
				}
			} else {
				if (!positiveRegular(_val)) {
					$(this).val("");
					layer.msg("请输入正确的比例数据格式,保留两位小数", {
						offset : '300px'
					});
				} else if (parseInt(_val) > 100) {
					$(this).val("");
					layer.msg("请输入正确的比例数据格式,不能超过100", {
						offset : '300px'
					});
				}
				;
			}
		}
	});
}*/

/*function checkAllForFinance(ele) {
	var flag = $(ele).prop("checked");
	$("#finance_list_tbody_id").find("input:checkbox").prop("checked", flag);
	$("#finance_attach_list_tbody_id").find("input:checkbox").prop("checked",
		flag);
}*/