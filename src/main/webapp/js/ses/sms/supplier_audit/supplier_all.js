//Ma Mingwei
function trim(str) { //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/** 全选全不选 */
function selectAll() {
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    if (checkAll.checked) {
        for (var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
        }
    } else {
        for (var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
        }
    }
}

/** 单选 */
function check() {
    var count = 0;
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    for (var i = 0; i < checklist.length; i++) {
        if (checklist[i].checked == false) {
            checkAll.checked = false;
            break;
        }
        for (var j = 0; j < checklist.length; j++) {
            if (checklist[j].checked == true) {
                checkAll.checked = true;
                count++;
            }
        }
    }
}
//审核
function shenhe(id) {
    if (id == null) {
        var size = $(":checkbox:checked").size();
        if (size == 0) {
            layer.msg("请选择供应商！", {
                offset: '100px',
            });
            return;
        }
        var id = $(":checkbox:checked").val();
        if (size > 1) {
            layer.msg("只能选择一项！", {
                offset: '100px',
            });
            return;
        }
    }
    /* var state = $("#" + id + "").parents("tr").find("td:last").text();//.trim();
     state = trim(state);
     var isExtract = $("#" + id + "_isExtract").text();
     if(state == "公示中" || state == "审核通过" || state == "退回修改" || state == "审核未通过" || state == "复核通过" || state == "复核未通过" || state == "合格" || state == "不合格") {
     layer.msg("请选择待审核项！", {
     offset: '100px',
     });
     return;
     } */
    var state = $("#" + id + "").attr("sts");
    if (state != 0 && state != 9 && state != -2 && state != 1 && state != 5) {
        layer.msg("请选择待审核项！", {
            offset: '100px',
        });
        return;
    }
    //抽取之后的才能复核
    /*   if(state == "未抽取"){
     layer.msg("该供应商未抽取 !", {
     offset : '100px',
     });
     return;
     } */

    // 校验审核人权限
    /* if(!validateAuditor(id)){
     return;
     } */

    // 审核人弹出框
    $.ajax({
        url: globalPath + "/supplierAudit/ajaxSupplier.do",
        data: {"supplierId": id},
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result && result.status == 1) {
                if ($.trim(result.data.auditor) == "") {
                    layer.prompt({
                        formType: 0,// 0（文本）默认1（密码）2（多行文本）
                        value: '',
                        title: '请填写审核人：'
                    }, function (val, index) {
                        if ($.trim(val) == "") {
							$(ele).val("");
							$(ele).focus();
							return;
                        }
                        // 保存审核人
                        var bool = updateAuditor(id, val);
                        if (bool) {
                            toAlertAudit(id);
                        }
                        layer.close(index);
                    });
                } else {
                    toAlertAudit(id);
                }
            } else {
                layer.msg(result.msg);
            }
        }
    });

}

function toAlertAudit(id) {
    $.ajax({
        url: globalPath + "/supplierAudit/auditNotReason.do",
        data: {"supplierId": id},
        success: function (result) {
            if (result == "" || result == null) {
                layer.alert('点击审核项，弹出不通过理由框！', {
                        title: '审核操作说明：',
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 1,
                        offset: '100px',
                        shift: 4 //动画类型
                    },
                    function () {
                        $("input[name='supplierId']").val(id);
                        $("#shenhe_form_id").attr("action", globalPath + "/supplierAudit/essential.html");
                        $("#shenhe_form_id").submit();
                    });
            } else {
                layer.alert(result, {
                        title: '上次未通过审核的原因：',
                        skin: 'layui-layer-molv', //样式类名
                        closeBtn: 0,
                        offset: '100px',
                        shift: 4 //动画类型
                    },
                    function () {
                        $("input[name='supplierId']").val(id);
                        $("#shenhe_form_id").attr("action", globalPath + "/supplierAudit/essential.html");
                        $("#shenhe_form_id").submit();
                    });
            }
        }
    });
}

