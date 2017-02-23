$(function(){
	list(1);
	var ids = $("#ids").val();
	selectedValue(ids);
});

/**
 * 初始化
 * @returns
 */
function list(curr){
	var name = $("#name").val();
	var type = $("#type").val();
	//如果是查物资生产型和物资销售型都查type为2的资质
	if (type == 2 || type == 3) {
		type = 2;
	}
	$.ajax({
		url: globalPath + "/qualification/list.do",
		type:"post",
		data:{'name' : name, 'type' : type,'page': curr},
		dataType:"json",
		async: false,
		success:function(res){
			if (res.success){
				var obj = res.obj;
				loadList(obj.list,obj.pageNum,obj.pageSize);
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
	    groups: pages >= 3 ? 3 : pages, 
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
function loadList(data,pageNum,pageSize){
	$("#dataTable tbody").empty();
	if (data != null && data.length > 0){
		for (var i =0;i<data.length; i++){
			loadData(data[i],i,pageNum,pageSize);
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
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data,index,pageNum,pageSize){
	var html = "<tr> "
		     + "  <td class='tc'>"
	         + "    <input  type='checkbox' name='chkItem' value='"+data.id+"' onclick='choseedValue()'/>"
		     + "  </td>"
		     + "  <td class='tc'>"+((index+1) +  (pageNum -1) * pageSize) +"</td>"
		     + "  <td class='tl pl20'>"+data.name+"</td>"
		     + "</tr>";
	$("#dataTable tbody").append(html);
}

function choseedValue(){
	var choseedIds = $("#choseIds").val();
	var choseedNames = $("#choseNames").val();
	var ids = [], names = [];
	$("input[name='chkItem']:checked").each(function(){
		ids.push($(this).val());
		names.push($(this).parents('tr').find('td').eq(2).text()+ " ");
	});
	if (choseedIds == '' || choseedIds == null) {
		choseedIds += ids.toString();
	} else {
		for ( var i = 0; i < ids.length; i++) {
			if(choseedIds.indexOf(ids[i]) == -1 ){
				choseedIds += ","+ids[i];
			} 
		}
	}
	if (choseedNames == '' || choseedNames == null) {
		choseedNames += names.toString();
	} else {
		for ( var i = 0; i < names.length; i++) {
			if(choseedNames.indexOf(names[i]) == -1 ){
				choseedNames += ","+names[i];
			} 
		}
	}
	$("#choseIds").val(choseedIds);
	$("#choseNames").val(choseedNames);
}


/**
 * 
 * @returns
 */
function ok(){
	var choseedIds = $("#choseIds").val();
	var choseedNames = $("#choseNames").val();
	var ids = [], names = [];
	/*$("input[name='chkItem']:checked").each(function(){
		ids.push($(this).val());
		names.push($(this).parents('tr').find('td').eq(2).text()+ " ");
	});
	
	if (ids.length == 0){
		layer.msg("请选择资质信息");
		return ;
	}*/
	if (choseedIds == '' || choseedIds == null) {
		layer.msg("请选择资质信息");
		return ;
	}
	var type = $("#type").val(); 
	/*if (type == 1){
		parent.addGeneralValue(ids.toString(),names.toString());
	}
	if (type == 2){
		parent.addProfileValue(ids.toString(),names.toString());
	}
	if (type == 3){
		parent.addProfileSalesValue(ids.toString(),names.toString());
	}*/
	if (type == 1){
		parent.addGeneralValue(choseedIds,choseedNames);
	}
	if (type == 2 || type == 4){
		parent.addProfileValue(choseedIds,choseedNames);
	}
	if (type == 3){
		parent.addProfileSalesValue(choseedIds,choseedNames);
	}
	cancel();
}

/**
 * 取消
 * @returns
 */
function cancel(){
	parent.layer.closeAll();
}

/**
 * 选中值
 * @param ids
 * @returns
 */
function selectedValue(ids){
	if (ids != ""){
		var idArray = ids.split(",");
		if (idArray.length > 0){
			$("input[name='chkItem']").each(function(){
				var obj = $(this).val();
				for (var i = 0;i<idArray.length;i++){
					 if (obj == idArray[i]){
						 $(this).attr("checked","checked");
					 }
				}
			});
		}
	}
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