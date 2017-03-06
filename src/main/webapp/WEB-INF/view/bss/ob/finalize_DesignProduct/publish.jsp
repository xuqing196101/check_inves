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
			area: ['580px','300px'],
			content: $("#openDiv")
		});
	}
	
	function cancel(){
		layer.closeAll();
	}
	
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
			$("#procurement").val(n[0]);
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
        $("#citySel4").val(treeNode.name);
        $("#categorieId4").val(treeNode.id);
        hideMenu();
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
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				    <td class="info"><div class="star_red">*</div>产品名称</td>
				    <td>
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				  </tr>
				  <tr>
				    <td class="info"><div class="star_red">*</div>采购机构</td>
				    <td>
				    	<input id="procurement" name="procurement" value="" type="text" class="w230 mb0 border0"  onclick="openDiv()" readonly>
					</td>
					<td colspan="2"></td>
				  </tr>
				   <tr>
				    <td class="info">选择目录</td>
				    <td colspan="3">
				    	<button class="btn" onclick=" showMenu(); return false;">选择目录</button>
				    	<input id="citySel4" name="procurement" value="" type="text" class="w230 mb0 border0"  onclick="" readonly>
				    </td>
				  </tr>
				  <tr>
				    <td class="info">规格型号</td>
				    <td colspan="3">
				   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
        					<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
       					</div>
				   	</td>
				  </tr>
				  <tr>
				    <td class="info">质量技术标准</td>
				    <td colspan="3">
				   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
        					<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
       					</div>
				   	</td>
				  </tr>
				 </tbody>
			 </table>
			 
			 <div class="col-md-12 clear tc mt10">
	    		<button class="btn btn-windows save" type="submit">保存</button>
	    		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
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
		  <th class="info">产品代码</th>
		  <th class="info">产品名称</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc w30"><input type="checkbox" name="chkItem" value="111" /></td>
		  <td class="tc w50">1</td>
		  <td>1111111</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		</tr>
		<tr>
		  <td class="tc w30"><input type="checkbox" name="chkItem" value="222" /></td>
		  <td class="tc w50">2</td>
		  <td>2222222</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		</tr>
		<tr>
		  <td class="tc w30"><input type="checkbox" name="chkItem" value="333" /></td>
		  <td class="tc w50">3</td>
		  <td>3333333</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		</tr>
		<tr>
		  <td class="tc w30"><input type="checkbox" name="chkItem" value="444" /></td>
		  <td class="tc w50">4</td>
		  <td>4444444</td>
		  <td><a href="javascript:void(0)">啦啦啦啦啦</a></td>
		</tr>
	</table>
			  
              <div class="tc mt10 col-md-12">
                <input class="btn" id="inputb" name="addr" onclick="ok()" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		   </div>
</body>
</html>