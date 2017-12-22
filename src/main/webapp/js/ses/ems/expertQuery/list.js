/*
 * 品目
 */
var key;
function showCategory(parm) {
  var zTreeObj;
  var zNodes;
  var setting = {
    async: {
      autoParam: ["id"],
      enable: true,
      url: globalPath + "/expertQuery/createtree.do",
      otherParam: {
        categoryIds: "${categoryIds}"
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

  if(parm) {
    // 加载下拉搜索框
    $("#gradeTreeContent").css({
      left: cityOffset.left + "px",
      top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    $("body").bind("mousedown", onBodyDownOrg);
  }
}

//搜索
function searchCategory() {
  var param = $("#search").val();
  if(param != null && param != "") {
    var zNodes;
    var zTreeObj;
    var setting = {
      async: {
        autoParam: ["id"],
        enable: true,
        url: globalPath + "/expertQuery/createtree.do",
      },
      check: {
        enable: true,
        chkboxType: {
          "Y": "s",
          "N": "s"
        }
      },
      data: {
        simpleData: {
          enable: true,
          idKey: "id",
          pIdKey: "parentId",
        }
      },
      callback: {
        onCheck: onCheckCategory
      },
      view: {
        showLine: true
      }
    };
    // 加载中的菊花图标
    var loading = layer.load(1);
    $.ajax({
      url: globalPath + "/expertQuery/createtree.do",
      data: {
        "param": param
      },
      async: false,
      dataType: "json",
      type: "post",
      success: function(data) {
        if(data.length == 0) {
          layer.msg("没有符合查询条件的产品类别信息！");
        } else {
          zNodes = data;
          zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
          zTreeObj.expandAll(true); //全部展开
        }
        // 关闭加载中的菊花图标
        layer.close(loading);
      }
    });
  } else {
    showCategory();
  }
}

function focusKey(e) {
  if(key.hasClass("empty")) {
    key.removeClass("empty");
  }
}

function blurKey(e) {
  if(key.get(0).value === "") {
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
  if(key.hasClass("empty")) {
    value = "";
  }
  if(lastValue === value) return;
  lastValue = value;
  if(value === "") return;
  updateNodes(false);
  nodeList = zTree.getNodesByParamFuzzy(keyType, value);
  updateNodes(true);
}

function updateNodes(highlight) {
  var zTree = $.fn.zTree.getZTreeObj("treeRole");
  for(var i = 0, l = nodeList.length; i < l; i++) {
    nodeList[i].highlight = highlight;
    zTree.updateNode(nodeList[i]);
  }
}

function getFontCss(treeId, treeNode) {
  return(!!treeNode.highlight) ? {
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
  for(var i = 0, l = nodes.length; i < l; i++) {
    v += nodes[i].name + ",";
    rid += nodes[i].id + ",";
  }
  if(v.length > 0) v = v.substring(0, v.length - 1);
  if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
  var cityObj = $("#category");
  cityObj.attr("value", v);
  $("#categoryIds").val(rid);
}

function onBodyDownOrg(event) {
  if(!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length > 0)) {
    hideRole();
  }
}

function hideRole() {
  $("#roleContent").fadeOut("fast");
  $("body").unbind("mousedown", onBodyDownOrg);

}