$(function() {
	// 地区
	$.ajax({
		url : globalPath + "/area/listByOne.do",
		dataType : "json",
		success : function(obj) {
			$.each(obj, function(i, result) {
				$("#province").append("<option value='" + result.id + "'>" + result.name + "</option>");
			});
		}
	});
	//加载地区树
	loadAreaZtree();
});

// 加载地区
function functionArea() {
	var parentId = $("#province").val();
	$.ajax({
		url : globalPath + "/area/find_by_parent_id.do",
		data : {
			"id" : parentId
		},
		dataType : "json",
		async : false,
		success : function(response) {
			$("#city").empty();
			$("#city").append("<option value=''>选择地区</option>");
			$.each(response, function(i, result) {
				$("#city").append("<option value='" + result.id + "'>" + result.name + "</option>");
			});
		}
	});
}


//人工抽取
function artificial_extracting(){
	$.ajax({
		url : globalPath + "/extractExpert/saveProjectInfo.do",
		data : $('#form').serialize(),
		dataType : "json",
		async : false,
		success : function() {
			layer.msg("操作成功", {offset: '100px'});
		},
		error: function () {
            layer.msg("操作失败", {offset: '100px'});
        }
	});
}

//添加人员信息
function addPerson(k){
	if(k == 1){
		//添加抽取人员
		var i = parseInt($("#extractPerson").find('tr').last().children("td").eq(1).text()) + 1;
		var info = "<tr>"+
		"<td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem1' value='' /></td>"+
		"<td class='tc'>"+i+"</td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"</tr>";
		$("#extractPerson").append(info);
	}else if(k == 2){
		//添加监督人员
		var i = parseInt($("#supervisesPerson").find('tr').last().children("td").eq(1).text()) + 1;
		var info = "<tr>"+
		"<td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem2' value='' /></td>"+
		"<td class='tc'>"+i+"</td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"<td><input value = ''></td>"+
		"</tr>";
		$("#supervisesPerson").append(info);
	}
}

//删除人员信息
function deletePerson(k){
	layer.confirm('您确定要删除吗?', {
		title: '提示',
		offset: ['222px', '360px'],
		shade: 0.01
	}, function(index) {
		layer.close(index);
		var count = 0;
		var value = 0;
		$('input:checkbox[name=chkItem'+k+']:checked').each(function(){
			if(count == 0){
				value = parseInt($(this).parent().next().text());
			}
			count ++;
			$(this).parent().parent().remove();
		});
		if(count != 0){
			var id = "extractPerson";
			if(k == 2){
				id = "supervisesPerson";
			}
			$("#"+id).find('tr').each(function(t){
				if(parseInt($("#"+id).find('tr').eq(t).children("td").eq(1).text()) > value){
					$("#"+id).find('tr').eq(t).children("td").eq(1).text(value);
					value ++;
				}
			});
		}else{
			layer.msg("请选择人员");
		}
	});
}

//加载地区树形结构
function loadAreaZtree(){
	var treeNodes; 
	 var setting = {
      async: {
        autoParam: ["id=area"],
        enable: true, 
        url: globalPath+"/SupplierExtracts/city.do",
        dataType: "json",
        type: "post",
      },
      check: {
        enable: true,
        chkboxType: {
          "Y": "s",
          "N": "ps"
        },
        chkStyle : "checkbox" 
       // autoCheckTrigger: true
      },
      data: {
        simpleData: {
          enable: true,
          idKey: "id",
          pIdKey: "parentId"
        },
        key: {
			children: "nodes"
		}
      },
      callback: {
           // beforeCheck: beforeClickArea,
            onCheck: choseArea,
            onAsyncSuccess:selectAllArea
      },
      view: {
            dblClickExpand: false
      }        
    };
    treeArea = $.fn.zTree.init($("#treeArea"), setting, treeNodes);
}

//显示地区树
function showTree(){
	var areaObj = $("#area");
    var areaOffset = $("#area").offset();
    $("#areaContent").css({
        left: areaOffset.left + "px",
        top: areaOffset.top + areaObj.outerHeight() + "px"
    }).slideDown("fast");
     $("body").bind("mousedown", onBodyDownArea);
}

//默认选中全国
function selectAllArea(){
	var treeObj=$.fn.zTree.getZTreeObj("treeArea");
	treeObj.checkAllNodes(true);
	showCheckArea(treeObj);
	
}

//地区树选中处理
function showCheckArea(treeObj){
	var areas=treeObj.getCheckedNodes(true);
    //省，直辖市
   	var pids = "";
   	//二级 市 区
   	var ids = "";
   	var idArr = new Array();
   	var names = "";
   	
   	for(var i=0; i<areas.length;i++){
   		if(areas[i].isParent){
			pids += areas[i].id + ",";
			names += areas[i].name + ",";
			idArr.push(areas[i].id);
			if(areas[i].id == "0"){
				break;
			}
   		}else{
   			var flag = true;
   			
   			for(var v=0;v<idArr.length;v++){
   				if(areas[i].parentId == idArr[v]){
   					flag = false;
   					break;
   				}
			}
   			
   			if(flag){
   				ids += areas[i].id + ",";
   				names += areas[i].name + ",";
   			}
   		}
   	}
	$("#province").val(pids.substring(0,pids.lastIndexOf(",")));
	$("#addressId").val(ids.substring(0,ids.lastIndexOf(",")));
	$("#area").val(names.substring(0,names.lastIndexOf(",")));
}

//递归取消父节点选中状态
function dischecked(treeNode,treeObj){
	var node = treeNode.getParentNode();
	if(null !=node){
		treeObj.checkNode(node, false);
		dischecked(node,treeObj);
	}
}
//获取选中节点地区
function choseArea(event,treeId,treeNode){
	var treeObj=$.fn.zTree.getZTreeObj("treeArea");
	dischecked(treeNode,treeObj);
	showCheckArea(treeObj);
}

//地区树绑定事件
function onBodyDownArea(event) {
    if (!(event.target.id == "menuBtn" || $(event.target).parents("#areaContent").length > 0)) {
        hideArea();
    }
}

function hideArea() {
    $("#areaContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownArea);
    selectLikeSupplier();
}