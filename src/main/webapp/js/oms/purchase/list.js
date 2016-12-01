
var treeObj = null;
$(function(){
	var datas;
	var setting={
		async:{
			autoParam:["id"],
			enable:true,
			url: globalPath + "/purchaseManage/gettree.do",
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
				rootPId:"-1",
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
	} else {
		var nodes = zTree.getNodes();
		zTree.selectNode(nodes[0]);
		zTree.setting.callback.onClick(null, zTree.setting.treeId, nodes[0]);
	}
}

/**
 * 点击tree
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event,treeId,treeNode){
	selectedTreeId =  treeNode.id;
	$("#treebody").load(globalPath + "/purchaseManage/gettreebody.do?id="+treeNode.id);
}

/**
 * 添加部门
 */
function addTreeNode(){
	window.location.href= globalPath + "/purchaseManage/add.html";
}



/**
 * 修改部门
 */
function editTreeNode(){
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
		treeObj.removeNode(node);
		var allNodes = treeObj.getNodes();
	    treeObj.selectNode(allNodes[0]);
	    treeObj.setting.callback.onClick(null, treeObj.setting.treeId, allNodes[0]);
	}
    
}

