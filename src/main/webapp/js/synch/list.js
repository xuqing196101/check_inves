$(function(){
	list(1);
});


/**
 * 初始化
 * @returns
 */
function list(curr){
	var type = $("#operType").val();
	$.ajax({
		url: globalPath + "/synch/list.do",
		type:"post",
		data:{'operType' : type,'page': curr},
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
		     + "  <td class='tc'>"+setStatus(data.dataType)+"</td>"
		     + "  <td class='tc'>"+data.synchTime+"</td>"
		     + "  <td class='t1 pl20'>"+data.descriptions+"</td>"
		     + "</tr>";
	$("#dataTable tbody").append(html);
}

/**
 * 
 * @param status 状态
 * @returns
 */
function setStatus(status){
	if (status == 1){
		return "供应商注册信息";
	}
	if (status == 2){
		return "供应商修改信息";
	}
	if (status == 3){
		return "专家注册信息";
	}
	if (status == 4){
		return "专家修改信息";
	}
	if (status == 5){
		return "门户公告信息";
	}
	if (status == 6){
		return "附件信息";
	}
	return "";
}

/**
 * 同步
 * @returns
 */
function synch(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	if (startTime == ""){
		layer.msg("开始时间不能为空");
		return ;
	}
	
	if (endTime == ""){
		layer.msg("结束时间不能为空");
		return ;
	}
	
	$.ajax({
		url: globalPath + "/synch/synched.do",
		type:"post",
		data:{'startTime' : startTime,'endTime': endTime},
		success:function(res){
			if (res.success){
				list(1);
			}
		}
	});
}


