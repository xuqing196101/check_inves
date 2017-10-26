/**
 * Created by yggc-easong on 2017/10/24.
 */
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
    /*check: {
        enable: true,
        chkboxType: {
            "Y": "s",
            "N": "s"
        }
    },*/
    callback: {
        // 点击复选框按钮触发事件
        //onCheck: zTreeOnCheck
        // 点击节点触发事件
        onClick: zTreeOnClick
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

$(function () {
    // 初始化树
    // initZtree();
})

/**
 * 搜索树
 */
function loadZtree() {
    var zNodes;
    // 加载中的菊花图标
    var loading = layer.load(1);
    // 获取搜索内容
    var searchName = $("#search").val();
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
function setDisabledNode(){
    var treeObj = $.fn.zTree.getZTreeObj("supplierGradeTree");
    var disabledNode = treeObj.getNodeByParam("level", 0);
    treeObj.setChkDisabled(disabledNode, true);
}

/**
 * 每次点击 checkbox 或 radio 后， 弹出该节点的 tId、name 以及当前勾选状态的信息
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnCheck(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("supplierGradeTree"), nodes = zTree.getCheckedNodes(true), names="", ids="";
    console.log(nodes);
    for(var i = 0; i < nodes.length; i++) {
        alert(nodes[i].name);
        names += nodes[i].name + ",";
        ids += nodes[i].id + ",";
    }
    if(names.length > 0) names = names.substring(0, names.length - 1);
    if(ids.length > 0) ids = ids.substring(0, ids.length - 1);
    alert(names);
    $("#supplierGradeInput").val(names);
    //$("#supplierTypeIds").val(rid);
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
    var currentRootNodeId = getCurrentRoot(treeNode);
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