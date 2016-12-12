
var treeObj = null;
$(function(){
	var datas;
	var type = $("#typeNameId").val();
	var setting={
		async:{
			autoParam:["id"],
			enable:true,
			url: globalPath + "/purchaseManage/getTree.do?typeName=" + type,
			dataType:"json",
			type:"post",
		},
		data:{
			keep:{
				parent:true
			},
			key:{
				title:"title",
				name:"name",
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
		callback:{
			onClick:zTreeOnClick,
			onAsyncSuccess: zTreeOnAsyncSuccess
		}
	};
	treeObj = $.fn.zTree.init($("#departTree"),setting,datas); 
	treeObj.expandAll(false);
});

var selectedTreeId = null;
var currentPid = null;
/**
 * 自动选中
 * @param event
 * @param treeId
 * @param treeNode
 * @param msg
 */
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	var zTree = $.fn.zTree.getZTreeObj("departTree");
	
	var  srcOrgId = $("#srcOrgId").val();
	if (srcOrgId != null && srcOrgId !=""){
		var treeNode = zTree.getNodeByParam("id",srcOrgId, null);
		zTree.selectNode(treeNode);
		zTree.setting.callback.onClick(null, zTree.setting.treeId, treeNode);
	} 
}

/**
 * 点击tree
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event,treeId,treeNode){
	if (treeNode != null && treeNode !=""){
		selectedTreeId = treeNode.id;
		currentPid = treeNode.pId;
		if (treeNode.pId != 0){
			$("#treebody").show();
			$("#treebody").load(globalPath + "/purchaseManage/getTreeBody.do?id="+treeNode.id);
		} else {
			$("#treebody").hide();
		}
	} else {
		selectedTreeId = null;
		currentPid = null;
	}
}

/**
 * 添加部门
 */
function addTreeNode(){
	if (selectedTreeId == null){
		layer.msg("请选择一个机构");
		return ;
	}
	var typeName = $("#typeNameId").val();
	window.location.href= globalPath + "/purchaseManage/add.html?parentId=" + selectedTreeId + "&typeName=" + typeName;
}

/**
 * 修改部门
 */
function editTreeNode(){
	if (selectedTreeId == null){
		layer.msg("请选择一个部门进行编辑");
		return;
	}
	if (currentPid == 0){
		layer.msg("根节点不能编辑");
		return ;
	}
	window.location.href= globalPath + "/purchaseManage/edit.html?id=" + selectedTreeId	;
}

/**
 * 删除部门
 */
function delTreeNode(){
	if (selectedTreeId == null){
		layer.msg("请选择一个部门");
		return ;
	}
	if (currentPid == 0){
		layer.msg("根节点不能删除");
		return ;
	}
	layer.confirm("您确认要删除吗?",{
		btn:['确认','取消']
	},function(){
		ajaxDelDepart();
	}
  );
	
}

/**
 * 异步调用
 */
function ajaxDelDepart(){
	 $.ajax({
			type : 'post',
			url :  globalPath + "/purchaseManage/delOrg.do",
			data : {id:selectedTreeId},
			success : function(msg) {
					  if(msg == 'ok'){
						  layer.msg("删除成功");
						  refreshAllTree();
					  } else{
						  layer.msg("删除失败");
					  }
			}
		}); 
}

/**
 * 刷新
 */
function refreshAllTree(){
	if (treeObj != null){
		var node = treeObj.getNodeByParam("id", selectedTreeId, null)
		var parentNode = node.getParentNode();
		treeObj.removeNode(node);
		if (parentNode != null && parentNode !="" && parentNode.pId !=0){
			treeObj.selectNode(parentNode);
			treeObj.setting.callback.onClick(null, treeObj.setting.treeId, parentNode);
		} else {
			$("#treebody").hide();
		}
	}
}

