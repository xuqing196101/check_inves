$(function(){
	var datas;
	var setting={
	    async:{
				autoParam:["id","name"],
				enable:true,
				url: globalPath + "/cateParam/initTree.do",
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
		    }
	};
	//初始化tree
    $.fn.zTree.init($("#ztree"),setting,datas); 
    //初始化参数类型
    initTypes();
});

var paramTypHtml = "";

function initTypes(){
	$.ajax({
		type:"post",
		dataType:"json",
		url: globalPath + "/cateParam/initTypes.do" ,
		success:function(data){
			loadPramTypes(data);
		}
	});
}

function loadPramTypes(data){
	if (data && data.length > 0){
		for (var i = 0;i<data.length;i++){
			paramTypHtml +="<option value='"+ data[i].id +"'>"+ data[i].name +"</option>"
		}
	}
}


var selectedTreeId = null;
/**点击事件*/
function zTreeOnClick(event,treeId,treeNode){
	if (treeNode.pId !=0) {
		selectedTreeId = treeNode.id;
	} else {
		selectedTreeId = null;
	}
}

/**
 * 新增
 */
function addParams(){
	if (selectedTreeId != null){
		loadHtml();
	} else {
		layer.msg("请选择一个品目,进行添加参数");
	}
}

/**
 * 组装html
 */
function loadHtml(){
	var html ="<li>"
	        + "  <div class=\"col-md-1 padding-10  fl\">"
	        + "   <div class=\"col-md-3 tc h60 fl\"><input name='chkItem' type=\"checkbox\"/></div>"
	        + "  </div>"
	        + "  <div class=\"col-md-6 fl\">参数名称：<input name=\"paramName\" type=\"text\" id=\"topic\" /></div>"
	        + "  <div class=\"col-md-5 fl\">参数类型：<select name=\"paramTypeId\">"
	        if (paramTypHtml != '') {
	        	html+= paramTypHtml
	        }
			+ "   </select> " 
			+ "  </div>" 
			+ "  <div class=\"clear\"></div> " 
			+ "</li>"
	$("#uListId").append(html);
}


/**
 * 删除
 */
function delParams(){
	$('input[name="chkItem"]:checked').each(function(){ 
		$(this).parents("li").remove();
	}); 
}

function saveParams(){
	
}
