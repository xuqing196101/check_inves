$(function () {
    $("#reverse_of_seven").attr("class","active");
    $("#reverse_of_seven").removeAttr("onclick");
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
        var index = layer.load(0, {
            shade : [ 0.1, '#fff' ],
            offset : [ '40%', '50%' ]
        });
        // 获取供应商ID
        var supplierId = $("#supplierId").val();
        $.ajax({
            url:globalPath + "/supplierAudit/selectChooseOrNoPassCate.do",
            data:{
                "id" : supplierId
            },
            success:function (data) {
                var opinionData = "同意入库，选择了"+data.passCateCount+"个产品类别，通过了"+(data.passCateCount - data.noPassCateCount)+"个产品类别";
                $("#opinion").val(opinionData);
                $("#opinionBack").val(opinionData);
                // 关闭旋转图标
                layer.close(index);
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
    // 请求操作
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
    // 将审核意见表单赋值
    $("#opinionId").val(opinion);
    $("#flagTime").val(0);
    $("#flagAduit").val(selectOption);
    $.ajax({
        url:globalPath + "/supplierAudit/saveAuditOpinion.do",
        type: "POST",
        data:$("#opinionForm").serialize(),
        dataType:"json",
        success:function (data) {
            if(flag == 1){
                var action = globalPath + "/supplierAudit/uploadApproveFile.html";
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
 * 下载审核/复核/意见函/考察表
 * @param str
 */
function downloadTable(str) {
    $("input[name='tableType']").val(str);
    var auditOpinion = $("#opinion").val();
    $("input[name='opinion']").val(auditOpinion);
    $("#shenhe_form_id").attr("action", globalPath + "/supplierAudit/downloadTable.html");
    $("#shenhe_form_id").submit();
    $("#downloadAttachFile").val("1");
}