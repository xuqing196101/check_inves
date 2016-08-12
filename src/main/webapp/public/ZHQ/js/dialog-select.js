// 超高时自动出现y方向滚动条
function overflow_y_auto(id) {
  var div = $('#' + id);
  var h = 360;
  if (div.prop("scrollHeight") > h && !div.hasClass("overflow_y_auto")) {
    div.addClass("overflow_y_auto");
  }else if(div.prop("offsetHeight") == div.prop("scrollHeight") && div.hasClass("overflow_y_auto")){
    div.removeClass("overflow_y_auto");
  }
  var r;
}

// Box选择器
function BoxSelect(d,id,url,chkStyle,params){
  // 插入搜索框
  $("#" + id).append("<div id='filter_" + id + "' class='input-group dialog_filter'><input type='text' placeholder='请输入关键字...' class='form-control input-sm'><span class='input-group-btn'><button type='button' class='btn btn-success btn-sm dialog_filter_btn'>搜索</button></span></div>");
  // 插入树形DIV
  $("#" + id).append("<div id='box_"+ id +"' class='sky-form dialog_box'></div>");
  params = params || {}
  var boxId = "box_" + id;
  initBox(d,boxId,url,chkStyle,params,false);
}

// 树形选择器
function zTreeSelect(d,id,url,chkStyle,params){
  // 插入搜索框
  $("#" + id).append("<div id='filter_" + id + "' class='input-group dialog_filter'><input type='text' placeholder='请输入关键字...' class='form-control input-sm'><span class='input-group-btn'><button type='button' class='btn btn-success btn-sm dialog_filter_btn'>搜索</button></span></div>");
  // 插入树形DIV
  $("#" + id).append("<div id='zTree_"+ id +"' class='ztree'></div>");
  params = params || {}
  var treeId = "zTree_" + id;
  initZtree(d,treeId,url,chkStyle,params,false);
}

// 对话框清空事件
function clearCheckedNodes(id,dialog_type,input_id,partner_id){
	if(dialog_type == 'tree'){
	var treeObj = $.fn.zTree.getZTreeObj("zTree_" + id);
	var nodes = treeObj.getCheckedNodes(true);
	for (var i=0, l = nodes.length; i < l; i++) {
		treeObj.checkNode(nodes[i], false, true);
	}
	}
	else{  
	$('#' + id + ' input:checked').each(function(){
		$(this).attr("checked", false);
	}); 
	}
	$("#" + input_id).val('');
	$("#" + partner_id).val(0);
}

// 显示选择对话框
function showDialog(dom,id,url,dialog_type,chkStyle,params){
	var containerId = "dialog_select";
	if ($("#" + containerId).length == 0){
		$("body").append('<div id="'+ containerId +'" class="invisible"></div>');
	}
	id += "_Dialog";
	var input_id = $(dom).attr("id");
    var partner_id = getPartnerId(input_id);
	dialog_type = dialog_type || "tree";
	chkStyle = chkStyle || "checkbox";
	var d = dialog.get(id);
	var btns = [
	{
		value: '确定',
		autofocus: true,
		callback: function(){
			$("#" + input_id).attr('readonly','readonly');
			this.close();
			return false;
		}
	},
	{
		value: '清空',
		callback: function(){
			$("#" + input_id).attr('readonly','readonly');
			clearCheckedNodes(id,dialog_type,input_id,partner_id);
			this.close();
			return false;
		}
	}
	];
    // 其他选项
    if(!isEmpty(params) && !isEmpty(params["vv_otherchoose"])){
    	btns.push({
    		value: params["vv_otherchoose"],
    		callback: function(){
    			clearCheckedNodes(id,dialog_type,input_id,partner_id);
    			$("#" + input_id).removeAttr("readonly");
    			$("#" + input_id).removeAttr("disabled");
    			this.close();
				return false;
    		}
    	});
    };

    if (!d){
    	d = dialog({
    		id: id,
    		title: '加载中...',
    		follow: dom,
    		quickClose: false,
    		button: btns.reverse(),
			cancel: false
    	});
    }
	d.show();
	if($("#" + id).length == 0){
    	$("#" + containerId).append('<div id="'+ id +'" class="dialog"></div>');
    	// 只传送页面需要的参数，剔除辅助参数
		// for(var s in params){
		// 	if(s.indexOf("vv_") == 0)
		// 	delete params.s
		// }
		// alert(params);
    	if (dialog_type == "tree"){
    		zTreeSelect(d,id,url,chkStyle,params);
    	}else{
    		BoxSelect(d,id,url,chkStyle,params);
    	}
    }
}

