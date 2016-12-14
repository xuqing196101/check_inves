<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/front.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
		// ztree
		$("#tab_content_div_id").find(".tab-pane").each(function(index) {
			var kind = "";
			var id = $(this).attr("id");
			if (id == "tab-1") {
				id="PRODUCT";
				kind = "tree_ul_id_1";
			}
			if (id == "tab-2"){
				id="SALES";
				kind = "tree_ul_id_2";
			} 
			if (id == "tab-3"){
				 id="PROJECT";
				 kind = "tree_ul_id_3";
			}
			if (id == "tab-4"){
				 id="SERVICE";
				 kind = "tree_ul_id_4";
			}
			loadZtree(id, kind);
		});
		
		if ("${currSupplier.status}" == 7) {
			showReason();
		}
		
	});
	
	function loadZtree(code, kind) {
	/* 	alert(kind); */
	/* 	var id = "";
		if (kind == "1") id = "tree_ul_id_1";
		if (kind == "2") id = "tree_ul_id_2";
		if (kind == "3") id = "tree_ul_id_3";
		if (kind == "4") id = "tree_ul_id_4"; */
	 
		var setting = {
			async : {
				autoParam: ["id"],
				enable : true,
				url : "${pageContext.request.contextPath}/supplier/category_type.html",
				otherParam : {
					"code":code,
					"supplierId": "${currSupplier.id}",
					 /*kind : kind */
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
					idKey : "id",
					pIdKey : "parentId"
				}
			},
		 	callback: {
		 		onClick:zTreeOnClick
			} 
		};
		zTreeObj = $.fn.zTree.init($("#" + kind), setting, zNodes);
	}
	
	
 
		function zTreeOnClick(event,treeId,treeNode){
			var categoryId=treeNode.id;
			
			layer.open({
				type : 2,
				title : '品目文件上传',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '300px', '280px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_item/getCategory.html?categoryId=' + categoryId, //url
				closeBtn : 1, //不显示关闭按钮
			});
			
			
		/* 	layer.open({
				type : 2,
				title : '审核反馈',
				closeBtn : 0, //不显示关闭按钮
				//skin : 'layui-layer-lan', //加上边框
				area : [ '500px', '300px' ], //宽高
				offset : '100px',
				shade : 0,
				maxmin : true,
				shift : 2,
				content : '${pageContext.request.contextPath}/supplier_item/getCategory.html?categoryId=' + categoryId, //url
			}); */
			
			
		/* 	$("#checkedAll").attr("checked",false);
			getDetail(treeNode.id);
			$("#mid").val(treeNode.id); */
		};
	/* 	function onCheck(e, treeId, treeNode) {
		var ids = "";
		var flag = treeNode.checked;
		var result = checkType();
		var tree = $.fn.zTree.getZTreeObj(result.id);
		var nodes = tree.getChangeCheckedNodes();
		for (var i = 0; i < nodes.length; i++) {
			if (!nodes[i].isParent) {
				if (ids) {
					ids += ",";
				}
				ids += nodes[i].id;
			}
		}
		
		if (ids) {
			$.ajax({
				url : "${pageContext.request.contextPath}/supplier_level/find_credit_ctnt_by_credit_id.do",
				type : "post",
				data : {
					ids : ids,
					flag : flag,
					type : result.type
				},
				dataType : "json",
				success : function(result) {
				},
			});
		} */
		
		/**for (var i = 0; i < nodes.length; i++) {
			nodes[i].checkedOld = nodes[i].checked;
		}*/
