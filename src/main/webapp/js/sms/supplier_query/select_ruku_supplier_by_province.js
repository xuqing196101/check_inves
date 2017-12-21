function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    var rid = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
        rid += nodes[i].id + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
    var cityObj = $("#supplierType");
    cityObj.attr("value", v);
    $("#supplierTypeIds").val(rid);
}

function showSupplierType() {
    var setting = {
        check: {
            enable: true,
            chkboxType: {
                "Y": "",
                "N": ""
            }
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        },
        callback: {
            beforeClick: beforeClick,
            onCheck: onCheck
        }
    };
    $.ajax({
        type: "GET",
        async: false,
        url: globalPath + "/supplierQuery/find_supplier_type.do?supplierId=''",
        dataType: "json",
        success: function (zNodes) {
            for (var i = 0; i < zNodes.length; i++) {
                if (zNodes[i].isParent) {

                } else {
                    //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标
                }
            }
            tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
            tree.expandAll(true); //全部展开
        }
    });
    var cityObj = $("#supplierType");
    var cityOffset = $("#supplierType").offset();
    $("#supplierTypeContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownSupplierType);
}

function hideSupplierType() {
    $("#supplierTypeContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownSupplierType);

}

function onBodyDownSupplierType(event) {
    if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
        hideSupplierType();
    }
}

var key;
function showCategory() {
    var zTreeObj;
    var zNodes;
    var setting = {
        async: {
            autoParam: ["id"],
            enable: true,
            url: globalPath + "/category/createtree.do",
            otherParam: {
                categoryIds: $("#categoryIds").val(),
            },
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
            beforeClick: beforeClickCategory,
            onCheck: onCheckCategory
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        },
        view: {
            fontCss: getFontCss
        }
    };
    zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
    key = $("#key");
    key.bind("focus", focusKey)
        .bind("blur", blurKey)
        .bind("propertychange", searchNode)
        .bind("input", searchNode);

    var cityObj = $("#category");
    var cityOffset = $("#category").offset();
    $("#roleContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownOrg);
}

function focusKey(e) {
    if (key.hasClass("empty")) {
        key.removeClass("empty");
    }
}

function blurKey(e) {
    if (key.get(0).value === "") {
        key.addClass("empty");
    }
}
var lastValue = "",
    nodeList = [],
    fontCss = {};

function clickRadio(e) {
    lastValue = "";
    searchNode(e);
}

function searchNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeRole");
    var value = $.trim(key.get(0).value);
    var keyType = "name";
    if (key.hasClass("empty")) {
        value = "";
    }
    if (lastValue === value) return;
    lastValue = value;
    if (value === "") return;
    updateNodes(false);
    nodeList = zTree.getNodesByParamFuzzy(keyType, value);
    updateNodes(true);
}

function updateNodes(highlight) {
    var zTree = $.fn.zTree.getZTreeObj("treeRole");
    for (var i = 0, l = nodeList.length; i < l; i++) {
        nodeList[i].highlight = highlight;
        zTree.updateNode(nodeList[i]);
    }
}

function getFontCss(treeId, treeNode) {
    return (!!treeNode.highlight) ? {
        color: "#A60000",
        "font-weight": "bold"
    } : {
        color: "#333",
        "font-weight": "normal"
    };
}

function filter(node) {
    return !node.isParent && node.isFirstNode;
}

function beforeClickCategory(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeRole");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheckCategory(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeRole"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    var rid = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
        rid += nodes[i].id + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
    var cityObj = $("#category");
    cityObj.attr("value", v);
    $("#categoryIds").val(rid);
}

function onBodyDownOrg(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length > 0)) {
        hideRole();
    }
}

function hideRole() {
    $("#roleContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownOrg);
}

function chongzhi() {
    $("#supplierName").val('');
    $("#startDate").val('');
    $("#mobile").val('');
    $("#endDate").val('');
    $("#contactName").val('');
    $("#categoryIds").val('');
    $("#supplierTypeIds").val('');
    $("#category").val('');
    $("#supplierType").val('');
    $("#isProvisional").val('');
    $("#creditCode").val('');
    $("#supplierGradeInputVal").val('');
    $("#supplierGradeInput").val('');
    $("#orgName option:selected").removeAttr("selected");
    $("#status option:selected").removeAttr("selected");
    $("#address option:selected").removeAttr("selected");
    $("#businessNature option:selected").removeAttr("selected");
    
    $("#form1 input[type='text']").val("");
    $("#form1 select").val("");
    
    $("#form1").submit();
}

function fanhui() {
    window.location.href = globalPath + "/supplierQuery/highmaps.html?judge=5";
}

