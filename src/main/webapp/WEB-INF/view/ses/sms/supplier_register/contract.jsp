<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp" %>
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
			/* if (id == "tab-1") {
				id="PRODUCT";
				kind = "tree_ul_id_1";
			}
			if (id == "tab-2"){
				id="SALES";
				kind = "tree_ul_id_2";
			}  */
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
	
	
	/* 	alert(kind); */
	/* 	var id = "";
		if (kind == "1") id = "tree_ul_id_1";
		if (kind == "2") id = "tree_ul_id_2";
		if (kind == "3") id = "tree_ul_id_3";
		if (kind == "4") id = "tree_ul_id_4"; */
	 
		var setting = {
/* 			async : {
				autoParam: ["id"],
				enable : true,
				url : "${pageContext.request.contextPath}/supplier/category_type.html",
				otherParam : {
					"code":code,
					"supplierId": "${currSupplier.id}",
					 kind : kind  
				},
				dataType : "json",
				type : "post",
			}, */
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
		 		onClick:zTreeOnClick
			} 
		};

	function loadZtree(code, kind) {
		var supplierId="${currSupplier.id}";
			$.ajax({
		        type: "GET",
		        async: false, 
		        url: "${pageContext.request.contextPath}/supplier_item/category_type.html?code="+code+"&supplierId="+supplierId,
		        dataType: "json",
		        success: function(zNodes){
			        	zTreeObj = $.fn.zTree.init($("#" + kind), setting, zNodes);
			    		zTreeObj.expandAll(true);
		          }
		    	});
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
	
	function prev(){
	 
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
	
	
	
	var treeid = null , nodeName=null;
	var datas;
	 $(document).ready(function(){  
          $.fn.zTree.init($("#tree_ul_id_3"),setting,datas);
	      var treeObj = $.fn.zTree.getZTreeObj("ztree");
	      var nodes =  treeObj.transformToArray(treeObj.getNodes()); 
	      for(var i=0 ;i<nodes.length;i++){
		     if (nodes[i].status==1) {
				 check==true;
		      }
	       }
	 }); 
	 
	 
	 var setting={
			   async:{
						autoParam:["id"],
						enable:true,
						url:"${pageContext.request.contextPath}/category/createtree.do",
						otherParam:{"otherParam":"zTreeAsyncTest"},  
						dataType:"json",
						datafilter:filter,
						type:"get",
					},
					callback:{
				    	onClick:zTreeOnClick,//点击节点触发的事件
	       			    
				    }, 
					check : {
						enable : true,
						chkStyle:"checkbox",  
						chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
					},
					
					data:{
						keep:{
							parent:true
						},
						key:{
							title:"title"
						},
						simpleData:{
							enable:true,
							idKey:"id",
							pIdKey:"pId",
							rootPId:"0",
						}
				    },
				   view:{
				        selectedMulti: false,
				        showTitle: false,
				   },
	         };
	 
	 
	 function filter(treeId,parentNode,childNode){
		 if (!childNodes) return null;
			for(var i = 0; i<childNodes.length;i++){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g,'.');
			}
		return childNodes;
	 }
	 
</script>
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
	<%@ include file="/reg_head.jsp"%>
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
						<div class="line"></div> <span class="step_desc_02">品目信息</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span> <span class="new_step current fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">品目合同上传</span> </span> <span class="new_step fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i> 
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
						 
						  <table class="table table-bordered">
						  
										  <tr>
										    <td class="info"> 品目名称</td>
										    <td colspan="3">合同上传</td>
										    <td colspan="3">收款进账单</td>
										  </tr>
										  
										  
										  <c:forEach items="${list2 }" var="obj">
									      <tr>
									        <td class="info">计算机</td>
										    <td class="info">2015合同</td>
										    <td class="info">2014合同 </td>
										    <td class="info"><2013合同</td>
										    <td class="info">  文件</td>
										    <td class="info">  文件</td>
										    <td class="info">文件 </td>
										                        
										    <td class="info">  </td>
										  </tr>
										</c:forEach>
										
									</table> 
								
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems(2)">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next(1)">下一步</button>
	  	  </div>
	</div>
	
	
	<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier_item/category_tree.html" method="post">
		<input name="supplierId" value="${currSupplier.id}" type="hidden" /> 
		<input name="categoryId" value=""  id="categoryId" type="hidden" /> 
		<input name="flag" value=""  id="flag" type="hidden" /> 
	 
	</form>
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}"><jsp:include page="../../../../../index_bottom.jsp"></jsp:include></c:if>
</body>
</html>
