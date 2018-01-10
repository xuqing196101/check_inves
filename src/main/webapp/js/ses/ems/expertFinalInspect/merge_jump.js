function jump(str) {
    var action;
    if (str == "basicInfo") {
        action = globalPath + "/finalInspect/basicInfo.html";
    }
    if (str == "expertType") {
        action = globalPath + "/finalInspect/expertType.html";
    }
    if (str == "product") {
        action = globalPath + "/finalInspect/product.html";
    }
    if (str == "expertFile") {
        action = globalPath + "/finalInspect/expertFile.html";
    }
    if (str == "expertAttachment") {
    	action = globalPath + "/finalInspect/expertAttachment.html";
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
function tojump(str,i) {
    if (str == "expertAttachment") {
    	action = globalPath + "/finalInspect/expertAttachment.html";
    }
    $("#finalInspectNumber").val(i);
    $("#form_id").attr("action", action);
    $("#form_id").submit();
}
