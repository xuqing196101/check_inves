var zTreeObj;
var zNodes;
$(function() {
	$("#page_ul_id").find("li").click(function() {
		var id = $(this).attr("id");
		var page = "tab-" + id.charAt(id.length - 1);
		$("input[name='defaultPage']").val(page);
	});
	var defaultPage = $("#defaultPage").val();
	if (defaultPage) {
		var num = defaultPage.charAt(defaultPage.length - 1);
		$("#page_ul_id").find("li").each(function(index) {
			var liId = $(this).attr("id");
			var liNum = liId.charAt(liId.length - 1);
			if (liNum == num) {
				$(this).attr("class", "active");
			} else {
				$(this).removeAttr("class");
			}
		});
		$("#tab_content_div_id").find(".tab-pane").each(function() {
			var id = $(this).attr("id");
			if (id == defaultPage) {
				$(this).attr("class", "tab-pane fade height-300 active in");
			} else {
				$(this).attr("class", "tab-pane fade height-300");
			}
		});
	} else {
		$("#page_ul_id").find("li").each(function(index) {
			if (index == 0) {
				var id = $(this).attr("id");
				defaultLoadTab(id);
				$(this).attr("class", "active");
			} else {
				$(this).removeAttr("class");
			}
		});
		$("#tab_content_div_id").find(".tab-pane").each(function(index) {
			if (index == 0) {
				$(this).attr("class", "tab-pane fade height-300 active in");
			} else {
				$(this).attr("class", "tab-pane fade height-300");
			}
		});
	}

	if ($("#supplierSt").val() == 7) {
		showReason();
	}

	var proError = $("#productError").val();
	var sellError = $("#sellError").val();
	var projectError = $("#projectError").val();
	var severError = $("#serverError").val();

	if (proError == "productError") {
		layer.alert("请选择生产型品目！");
	}
	if (sellError == "sellError") {
		layer.alert("请选择销售型品目！");
	}
	if (projectError == "projectError") {
		layer.alert("请选择工程型品目！");
	}
	if (severError == "serverError") {
		layer.alert("请选择服务型品目！");
	}
});
var loading;
// 加载默认的页签
function defaultLoadTab(id) {
	if (id == "li_id_1") {
		loadTab('PRODUCT', 'tree_ul_id_1', 1);
	}
	if (id == "li_id_2") {
		loadTab('SALES', 'tree_ul_id_2', 2);
	}
	if (id == "li_id_3") {
		loadTab('PROJECT', 'tree_ul_id_3', null);
	}
	if (id == "li_id_4") {
		loadTab('SERVICE', 'tree_ul_id_4', null);
	}
}

// 加载对应的节点数据
function loadZtree(code, kind, status) {
	// 加载中的菊花图标
	loading = layer.load(1);
	if (code != 'PROJECT') {
		var setting = {
			async : {
				autoParam : [ "id", "code" ],
				enable : true,
				url : globalPath + "/supplier_item/category_type.do",
				otherParam : {
					"code" : code,
					"supplierId" : $("#supplierId").val(),
					"status" : status
				},
				dataType : "json",
				type : "post",
			},
			check : {
				enable : true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				},
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId",
				}
			},
			callback : {
				onCheck : saveCategory,
				onAsyncSuccess : zTreeOnAsyncSuccess,
				onExpand : zTreeOnExpand,
				beforeCheck : zTreeBeforeCheck
			},
			view : {
				showLine : true
			}
		};
		$.fn.zTree.init($("#" + kind), setting, zNodes);
	} else {
		$.ajax({
			url : globalPath + '/supplier_item/loadCategory.do',
			type : 'POST', // GET
			data : {
				'code' : code,
				supplierId : $("#supplierId").val(),
				status : status
			},
			dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
			success : function(data) {
				var _obj = eval(data);
				var setting = {
					check : {
						enable : true,
						chkStyle : "checkbox",
						chkboxType : {
							"Y" : "ps",
							"N" : "ps"
						},
					},
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "parentId",
						}
					},
					callback : {
						onCheck : saveCategory,
						onAsyncSuccess : zTreeOnAsyncSuccess,
						onExpand : zTreeOnExpand,
						beforeCheck : zTreeBeforeCheck
					},
					view : {
						showLine : true
					}
				};
				$.fn.zTree.init($("#" + kind), setting, _obj);
				zTreeOnAsyncSuccess(null, kind, null, null);
			}
		});
	}
}