// 页面开始加载
$(function(){
  // 树形复选框
  $('body').on("click",".tree_checkbox",function(){
    showDialog(this,$(this).attr("id"),$(this).attr("json_url"),"tree","checkbox",$.parseJSON($(this).attr("json_params")));
  });
  // 树形单选框
  $('body').on("click",'.tree_radio',function(){
    showDialog(this,$(this).attr("id"),$(this).attr("json_url"),"tree","radio",$.parseJSON($(this).attr("json_params")));
  });

  // 弹框复选框
  $('body').on("click",'.box_checkbox',function(){
    showDialog(this,$(this).attr("id"),$(this).attr("json_url"),"box","checkbox",$.parseJSON($(this).attr("json_params")));
  });
  // 弹框单选框
  $('body').on("click",'.box_radio',function(){
    showDialog(this,$(this).attr("id"),$(this).attr("json_url"),"box","radio",$.parseJSON($(this).attr("json_params")));
  });

  // 绑定搜索输入框的回车事件
  $('body').on('keydown','.dialog_filter input',function(event){
    if(event.keyCode==13){
      diaogFilter(this);
    }
  });

  // 绑定搜索按钮的点击事件
  $('body').on('click', '.dialog_filter_btn', function(){
    diaogFilter(this);
  });

  // 弹框单选框选中后触发事件
  $('body').on('click','.dialog_box input[type="radio"]',function(){
    var input_id = $(this).attr("name");
    var partner_id = getPartnerId(input_id);
    var name = $(this).parent().text();
    var id = $(this).val();
    $("#" + input_id).val(name);
    $("#" + partner_id).val(id);
    $("#" + input_id).attr('readonly','readonly');
    var dialog_id = getDialogId(input_id);
    var d = dialog.get(dialog_id);
    if (d){
      d.close();
    }
  });

  // 弹框复选框选中后触发事件
  $('body').on('click','.dialog_box input[type="checkbox"]',function(){
  // 后代全选或者取消全选
  var status = $(this).attr("checked") == "checked" ? true : false;
  $(this).parent().next(".box_select_item").find("input[type='checkbox']").attr("checked",status);
  var input_id = $(this).attr("name");
  var partner_id = getPartnerId(input_id);
  var dialog_id = getDialogId(input_id);
  var limited = $("#" + input_id).attr("limited") == undefined ? 10000 : parseInt($("#" + input_id).attr("limited"));
  var ids = [];
  var names = [];
  var check_count = 0;
  $("#" + dialog_id + " input[type='checkbox']:checked").each(function(){
    // 只取叶子节点
    if ($(this).parent().next(".box_select_item").find("input[type='checkbox']").length == 0){
      check_count += 1;
      if (check_count > limited) {
        setTips("box_" + dialog_id,"选择项不能超过"+ limited +"个！");
      }
      else {
        setTips("box_" + dialog_id,"&nbsp;");
        ids.push($(this).val());
        names.push($(this).parent().text());
      }
    }
  });
  $("#" + input_id).val(names.join(","));
  $("#" + partner_id).val(ids.join(","));
  $("#" + input_id).attr('readonly','readonly');
  });

});

