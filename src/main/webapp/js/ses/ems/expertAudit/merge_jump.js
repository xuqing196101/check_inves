function jump(str) {
    var action;
    if (str == "basicInfo") {
        action = globalPath + "/expertAudit/basicInfo.html";
    }
    if (str == "experience") {
        action = globalPath + "/expertAudit/experience.html";
    }
    if (str == "expertType") {
        action = globalPath + "/expertAudit/expertType.html";
    }
    if (str == "expertFile") {
        action = globalPath + "/expertAudit/expertFile.html";
    }
    if (str == "product") {
        action = globalPath + "/expertAudit/product.html";
    }
    if (str == "reasonsList") {
        action = globalPath + "/expertAudit/reasonsList.html";
    }
    if (str == "uploadApproveFile") {
        var expertId = $("#expertId").val();
        // 获取审核意见
        var opinion  = $("#opinion").val();
        if(expertId != -3){
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
        action = globalPath + "/expertAudit/uploadApproveFile.html";
    }
    $("#form_id").attr("action", action);
    $("#form_id").submit();
}