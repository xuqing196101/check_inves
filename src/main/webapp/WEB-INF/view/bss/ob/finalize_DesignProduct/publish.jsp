<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
<title>发布定型产品页面</title>
<script type="text/javascript">
$(document).ready(function(){
	var datas;
	var setting={
			   async:{
						autoParam:["id"],
						enable:true,
						url:"${pageContext.request.contextPath}/category/createtree.do",
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

}); 

	function openDiv(){
		layer.open({
			type: 1,
			title: '采购机构列表',
			skin: 'layui-layer-rim',
			shadeClose: true,
			area: ['650px','400px'],
			content: $("#openDiv")
		});
	}
	
	function cancel(){
		layer.closeAll();
	}
	
	/* 选择机构确定 */
	function ok(){
		var item = document.getElementsByName("chkItem");
		var n = new Array();
 		var j = 0;
 		for (var i = 0; i < item.length; i++) {
 			if(item[i].checked){
 				n[j] = item[i].value;
 				j ++;
 			}
 		}
 		if(n.length > 1){
 			alert("只能选择一家采购机构");
 		}else{
 			jgid = n[0];
 			$("#pro").val(jgid);
			$.ajax({
				url: "${pageContext.request.contextPath }/product/selPurchaseDepbyId.do",
				type: "post",
				data: {
					id: jgid
				},
				success: function(data) {
					$("#procurementId").val(data.data);
				}
			});
			layer.closeAll();
 		}
	}
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
	  if (treeNode) {
		  if(treeNode.level > 4 || treeNode.level == 4){
			  $("#citySel4").val(treeNode.name);
	          $("#categorieId4").val(treeNode.id);
	          hideMenu();
		  }else{
			  layer.msg("不能选择该目录");
		  }
       
        
	  }
    }
 
    function showMenu() {
		var cityObj = $("#citySel4");
		var cityOffset = $("#citySel4").offset();
		$("#menuContent").css({left: "445px", top: "205px"}).slideDown("fast");

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
	
	/* 发布 */
	function sub(i){
		var code = $("#code").val();
		var name = $("#name").val();
		var procurementId = $("#pro").val();
		var category = $("#categorieId4").val();
		var standardModel = $("#standardModel").val();
		var qualityTechnicalStandard = $("#qualityTechnicalStandard").val();
		if(code == null || code == ""){
			$("#pcode").html("产品代码不能为空");
		}
		if(name == null || name == ""){
			$("#pname").html("产品名称不能为空");
		}
		if(procurementId == null || procurementId == ""){
			$("#ppro").html("采购机构不能为空");
		}
		if(code != null && name != null && procurementId != null && code != "" && name != "" && procurementId != ""){
		window.location.href = "${pageContext.request.contextPath}/product/add.html?code="+code+"&&name="+name+"&&procurementId="+procurementId
				+"&&category="+category+"&&standardModel="+standardModel+"&&qualityTechnicalStandard="+qualityTechnicalStandard+"&&i="+i;
		}
	}
	
	function codeover(){
		$("#pcode").html("");
	}
	function nameover(){
		$("#pname").html("");
	}
	function pover(){
		$("#ppro").html("");
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li><li class="active"><a href="javascript:void(0)">发布定型产品</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 发布定型产品页面开始 -->
  <div class="wrapper mt10">
  <div class="container">
  <div class="headline-v2">
     	<h2>发布定型产品</h2>
	</div> 
  <div class="mt10">
   </div> 
  <table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="info"><div class="star_red">*</div>产品代码</td>
				    <td>
				    	<input id="code" name="" value="" type="text" class="w230 mb0 border0" onmousedown="codeover()">
				    	<div class="star_red" id = "pcode"></div>
				    </td>
				    <td class="info"><div class="star_red">*</div>产品名称</td>
				    <td>
				    	<input id="name" name="" value="" type="text" class="w230 mb0 border0" onmousedown="nameover()">
				    	<div class="star_red" id = "pname"></div>
				    </td>
				  </tr>
				  <tr>
				    <td class="info"><div class="star_red">*</div>采购机构</td>
				    <td>
				    	<input id="procurementId" name="" value="" type="text" class="w230 mb0 border0"  onclick="openDiv()" onmousedown="pover()" readonly>
						<input id = "pro" style="display:none;" value="">
						<div class="star_red" id = "ppro"></div>
					</td>
					<td colspan="2"></td>
				  </tr>
				   <tr>
				    <td class="info">选择目录</td>
				    <td colspan="3">
				    	<button class="btn" onclick=" showMenu(); return false;">选择目录</button>
				    	<input id="citySel4" name="" value="" type="text" class="w230 mb0 border0"  onclick="" readonly>
				    	<input id="categorieId4" name="categoryId" value="" type="hidden" class="w230 mb0 border0" >
				    </td>
				  </tr>
				  <tr>
				    <td class="info">规格型号</td>
				    <td colspan="3">
				   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
        					<textarea id = "standardModel" name="" class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
       					</div>
				   	</td>
				  </tr>
				  <tr>
				    <td class="info">质量技术标准</td>
				    <td colspan="3">
				   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
        					<textarea id = "qualityTechnicalStandard" name="" class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
       					</div>
				   	</td>
				  </tr>
				 </tbody>
			 </table>
			 
			 <div class="col-md-12 clear tc mt10">
	    		<button class="btn btn-windows save" type="button" onclick = "sub(1)">暂存</button>
	    		<button class="btn btn-windows apply" type="button" onclick = "sub(2)">发布</button>
	    		<button class="btn btn-windows back" type="button" onclick="window.location.href = '${pageContext.request.contextPath}/product/list.html'">返回</button>
			 </div>
	</div>
  </div>
  
  <!-- 目录框 -->
  
 		<div id="menuContent" class="menuContent dw188 tree_drop">
			<ul id="treeDemo" class="ztree slect_option"></ul>
		</div>
  <!-- 选择框弹出 -->
  <div id="openDiv" class="dnone layui-layer-wrap" >
		  <div class="drop_window">
			  
			  <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">选择</th>
		  <th class="w50 info">序号</th>
		  <th class="info">单位名称</th>
		  <th class="info">采购机构级别</th>
		</tr>
		</thead>
		<c:forEach items="${purchaseDepList }" var="purchase" varStatus="vs">
		<tr>
		  <td class="tc w30"><input type="checkbox" name="chkItem" value="${purchase.id}" /></td>
		  <td class="tc w50">${vs.index+1}</td>
		  <td>${purchase.depName}</td>
		  <td>${purchase.levelDep}</td>
		</tr>
		</c:forEach>
	</table>
			  
              <div class="tc mt10 col-md-12">
                <input class="btn" id="inputb" name="addr" onclick="ok()" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		   </div>
</body>
</html>