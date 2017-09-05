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
    if (str == "preliminaryInfo") {
    	action = globalPath + "/expertAudit/preliminaryInfo.html";
    }
    if (str == "reasonsList") {
        action = globalPath + "/expertAudit/reasonsList.html";
    }
    if (str == "uploadApproveFile") {
        var flag = true;
        if(status == -2 || status == 16 || status == 15){
            /*var expertId = $("#expertId").val();
            $.ajax({
                url:globalPath + "/expertAudit/isHaveOpinion.do",
                type: "POST",
                async:false,
                data:{
                    "expertId":expertId
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
                        layer.msg("请下载审批表！");
                        flag = false;
                        return;
                    }
                }
            });*/
            layer.msg("请点击'审核汇总'的下一步进行操作！");
            return;
        }else {
            action = globalPath + "/expertAudit/uploadApproveFile.html";
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
	
    // 获取专家状态
    status = $("#status").val();
    sign = $("input[name='sign']").val();
    if(status == -2 || status == -3 || status == 5 || status == 4){
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
