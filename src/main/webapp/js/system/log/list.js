/**
 * 初始化
 * @returns
 */
function list(curr){
	var operPerson = $("#operPerson").val();
	var type = $("#type").val();
	var desc = $("#desc").val();
	var operIp = $("#ip").val();
	var operStartTime = $("#startTime").val();
	var operEndTime = $("#endTime").val();;
	$.ajax({
		url: globalPath + "/systemLog/list.do",
		type:"post",
		data:{'operPerson' : operPerson, 'logType' : type,'operIp':operIp,'operStartTime':operStartTime,
				'operEndTime': operEndTime,'desc': desc,'page': curr},
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
 * 动态加载tab
 * @param obj
 * @param type
 * @returns
 */
function loadTab(obj,type){
	clearLiCss();
	window.location.href = globalPath+ "/systemLog/init.html?type=" + type;
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
		$("#titleId").text("系统操作");
	}
	if (type == "2"){
		$("#tabSpeciId").addClass("active");
		$("#titleId").text("系统异常");
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
		     + "  <td class='tl pl20'>"+data.descriptions+"</td>"
		     + "  <td class='tc'>"+data.operatePersonName+"</td>"
		     + "  <td class='tc'>"+timestampToDate('yyyy-MM-dd hh:mm:ss',data.operateTime)+"</td>"
		     + "  <td class='tc'>"+data.operateIp+"</td>"
	if (data.logType == 1){
		html += "  <td class='tc'>"+data.responseTime+"</td>"
	}
	html+= "<td class='tc'><a href='javascript:detail(\""+ data.id +"\");'>详情</a></td>";
	html+= "</tr>";
	$("#dataTable tbody").append(html);
}

/**
 * 详情
 * @param id 主键Id
 * @returns
 */
function detail(id){
	layer.open({
		type: 2,
		title: '详情',
		area: [$(document).width() - 100 +'px', '400px'],
		content: globalPath+'/systemLog/detail.html?id=' + id
	});
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
	 $("#operPerson").val('');
	 $("#desc").val('');
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
