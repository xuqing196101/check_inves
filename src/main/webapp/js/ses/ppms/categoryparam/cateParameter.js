
var typesObj = null;

$(function(){
	var datas;
	var orgId = $("#orgId").val();
	var setting={
	    async:{
				autoParam:["id","name"],
				enable:true,
				url: globalPath + "/cateParam/initTree.do?orgId=" + orgId,
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
    hidden();
    typesObj = initTypes();
});


/** 选中的treeId  **/
var selectedTreeId = null;
/**
 * 编辑
 */
var itemId = "";



/**
 * 点击tree
 */
var classified = false;
function zTreeOnClick(event,treeId,treeNode){
	if(treeNode.isParent==true){
		return false;
	}
	$('#name').empty();
	if (treeNode.pId !=0) {
		showul();
		selectedTreeId = treeNode.id;
		var currentObj = reloadCurrentNode(selectedTreeId);
		if (currentObj != null){
			treeNode.classify = currentObj.classify +  "";
			treeNode.pubStatus = currentObj.isPublish;
			treeNode.status = currentObj.paramStatus;
			treeNode.auditDate = currentObj.auditDate;
			treeNode.auditAdvise = currentObj.auditAdvise;
		}
		if(treeNode.status>=2){
			$("#submitId").hide();
		}
		findParams(selectedTreeId,treeNode);
		
	} else {
		selectedTreeId = null;
		hidden();
	}
}


/**
 * 新增
 */
function addParams(){
	if (selectedTreeId != null){
		itemId = "";
		var msg = getStatus('add');
		if (msg !=null){
		  if (msg != "ok"){
			  layer.msg(msg);
			  return false;
		  }
		}
		$('input[name="paramName"]').val("");
		$('select[name="paramTypeId"]').find("option:eq(0)").attr('selected',true);
		openDiv();
	} else {
		layer.msg("请选择一个品目,进行添加参数");
	}
}

/**
 * 编辑
 */
function editParams(){
	var chekedId = "";
	itemId = "";
	var count = 0;
	
	var msg = getStatus('edit');
	if (msg != null){
	  if (msg != "ok"){
		  layer.msg(msg);
		  return false;
	  }
	}
	
	$('input[name="chkItem"]:checked').each(function(){ 
		count ++;
		chekedId = $(this).val();
	}); 
	
	if (count == 1 && chekedId != ""){
		itemId = chekedId;
		getCategory(chekedId);
	} else {
		layer.msg("请选择一个参数项进行编辑");
	}
}

/**
 * 删除
 */
function delParams(){
	
	var msg = getStatus('del');
	if (msg != null){
	  if (msg != "ok"){
		  layer.msg(msg);
		  return false;
	  }
	}
	
	var ids = [];
	$('input[name="chkItem"]:checked').each(function(){ 
		ids.push($(this).val());
	}); 
	if (ids.length > 0){
		delParameter(ids.toString());
	} else {
		layer.msg("请选择一个或多个参数项进行删除");
	}
	
}


/**
 * 保存
 */
function saveParameter(){
	
	var orgId = $("#orgId").val();
	var paramName = $('input[name="paramName"]').val();
	var paramTypeId = $('select[name="paramTypeId"]').val();
	var paramRequired = $('select[name="paramRequired"]').val();
	var treeObj=$.fn.zTree.getZTreeObj("ztree");  
	var treeNode = treeObj.getNodeByParam("id",selectedTreeId, null);
	
	$.ajax({
		url: globalPath + "/cateParam/save.do" ,
		type:"post",
		data:{'name': paramName, 'type' : paramTypeId,'orgId':orgId, 'cateId': selectedTreeId,'id' : itemId,"paramRequired":paramRequired},
		success:function(res){
			if (res.result) {
				layer.msg("保存成功");
				findParams(selectedTreeId,treeNode);
				cancel();
			} else {
				layer.msg(res.errorMsg);
			}
		}
	});
}

/**
 * 取消
 */
function cancel(){
	
	layer.closeAll();
}

/**
 * 提交
 */
function submitParams(){
	var isOpen = "";
	var smallClassify = [];
	
	/*var paramLength = $("#uListId > li").length;*/
	
	/*if (classified){
		if (paramLength == 2){
			layer.msg("参数不能为空");
			return ;
		}
	} else {
		if (paramLength == 1){
			layer.msg("参数不能为空");
			return ;
		}
	}*/
	
	/*$("input[name='isOPen']:checked").each(function(){
		isOpen = $(this).val();
	});*/
	if (classified){
		$("input[name='chkItem']:checked").each(function(){
			smallClassify.push($(this).val());
		});
		if (smallClassify==""){
			layer.alert("请选择要提交的参数");
			return false;
		}
	}
	submit(isOpen,smallClassify,selectedTreeId);
}

/**
 * 删除方法调用
 */
function delParameter(id){
	
	$.ajax({
		type:"post",
		data:{'ids':id},
		url: globalPath + "/cateParam/delParamters.do" ,
		success:function(msg){
			if (msg == 'ok'){
				layer.msg("删除成功");
				$('input[name="chkItem"]:checked').each(function(){ 
					$(this).parent().parent().remove();
				});
			}
		}
	});
}


/**
 * 查询一个品目参数
 */
function getCategory(id){
	
	$.ajax({
		type:"post",
		dataType:"json",
		data:{'id':id},
		url: globalPath + "/cateParam/edit.do" ,
		success:function(data){
			 $('input[name="paramName"]').val(data.paramName);
			 $('select[name="paramTypeId"]').val(data.paramTypeId);
			 $('select[name="paramRequired"]').val(data.paramRequired);
			 openDiv();
		}
	});
}

/**
 * 提交
 */
function submit(isOpen,smallClassify, id){
	$.ajax({
		type:"post",
		data:{'open':isOpen,'classify':smallClassify.toString(),'id':id},
		url: globalPath + "/cateParam/submitParams.do" ,
		success:function(msg){
			if (msg == 'ok'){
				layer.msg("提交成功");
				hiddenAudit();
			} else {
				layer.msg(msg);
			}
		}
	});
}



/**
 * 根据品目ID查询对应的参数
 * @param cateId 品目ID
 */
function findParams(cateId, treeNode){
	
	$("#uListId").empty();
	$('#name').empty();
	$('#check').empty();
	var html="<h2 class='f16 count_flow'><i>01</i>基本信息</h2><div class='content table_box'><table class='table table-bordered'>";
	html+="<tr id='trs'>";
	html+="<td class='bggrey w100'>产品目录:</td>";
	html+="<td colspan='3'>"+treeNode.name+"</td>";
	html+="</tr>";
	html+="</table></div>";
	$('#name').append(html);
	$.ajax({
		type:"post",
		dataType:"json",
		data:{'cateId':cateId},
		async: false,
		url: globalPath + "/cateParam/params.do" ,
		success:calledback
	});
	//判断是否公开
	opens(treeNode);
	//判断选中类型

	var root = getCurrentRoot(treeNode);
	if (root.classify != null && root.classify == 'GOODS'){
		selectedClass(treeNode);
		classified = true;
	} else {
		classified = false;
	}
	//加载审核信息
	if (treeNode.auditDate != null){
		loadAuditHtml(treeNode.status,treeNode.auditAdvise);
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



function selectAll(){
	 var checklist = document.getElementsByName ("chkItem");
	 var checkAll = document.getElementById("checkAll");
	 if(checkAll.checked){
		   for(var i=0;i<checklist.length;i++)
		   {
		      checklist[i].checked = true;
		   } 
		 }else{
		  for(var j=0;j<checklist.length;j++)
		  {
		     checklist[j].checked = false;
		  }
	 }
}
/**
 * 加载参数数据
 * @param data
 */
function calledback(data){
	var html="<h2 class='f16 count_flow '><i>02</i>参数信息</h2><div class='content table_box'>";
		html+="<table class='table table-bordered table-condensed table-hover table-striped' >";
		html+="<thead><tr><td class='info w50 tc'><input id='checkAll' type='checkbox' onclick='selectAll()'></td><td class='info tc'>参数名称</td><td class='info tc'>参数类型</td><td class='info tc'>是否必填</td></tr><thead/><tbody>";

	if (data != null && data.length > 0){
		for (var i=0;i<data.length;i++){
			html+=loadHtml(data[i].id,data[i].paramName,data[i].paramTypeName,data[i].paramRequired);
		}
	}
	html+="</tbody></table>";
	html+="</div>";
	$("#uListId").append(html);
}

/**
 * 打开弹出层
 */
function openDiv(){
	
	layer.open({
		  type: 1,
		  title: '新增参数',
		  skin: 'layui-layer-rim',
		  shadeClose: true,
		  area: ['500px','300px'],
		  content: $("#openDiv")
		});
}

/**
 * 组装html
 */
function loadHtml(id,paramName, paramTypeName,paramRequired){
	var html="<tr>" +
	        "<td class='tc'><input name='chkItem' value='"+id+"' type='checkbox' class='mt10'/></td>"+
			"<td class='tc'>"+paramName+"</td>" +
			"<td class='tc' >"+paramTypeName+"</td>" +
			"<td class='tc' >"+(paramRequired==1?"是":"否")+"</td>" +
			"</tr>";
	/*var html ="<li>"
	        + "  <div class=\"col-md-1 col-xs-6 col-sm-4 tc\">"
	        + "    <input name='chkItem' value='"+id+"' type=\"checkbox\" class=\"mt10\"/>"
	        + "  </div>"
	        + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\">" + paramName +"</div>"
	        + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\"> 参数类型: " + paramTypeName + "</div>" 
	        +"</li>"*/
	return html;
	/*$("#uListId").append(html);*/
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

/** 是否公开 */
function opens(treeNode){
	var pubStatus = treeNode.pubStatus ;
	if (pubStatus != null && pubStatus !=""){
		loadRadioHtml(pubStatus);
	} else {
		loadRadioHtml("");
	}
}

/** 
 * 选择类型
 * @param treeNode
 */
function selectedClass(treeNode){
	var classz = treeNode.classify;
	loadCheckbox(classz);
}

/**
 * 加载radiohtml
 * @param redioChecked 选中的值
 */
function loadRadioHtml(redioChecked){
	var pub="";
	if (redioChecked == 0){
		pub="公开";
	}else if(redioChecked == 1){
		pub="不公开";
	}else{
		
	}
	var html="<tr>";
	html+="<td class='bggrey w100'>是否公开：</td>";
	html+="<td id='tds'>"+pub+"</td>";
	html+="</tr>";
	/*var html = "<li> "
		     + "  <div class='col-md-4 col-sm-4 col-xs-6 tr'>"
		     + "    <span class='red'>*</span>是否公开:"
		     + " </div>"
		     + " <div class='col-md-8 col-sm-8 col-xs-6'> ";*/
	/*var html="<tr>" +
			"<td colspan=\"2\" width=\"20%\" class=\"info\">" +
			"<span class='red'>*</span>是否公开：" +
			"</td>" +
			"<td  colspan=\"5\">";
	if (redioChecked == 0){
		html +=  " <input type='radio' checked='checked' name='isOPen'  value='0'/>是     ";
		html +=  " 　　<input type='radio'  name='isOPen'  value='1'/>否";
	} else if (redioChecked == 1){
		html +=  " <input type='radio'  name='isOPen'  value='0'/>是     ";
		html +=  " 　　<input type='radio' checked='checked' name='isOPen'  value='1'/>否";
	} else {
		html +=  " <input type='radio'  name='isOPen'  value='0'/>是     ";
		html +=  " 　　<input type='radio'  name='isOPen'  value='1'/>否";
	}
		html+= " </div>"
		html+= " </li>";
		html+= " </td></tr>";*/
	$(html).insertAfter($("#trs"));
}

/***
 * 加载types对应的checkbox
 * @param checkedVal type值
 */
function loadCheckbox(checkedVal){
	var html="";
	html+="<td class='bggrey w100'>类型：</td>";
	html+="<td>";
	/*var html = "<li  id='typeId'>"
	         + " <div class='col-md-4 col-sm-4 col-xs-5 tr'>"
	      	 + "  <span class='red'>*</span>类型: "
	      	 + " </div>"
	 		 + " <div class='col-md-8 col-sm-8 col-xs-7'>";*/
	/*var html="<tr>" +
			"<td  colspan=\"2\" width=\"20%\" class=\"info\">" +
			"<span class='red'>*</span>类型：" +
			"</td>" +
			"<td  colspan=\"5\">";*/
	 var types="";
	 for (var i =0;i<typesObj.length;i++){
		 if (checkedVal == 1 && typesObj[i].code == 'PRODUCT'){
			 types+=typesObj[i].name+",";
		 } else if (checkedVal == 2 && typesObj[i].code == 'SALES'){
			 types+=typesObj[i].name+",";
		 }else if (checkedVal == 3){
			 types+=typesObj[i].name+",";
		 } else {
			 types+=typesObj[i].name+",";
		 }
		
	 }
	 if(types.length>0){
		 types=types.substring(0, types.length-1);
	 }
	 html+=types;
	 html+="</td>";
	   /* html+= "</div></li>";*/
	 /*html+= " </td></tr>";*/
	  $(html).insertAfter($("#tds"));
}

/**
 * 审核信息
 * @param auditStatus
 * @param auditAdvise
 */
function loadAuditHtml(auditStatus,auditAdvise){
	var statusText = "";
	if (auditStatus == 1){
		statusText = "不通过";
	}
	if (auditStatus == 3){
		statusText = "通过";
	}
	
	if (statusText !=""){
		var html="<h2 class='f16 count_flow'><i>03</i>审核信息</h2><div class='content table_box'><table class='table table-bordered'>";
		html+="<tr>";
		html+="<td class='bggrey w100'>审核状态:</td>";
		html+="<td colspan='3'>"+statusText+"</td>";
		html+="</tr>";
		html+="<tr >";
		html+="<td class='bggrey w100'>审核意见:</td>";
		
		/*var html = "<li id='auditId'>"
			  + " <div class='col-md-4 col-sm-4 col-xs-5 tr'>"
			  + "  审核状态: " 
			  + " </div>"
			  + " <div class='col-md-8 col-sm-8 col-xs-7'>"
	          + statusText ;
			  + " </div>"
			  + "</li>";
		var html="<tr>" +
					"<td width=\"20%\" colspan=\"2\" class=\"info\">" +
					"审核状态：" +
					"</td>" +
					"<td width=\"15%\">"+statusText+"</td>";
		html+="<td width=\"15%\" class=\"info\">" +
 		"审核意见：" +
 		"</td>"; 
		if (auditAdvise !=null && auditAdvise != ""){
			 html += "<li id='adviseId'>";
			   html += "<div class='col-md-12 col-sm-4 col-xs-5 tr'>";
			   html	+= "  审核意见: "  + auditAdvise ;
			   html += "</div>"
		     html += "</li>"
			 
			 html+="<td  colspan=\"3\">" +auditAdvise+"</td>";
		 }else{
			 html+="<td  colspan=\"3\"></td>";
		 }*/
		html+="<td colspan='3'>"+auditAdvise+"</td>";
		html+="</tr>";
		html+="</table></div>";
		$('#check').append(html);
		if ($("#check")){
			$("#check").show();
		}
	}
}

/**
 * 隐藏审核信息
 */
function hiddenAudit(){
	if ($("#check")){
		$("#check").hide();
	}
	if($("#submitId")){
		$("#submitId").hide();
	}
	
}

/**
 * 默认隐藏的项
 */
function hidden(){
	$("#uListId").hide();
	$("#submitId").hide();
}

/**
 * 显示
 */
function showul(){
	$("#uListId").show();
	$("#submitId").show();
}

/**
 * 重新加载当前选中的节点
 * @param id treeId
 */
function reloadCurrentNode(id){
	var currentNode;
	$.ajax({
		  dataType:"json",
		  type:"POST",
		  data:{'id':id},
		  async: false,
	  	  url:  globalPath + "/category/update.do",
	      success:function(data){
	    	  currentNode = data;
	  	  }
	  });
	return currentNode;
}

/**
 * 获取当前的状态
 * @param opera 操作类型
 * @returns {String}
 */
function getStatus(opera){
	var statusMsg = null;
	$.ajax({
		  type:"POST",
		  data:{'id':selectedTreeId,'opera':opera},
		  async: false,
	  	  url:  globalPath + "/cateParam/getStatus.do",
	      success:function(msg){
	    	  statusMsg = msg;
	  	  }
	  });
	return statusMsg;
}


