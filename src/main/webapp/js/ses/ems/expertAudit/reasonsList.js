$(function () {
    // 将审核意见的radio选中
    var hiddenSelectOptionId = $("#hiddenSelectOptionId").val();
    $("input[name='selectOption'][value='"+hiddenSelectOptionId+"']").prop("checked",true);

    $("input[name='selectOption']").bind("click", function(){
        // 清空意见内容
        $("#opinion").val("");
        var selectedVal = $(this).val();
        if(selectedVal == 0){
            $("#opinion").val("不通过");
            return;
        }
        // 判断意见是否已经获取，有的话不再发送请求
        var opinionBack = $("#opinionBack").val();
        if(opinionBack != ''){
            $("#opinion").val(opinionBack);
            return;
        }
        // 获取专家ID
        var expertId = $("#expertId").val();
        $.ajax({
            url:globalPath + "/expertAudit/selectChooseOrNoPassCate.do",
            data:{
                "id" : expertId
            },
            success:function (data) {
                var opinionData = "同意入库，选择了"+data.passCateCount+"个小类，通过了"+(data.passCateCount - data.noPassCateCount)+"个小类";
                $("#opinion").val(opinionData);
                $("#opinionBack").val(opinionData);
            }
        })
    });
})

/**
 * 下一步操作
 */
function nextStep() {
    tempSave(1);
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
        if(opinion == ''){
            layer.msg("审核意见不能为空！");
            return;
        }
        if(opinion.length > 1000){
            layer.msg("审核意见不能超过1000字！");
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
        }
    });

}