// 审核文本框
function auditText(_this, auditType, auditField, disabled) {
	if (disabled == "true") {
		layer.msg('此项不能审核！');
		return;
	}
	// 只有审核的状态能审核
	if (isAudit) {
		var auditContent = $(_this).val();
		var auditFieldName = $(_this).parents("li").find("span").text()
			.replace("：", "").trim();
		var auditData = {
			supplierId : supplierId,
			auditType : auditType,
			auditField : auditField,
			auditFieldName : auditFieldName,
			auditContent : auditContent
		};
		$(_this).doAudit({
			auditData : auditData,
			funcBeforeAudit : function() {
				if ($(_this).parent().children("a.abolish").length > 0) {
					layer.msg('该条信息已审核过并退回过！');
					return false;
				}
			},
			funcAfterAddAudit : function() {
				// 先获取原来的边框颜色
				var oldBorderColor = $(_this).css('border-color');
				var oldOnmouseover = $(_this).attr("onmouseover");
				if (oldBorderColor && oldOnmouseover) {
					$(_this).attr("data-oldBorderColor", oldBorderColor);
					$(_this).attr("data-oldOnmouseover", oldOnmouseover);
				}
				$(_this).css('border-color', '#FF0000'); // 边框变红色
				$(_this).removeAttr("onmouseover");
			},
			funcAfterCancelAudit : function() {
				// 先获取原来的边框颜色
				var oldBorderColor = $(_this).attr("data-oldBorderColor");
				var oldOnmouseover = $(_this).attr("data-oldOnmouseover");
				if (oldBorderColor && oldOnmouseover) {
					$(_this).css('border-color', oldBorderColor);
					$(_this).attr('onmouseover', oldOnmouseover);
				} else {
					$(_this).css("border", "");
				}
			}
		});
	}
}

// 审核类型
function auditType(_this, auditType, auditField, auditFieldName) {
	// 只有审核的状态能审核
	if (isAudit && auditType && auditType == "supplierType_page") {
		var auditData = {
				supplierId : supplierId,
				auditType : auditType,
				auditField : auditField,
				auditFieldName : auditFieldName,
				auditContent : auditFieldName + "类型"
		};
		$(_this).doAudit({
			auditData : auditData,
			funcBeforeAudit : function() {
				if ($(_this).parent().children("img.abolish_img").length > 0) {
					layer.msg('该条信息已审核过并退回过！');
					return false;
				}
			},
			funcAfterAddAudit : function() {
				$(_this).css('border', '1px solid #FF0000'); // 添加红边框
			},
			funcAfterCancelAudit : function() {
				$(_this).css("border", "");
			}
		});
	}
}

// 审核列表
function auditList(_this, auditType, auditField, auditFieldName, auditContent) {
	// 只有审核的状态能审核
	if (isAudit) {
		if (auditContent) {
			if (auditType && auditType.indexOf("mat_") < 0) {
				if (auditFieldName == "售后服务机构") {
					auditContent = auditContent + "分支机构信息";
				} else {
					auditContent = auditContent + auditFieldName;
				}
			}
		}
		if (auditType && auditType.indexOf("mat_") == 0) {
			if (auditFieldName == "工程-注册人员登记") {
				var temp = "注册名称为：";
				if (auditContent) {
					auditContent = "注册名称为：" + auditContent + "的信息";
				} else {
					auditContent = temp;
				}
			} else if (auditFieldName == "物资生产-资质证书"
					|| auditFieldName == "物资销售-资质证书"
					|| auditFieldName == "工程-资质证书"
					|| auditFieldName == "服务-资质证书") {
				var temp = "证书名称为：";
				if (auditContent) {
					auditContent = "证书名称为：" + auditContent + "的信息";
				} else {
					auditContent = temp;
				}
			} else {
				var temp = "证书编号为：";
				if (auditContent) {
					auditContent = "证书编号为：" + auditContent + "的信息";
				} else {
					auditContent = temp;
				}
			}
		}
		var auditData = {
			supplierId : supplierId,
			auditType : auditType,
			auditField : auditField,
			auditFieldName : auditFieldName,
			auditContent : auditContent
		};
		$(_this).doAudit({
			auditData : auditData,
			funcBeforeAudit : function() {
				if ($(_this).hasClass("icon_sc")) {
					layer.msg('该条信息已审核过并退回过！');
					return false;
				}
			},
			funcAfterAddAudit : function() {
				/*
				 * var icon = "<img
				 * src='"+globalPath+"/public/backend/images/light_icon_2.png'/>";
				 * $(_this).html("").append(icon);
				 */
				var iconUrl = globalPath
						+ "/public/backend/images/light_icon_2.png";
				$(_this).attr("src", iconUrl);
			},
			funcAfterCancelAudit : function() {
				/*
				 * var icon = "<img
				 * src='"+globalPath+"/public/backend/images/light_icon.png'/>";
				 * $(_this).html("").append(icon);
				 */
				var iconUrl = globalPath
						+ "/public/backend/images/light_icon.png";
				$(_this).attr("src", iconUrl);
			}
		});
	}
}

