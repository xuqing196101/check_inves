<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<link
	href="${pageContext.request.contextPath }/public/select2/css/select2.css"
	rel="stylesheet" />
<title>发布定型产品页面</title>
<script type="text/javascript">
$(document).ready(function(){
	intiTree();
	//加载采购机构信息
	$.ajax({
		url: "${pageContext.request.contextPath }/ob_project/mechanism.html",
		contentType: "application/json;charset=UTF-8",
		dataType: "json", //返回格式为json
		type: "POST", //请求方式           
		success: function(data) {
			if (data) {
				$.each(data, function(i, org) {
					$("#orgId").append("<option  value=" + org.id + ">" + org.shortName + "</option>");
				});
			} 
		 $("#orgId").select2();
		 $("#orgId").select2("val", "${obProduct.procurementId}");
		 $("#sela a").css("color","#555555");
		}
	});
}); 

	 /** 判断是否为根节点 */
    function isRoot(node){
    	if (node.pId == 0){
    		return true;
    	} 
    	return false;
    }
 
 /*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
  	  if (isRoot(treeNode)){
  		  layer.msg("不可选择根节点");
  		  return;
  	  }
	  if (!treeNode.isParent) {
	  	$("#citySel4").val(treeNode.name);
	    $("#categorieId4").val(treeNode.id);
	    $("#categoryLevel").val(treeNode.level+1);
	    hideMenu();
	    //加载采购机构信息
        $.ajax({
				url: "${pageContext.request.contextPath }/product/selOrgByCategory.do",
				type: "post",
				data: {
					smallPointsId:treeNode.id
				},
				success: function(data) {
					$("#orgId").select2("val", data);
				},
				error: function() {
					
				}
			});
	  }
    }
 
    function showMenu() {
		var cityObj = $("#citySel4");
		var cityOffset = $("#citySel4").offset();
		$("#menuContent").css({}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
    function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}

	  function intiTree(){
		  /* 加载目录信息 */
			var datas;
			var setting={
					   async:{
								autoParam:["id"],
								enable:true,
								url:"${pageContext.request.contextPath}/category/createtreeById.do",
								otherParam:{"otherParam":"zTreeAsyncTest"},  
								dataType:"json",
								type:"get",
							},
							callback:{
						    	onClick:zTreeOnClick,//点击节点触发的事件
			       			    
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
		    $.fn.zTree.init($("#treeDemo"),setting,datas);
	  }
	
	//搜索
	function searchs(){
			var name=$("#search").val();
			if(name!=""){
		 var zNodes;
			var zTreeObj;
			var setting = {
					async:{
						autoParam:["id"],
						enable:true,
						url: "${pageContext.request.contextPath}/category/createtreeById.do"
					},
				data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "parentId",
					}
				},
				callback: {
					onClick: zTreeOnClick
				},view: {
					showLine: true
				}
			};
			// 加载中的菊花图标
			 var loading = layer.load(1);
			
				$.ajax({
					url: "${pageContext.request.contextPath}/category/createtreeById.do",
					data: { "name" : encodeURI(name)},
					async: false,
					dataType: "json",
					success: function(data){
						if (data.length == 1) {
							layer.msg("没有符合查询条件的产品类别信息！");
						} else {
							zNodes = data;
							zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
							zTreeObj.expandAll(true);//全部展开
						}
						// 关闭加载中的菊花图标
						
						layer.close(loading);
						
					}
				});
		}else{
			intiTree();
		}
	}
	
	/* 清空提示消息 */
	function codeover(){
		$("#pcode").html("");
	}
	function nameover(){
		$("#pname").html("");
	}
	function pover(){
		$("#ppro").html("");
	}
	function pcategory(){
		$("#pcategory").html("");
	}
	
	/* 发布 */
	function sub(i){
		var id = $("#productId").val();
		var code = $("#code").val();
		var name = $("#name").val();
		var procurementId = $("#orgId option:selected").val();
		var category = $("#categorieId4").val();
		var categoryLevel = $("#categoryLevel").val();
		var standardModel = $("#standardModel").val();
		var qualityTechnicalStandard = $("#qualityTechnicalStandard").val();
		window.location.href = "${pageContext.request.contextPath}/product/edit.html?code="+code+"&&name="+name+"&&procurementId="+procurementId
				+"&&category="+category+"&&standardModel="+standardModel+"&&qualityTechnicalStandard="+qualityTechnicalStandard+"&&i="+i+"&&id="+id+"&&categoryLevel="+categoryLevel;
	}
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">保障作业</a></li>
				<li><a href="javascript:void(0)">定型产品竞价</a></li>
				<li class="active"><a href="javascript:void(0)">定型产品管理</a></li>
				<li class="active"><a href="javascript:void(0)">修改产品信息</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 修改定型产品页面开始 -->
	<div class="wrapper mt10">
		<div class="container">
			<div class="headline-v2">
				<h2>发布定型产品</h2>
			</div>
			<div class="content table_box">
				<input id="productId" value="${obProduct.id }"
					style="display: none;">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="info" width="18%"><div class="star_red">*</div>产品代码</td>
							<td width="32%"><input id="code" name="" onmousedown="codeover()"
								value="${obProduct.code }" type="text" class="w230 mb0">
								<div class="star_red" id="pcode">${errorCode }</div></td>
							<td class="info" width="18%"><div class="star_red">*</div>产品名称</td>
							<td width="32%"><input id="name" name="" onmousedown="nameover()"
								value="${obProduct.name }" type="text" class="w230 mb0">
								<div class="star_red" id="pname">${errorName }</div></td>
						</tr>
						<tr>
								<td class="info" width="18%"><div class="star_red">*</div>选择目录</td>
							<td >
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0 m0">
							<input id="citySel4" name="" value="${categoryName }" type="text" class="w230 mb0" onclick=" showMenu(); return false;" readonly> 
							<input id="categorieId4" name="categoryId" value="${cId}" type="hidden" class="w230 mb0 border0"> 
							<input id="categoryLevel" name="categoryLevel" value="${obProduct.productCategoryLevel}" type="hidden" class="w230 mb0 border0">
							<div class="star_red" id="pcategory">${error_category }</div>
							<!-- 目录框 -->
							<div id="menuContent" class="menuContent col-md-8 col-xs-8 col-sm-8 p0 tree_drop" style="z-index:10000;position:absolute;top:30px;left:0px" hidden="hidden">
								<div class="col-md-12 col-xs-12 col-sm-12 p0">
								    <input type="text" id="search" class="fl m0">
								    <img alt="" src="${pageContext.request.contextPath }/public/backend/images/view.png" style="position: absolute; right: 10px;top: 5px;" onclick="searchs()">
								</div>
								<ul id="treeDemo" class="ztree slect_option clear" style="max-height: 400px;"></ul>
							</div>
							</div>
							</td>
							<td class="info" width="18%"><div class="star_red">*</div>采购机构</td>
							<td width="32%" id = "sela" ><select id="orgId"
								name="supplierId" style="width: 66%;" onchange="pover()">
									<option value=""></option>
							</select>
								<div class="star_red" id="ppro">${errorProcurement }</div></td>
						</tr>
						
						<tr>
							<td class="info">规格型号</td>
							<td colspan="3">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea id="standardModel" name=""
										class="col-md-12 col-sm-12 col-xs-12" style="height: 130px">${obProduct.standardModel }</textarea>
								</div>
								<div class="star_red">${error_standardModel }</div>
							</td>
						</tr>
						<tr>
							<td class="info">质量技术标准</td>
							<td colspan="3">
								<div class="col-md-12 col-sm-12 col-xs-12 p0">
									<textarea id="qualityTechnicalStandard" name=""
										class="col-md-12 col-sm-12 col-xs-12" style="height: 130px">${obProduct.qualityTechnicalStandard }</textarea>
								</div>
								<div class="star_red">${error_quality }</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-12 clear tc mt10">
				<button class="btn btn-windows save" type="button" onclick="sub(1)">暂存</button>
				<button class="btn btn-windows apply" type="button" onclick="sub(2)">发布</button>
				<button class="btn btn-windows back" type="button"
					onclick="window.location.href = '${pageContext.request.contextPath}/product/list.html'">返回</button>
			</div>
		</div>
	</div>

</body>
</html>