$(function () {
    // 供应商id
    var supplierId = $("#supplierId").val();
    var status = $("#status").val();
    // 预审核结束状态
    if(status == -2 || status == -3 || status == 3){
        $("#checkWord").show();
        // 审核状态为3（复审不合格）或者-3（公示中）的意见不可更改
        if(status == 3){
            $("input[name='selectOption']").prop("disabled",true);
            $("#opinion").prop("disabled", true);
        }
    }
    // 将审核意见的radio选中
    var hiddenSelectOptionId = $("#hiddenSelectOptionId").val();
    $("input[name='selectOption'][value='"+hiddenSelectOptionId+"']").prop("checked",true);
    // 获取复选框选择类型
    //var checkVal = $("input:radio[name='selectOption'] :checked").val();
    $("input[name='selectOption']").bind("click", function(){
        // 无提示暂存审核意见
        tempSave("noTip");
        // 清空意见内容
        //$("#opinion").val("");
        var selectedVal = $(this).val();
        if(selectedVal == 0){
            $("#cate_result").html("不通过。");
            return;
        }
        // 判断意见是否已经获取，有的话不再发送请求
        /*var opinionBack = $("#opinionBack").val();
        if(opinionBack != ''){
            $("#opinion").val(opinionBack);
            return;
        }*/
        getCheckOpinionType(supplierId);
    });
    // 判断复选框操作
    if(hiddenSelectOptionId != '' && hiddenSelectOptionId == 0){
        // 预审核不通过
        $("#cate_result").html("不通过。");
    }
    if(hiddenSelectOptionId != '' && hiddenSelectOptionId == 1){
        // 预审核通过
        getCheckOpinionType(supplierId);
    }
    // 无提示暂存审核意见
    $("#oprTempSave,#oprNextStep,input[name='selectOption']").mousedown(function(e){
        lock = 1;
    });
    $("#opinion").focus(function(){
		$(this).attr("data-oval", $(this).val());
	}).blur(function(e){
		//e.relatedTarget
		if(e){
			var oldVal = $(this).attr("data-oval"); //获取原值
			var newVal = $(this).val(); //获取当前值
			if (newVal && $.trim(newVal) != "" && oldVal != newVal){
				var checkVal = $("input:radio[name='selectOption']:checked").val();
				if(checkVal == undefined){
				    layer.msg("请选择审核意见项！");
				    return;
				}
				if(lock == 0){
					tempSave("noTip");
				}
			}
		}
	});
});

/**
 * 审核意见预审核通过类型判断
 * @param supplierId
 */
function getCheckOpinionType(supplierId) {
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
    // 获取供应商ID
    $.ajax({
        url:globalPath + "/supplierAudit/selectChooseOrNoPassCate.do",
        data:{
            "id" : supplierId
        },
        success:function (data) {
            var opinionData = "同意入库，选择了"+data.passCateCount+"个产品类别，通过了"+(data.passCateCount - data.noPassCateCount)+"个产品类别。";
            $("#cate_result").html(opinionData);
            //$("#opinionBack").val(opinionData);
            // 关闭旋转图标
            layer.close(index);
        }
    });
}
/**
 * 下一步操作
 */
function nextStep() {
	var status = $("#status").val();
	if(status == -2 || status == 0 || status == 9){
		tempSave(1);
	}
	var sign = $("input[name='sign']").val();
	if(status == -3 || status == 3 || (status == 1 && sign == 1)){
		toStep("eight");
	}
    
}

/**
 * 审核汇总暂存
 */
var lock = 0;// 对冲突元素加锁
function tempSave(flag){
	// 获取审核意见
    var opinion = $("#opinion").val();
    // 获取选择radio类型
    var selectOption = $("input[name='selectOption']:checked").val();

    // 1： 下一步  否则暂存
    if(flag == 1){
        // 判断审核项是否有没有不通过项
        // 获取复选框值
        if(vartifyAuditCount()){
            return;
        }
        // 判断附件是否下载
        var downloadAttachFile = $("#downloadAttachFile").val();
        if(downloadAttachFile == ''){
            layer.msg("请下载审核表！");
            flags = true;
            return flags;
        }
        // 标识后台需要做校验（不是暂存）
        $("#vertifyFlag").val("vartify");
    }
    // 请求操作
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
    // 将审核意见表单赋值
    $("#opinionId").val(opinion);
    $("#flagTime").val(0);
    $("#flagAduit").val(selectOption);
    var cate_result = $("#cate_result").html();
    $("#cateResult").val(cate_result);
    $.ajax({
        url:globalPath + "/supplierAudit/saveAuditOpinion.do",
        type:"POST",
        data:$("#opinionForm").serialize(),
        dataType:"json",
        success:function (data) {
            if(flag == 1){
                if(data == 500){
                    layer.msg(data.msg);
                }else {
                    /*var action = globalPath + "/supplierAudit/uploadApproveFile.html";
                    $("#form_id").attr("action", action);
                    $("#form_id").submit();*/
                    var action = globalPath + "/supplierAudit/uploadApproveFile.html";
                    submitJumpForm(action);
                }
            }else if(flag == "noTip"){
                lock = 0;// 释放锁
            }else{
                if(data.status == 200){
                    layer.msg("暂存成功！");
                }
            }
            // 关闭旋转图标
            layer.close(index);
        }
    });
}

