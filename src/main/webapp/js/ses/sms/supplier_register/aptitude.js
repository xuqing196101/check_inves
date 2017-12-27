$(function() {
	var flag_proQua = $("#flag_proQua").val();
	var flag_saleQua = $("#flag_saleQua").val();
	var flag_projectQua = $("#flag_projectQua").val();
	var flag_serviceQua = $("#flag_serviceQua").val();
	if (flag_proQua == "false" && flag_saleQua == "false" 
		&& flag_projectQua == "false" && flag_serviceQua == "false") {
		layer.alert("没有需要上传的资质文件，请直接点击下一步！");
	}

	var aptitude_error = $("#aptitude_error").val();
	if (aptitude_error == "notComplete") {
		layer.alert("资质文件没有上传完毕！");
	}
	
	// controlForm();
	readOnlyForm();

	/*
	 * //第二步 被修改过的证书编号 var modifiedCertCodes = "${modifiedCertCodes}";
	 * if(modifiedCertCodes){ var modifiedCertCodesArray =
	 * modifiedCertCodes.split("-"); for ( var i = 0; i <
	 * modifiedCertCodesArray.length; i++) { var obj =
	 * $("[value='"+modifiedCertCodesArray[i]+"']"); var index =
	 * obj.attr("label"); getFileByCode(obj,index,"2"); } }
	 */
});

// 暂存
function saveItems() {
	$.ajax({
		url : globalPath + "/supplier/saveItemsInfo.do",
		type : "post",
		data : $("#item_form").serializeArray(),
		success : function(msg) {
			if (msg == 'ok') {
				layer.msg('暂存成功');
			}
			if (msg == 'failed') {
				layer.msg('暂存失败');
			}
		}
	});
}

// 无提示暂存
function tempSave() {
	$.ajax({
		url : globalPath + "/supplier/saveItemsInfo.do",
		type : "post",
		data : $("#item_form").serializeArray(),
		success : function(msg) {
			return "0";
		}
	});
}

// 下一步
function next() {
	var flag = isAptitue();
	if (flag == true) {
		$.ajax({
			url : globalPath + "/supplier/saveItemsInfo.do",
			type : "post",
			data : $("#item_form").serializeArray(),
			success : function(msg) {
				$("#aptitude_form").submit();
			}
		});
	} else {
		layer.alert("请完善工程资质证书信息！");
	}
}

// 上一步
function prev() {
	tempSave();
	updateStep(3);
}

function onkeydownCertCode(_this) {
	$(_this).attr("oldCode", $(_this).val());
}

function onkeyupCertCode(_this, index) {
	var newVal = $(_this).val();
	var oldVal = $(_this).attr("oldCode");
	if (oldVal != newVal) {
		getFileByCode(_this, index, '2');
	}
}