function toEssential(id, sign) {
    // 校验审核人权限
    /* if(!validateAuditor(id)){
     return;
     } */
    //jumppage(globalPath + '/supplierAudit/essential.html?supplierId='+id+'&sign='+sign)
    // 审核人弹出框
    $.ajax({
        url: globalPath + "/supplierAudit/ajaxSupplier.do",
        data: {"supplierId": id},
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result && result.status == 1) {
                if ($.trim(result.data.auditor) == "") {
                    layer.prompt({
                        formType: 0,// 0（文本）默认1（密码）2（多行文本）
                        value: '',
                        title: '请填写审核人：'
                    }, function (val, index, ele) {
                        if ($.trim(val) == "") {
							$(ele).val("");
							$(ele).focus();
							return;
                        }
                        // 保存审核人
                        var bool = updateAuditor(id, val);
                        if (bool) {
                            jumppage(globalPath + '/supplierAudit/essential.html?supplierId=' + id + '&sign=' + sign);
                        }
                        layer.close(index);
                    });
                } else {
                    jumppage(globalPath + '/supplierAudit/essential.html?supplierId=' + id + '&sign=' + sign);
                }
            } else {
                layer.msg(result.msg);
            }
        }
    });
}

function validateAuditor(id) {
    var validateAuditorFlag = true;
    $.ajax({
        url: globalPath + "/supplierAudit/validateAuditor.do",
        data: {"supplierId": id},
        dataType: "json",
        async: false,
        success: function (result) {
            if (result && result.status == "0") {
                validateAuditorFlag = false;
                layer.msg(result.msg, {
                    offset: '100px',
                });
            }
        }
    });
    return validateAuditorFlag;
}

function updateAuditor(id, auditor) {
    var bool = false;
    $.ajax({
        url: globalPath + "/supplierAudit/updateAuditor.do",
        data: {"supplierId": id, "auditor": auditor},
        type: "post",
        dataType: "json",
        async: false,
        success: function (result) {
            if (result && result.status == 1) {
                bool = true;
            } else {
                layer.msg(result.msg);
            }
        }
    });
    return bool;
}


/* $.ajax({
 url: globalPath + "/supplierAudit/auditNotReason.do",
 data: {"supplierId" : id},
 success: function(result) {
 alert(result);
 if(result != null){
 layer.alert(result, {
 title: '上次未通过审核的原因：',
 skin: 'layui-layer-molv', //样式类名
 closeBtn: 0,
 offset: '100px',
 shift: 4 //动画类型
 },
 function(){
 $("input[name='supplierId']").val(id);
 $("#shenhe_form_id").submit();
 aert("1");
 });
 }else{}
 $("input[name='supplierId']").val(id);
 $("#shenhe_form_id").submit();
 alert("2");
 }
 }); */

//重置搜索栏
function resetForm() {
    $("input[name='supplierName']").val("");
    $("input[name='auditDate']").val("");
    $("input[name='addressName']").val("");
    //还原select下拉列表只需要这一句-//但是这一句话不支持IE8即
    //$("#status option:selected").removeAttr("selected");
    //$("#businessNature option:selected").removeAttr("selected");
    //都改成js代码,测试IE8也行的通
    document.getElementById("status")[0].selected = true;
    document.getElementById("businessNature")[0].selected = true;
    $("#form1").submit();
}

