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
    if (str == "product") {
        action = globalPath + "/expertAudit/product.html";
    }
    if (str == "expertFile") {
        action = globalPath + "/expertAudit/expertFile.html";
    }
    if (str == "reasonsList") {
        action = globalPath + "/expertAudit/reasonsList.html";
    }
    if (str == "uploadApproveFile") {
        // 获取审核意见
        var opinion  = $("#opinion").val();
        if(status == -2 || status == -3 || status == 5){
            // 获取审核意见
            var opinion  = $("#opinion").val();
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


var status;
$(function () {
    // 获取专家状态
    debugger;
    status = $("#status").val();
    if(status == -2 || status == -3 || status == 5){
        $("#reverse_of_five_i").show();
        $("#reverse_of_six").show();
    }
});

/* // 获取导航cookie值
    var strType =  readCookie("navigation_type");

    // 获取专家状态
    var status = $("#status").val();
    if(status == -2 || status == -3 || status == 5){
        $("#reverse_of_five_i").show();
        $("#reverse_of_six").show();
    }
    // 上传批准审核表
    if(strType == "uploadApproveFile"){
        $("#reverse_of_six").attr("class","active");
        $("#reverse_of_six").removeAttr("onclick");
    }
    // 审核汇总
    if(strType == "reasonsList"){
        $("#reverse_of_five").attr("class","active");
        $("#reverse_of_five").removeAttr("onclick");
    }
    // 承诺书和申请表
    if(strType == "expertFile"){
        $("#reverse_of_four").attr("class","active");
        $("#reverse_of_four").removeAttr("onclick");
    }
    // 产品类别
    if(strType == "product"){
        $("#reverse_of_three").attr("class","active");
        $("#reverse_of_three").removeAttr("onclick");
    }
    // 专家类别
    if(strType == "expertType"){
        $("#reverse_of_two").attr("class","active");
        $("#reverse_of_two").removeAttr("onclick");
    }
    // 基本信息
    if(strType == "basicInfo"){
        $("#reverse_of_one").attr("class","active");
        $("#reverse_of_one").removeAttr("onclick");
    }
});*/
