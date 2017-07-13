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
        var supplierStatus = $("#supplierStatus").val();
        // 获取审核意见
        var opinion  = $("#opinion").val();
        if(supplierStatus != -3){
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
        }
        action = globalPath + "/supplierAudit/uploadApproveFile.html";
    }
    $("#form_id").attr("action", action);
    $("#form_id").submit();
}