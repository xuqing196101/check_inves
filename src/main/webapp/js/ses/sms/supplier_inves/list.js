// 审核
function shenhe() {
	var id = $(":radio:checked").val();
	var status = $("#" + id + "").attr("sts");
	if (id == null) {
		layer.msg("请选择供应商！", {
			offset : '100px',
		});
		return;
	}
	if (status != 5) {
		layer.msg("请选择待考察的供应商！", {
			offset : '100px',
		});
		return;
	}
	if(validateInves(id)){
		$("input[name='supplierId']").val(id);
		$("#shenhe_form").attr("action",
				globalPath + "/supplierAudit/essential.html");
		$("#shenhe_form").submit();
	}
}

// 校验
function validateInves(id){
	var bool = false;
	$.ajax({
        url: globalPath + "/supplierInves/validateInves.do",
        data: {supplierId : id},
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if(result && result.status == 200){
            	bool = true;
            }else{
            	layer.msg(layer.msg, {offset: '100px'});
            }
        },
        error: function () {
            layer.msg("请求错误！", {offset: '100px'});
        }
    });
	return bool;
}

// 重置
function resetForm(){
	$("#formSearch input[type='text']").val("");
    $("#formSearch select").val("");
	$("#formSearch").submit();
}

// 下载
function downloadByType(type) {
	if(type == 1 || type == 2){
		var id = $(":radio:checked").val();
		if (id == null) {
			layer.msg("请选择供应商！", {
				offset : '100px',
			});
			return;
		}
		$("input[name='supplierId']").val(id);
	}
	// 下载考察记录表
	if(type == 1){
		$("#shenhe_form").attr("action",
				globalPath + "/supplierInves/downloadInvesRecord.html");
	}
	// 下载附件
	if(type == 2){
		$("#shenhe_form").attr("action",
				globalPath + "/supplierInves/downloadAttach.html");
	}
	// 下载意见函
	if(type == 3){
		$("#shenhe_form").attr("action",
				globalPath + "/supplierInves/downloadOpinionLetter.html");
	}
	$("#shenhe_form").submit();
}