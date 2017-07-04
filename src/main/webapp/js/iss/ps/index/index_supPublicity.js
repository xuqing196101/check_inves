/**
 * 加载list
 */
function list(curr){
	// 供应商名称
	var supplierName = $("#supplierName").val();
	$.ajax({
		url: globalPath + "/index/indexSupPublicityAjax.do",
		type: "post",
		data:{"supplierName":supplierName},
		dataTYpe: "json",
		success: function(res){
			if (res.status == 200){
				var obj = res.data;
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
 */
function loadList(data, pageNum, pageSize){
	// 将原先的内容清空
	$("#supPublicityList").empty();
	if(data != null && data.length > 0){
		for(var i = 0; i < data.length; i++){
			loadData(data[i], i, pageNum, pageSize);
		}
	}
}

/**
 * 查询方法
 */
function query(){
	list(1);
}

/**
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data,index,pageNum,pageSize){
	var html = "<li> "
		     + "  <span class='col-md-2 col-xs-2 col-sm-2'>"+data.supplierName+"</span>"
		     + "  <span class='col-md-3 col-xs-3 col-sm-3'>"+data.supplierTypeNames+"</span>"
		     + "  <span class='col-md-1 col-xs-1 col-sm-1'>"+data.businessNature+"</span>"
		     + "  <span class='col-md-2 col-xs-2 col-sm-2'>"+data.orgName+"</span>"
		     + "  <span class='col-md-4 col-xs-4 col-sm-4'>"+"同意入库，选择了"+data.passCateCount+"个产品类别，通过了"+(data.passCateCount - data.noPassCateCount)+"个产品类别"+"</span>"
		     html += "</li>";
	$("#supPublicityList").append(html);
}