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
		
	});
	
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
				onExpand: zTreeOnExpand
			},
			view: {
				showLine: true
			}
	 	};
		$.fn.zTree.init($("#" + kind), setting, zNodes);
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
			loading = layer.load(1);
			var supplierId = "${currSupplier.id}";
			var path = "${pageContext.request.contextPath}/supplier_item/getCategories.html?supplierId=" + supplierId + "&supplierTypeRelateId=" + code;
			$("#tbody_category").load(path);
		}
	};
	
	//加载tab页签
	function loadTab(code,kind, status){
		$("#cate-" + kind.charAt(kind.length - 1)).val("");
		loadZtree(code,kind, status);
	}
	
	function saveItems(){
		/*  getCategoryId();
		$("#flag").val("");
		$("#items_info_form_id").submit(); */
	//	function temporarySave(){
		  $("input[name='flag']").val("file");
			$.ajax({
				url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
				type : "post",
				data : $("#items_info_form_id").serializeArray(),
				contextType: "application/x-www-form-urlencoded",
				success:function(msg){
				 
			 	if (msg == 'ok'){
						layer.msg('暂存成功');
					} 
				  if (msg == 'failed'){
						layer.msg('暂存失败');
					}  
				}
			});
		//}
		
	}
	
	function next(flag){
		var flag =supCategory();
	 
		if(flag==false){
			layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
		}else{
			$("#flag").val(flag);
			$("#items_info_form_id").submit();
		}
	}
	
	function prev(flag){
		getCategoryId();
		$("#flag").val(flag);
		$("#items_info_form_id").submit();
	}
	
	function getCategoryId(){
		var ids=[]; 
		for (var i = 1; i < 5; i++) {
			var id = "tree_ul_id_" + i;
			var tree = $.fn.zTree.getZTreeObj(id);
			if(tree!=null){
				nodes = tree.getCheckedNodes(true);
				for (var j = 0; j < nodes.length; j++) {
					ids.push(nodes[j].id);
				}
			}
		}
		$("#categoryId").val(ids);
	 	return ids;
	}
	var loading;
	function saveCategory(event, treeId, treeNode){
		loading = layer.load(1);
		var clickFlag;
		if (treeNode.checked) {
			clickFlag = "1";
		} else {
			clickFlag = "0";
		}
		$("#clickFlag").val(clickFlag);	 	
		
		var tree = $.fn.zTree.getZTreeObj(treeId);
		$("#categoryId").val(treeNode.id);
		var attr1=$("#li_id_1").attr("class");
		if(attr1=='active'){
			$("#supplierTypeRelateId").val("PRODUCT");
		}
		var attr2=$("#li_id_2").attr("class");
		if(attr2=='active'){
			$("#supplierTypeRelateId").val("SALES");
		}
		var attr3=$("#li_id_3").attr("class");
		if(attr3=='active'){
			$("#supplierTypeRelateId").val("PROJECT");
		}
		var attr4=$("#li_id_4").attr("class");
		if(attr4=='active'){
			$("#supplierTypeRelateId").val("SERVICE");
		}
		$("#flag").val("4");
		var supplierId="${currSupplier.id}";
		var type = $("#supplierTypeRelateId").val();
		$.ajax({
			url: "${pageContext.request.contextPath}/supplier_item/saveCategory.do",
			async: false,
			data: $("#items_info_form_id").serialize(),
			success: function() {
				zTreeOnAsyncSuccess(null, treeId, null, null);
			}
		});
	}
	
	function supCategory(){
		var flag=true;
		var supplierId="${currSupplier.id}";
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier_item/getSupplierCate.do",
			type : "post",
			data : {
				supplierId : supplierId,
			},
			dataType : "json",
			success : function(result) {
				if(result=="0"){
					flag=false;
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
	
	function searchCate(cateId, treeId,type,seq, code) {
		var zNodes;
		var zTreeObj;
		var setting = {
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
				onExpand: zTreeOnExpand
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
					}
					// 关闭加载中的菊花图标
					layer.close(loading);
				}
			});
		}
	}
	sessionStorage.locationC=true;
	sessionStorage.index=3;
</script>
</head>

<body>
	<div class="wrapper">
<%@include file="supplierNav.jsp" %>
	<%-- 	<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i> -->
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_02">产品类别</span> </span> <span class="new_step fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step  fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_02">销售(承包)合同</span> </span> <span class="new_step fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_01">采购机构</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span> <span class="new_step fl"><i class="">8</i> 
						<span class="step_desc_01">提交</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if> --%>

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
											勾选物资生产型产品类别信息
									</h2>
									<div id="div-1" class="mb10">
								  	产品类别:<input type="text" id="cate-1">
								          目录编码:<input type="text" id="code-1">
								  	<input class="btn" type="button" value="搜索" onclick="searchCate('cate-1','tree_ul_id_1','PRODUCT',1,'code-1')"/>
								  	</div>
									<div class="lr0_tbauto">
										<ul id="tree_ul_id_1" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<h2 class="f16 ">
											勾选物资销售型产品类别信息
									</h2>
									<div id="div-2" class="mb10">
								  	产品类别:<input type="text" id="cate-2">
								          目录编码:<input type="text" id="code-2">
								  	<input class="btn" type="button" value="搜索" onclick="searchCate('cate-2','tree_ul_id_2','SALES',2,'code-2')"/>
								  	</div>
									<div class="lr0_tbauto">
										<ul id="tree_ul_id_2" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<h2 class="f16  ">
									      	勾选工程产品类别信息
									</h2>
									<div id="div-3" class="mb10">
								  	产品类别:<input type="text" id="cate-3">
								          目录编码:<input type="text" id="code-3">
								  	<input class="btn" type="button" value="搜索" onclick="searchCate('cate-3','tree_ul_id_3','PROJECT',null,'code-3')"/>
								  	</div>
									<div class="lr0_tbauto">
										<ul id="tree_ul_id_3" class="ztree_supplier mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<h2 class="f16">
										 勾选服务产品类别信息
									</h2>
									<div id="div-4" class="mb10">
								  	产品类别:<input type="text" id="cate-4">
								          目录编码:<input type="text" id="code-4">
								  	<input class="btn" type="button" value="搜索" onclick="searchCate('cate-4','tree_ul_id_4','SERVICE',null,'code-4')"/>
								  	</div>
									<div class="lr0_tbauto">
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
	<!-- footer -->
	<!-- <c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if> -->
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/commons.js"></script>
</html>
