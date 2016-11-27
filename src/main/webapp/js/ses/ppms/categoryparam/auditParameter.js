
var typesObj;
$(function(){
	var datas;
	var setting={
		    async:{
					autoParam:["id","name"],
					enable:true,
					url: globalPath + "/auditParams/initTree.do",
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
			    view:{
			        selectedMulti: false,
			        showTitle: false,
			   }
		};
		//初始化tree
	    $.fn.zTree.init($("#ztree"),setting,datas); 
	    $("#uListId").hide();
	    //初始化类型
	    typesObj = initTypes();
});

/**
 * 点击tree
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event,treeId,treeNode){
	if (treeNode.pId !=0) {
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
	
	var publishStatus = treeNode.pubStatus;
	if (publishStatus != null && publishStatus != ""){
		loadRadioHtml(publishStatus);
	}
	
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
	if (data != null && data.length > 0){
		for (var i=0;i<data.length;i++){
			loadHtml(data[i].paramName,data[i].paramTypeName);
		}
	} else {
		$("#uListId").hide();
	}
}

/**
 * 加载html
 * @param paramName 参数名称
 * @param paramTypeName 参数类型
 */
function loadHtml(paramName, paramTypeName){
	var html ="<li>"
             + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\">" + paramName +"</div>"
             + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\"> 参数类型: " + paramTypeName + "</div>" 
             +"</li>"
    $("#uListId").append(html);
}

/**
 * 加载是否公开
 * @param checked
 */
function loadRadioHtml(checked){
	var  yes_checked = false,no_checked = false;
	if (checked == 0){
		yes_checked = true;
	}
	if (checked == 1){
		no_checked = true;
	}
	var html = "<li> "
		     + " <div class='col-md-4 col-sm-4 col-xs-6 tr'> "
		     + "    <span class='red'>*</span>是否公开: "
		     + " </div> "
		     + " <div class='col-md-8 col-sm-8 col-xs-6'> "
		     + "  <input type='radio' disabled='disabled' checked="+yes_checked+"   name='isOPen'  value='0'/>是    " 
		     + "  <input type='radio' disabled='disabled' checked="+no_checked+"  name='isOPen' value='1' /> 否    "
		     + "</div> "
		     + "</li>";
	$("#uListId").append(html);
}

/**
 * 加载checkbox
 * @param checkedVal 判断选中的值
 */
function loadcheckbox(checkedVal){
	
	var html = "<li  id='typeId'>"
        + " <div class='col-md-4 col-sm-4 col-xs-5 tr'>"
     	 + "  <span class='red'>*</span>类型: "
     	 + " </div>"
		 + " <div class='col-md-8 col-sm-8 col-xs-7'>";
	for (var i =0;i<typesObj.length;i++){
		 if (checkedVal == 1 && typesObj[i].code == 'PRODUCT'){
			 html+="<input name='smallClass' type='checkbox' checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 } else if (checkedVal == 2 && typesObj[i].code == 'SALES'){
			 html+="<input name='smallClass' type='checkbox' checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 }else if (checkedVal == 3){
			 html+="<input name='smallClass' type='checkbox' checked='checked' value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 } else {
			 html+="<input name='smallClass' type='checkbox'  value='"+typesObj[i].code+"' />" +typesObj[i].name;
		 }
		
	}
   html+= "</div></li>";
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
