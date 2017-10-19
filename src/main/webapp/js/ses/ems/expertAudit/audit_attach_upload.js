$(function () {
    // 导航栏显示
    $("#reverse_of_six").attr("class","active");
    $("#reverse_of_six").removeAttr("onclick");

    // 绑定审核通过和不通过事件
    /*$("#auditPass").click(function () {
        // -3:复审通过
        audit(-3);
    });
    $("#auditNoPass").click(function () {
        // 5:复审不通过
        audit(5);
    });*/
    $("#auditOver").click(function () {
        // 复审结束
        audit();
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
function audit(){
    // 提交表单，修改供应商状态
    // 设置状态
    // $("#status").val(status);
    // 获取上传文件businessId
    var auditOpinionFile = $("#auditOpinionFile").val();
    $("#auditOpinionAttach").val(auditOpinionFile);
    layer.confirm('您确认吗？', {
        closeBtn: 0,
        offset: '100px',
        shift: 4,
        btn: ['确认', '取消']
    }, function (index) {
        $.ajax({
            url: globalPath + "/expertAudit/updateStatusAjax.do",
            type: "POST",
            data: $("#form_shenhe").serialize(),
            dataType: "json",
            success: function (data) {
                if (data.status == 200) {
                    // 跳转查询界面
                    layer.confirm("操作成功", {
                            btn: ['确定']
                        }, function () {
                            window.location.href = globalPath + "/expertAudit/list.html";
                        }
                    )
                } else {
                    if (data.status == 500) {
                        layer.alert(data.msg);
                    }
                }
            }
        });
        layer.close(index);
    });
}

//初审结束
function chuAuditEnd(){
	layer.confirm('您确认吗？', {
        closeBtn: 0,
        offset: '100px',
        shift: 4,
        btn: ['确认', '取消']
    }, function (index) {
        $.ajax({
            url: globalPath + "/expertAudit/chuAudit.do",
            type: "POST",
            data: $("#form_shenhe").serialize(),
            dataType: "json",
            success: function (data) {
                if (data.status == 200) {
                    // 跳转查询界面
                    layer.confirm("操作成功", {
                            btn: ['确定']
                        }, function () {
                            window.location.href = globalPath + "/expertAudit/list.html";
                        }
                    )
                } else {
                    if (data.status == 500) {
                        layer.alert(data.msg);
                    }
                }
            }
        });
        layer.close(index);
    });
}