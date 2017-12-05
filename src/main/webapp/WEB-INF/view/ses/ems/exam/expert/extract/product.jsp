<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
	var key;
	var zTreeObj;
	var temp = new Array();
	$(function() {
		var zNodes;
		loadZtree();
		//加载目录树
		function loadZtree() {
			var setting = {
				async : {
					autoParam : [ "id" ],
					enable : true,
					url : "${pageContext.request.contextPath}/extractExpert/getTree.do?code=${type}&&ids=${ids}",
					otherParam : {
						categoryIds : "${categoryIds}",
					},
					dataFilter : ajaxDataFilter,
					dataType : "json",
					type : "post",
				},
				check : {
					enable : true,
					chkboxType : {
						"Y" : "ps",
						"N" : "ps"
					}
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "parentId"
					}
				},
				view : {
					fontCss : getFontCss
				},
				callback : {
					onCheck : onCheck,
					beforeCheck: zTreeBeforeCheck,
				}
			};
			zTreeObj = $.fn.zTree.init($("#ztree"), setting, zNodes);
			key = $("#key");
			key.bind("focus", focusKey).bind("blur", blurKey).bind("propertychange", searchNode).bind("input", searchNode);
		}
	});

	function onCheck(e, treeId, treeNode) {
		var index = parent.layer.load(2);
		var treeObj = $.fn.zTree.getZTreeObj("ztree");
		parent.layer.close(index);
	};
	
	function zTreeBeforeCheck(treeId, treeNode){
		if (treeNode.level != 3 && treeNode.isParent == true) {
	          layer.msg("请在末节点上进行操作！");
	          return false;
	    }else{
	    	return true;
	    }
	}
	
	//过滤父节点选中子节点 默认选中
	function ajaxDataFilter(treeId, parentNode, responseData) {
		if (responseData[0].name != "物资" && responseData[0].name != "工程"
				&& responseData[0].name != "服务") {
			if (parentNode.checked == true) {
				for (var i = 0; i < responseData.length; i++) {
					responseData[i].checked = true;
				}
			}
		}
		// 判断是否为空
		if(responseData) {
			// 判断如果父节点是第三级,则将查询出来的子节点全部改为isParent = false
			if(parentNode != null && parentNode != "undefined" && parentNode.level == 2) {
				for(var i = 0; i < responseData.length; i++) {
					responseData[i].isParent += false;
				}
			}
		}
		return responseData;
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
	
	var lastValue = "", nodeList = [], fontCss = {};

	function clickRadio(e) {
		lastValue = "";
		searchNode(e);
	}

	function searchNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		var value = $.trim(key.get(0).value);
		var keyType = "name";
		if (key.hasClass("empty")) {
			value = "";
		}
		if (lastValue === value)
			return;
		lastValue = value;
		if (value === "")
			return;
		updateNodes(false);
		nodeList = zTree.getNodesByParamFuzzy(keyType, value);
		updateNodes(true);
	}

	function updateNodes(highlight) {
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		for (var i = 0, l = nodeList.length; i < l; i++) {
			nodeList[i].highlight = highlight;
			zTree.updateNode(nodeList[i]);
		}
	}

	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {
			color : "#A60000",
			"font-weight" : "bold"
		} : {
			color : "#333",
			"font-weight" : "normal"
		};
	}

	function filter(node) {
		return !node.isParent && node.isFirstNode;
	}

	var treeid = null;

	//获取选中子节点id
	function getChildren(cate) {
		var Obj = $.fn.zTree.getZTreeObj("ztree");
		var nodes = Obj.getCheckedNodes(true);
		var ids = new Array();
		var names = new Array();
		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].level == 3 || nodes[i].isParent == false) {
				//判断当前节点不存在存在于temp集合 就添加到cate集合中
				/* if (!contains(temp, nodes[i].id)) { */
					ids.push(nodes[i].id);
					names.push(nodes[i].name);
					//若是父节点查询当前的节点的所有子节点
					/* temp.push(nodes[i].id);
					if (nodes[i].isParent) {
						//递归其全部子节点
						selectAllChildNode(nodes[i]);
					}
				} */
			}
		}
		//是否满足
		var issatisfy = $('input[name="radio"]:checked ').val();
		if (cate != null) {
			$(cate).val(names.toString());/* 将选中目录名称显示在输入框中 */
			$(cate).parents("li").find(".categoryId").val(ids.toString());
			$(cate).parents("li").find(".isSatisfy").val(issatisfy);
		}
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}

	//递归子节点,存储进临时数组
	function selectAllChildNode(node) {
		var childNode = node.children;
		if (childNode && childNode.length > 0) {
			for (var i = 0; i < childNode.length; i++) {
				if (childNode[i].checked) {
					temp.push(childNode[i].id);
					if (childNode[i].isParent) {
						selectAllChildNode(childNode[i]);
					}
				}
			}
		}
	}
	
	//判断数组中是否包含此元素
	function contains(arr, val) {
		for (i in arr) {
			if (arr[i] == val)
				return true;
		}
		return false;
	}
	
	function exptype() {
		$("#ztree").css("display", "none");
		$("#liradio").css("display", "none");
		var x = document.getElementsByName("radio");
		for (var i = 0; i < x.length; i++) { //对所有结果进行遍历，如果状态是被选中的，则将其选择取消  
			if (x[i].checked == true) {
				x[i].checked = false;
			}
		}
	}
	
	function exptype1() {
		$("#ztree").css("display", "block");
		$("#liradio").css("display", "block");
		var x = document.getElementsByName("radio"); //获取所有name=brand的元素  
		x[0].checked = true;
	}

	//品目搜索
	function searchCate() {
		var code = '${type}';
		var ids = '${ids}';
		var zTreeObj;
		var setting = {
			check : {
				enable : true,
				autoCheckTrigger : true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				}, //勾选checkbox对于父子节点的关联关系  
			},
			data : {
				simpleData : {
					enable : true,
					autoCheckTrigger : true,
					idKey : "id",
					pIdKey : "parentId"
				}
			},
			callback : {
				onCheck : onCheck,
			},
			view : {
				fontCss : getFontCss,
				showLine : true
			},
		};
		var index = layer.load(1, {
			shade : [ 0.1, '#fff' ]
		//0.1透明度的白色背景
		});
		var cateName = $("#key").val();
		var codeName = $("#codeName").val();
		if (cateName == "" && codeName == "") {
			location.reload();
		} else {
			$.ajax({
				url : "${pageContext.request.contextPath}/extractExpert/searchCate.do",
				type : "post",
				data : {
					"code" : code,
					"cateName" : cateName,
					"ids" : ids,
					"codeName" : codeName,
				},
				async : false,
				dataType : "json",
				success : function(data) {
					zTreeObj = $.fn.zTree.init($("#ztree"), setting,
							data);
					zTreeObj.expandAll(true); //全部展开
				}
			});
		}
		layer.close(index);
		// 过滤掉四级以下的节点
		setTimeout(function() {
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var nodes = treeObj.getNodes();
			for (var i = 0; i < nodes.length; i++) {
				if (nodes[i].level > 3) {
					treeObj.removeNode(nodes[i]);
				}
			}
		}, 200);
	}
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container padding-top-20   ">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="code" value="${type}" type="hidden">
					<input class="span2" name="ids" value="${ids}" type="hidden">
					<li class="col-md-6  p0 " id="liradio">
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" 
								<c:if test="${isSatisfy == null || isSatisfy !='2'}">checked="checked" </c:if>
								
								value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="2"
								<c:if test="${isSatisfy!=null && isSatisfy =='2'}">checked="checked"</c:if>
								 class="fl" />
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
				
			</div>
			<div >
				产品类别：<input type="text" id="key" class="mr3 empty w125" name="cateName">
				目录编码：<input type="text" id="codeName" class="mr3 empty w125" name="codeName">
				<div class="tc">
					<input type="button" id="search" class="btn" value="搜索" onclick="searchCate()">
				</div>
				<div class="clear"></div>
			</div>
			<div id="ztree" class="ztree margin-left-13" ></div>
	</div>
</body>
</html>
