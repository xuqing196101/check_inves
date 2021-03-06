// 查看附件
function viewAttach(url, title) {
	layer.open({
	  type: 2, // page层
	  area: ['800px', '450px'],
	  title: title,
	  closeBtn: 1,
	  shade: 0.01, // 遮罩透明度
	  moveType: 1, // 拖拽风格，0是默认，1是传统拖动
	  shift: 1, // 0-6的动画形式，-1不开启
	  offset: '10px',
	  shadeClose: false,
	  content: globalPath + url,
	});
}

//审核操作
//isAccord是否一致  1：一致， 2不一致。auditType审核类型  1：复核，2考察。
function opr(_this, id , isAccord, auditType) {
	var countAttachAuditNotPass = parseInt($("#countAttachAuditNotPass").val());
	var _isAccord = $("#isAccord_" + id).val();
	if(isAccord == 1){
		if($(_this).hasClass("bgdd") && $(_this).hasClass("black_link")){// 默认按钮
			$(_this).removeClass("bgdd");
			$(_this).removeClass("black_link");
			$("#isAccord_" + id).val(isAccord);
			$(_this).next().removeClass("bgred");
			$(_this).next().addClass("bgdd");
			$(_this).next().addClass("black_link");
			if(_isAccord == 2){
				$("#countAttachAuditNotPass").val(--countAttachAuditNotPass);
			}
		}else{
			$(_this).addClass("bgdd");
			$(_this).addClass("black_link");
			$("#isAccord_" + id).val("0");
			isAccord = 0;
		}
	}
	if(isAccord == 2){
		if($(_this).hasClass("bgdd") && $(_this).hasClass("black_link")){// 默认按钮
			$(_this).removeClass("bgdd");
			$(_this).removeClass("black_link");
			$(_this).addClass("bgred");
			$("#isAccord_" + id).val(isAccord);
			$(_this).prev().addClass("bgdd");
			$(_this).prev().addClass("black_link");
			$("#countAttachAuditNotPass").val(++countAttachAuditNotPass);
		}else{
			$(_this).removeClass("bgred");
			$(_this).addClass("bgdd");
			$(_this).addClass("black_link");
			$("#isAccord_" + id).val("0");
			isAccord = 0;
			$("#countAttachAuditNotPass").val(--countAttachAuditNotPass);
		}
	}
	
	saveAuditIsAccord(id, auditType, isAccord);
}

//保存意见--isAccord是否一致  1：一致， 2不一致
function saveAuditIsAccord(id, auditType, isAccord){
	var supplierId = $("#supplierId").val();
	$.ajax({
		url : globalPath + "/supplierAttachAudit/saveAuditInformation.do",
		type: "post",
		data: {"id" : id, "isAccord" : isAccord, "auditType" : auditType, "supplierId" : supplierId},
		success: function(result){
			if(result.data > 0){
				$("#qualified").attr("checked", false);
				$("#qualified").attr("disabled", true);
				//$("#cate_result").html("");
			}else{
				$("#qualified").attr("disabled", false);
			}
		}
	});
}

//保存理由--auditType审核类型  1：复核，2考察。
function saveAuditSuggest(id, auditType, index){
	var suggest = $("#"+ id +"_suggest_"+ index).val();
	suggest = trim(suggest);
	$.ajax({
		url : globalPath + "/supplierAttachAudit/saveAuditInformation.do",
		type: "post",
		data: {"id" : id, "auditType" : auditType, "suggest" : suggest},
	});
}

//删除左右两端的空格
function trim(str) { 
    return str.replace(/(^\s*)|(\s*$)/g, "");
}