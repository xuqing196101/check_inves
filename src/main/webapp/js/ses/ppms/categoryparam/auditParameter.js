//类型初始化
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
	    //控制按钮
	    hiddenParams();
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
		selectedTreeId = null;
		hiddenParams();
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
	if (publishStatus != null){
		loadRadioHtml(publishStatus);
	}
	
	var root = getCurrentRoot(treeNode);
	if (root.classify != null && root.classify == 'GOODS'){
		loadcheckbox(treeNode.classify);
	} 
	if (treeNode.status >= 2){
		showParams();
	} else {
		hiddenParams();
	}
	loadAuditValue(treeNode);
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
			loadHtml(data[i].paramName,data[i].paramTypeName,data[i].paramRequired);
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
function loadHtml(paramName, paramTypeName,paramRequired){
	/*var html ="<li>"
             + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\">" + paramName +"</div>"
             + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\"> 参数类型: " + paramTypeName + "</div>" 
             +"</li>"*/
	var html="<tr>" +
			"<td width=\"15%\" class=\"info\">参数名称：</td>" +
			"<td width=\"15%\">"+paramName+"</td>" +
			"<td width=\"15%\" class=\"info\">参数类型：</td>" +
			"<td width=\"15%\" >"+paramTypeName+"</td>" +
			"<td width=\"15%\" class=\"info\">是否必填：</td>" +
			"<td width=\"15%\" >"+(paramRequired==1?"是":"否")+"</td>" +
			"</tr>";
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
	/*var html = "<li> "
		     + " <div class='col-md-4 col-sm-4 col-xs-6 tr'> "
		     + "    <span class='red'>*</span>是否公开: "
		     + " </div> "
		     + " <div class='col-md-8 col-sm-8 col-xs-6'> ";*/
	var html="<tr>" +
			"<td width=\"15%\" class=\"info\">" +
			"<span class='red'>*</span>是否公开：" +
			"</td>" +
			"<td  colspan=\"5\">";
	if (yes_checked){
		html += "  <input type='radio' disabled='disabled' checked='checked'   name='isOPen'  >是    " 
		html += "  <input type='radio' disabled='disabled'    name='isOPen'  /> 否    "
	}
	if (no_checked){
		html += "  <input type='radio' disabled='disabled'    name='isOPen'  >是    " 
		html += "  <input type='radio' disabled='disabled'  checked='checked'  name='isOPen'  /> 否    "
	}
		
/*	html+=  "</div> "
	html+= "</li>";*/
	html+= " </td></tr>";
	$("#uListId").append(html);
}

/**
 * 加载checkbox
 * @param checkedVal 判断选中的值
 */
function loadcheckbox(checkedVal){
	
	/*var html = "<li  id='typeId'>"
             + " <div class='col-md-4 col-sm-4 col-xs-5 tr'>"
     	     + "  <span class='red'>*</span>类型: "
     	     + " </div>"
		     + " <div class='col-md-8 col-sm-8 col-xs-7'>";*/
	var html="<tr>" +
			"<td width=\"15%\" class=\"info\">" +
			"<span class='red'>*</span>类型：" +
			"</td>" +
			"<td  colspan=\"5\">";
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
	html+= " </td></tr>";
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

/**
 * 审核参数
 */
function auditParams(){
	var status = $("select[name='auditStatus']").val();
	var text  = $("#textId").val();
	
	if (status == 1){
		if (text == ''){
			layer.msg("审核意见不能为空");
			return ;
		}
	}
	
	if (selectedTreeId == null){
		layer.msg("请选择需要审核的品目");
		return ;
	}
	audit(status,text);
}

/**
 * 审核请求
 * @param status 状态
 * @param advise 意见
 */
function audit(status,advise){
	if (selectedTreeId != null){
		$.ajax({
			  dataType:"json",
			  type:"POST",
			  data:{'id':selectedTreeId,'status':status,'advise':advise},
			  async: false,
		  	  url:  globalPath + "/auditParams/audit.do",
		      success:function(data){
		    	  getResult(data);
		  	  }
		});
	}
}

/**
 * 获取更新状态
 * @param data 返回数据
 */
function getResult(data){
	if (data.result){
		layer.msg("提交成功");
		updateTreeNode(data.obj);
	} else {
		layer.msg(data.errorMsg);
	}
}

/**
 * 更新选中的treeNode
 * @param obj
 */
function updateTreeNode(obj){
	if (obj != null){
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		var nodes = zTree.getSelectedNodes();
		if (nodes!= null){
			var node = nodes[0];
			node.status = obj.paramStatus;
			
			if (obj.paramStatus == 1){
				refreshParentNode();
				hiddenParams();
			}else{
				
				loadAuditValue(obj);
			}
		}
	}
}


/**
 * 刷新父级节点
 */
function refreshParentNode() {  
	   var zTree = $.fn.zTree.getZTreeObj("ztree"),
	   type = "refresh", 
	   silent = false,  
	   nodes = zTree.getSelectedNodes();
	   //var nodexs=nodes[0].getParentNode().getParentNode().getParentNode().getParentNode();
	   var parentNode = zTree.getNodeByTId(nodes[0].parentTId); 
	   zTree.reAsyncChildNodes(parentNode, type, silent);  
}

/**
 * 加载选中的值
 * @param treeNode
 */
function loadAuditValue (treeNode){
	if(treeNode.status == 3||treeNode.paramStatus==3){
		$("#urlId").hide();
		var auditAdvise="";
		if (treeNode.auditAdvise !=null && treeNode.auditAdvise != ""){
			auditAdvise=treeNode.auditAdvise;
		}
	   var htmls="<tr>" +
	   		"<td width=\"23%\" class=\"info\">审核状态：</td>" +
	   		"<td width=\"23%\" >已审核</td>" +
	   		"<td width=\"23%\" class=\"info\">审核意见：</td>" +
	   		"<td width=\"23%\" >"+auditAdvise+"</td>" +
	   		"</tr>";
	   $("#tableId").html(htmls);
	   $("#tableId").show();
	}else{
		$("#tableId").hide();
		$("#urlId").show();
	}
	/*if (treeNode.status == 1 || treeNode.status == 3){
		$("select[name='auditStatus']").val(treeNode.status);
	} else {
		$("select[name='auditStatus'] option:first").prop("selected","selected");
	}
	
	if (treeNode.auditAdvise != null && treeNode.auditAdvise != ""){
		$("#textId").val(treeNode.auditAdvise);
	} else {
		$("#textId").val("");
	}*/
}

/**
 * 
 * @param obj 当前对象
 */
function loadAuditText(obj){
	var status = $(obj).val();
	if (status == 1){
		$("#markId").show();
	} else {
		$("#markId").hide();
	}
}

/**
 * 隐藏
 */
function hiddenParams(){
	$("#baseParamId").hide();
	$("#auditParamId").hide();
	$("#auditBtnId").hide();
	$("#markId").hide();
}

/**
 * 显示
 */
function showParams(){
	$("#baseParamId").show();
	$("#auditParamId").show();
	$("#auditBtnId").show();
}