function zTreeBeforeCheck(treeId, treeNode) {
	// 加载中的菊花图标
	loading = layer.load(1);
	// 对工程下工程勘察和工程设计进行特殊处理
	if (treeId == 'tree_ul_id_3') {
		if (treeNode.code.indexOf('B02') == 0
				|| treeNode.code.indexOf('B03') == 0) {
			// return true;
			return checkNode(treeNode);
		} else {
			if (treeNode.isParent == true) {
				layer.msg("请在末节点上进行操作！");
				layer.close(loading);
				return false;
			} else {
				// return true;
				return checkNode(treeNode);
			}
		}
	} else {
		if (treeNode.isParent == true) {
			layer.msg("请在末节点上进行操作！");
			layer.close(loading);
			return false;
		} else {
			// return true;
			return checkNode(treeNode);
		}
	}
}

// var enableNodeList = [];// 存放可以编辑的节点
function checkNode(treeNode) {
	// 已经通过审核的节点不能修改
	var currSupplierSt = $("#supplierSt").val();
	if (currSupplierSt == '2') {// 退回修改的状态
		var errorField = $("#errorField").val();
		/*
		 * if(errorField.indexOf(treeNode.id) >= 0){ return true; }
		 */
		if (!treeNode.checked) {
			layer.msg("退回修改不能新增产品类别！");
		} else {
			layer.msg("此节点已通过审核，不能修改！");
		}
		layer.close(loading);
		return false;
	}
	return true;
}

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if (treeNode == null) {
		// 加载已选产品类别列表
		var code;
		if (treeId == 'tree_ul_id_1') {
			code = "PRODUCT";
		}
		if (treeId == 'tree_ul_id_2') {
			code = "SALES";
		}
		if (treeId == 'tree_ul_id_3') {
			code = "PROJECT";
		}
		if (treeId == 'tree_ul_id_4') {
			code = "SERVICE";
		}
		var supplierId = $("#supplierId").val();
		var path = globalPath + "/supplier_item/getCategories.html?supplierId="
				+ supplierId + "&supplierTypeRelateId=" + code;
		$("#tbody_category").load(path);
		// 关闭加载中的菊花图标
		layer.close(loading);
	}
};

// 加载tab页签
function loadTab(code, kind, status) {
	$("#cate-" + kind.charAt(kind.length - 1)).val("");
	loadZtree(code, kind, status);
}

function saveItems() {
	layer.msg('暂存成功');
}

function next(flag) {

	// 验证审核未通过的节点
	var currSupplierSt = $("#supplierSt").val();
	if (currSupplierSt == '2') {// 退回修改的状态
		var bool = true;
		var notPassMsg = "";// 未通过信息
		$("#tbody_category tr").each(function(index) {
			var checkedId = $(this).find("td:last").attr("data-catId");
			var errorField = $("#errorField").val();
			if (errorField.indexOf(checkedId) >= 0) {
				var td1Text = $(this).find("td:eq(1)").text();
				var td2Text = $(this).find("td:eq(2)").text();
				var td3Text = $(this).find("td:eq(3)").text();
				var td4Text = $(this).find("td:eq(4)").text();
				var td5Text = $(this).find("td:eq(5)").text();
				td2Text = td2Text == "" ? "" : " > " + td2Text;
				td3Text = td3Text == "" ? "" : " > " + td3Text;
				td4Text = td4Text == "" ? "" : " > " + td4Text;
				td5Text = td5Text == "" ? "" : " > " + td5Text;
				var msg = td1Text + td2Text + td3Text + td4Text + td5Text;
				notPassMsg += msg + "<br>";
				// bool = false;
				return true;
			}
		});
		if (notPassMsg != "") {
			bool = false;
			layer.alert("以下节点：<br>" + notPassMsg + "审核未通过，需要修改！");
			return;
			/*
			 * layer.confirm("以下节点：<br>"+notPassMsg+"审核未通过，建议修改！", { offset:
			 * '200px', scrollbar: false, btn: ['下一步','继续修改'] //按钮 },
			 * function(index) { bool = true; layer.close(index);
			 * $("#flag").val(flag); $("#items_info_form_id").submit(); },
			 * function(index){ bool = false; layer.close(index); });
			 */
		}
		if (!bool) {
			return;
		}
	}

	var flag = supCategory();
	if (flag == false) {
		layer.alert("请选择一个节点", {
			offset : [ '150px', '500px' ],
			shade : 0.01
		});
	} else {
		$("#flag").val(flag);
		$("#items_info_form_id").submit();
	}

}

