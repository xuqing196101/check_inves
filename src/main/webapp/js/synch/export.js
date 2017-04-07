$(function(){
	list(1);
});


/**
 * 初始化
 * @returns
 */
function list(curr){
	var type = $("#operType").val();
	var searchType = $("#searchType").val();
	var startTime = $("#searchStartTime").val();
	var endTime = $("#searchEndTime").val();
	$.ajax({
		url: globalPath + "/synchExport/list.do",
		type:"post",
		data:{'operType' : type,'page': curr,'searchType':searchType,'startTime' : startTime, 'endTime' :endTime},
		dataType:"json",
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
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data,index,pageNum,pageSize){
	var html = "<tr> "
		     + "  <td class='tc'>"+((index+1) +  (pageNum -1) * pageSize) +"</td>"
		     + "  <td class='tc'>"+data.dataTypeName+"</td>"
		     + "  <td class='tc'>"+data.synchTime+"</td>"
		     + "  <td class='t1 pl20'>"+data.descriptions+"</td>"
		     + "</tr>";
	$("#dataTable tbody").append(html);
}


/**
 * 同步
 * @returns
 */
function synchExport(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var dataType = [];
	$("input[name='dataType']:checked").each(function(){
		dataType.push($(this).val());
	});
	
	if (dataType.length == 0){
		layer.msg("请选择同步类型");
		return ;
	}
	
	if (startTime == ""){
		layer.msg("开始时间不能为空");
		return ;
	}
	
	if (endTime == ""){
		layer.msg("结束时间不能为空");
		return ;
	}
	 var index = layer.load(0, {
			shade : [ 0.1, '#fff' ],
			offset : [ '45%', '53%' ]
		});
	$.ajax({
		url: globalPath + "/synchExport/dataExport.do",
		type:"post",
		data:{'startTime' : startTime,'endTime': endTime,'synchType': dataType.toString()},
		success:function(res){
			if (res.success){
				layer.close(index);
				layer.msg("导出成功");
				list(1);
			}else{
				layer.close(index);
				layer.msg("导出失败");
			}
		}
	});
}

/**
 * 查询
 * @returns
 */
function query(){
	list(1);
}

/**
 * 重置
 * @returns
 */
function reset(){
	$("#searchType").val("");
	$("#searchStartTime").val("");
	$("#searchEndTime").val("");
	list(1);
}