// 从树形ID获取INPUT的ID
function getInputId(id){
  if(id.indexOf("zTree_") == 0 && id.lastIndexOf("_Dialog") > 0){
    return id.substring(6,id.lastIndexOf("_Dialog")); // ztreeID
  }else if(id.indexOf("box_") == 0 && id.lastIndexOf("_Dialog") > 0){
    return id.substring(4,id.lastIndexOf("_Dialog"));  //boxID
  }else{
    return id.substring(0,id.lastIndexOf("_Dialog"));  //dialogID
  }
}

// 从树形ID获得Tips的ID
function getTipsId(id){
  if(id.indexOf("zTree_") == 0 && id.lastIndexOf("_Dialog") > 0){
    return id.replace("zTree_","tips_");
  }else if(id.indexOf("box_") == 0 && id.lastIndexOf("_Dialog") > 0){
    return id.replace("box_","tips_");
  }
}

// 设置提示信息
function setTips(id,tips){
  var tips_id = getTipsId(id);
  if(!document.getElementById(tips_id)){
    $("#" + id).before('<div id="'+ tips_id +'" class="dialog_tips text-red">' + tips + '</div>');
  }else{
    $("#" + tips_id).html(tips);
  }
}

// 从INPUT的ID获得PARTNER的ID
function getPartnerId(id){
  return $("#" + id).attr("partner");
}

// 从INPUT的ID获得dialog的ID
function getDialogId(id){
  return id + "_Dialog";
}

// 获取选择框的类型radio还是checkbox
function getChkStyle(input_id){
  if ($("#" + input_id).hasClass("tree_radio") || $("#" + input_id).hasClass("box_radio")){
    return "radio";
  }else{
    return "checkbox";
  }
}

// 根据值初始化zTree选择框的勾选状态
function initZtreeCheckStatus(treeObj,vArray){
  var node;
  $.each(vArray, function(n, v) {
    node = treeObj.getNodeByParam("id",v);
    if (node){
      treeObj.checkNode(node, true, true)
    }
  });
}

// 根据值初始化Box选择框的勾选状态
function initBoxCheckStatus(boxId,vArray,chkStyle){
  var node;
  $.each(vArray, function(n, v) {
    $("#" + boxId).find("input[type='"+ chkStyle +"'][value='"+ v +"']").attr("checked",true);
  });
}

// 搜索
function diaogFilter(dom){
  var name = $(dom).parents("div:first").children("input").val();
  var dialog_id = $(dom).parents("div.dialog:first").attr("id");
  var input_id = getInputId(dialog_id);
  var url = $("#" + input_id).attr("json_url");
  var params = $.parseJSON($("#" + input_id).attr("json_params"))
  if (params == undefined) {params = {}};
  var d = dialog.get(dialog_id);
  if (!d){
    d = dialog({
      id: dialog_id
    });
  }
  var chkStyle = getChkStyle(input_id);
  // var params = name == undefined ? {} : {'ajax_key' : name};
  if(name != undefined) {params["ajax_key"] = name}
  // 树形搜索
  if ($(dom).parents("div:first").siblings(".ztree").length > 0){
    var treeId = $(dom).parents("div:first").siblings(".ztree").attr("id");
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if (treeObj){
      $.fn.zTree.destroy(treeId);
      $("#" + treeId).html("正在搜索...");
    }
    initZtree(d,treeId,url,chkStyle,params,true);
  }else{
    var boxId = $(dom).parents("div:first").siblings(".dialog_box").attr("id");
    $("#" + boxId).html("正在搜索...");
    initBox(d,boxId,url,chkStyle,params,true);
  }
}

//过滤节点的机制
function filter(node,level) {  
	// levle:-1 表示只可以选叶子节点，其他数字表示N层级以上的节点
	if(level == -1){
		return !node.isParent;
	}
	else{
		return node.level >= level;
	}
}  

