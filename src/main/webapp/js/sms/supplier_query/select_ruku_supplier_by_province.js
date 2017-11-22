/**
 * Created by yggc-easong on 2017/10/24.
 */
$(function () {
    // 页面加载完毕绑定结果导出事件
    $("#export_result").click(function () {
        // 禁用点击按钮
        $("#export_result").attr("disabled", true);
        var exportExcelCond = $("#exportExcelCond").serialize();
        // 加载等待框
        loading = layer.load(1);
        // 禁用下载按钮
        var url = globalPath + "/supplierQuery/exportExcel.do";
        var xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);        // 也可以使用POST方式，根据接口
        //设定Content-Type头信息，模拟HTTP POST方法发送一个表单，这样服务器才会知道如何处理上传的内容
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        xhr.responseType = "blob";    // 返回类型blob
        // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
        xhr.onload = function () {
            // 请求完成
            if (this.status === 200) {
                // 返回200
                var blob = this.response;
                var reader = new FileReader();
                reader.readAsDataURL(blob);    // 转换为base64，可以直接放入a表情href
                reader.onload = function (e) {
                    // 转换完成，创建一个a标签用于下载
                    var a = document.createElement('a');
                    a.download = '供应商信息.xlsx';
                    a.href = e.target.result;
                    $("body").append(a);    // 修复firefox中无法触发click
                    a.click();
                    $(a).remove();
                    // 关闭等待框
                    layer.close(loading);
                }
            }
            // 开启按钮
            $("#export_result").attr("disabled", false);
        };
        // 发送ajax请求
        xhr.send(exportExcelCond);
    });
});

/**
 * 初始化供应商品目树
 */
var parm;
var treeSetting = {
    async: {
        autoParam: ["id"],
        enable: true,
        url: globalPath + "/category/selectAllCateByCond.do",
        dataType: "json",
        type: "post",
    },
    check: {
        enable: true,
        chkboxType: {
            "Y": "s",
            "N": "s"
        }
    },
    callback: {
        // 点击复选框按钮触发事件
        onCheck: zTreeOnCheck,
        // 点击节点触发事件
        // onClick: zTreeOnClick
        onAsyncSuccess: setDisabledNode
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId:"0"
        }
    }
};

/**
 * 搜索树
 */
function loadZtree() {
    var zNodes;
    // 加载中的菊花图标
    var loading = layer.load(1);
    // 获取搜索内容
    var searchName = $.trim($("#search").val());
    // 清除选中内容
    $("#supplierGradeInput").val("");
    var parms = $("#supplierTypeIds").val();
    treeSetting.async.otherParam= ["code", parms];
    $.ajax({
        url: globalPath + "/category/selectAllCateByCond.do",
        data: {
            "name": searchName,
            "code": parms
        },
        async: false,
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data.length <= 0) {
                layer.msg("没有符合查询条件的产品类别信息！");
            } else {
                zNodes = data;
                zTreeObj = $.fn.zTree.init($("#supplierGradeTree"), treeSetting, zNodes);
                zTreeObj.expandAll(true);//全部展开
                setDisabledNode();
            }
            // 禁用选节点
            //设置禁用的复选框节点
            //setDisabledNode();
            // 关闭加载中的菊花图标
            layer.close(loading);
        }
    });
};

/**
 * 禁用节点
 */
function setDisabledNode(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("supplierGradeTree");
    var disabledNode = treeObj.getNodesByParam("level", 0);
    // 设置多个
    $(disabledNode).each(function (index, ele) {
        treeObj.setChkDisabled(ele, true);
    })
}

/**
 * 每次点击 checkbox 或 radio 后， 弹出该节点的 tId、name 以及当前勾选状态的信息
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnCheck(event, treeId, treeNode) {
    // 如果父节点为根节点则提示不可选择根节点
    if (treeNode.parentId == "0") {
        layer.msg("不可选择根节点");
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj("supplierGradeTree"), nodes = zTree.getCheckedNodes(true), names = "", ids = "";
    for (var i = 0; i < nodes.length; i++) {
        names += nodes[i].name + ",";
        ids += nodes[i].id + ",";
    }
    if (names.length > 0) names = names.substring(0, names.length - 1);
    if (ids.length > 0) ids = ids.substring(0, ids.length - 1);
    $("#supplierGradeInput").val(names);
    $("#supplierGradeInputVal").val(ids);
    // 打开等级选择框
    $("#supplierLevelLi").css("display", "block");
};

/**
 *用于捕获节点被点击的事件回调函数
 *如果设置了 setting.callback.beforeClick 方法，且返回 false，将无法触发 onClick 事件回调函数。
 *默认值：null
 * @param event :js event 对象
 * @param treeId :对应 zTree 的 treeId，便于用户操控
 * @param treeNode :被点击的节点 JSON 数据对象
 */
function zTreeOnClick(event, treeId, treeNode){
    // 如果父节点为根节点则提示不可选择根节点
    if(treeNode.parentId == "0"){
        layer.msg("不可选择根节点");
        return;
    }
    // 获取当前节点的根节点的Id
    // var currentRootNodeId = getCurrentRoot(treeNode);
    // 获取取当前节点的ID
    var currentNodeId = treeNode.id;
    // 获取当前节点的name
    var currentNodeName = treeNode.name;
    // 将选中节点的ID设置到隐藏域中
    $("#supplierGradeInputVal").val(currentNodeId);
    // 将选中节点的name设置到所选input框中
    $("#supplierGradeInput").val(currentNodeName);
    // 关闭树
    hideSupplierGradeTreeContent();
    // 打开等级选择框
    $("#supplierLevelLi").css("display", "block");
}

/**
 * 获取选中节点的根节点
 * @returns {*}
 */
//获取当前节点的根节点(treeNode为当前节点)
function getCurrentRoot(treeNode){
    if(treeNode.getParentNode() != null){
        var parentNode = treeNode.getParentNode();
        return getCurrentRoot(parentNode);
    }else{
        return treeNode.id;
    }
}

function initZtree(parm) {
    var parms = $("#supplierTypeIds").val();
    if(parms == ''){
        layer.msg("请先选择供应商类型");
        return;
    }
    treeSetting.async.otherParam= ["code", parms];
    // 初始化树
    zTreeObj = $.fn.zTree.init($("#supplierGradeTree"), treeSetting);
    if(parm){
        // 加载下拉框菜单
        var cityObj = $("#supplierGradeInput");
        var cityOffset = $("#supplierGradeInput").offset();
        $("#supplierGradeTreeContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownSupplierGradeContent);
    }
}

/**
 * 鼠标点击事件
 * @param event
 */
function onBodyDownSupplierGradeContent(event) {
    if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierGradeTreeContent").length > 0)) {
        hideSupplierGradeTreeContent();
    }
}

/**
 * 鼠标离开事件
 * @param event
 */
function hideSupplierGradeTreeContent() {
    $("#supplierGradeTreeContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownSupplierGradeContent);
    $("#search").val("");
}