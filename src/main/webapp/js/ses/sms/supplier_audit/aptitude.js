//默认不显示叉
$(function() {
	findDate();
	$("td").each(function() {
		$(this).find("p").hide();
	});
});
/** 分页* */
function listPage(pages, total, startRow, endRow, pageNum) {
	laypage({
		cont : $("#pagediv"), // 容器。值支持id名、原生dom对象，jquery对象,
		pages : pages, // 总页数
		skin : '#2c9fA6', // 加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		skip : true, // 是否开启跳页
		total : total,
		startRow : startRow,
		endRow : endRow,
		groups : pages >= 3 ? 3 : pages, // 连续显示分页数
		curr : function() { // 通过url获取当前页，也可以同上（pages）方式获取
			return pageNum;
		}(),
		jump : function(e, first) { // 触发分页后的回调
			if (!first) { // 一定要加此判断，否则初始时会无限刷新
				$("#page").val(e.curr);
				findDate();
			}
		}
	});
}
//获取 数据
function findDate() {
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ],
			offset : [ '40%', '50%' ]
		});
		$.ajax({
			type : "POST",
			url : globalPath + "/supplierAudit/overAptitude.do",
			data : $("#form_id").serializeArray(),
			success : function(obj) {
				if (obj) {
					listPage(obj.data.pages, obj.data.total, obj.data.startRow,
							obj.data.endRow, obj.data.pageNum);
					showData(obj.data);
				} else {
					layer.msg(obj.msg);
				}
				layer.close(index);
			},
			error : function(data) {
				layer.msg("请求异常!");
				layer.close(index);
			},
		});
}
//封装填充 数据
function showData(obj) {
	$("#tab_content_2 tbody").empty();
	$(obj.list).each(
			function(index, item) {
				$("#tab_content_2 tbody").append("<tr>"+
								"<td class=\"tc info\">" + ((index+1)+(obj.pageNum-1)*(obj.pageSize))+ "</td>"+
	                            "<td class=\"tc info\">"+isNull(item.rootNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.firstNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.secondNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.thirdNode)+"</td>"+
	                            "<td class=\"tc info\">"+isNull(item.fourthNode)+"</td>"+
	                            "<td class=\"tc info\">"+isShow(item.fileCount,"qualifications",item.rootNode,item.itemsId,item.supplierItemId,item.secondNode,item.secondNodeID)+"</td>"+
	                            "<td class=\"tc info\">"+isShow(item.contractCount,"contract",item.rootNode,item.itemsId,item.supplierItemId,item.secondNode,item.secondNodeID)+"</td></tr>"
				);
			});
}
//判断显示相关内容 合同
function onContractShow(rootNode,itemId,id,secondNode,secondNodeId){
	showFrame(rootNode+"-销售合同信息",itemId,2,id,secondNode,secondNodeId);
}
//判断显示相关内容 资质
function onQualificationsShow(rootNode,itemId,secondNode,secondNodeId){
	showFrame(rootNode+"-资质文件信息",itemId,0,'',secondNode,secondNodeId);
}
//是否有内容显示  
function isShow(count,type,rootNode,itemId,id,secondNode,secondNodeId){
	var rut="";
	
	//合同
	if(type=='contract'){
		if(count>0){
			rut= "<a href=\"javascript:void(0);\" onclick=\"onContractShow('"+rootNode+"','"+itemId+"','"+id+"','"+secondNode+"','"+secondNodeId+"')\">查看</a>";
		}else {
			rut= "";
		}
		//资质
	}else if(type=='qualifications'){
		if(count>0){
			rut="<a href=\"javascript:void(0);\" onclick=\"onQualificationsShow('"+rootNode+"','"+itemId+"','"+secondNode+"','"+secondNodeId+"')\">查看</a>";
		}else{
			rut= "";
		};
	};
	return rut;
}
//弹出框
function showFrame(title,cateTree,flng,id,secondNode,secondNodeId){
	var supplierId=$("#supplierId").val();
	var content;
	var auditType;
	var auditContent;
	if(flng==0){
		//资质
		auditContent='上传资质文件信息';
		auditType='aptitude_page';
	    content=globalPath + "/supplierAudit/showQualifications.do?itemId="+cateTree+"&supplierId="+supplierId;
	}else{
		//合同
		auditContent='上传合同文件信息';
		auditType='contract_page';
		content=globalPath + "/supplierAudit/showContract.do?itemId="+cateTree+"&supplierId="+supplierId+"&supplierItemId="+id;
	}
	var iframeWin;
	layer.open({
	  type: 2, //page层
	  area: ['900px', '450px'],
	  title: title,
	  closeBtn: 1,
	  shade:0.01, //遮罩透明度
	  moveType: 1, //拖拽风格，0是默认，1是传统拖动
	  shift: 1, //0-6的动画形式，-1不开启
	  offset: '60px',
	  shadeClose: false,
	  content: content,
	  success: function(layero, index){
	    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	  },
	  btn: ['审核不通过原因','关闭'] 
	  ,yes: function(){
		if(iframeWin){
	    iframeWin.reasonProject(secondNodeId, secondNode,auditType,auditContent);
		}
	  }
	  ,btn2: function(){
	    layer.closeAll();
	  }
	});
}

//是否为空
function isNull(obj){
	if(obj){
		return obj;
	}else{
		return "";
	}
}
// 下一步
function nextStep() {
	var action = globalPath + "/supplierAudit/applicationForm.html";
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}

// 上一步
function lastStep() {
	/* $("#form_id").attr("action", lastUrl); */
	var action = globalPath + "/supplierAudit/supplierType.html";
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}


