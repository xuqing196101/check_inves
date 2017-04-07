/**
 * Created by zhangxq on 2017/4/7.
 */
/**查詢按钮*/
$(".queryBtn").click(function () {
    $.ajaxSetup({cache:false});
    var packageId = $("input[name='packageId']").val();
    var projectId = $("input[name='projectId']").val();
    var path = globalPath + "/expert/gotoCiteExpertView.html?packageId=" + packageId + "&projectId=" + projectId + "&expertName="+$("#expertName").val();
    $("#tab-1").load(path);
})
/**重置按钮*/
$(".resetBtn").click(function () {
    var packageId = $("input[name='packageId']").val();
    var projectId = $("input[name='projectId']").val();
    var path = globalPath + "/expert/gotoCiteExpertView.html?packageId="+packageId + "&projectId="+projectId
    $("#tab-1").load(path);
})
/**点击全选按钮*/
$("#checkAll").click(function () {
    var value = "";
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    if(checkAll.checked) {
        for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
            var hiddenValue = $("#hiddenValue").val();
            if(hiddenValue != null && hiddenValue != '' ){
                var newStr  = hiddenValue.replace(checklist[i].value+",","");
                $("#hiddenValue").val(newStr);
            }
            value = value + checklist[i].value+",";
        }
    } else {
        for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
            var hiddenValue = $("#hiddenValue").val();
            var newStr  = hiddenValue.replace(checklist[j].value+",","");
            $("#hiddenValue").val(newStr);
        }
    }
    $("#hiddenValue").val($("#hiddenValue").val()+value);
})
/**初始化选中值,避免分页取消勾选*/
function _selected(value){
    var count = 0;
    $("#hiddenValue").val(value);
    var strs = new Array(); //定义一数组
    strs = value.split(","); //字符分割
    var checkAll = document.getElementById("checkAll");
    var checklist = document.getElementsByName("chkItem");
    for(var j = 0; j < checklist.length; j++) {
        for(var k = 0 ; k < strs.length; k++){
            if(checklist[j].value == strs[k]) {
                checklist[j].checked = true;
                count ++;
            }
        }
    }
    if(count != 0 && checklist.length == count){
        checkAll.checked = true;
    }
}
/**单选一个checkbox*/
$("input[name='chkItem']").click(function () {
    var checklist = document.getElementsByName("chkItem");
    var checkAll = document.getElementById("checkAll");
    for(var i = 0; i < checklist.length; i++) {
        if(checklist[i].checked == false) {
            var hiddenValue = $("#hiddenValue").val();
            // != null 去除重复
            if(hiddenValue != null && hiddenValue != ''){
                var newStr  = hiddenValue.replace(checklist[i].value+",","");
                $("#hiddenValue").val(newStr);
            }
        }
        var value = "";
        for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
                var hiddenValue = $("#hiddenValue").val();
                // != null 去除重复
                if(hiddenValue != null && hiddenValue != ''){
                    var newStr  = hiddenValue.replace(checklist[j].value+",","");
                    $("#hiddenValue").val(newStr);
                }
                value = value + checklist[j].value+",";
            }
        }
        $("#hiddenValue").val($("#hiddenValue").val()+value);
    }
    var len = $("input[name='chkItem']:checked").length;
    if(len != 0 && (len == 0 && checklist.length == 0 )){
        checkAll.checked = true;
    }
})
/**返回按钮*/
$(".footerDiv > .back").click(function () {
    var projectId = $("input[name='projectId']").val();
    $.ajaxSetup({cache:false});
    var path = globalPath + '/packageExpert/auditManage.html?projectId='+projectId;
    $("#tab-1").load(path);
})
/**保存按钮*/
$(".footerDiv > .save").click(function () {
    var packageId = $("input[name='packageId']").val();
    var projectId = $("input[name='projectId']").val();
    var idstr = $("#hiddenValue").val();
    if(idstr == ''){
        layer.alert("请选择专家",{offset: '50px', shade:0.01});
    }else{
        $.ajax({
            url: globalPath + '/expert/saveCiteExpert.do',
            type: 'POST',
            data: {'packageId':packageId,'expertIds':idstr},
            dataType:'json',
            success: function (data) {
                if(data.isSuccess){
                    if(data.messageCode==20){
                        layer.alert("引用临时专家保存成功",{offset: '50px', shade:0.01, time:500});
                        var path  = globalPath + "/packageExpert/auditManage.html?projectId="+projectId
                        $("#tab-1").load(path);
                    }else{
                        layer.alert("服务器异常",{offset: '50px', shade:0.01});
                    }
                }else{
                    layer.alert("服务器异常",{offset: '50px', shade:0.01});
                }
            },
            error: function () {

            }
        });
    }
})