/**
 * 下载审核/复核/意见函/考察表
 * @param str
 */
function downloadTable(str) {
    $("input[name='tableType']").val(str);
    var cate_result = $("#cate_result").html();
    var auditOpinion = $("#opinion").val();
    $("input[name='opinion']").val(cate_result+auditOpinion);
    $("#shenhe_form_id").attr("action", globalPath + "/supplierAudit/downloadTable.html");
    $("#shenhe_form_id").submit();
    $("#downloadAttachFile").val("1");
}

/**
 * 校验审核项
 * @Auth Easong
 */
function vartifyAuditCount(){
    var flags = false;
    // 获取审核意见
    var opinion  = $.trim($("#opinion").val());
    var checkVal = $("input:radio[name='selectOption']:checked").val();
    if(checkVal == undefined){
        layer.msg("请选择审核意见项");
        flags = true;
        return flags;
    }
    var supplierId=$("#supplierId").val();
    $.ajax({
        url:globalPath + "/supplierAudit/vertifyYushenhe.do",
        type:"POST",
        async:false,
        data:{
            "supplierId":supplierId,
            "flag":checkVal
        },
        dataType:"json",
        success:function (data) {
            if (data.status != 0) {
                layer.msg(data.msg);
                flags = true;
                return flags;
            }
        }
    });
    /*// 点击审核通过复选框时的校验
    if(checkVal == 1){
        var supplierId=$("#supplierId").val();
        $.ajax({
            url:globalPath + "/supplierAudit/vertifyAuditItem.do",
            type: "POST",
            async:false,
            data:{
                "supplierId":supplierId,
                "flag":2
            },
            dataType:"json",
            success:function (data) {
                if (data.status == 500) {
                    layer.msg(data.msg);
                    flags = true;
                    return flags;
                }
            }
        });
    }
    // 点击审核不通过复选框时的校验
    if(checkVal == 0){
        var supplierId=$("#supplierId").val();
        $.ajax({
            url:globalPath + "/supplierAudit/vertifyAuditNoPassItem.do",
            type: "POST",
            async:false,
            data:{
                "supplierId":supplierId,
                "flag":2
            },
            dataType:"json",
            success:function (data) {
                if (data.status == 500) {
                    layer.msg(data.msg);
                    flags = true;
                    return flags;
                }
            }
        });
    }*/
    // 判断审核意见
    opinion = $.trim(opinion);
    if(checkVal != 1 && opinion == ''){
        layer.msg("审核意见不能为空！");
        flags = true;
        return flags;
    }
    if(opinion.length > 1000){
        layer.msg("审核意见不能超过1000字！");
        flags = true;
        return flags;
    }
    return flags;
}

function vertifyAduitItem(supplierId) {
    $.ajax({
        url:globalPath + "/supplierAudit/vertifyAuditNoPassItem.do",
        type:"POST",
        async:false,
        data:{
            "supplierId":supplierId
        },
        dataType:"json",
        success:function (data) {
            if (data.status == 500) {
                layer.msg(data.msg);
                flags = true;
                return flags;
            }
        }
    });
}


