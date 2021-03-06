$(function () {
    // 绑定审核通过和不通过事件
    /*$("#auditPass").click(function () {
        // -3:审核通过
        audit(-3);
    });
    $("#auditNoPass").click(function () {
        // 3:审核不通过
        audit(3);
    });*/
    $("#auditOver").click(function () {
        // 3:审核不通过
        audit();
    });
});

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
            url: globalPath + "/supplierAudit/updateStatusAjax.do",
            type: "POST",
            data: $("#form_shen").serialize(),
            dataType: "json",
            success: function (data) {
                if (data.status == 200) {
                    // 跳转查询界面
                    layer.confirm("操作成功", {
                            btn: ['确定']
                        }, function () {
                            window.location.href = globalPath + "/supplierAudit/supplierAll.html";
                        }
                    );
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