function prev() {
	updateStep(2);
}

function getCategoryId() {
	var ids = [];
	for ( var i = 1; i < 5; i++) {
		var id = "tree_ul_id_" + i;
		var tree = $.fn.zTree.getZTreeObj(id);
		if (tree != null) {
			nodes = tree.getCheckedNodes(true);
			for ( var j = 0; j < nodes.length; j++) {
				ids.push(nodes[j].id);
			}
		}
	}
	$("#categoryId").val(ids);
	return ids;
}
function loadChildrenStr(treeNode) {
	var _str = "";
	if (!treeNode.isParent) {// 末节点
		return _str;
	}
	if (treeNode.children && treeNode.children.length > 0) {
		for ( var i = 0; i < treeNode.children.length; i++) {
			var endStr = loadChildrenStr(treeNode.children[i]);
			_str += treeNode.children[i].id + "," + endStr;
		}
	}
	return _str;
}

function saveCategory(event, treeId, treeNode) {
	// 对工程下工程勘察和工程设计进行特殊处理
	if (treeId == 'tree_ul_id_3') {
		if (treeNode.code.indexOf('B02') == 0
				|| treeNode.code.indexOf('B03') == 0) {
			var categoryIds = "";
			// 工程勘察不展开且勾选时,加载子节点
			if (treeNode.children && treeNode.children.length > 0) {
				categoryIds = loadChildrenStr(treeNode);
			} else {
				categoryIds = treeNode.id;
			}
			if (categoryIds.indexOf(",") != -1) {
				categoryIds = categoryIds.substring(0, categoryIds.length - 1);
			}
			$("#categoryId").val(categoryIds);
		} else {
			$("#categoryId").val(treeNode.id);
		}
	} else {
		$("#categoryId").val(treeNode.id);
	}
	var clickFlag;
	if (treeNode.checked) {
		clickFlag = "1";
	} else {
		clickFlag = "0";
	}
	$("#clickFlag").val(clickFlag);

	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getSelectedNodes();

	var attr1 = $("#li_id_1").attr("class");
	if (attr1 == 'active') {
		$("#supplierTypeRelateId").val("PRODUCT");
	}
	var attr2 = $("#li_id_2").attr("class");
	if (attr2 == 'active') {
		$("#supplierTypeRelateId").val("SALES");
	}
	var attr3 = $("#li_id_3").attr("class");
	if (attr3 == 'active') {
		$("#supplierTypeRelateId").val("PROJECT");
	}
	var attr4 = $("#li_id_4").attr("class");
	if (attr4 == 'active') {
		$("#supplierTypeRelateId").val("SERVICE");
	}
	$("#flag").val("4");
	var supplierId = $("#supplierId").val();
	var index_loading = layer.load(1);
	$.ajax({
		url : globalPath + "/supplier_item/saveCategory.do",
		async : false,
		data : $("#items_info_form_id").serialize(),
		beforeSend : function() {
			// 禁用按钮防止重复提交，发送前响应
			// 加载中的菊花图标
			for ( var i = 0, l = nodes.length; i < l; i++) {
				treeObj.setChkDisabled(nodes[i], true);
			}
		},
		success : function() {
			zTreeOnAsyncSuccess(null, treeId, null, null);
		},
		complete : function() {// 完成响应
			for ( var i = 0, l = nodes.length; i < l; i++) {
				treeObj.setChkDisabled(nodes[i], false);
			}
			layer.close(index_loading);
		},
	});
}

