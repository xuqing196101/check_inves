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
		url: globalPath + "/synchImport/list.do",
		type:"post",
		data:{'operType' : type,'page': curr, 'searchType':searchType,'startTime' : startTime, 'endTime' :endTime},
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
function synchImport(){
	$.ajax({
		url: globalPath + "/synchImport/dataImport.do",
		type:"post",
		dataType:"json",
		success:function(res){
			if (res.success){
				layer.msg("导入成功");
				list(1);
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

