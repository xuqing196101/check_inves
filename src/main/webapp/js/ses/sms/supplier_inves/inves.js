// 添加考察组人员
function addSignature(){
	$.ajax({
		url : globalPath + "/supplierInves/addSignature.do",
		type : "post",
		dataType : "json",
		data : {supplierId : $("#supplierId").val()},
		success : function(data) {
			if(data && data.status == 200){
				var signNumber = $("#signNumber").val();
				signNumber = parseInt(signNumber);
				var html = '<tr class="h40" id="tr_'+data.data.id+'">'+
			            	'<td class="tc">'+(signNumber+1)+'<input type="hidden" name="signs['+signNumber+'].id" value="'+data.data.id+'" /></td>'+
			                '<td class="tc"><input type="text" class="w100p mb0" name="signs['+signNumber+'].name" value="" onchange="tempSaveSignature(this)" maxlength="10"/></td>'+
			                '<td class="tc"><input type="text" class="w100p mb0" name="signs['+signNumber+'].company" value="" onchange="tempSaveSignature(this)" maxlength="50"/></td>'+
			                '<td class="tc"><input type="text" class="w100p mb0" name="signs['+signNumber+'].job" value="" onchange="tempSaveSignature(this)" maxlength="50"/></td>'+
			                '<td class="tc"><img src="'+globalPath+'/public/backend/images/sc.png" onclick="delSignature('+data.data.id+')"></td>'+
				            '</tr>';
				$("#tbody_sign").append(html);
				$("#signNumber").val((signNumber+1));
			}
		}
	});
}

// 删除考察组人员
function delSignature(id){
	$.ajax({
		url : globalPath + "/supplierInves/delSignature.do",
		type : "post",
		dataType : "json",
		data : {id : id},
		success : function(data) {
			if(data && data.status == 200){
				$("tr[data-id='"+id+"']").remove();
				var signNumber = $("#signNumber").val();
				$("#signNumber").val(--signNumber);
				// 重新计算序号
				$("#tbody_sign tr").each(function(index){
					$(this).find("td:first").text(index+1);
				});
			}
		}
	});
}

// 实时更新考察产品类别
function tempSaveCateAudit(id){
	var isSupplied = $("#isSupplied_"+id).val();
	var suggest = $("#suggest_"+id).val();
	$.ajax({
		url : globalPath + "/supplierInves/saveCateAudit.do",
		type : "post",
		dataType : "json",
		data : {
			id : id,
			isSupplied : isSupplied,
			suggest : suggest
		},
		success : function(data) {
			if(data && data.status == 200){
				var ids = data.data;
				if(ids){
					for(var i=0,len=ids.length; i<len; i++){
						if(isSupplied == 1){
							changeCateAuditBtn("#btn_yes_"+id, ids[i], isSupplied);
						}
						if(isSupplied == 2){
							changeCateAuditBtn("#btn_no_"+id, ids[i], isSupplied);
						}
					}
				}
			}
		}
	});
}
// 实时更新考察其他信息
function tempSaveInvesOther(_this){
	var param = {};
	var id = $("#invesOtherId").val();
	param["id"] = id;
	if(id == ""){
		param["supplierId"] = $("#supplierId").val();
	}
	param[_this.name] = _this.value;
	$.ajax({
		url : globalPath + "/supplierInves/saveInvesOther.do",
		type : "post",
		dataType : "json",
		data : param,
		success : function(data) {
			if(data && data.status == 200){
				if(id == ""){
					$("#invesOtherId").val(data.data.id);
				}
			}
		}
	});
}
//实时更新考察意见
function tempSaveAuditOpinion(_this){
	var param = {};
	var id = $("#auditOpinionId").val();
	param["id"] = id;
	//if(id == ""){
		param["supplierId"] = $("#supplierId").val();
	//}
	param[_this.name] = _this.value;
	if(_this.name == "opinion" && $("#cate_result").val() != ""){
		param[_this.name] = $("#cate_result").val() + _this.value;
	}
	$.ajax({
		url : globalPath + "/supplierInves/saveAuditOpinion.do",
		type : "post",
		dataType : "json",
		data : param,
		success : function(data) {
			if(data){
				if(data.status == 200){
					if(id == ""){
						$("#auditOpinionId").val(data.data.id);
					}
				}else{
					layer.msg(data.msg, {offset: '100px'});
					$("#qualified").attr("checked", false);
					$("#qualified").attr("disabled", true);
				}
			}
		}
	});
}
// 实时更新考察组人员
function tempSaveSignature(_this){
	var param = {};
	var id = $(_this).parents("tr").attr("data-id");
	var index = $(_this).parents("tr").attr("id").replace("tr_", "");
	var name = _this.name.replace("signs["+index+"].", "");
	param["id"] = id;
	if(id == ""){
		param["supplierId"] = $("#supplierId").val();
	}
	param[name] = _this.value;
	$.ajax({
		url : globalPath + "/supplierInves/saveSignature.do",
		type : "post",
		dataType : "json",
		data : param,
		success : function(data) {
			if(data && data.status == 200){
				if(id == ""){
					$(_this).parent("tr").attr("id", "tr_" + data.data.id);
				}
			}
		}
	});
}

