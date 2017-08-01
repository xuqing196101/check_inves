$(function () {
    $("#reverse_of_seven").attr("class","active");
    $("#reverse_of_seven").removeAttr("onclick");
    // 供应商id
    var supplierId = $("#supplierId").val();
    // 预审核结束状态
    if(status == -2 || status == -3 || status == 3){
        $("#checkWord").show();
        // 审核状态为3（复审不合格）或者-3（公示中）的意见不可更改
        if( status == 3){
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
	if(status == -2 || status == 0){
		tempSave(1);
	}
	var sign = $("input[name='sign']").val();
	if(status == -3 || status == 3 || (status == 1 && sign == 1)){
		tempSave('nextStep');
	}
    
}

/**
 * 审核汇总暂存
 */
function tempSave(flag){
	if(flag == 'nextStep'){
		$("#form_id").attr("action", globalPath + "/supplierAudit/uploadApproveFile.html");
        $("#form_id").submit();
	}else{
	    // 获取审核意见
	    var opinion  = $("#opinion").val();
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
	        type: "POST",
	        data:$("#opinionForm").serialize(),
	        dataType:"json",
	        success:function (data) {
	            if(flag == 1){
	                if(data == 500){
                        layer.alert(data.msg);
                    }else {
                        var action = globalPath + "/supplierAudit/uploadApproveFile.html";
                        $("#form_id").attr("action", action);
                        $("#form_id").submit();
                    }
	            }else{
	                if(data.status == 200){
	                    layer.alert("暂存成功！");
	                }
	            }
	            // 关闭旋转图标
	            layer.close(index);
	        }
	    });
	}
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
    var opinion  = $("#opinion").val();
    var checkVal = $("input:radio[name='selectOption']:checked").val();
    if(checkVal == undefined){
        layer.msg("请选择审核意见项");
        flags = true;
        return flags;
    }
    // 点击审核通过复选框时的校验
    if(checkVal == 1){
        var supplierId=$("#supplierId").val();
        $.ajax({
            url:globalPath + "/supplierAudit/vertifyAuditItem.do",
            type: "POST",
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
    // 点击审核不通过复选框时的校验
    if(checkVal == 0){
        var supplierId=$("#supplierId").val();
        $.ajax({
            url:globalPath + "/supplierAudit/vertifyAuditNoPassItem.do",
            type: "POST",
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
    // 判断审核意见
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

function  vertifyAduitItem(supplierId) {
    $.ajax({
        url:globalPath + "/supplierAudit/vertifyAuditNoPassItem.do",
        type: "POST",
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