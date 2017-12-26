$().ready(function() {
	$("#save_pro_form_id").validForm();
});

$(function() {
	window.onload = function() {
		// 工程类--承揽业务范围
		var businessScope = $("#businessScope").val();
		$("#areaSelect").find("option").each(function(i, element) {
			if (businessScope.indexOf(element.value) != -1) {
				element.selected = true;
				$("#area_" + element.value).show();
				init_web_upload();
			} else {
				element.selected = false;
				$("#area_" + element.value).hide();
			}
		});

		// 工程类--资质等级
		$("select[id^='certType_']").each(function(i, element) {
			getAptLevel($(this));
		});

		// 去掉实时保存
		/*
		 * $("input").not(".validatebox-text").not("input[type='button']").bind("blur",
		 * tempSave); $("textarea").bind("blur", tempSave);
		 * $("select").bind("change", tempSave);
		 * $(".certTypeSelect").unbind("blur", tempSave);
		 */
		
		var flagSupplierType = $("#flagSupplierType").val();
		var flagProduct = $("#flagProduct").val();
		var flagSell = $("#flagSell").val();
		var flagProject = $("#flagProject").val();
		var flagServer = $("#flagServer").val();
		var flagTypeAudit = $("#flagTypeAudit").val();
		
		if (flagSupplierType == "false") {
			layer.msg("请选择供应商类型！");
		}
		if (flagTypeAudit == "false") {
			layer.msg("没有审核通过的供应商类型！");
		}
		var msg = "";
		if (flagProduct == "false") {
			msg = msg + "物资-生产专业信息、";
		}
		if (flagSell == "false") {
			msg = msg + "物资-销售专业信息、";
		}
		if (flagProject == "false") {
			msg = msg + "工程专业信息、";
		}
		if (flagServer == "false") {
			msg = msg + "服务专业信息、";
		}
		if (msg != "") {
			var msg = msg.substring(0, msg.length - 1);
			layer.msg(msg + "没有通过校验！");
		}
		var checkeds = $("#supplierTypes").val();
		if (checkeds != "" && checkeds != "null") {
			$("#tab_div").show();
			$("#tab_content_div_id").show();
		}
		var arrays = checkeds.split(",");
		var checkedArray = [];
		var checkBoxAll = $("input[name='chkItem']");
		var supplierId = $("#supplierId").val();
		/*
		 * $.ajax({ url:
		 * globalPath + "/supplier/isPass.do", data: {
		 * "supplierId": supplierId, "stype":"SALES" }, type: "post", //async:
		 * false,// 同步 success: function(data) { if (data == "1") { } else {
		 * $.each(checkBoxAll,function(j, checkbox) { var checkValue =
		 * $(checkbox).val(); if(checkValue=="SALES"){
		 * $(checkbox).attr("disabled","disabled"); } }); } } });
		 */

		if (arrays.length > 0) {
			initTabTitleCss();
			for ( var i = 0; i < arrays.length; i++) {
				$.each(checkBoxAll, function(j, checkbox) {
					// 获取复选框的value属性
					var checkValue = $(checkbox).val();
					if (arrays[i] == checkValue) {
						$(checkbox).attr("checked", true);
						if (arrays[i] != 'PROJECT') {
							$("#project_div").attr("class", "dis_none fades ");
						}
						if (arrays[i] != 'PRODUCT') {
							$("#production_div").attr("class",
									"dis_none fades ");
						}
						if (arrays[i] != 'SALES') {
							$("#sale_div").attr("class", "dis_none fades ");
						}
						if (arrays[i] != 'SERVICE') {
							$("#server_div").attr("class", "dis_none fades ");
						}
						if (arrays[i] == 'PRODUCT') {
							$("#productId").show();
							$("#production_div").attr("class",
									"fades active in");
						} else if (arrays[i] == 'SALES') {
							$("#salesId").show();
							$("#sale_div").attr("class", "fades active in");
						} else if (arrays[i] == 'PROJECT') {
							$("#projectId").show();
							$("#project_div").attr("class", "fades active in");
						} else if (arrays[i] == 'SERVICE') {
							$("#serviceId").show();
							$("#server_div").attr("class", "fades active in");
						}
						checkedArray.push(arrays[i]);
					}
				});
			}
		}

		if (checkedArray.length == 0) {
			$("#tab_div").attr("class", "container opacity_0");
		} else {
			$("#tab_div").attr("class", "container opacity_1");
		}
		var first = checkedArray[0];
		if (first != null && first != "" && first != "undefined") {
			loadTab(first);
		}
		
		checkCharLimit('countryPro','limit_char_countryPro',1000);// 承担国家军队科研项目
		checkCharLimit('countryReward','limit_char_countryReward',1000);// 获得国家军队科技奖项
		checkCharLimit('conAchi','limit_char_conAchi',1000);// 国家或军队保密工程业绩
		
		controlForm();
	}
});

// 显示生产的信息
function product(obj) {
	if (obj == "PRODUCT") {
		$("#productId").show();
	}
}
// 显示销售的信息
function sales(obj) {
	if (obj == "SALES") {
		$("#salesId").show();
	}
}
// 显示工程的信息
function project(obj) {
	if (obj == "PROJECT") {
		$("#projectId").show();
	}
}
// 显示服务的信息
function services(obj) {
	if (obj == "SERVICE") {
		$("#serviceId").show();
	}
}

// 初始化所有的tab标题
function hideTabTitle() {
	$("#productId").hide();
	$("#salesId").hide();
	$("#projectId").hide();
	$("#serviceId").hide();
}

// 初始化tab的标题样式
function initTabTitleCss() {
	$("#productId").removeClass("active");
	$("#salesId").removeClass("active");
	$("#projectId").removeClass("active");
	$("#serviceId").removeClass("active");
}