// 审核
function shenhe(status){
    var supplierId = $("input[name='supplierId']").val();
    /*if(status == 3){
        //询问框
        layer.confirm('您确认吗？', {
            closeBtn: 0,
            offset: '100px',
            shift: 4,
            btn: ['确认','取消']
        }, function(){
            var index = layer.prompt({
                title: '请填写理由：',
                formType: 2,
                offset: '100px',
            }, function(text) {
                $.ajax({
                    url: globalPath + "/supplierAudit/recordNotPassed.html",
                    data: {"reason" : text , "supplierId" : supplierId},
                    success: function() {
                        //提交审核
                        $("#status").val(status);
                        $("#status").val(status);
                        $("#form_shen").submit();
                    },
                });
            });
        });
    }else{*/
        //询问框
    if(status == -2){
        /*// 获取审核意见
        var opinion  = $("#opinion").val();
        if(opinion == ''){
            layer.msg("审核意见不能为空！");
            return;
        }
        if(opinion.length > 1000){
            layer.msg("审核意见不能超过1000字！");
            return;
        }*/
        // 校验
        var flags = vartifyAuditCount();
        if(flags){
            return;
        }
        // 校验通过
        layer.confirm('您确认吗？', {
            closeBtn: 0,
            offset: '100px',
            shift: 4,
            btn: ['确认','取消']
        }, function(index){
            //最终意见
            $("#status").val(status);
            //$("#auditOpinion").val($("#auditOpinionFile").val());
            //$("input[name='opinion']").val(opinion);
            // ajax提交改变供应商状态
            $.ajax({
                url: globalPath + "/supplierAudit/updateStatusOfPublictity.do",
                data: $("#form_shen").serialize(),
                success: function (data) {
                    if(data.status == 200){
                        $("#tongguoSpan").hide();
                        $("#tuihui").hide();
                        $("#checkWord").show();
                        $("#publicity").show();
                        $("#tempSave").css("display","inline-block");
                        $("#nextStep").css("display","inline-block");
                        // 显示上传批准审核表页面标签
                        $("#reverse_of_seven_i").show();
                        $("#reverse_of_eight").show();
                    }
                }
            });
            layer.close(index);
            return;
        });
    }

    if(status == 2){
        var flags = false;
        $.ajax({
            url:globalPath + "/supplierAudit/vertifyReturnToModify.do",
            type: "POST",
            async:false,
            data:{
                "supplierId":supplierId,
            },
            dataType:"json",
            success:function (data) {
                if (data.status != 0) {
                    layer.msg(data.msg);
                    flags = true;
                    return;
                }
            }
        });
        if(flags){
            return;
        }
        layer.confirm('您确认吗？', {
            closeBtn: 0,
            offset: '100px',
            shift: 4,
            btn: ['确认','取消']
        }, function(index){
            //最终意见
            $("#status").val(status);
            //提交审核
            $("#form_shen").submit();
        });
    }

    if(status != -2 && status != 2){
        var opinion = document.getElementById('opinion').value;
        opinion = trim(opinion);
        if (opinion != null && opinion != "") {
            if (opinion.length <= 200) {
                layer.confirm('您确认吗？', {
                    closeBtn: 0,
                    offset: '100px',
                    shift: 4,
                    btn: ['确认','取消']
                }, function(index){
                    //最终意见
                    $("#status").val(status);
                    $("input[name='opinion']").val(opinion);
                    if(status == -2){
                        $.ajax({
                            url: globalPath + "/supplierAudit/updateStatusOfPublictity.do",
                            data: $("#form_shen").serialize(),
                            success: function (data) {
                                if(data.status == 200){
                                    layer.alert('完成操作，请公示！', function(index){
                                        $("#opinion").attr("disabled", true);
                                        $("#tongguoSpan").hide();
                                        $("#checkWord").show();
                                        $("#publicity").show();
                                        init_web_upload();
                                        layer.close(index);
                                    });
                                }
                            }
                        });
                        layer.close(index);
                        return;
                    }
                    //提交审核
                    $("#form_shen").submit();
                });
            } else {
                layer.msg("不能超过200字", {offset: '100px'});
            }
        } else {
            layer.msg("请填写最终意见", {offset: '100px'});
            return;
        }
    }
};

// 全选全不选
function selectAll(){
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    if(checkAll.checked){
        for(var i=0;i<checklist.length;i++){
            checklist[i].checked = true;
        } ;
    }else{
        for(var j=0;j<checklist.length;j++){
            checklist[j].checked = false;
        };
    };
}

