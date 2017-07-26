function jump(str) {
    var action;
    if (str == "essential") {
        action = globalPath + "/supplierAudit/essential.html";
    }
    if (str == "financial") {
        action = globalPath + "/supplierAudit/financial.html";
    }
    if (str == "shareholder") {
        action = globalPath + "/supplierAudit/shareholder.html";
    }
    if (str == "materialProduction") {
        action = globalPath + "/supplierAudit/materialProduction.html";
    }
    if (str == "materialSales") {
        action = globalPath + "/supplierAudit/materialSales.html";
    }
    if (str == "engineering") {
        action = globalPath + "/supplierAudit/engineering.html";
    }
    if (str == "serviceInformation") {
        action = globalPath + "/supplierAudit/serviceInformation.html";
    }
    if (str == "items") {
        action = globalPath + "/supplierAudit/items.html";
    }
    if (str == "aptitude") {
        action = globalPath + "/supplierAudit/toPageAptitude.html";
    }
    if (str == "contract") {
        action = globalPath + "/supplierAudit/contract.html";
    }
    if (str == "applicationForm") {
        action = globalPath + "/supplierAudit/applicationForm.html";
    }
    if (str == "reasonsList") {
        action = globalPath + "/supplierAudit/reasonsList.html";
    }
    if (str == "supplierType") {
        action = globalPath + "/supplierAudit/supplierType.html";
    }
    if (str == "uploadApproveFile") {
        //var flag = true;
        if(status == -2 || status == 0){
            /*var supplierId = $("#supplierId").val();
            $.ajax({
                url:globalPath + "/supplierAudit/isHaveOpinion.do",
                type: "POST",
                async:false,
                data:{
                  "supplierId":supplierId
                },
                dataType:"json",
                success:function (data) {
                    if(data.data == null){
                        layer.msg("审核意见不能为空！");
                        flag = false;
                        return;
                    }else if(data.data.opinion == null){
                        layer.msg("审核意见不能为空！");
                        flag = false;
                        return;
                    }else if(data.data.isDownLoadAttch == null){
                        // 判断附件是否下载
                        layer.msg("请下载审核表！");
                        flag = false;
                        return;
                    }
                }
            });*/
            layer.msg("请点击'审核汇总'下一步进行操作！");
            return;
        }else{
            action = globalPath + "/supplierAudit/uploadApproveFile.html";
        }
        /*if(flag){

        }*/
    }
    $("#form_id").attr("action", action);
    $("#form_id").submit();
}
var status;
var sign;
$(function () {
    status = $("#status").val();
    sign = $("input[name='sign']").val();
    // 预审核结束状态
    if(status == -2 || status == -3 || status == 3 || (status == 1 && sign == 1)){
        $("#reverse_of_seven_i").show();
        $("#reverse_of_eight").show();
    }
})