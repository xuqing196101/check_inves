//类型初始化
var typesObj;
$(function(){
	var datas;
	var setting={
		    async:{
					autoParam:["id","name"],
					enable:true,
					url: globalPath + "/publish/initTree.do",
					dataType:"json",
					type:"post",
				},
				callback:{
			    	onClick:zTreeOnClick,
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
 * 点击tree
 * @param event
 * @param treeId
 * @param treeNode
 */
var selectedTreeId = null;
function zTreeOnClick(event,treeId,treeNode){
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
	$("#uListId").show();
	$("#uListId").empty();
	
	$.ajax({
		type:"post",
		dataType:"json",
		data:{'cateId':cateId},
		async:false,
		url: globalPath + "/cateParam/params.do" ,
		success:calledback
	});
	
	
	//加载类型
	var root = getCurrentRoot(treeNode);
	if (root.classify != null && root.classify == 'GOODS'){
		loadcheckbox(treeNode.classify);
	} 
	
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
	var html="<div class='content table_box'>";
		html+="<table class='table table-bordered table-condensed table-hover table-striped' >";
		html+="<tr><td class='info'>参数名称</td><td class='info'>参数类型</td><td class='info'>是否必填</td></tr>";
	
	if (data != null && data.length > 0){
		for (var i=0;i<data.length;i++){
			loadHtml(data[i].paramName,data[i].paramTypeName);
		}
	} else {
		$("#uListId").hide();
	}
	html+="</table>";
	html+="</div>";
}

/**
 * 加载html
 * @param paramName 参数名称
 * @param paramTypeName 参数类型
 */
function loadHtml(paramName, paramTypeName){
	var html="<tr>" +
	"<td width=\"23%\" class=\"info\">参数名称：</td>" +
	"<td width=\"23%\">"+paramName+"</td>" +
	"<td width=\"23%\" class=\"info\">参数类型：</td>" +
	"<td width=\"23%\" >"+paramTypeName+"</td>" +
	"</tr>";
    $("#uListId").append(html);
}

/**
 * 加载checkbox
 * @param checkedVal 判断选中的值
 */
function loadcheckbox(checkedVal){
	
/*	var html = "<li  id='typeId'>"
             + " <div class='col-md-4 col-sm-4 col-xs-5 tr'>"
     	     + "  <span class='red'>*</span>类型: "
     	     + " </div>"
		     + " <div class='col-md-8 col-sm-8 col-xs-7'>";*/
	var html="<tr>" +
	"<td   width=\"28%\" class=\"info\">" +
	"<span class='red'>*</span>类型：" +
	"</td>" +
	"<td  >";
	for (var i =0;i<typesObj.length;i++){
		 if (checkedVal == 1 && typesObj[i].code == 'PRODUCT'){
			 html+="<input name='smallClass' type='checkbox' disabled='disabled' checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 } else if (checkedVal == 2 && typesObj[i].code == 'SALES'){
			 html+="<input name='smallClass' type='checkbox' disabled='disabled' checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 }else if (checkedVal == 3){
			 html+="<input name='smallClass' type='checkbox' disabled='disabled' checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 } else {
			 html+="<input name='smallClass' type='checkbox' disabled='disabled' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 }
		
	}
   /*html+= "</div></li>";*/
	html+="</td><td  width=\"28%\" >" +
	"</td>" +
	"<td  width=\"28%\" ></td></tr>"; 
  $("#uListId").append(html);
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