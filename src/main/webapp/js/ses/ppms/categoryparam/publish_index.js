//类型初始化
var typesObj;
$(function(){
	var datas;
	var setting={
		    async:{
					autoParam:["id","name"],
					enable:true,
					url: globalPath + "/publish/initTreeIndex.do",
					dataType:"json",
					type:"post",
				},
				callback:{
			    	onClick:zTreeOnClick,
			    	beforeExpand: expandNode
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
			    check:{
				    chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
	       		    chkStyle:"checkbox",
	       		    nocheckInherit: false,
					enable: false
			   },
			    view:{
			        selectedMulti: false,
			        showTitle: false,
			   }
		};
		//初始化tree
	    $.fn.zTree.init($("#ztree"),setting,datas); 
	    //初始化类型
	    typesObj = initTypes();
	    $("#uListId").hide();
});

/**
 * 只有父节点树则不展开
 */
function expandNode(treeId,treeNode){
	if(typeof treeNode.children =="undefined"){
		return false;
	}
}

/**
 * 点击tree
 * @param event
 * @param treeId
 * @param treeNode
 */
var selectedTreeId = null;
function zTreeOnClick(event,treeId,treeNode){
	if(treeNode.isParent==true){
		return false;
	}
	if (treeNode.pId !=0) {
		selectedTreeId = treeNode.id;
		getTreeNodeData(treeNode.id,treeNode);
	} else {
		$("#uListId").hide();
	}
}

/**
 *  获取treeNode集合
 * @param cateId 品目Id
 * @param classify 分类
 * @param publishStatus 是否发布
 */
function getTreeNodeData(cateId,treeNode){
	$('#publishBtnDiv').show();
	$('#baseInfoDiv').show();
	$('#productParamDiv').show();
	
	//清空表格内容
	clearTbody('baseInfoTbody');
	$('#productParamTbody').empty();
	
	$.ajax({
		type:"post",
		dataType:"json",
		data:{'cateId':cateId},
		async:false,
		url: globalPath + "/cateParam/params.do" ,
		success:calledback
	});
	
	//填入产品名称
	$("#baseInfoTbody td.productName").text(treeNode.name||'');
	
	//加载是否公开
	var publishStatus = treeNode.pubStatus;
	if (publishStatus != null){
		$("#baseInfoTbody td.isOpen").append(publishStatus?'是':'否');
	}
	
	//加载类型
	var root = getCurrentRoot(treeNode);
	if (root.classify != null && root.classify == 'GOODS'){
		loadcheckbox(treeNode.classify);
	} 
	
	//加载发布状态
	if (treeNode.status !=null && treeNode.status !=""){
		loadPublishHtml(treeNode.status);
	}
}

/**
 * 清空指定td
 * @param tbody
 */
function clearTbody(tbody){
	$('#'+tbody).find('td:not(.bggrey)').empty();
}

/**
 * 初始化数据类型
 * @returns 返回类型数据
 */
function initTypes(){
	var typeData;
	$.ajax({
		type:"post",
		dataType:"json",
		async: false ,
		url: globalPath + "/cateParam/initTypes.do" ,
		success:function(data){
			typeData = data;
		}
	});
	return typeData;
}

/**
 * 加载参数数据
 * @param data
 */
function calledback(data){
	if (data != null && data.length > 0){
		for (var i=0;i<data.length;i++){
			loadHtml(i+1,data[i].paramName,data[i].paramTypeName);
		}
	} else {
		$('#publishBtnDiv').hide();
		$('#baseInfoDiv').hide();
		$('#productParamDiv').hide();
		
		//清空表格内容
		clearTbody('baseInfoTbody');
		$('#productParamTbody').empty();
	}
}

/**
 * 加载html
 * @param paramName 参数名称
 * @param paramTypeName 参数类型
 */
function loadHtml(idx,paramName, paramTypeName){
	var html='<tr><td class="tc">'+idx+'</td><td class="tc">'+paramName+'</td><td class="tc">'+paramTypeName+'</td></tr>';
    $("#productParamTbody").append(html);
}


/**
 * 加载checkbox
 * @param checkedVal 判断选中的值
 */
function loadcheckbox(checkedVal){
	var html="";
	for (var i =0;i<typesObj.length;i++){
		 if (checkedVal == 1 && typesObj[i].code == 'PRODUCT'){
			 html+= typesObj[i].name + ',';
		 } else if (checkedVal == 2 && typesObj[i].code == 'SALES'){
			 html+= typesObj[i].name + ',';
		 }else if (checkedVal == 3){
			 html+= typesObj[i].name + ',';
		 }
	}
    $("#baseInfoTbody td.type").append(html.substring(0,html.length-1));
}

/**
 * 获取当前节点的根节点
 * @param treeNode treeNode节点
 * @returns 当前节点
 */
function getCurrentRoot(treeNode){  
	if(treeNode.getParentNode()!=null){  
		var parentNode = treeNode.getParentNode(); 
		return getCurrentRoot(parentNode);
	} else {
		return treeNode;
	}
}