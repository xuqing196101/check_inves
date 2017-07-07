//默认不显示叉
$(function() {
	init();
});
//第一个 tab 初始化时
function initDivHide(showId,type,typeId,tableId){
	//模糊匹配 隐藏
	$("div[id^='tab_']").hide();
	$("#"+showId+"").show();
	findDate(typeId,tableId);
}
/**
 * 初始化  页签 分组显示
 */
function init(){
	var i=0;
	var liclass;
	var supplierTypes=$("#supplierTypes").val();
	if(!supplierTypes){
		return;
	}
	if(supplierTypes.indexOf('PRODUCT') !='-1'){
		i++;
		$("#tab_1").addClass("active in");
		$("#page_ul_id").append("<li class=\"active\"   id=\"productId\"> "+
	       " <a aria-expanded=\"true\" href=\"#tab_1\" onclick=\"initDivHide('tab_1','productId','PRODUCT','content_1')\" data-toggle=\"tab\">物资-生产型专业信息</a>"+
	    " </li>");
		initDivHide('tab_1','productId','PRODUCT','content_1');
	}
	if(supplierTypes.indexOf('SALES')  !='-1'){
		i++;
		if(i==0){
			$("#tab_2").addClass("active in");
			liclass=" class=\"active\"";
		}else{
		    liclass=" class='activeliCountEng'  ";
		}
		$("#page_ul_id").append("<li "+liclass+" id=\"salesId\"> "+
	        " <a aria-expanded=\"false\" href=\"#tab_2\" onclick=\"initDivHide('tab_2','salesId','SALES','content_2')\"  data-toggle=\"tab\">物资-销售型专业信息</a>"+
	    " </li>");
		if(i==0){
			initDivHide('tab_2','productId','SALES','content_2');
		}
	}
	if(supplierTypes.indexOf('PROJECT')  !='-1'){
		i++;
		if(i==0){
			$("#tab_3").addClass("active in");
			liclass=" class=\"active\" ";
		}else{
			liclass=" class='activeliCountEng' ";
		}
		$("#page_ul_id").append(" <li "+liclass+" id='projectId' > "+
	       " <a aria-expanded=\"false\" href=\"#tab_3\" onclick=\"initDivHide('tab_3','projectId','PROJECT','content_3')\" data-toggle=\"tab\">工程专业信息</a>"+
	    " </li>");
		if(i==0){
			initDivHide('tab_3','projectId','PROJECT','content_3');
	    }
	}
	if(supplierTypes.indexOf('SERVICE')  !='-1'){
		i++;
		if(i==0){
			$("#tab_4").addClass("active in");
			liclass=" class=\"active\" ";
		}else{
			liclass=" class='activeliCountEng' ";
		}
		$("#page_ul_id").append("<li "+liclass+" id=\"serviecId\" >"+
		       " <a aria-expanded=\"false\" href=\"#tab_4\" onclick=\"initDivHide('tab_4','serviecId','SERVICE','content_4')\" data-toggle=\"tab\">服务专业信息</a>"+
		    " </li>");
		if(i==0){
			initDivHide('tab_4','serviecId','SERVICE','content_4');
		}
	}
}
/** 分页* */
function listPage(pages, total, startRow, endRow, pageNum,type,tablerId) {
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
				findDate(type,tablerId);
			}
		}
	});
}
//获取 数据
function findDate(type,tablerId) {
		var index = layer.load(0, {
			shade : [ 0.1, '#fff' ],
			offset : [ '40%', '50%' ]
		});
		$("[name=supplierType]").val(type);
		$.ajax({
			type : "POST",
			url : globalPath + "/supplierAudit/overAptitude.do",
			data : $("#form_id").serializeArray(),
			success : function(obj) {
				if (obj) {
					listPage(obj.data.pages, obj.data.total, obj.data.startRow,
							obj.data.endRow, obj.data.pageNum,type);
					showData(obj.data,tablerId,type);
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
function showData(obj,tablerId,typeId) {
	var itemsStyle,aptitudeStyle,contractStyle,projectDiv;
	$("#"+tablerId+" tbody").empty();
	$(obj.list).each(
			function(index, item) {
				var ind=((index+1)+(obj.pageNum-1)*(obj.pageSize));
				//物资 生产 目录
				var isItemsProductPageAudit=isNumber(item.isItemsProductPageAudit);
				//物资 销售 目录
				var setIsItemsSalesPageAudit=isNumber(item.isItemsSalesPageAudit);
				//物资 生产 合同 	
				var isContractProductPageAudit=isNumber(item.isContractProductPageAudit);
				//物资 销售 合同
				var isContractSalesPageAudit=isNumber(item.isContractSalesPageAudit);
				//物资 生产 资质
				var isAptitudeProductPageAudit=isNumber(item.isAptitudeProductPageAudit);
				//物资 销售 资质
				var isAptitudeSalesPageAudit=isNumber(item.isAptitudeSalesPageAudit);
				// 根据类型 判断 
				switch (typeId) {
				case 'PRODUCT':
					//判断 是否 有审核 记录 采用不同的样式
					if(isItemsProductPageAudit > 0){
						itemsStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						itemsStyle=" class=\"tc info\" ";
					}
					if(isContractProductPageAudit > 0 || isItemsProductPageAudit > 0){
						contractStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						contractStyle=" class=\"tc info\" ";
					}
					if(isAptitudeProductPageAudit > 0 || isItemsProductPageAudit > 0){
						aptitudeStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						aptitudeStyle=" class=\"tc info\" ";
					}
					break;
				case 'SALES':
					if(setIsItemsSalesPageAudit > 0){
						itemsStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						itemsStyle=" class=\"tc info\" ";
					}
					if(isContractSalesPageAudit > 0 || setIsItemsSalesPageAudit > 0){
						contractStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						contractStyle=" class=\"tc info\" ";
					}
					if(isAptitudeSalesPageAudit > 0 || setIsItemsSalesPageAudit > 0){
						aptitudeStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						aptitudeStyle=" class=\"tc info\" ";
					}
					break;
				default:
					if(isItemsProductPageAudit > 0){
						itemsStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						itemsStyle=" class=\"tc info\" ";
					}
					if(isContractProductPageAudit > 0 || isItemsProductPageAudit > 0){
						contractStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						contractStyle=" class=\"tc info\" ";
					}
					if(isAptitudeProductPageAudit > 0 || isItemsProductPageAudit > 0){
						aptitudeStyle=" class=\"tc info table-border-color-red\" ";
					}else{
						aptitudeStyle=" class=\"tc info\" ";
					}
					break;
				}
				if("content_3" !=tablerId){
					projectDiv="<td "+contractStyle+" id=\"contract"+ind+"\" >"+isShow(tablerId,ind,item.contractCount,"contract",item.rootNode,item.itemsId,item.supplierItemId,item.secondNode,item.secondNodeID)+"</td>";
				}else{
					projectDiv="";
				}
				$("#"+tablerId+" tbody").append("<tr id=\"showTr"+ind+"\">"+
								"<td class=\"tc info\">" + ind+ "</td>"+
								"<input type=\"hidden\" id=\"isItemsProductPageAudit"+ind+"\" value=\""+isItemsProductPageAudit+"\">"+
								"<input type=\"hidden\" id=\"setIsItemsSalesPageAudit"+ind+"\" value=\""+setIsItemsSalesPageAudit+"\">"+
								"<input type=\"hidden\" id=\"isAptitudeProductPageAudit"+ind+"\" value=\""+isAptitudeProductPageAudit+"\">"+
								"<input type=\"hidden\" id=\"isContractProductPageAudit"+ind+"\" value=\""+isContractProductPageAudit+"\">"+
								"<input type=\"hidden\" id=\"isAptitudeSalesPageAudit"+ind+"\" value=\""+isAptitudeSalesPageAudit+"\">"+
								"<input type=\"hidden\" id=\"isContractSalesPageAudit"+ind+"\" value=\""+isContractSalesPageAudit+"\">"+
	                            "<td "+itemsStyle+" id=\"rootNode"+ind+"\" onclick=\"onCategory('"+tablerId+"','"+ind+"','"+item.itemsName+"','"+item.itemsId+"','"+typeId+"')\" >"+isNull(item.rootNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"firstNode"+ind+"\" onclick=\"onCategory('"+tablerId+"','"+ind+"','"+item.itemsName+"','"+item.itemsId+"','"+typeId+"')\">"+isNull(item.firstNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"secondNode"+ind+"\" onclick=\"onCategory('"+tablerId+"','"+ind+"','"+item.itemsName+"','"+item.itemsId+"','"+typeId+"')\">"+isNull(item.secondNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"thirdNode"+ind+"\" onclick=\"onCategory('"+tablerId+"','"+ind+"','"+item.itemsName+"','"+item.itemsId+"','"+typeId+"')\">"+isNull(item.thirdNode)+"</td>"+
	                            "<td "+itemsStyle+" id=\"fourthNode"+ind+"\" onclick=\"onCategory('"+tablerId+"','"+ind+"','"+item.itemsName+"','"+item.itemsId+"','"+typeId+"')\">"+isNull(item.fourthNode)+"</td>"+
	                            "<td "+aptitudeStyle+" id=\"qualifications"+ind+"\" >"+isShow(tablerId,ind,item.fileCount,"qualifications",item.rootNode,item.itemsId,item.supplierItemId,item.secondNode,item.secondNodeID)+"</td>"+
	                            projectDiv+"</tr>"
				);
			});
}
var is=true;
//审核 目录
function onCategory(tablerId,ind,secondNode,secondNodeId,wzType){
	if(!is){
		return;
	}
	is=false;
	var showin;
	var auditContent;
	var auditType;
	switch (wzType) {
	case 'PRODUCT':
		secondNode='物资-生产目录信息';
		auditType='items_product_page';
		showin=$("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val();
		break;
	case 'SALES':
		secondNode='物资-销售目录信息';
		auditType='items_sales_page';
		showin=$("#"+tablerId+" #setIsItemsSalesPageAudit"+ind+"").val();
		break;
	case 'PROJECT':
		secondNode='工程-目录信息';
		auditType='items_product_page';
		showin=$("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val();
		break;
	case 'SERVICE':
		secondNode='服务-目录信息';
		auditType='items_product_page';
		showin=$("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val();
		break;
	}
	if(showin==0){
		auditContent=contentParent(tablerId,ind,'目录信息');
		reasonProject(tablerId,ind,secondNodeId, secondNode,auditType,auditContent,wzType);
	}else{
		layer.msg('已审核！', {offset:'100px'});
	}
	is=true;
}
//判断显示相关内容 合同
function onContractShow(tablerId,ind,rootNode,itemId,id,secondNode,secondNodeId){
	showFrame(tablerId,ind,rootNode+"-销售合同信息",itemId,2,id,secondNode,secondNodeId);
}
//判断显示相关内容 资质
function onQualificationsShow(tablerId,ind,rootNode,itemId,secondNode,secondNodeId){
	showFrame(tablerId,ind,rootNode+"-专业资质要求信息",itemId,0,'',secondNode,secondNodeId);
}
//是否有内容显示  
function isShow(tablerId,ind,count,type,rootNode,itemId,id,secondNode,secondNodeId){
	var rut="";
	
	//合同
	if(type=='contract'){
		if(count>0){
			rut= "<a href=\"javascript:void(0);\" onclick=\"onContractShow('"+tablerId+"','"+ind+"','"+rootNode+"','"+itemId+"','"+id+"','"+secondNode+"','"+secondNodeId+"')\">审核</a>";
		}else {
			rut= "";
		}
		//资质
	}else if(type=='qualifications'){
		if(count>0){
			rut="<a href=\"javascript:void(0);\" onclick=\"onQualificationsShow('"+tablerId+"','"+ind+"','"+rootNode+"','"+itemId+"','"+secondNode+"','"+secondNodeId+"')\">审核</a>";
		}else{
			rut= "";
		};
	};
	return rut;
}
//弹出框
function showFrame(tablerId,ind,title,cateTree,flng,id,secondNode,secondNodeId){
	var supplierId=$("#supplierId").val();
	var content;
	var auditType;
	var auditContent;
	if(flng==0){
		//资质
	    content=globalPath + "/supplierAudit/showQualifications.do?itemId="+cateTree+"&supplierId="+supplierId+"&ids="+ind+"&tablerId="+tablerId+"";
	}else{
		//合同
		auditContent='上传合同文件信息';
		auditType='contract_page';
		content=globalPath + "/supplierAudit/showContract.do?itemId="+cateTree+"&supplierId="+supplierId+"&supplierItemId="+id+"&ids="+ind+"&tablerId="+tablerId+"";
	}
	layer.open({
	  type: 2, //page层
	  area: ['880px', '330px'],
	  title: title,
	  closeBtn: 1,
	  shade:0.01, //遮罩透明度
	  moveType: 1, //拖拽风格，0是默认，1是传统拖动
	  shift: 1, //0-6的动画形式，-1不开启
	  offset: '60px',
	  shadeClose: false,
	  content: content,
	 /* btn: ['关闭'],yes: function(){
		  layer.closeAll();
	  },btn2: function(){
	    layer.closeAll();
	  }*/
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
	var action = globalPath + "/supplierAudit/supplierType.html";
	$("#form_id").attr("action", action);
	$("#form_id").submit();
}
//目录 审核不通过理由  物资 生产 
function reasonProject(tablerId,ind,auditField, auditFieldName,type,auditContent,wzType) {
	var supplierId = $("#supplierId").val();
	var auditType;
	
	if('PRODUCT'==wzType){
		auditType = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val();
	}else if('SALES'==wzType){
		auditType = $("#"+tablerId+" #setIsItemsSalesPageAudit"+ind+"").val();
	}else{
		auditType = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val();
	}
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
		  if($.trim(text).length>900){
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
						$("#"+tablerId+" #rootNode"+ind+"").val('1');
						$("#"+tablerId+" #rootNode"+ind+"").css('border-color', '#FF0000');
						
						$("#"+tablerId+" #firstNode"+ind+"").val('1');
						$("#"+tablerId+" #firstNode"+ind+"").css('border-color', '#FF0000');
						
						$("#"+tablerId+" #secondNode"+ind+"").val('1');
						$("#"+tablerId+" #secondNode"+ind+"").css('border-color', '#FF0000');
						
						$("#"+tablerId+" #thirdNode"+ind+"").val('1');
						$("#"+tablerId+" #thirdNode"+ind+"").css('border-color', '#FF0000');
						
						$("#"+tablerId+" #fourthNode"+ind+"").val('1');
						$("#"+tablerId+" #fourthNode"+ind+"").css('border-color', '#FF0000');
						
						$("#"+tablerId+" #qualifications"+ind+"").css('border-color', '#FF0000');
						$("#"+tablerId+" #contract"+ind+"").css('border-color', '#FF0000');
						if('PRODUCT'==wzType){
							$("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val(1);
						}else if('SALES'==wzType){
							$("#"+tablerId+" #setIsItemsSalesPageAudit"+ind+"").val(1);
						}else{
							$("#"+tablerId+" #isItemsProductPageAudit"+ind+"").val(1);
						}
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

//暂存
function zhancun(){
  var supplierId = $("#supplierId").val();
  $.ajax({
    url: "${pageContext.request.contextPath}/supplierAudit/temporaryAudit.do",
    dataType: "json",
    data:{supplierId : supplierId},
    success : function (result) {
        layer.msg(result, {offset : [ '100px' ]});
    },error : function(){
      layer.msg("暂存失败", {offset : [ '100px' ]});
    }
  });
}