// 审核附件
function auditFile(_this, auditType, auditField) {
	// 只有审核的状态能审核
	if (isAudit) {
		var auditFieldName = $(_this).parents("li").find("span").text()
				.replace("：", "").trim();
		var auditData = {
			supplierId : supplierId,
			auditType : auditType,
			auditField : auditField,
			auditFieldName : auditFieldName,
			auditContent : "附件"
		};
		$(_this).doAudit({
			auditData : auditData,
			funcBeforeAudit : function() {
				if ($(_this).parent().children("img.abolish_img_file").length > 0) {
					layer.msg('该条信息已审核过并退回过！');
					return false;
				}
			},
			funcAfterAddAudit : function() {
				// 先获取原来的边框颜色
				var oldBorderColor = $(_this).css('border');
				if (oldBorderColor) {
					$(_this).attr("data-oldBorderColor", oldBorderColor);
				}
				$(_this).css('border', '1px solid #FF0000'); // 添加红边框
			},
			funcAfterCancelAudit : function() {
				// 先获取原来的边框颜色
				var oldBorderColor = $(_this).attr("data-oldBorderColor");
				if (oldBorderColor) {
					$(_this).css('border', oldBorderColor);
				} else {
					$(_this).css("border", "");
				}
			}
		});
	}
}

//添加出资人
function addStockholder(supplierId){
	var uId = null;
	$.ajax({
		url : globalPath + "/supplier/getUUID.do",
		async : false,
		success : function(data) {
			uId = data;
		}
	});
	$("#stockholder_account_tbody_id").append(
		"<tr id='stockholder_tr_"+uId+"'>" 
		+ "<td class='tc'><input type='checkbox' name='stockholder_chkItem' value='"
		+ uId
		+ "' />"
		+ "</td>"
		+ "<td class='tc'></td>"
		+ "<td class='tc'></td>"
		+ "<td class='tc'></td>"
		+ "<td class='tc'></td>"
		+ "<td class='tc'></td>"
		+ "<td class='tc'></td>"
		+ "<td class='tc'>"
		+ "<img src='"+ globalPath +"/public/backend/images/light_icon.png' class='icon_edit' "
        + 'onclick="auditList(this,'
        + "'basic_page','"
        + uId
        + "','股东信息','');"
        + '"/>'
		+ "</td>"
		+ "</tr>");
	//添加到数据库
	$.ajax({
		url : globalPath + "/supplier/saveTempStockholder.do",
		async : false,
		data : {
			id : uId,
			supplierId : supplierId
		},
		success : function(data) {
			
		}
	});
}
//删除出资人
function deleteStockholder(supplierId){
	var count = 0;
	$('input[name="stockholder_chkItem"]:checked').each(function(){ 
		count++; 
	}); 
	if(count>0){
		$('input[name="stockholder_chkItem"]:checked').each(function(){ 
			var currId = $(this).val();
			$("#stockholder_tr_"+currId).remove();
			//删除数据
			$.ajax({
				url : globalPath + "/supplier/deleteTempStockholder.do",
				async : false,
				data : {
					id : currId,
					supplierId : supplierId
				},
				success : function(data) {
					
				}
			});
		}); 
	}else{
		layer.alert("请选择要删除的行",{offset: '222px', shade:0.01});
	}
	
	
}