// 选中信息头
function checks(obj) {
	hideTabTitle();
	var value = $(obj).val();
	if (value == 'SALES') {
		isSalePass(value);
	}
	var selectedArray = [];
	$("input[name='chkItem']:checked").each(function(index, ele) {
		$("#tab_div").addClass("opacity_1");
		var v = $(ele).val();
		selectedArray.push(v);
		product(v);
		sales(v);
		project(v);
		services(v)
	});

	if (selectedArray.length == 0) {
		$("#tab_div").attr("class", "container opacity_0");
	}
	var first = selectedArray[0];

	if (first != null && first != "" && first != "undefined") {
		loadTab(first);
	}
	saveSupplierTypeRelate(selectedArray.join(","));
}

// 页签切换
function loadTab(val) {
	initTabTitleCss();
	$("#production_div").attr("class", "tab-pane fades");
	$("#sale_div").attr("class", "tab-pane fades");
	$("#project_div").attr("class", "tab-pane fades ");
	$("#server_div").attr("class", "tab-pane fades ");
	if (val == 'PRODUCT') {
		$("#productId").addClass("active");
		$("#production_div").attr("class", "tab-pane fades active in");
	}
	if (val == 'SALES') {
		$("#salesId").addClass("active");
		$("#sale_div").attr("class", "tab-pane fades active in");
	}
	if (val == 'PROJECT') {
		$("#projectId").addClass("active");
		$("#project_div").attr("class", "tab-pane fades active in");
	}
	if (val == 'SERVICE') {
		$("#serviceId").addClass("active");
		$("#server_div").attr("class", "tab-pane fades active in");
	}
	init_web_upload();
}

// 上一步
function prev() {
	//tempSave();
	updateStep(1);
}

// 暂存
function ajaxSave() {
	var checkedTypes = [];
	$('input[name="chkItem"]:checked').each(function() {
		checkedTypes.push($(this).val());
	});
	$("input[name='supplierTypeIds']").val(checkedTypes);

	if (checkedTypes.length == 0) {
		layer.msg("请选择供应商类型");
		return false;
	}
	// 设置承揽业务范围
	setBusinessScope();
	// 提交的时候表单域设置成可编辑
	enableForm();
	$.ajax({
		url : globalPath + "/supplier/saveSupplierType.do",
		type : "post",
		data : $("#save_pro_form_id").serializeArray(),
		contextType : "application/x-www-form-urlencoded",
		success : function(msg) {
			layer.msg('暂存成功');
			var data = msg.split(",");
			if (data[0] != "null" && data[0] != null) {
				$("input[name='supplierMatPro.id']").val(data[0]);
			} else {
				$("input[name='supplierMatPro.id']").val("");
			}
			if (data[1] != "null" && data[1] != null) {
				$("input[name='supplierMatSell.id']").val(data[1]);
			} else {
				$("input[name='supplierMatSell.id']").val("");
			}
			if (data[2] != "null" && data[2] != null) {
				$("input[name='supplierMatEng.id']").val(data[2]);
			} else {
				$("input[name='supplierMatEng.id']").val("");
			}
			if (data[3] != "null" && data[3] != null) {
				$("input[name='supplierMatSe.id']").val(data[3]);
			} else {
				$("input[name='supplierMatSe.id']").val("");
			}
			$(":checkbox").removeAttr("isAdd");
			controlForm();
		},
		error : function() {
			layer.msg('暂存失败！');
			controlForm();
		}
	});
}

// 无提示实时暂存
function tempSave(ele) {
	// 避免失去焦点事件和按钮事件冲突
	if (ele && ele.relatedTarget && ele.relatedTarget.type == "button"
			&& ($(ele.relatedTarget).text() != "上一步")) {
		return;
	}
	var checkedTypes = [];
	$('input[name="chkItem"]:checked').each(function() {
		checkedTypes.push($(this).val());
	});

	$("input[name='supplierTypeIds']").val(checkedTypes);
	// 设置承揽业务范围
	setBusinessScope();
	// 提交的时候表单域设置成可编辑
	enableForm();
	$.ajax({
		url : globalPath + "/supplier/saveSupplierType.do",
		type : "post",
		data : $("#save_pro_form_id").serialize(),
		contextType : "application/x-www-form-urlencoded",
		success : function(msg) {
			var data = msg.split(",");
			if (data[0] != "null" && data[0] != null) {
				$("input[name='supplierMatPro.id']").val(data[0]);
			} else {
				$("input[name='supplierMatPro.id']").val("");
			}
			if (data[1] != "null" && data[1] != null) {
				$("input[name='supplierMatSell.id']").val(data[1]);
			} else {
				$("input[name='supplierMatSell.id']").val("");
			}
			if (data[2] != "null" && data[2] != null) {
				$("input[name='supplierMatEng.id']").val(data[2]);
			} else {
				$("input[name='supplierMatEng.id']").val("");
			}
			if (data[3] != "null" && data[3] != null) {
				$("input[name='supplierMatSe.id']").val(data[3]);
			} else {
				$("input[name='supplierMatSe.id']").val("");
			}
			$(":checkbox").removeAttr("isAdd");
			controlForm();
		}
	});
}

// 保存供应商类型关系
function saveSupplierTypeRelate(supplierTypeIds) {
	var supplierId = $("#supplierId").val();
	$.ajax({
		url : globalPath + "/supplier/saveSupplierTypeRelate.do",
		type : "post",
		data : {
			supplierId : supplierId,
			supplierTypeIds : supplierTypeIds
		},
		success : function(msg) {

		}
	});
}

// 判断类型是否审核不通过
function isSupplierTypeEnable(type) {
	var supplierSt = $("#supplierSt").val();
	var infoSupplierTypeAudit = $("#infoSupplierTypeAudit").val();
	if (supplierSt == '2') {
		return (infoSupplierTypeAudit.indexOf(type) == -1);
	}
	return true;
}

