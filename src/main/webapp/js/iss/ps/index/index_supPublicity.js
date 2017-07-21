/**
 * 加载list
 */
function list(curr){
    var index = layer.load(0, {
        shade : [ 0.1, '#fff' ],
        offset : [ '40%', '50%' ]
    });
	// 供应商名称
	var supplierName = $("#supplierName").val();
	// 类型ID
	var supplierTypeId = $("#supplierTypeId option:selected").val();
	// 企业性质
	var businessNature = $("#businessNature option:selected").val();
	// 采购机构ID
	var orgId = $("#orgId option:selected").val();
	$.ajax({
		url: globalPath + "/index/indexSupPublicityAjax.do",
		type: "post",
		data:{
			"supplierName":supplierName,
			"businessNature":businessNature,
			"supplierTypeId":supplierTypeId,
			"orgId":orgId,
			"page":curr
		},
		dataTYpe: "json",
		success: function(res){
			if (res.status == 200){
				var obj = res.data;
				loadList(obj.list,obj.pageNum,obj.pageSize);
				loadPage(obj.pages,obj.total,obj.startRow,obj.endRow,curr);
			}
			layer.close(index);
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
 * 重置按钮
 */
function resetAll(){
    $("#supplierName").val("");
    $("#businessNature option[value='']").prop("selected",true);
    $("#supplierTypeId option[value='']").prop("selected",true);
    $("#orgId option[value='']").prop("selected",true);
    list(1);
}

/**
 * 新增加载数据
 * @param data 
 * @returns
 */
function loadData(data,index,pageNum,pageSize){
	var html = "<li> "
		     + "  <span class='col-md-3 col-xs-3 col-sm-3'>"+data.supplierName+"</span>"
		     + "  <span class='col-md-2 col-xs-2 col-sm-2' title="+data.supplierTypeNames+">"+data.supplierTypeNames+"</span>"
		     + "  <span class='col-md-1 col-xs-1 col-sm-1'>"+data.businessNature+"</span>"
		     + "  <span class='col-md-1 col-xs-1 col-sm-1'>"+data.orgName+"</span>"
             + "  <span class='col-md-3 col-xs-3 col-sm-3'>"+"同意入库，选择了"+data.passCateCount+"个产品类别，通过了<a class='publicityCss' href=\"javascript:;\" onclick=\"loadItem('"+data.id+"')\">"+(data.passCateCount - data.noPassCateCount)+"</a>个产品类别"+"</span>"
		     + "  <span class='col-md-2 col-xs-2 col-sm-2 text-right'>"+timestampToDate('yyyy-MM-dd', data.auditDate)+"</span>"
		     html += "</li>";
	$("#supPublicityList").append(html);
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

/**
 * 加载产品类别
 * @param id
 */
function loadItem(id){
    window.location.href = globalPath + "/index/indexSupPublicityItem.html?supplierId="+id;
}