// 根据证书编号获取附件信息
function getFileByCode(obj, number, flag) {
	var supplierId = $("#supplierId").val();
	var certCode = "";
	var professType = "";
	if (flag == "1") {
		certCode = $(obj).parent().next().find("input").val();
		// 清空编号
		$(obj).parent().next().find("input[type='text']").val("");
		// 清空专业类别下拉框
		$(obj).parent().next().next().find("select").empty();
		// 清空资质等级
		$(obj).parent().next().next().next().find("input[type='text']").val("");
		$(obj).parent().next().next().next().find("input[type='hidden']").val("");
		// 清空预览图片
		$(obj).parents("tr").find("td:last").empty();
		professType = $(obj).parent().next().next().children().val();
	} else if (flag == "2") {
		certCode = $(obj).val();
		typeId = $(obj).parent().prev().find("select").val();
		// 清空等级和附件
		// $(obj).parent().next().find("input[type='text']").val("");
		// 清空专业类别下拉框
		$(obj).parent().next().find("select").empty();
		// 清空资质等级
		$(obj).parent().next().next().find("input[type='text']").val("");
		$(obj).parent().next().next().find("input[type='hidden']").val("");
		$.ajax({
			url : globalPath + "/supplier/getProType.do",
			type : "post",
			data : {
				"typeId" : typeId,
				"certCode" : certCode,
				"supplierId" : supplierId
			},
			dataType : "json",
			async : false,
			success : function(data) {
				var select = $(obj).parent().next().children();
				var html = "<option value=''>请选择</option>";
				$(data).each(function(i) {
					html += "<option value=" + data[i] + ">" + data[i] + "</option>";
				});
				$(select).append(html);
			}
		});
		// $(obj).parent().next().next().next().html("");
		// 清除图片显示图标
		$(obj).parents("tr").find("td:last").empty();
		// professType=$(obj).parent().next().children().val();
	} else {
		$(obj).parent().next().find("input[type='text']").val("");
		$(obj).parent().next().find("input[type='hidden']").val("");
		certCode = $(obj).parent().prev().children().val();
		$(obj).parents("tr").find("td:last").empty();
		professType = $(obj).val();
	}

	var typeId = "";
	if (flag == "1") {
		typeId = $(obj).val();
	} else if (flag == "2") {
		typeId = $(obj).parent().prev().find("select").val();
	} else if (flag == "3") {
		typeId = $(obj).parent().prev().prev().find("select").val();
	}
	if (typeId != null && typeId != "" && typeId != "undefined"
			&& certCode != null && certCode != "" && certCode != "undefined"
			&& professType != null && professType != "") {
		getData(obj, typeId, certCode, supplierId, professType, number, flag);
	}
	tempSave();
}
// 请求 获取 数据
function getData(obj, typeId, certCode, supplierId, professType, number, flag) {
	// 根据类型和证书编号获取等级
	$.ajax({
		url : globalPath + "/supplier/getLevel.do",
		type : "post",
		data : {
			"typeId" : typeId,
			"certCode" : certCode,
			"supplierId" : supplierId,
			"professType" : professType
		},
		dataType : "json",
		async : false,
		success : function(result) {
			if (result != null && result != "") {
				if (flag == "1") {
					$(obj).parent().next().next().find(
							"input[type='text']").val(result.name);
					$(obj).parent().next().next().find(
							"input[type='hidden']").val(result.id);
				} else if (flag == "0") {
					$(obj).parent().find("input[type='text']").val(
							result.name);
					$(obj).parent().next().find("input[type='hidden']")
							.val(result.id);
				} else {
					$(obj).parent().next().find("input[type='text']")
							.val(result.name);
					$(obj).parent().next().find("input[type='hidden']")
							.val(result.id);
				}
				// 通过append将附件信息追加到指定位置
				$.ajax({
					url : globalPath + "/supplier/getFileByCode.do",
					type : "post",
					async : false,
					dataType : "html",
					data : {
						"typeId" : typeId,
						"certCode" : certCode,
						"supplierId" : supplierId,
						"number" : number,
						"professType" : professType
					},
					success : function(data) {
						if (flag == "1") {
							$(obj).parent().next().next()
									.next().next().html(data);
						} else {
							$(obj).parent().next().next()
									.next().html(data);
						}
						init_web_upload();
					}
				});
			}
		}
	});
}

function isAptitue() {
	var flag = true;
	// 审核不通过的供应商类型不校验
	var infoSupplierTypeAudit = $("#infoSupplierTypeAudit").val();
	if (infoSupplierTypeAudit.indexOf("PROJECT") >= 0) {
		return true;
	}
	$("input[type='text']").each(function() {
		if ($(this).val() == "") {
			flag = false;
		}
	});
	return flag;
}

function controlForm() {
	// 如果供应商状态是退回修改，控制表单域的编辑与不可编辑
	var currSupplierSt = $("#supplierSt").val();
	// console.log(currSupplierSt);
	if (currSupplierSt == '2') {
		$("input[type='text'],select,textarea").attr('disabled', true);
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
	// alert(currSupplierSt);
	if (currSupplierSt == '2') {
		// $("input[type='text'],textarea").attr('readonly', 'readonly');
		$("input[type='text'],textarea").each(function() {
			if (boolColor(this)) {
				$(this).removeAttr('readonly');
			} else {
				$(this).attr('readonly', 'readonly');
				$(this).removeAttr("onblur").removeAttr("onchange");
			}
		});

		$("select").focus(function() {
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
function checkIsDelForTuihui(checkedObjs, audit) {
	var currSupplierSt = $("#supplierSt").val();
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

sessionStorage.locationD = true;
sessionStorage.index = 4;

//控制其它等级的显示和影藏
/*function disLevel(obj) {
	if ($(obj).val() == "其它") {
		$(obj).next().removeClass("dis_none");
	} else {
		$(obj).next().addClass("dis_none");
	}
	tempSave();
}*/

/* 获取内层div的最大高度赋予外层div */
/*function psize() {
	var temp_heights = []
	$(".fades").each(function() {
		temp_heights.push($(this).outerHeight());
	})
	$("#tab_content_div_id").outerHeight(Math.max.apply(null, temp_heights));
}*/