// 下一步
function next(obj) {
	var checkedTypes = [];
	$('input[name="chkItem"]:checked').each(function() {
		checkedTypes.push($(this).val());
	});
	var bool1 = false;
	var bool2 = false;
	for ( var i = 0; i < checkedTypes.length; i++) {
		if (checkedTypes[i] == 'GOODS') {
			bool1 = true;
		}
		if (checkedTypes[i] == 'SALES' || checkedTypes[i] == 'PRODUCT') {
			bool2 = true;
		}
	}
	var flag = true;
	$("input[name='supplierTypeIds']").val(checkedTypes);
	$("input[name='flag']").val(obj);
	if (bool1 == true && bool2 != true) {
		layer.msg("请勾选产品货物类属性");
	} else {
		if (checkedTypes.length > 0) {
			flag = true;
		} else {
			flag = false;
			layer.msg("请选择供应商类型");
		}
	}
	// 判断有没有勾选物资生产
	var isProCheck = false;// 物资生产
	var isSaleCheck = false;// 物资销售
	var isEngCheck = false;// 工程
	var isServerCheck = false;// 服务
	var supplierTypeAuditStr = $("#supplierTypeAuditStr").val();
	$("input[name='chkItem']").each(function(index, element) {
		if (element.value == "PRODUCT" && element.checked == true) {
			isProCheck = true;
		}
		if (element.value == "SALES" && element.checked == true) {
			isSaleCheck = true;
		}
		if (element.value == "PROJECT" && element.checked == true) {
			isEngCheck = true;
		}
		if (element.value == "SERVICE" && element.checked == true) {
			isServerCheck = true;
		}
	});
	if (isProCheck == true && isSupplierTypeEnable("PRODUCT")) {
		$("#cert_pro_list_tbody_id").find("input[type='text']").each(
			function(index, element) {
				if ($.trim(element.value) == "" || !isProCheck) {
					flag = false;
					layer.msg("物资生产资质证书信息不能为空！");
				}
			});
	}
	// 判断有没有勾选工程
	if (isEngCheck == true && isSupplierTypeEnable("PROJECT")) {
		$("#cert_eng_list_tbody_id").find("input[type='text']").each(
			function(index, element) {
				if ($.trim(element.value) == "" || !isEngCheck) {
					flag = false;
					layer.msg("工程资质（认证）证书信息不能为空！");
				}
			});
		$("#aptitute_list_tbody_id").find("input[type='text']").each(
			function(index, element) {
				if ($.trim(element.value) == "" || !isEngCheck) {
					flag = false;
					layer.msg("工程资质证书详细信息不能为空！");
				}
			});
		// 要填则必须填写完整
		$("#eng_qua_list_tbody_id").find("tr").each(
			function(index, element) {
				var count = 0;// 统计没有填写的数量
				var size = 0; // 总共需要填写的数量
				$(this).find("td").not(":first").each(
					function(n, e) {
						var txt = $(this).find("input[type='text']").val();// 文本
						var pic = $(this).find("ul[id^='eng_qua_show_'][id$='_disFileId']").html();// 图片
						if ((txt !== undefined && $.trim(txt) == "") || (pic !== undefined && $.trim(pic) == "")) {
							count++;
						}
						size++;
					});
				// console.log(count+","+size);
				if (count != 0 && count < size) {
					flag = false;
					layer.msg("工程资质证书信息没有填写完整(第" + (index + 1) + "行)！");
					return false;
				}
			});
	}

	// 判断物资销售专业信息是否填写完整
	if (isSaleCheck == true && isSupplierTypeEnable("SALES")) {
		/*
		 * $("#cert_sell_list_tbody_id").find("input[type='text']").each(
		 * function(index, element) { if (element.value == "" || !isSaleCheck) {
		 * flag = false; layer.msg("物资销售资质证书信息没有填写完整!"); } });
		 */
		// 要填则必须填写完整
		$("#cert_sell_list_tbody_id").find("tr").each(
			function(index, element) {
				var count = 0;// 统计没有填写的数量
				var size = 0; // 总共需要填写的数量
				$(this).find("td").not(":first").each(
					function(n, e) {
						var txt = $(this).find("input[type='text']").val();// 文本
						var pic = $(this).find("ul[id^='sale_show_'][id$='_disFileId']").html();// 图片
						if ((txt !== undefined && $.trim(txt) == "") || (pic !== undefined && $.trim(pic) == "")) {
							count++;
						}
						size++;
					});
				// console.log(count+","+size);
				if (count != 0 && count < size) {
					flag = false;
					layer.msg("物资销售资质证书信息没有填写完整(第" + (index + 1)
							+ "行)！");
					return false;
				}
			});
	}

	// 判断服务专业信息是否填写完整
	if (isServerCheck == true && isSupplierTypeEnable("SERVICE")) {
		/*
		 * $("#cert_se_list_tbody_id").find("input[type='text']").each(
		 * function(index, element) { if (element.value == "" || !isServerCheck) {
		 * flag = false; layer.msg("服务资质证书信息没有填写完整!"); } });
		 */
		// 要填则必须填写完整
		$("#cert_se_list_tbody_id").find("tr").each(
			function(index, element) {
				var count = 0;// 统计没有填写的数量
				var size = 0; // 总共需要填写的数量
				$(this).find("td").not(":first").each(
					function(n, e) {
						var txt = $(this).find("input[type='text']").val();// 文本
						var pic = $(this).find("ul[id^='se_show_'][id$='_disFileId']").html();// 图片
						if ((txt !== undefined && $.trim(txt) == "") || (pic !== undefined && $.trim(pic) == "")) {
							count++;
						}
						size++;
					});
				// console.log(count+","+size);
				if (count != 0 && count < size) {
					flag = false;
					layer.msg("服务资质证书信息没有填写完整(第" + (index + 1)
							+ "行)！");
					return false;
				}
			});
	}

	$("input[name$='expEndDate']").each(function() {
		var startDate = $(this).parent().prev().children(
				"input[name$='expStartDate']").val();
		var tbody_id = $(this).parents("tbody").attr("id");
		if ($(this).val() != "" && startDate != ""
				&& $(this).val() <= startDate) {
			if (tbody_id == "cert_pro_list_tbody_id" && isSupplierTypeEnable("PRODUCT")) {
				flag = false;
				layer.msg("物资生产资质证书-结束时间应大于开始时间！");
			}
			if (tbody_id == "cert_sell_list_tbody_id" && isSupplierTypeEnable("SALES")) {
				flag = false;
				layer.msg("物资销售资质证书-结束时间应大于开始时间！");
			}
			if (tbody_id == "eng_qua_list_tbody_id" && isSupplierTypeEnable("PROJECT")) {
				flag = false;
				layer.msg("工程资质证书-结束时间应大于开始时间！");
			}
			if (tbody_id == "cert_eng_list_tbody_id" && isSupplierTypeEnable("PROJECT")) {
				flag = false;
				layer.msg("工程资质（认证）证书-结束时间应大于开始时间！");
			}
			if (tbody_id == "cert_se_list_tbody_id" && isSupplierTypeEnable("SERVICE")) {
				flag = false;
				layer.msg("服务资质证书-结束时间应大于开始时间！");
			}
			// layer.msg("结束时间应大于开始时间！");
		}
	});
	
	if (flag == true) {
		var index = layer.load(1);
		var supplierId = $("#supplierId").val();
		$.ajax({
			url : globalPath + "/supplier/isPass.do",
			data : {
				"supplierId" : supplierId
			},
			type : "post",
			success : function(data) {
				if (data == "1") {
					// 提交的时候表单域设置成可编辑
					enableForm();
					$("#save_pro_form_id").submit();
					layer.close(index);
				} else {
					layer.confirm(
						"由于您近3年加权平均净资产不足3000万元，不符合物资销售型供应商注册要求，将会清除物资销售型专业数据，是否确认操作？",
						{
							offset : '200px',
							scrollbar : false,
						},
						function(index) {
							$("input[name='old']").val("old");
							// 提交的时候表单域设置成可编辑
							enableForm();
							$("#save_pro_form_id").submit();
							/*
							 * $.ajax({ url:
							 * globalPath + "/supplier/deleteOld.do",
							 * data: { "supplierId":
							 * supplierId }, dataType:
							 * "json", success:
							 * function(data) { alert(data); }
							 * });
							 */
						});
					layer.close(index);
				}
				/*
				 * layer.msg("近3年加权平均净资产不足3000万元，不符合物资销售型供应商注册要求！");
				 * layer.close(index);
				 */
			}
		});
	}
}