/* 	} */
	

	/** 保存品目树信息 */
	function saveItems(jsp) {
		var result = getIds("tree_ul_id_1");
		var addProCategoryIds = result.addIds;
		var deleteProCategoryIds = result.deleteIds;
		
		result = getIds("tree_ul_id_2");
		var addSellCategoryIds = result.addIds;
		var deleteSellCategoryIds = result.deleteIds;
		
		result = getIds("tree_ul_id_3");
		var addEngCategoryIds = result.addIds;
		var deleteEngCategoryIds = result.deleteIds;
		
		result = getIds("tree_ul_id_4");
		var addServeCategoryIds = result.addIds;
		var deleteServeCategoryIds = result.deleteIds;
		
		$("input[name='jsp']").val(jsp);
		
		$("input[name='addProCategoryIds']").val(addProCategoryIds);
		$("input[name='deleteProCategoryIds']").val(deleteProCategoryIds);
		
		$("input[name='addSellCategoryIds']").val(addSellCategoryIds);
		$("input[name='deleteSellCategoryIds']").val(deleteSellCategoryIds);
		
		$("input[name='addEngCategoryIds']").val(addEngCategoryIds);
		$("input[name='deleteEngCategoryIds']").val(deleteEngCategoryIds);
		
		$("input[name='addServeCategoryIds']").val(addServeCategoryIds);
		$("input[name='deleteServeCategoryIds']").val(deleteServeCategoryIds);
		
		$("#items_info_form_id").submit();
	}
	
	function ss(flag) {
/* 		var treeObj = $.fn.zTree.getZTreeObj(id);
		var addIds = "";
		var deleteIds = "";
		if (treeObj) {
			var nodes = treeObj.getChangeCheckedNodes();
			for ( var i = 0; i < nodes.length; i++) {
				if (!nodes[i].isParent) {
					if (nodes[i].checked) {
						if (addIds) {
							addIds += ",";
						}
						addIds += nodes[i].id;
					} else {
						if (deleteIds) {
							deleteIds += ",";
						}
						deleteIds += nodes[i].id;
					}
				}
			}
		}
		var result = {
			addIds : addIds,
			deleteIds : deleteIds
		}; 
		
		return result;*/

		
		
	}
	
	function saveItems(flag){
		 getCategoryId();
		$("#flag").val(flag);
		$("#items_info_form_id").submit();
	}
	
	function next(flag){
		 getCategoryId();
		$("#flag").val(flag);
		$("#items_info_form_id").submit();
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
				//	if (!nodes[j].isParent) {
						//alert(nodes[j].id);
						ids.push(nodes[j].id);
					//}
				}
			}
		}
		$("#categoryId").val(ids);
		//return ids;
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
</script>
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
	<%@ include file="/index_head.jsp"%>
	</c:if>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i> -->
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">4</i>
<!-- 						<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step current fl"><i class="">5</i> -->
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">5</i>
<!-- 						<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i> -->
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">7</i> 
						<span class="step_desc_01">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if>
 
		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10" >
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<li id="li_id_1" class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">物资-生产型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<li id="li_id_2" class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class=" f18">物资-销售型品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
								<li id="li_id_3" class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class=" f18">工程品目信息</a></li>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<li id="li_id_4" class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" class=" f18">服务品目信息</a></li>
							</c:if>
						</ul>
						<div class="tab-content padding-top-20" id="tab_content_div_id" style="float:left;width:300px;">
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<h2 class="f16 ">
											勾选物资生产型品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_1" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">
								<!-- 物资销售型 -->
								<div class="tab-pane fade height-300" id="tab-2">
									<h2 class="f16 ">
											勾选物资销售型品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_2" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
							<!-- 服务 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<h2 class="f16  ">
									      	勾选工程品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_3" class="ztree mt30"></ul>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}">
								<!-- 生产 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<h2 class="f16">
										 勾选服务品目信息
									</h2>
									<div class="lr0_tbauto w200">
										<ul id="tree_ul_id_4" class="ztree mt30"></ul>
									</div>
								</div>
								
								
								
							</c:if>
							
						</div>
						
						<div style="float:left;margin-top:30px;">
						${err_item }
						</div>
				<%-- 		<div>
						计算机附件
						 <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25">
							 <u:show showId="business_show"  businessId="12345678" sysKey="1" typeId="asdhkja1212312" /> 
				   	   		 <u:upload id="business_up"   businessId="12345678" sysKey="1" typeId="asdhkja1212312" auto="true" />
						   </div>
				   
				   
								
						</div> --%>
								
						<!-- <div class="mt40 tc mb50">
							<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems('professional_info')">上一步</button>
							<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems('items')">暂存</button>
							<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveItems('products')">下一步</button>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev(3)">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems(2)">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next(1)">下一步</button>
	  	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/save_or_update.html" method="post">
		<input name="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="categoryId" value=""  id="categoryId" type="hidden" /> 
		<input name="flag" value=""  id="flag" type="hidden" /> 
		<%-- <input name="jsp" type="hidden" />
		<input type="hidden" name="defaultPage" value="${defaultPage}" />
		
		<input type="hidden" name="addProCategoryIds" />
		<input type="hidden" name="deleteProCategoryIds" />
		
		<input type="hidden" name="addSellCategoryIds" />
		<input type="hidden" name="deleteSellCategoryIds" />
		
		<input type="hidden" name="addEngCategoryIds" />
		<input type="hidden" name="deleteEngCategoryIds" />
		
		<input type="hidden" name="addServeCategoryIds" />
		<input type="hidden" name="deleteServeCategoryIds" /> --%>
	</form>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