///动态设置zTree的所有节点有checkbox  
function updateNoCheckNodes(treeId,level) {  
    var zTree = $.fn.zTree.getZTreeObj(treeId);             
    var nodes = zTree.getNodesByFilter(function(node){  
		// levle:-1 表示只可以选叶子节点，其他数字表示N层级以上的节点
		if(level == -1){
			return node.isParent;
		}
		else{
			return node.isParent && node.level < level;
		}
	}); 
    //遍历每一个节点然后动态更新nocheck属性值  
    for (var i = 0; i < nodes.length; i++){  
        var node = nodes[i];  
        node.nocheck = true; 
        zTree.updateNode(node);  
    }
}

// 初始化树
function initZtree(d,treeId,url,chkStyle,params,filter){
  $.ajax({
    type: 'post',
    url: url,
    dataType: 'json',
    data: params,
    cache: false,
    success: function(data){
      if (data == null) {
        d.content("没有可选项！");
      }else{
        if (data.length == 0){
          $("#" + treeId).html("搜索结果为空，请换个关键字试试。");
        }else{
          var input_id = getInputId(treeId);
          $.fn.zTree.init($("#" + treeId), ztree_setting(chkStyle), data);
          d.title("请选择...");
          var partner_id = getPartnerId(input_id);
          var vArray = $("#" + partner_id).val() == undefined ? [] : $("#" + partner_id).val().split(",");
          var treeObj = $.fn.zTree.getZTreeObj(treeId);
          if (!filter){
            d.content($("#" + getDialogId(input_id)));
          }else{
            treeObj.expandAll(true); //搜索展开全部节点
          }
          //默认勾上已选项
          initZtreeCheckStatus(treeObj,vArray);
          // 设置不可选的节点
          if(!isEmpty(params) && !isEmpty(params["vv_checklevel"])){
          	updateNoCheckNodes(treeId,params["vv_checklevel"])
          }
          // 自动滚动条
          overflow_y_auto(treeId);
          }
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
      // alert(textStatus);
      d.title("抱歉，出错了！");
      d.content("加载失败，错误代码" + textStatus + "，请刷新页面重试。");
    }
  });
}

// 初始化BOX
function initBox(d,boxId,url,chkStyle,params,filter){
  $.ajax({
    type: 'post',
    url: url,
    dataType: 'json',
    data: params,
    cache: false,
    success: function(data){
      if (data == null) {
        d.content("没有可选项！");
      }else{
        if (data.length == 0){
          $("#" + boxId).html("搜索结果为空，请换个关键字试试。");
        }else{
          var input_id = getInputId(boxId);
          d.title("请选择...");
          var partner_id = getPartnerId(input_id);
          var vArray = $("#" + partner_id).val() == undefined ? [] : $("#" + partner_id).val().split(",");
          var arr = convertSimpleData(data);
          var content = '<section><fieldset>';
          $.each(arr, function (index, obj) {
            content += create_box_item(input_id,obj,chkStyle,false);
          });
          content += '</fieldset></section>';
          $("#" + boxId).html(content);
          if (!filter){
            d.content($("#" + getDialogId(input_id)));
          }
          //默认勾上已选项
          initBoxCheckStatus(boxId,vArray,chkStyle);
          // 自动滚动条
          overflow_y_auto(boxId);
        }
      }
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      // alert(textStatus);
      d.title("抱歉，出错了！");
      d.content("加载失败，请刷新页面重试。");
    }
  });
}

// 创建box的选项
function create_box_item(input_id,obj,chkStyle,clear_both){
  chkStyle = chkStyle || "checkbox";
  var iconStyle  = chkStyle == "checkbox" ? '<i></i>' : '<i class="rounded-x"></i>';
  clear_both = clear_both || false;
  var cls = clear_both ? chkStyle + " clear_both" : chkStyle
  var content;
  // 判断是否有孙子,如果有孙子，儿子必须强制换行（即清除float)
  var has_grandson = false;
  if (obj.children != undefined){
    var tmp = $.grep(obj.children,function(child){
      return child.children != undefined
    });
    if (tmp.length > 0){
      has_grandson = true;
    }
  }
  if (obj.children == undefined){
    content = '<label class="' + cls + '"><input type="'+ chkStyle +'" name="'+ input_id +'" value="'+ obj.id +'">' + iconStyle + obj.name +'</label>';
  }
  else{
    content = '<label class="'+ chkStyle +' clear_both"><input type="'+ chkStyle +'" name="'+ input_id +'" value="'+ obj.id +'">' + iconStyle + obj.name +'</label>';
    content += '<div class="box_select_item"><div class="inline-group">';
    $.each(obj.children, function (index, child) {
      content += create_box_item(input_id,child,chkStyle,has_grandson);
    });
    content += '</div></div>';
  }
  return content;
}

// zTree的设置
function ztree_setting(chkStyle) {
  chkStyle = chkStyle || "checkbox";
  var setting = {
    check: {
      enable: true,
      chkStyle: chkStyle,
      autoCheckTrigger: true,
      chkboxType: { "Y": "ps", "N": "ps" },
      radioType: "all"
    },
    data: {
      simpleData: {
        enable: true
      }
    },
    callback: {
      onCollapse: zTreeOnExpand,
      onExpand: zTreeOnExpand,
      onCheck: zTreeOnCheck
    }
  };
  return setting;
}

// zTree展开和折叠触发函数
function zTreeOnExpand(event, treeId, treeNode) {
  overflow_y_auto(treeId);
}

// zTree选择后触发函数
function zTreeOnCheck(event, treeId, treeNode) {
  var input_id = getInputId(treeId);
  var partner_id = getPartnerId(input_id);
  var dialog_id = input_id + "_Dialog"
  var chkStyle = getChkStyle(input_id);
  if (chkStyle == "radio") {
    // 如果是radio
    $("#" + input_id).val(treeNode.name);
    $("#" + partner_id).val(treeNode.id);
    dialog.get(dialog_id).close();
    $("#" + input_id).focus();
  }else{
    // 如果是checkbox
    var limited = $("#" + input_id).attr("limited") == undefined ? 10000 : parseInt($("#" + input_id).attr("limited"));
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    var nodes = treeObj.getCheckedNodes(true);
    var node_id = [];
    var node_name = [];
    var check_count = 0;
    var too_much = false;
    $.each(nodes, function(n, node) {
      if (!node.isParent) {
        check_count += 1;
        if (check_count > limited) {
          setTips(treeId,"选择项不能超过"+ limited +"个！");
        }
        else {
          setTips(treeId,"&nbsp;");
          node_id.push(node.id);
          node_name.push(node.name);
        }
      }
    });
    $("#" + input_id).val(node_name.join(","));
    $("#" + partner_id).val(node_id.join(","));
  }
  $("#" + input_id).attr('readonly','readonly');
}

// simpleData 将简单的带PID形式的JSON转化为嵌套数组的JSON
function convertSimpleData(rows){
  function exists(rows, parentId){
    for(var i=0; i<rows.length; i++){
      if (rows[i].id == parentId) return true;
    }
    return false;
  }

  var nodes = [];
  // 取第一层节点
  for(var i=0; i<rows.length; i++){
    var row = rows[i];
    if (!exists(rows, row.pId)){
      nodes.push({
        id:row.id,
        name:row.name
      });
    }
  }

  var toDo = [];
  for(var i=0; i<nodes.length; i++){
    toDo.push(nodes[i]);
  }
  while(toDo.length){
    var node = toDo.shift();  // 父节点
    // 取子节点
    for(var i=0; i<rows.length; i++){
      var row = rows[i];
      if (row.pId == node.id){
        var child = {id:row.id,name:row.name};
        if (node.children){
          node.children.push(child);
        } else {
          node.children = [child];
        }
        toDo.push(child);
      }
    }
  }
  return nodes;
}
;