// 审核操作
function oprCateAudit(_this, id , isSupplied) {
	changeCateAuditBtn(_this, id, isSupplied);
	tempSaveCateAudit(id);
}

function changeCateAuditBtn(_this, id , isSupplied){
	var countCateAuditNotPass = parseInt($("#countCateAuditNotPass").val());
	if(isSupplied == 1){
		if($(_this).hasClass("bgdd") && $(_this).hasClass("black_link")){// 默认按钮
			$(_this).removeClass("bgdd");
			$(_this).removeClass("black_link");
			$("#isSupplied_" + id).val(isSupplied);
			$(_this).next().removeClass("bgred");
			$(_this).next().addClass("bgdd");
			$(_this).next().addClass("black_link");
		}else{
			$(_this).addClass("bgdd");
			$(_this).addClass("black_link");
			$("#isSupplied_" + id).val("0");
		}
	}
	if(isSupplied == 2){
		if($(_this).hasClass("bgdd") && $(_this).hasClass("black_link")){// 默认按钮
			$(_this).removeClass("bgdd");
			$(_this).removeClass("black_link");
			$(_this).addClass("bgred");
			$("#isSupplied_" + id).val(isSupplied);
			$(_this).prev().addClass("bgdd");
			$(_this).prev().addClass("black_link");
			$("#countCateAuditNotPass").val(++countCateAuditNotPass);
		}else{
			$(_this).removeClass("bgred");
			$(_this).addClass("bgdd");
			$(_this).addClass("black_link");
			$("#isSupplied_" + id).val("0");
			$("#countCateAuditNotPass").val(--countCateAuditNotPass);
		}
	}
}

// 考察合格鼠标悬停事件
function onmouseoverInvesPass(){
	var countAttachAuditNotPass = parseInt($("#countAttachAuditNotPass").val());
	var countCateAuditNotPass = parseInt($("#countCateAuditNotPass").val());
	var countCateAuditAll = parseInt($("#countCateAuditAll").val());
	if(countAttachAuditNotPass > 0 || countCateAuditNotPass == countCateAuditAll){
		$("#qualified").attr("disabled", true);
	}else{
		$("#qualified").attr("disabled", false);
	}
}

// 考察结束
function invesEnd(){
	var supplierId = $("#supplierId").val();
	var auditFlag = $("input[name='flagAduit']:checked").val();
	$.ajax({
		url : globalPath + "/supplierInves/invesEnd.do",
		type : "post",
		dataType : "json",
		data : {
			supplierId : supplierId,
			auditFlag : auditFlag
		},
		success : function(data) {
			if(data){
				if(data.status == 200){
					location.href = globalPath + "/supplierInves/list.html";
				}else{
					layer.msg(data.msg, {offset: '100px'});
				}
			}
		}
	});
}