function addEngQua() {
	var matEngId = $("input[name='supplierMatEng.id']").val();
	var supplierId = $("input[name='id']").val();
	var engQuaNumber = $("#engQuaNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addEngQua.do",
		async : false,
		dataType : "html",
		data : {
			"number" : engQuaNumber
		},
		success : function(data) {
			$("#eng_qua_list_tbody_id").append(data);
			init_web_upload();
		}
	});
	engQuaNumber++;
	$("#engQuaNumber").val(engQuaNumber);
}

function delEngQua() {
	var checkboxs = $("#eng_qua_list_tbody_id").find(":checkbox:checked");
	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#engPageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm(
			"已勾选" + size + "条记录，确定删除？",
			{
				offset : '200px',
				scrollbar : false,
			},
			function(index) {
				var engQuaIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(n, v) {
					var isAdd = $(this).attr("isAdd");
					if (isAdd) {
						var tr = $(this).parent().parent();
						$(tr).remove();
					} else {
						if (n > 0) {
							engQuaIds += ",";
						}
						engQuaIds += $(this).val();
					}
				});
				/*
				 * window.location.href =
				 * globalPath + "/supplier_eng_qua/delete_eng_qua.html?engQuaIds=" +
				 * engQuaIds + "&supplierId=" + supplierId;
				 * layer.close(index);
				 */
				// 采用ajax post方式删除
				if (engQuaIds != "") {
					$.ajax({
						url : globalPath + "/supplier_eng_qua/delete_eng_qua.do",
						async : false,
						type : "POST",
						data : {
							"ids" : engQuaIds
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
			});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function addRegPerson() {
	var matEngId = $("input[name='supplierMatEng.id']").val();
	var supplierId = $("input[name='id']").val();
	var id;
	$.ajax({
		url : globalPath + "/supplier/getUUID.do",
		async : false,
		success : function(data) {
			id = data;
		}
	});
	var certPersonNumber = $("#certPersonNumber").val();
	$("#reg_person_list_tbody_id").append(
		"<tr>"
		+ "<td class='tc'><input type='checkbox' value='"
		+ id
		+ "' class='border0' isAdd='true'/><input type='hidden' name='supplierMatEng.listSupplierRegPersons["
		+ certPersonNumber
		+ "].id' value='"
		+ id
		+ "'/></td>"
		+ "<td class='tc'><input type='text' class='border0' name='supplierMatEng.listSupplierRegPersons["
		+ certPersonNumber
		+ "].regType'/> </td>"
		+ "<td class='tc'><input type='text' class='border0' name='supplierMatEng.listSupplierRegPersons["
		+ certPersonNumber + "].regNumber'/> </td>"
		+ "</tr>");
	certPersonNumber++;
	$("#certPersonNumber").val(certPersonNumber);
}

function delRegPerson() {
	var checkboxs = $("#reg_person_list_tbody_id").find(":checkbox:checked");
	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#engPageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm(
			"已勾选" + size + "条记录，确定删除？",
			{
				offset : '200px',
				scrollbar : false,
			},
			function(index) {
				var regPersonIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(n, v) {
					var isAdd = $(this).attr("isAdd");
					if (isAdd) {
						var tr = $(this).parent().parent();
						$(tr).remove();
					} else {
						if (n > 0) {
							regPersonIds += ",";
						}
						regPersonIds += $(this).val();
					}
				});
				/*
				 * window.location.href =
				 * globalPath + "/supplier_reg_person/delete_reg_person.html?regPersonIds=" +
				 * regPersonIds + "&supplierId=" + supplierId;
				 * layer.close(index);
				 */
				// 采用ajax post方式删除
				if (regPersonIds != "") {
					$.ajax({
						url : globalPath + "/supplier_reg_person/delete_reg_person.do",
						async : false,
						type : "POST",
						data : {
							"ids" : regPersonIds
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
			});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function addCertEng() {
	var matEngId = $("input[name='supplierMatEng.id']").val();
	var supplierId = $("input[name='id']").val();
	var certEngNumber = $("#certEngNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addEngCert.do",
		async : false,
		dataType : "html",
		data : {
			"number" : certEngNumber
		},
		success : function(data) {
			$("#cert_eng_list_tbody_id").append(data);
			init_web_upload();
		}
	});
	certEngNumber++;
	$("#certEngNumber").val(certEngNumber);
}

function delCertEng() {
	var all = $("#cert_eng_list_tbody_id").find(":checkbox");
	var checkboxs = $("#cert_eng_list_tbody_id").find(":checkbox:checked");

	if (checkboxs.length == all.length) {
		layer.msg("供应商资质（认证）证书信息请至少保留一条！");
		return;
	}

	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#engPageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm(
			"已勾选" + size + "条记录，确定删除？",
			{
				offset : '200px',
				scrollbar : false,
			},
			function(index) {
				var certEngIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(n, v) {
					var isAdd = $(this).attr("isAdd");
					if (isAdd) {
						var tr = $(this).parent().parent();
						$(tr).remove();
					} else {
						if (n > 0) {
							certEngIds += ",";
						}
						certEngIds += $(this).val();
					}
				});
				/*
				 * window.location.href =
				 * globalPath + "/supplier_cert_eng/delete_cert_eng.html?certEngIds=" +
				 * certEngIds + "&supplierId=" + supplierId;
				 * layer.close(index);
				 */
				// 采用ajax post方式删除
				if (certEngIds != "") {
					$.ajax({
						url : globalPath + "/supplier_cert_eng/delete_cert_eng.do",
						async : false,
						type : "POST",
						data : {
							"ids" : certEngIds
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
			});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function addAptitute() {
	var matEngId = $("input[name='supplierMatEng.id']").val();
	var supplierId = $("input[name='id']").val();
	var certAptNumber = $("#certAptNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addAptCert.do",
		async : false,
		dataType : "html",
		data : {
			"number" : certAptNumber
		},
		success : function(data) {
			$("#aptitute_list_tbody_id").append(data);
			init_web_upload();
		}
	});
	certAptNumber++;
	$("#certAptNumber").val(certAptNumber);
}

function delAptitute() {
	var all = $("#aptitute_list_tbody_id").find(":checkbox");
	var checkboxs = $("#aptitute_list_tbody_id").find(":checkbox:checked");

	if (checkboxs.length == all.length) {
		layer.msg("供应商资质证书详细信息请至少保留一条！");
		return;
	}

	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#engPageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm(
			"已勾选" + size + "条记录，删除资质证书，将删除关联的产品类别，确定删除？",
			{
				offset : '200px',
				scrollbar : false,
			},
			function(index) {
				var aptituteIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(n, v) {
					var isAdd = $(this).attr("isAdd");
					if (isAdd) {
						var tr = $(this).parent().parent();
						$(tr).remove();
					} else {
						if (n > 0) {
							aptituteIds += ",";
						}
						aptituteIds += $(this).val();
					}
				});
				/*
				 * window.location.href =
				 * globalPath + "/supplier_aptitute/delete_aptitute.html?aptituteIds=" +
				 * aptituteIds + "&supplierId=" + supplierId;
				 * layer.close(index);
				 */
				// 采用ajax post方式删除
				if (aptituteIds != "") {
					$.ajax({
						url : globalPath + "/supplier_aptitute/delete_aptitute.do",
						async : false,
						type : "POST",
						data : {
							"ids" : aptituteIds
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
			});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

// 撤销删除资质信息
function undoDelAptitute() {
	var supplierId = $("#supplierId").val();
	var certAptNumber = $("#certAptNumber").val();
	$.ajax({
		url : globalPath + "/supplier/undoDelAptitude.do",
		async : false,
		dataType : "html",
		data : {
			supplierId : supplierId,
			ind : certAptNumber
		},
		success : function(data) {
			$("#aptitute_list_tbody_id").append(data);
			//init_web_upload();
			var undoCount = $("#undoCount").val();
			$("#certAptNumber").val(parseInt(certAptNumber) + parseInt(undoCount));
		}
	});
}

function addCertPro() {
	var matProId = $("input[name='supplierMatPro.id']").val();
	var supplierId = $("input[name='id']").val();
	var certProNumber = $("#certProNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addProductCert.do",
		async : false,
		dataType : "html",
		data : {
			"number" : certProNumber
		},
		success : function(data) {
			$("#cert_pro_list_tbody_id").append(data);
			init_web_upload();
		}
	});
	certProNumber++;
	$("#certProNumber").val(certProNumber);
}

function delCertPro() {
	var allCertProCount = 0;// 所有的质量管理体系认证证书数量
	var checkedCertProCount = 0;// 已选的质量管理体系认证证书数量
	var all = $("#cert_pro_list_tbody_id").find(":checkbox");
	var checkboxs = $("#cert_pro_list_tbody_id").find(":checkbox:checked");

	if (checkboxs.length == all.length) {
		layer.msg("资质证书信息请至少保留一条！");
		return;
	}

	$(all).each(function(index) {
		var certPropName = $(this).parent().next().find("input").val();
		if (certPropName == '质量管理体系认证证书') {
			allCertProCount++;
		}
	});

	var delFlag = true;
	$(checkboxs).each(function(index) {
		var certPropName = $(this).parent().next().find("input").val();
		if (certPropName == '质量管理体系认证证书') {
			checkedCertProCount++;
		}
	});
	if (checkedCertProCount == allCertProCount) {
		delFlag = false;
	}
	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#proPageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		if (delFlag) {// 含有资质证书信息-质量管理体系认证证书不能删除(物资类型)
			layer.confirm(
				"已勾选" + size + "条记录，确定删除？",
				{
					offset : '200px',
					scrollbar : false,
				},
				function(index) {
					var certProIds = "";
					var supplierId = $("input[name='id']").val();
					$(checkboxs).each(function(n, v) {
						var isAdd = $(this).attr("isAdd");
						if (isAdd) {
							var tr = $(this).parent().parent();
							$(tr).remove();
						} else {
							if (n > 0) {
								certProIds += ",";
							}
							certProIds += $(this).val();
						}
					});
					/*
					 * window.location.href =
					 * globalPath + "/supplier_cert_pro/delete_cert_pro.html?certProIds=" +
					 * certProIds + "&supplierId=" + supplierId;
					 * layer.close(index);
					 */
					// 采用ajax post方式删除
					if (certProIds != "") {
						$.ajax({
							url : globalPath + "/supplier_cert_pro/delete_cert_pro.do",
							async : false,
							type : "POST",
							data : {
								"ids" : certProIds
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
				});
		} else {
			layer.alert("质量管理体系认证证书不能删除，请至少保留一个！", {
				offset : '200px',
				scrollbar : false,
			});
		}
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function addCertSell() {
	var matSellId = $("input[name='supplierMatSell.id']").val();
	var supplierId = $("input[name='id']").val();
	var certSaleNumber = $("#certSaleNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addSaleCert.do",
		async : false,
		dataType : "html",
		data : {
			"number" : certSaleNumber
		},
		success : function(data) {
			$("#cert_sell_list_tbody_id").append(data);
			init_web_upload();
		}
	});
	certSaleNumber++;
	$("#certSaleNumber").val(certSaleNumber);
}

function delCertSell() {
	var checkboxs = $("#cert_sell_list_tbody_id").find(":checkbox:checked");
	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#sellPageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm(
			"已勾选" + size + "条记录，确定删除？",
			{
				offset : '200px',
				scrollbar : false,
			},
			function(index) {
				var certSellIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(n, v) {
					var isAdd = $(this).attr("isAdd");
					if (isAdd) {
						var tr = $(this).parent().parent();
						$(tr).remove();
					} else {
						if (n > 0) {
							certSellIds += ",";
						}
						certSellIds += $(this).val();
					}
				});
				/*
				 * window.location.href =
				 * globalPath + "/supplier_cert_sell/delete_cert_sell.html?certSellIds=" +
				 * certSellIds + "&supplierId=" + supplierId;
				 * layer.close(index);
				 */
				// 采用ajax post方式删除
				if (certSellIds != "") {
					$.ajax({
						url : globalPath + "/supplier_cert_sell/delete_cert_sell.do",
						async : false,
						type : "POST",
						data : {
							"ids" : certSellIds
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
			});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function addCertSe() {
	var matSeId = $("input[name='supplierMatSe.id']").val();
	var supplierId = $("input[name='id']").val();
	var certSeNumber = $("#certSeNumber").val();
	$.ajax({
		url : globalPath + "/supplier/addSeCert.do",
		async : false,
		dataType : "html",
		data : {
			"number" : certSeNumber
		},
		success : function(data) {
			$("#cert_se_list_tbody_id").append(data);
			init_web_upload();
		}
	});
	certSeNumber++;
	$("#certSeNumber").val(certSeNumber);
}

function delCertSe() {
	var checkboxs = $("#cert_se_list_tbody_id").find(":checkbox:checked");
	var size = checkboxs.length;
	if (size > 0) {

		// 退回修改审核通过的项不能删除
		var isDel = checkIsDelForTuihui(checkboxs, $("#servePageField").val());
		if (!isDel) {
			layer.msg("审核通过的项不能删除！");
			return;
		}

		layer.confirm(
			"已勾选" + size + "条记录，确定删除？",
			{
				offset : '200px',
				scrollbar : false,
			},
			function(index) {
				var certSeIds = "";
				var supplierId = $("input[name='id']").val();
				$(checkboxs).each(function(n, v) {
					var isAdd = $(this).attr("isAdd");
					if (isAdd) {
						var tr = $(this).parent().parent();
						$(tr).remove();
					} else {
						if (n > 0) {
							certSeIds += ",";
						}
						certSeIds += $(this).val();
					}
				});
				/*
				 * window.location.href =
				 * globalPath + "/supplier_cert_se/delete_cert_se.html?certSeIds=" +
				 * certSeIds + "&supplierId=" + supplierId;
				 * layer.close(index);
				 */
				// 采用ajax post方式删除
				if (certSeIds != "") {
					$.ajax({
						url : globalPath + "/supplier_cert_se/delete_cert_se.do",
						async : false,
						type : "POST",
						data : {
							"ids" : certSeIds
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
			});
	} else {
		layer.alert("请至少勾选一条记录！", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

// 全选
function checkAll(ele, id) {
	var flag = $(ele).prop("checked");
	$("#" + id).find("input:checkbox").each(function() {
		$(this).prop("checked", flag);
	});
}

// 控制省市附件的显示与隐藏
function disAreaFile(obj) {
	var currSupplierSt = "${currSupplier.status}";
	var engPageField = "${engPageField}";
	var businessScope = "${currSupplier.supplierMatEng.businessScope}";
	$(obj).find("option").each(function(i, element) {
		if (currSupplierSt == '2'
				&& engPageField.indexOf(element.text) < 0
				&& businessScope.indexOf(element.value) >= 0) {
			element.selected = true;
		}
		if (currSupplierSt == '2'
				&& engPageField.indexOf(element.text) < 0) {
			return true;
		} else {
			if (element.selected == true) {
				$("#area_" + element.value).show();
				init_web_upload();
			} else {
				$("#area_" + element.value).hide();
			}
		}
	});
	// 设置承揽业务范围
	setBusinessScope();
}

// 设置承揽业务范围
function setBusinessScope() {
	var areaIds = "";
	$("#areaSelect").find("option").each(function(i, element) {
		if (element.selected == true) {
			areaIds = areaIds + element.value + ",";
		}
	});
	$("#businessScope").val(areaIds);
}

var tempText = "";
function getAptLevelSelect(record) {
	tempText = record.text;
}
// 资质类型下拉框改变时调用的方法
function getAptLevel(obj, isSelect) {
	var supplierId = $("#sid").val();
	if (obj instanceof jQuery) {
		var typeId = obj.val();
		var currentText = obj.combobox("getText");
		var allText = obj.combobox("getData");
		var objId = obj.attr("id");
		var objIdNum = objId.replace("certType_", "");
		var selectedLevel = $("#certLevel_" + objIdNum).val();
		if (isSelect) {// 如果是选择下拉
			$("[id='certGrade_" + objIdNum + "']").combobox("clear");// 清除选中值
			$("[id='certGrade_" + objIdNum + "']").combobox('loadData', {});// 清空option
		}
		if (typeId != null && typeId != "") {
			$.ajax({
				url : globalPath + "/supplier/getAptLevel.do",
				type : "POST",
				data : {
					"typeId" : typeId,
					"supplierId" : supplierId,
				},
				dataType : "json",
				success : function(data) {
					var easyuiData = [];
					var flag_certGrade = 0;
					if (data == null || data == {} || data == "") {
						var cur_str = {
							label : typeId,
							value : typeId,
							selected : true
						};
						easyuiData.push(cur_str);
					} else {
						for ( var i = 0; i < data.length; i++) {
							if (null != data[i]) {
								var optionDOM = "";
								var cur_str = "";
								if (selectedLevel != ""
										&& selectedLevel == data[i].id) {
									// optionDOM = "<option value='" +
									// data[i].id + "'
									// selected='selected'>" +
									// data[i].name + "</option>";
									cur_str = {
										label : data[i].id,
										value : data[i].name,
										selected : true
									};
									// flag_certGrade = 1;
								} else {
									// var optionDOM = "<option value='"
									// + data[i].id + "'>" +
									// data[i].name + "</option>";
									cur_str = {
										label : data[i].id,
										value : data[i].name
									};
								}
								if (selectedLevel != "") {
									flag_certGrade = 1;
								}
								easyuiData.push(cur_str);
								// obj.parent().next().next().next().find("select").append(optionDOM);
							}
						}
					}
					if (flag_certGrade == 0) {
						easyuiData[0].selected = true;
					}

					var currentText = obj.combobox("getText");
					var flag_current = 0;
					var selectData = obj.combobox("getData");
					for ( var i = 0; i < selectData.length; i++) {
						if (selectData[i].value == currentText) {
							// flag_current = 1;
						}
					}

					// var certTypes = $("[id='certType_" + objIdNum +
					// "']").combobox("getData");
					var certTypes = $.parseJSON($("#quaListJson").val());
					// console.log(certTypes);
					var certLevelEditable = true;
					if (certTypes) {
						var certTypeIds = [];
						for ( var i = 0, len = certTypes.length; i < len; i++) {
							certTypeIds[i] = certTypes[i].id;
						}
						if ($.inArray(typeId, certTypeIds) != -1) {
							certLevelEditable = false;
						}
					}
					if (flag_current == 0) {
						$("[id='certGrade_" + objIdNum + "']")
							.combobox({
								valueField : 'label',
								textField : 'value',
								data : easyuiData,
								editable : certLevelEditable,
								hasDownArrow : true
							});
					} else {
						$("[id='certGrade_" + objIdNum + "']")
							.combobox({
								valueField : 'label',
								textField : 'value',
								data : ""
							});
					}
				}
			});
		} else {
			$("[id='certGrade_" + objIdNum + "']").combobox({
				height : 0,
				valueField : 'label',
				textField : 'value',
				data : "",
				editable : true
			});
		}
	} else {
		var typeId = $(obj).val();
		if (typeId != null && typeId != "") {
			$(obj).parent().next().next().next().find("select").html("");
			$.ajax({
				url : globalPath + "/supplier/getAptLevel.do",
				data : {
					"typeId" : typeId,
				},
				dataType : "json",
				success : function(data) {
					for ( var i = 0; i < data.length; i++) {
						var optionDOM = "";
						var objId = obj.attr("id");
						var objIdNum = objId.replace("certType_", "");
						var selectedLevel = $("#certLevel_" + objIdNum)
								.val();
						if (selectedLevel != ""
								&& selectedLevel == data[i].id) {
							optionDOM = "<option value='" + data[i].id
									+ "' selected='selected'>"
									+ data[i].name + "</option>";
						} else {
							var optionDOM = "<option value='"
									+ data[i].id + "'>" + data[i].name
									+ "</option>";
						}
						$(obj).parent().next().next().next().find(
								"select").append(optionDOM);
					}
				}
			});
		}
	}
}

// 保密工程业绩下拉框事件
function onchangeConAchi(_this) {
	if ($(_this).val() == '1') {
		$("#conAchiDiv").show();
		init_web_upload();
		$("#conAchi").attr("required", true);
	} else {
		$("#conAchiDiv").hide();
		$("#conAchi").attr("required", false);
	}
}

// 判断销售型是否满足要求
function isSalePass(val) {
	var supplierId = $("#supplierId").val();
	$.ajax({
		url : globalPath + "/supplier/isPass.do",
		data : {
			"supplierId" : supplierId,
			"type" : val
		},
		type : "post",
		success : function(data) {
			if (data == "1") {

			} else {
				layer.msg("近3年加权平均净资产不满足物资销售型供应商的要求！");
				layer.close(index);
			}
		}
	});
}

// 控制表单
function controlForm(){
	// 如果供应商状态是退回修改，控制表单域的编辑与不可编辑
	var currSupplierSt = $("#supplierSt").val();;
	//console.log(currSupplierSt);
	if(currSupplierSt == '2'){
		$("input[type='text'],select,textarea").attr('disabled',true);
		$("input[type='text'],select,textarea").each(function(){
			// 或者$(this).attr("style").indexOf("border: 1px solid red;") > 0
			// 或者$(this).css("border") == '1px solid rgb(239, 0, 0)'
			if($(this).css("border-top-color") == 'rgb(255, 0, 0)' 
				|| $(this).css("border-bottom-color") == 'rgb(255, 0, 0)' 
				|| $(this).css("border-left-color") == 'rgb(255, 0, 0)' 
				|| $(this).css("border-right-color") == 'rgb(255, 0, 0)' 
				|| $(this).parents("td").css("border-top-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-bottom-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-left-color") == 'rgb(255, 0, 0)'
				|| $(this).parents("td").css("border-right-color") == 'rgb(255, 0, 0)'
			){
				$(this).attr('disabled',false);
			}
		});
		
		// 特殊处理资质证书信息
		// eng_qua_list_tbody_id
		$("#eng_qua_list_tbody_id input").removeAttr('disabled').removeAttr('readonly');
		
		/* $("select").change(function(){
			this.selectedIndex=this.defaultIndex;
		}); */
		
		// 控制4大类别的编辑性
		$("input[type='checkbox'][name='chkItem']").attr('disabled',true);
		/* $("input[type='checkbox'][name='chkItem']").each(function(){
			var typeErrorField = '${typePageField}';
			if(typeErrorField.indexOf($(this).parent().attr("id")) >= 0){
				$(this).attr('disabled',false);
			}
		}); */
		// 控制承揽业务范围：省、直辖市
		var engPageField = '${engPageField}';
		$("#areaSelect").attr('disabled',false);
		$("#areaSelect").find("option").each(function(i, element){
			if (engPageField.indexOf(element.text) >= 0) {
				
			}else{
				element.disabled = 'disabled';
			}
		});
	}
}

// 表单可编辑
function enableForm(){
	var currSupplierSt = $("#supplierSt").val();;
	if(currSupplierSt == '2'){
		$("input[type='text'],input[type='checkbox'],select,textarea,input[type='hidden']").attr('disabled',false);
	}
}

// 审核通过的项不能删除(列表)
function checkIsDelForTuihui(checkedObjs, audit){
	var currSupplierSt = $("#supplierSt").val();;
	if(currSupplierSt == '2'){
		var isDel = true;
		$(checkedObjs).each(function(index) {
			if(audit.indexOf($(this).val()) < 0){
				isDel = false;
				return false;
			}
		});
		return isDel;
	}
	return true;
}

// 核对字符长度
function checkCharLimit(inputId,countId,limit){
	var inputVal = $("#"+inputId).val();
	var inputLen = inputVal ? inputVal.length : 0;
	$("#"+countId).text(limit - inputLen);
}

sessionStorage.locationB = true;
sessionStorage.index = 2;

//********************以下为注释不用代码********************

//高亮不通过的字段
/*function displayReason(auditField, auditType) {
	var supplierId = $("#supplierId").val();
	$.ajax({
		url : globalPath + "/supplierAudit/displayReason.do",
		data : {
			"supplierId" : supplierId,
			"auditField" : auditField,
			"auditType" : auditType
		},
		dataType : json,
		success : function(data) {
			layer.msg(data.suggest, {
				offset : '200px'
			});
		}
	});
}*/

/*function seach(obj) {
var id = $(obj).next().val();
var sid = $("#sid").val();
if (id.length > 0) {
	layer.open({
		type : 2,
		title : '查询产品分类',
		// skin : 'layui-layer-rim', //加上边框
		area : [ '800px', '500px' ], // 宽高
		offset : '100px',
		scrollbar : false,
		content : '${pageContext.request.contextPath}/supplier_item/category.html?id='
				+ id + '&&sid=' + sid, // url
		closeBtn : 1, // 不显示关闭按钮
	});
} else {
	layer.alert("请至少勾选一条记录！", {
		offset : '200px',
		scrollbar : false,
	});
}
}*/

/*function name() {
var id = [];
$('input[name="chkItem"]:checked').each(function() {
	id.push($(this).next().val());
});
return id;
}*/

/*function valus() {
var id = [];
$('input[name="chkItem"]:checked').each(function() {
	id.push($(this).val());
});
return id;
}*/

/*function checknums(obj) {
var vals = $(obj).val();
var reg = /^\d+\.?\d*$/;
if (!reg.exec(vals)) {
	$(obj).val("");
	$("#err_fund").text("数字非法");
} else {
	$("#err_fund").text();
	$("#err_fund").empty();
}
}*/

//之前的代码，管用
/*function getAptLevel_annotation(obj) {
var typeId = $(obj).val();
var supplierId = $("#supplierId").val();
if (typeId != null && typeId != "") {
	$(obj).parent().next().next().next().find("select").html("");
	$.ajax({
		url : globalPath + "/supplier/getAptLevel.do",
		data : {
			"typeId" : typeId,
			"supplierId" : supplierId
		},
		dataType : "json",
		success : function(data) {
			for ( var i = 0; i < data.length; i++) {
				var optionDOM = "";
				if ($(obj).next().val() != ""
						&& $(obj).next().val() == data[i].id) {
					optionDOM = "<option value='" + data[i].id
							+ "' selected='selected'>" + data[i].name
							+ "</option>";
				} else {
					var optionDOM = "<option value='" + data[i].id + "'>"
							+ data[i].name + "</option>";
				}
				$(obj).parent().next().next().next().find("select").append(
						optionDOM);
			}
		}
	});
}
}*/