function supCategory() {
	var flag = true;
	var supplierId = $("#supplierId").val();
	$.ajax({
		url : globalPath + "/supplier_item/getSupplierCate.do",
		type : "post",
		data : {
			supplierId : supplierId,
		},
		dataType : "json",
		success : function(result) {
			if (result == "0") {
				flag = false;
			}
		}
	});
	return flag;
}

function showReason() {
	var supplierId = $("#supplierId").val();
	var left = document.body.clientWidth - 500;
	var top = window.screen.availHeight / 2 - 150;
	layer.open({
		type : 2,
		title : '审核反馈',
		closeBtn : 0, // 不显示关闭按钮
		skin : 'layui-layer-lan', // 加上边框
		area : [ '500px', '300px' ], // 宽高
		offset : [ top, left ],
		shade : 0,
		maxmin : true,
		shift : 2,
		content : globalPath + '/supplierAudit/showReasonsList.html?&auditType=item_pro_page,item_sell_page,item_eng_page,item_serve_page'
				+ '&jsp=dialog_item_reason'
				+ '&supplierId='
				+ supplierId, // url
	});
}

// 树节点展开的回调事件
function zTreeOnExpand(event, treeId, treeNode) {
	$("a[title='" + treeNode.name + "']").next("ul").removeAttr("style");
}

function searchCate(cateId, treeId, type, seq, code) {
	var zNodes;
	var zTreeObj;
	var setting = {
		async : {
			autoParam : [ "id", "code" ],
			enable : true,
			url : globalPath + "/supplier_item/category_type.do",
			otherParam : {
				"code" : code,
				"supplierId" : $("#supplierId").val(),
				"status" : seq
			},
			dataType : "json",
			type : "post",
		},
		check : {
			enable : true,
			chkStyle : "checkbox",
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			},// 勾选checkbox对于父子节点的关联关系
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
			}
		},
		callback : {
			onCheck : saveCategory,
			onAsyncSuccess : zTreeOnAsyncSuccess,
			onExpand : zTreeOnExpand,
			beforeCheck : zTreeBeforeCheck
		},
		view : {
			showLine : true
		}
	};
	// 加载中的菊花图标
	loading = layer.load(1);
	var cateName = $("#" + cateId).val();
	var codeName = $("#" + code).val();
	if (cateName == "" && codeName == "") {
		loadTab(type, treeId, seq);
	} else {
		var supplierId = $("#supplierId").val();
		var id = type;
		$.ajax({
			url : globalPath + "/supplier_item/searchCate.do",
			type : "post",
			data : {
				"typeId" : id,
				"cateName" : cateName,
				"supplierId" : supplierId,
				"codeName" : codeName
			},
			async : false,
			dataType : "json",
			success : function(data) {
				if (data.length == 1) {
					layer.msg("没有符合查询条件的产品类别信息！");
				} else {
					zNodes = data;
					zTreeObj = $.fn.zTree
							.init($("#" + treeId), setting, zNodes);
					zTreeObj.expandAll(true);// 全部展开
					// 如果搜索到的最后一个节点是父节点，折叠最后一个节点
					var allNodes = zTreeObj.transformToArray(zTreeObj
							.getNodes());
					if (allNodes && allNodes.length > 0) {
						// 最后一个节点
						var lastNode = allNodes[allNodes.length - 1];
						if (lastNode.isParent) {
							zTreeObj.expandNode(lastNode, false);// 折叠最后一个节点
						}
					}
				}
				// 关闭加载中的菊花图标
				layer.close(loading);
			}
		});
	}
}

sessionStorage.locationC = true;
sessionStorage.index = 3;