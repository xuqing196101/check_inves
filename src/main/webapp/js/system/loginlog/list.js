/**
 * 初始化
 * @returns
 */
function list(curr){
	var name = $("#name").val();
	var ip = $("#ip").val();
	var loginType = $("#loginType").attr("selected",true).val();
	var loginStartTime = $("#startTime").val();
	var loginEndTime = $("#endTime").val();
	$.ajax({
		url: globalPath + "/loginlog/list.do",
		type:"post",
		data:{'name' : name, 'type' : loginType,'ip':ip,'startDate':loginStartTime,
				'endDate': loginEndTime, 'page': curr},
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
function loadList(data,pageNum,pageSize){
	$("#dataTable tbody").empty();
	if (data != null && data.length > 0){
		for (var i =0;i<data.length; i++){
			loadData(data[i],i,pageNum,pageSize);
		}
	}
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
		$("#titleId").text("登录操作");
	}
}

/**
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data,index,pageNum,pageSize){
	var html = "<tr> "
		     + "  <td class='tc'>"+((index+1) +  (pageNum -1) * pageSize) +"</td>"
		     + "  <td class='tl pl20'>"+data.name+"</td>"
		     if(data.type == 1){
		    	 html += "  <td class='tc'>专家 </td>"
		     }
			 if(data.type == 2){
				 html += "<td class='tc'>供应商</td>"
			 }
			 if(data.type == 3){
				 html += "<td class='tc'>后台管理员</td>"
			 }
			 html += "  <td class='tc'>"+timestampToDate('yyyy-MM-dd hh:mm:ss',data.createdAt)+"</td>"
			 html += "  <td class='tl pl20'>"+data.ip+"</td>";
		     html += "</tr>";
	$("#dataTable tbody").append(html);
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
	 $("#name").val('');
	 $("#loginType").val('');
	 $("#ip").val('');
	 $("#startTime").val('');
	 $("#endTime").val('');;
	 list(1);
}

/**
 * 时间戳转时间格式
 * @param format
 * @param timestamp
 * @returns
 */
function timestampToDate(format, timestamp){ 
	var date = new Date(timestamp);
	return date.format(format);
}

/**
 * 时间格式化
 * @param format
 * @returns
 */
Date.prototype.format = function(fmt){
	var o = { 
	        "M+" : this.getMonth()+1,                 //月份 
	        "d+" : this.getDate(),                    //日 
	        "h+" : this.getHours(),                   //小时 
	        "m+" : this.getMinutes(),                 //分 
	        "s+" : this.getSeconds(),                 //秒 
	        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	        "S"  : this.getMilliseconds()             //毫秒 
	    }; 
	    if(/(y+)/.test(fmt)) {
	            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	    }
	     for(var k in o) {
	        if(new RegExp("("+ k +")").test(fmt)){
	             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	         }
	     }
	    return fmt; 
}