//发布
function publish() {
    var id = $(":checkbox:checked").val();
    var size = $(":checkbox:checked").size();
    var state = $("#" + id + "").parents("tr").find("td:last").text();//.trim();
    state = trim(state);
    if (size == 1) {
        if (state != "待审核" && state != "退回再审核" && state != "退回修改" && state != "审核未通过") {
            $.ajax({
                url: globalPath + "/supplierAudit/publish.html",
                data: "supplierId=" + id,
                type: "post",
                datatype: "json",
                success: function (result) {
                    result = eval("(" + result + ")");
                    if (result == "yes") {
                        layer.msg("发布成功！", {offset: '100px'});
                        window.setTimeout(function () {
                            $("#form1").submit();
                        }, 1000);
                    } else {
                        layer.msg('该供应商已发布过！', {
                            shift: 6,
                            offset: '100px'
                        });
                    }
                },
                error: function () {
                    layer.msg("发布失败！", {offset: '100px'});
                }
            });
        } else {
            layer.alert("只有入库供应商才能发布！", {offset: '100px'});
        }
    } else if (size > 1) {
        layer.msg("只能选择一项！", {offset: '100px'});
    } else {
        layer.msg("请选择供应商！", {offset: '100px'});
    }

}

//禁用F12键及右键
function click(e) {
    if (document.layers) {
        if (e.which == 3) {
            oncontextmenu = 'return false';
        }
    }
}
if (document.layers) {
    document.captureEvents(Event.MOUSEDOWN);
}
document.onmousedown = click;
document.oncontextmenu = new Function("return false;");
document.onkeydown = document.onkeyup = document.onkeypress = function () {
    if (window.event.keyCode == 123) {
        window.event.returnValue = false;
        return (false);
    }
};


//下载审核/复核/意见函/考察表
function downloadTable(str) {
    var size = $(":checkbox:checked").size();
    var id = $(":checkbox:checked").val();
    var status = $("#" + id + "").attr("sts");
    if (size == 0) {
        layer.msg("请选供应商 !", {offset: '100px',});
        return;
    } else if (size > 1) {
        layer.msg("只能选择一项 !", {offset: '100px',});
        return;
    } else if (sign == 1 && status == waitCheckStatus) {
        layer.msg("请选择审核过的供应商！", {offset: '100px',});
        return;
    } else if (sign == 2 && status == waitReCheckStatus) {
        layer.msg("请选择复核过的供应商！", {offset: '100px',});
        return;
    } else if (sign == 3 && status == waitInspectStatus) {
        layer.msg("请选择考察过的供应商！", {offset: '100px',});
        return;
    } else if (downloadCheckTabStatusArr != null && $.inArray(status, downloadCheckTabStatusArr) >= 0) {
        var id = $(":checkbox:checked").val();
        $("input[name='supplierId']").val(id);
        $("input[name='tableType']").val(str);
        $("#shenhe_form_id").attr("action", globalPath + "/supplierAudit/downloadTable.html");
        $("#shenhe_form_id").submit();
    } else {
        layer.msg("请选择审核过的供应商！", {offset: '100px',});
    }
}

//添加签字人员
function tianjia() {
    var ids = [];
    $('input[type="checkbox"]:checked').each(function (i) {
        ids.push($(this).val());
    });
    if (ids.length > 0) {
        $.ajax({
            url: globalPath + "/supplierAudit/signature.do?ids=" + ids,
            type: "post",
            success: function (result) {
                if (result == "yes") {
                    layer.open({
                        type: 2,
                        title: '填写签字人员信息',
                        // skin : 'layui-layer-rim', //加上边框
                        area: ['800px', '500px'], //宽高
                        offset: '20px',
                        scrollbar: false,
                        content: globalPath + '/supplierAudit/addSignature.html?ids=' + ids, //url
                        closeBtn: 1, //不显示关闭按钮
                    });
                } else {
                    layer.msg(result + "已添加过！", {offset: '100px',});
                }
            }
        });
    } else {
        layer.msg("请选择供应商 !", {offset: '100px',});
    }
};

//入库申请表下载
function downloadApplication() {
    var size = $(":checkbox:checked").size();
    if (size == 0) {
        layer.msg("请选供应商 !", {offset: '100px',});
    } else if (size > 1) {
        layer.msg("只能选择一项 !", {offset: '100px',});
    } else {
        var supplierId = $(":checkbox:checked").val();
        $("#supplierJson").val(supplierId);
        $("#download_form").submit();
    }
}