$(function () {
    $("#reverse_of_five_i").show();
    $("#reverse_of_six").show();
    $("#reverse_of_six").attr("class","active");

    // 绑定审核通过和不通过事件
    $("#auditPass").click(function () {
        // -3:复审通过
        audit(-3);
    });
    $("#auditNoPass").click(function () {
        // 5:复审不通过
        audit(5);
    });
})

/**
 * 上一步
 */
function lastStep(){
    var action = globalPath + "/expertAudit/reasonsList.html";
    $("#form_id").attr("action",action);
    $("#form_id").submit();
}

/**
 * 审核
 * @param status
 */
function audit(status){
    // 提交表单，修改供应商状态
    // 设置状态
    $("#status").val(status);
    // 获取上传文件businessId
    var auditOpinionFile = $("#auditOpinionFile").val();
    $("#auditOpinionAttach").val(auditOpinionFile);
    $.ajax({
        url:globalPath + "/expertAudit/updateStatusAjax.do",
        type: "POST",
        data:$("#form_shenhe").serialize(),
        dataType:"json",
        success:function (data) {
            if(data.status == 200){
                // 跳转查询界面
                layer.confirm("操作成功",{
                        btn:['确定']
                    },function(){
                        window.location.href = globalPath+"/expertAudit/list.html";
                    }
                )
            }else{
                if(data.status == 500){
                    layer.alert(data.msg);
                }
            }
        }
    });
}
