/**
 * 初始化
 * @returns
 */
function list(curr){
	var name = $("#name").val();
	var type = $("#type").val();
	$.ajax({
		url: globalPath + "/qualification/list.do",
		type:"post",
		data:{'name' : name, 'type' : type,'page': curr},
		dataType:"json",
		success:function(res){
			if (res.success){
				var obj = res.obj;
				loadList(obj.list);
				loadPage(obj.pages,obj.total,obj.startRow,obj.endRow,curr);
			}
		}
	});
}

/**
 * 分页
 * @param pages
 * @param total
 * @param start
 * @param end
 * @param current
 * @returns
 */
function loadPage(pages,total,start,end, current){
	laypage({
	    cont: $("#pagediv"),
	    pages: pages, 
	    skin: '#2c9fA6', 
	    skip: true, 
	    total: total,
	    startRow: start,
	    endRow: end,
	    groups: pages >= 5 ? 5 : pages, 
	    curr: current, 
	    jump: function(e, first){ 
	        if(!first){ 
	        	list(e.curr);
	        }
	    }
	  });
}

/**
 * 加载数据
 * @param data
 * @returns
 */
function loadList(data){
	$("#dataTable tbody").empty();
	if (data != null && data.length > 0){
		for (var i =0;i<data.length; i++){
			loadData(data[i]);
		}
	}
}



/** 全选全不选 */
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
 * 动态加载tab
 * @param obj
 * @param type
 * @returns
 */
function loadTab(obj,type){
	clearLiCss();
	window.location.href = globalPath+ "/qualification/init.html?type=" + type;
}

/**
 * 清空tab ul 下的li样式
 * @returns
 */
function clearLiCss(){
	$("#tabUl li").each(function(){
		$(this).removeClass("class");
	});
}

/**
 * 加载选中tab的样式
 * @returns
 */
function loadCss(){
	var type = $("#type").val();
	if (type == "1"){
		$("#tabNormald").addClass("active");
		$("#titleId").text("通用资质管理");
	}
	if (type == "2"){
		$("#tabSpeciId").addClass("active");
		$("#titleId").text("专用资质管理");
	}
}

/**
 * 添加
 * 
 */
function add(){
	$("#quaName").val("");
	$("#operaType").val("add");
	openDiv('新增');
}

/**
 * 编辑
 */
function edit(){
	var count = 0;	
	var id = null;
	$("input[name='chkItem']:checked").each(function(){
		count ++;
		id = $(this).val();
	});	
	if (count != 1){
		layer.msg("请选择一条记录进行编辑");
		return ;
	}
	
	$("#operaType").val("edit");
	
	$.ajax({
		url:  globalPath + "/qualification/getQualification.do" ,
		type:"post",
		data:{'id':id},
		dataType:"json",
		success:function(res){
			if (res.success) {
				if (res.obj != null){
					getQualification(res.obj);
				}
			} else {
				layer.msg(res.obj);
			}
		}
	});
}

/**
 * 删除
 */
function del(){
	var idArray = [];
	$("input[name='chkItem']:checked").each(function(){
		idArray.push($(this).val());
	});
	
	if (idArray.length == 0){
		layer.msg("请选择需要删除的记录");
		return ;
	} else {
		layer.confirm("您确认要删除吗？",{btn:['确认','取消']},function(){ajaxDel(idArray.toString())});
	}
}

/**
 * 保存
 * @returns
 */
function save(){
	var type = $("#type").val();
	var name = $("#quaName").val();
	var operaType = $("#operaType").val();
	var id = null;
	$("input[name='chkItem']:checked").each(function(){
		id = $(this).val();
	});	
	$.ajax({
		type:"post",
		url:  globalPath+"/qualification/save.do" ,
		data:{'name': name, 'type' : type, 'operaType' : operaType,"id": id},
		dataType:"json",
		success:function(res){
			if (res.success) {
				layer.msg("保存成功");
				if (res.obj != null){
					if (operaType == "add"){
						loadData(res.obj);
					}
					if (operaType == "edit"){
						updateTableData(res.obj);
					}
				}
			} else {
				layer.msg(res.obj);
			}
		}
	});
}

/**
 * 新增计算编号
 * @returns
 */
function calIndex(){
	var indexArray = [];
	var count  = 1;
	$("input[name='chkItem']").each(function(){
		indexArray.push($(this).parents('tr').find('td').eq(1).text());
	});
	
	if (indexArray.length > 0){
		var parentIndex = Math.max.apply(null, indexArray);
		if (parentIndex != 0){
			count = count + parentIndex;
		}
	}
	return count;
}

/**
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data){
	var count = calIndex();
	var html = "<tr> "
		     + "  <td class='tc'>"
	         + "    <input  type='checkbox' name='chkItem' value='"+data.id+"' />"
		     + "  </td>"
		     + "  <td class='tc'>"+count+"</td>"
		     + "  <td class='tc'>"+data.name+"</td>"
		     + "</tr>";
	$("#dataTable tbody").append(html);
	
	cancel();
}

/**
 * 获取对象
 * @param obj
 * @returns
 */
function getQualification(obj){
	if (obj != null){
		openDiv('编辑');
		$("#quaName").val(obj.name);
	}
}

/**
 * 加载获取后的对象
 * @param data
 * @returns
 */
function updateTableData(data){
	if (data != null){
		$("input[name='chkItem']:checked").each(function(){
			$(this).parents('tr').find('td').eq(2).text(data.name);
		});	
	}
	cancel();
}

/**
 * ajax 删除
 * @param ids
 */
function ajaxDel(ids){
	$.ajax({
		url:  globalPath+"/qualification/del.do" ,
		type:"post",
		data:{'id':ids},
		success:function(msg){
			if (msg == "ok") {
				layer.msg("删除成功");
				reLoadTableData();
			} else {
				layer.msg("删除失败");
			}
		}
	});
}

/**
 * 删除tr
 * @returns
 */
function reLoadTableData(){
	$("input[name='chkItem']:checked").each(function(){
		$(this).parents('tr').remove();
	});
	reCalculateIndex();
}

/**
 * 重新计算下标
 * @returns
 */
function reCalculateIndex(){
	var count = 0;
	$("input[name='chkItem']").each(function(){
		count ++;
		$(this).parents('tr').find('td').eq(1).text(count);
	});
}

/**
 * 打开div
 * @returns
 */
function openDiv(title){
	layer.open({
		  type: 1,
		  title: title,
		  skin: 'layui-layer-rim',
		  shadeClose: true,
		  area: ['580px','200px'],
		  content: $("#openDiv")
		});
}

/**
 * 取消
 * @returns
 */
function cancel(){
	layer.closeAll();
}

/**
 * 查询
 * @returns
 */
function search(){
	list(1);
}

/**
 * 重置
 * @returns
 */
function resetQuery(){
	$("#name").val("");
	list(1);
}