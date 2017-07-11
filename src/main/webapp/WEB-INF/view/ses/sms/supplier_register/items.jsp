<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<title>供应商注册</title>
<style type="text/css">
	.current{
		cursor:pointer;
	}
</style>
<script type="text/javascript">
	var zTreeObj;
	var zNodes;
	$(function() {
		$("#page_ul_id").find("li").click(function() {
			var id = $(this).attr("id");
			var page = "tab-" + id.charAt(id.length - 1);
			$("input[name='defaultPage']").val(page);
		});
		var defaultPage = "${defaultPage}";
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
		
		if ("${currSupplier.status}" == 7) {
			showReason();
		}
		
		var proError = "${productError}";
		var sellError = "${sellError}";
		var projectError = "${projectError}";
		var severError = "${serverError}";
		
		if (proError == "productError") {
			layer.alert("请选择生产行品目！");
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
	//加载默认的页签
	function defaultLoadTab(id){
		if (id == "li_id_1"){
			loadTab('PRODUCT','tree_ul_id_1',1);
		}
		if (id == "li_id_2"){
			loadTab('SALES','tree_ul_id_2',2);
		}
		if (id == "li_id_3"){
			loadTab('PROJECT','tree_ul_id_3',null);
		}
		if (id == "li_id_4"){
			loadTab('SERVICE','tree_ul_id_4',null);
		}
	}
	
	//加载对应的节点数据
	function loadZtree(code, kind, status) {
        // 加载中的菊花图标
        loading = layer.load(1);
        if(code != 'PROJECT'){
            var setting = {
                async : {
                     autoParam: ["id","code"],
                     enable : true,
                     url : "${pageContext.request.contextPath}/supplier/category_type.do",
                     otherParam : {
                     "code":code,
                     "supplierId": "${currSupplier.id}",
                     "status" : status
                     },
                     dataType : "json",
                     type : "post",
                 },
                check : {
                    enable : true,
                    chkStyle:"checkbox",
                    chkboxType:{"Y" : "ps", "N" : "ps"},
                },
                data : {
                    simpleData : {
                        enable : true,
                        idKey: "id",
                        pIdKey: "parentId",
                    }
                },
                callback: {
                    onCheck: saveCategory,
                    onAsyncSuccess: zTreeOnAsyncSuccess,
                    onExpand: zTreeOnExpand,
                    beforeCheck: zTreeBeforeCheck
                },
                view: {
                    showLine: true
                }
            };
            $.fn.zTree.init($("#" + kind), setting, zNodes);
        }else{
            $.ajax({
                url:'${pageContext.request.contextPath}/supplier/loadCategory.do',
                type:'POST', //GET
                data:{
                    'code':code,supplierId:"${currSupplier.id}",status:status
                },
                dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                success:function(data){
                    var _obj = eval(data);
                    var setting = {
                        check : {
                            enable : true,
                            chkStyle:"checkbox",
                            chkboxType:{"Y" : "ps", "N" : "ps"},
                        },
                        data : {
                            simpleData : {
                                enable : true,
                                idKey: "id",
                                pIdKey: "parentId",
                            }
                        },
                        callback: {
                            onCheck: saveCategory,
                            onAsyncSuccess: zTreeOnAsyncSuccess,
                            onExpand: zTreeOnExpand,
                            beforeCheck: zTreeBeforeCheck
                        },
                        view: {
                            showLine: true
                        }
                    };
                    $.fn.zTree.init($("#" + kind), setting, _obj);
                    zTreeOnAsyncSuccess(null, kind, null, null);
                }
            })
        }
	}
	
	function zTreeBeforeCheck(treeId, treeNode) {
		// 加载中的菊花图标
		loading = layer.load(1);
		//对工程下工程勘察和工程设计进行特殊处理
		if (treeId == 'tree_ul_id_3') {
			if (treeNode.code.indexOf('B02') == 0
					|| treeNode.code.indexOf('B03') == 0) {
				//return true;
				return checkNode(treeNode);
			} else {
				if (treeNode.isParent == true) {
					layer.msg("请在末节点上进行操作！");
					layer.close(loading);
					return false;
				} else {
					//return true;
					return checkNode(treeNode);
				}
			}
		} else {
			if (treeNode.isParent == true) {
				layer.msg("请在末节点上进行操作！");
				layer.close(loading);
				return false;
			} else {
				//return true;
				/return checkNode(treeNode);
			}
		}
	}
	
	var enableNodeList = [];// 存放可以编辑的节点
	function checkNode(treeNode){
		// 已经通过审核的节点不能修改
    var currSupplierSt = '${currSupplier.status}';
		if(currSupplierSt == '2'){// 退回修改的状态
			/* if($.inArray(treeNode.id, enableNodeList) >= 0){
				return true;
			}
			var bool = true;
			$("#tbody_category tr").each(function(index){
     		var checkedId = $(this).find("td:last").attr("data-catId");
     		var errorField = $("#errorField").val();
     		if(checkedId == treeNode.id && errorField.indexOf(treeNode.id) < 0){
       		layer.msg("此节点已通过审核，不能修改!");
     			bool = false;
     			return false;
     		}
     	});
     	if(!bool){
     		layer.close(loading);
     		return false;
     	}
     	enableNodeList.push(treeNode.id);
     	return true; */
     	var errorField = $("#errorField").val();
     	if(errorField.indexOf(treeNode.id) >= 0){
     		return true;
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
			var supplierId = "${currSupplier.id}";
			var path = "${pageContext.request.contextPath}/supplier_item/getCategories.html?supplierId="
					+ supplierId + "&supplierTypeRelateId=" + code;
			$("#tbody_category").load(path);
			// 关闭加载中的菊花图标
			layer.close(loading);
		}
	};

	//加载tab页签
	function loadTab(code, kind, status) {
		$("#cate-" + kind.charAt(kind.length - 1)).val("");
		loadZtree(code, kind, status);
	}

	function saveItems() {
		/*  getCategoryId();
		$("#flag").val("");
		$("#items_info_form_id").submit(); */
		//	function temporarySave(){
		$("input[name='flag']").val("file");
		$
				.ajax({
					url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
					type : "post",
					data : $("#items_info_form_id").serializeArray(),
					contextType : "application/x-www-form-urlencoded",
					success : function(msg) {

						if (msg == 'ok') {
							layer.msg('暂存成功');
						}
						if (msg == 'failed') {
							layer.msg('暂存失败');
						}
					}
				});
		//}

	}

	function next(flag) {
	
		// 验证审核未通过的节点
    var currSupplierSt = '${currSupplier.status}';
		if(currSupplierSt == '2'){// 退回修改的状态
			var bool = true;
			var notPassMsg = "";// 未通过信息
			$("#tbody_category tr").each(function(index){
      		var checkedId = $(this).find("td:last").attr("data-catId");
      		var errorField = $("#errorField").val();
      		if(errorField.indexOf(checkedId) >= 0){
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
      			//bool = false;
      			return true;
      		}
      	});
      	if(notPassMsg != ""){
      		bool = false;
					/* layer.alert("以下节点：<br>"+notPassMsg+"审核未通过，需要修改！");
					return; */
					layer.confirm("以下节点：<br>"+notPassMsg+"审核未通过，建议修改！", {
						offset: '200px',
						scrollbar: false,
						btn: ['下一步','继续修改'] //按钮
					}, function(index) {
						bool = true;
						layer.close(index);
						$("#flag").val(flag);
						$("#items_info_form_id").submit();
					}, function(index){
						bool = false;
						layer.close(index);
					});
				}
				if(!bool){
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

	function prev(flag) {
		getCategoryId();
		$("#flag").val(flag);
		$("#items_info_form_id").submit();
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
		if (!treeNode.isParent) {//末节点
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
		//对工程下工程勘察和工程设计进行特殊处理
		if (treeId == 'tree_ul_id_3') {
			if (treeNode.code.indexOf('B02') == 0
					|| treeNode.code.indexOf('B03') == 0) {
				var categoryIds = "";
				//工程勘察不展开且勾选时,加载子节点
				if (treeNode.children && treeNode.children.length > 0) {
					categoryIds = loadChildrenStr(treeNode);
				} else {
					categoryIds = treeNode.id;
				}
				if (categoryIds.indexOf(",") != -1) {
					categoryIds = categoryIds.substring(0,
							categoryIds.length - 1);
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
		var supplierId = "${currSupplier.id}";
		var type = $("#supplierTypeRelateId").val();
		var index_loading = layer.load(1);
		$
				.ajax({
					url : "${pageContext.request.contextPath}/supplier_item/saveCategory.do",
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
					complete : function() {//完成响应
						for ( var i = 0, l = nodes.length; i < l; i++) {
							treeObj.setChkDisabled(nodes[i], false);
						}
						layer.close(index_loading);
					},
				});
	}

	function supCategory() {
		var flag = true;
		var supplierId = "${currSupplier.id}";
		$
				.ajax({
					url : "${pageContext.request.contextPath}/supplier_item/getSupplierCate.do",
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
</script>
<script type="text/javascript">
	function showReason() {
		var supplierId = "${currSupplier.id}";
		var left = document.body.clientWidth - 500;
		var top = window.screen.availHeight / 2 - 150;
		layer.open({
			type : 2,
			title : '审核反馈',
			closeBtn : 0, //不显示关闭按钮
			skin : 'layui-layer-lan', //加上边框
			area : [ '500px', '300px' ], //宽高
			offset : [top, left],
			shade : 0,
			maxmin : true,
			shift : 2,
			content : '${pageContext.request.contextPath}/supplierAudit/showReasonsList.html?&auditType=item_pro_page,item_sell_page,item_eng_page,item_serve_page' + '&jsp=dialog_item_reason' + '&supplierId=' + supplierId, //url
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
        autoParam: ["id","code"],
        enable : true,
        url : "${pageContext.request.contextPath}/supplier/category_type.do",
        otherParam : {
        "code" : code,
        "supplierId" : "${currSupplier.id}",
        "status" : seq
        },
        dataType : "json",
        type : "post",
      },
			check : {
				enable : true,
				chkStyle:"checkbox",  
				chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
			},
			data : {
				simpleData : {
					enable : true,
					idKey: "id",
					pIdKey: "parentId",
				}
			},
			callback: {
				onCheck: saveCategory,
				onAsyncSuccess: zTreeOnAsyncSuccess,
				onExpand: zTreeOnExpand,
				beforeCheck: zTreeBeforeCheck
			},view: {
				showLine: true
			}
		};
		// 加载中的菊花图标
		loading = layer.load(1);
		var cateName = $("#" + cateId).val();
		var codeName = $("#" + code).val();
		if (cateName == "" && codeName == "") {
			loadTab(type,treeId,seq);
		} else {
			var supplierId= $("#supplierId").val();
			var id = type;
			$.ajax({
				url: "${pageContext.request.contextPath}/expert/searchCate.do",
				type:"post",
				data: {"typeId" : id, "cateName" : cateName, "supplierId" : supplierId, "codeName": codeName},
				async: false,
				dataType: "json",
				success: function(data){
					if (data.length == 1) {
						layer.msg("没有符合查询条件的产品类别信息！");
					} else {
						zNodes = data;
						zTreeObj = $.fn.zTree.init($("#" + treeId), setting, zNodes);
						zTreeObj.expandAll(true);//全部展开
						// 如果搜索到的最后一个节点是父节点，折叠最后一个节点
						var allNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
						if(allNodes && allNodes.length > 0){
							// 最后一个节点
							var lastNode = allNodes[allNodes.length-1];
							if(lastNode.isParent){
								zTreeObj.expandNode(lastNode, false);//折叠最后一个节点
							}
						}
					}
					// 关闭加载中的菊花图标
					layer.close(loading);
				}
			});
		}
	}
	function updateStep(step){
		var supplierId = "${currSupplier.id}";
		location.href = "${pageContext.request.contextPath}/supplier/updateStep.html?step=" + step + "&supplierId=" + supplierId;
	}
	sessionStorage.locationC=true;
	sessionStorage.index=3;
</script>
</head>

<body>
	<div class="wrapper">
		<div class="container clear margin-top-30">
			<h2 class="step_flow">
				<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
	            <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
	            <span id="ty3" class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
	            <span id="sp4" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
	            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
	            <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
	            <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
	            <span id="sp8" class="new_step fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
	            <div class="clear"></div>
			</h2>
		</div>

		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<li id="li_id_1" onclick="loadTab('PRODUCT','tree_ul_id_1',1);" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">物资-生产型产品类别信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<li id="li_id_2" onclick="loadTab('SALES','tree_ul_id_2',2);" ><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">物资-销售型产品类别信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3" onclick="loadTab('PROJECT','tree_ul_id_3',null);" ><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">工程产品类别信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4" onclick="loadTab('SERVICE','tree_ul_id_4',null);" ><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">服务产品类别信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<h2 class="f16 ">
											<font color="red">*</font> 勾选物资生产型产品类别信息
									</h2>
									<div id="div-1" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
										<div class="fl mr5">
								  			产品类别：<input type="text" id="cate-1">
								  		</div>
								  		<div class="fl mr5">
								                                      目录编码：<input type="text" id="code-1">
								        </div> 
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-1','tree_ul_id_1','PRODUCT',1,'code-1')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-sm-12 col-xs-12 p0">
										<ul id="tree_ul_id_1" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<h2 class="f16 ">
											<font color="red">*</font> 勾选物资销售型产品类别信息
									</h2>
									<div id="div-2" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
										<div class="fl mr5">
								  	                     产品类别：<input type="text" id="cate-2">
								  	    </div>
								  	    <div class="fl mr5">
								                                 目录编码：<input type="text" id="code-2">
								        </div>
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-2','tree_ul_id_2','SALES',2,'code-2')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-sm-12 col-xs-12 p0">
										<ul id="tree_ul_id_2" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<h2 class="f16  ">
									      	<font color="red">*</font> 勾选工程产品类别信息
									</h2>
									<div id="div-3" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
									    <div class="fl mr5">
								  			产品类别：<input type="text" id="cate-3">
								  		</div>
								  		<div class="fl mr5">
								          	目录编码：<input type="text" id="code-3">
								        </div>
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-3','tree_ul_id_3','PROJECT',null,'code-3')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-xs-12 col-sm-12 p0">
										<ul id="tree_ul_id_3" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<h2 class="f16">
										 <font color="red">*</font> 勾选服务产品类别信息
									</h2>
									<div id="div-4" class="mb10 col-md-12 col-xs-12 col-sm-12 p0">
										<div class="fl mr5">
								  			产品类别：<input type="text" id="cate-4">
								  		</div>
								  		<div class="fl mr5">
								        	  目录编码：<input type="text" id="code-4">
								        </div>
								  	<input class="btn mt1 fl" type="button" value="搜索" onclick="searchCate('cate-4','tree_ul_id_4','SERVICE',null,'code-4')"/>
								  	</div>
									<div class="lr0_tbauto col-md-12 col-sm-12 col-xs-12 p0">
										<ul id="tree_ul_id_4" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<div class="mt20" id="tbody_category"></div>
							<div id="pagediv" align="right" class="mb50"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 <div class="btmfix">
	  	  <div class="mt5 mb5 tc">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev('3')">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems(2)">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next(1)">下一步</button>
	  	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/save_or_update.html" method="post">
		<input name="supplierId" id="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="categoryId" value=""  id="categoryId" type="hidden" /> 
		<input name="clickFlag" value=""  id="clickFlag" type="hidden" /> 
		<input name="flag" value=""  id="flag" type="hidden" />  
		<input name="supplierTypeIds" type="hidden" value="${currSupplier.supplierTypeIds }" /> 
		<input name="supplierTypeRelateId"  id="supplierTypeRelateId" type="hidden" value="" /> 
	</form>
   <div class="footer_margin">
   		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
   </div>
</body>
</html>
