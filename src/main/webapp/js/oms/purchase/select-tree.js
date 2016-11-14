/*
 tianKunFeng
 */

var datas;

// ======加载选择栏目的树=======
var zTree;


function beforeClick(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	
	check = true;//不检查叶子节点  可以选择任何节点
	if (!check) {
		layer.open({
			content : "请选择叶子节点",
			icon : 2,
			shade : [ 0.01, '#000' ],
			time : 1500,
			offset : [ '222px', '360px' ],
			yes : function(index) {
				layer.closeAll();
			}
		});
	}
	// alert("只能选择子节点...");
	return check;
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), v = "";
	ids = "";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		ids += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (ids.length > 0)
		ids = ids.substring(0, ids.length - 1);
	// console.dir(ids);
	var proSecObj = $("#proSec");
	proSecObj.attr("value", v);
	$("#treeId").val(ids);
	hideMenu();
}

function showMenu() {
	var proSecObj = $("#proSec");
	var proOffset = $("#proSec").offset();
	$("#menuContent").css({
		left : proOffset.left + "px",
		top : proOffset.top + proSecObj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
			event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}
