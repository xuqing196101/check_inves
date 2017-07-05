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
	var itemsStyle,aptitudeStyle,contractStyle;
	$("#tab_content_2 tbody").empty();
	$(obj.list).each(
			function(index, item) {
				var ind=((index+1)+(obj.pageNum-1)*(obj.pageSize));
				var isItemsPageAudit=isNumber(item.isItemsPageAudit);
				var isAptitudePAgeAudit=isNumber(item.isAptitudePAgeAudit);
				var isContractPageAudit=isNumber(item.isContractPageAudit);
				//判断 是否 有审核 记录 采用不同的样式
				if(isItemsPageAudit > 0){
					itemsStyle=" class=\"tc info table-border-color-red\" ";
				}else{
					itemsStyle=" class=\"tc info\" ";
				}
				if(isAptitudePAgeAudit>0){
					aptitudeStyle="class=\"tc info table-border-color-red\"";
				}else{
					aptitudeStyle=" class=\"tc info\" ";
				}
				if(isContractPageAudit>0){
					contractStyle=" class=\"tc info table-border-color-red\" ";
				}else{
					contractStyle=" class=\"tc info\" ";
				}
				$("#tab_content_2 tbody").append("<tr>"+
								"<td class=\"tc info\">" + ind+ "</td>"+
								"<input type=\"hidden\" id=\"isItemsPageAudit"+ind+"\" value=\""+isItemsPageAudit+"\">"+
								"<input type=\"hidden\" id=\"isAptitudePAgeAudit"+ind+"\" value=\""+isAptitudePAgeAudit+"\">"+
								"<input type=\"hidden\" id=\"isContractPageAudit"+ind+"\" value=\""+isContractPageAudit+"\">"+
	                            "<td "+itemsStyle+" id=\"rootNode"+ind+"\" onclick=\"onCategory('"+ind+"','"+item.itemsName+"','"+item.itemsId+"')\" >"+isNull(item.rootNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"firstNode"+ind+"\" onclick=\"onCategory('"+ind+"','"+item.itemsName+"','"+item.itemsId+"')\">"+isNull(item.firstNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"secondNode"+ind+"\" onclick=\"onCategory('"+ind+"','"+item.itemsName+"','"+item.itemsId+"')\">"+isNull(item.secondNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"thirdNode"+ind+"\" onclick=\"onCategory('"+ind+"','"+item.itemsName+"','"+item.itemsId+"')\">"+isNull(item.thirdNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"fourthNode"+ind+"\" onclick=\"onCategory('"+ind+"','"+item.itemsName+"','"+item.itemsId+"')\">"+isNull(item.fourthNode)+"</td>"+
	                            "<td "+aptitudeStyle+" id=\"qualifications"+ind+"\" >"+isShow(ind,item.fileCount,"qualifications",item.rootNode,item.itemsId,item.supplierItemId,item.secondNode,item.secondNodeID)+"</td>"+
	                            "<td "+contractStyle+" id=\"contract"+ind+"\" >"+isShow(ind,item.contractCount,"contract",item.rootNode,item.itemsId,item.supplierItemId,item.secondNode,item.secondNodeID)+"</td></tr>"
				);
			});
}
var is=true;
//审核 目录
function onCategory(ind,secondNode,secondNodeId){
	if(!is){
		return;
	}
	is=false;
	var showin=$("#isItemsPageAudit"+ind+"").val();
	if(showin==0){
		var auditContent='品目信息';
		var auditType='items_page';
		reasonProject(ind,secondNodeId, secondNode,auditType,auditContent);
	}else{
		layer.msg('已审核！', {offset:'100px'});
	}
	is=true;
}
//判断显示相关内容 合同
function onContractShow(ind,rootNode,itemId,id,secondNode,secondNodeId){
	showFrame(ind,rootNode+"-销售合同信息",itemId,2,id,secondNode,secondNodeId);
}
//判断显示相关内容 资质
function onQualificationsShow(ind,rootNode,itemId,secondNode,secondNodeId){
	showFrame(ind,rootNode+"-资质文件信息",itemId,0,'',secondNode,secondNodeId);
}
//是否有内容显示  
function isShow(ind,count,type,rootNode,itemId,id,secondNode,secondNodeId){
	var rut="";
	
	//合同
	if(type=='contract'){
		if(count>0){
			rut= "<a href=\"javascript:void(0);\" onclick=\"onContractShow('"+ind+"','"+rootNode+"','"+itemId+"','"+id+"','"+secondNode+"','"+secondNodeId+"')\">查看</a>";
		}else {
			rut= "";
		}
		//资质
	}else if(type=='qualifications'){
		if(count>0){
			rut="<a href=\"javascript:void(0);\" onclick=\"onQualificationsShow('"+ind+"','"+rootNode+"','"+itemId+"','"+secondNode+"','"+secondNodeId+"')\">查看</a>";
		}else{
			rut= "";
		};
	};
	return rut;
}
//弹出框
function showFrame(ind,title,cateTree,flng,id,secondNode,secondNodeId){
	var supplierId=$("#supplierId").val();
	var showin=$("#auditType"+ind+"").val();
	var content;
	var auditType;
	var auditContent;
	if(flng==0){
		//资质
	    content=globalPath + "/supplierAudit/showQualifications.do?itemId="+cateTree+"&supplierId="+supplierId+"&ids="+ind;
	}else{
		//合同
		auditContent='上传合同文件信息';
		auditType='contract_page';
		content=globalPath + "/supplierAudit/showContract.do?itemId="+cateTree+"&supplierId="+supplierId+"&supplierItemId="+id+"&ids="+ind;
	}
	//var iframeWin;
	layer.open({
	  type: 2, //page层
	  area: ['980px', '430px'],
	  title: title,
	  closeBtn: 1,
	  shade:0.01, //遮罩透明度
	  moveType: 1, //拖拽风格，0是默认，1是传统拖动
	  shift: 1, //0-6的动画形式，-1不开启
	  offset: '60px',
	  shadeClose: false,
	  content: content,
	 /* success: function(layero, index){
	    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	  },*/
	  btn: ['关闭'],yes: function(){
		/*if(iframeWin){
	    iframeWin.reasonProject(ind,secondNodeId, secondNode,auditType,auditContent,auditCount,showin);
		};*/
		  layer.closeAll();
	  },btn2: function(){
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
//数字
function isNumber(obj){
	if(isNull(obj)){
		return obj;
	}else{
		return 0;
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
//目录 审核不通过理由
function reasonProject(ind,auditField, auditFieldName,type,auditContent) {
	var supplierId = $("#supplierId").val();
	var auditType = $("#isItemsPageAudit"+ind+"").val();
	if(auditType!=null && auditType !='' && auditType>'0' ){
		layer.msg('已审核', {offset:'100px'});
		return;
	}
	var index = layer.prompt({
		title: '请填写不通过的理由：',
		formType: 2,
		offset: '100px',
		maxlength: '100',
	}, function(text) {
		var text = $.trim(text);
	  if(text != null && text !=""){
		  if($.trim(text)>900){
			  layer.msg('审核内容长度过长！', {offset:'100px'});
			  return;
		  }
			$.ajax({
				url: globalPath+"/supplierAudit/auditReasons.do",
				type: "post",
				data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType="+type+"&auditContent=" + auditContent + "&auditField=" + auditField,
				dataType: "json",
				success: function(result) {
					if(result.status==500){
						layer.msg(result.msg, {
							shift: 6, //动画类型
							offset: '100px',
						});    
						$("#rootNode"+ind+"").val('1');
						$("#rootNode"+ind+"").css('border-color', '#FF0000');
						
						$("#firstNode"+ind+"").val('1');
						$("#firstNode"+ind+"").css('border-color', '#FF0000');
						
						$("#secondNode"+ind+"").val('1');
						$("#secondNode"+ind+"").css('border-color', '#FF0000');
						
						$("#thirdNode"+ind+"").val('1');
						$("#thirdNode"+ind+"").css('border-color', '#FF0000');
						
						$("#fourthNode"+ind+"").val('1');
						$("#fourthNode"+ind+"").css('border-color', '#FF0000');
						$("#isItemsPageAudit"+ind+"").val(1);
					}else{
						layer.msg(result.msg, {
							shift: 6, //动画类型
							offset: '100px',
						});
					}
				}
			});
				layer.close(index);
			}else{
  		layer.msg('不能为空！', {offset:'100px'});
  	};
	});
}

