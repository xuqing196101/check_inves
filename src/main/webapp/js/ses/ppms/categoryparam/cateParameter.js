
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
	if (treeNode.pId !=0) {
		var root = getCurrentRoot(treeNode);
		if (root.classify != null && root.classify == 'GOODS'){
			$("#typeId").show();
			classified = true;
		} else {
			$("#typeId").hide();
			classified = false;
		}
		
		var pubStatus = treeNode.pubStatus ;
		if (pubStatus != null && pubStatus !=""){
			$("input[name='isOPen']").each(function(){
				var value = $(this).val();
				if (pubStatus == value){
					$(this).attr('checked','checked');
				}
			});
		}
		
		selectedTreeId = treeNode.id;
		findParams(selectedTreeId);
	} else {
		selectedTreeId = null;
	}
}


/**
 * 新增
 */
function addParams(){
	if (selectedTreeId != null){
		itemId = "";
		$('input[name="paramName"]').val("");
		$('select[name="paramTypeId"]').val("");
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
	
	$.ajax({
		url: globalPath + "/cateParam/save.do" ,
		type:"post",
		data:{'name': paramName, 'type' : paramTypeId,'orgId':orgId, 'cateId': selectedTreeId,'id' : itemId},
		success:function(res){
			if (res.result) {
				layer.msg("保存成功");
				findParams(selectedTreeId);
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
	$("input[name='isOPen']:checked").each(function(){
		isOpen = $(this).val();
	});
	if (classified){
		$("input[name='smallClass']:checked").each(function(){
			smallClassify.push($(this).val());
		});
		if (smallClassify.length == 0){
			layer.msg("类型不能为空");
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
					$(this).parents("li").remove();
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
function findParams(cateId){
	
	$("#uListId").empty();
	
	$.ajax({
		type:"post",
		dataType:"json",
		data:{'cateId':cateId},
		url: globalPath + "/cateParam/params.do" ,
		success:calledback
	});
}

/**
 * 加载参数数据
 * @param data
 */
function calledback(data){
	
	if (data != null && data.length > 0){
		for (var i=0;i<data.length;i++){
			loadHtml(data[i].id,data[i].paramName,data[i].paramTypeName);
		}
	}
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
		  area: ['500px','200px'],
		  offset:['100px','400px'],
		  content: $("#openDiv")
		});
}

/**
 * 组装html
 */
function loadHtml(id,paramName, paramTypeName){
	
	var html ="<li>"
	        + "  <div class=\"col-md-1 col-xs-6 col-sm-4 tc\">"
	        + "    <input name='chkItem' value='"+id+"' type=\"checkbox\" class=\"mt10\"/>"
	        + "  </div>"
	        + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\">" + paramName +"</div>"
	        + "  <div class=\"col-md-5 col-xs-12 col-sm-4 tl\"> 参数类型: " + paramTypeName + "</div>" 
	        +"</li>"
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




