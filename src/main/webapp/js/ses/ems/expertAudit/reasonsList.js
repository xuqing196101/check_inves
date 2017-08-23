$(function () {
    // 导航栏显示
    $("#reverse_of_five").attr("class","active");
    $("#reverse_of_five").removeAttr("onclick");
    // 获取专家ID
    var expertId=$("#expertId").val();

    // 将审核意见的radio选中
    var hiddenSelectOptionId = $("#hiddenSelectOptionId").val();
    $("input[name='selectOption'][value='"+hiddenSelectOptionId+"']").prop("checked",true);
    // 预复审合格状态
    if(status == -2 || status == -3 || status == 5 || status == 4){
        $("#checkWord").show();
        // 审核状态为5（复审不合格）或者-3（公示中）的意见不可更改
        if(status == -3 ||status == 5 || status == 4){
            $("input[name='selectOption']").prop("disabled",true);
            $("#opinion").prop("disabled", true);
        }
    }
    
    //除了待审核状态都不可操作
    if(status != 0 && status != -2 && (sign !=2 && status ==1) && status != 6){
    	$("#opinion").prop("disabled", true);
    }
    
    $("input[name='selectOption']").bind("click", function(){
        // 清空意见内容
        //$("#cate_result").val("");
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
        getCheckOpinionType(expertId);
    });
    // 判断复选框操作
    if(hiddenSelectOptionId != '' && hiddenSelectOptionId == 0){
        // 预审核不通过
        $("#cate_result").html("不通过。");
    }
    if(hiddenSelectOptionId != '' && hiddenSelectOptionId == 1){
        // 预审核通过
        getCheckOpinionType(expertId);
    }
});

function getCheckOpinionType(expertId){
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
    // 获取专家ID
    var expertId = $("#expertId").val();
    $.ajax({
        url:globalPath + "/expertAudit/selectChooseOrNoPassCate.do",
        data:{
            "id" : expertId
        },
        success:function (data) {
            var opinionData = "同意入库，选择了"+data.passCateCount+"个参评类别，通过了"+(data.passCateCount - data.noPassCateCount)+"个参评类别。";
            $("#cate_result").html(opinionData);
            //$("#opinionBack").val(opinionData);
            // 关闭旋转图标
            layer.close(index);
        }
    });
}

/**
 * 上一步操作
 */
function lastStep() {
    var action = globalPath + "/expertAudit/expertFile.html";
    $("#form_id").attr("action", action);
    $("#form_id").submit();
}

/**
 * 下一步操作
 */
function nextStep() {
	if(status == -2 || status == 1){
		tempSave(1);
	}
    if(status == -3 || status == 4 || status == 5){
    	$("#form_id").attr("action", globalPath + "/expertAudit/uploadApproveFile.html");
        $("#form_id").submit();
    }
}

/**
 * 审核汇总暂存
 */
function tempSave(flag){
    // 获取审核意见
    var opinion  = $("#opinion").val();
    // 获取选择radio类型
    var selectOption = $("input[name='selectOption']:checked").val();

    if(flag == 1){
        var flags = vartifyAuditCount();
        if(flags){
            return;
        }
        // 判断附件是否下载
        var downloadAttachFile = $("#downloadAttachFile").val();
        if(downloadAttachFile == ''){
            layer.msg("请下载审批表！");
            return;
        }
        // 标识后台不做校验
        $("#vertifyFlag").val("vartify");
    }
    // 请求操作
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
    // 将审核意见表单赋值
    $("#opinionId").val(opinion);
    $("#flagTime").val(1);
    $("#flagAudit").val(selectOption);
    $.ajax({
        url:globalPath + "/expertAudit/saveAuditOpinion.do",
        type: "POST",
        data:$("#opinionForm").serialize(),
        dataType:"json",
        success:function (data) {
            if(flag == 1){
                var action = globalPath + "/expertAudit/uploadApproveFile.html";
                $("#form_id").attr("action", action);
                $("#form_id").submit();
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

/**
 * 下载入库复审表
 * @param str
 */
function downloadTable(str) {
    var cate_result = $("#cate_result").html();
    var auditOpinion = $("#opinion").val();
    $("input[name='tableType']").val(str);
    $("input[name='opinion']").val(cate_result + auditOpinion);
    $("#form_id_word").attr("action", globalPath + "/expertAudit/download.html");
    $("#form_id_word").submit();
    $("#downloadAttachFile").val("1");
    $("#isDownLoadAttch").val("1");
    if(str == 1){
    	downloadCount();
    }
}

/**
 * 校验审核项
 * @Auth Easong
 */
function vartifyAuditCount(){
    var flags = false;
    // 获取审核意见
    var opinion  = $("#opinion").val();
    // 获取选择radio类型
    var checkVal = $("input:radio[name='selectOption']:checked").val();
    if(checkVal === undefined){
        layer.msg("请选择审核意见项");
        flags = true;
        return flags;
    }
    // 点击审核通过复选框时的校验
    if(checkVal == 1){
        var expertId=$("#expertId").val();
        $.ajax({
            url:globalPath + "/expertAudit/vertifyAuditItem.do",
            type: "POST",
            async:false,
            data:{
                "expertId":expertId
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
        var expertId=$("#expertId").val();
        $.ajax({
            url:globalPath + "/expertAudit/vertifyAuditNoPassItem.do",
            type: "POST",
            async:false,
            data:{
                "expertId":expertId
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
    if(opinion == ''){
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