// 移除
function dele(){
    var ids = [];
    $('input[name="chkItem"]:checked').each(function(){
        ids.push($(this).val());
    });
    if(ids.length>0){
        layer.confirm('确认撤销审核操作吗？', {title:'提示！',offset: ['200px']}, function(index){
            layer.close(index);
            $.ajax({
                url: globalPath + "/supplierAudit/deleteById.html",
                data: "ids="+ids,
                dataType: "json",
                success: function(result){
                    result = eval("(" + result + ")");
                    if(result.msg == "yes"){
                        layer.msg("删除成功！",{offset : '100px'});
                        window.setTimeout(function(){
                            /*var action = globalPath + "/supplierAudit/reasonsList.html";
                            $("#form_id").attr("action",action);
                            $("#form_id").submit();*/
                            var action = globalPath + "/supplierAudit/reasonsList.html";
                            submitJumpForm(action);
                        }, 1000);
                    }
                },
                error: function(message){
                    layer.msg("删除失败！",{offset : '100px'});
                }
            });
        });
    }else{
        layer.alert("请选择需要移除的记录！",{offset:'100px'});
    }
}

// 去改状态
function toUpdateStatus(){
	var ids = [];
	$('input[name="chkItem"]:checked').each(function(){
		ids.push($(this).val());
	});
	if(ids.length > 0){
		$("#auditStatusRadio").fadeIn().css("display","inline");
	}else{
		layer.alert("请选择需要修改状态的记录！",{offset:'100px'});
	}
}
// 改状态
function updateStatus(status){
	var ids = [];
	var bool = true;
	var errorMsg = "";
	$('input[name="chkItem"]:checked').each(function(){
		ids.push($(this).val());
		var currSt = $(this).attr("st");// 当前审核状态
		var currAt = $(this).attr("at");// 当前审核类型
		/* // 已修改 不能点击任何状态
		if(currSt == 3){
			bool = false;
			errorMsg = "选择中包含已修改的记录，已修改的记录不能修改任何状态！可以重新审核";
			return false;
		}
		// 已撤销 不能点击任何状态
		if(currSt == 5 || currSt == 6){
			bool = false;
			errorMsg = "选择中包含撤销退回/撤销不通过的记录，撤销的记录不能修改任何状态！可以重新审核";
			return false;
		}
		// 退回修改/未修改 只能点击 撤销退回
		if((currSt == 1 || currSt == 4) && status != 5){
			bool = false;
			errorMsg = "选择中包含退回修改/未修改的记录，退回修改和未修改的记录只能撤销退回！";
			return false;
		}
		// 审核不通过 只能点击 撤销不通过
		if(currSt == 2 && status != 6){
			bool = false;
			errorMsg = "选择中包含审核不通过的记录，审核不通过的记录只能撤销不通过！";
			return false;
		} */
		// 已修改 不能点击任何状态
		if(currSt == 3){
			bool = false;
			errorMsg = "选择中包含已修改的记录，已修改的记录不能修改任何状态！可以重新审核";
			return false;
		}
		// 已撤销 不能点击任何状态
		if(currSt == 5){
			bool = false;
			errorMsg = "选择中包含撤销审核的记录，撤销审核的记录不能修改任何状态！可以重新审核";
			return false;
		}
		// 退回修改/未修改/审核不通过 只能点击 撤销审核
		if((currSt == 1 || currSt == 4 || currSt == 2) && status != 5){
			bool = false;
			errorMsg = "选择中包含有问题/未修改/审核不通过的记录，只能撤销审核！";
			return false;
		}
		// 审核类型为 供应商类型/产品类别 的审核记录只能撤销审核
		if((currAt == "supplierType_page" || currAt.indexOf("items_") == 0) && status != 5){
			bool = false;
			errorMsg = "选择中包含供应商类型/产品类别，只能撤销审核！";
			return false;
		}
	});
	if(!bool){
		//layer.msg(errorMsg, {offset : '100px'});
		layer.alert(errorMsg);
		return;
	}
	if(ids.length > 0){
		layer.confirm('您确定要更改状态吗？', {title:'提示！', offset: ['200px']}, function(index){
			layer.close(index);
			$.ajax({
				url: globalPath + "/supplierAudit/updateReturnStatus.do",
				type: "post",
				data: {
					ids: ids.join(","),
					status: status
				},
				dataType: "json",
				success: function(result){
					if(result && result.status == 500){
						layer.msg(result.msg, {offset : '100px'});
			 			window.setTimeout(function(){
							/*var action = globalPath + "/supplierAudit/reasonsList.html";
							$("#form_id").attr("action",action);
							$("#form_id").submit();*/
			 			    var action = globalPath + "/supplierAudit/reasonsList.html";
						    submitJumpForm(action);
						}, 1000);
					}else{
						layer.msg(result.msg, {offset : '100px'});
					}
				},
				error: function(message){
					layer.msg("更新失败！", {offset : '100px'});
				}
			});
		});
	}else{
		layer.alert("请选择需要修改状态的记录！",{offset:'100px'});
	}
}