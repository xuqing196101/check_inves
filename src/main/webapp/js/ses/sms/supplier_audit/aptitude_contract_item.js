$(function(){
	/*var count1=$("#count1").val();
	var count2=$("#count2").val();
	var count3=$("#count3").val();
	var count4=$("#count4").val();
	var count5=$("#count5").val();
	var count6=$("#count6").val();
	var ind = parseInt($("#ind").val());
	if(parseInt(count1)>0){
		$("#td11").css('border-color', '#FF0000');
		$("#show_td1").attr('src', globalPath+'/public/backend/images/sc.png');
	}
	if(parseInt(count2)>0){
		$("#td12").css('border-color', '#FF0000');
		$("#show_td2").attr('src', globalPath+'/public/backend/images/sc.png');
	}
	if(parseInt(count3)>0){
		$("#td13").css('border-color', '#FF0000');
		$("#show_td3").attr('src', globalPath+'/public/backend/images/sc.png');
	}
	if(parseInt(count4)>0){
		$("#td14").css('border-color', '#FF0000');
		$("#show_td4").attr('src', globalPath+'/public/backend/images/sc.png');
	}
	if(parseInt(count5)>0){
		$("#td15").css('border-color', '#FF0000');
		$("#show_td5").attr('src', globalPath+'/public/backend/images/sc.png');
	}
	if(parseInt(count6)>0){
		$("#td16").css('border-color', '#FF0000');
		$("#show_td6").attr('src', globalPath+'/public/backend/images/sc.png');
	}*/
});
  
// 审核 销售合同不通过理由
function reasonProject(ind,auditField,auditFieldName,idx) {
	//var supplierStatus = $("input[name='supplierStatus']").val();
    //var sign = $("input[name='sign']").val();
    // 只有审核的状态能审核
    if(window.parent.isAudit){
		//var businessId=$("#fileId"+idx+"").val();
		//auditField=auditField+"_"+businessId;
		var supplierId = $("#supplierId").val();
		//var auditCount = $("#count"+idx+"").val();
		ind=parseInt(ind)+1;
		var tablerId=$("#tablerId").val();
		//var auditContent=content(tablerId,ind,'销售合同_'+showData(idx));
		var auditContent=content(tablerId,ind,showData(idx));
		var audits;
		var auditType;
		/*if(auditCount!=null && auditCount !='' && auditCount>'0' ){
			layer.msg('已审核', {offset:'100px'});
			return;
		}*/
		switch (tablerId) {
		case 'content_1'://物资生产
			auditFieldName='物资-生产销售合同';
			auditType="contract_product_page";
			audits = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"",window.parent.document).val();
			break;
		case 'content_3'://工程
			auditFieldName='工程-销售合同';
			auditType="contract_product_page";
			audits = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"",window.parent.document).val();
			break;
		case 'content_4'://服务
			auditType="contract_product_page";
			auditFieldName='服务-销售合同';
			audits = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"",window.parent.document).val();
			break;
		case 'content_2'://物资销售
			auditType="contract_sales_page";
			audits = $("#"+tablerId+" #isItemsSalesPageAudit"+ind+"",window.parent.document).val();
			auditFieldName='物资-销售合同';
			break;
		}
		if(audits!=null && audits !='' && audits>'0' ){
			layer.msg('产品目录审核不通过，该销售合同不可审核', {offset:'100px'});
			return;
		}
		var contractId=$("#"+tablerId+" #contractId"+ind+"",window.parent.document).val();
		
	    var auditData = {
			"supplierId": supplierId,
	        "auditType": auditType,
	        "auditField": auditField,
	        "auditFieldName": auditFieldName,
	        "auditContent": auditContent
	    };
	    
	    $("#td1"+idx+"").doAudit({
			auditData : auditData,
			funcBeforeAudit : function() {
				if ($("#td1"+idx+" img").hasClass("icon_sc")) {
					layer.msg('该条信息已审核过并退回过！');
					return false;
				}
			},
			funcAfterAddAudit : function() {
				// 先获取原来的边框颜色
				var oldBorderColor = $("#td1"+idx+"").css('border');
				if(oldBorderColor){
					$("#td1"+idx+"").attr("data-oldBorderColor", oldBorderColor);
				}
				$("#td1"+idx+"").css('border', '1px solid #FF0000');
				//$("#show_td"+idx+"").attr('src', globalPath+'/public/backend/images/sc.png');
				$("#td1"+idx+" img").attr('src', globalPath+'/public/backend/images/light_icon_2.png');
				$("#count"+idx+"").val('1');
				// 刷新父页面数据
				window.parent.flushData();
			},
			funcAfterCancelAudit : function() {
				// 先获取原来的边框颜色
				var oldBorderColor = $("#td1"+idx+"").attr("data-oldBorderColor");
				if(oldBorderColor){
					$("#td1"+idx+"").css('border', oldBorderColor);
				}else{
					$("#td1"+idx+"").css('border', '');
				}
				$("#td1"+idx+" img").attr('src', globalPath+'/public/backend/images/light_icon.png');
				// 刷新父页面数据
				window.parent.flushData();
			}
		});
	    
	    /*// 判断：新审核/可再次审核/不可再次审核
	    // 获取旧的审核记录
	    var result = window.parent.getOldAudit(auditData);
	    if(result && result.status == 0){
	    	layer.msg('该条信息已审核过并退回过！');
			return;
	    }
	    var defaultVal = "";
	    var options = {
			title: '请填写不通过的理由：',
			value: defaultVal,
			formType: 2, 
			//offset: '100px',
			maxlength: '100'
		};
		if (result && result.status == 1 && result.data) {
			defaultVal = result.data.suggest;
			options.value = defaultVal;
			options.btn = [ '确定', '撤销', '取消' ];
			options.btn2 = function(index) {
				var bool = window.parent.cancelAudit(auditData);
				if (bool) {
					$("#td1"+idx+"").css('border-color', '');
					$("#show_td"+idx+"").attr('src', globalPath+'/public/backend/images/light_icon.png');
					// 刷新父页面数据
					window.parent.flushData();
				}
			};
			options.btn3 = function(index) {
				layer.close(index);
			};
		}
		layer.prompt(options, function(value, index, elem){
	 		var text = $.trim(value);
	 		if (text != null && text != "") {
	     		auditData.suggest = text;
				if(text.length>900){
					layer.msg('审核理由内容太长', {offset:'100px'});
					return;
				}
				$.ajax({
					url: globalPath+"/supplierAudit/auditReasons.do",
					type: "post",
					//data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType="+auditType+"&auditContent=" + auditContent + "&auditField=" + auditField,
					data: auditData,
					dataType: "json",
					success: function(result) {
						if(result.status==500){
							layer.msg(result.msg, {
								shift: 6, //动画类型
								offset: '100px',
							});    
							//销售合同资质 要求
//							$("input[name='"+tablerId+"itemsCheckboxName']",window.parent.document).each(function(){
//								var index=$(this).val();
//								var firstNodeId=$("#"+tablerId+" #firstNodeId"+index+"",window.parent.document).val();
//								var secondNodeId=$("#"+tablerId+" #secondNodeId"+index+"",window.parent.document).val();
//								var thirdNodeId=$("#"+tablerId+" #thirdNodeId"+index+"",window.parent.document).val();
//								var fourthNodeId=$("#"+tablerId+" #fourthNodeId"+index+"",window.parent.document).val();
//								//判断 资质关联id 是否包含
//								if(contractId){
//									contractId=$.trim(contractId);
//									var slip=contractId.split(',');
//									$(slip).each(function(inde,value){
//										//以下id结束的 标签比较
//										var v=$("input[id$='NodeId"+index+"'][value*='"+value+"']",window.parent.document).val();
//										if(v){
//											//资质文件
//											$("#"+tablerId+" #contract"+index+"",window.parent.document).css('border-color', '#FF0000');
//											if('contract_product_page'==auditType){
//											//物资生产   服务
//												var old=$("#"+tablerId+" #isContractProductPageAudit"+ind+"",window.parent.document).val();
//												$("#"+tablerId+" #isContractProductPageAudit"+ind+"",window.parent.document).val(parseInt(old)+1);
//											}else{
//											//物资销售
//												var old=$("#"+tablerId+" #isContractSalesPageAudit"+ind+"",window.parent.document).val();;
//												$("#"+tablerId+" #isContractSalesPageAudit"+ind+"",window.parent.document).val(parseInt(old)+1);
//											}
//										}
//									});
//								}
//							});
							
							$("#td1"+idx+"").css('border-color', '#FF0000');
							//$("#show_td"+idx+"").attr('src', globalPath+'/public/backend/images/sc.png');
							$("#show_td"+idx+"").attr('src', globalPath+'/public/backend/images/light_icon_2.png');
							$("#count"+idx+"").val('1');
							// 刷新父页面数据
							window.parent.flushData();
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
			}
		});*/
    }
}
function showData(index){
	var rut;
	index=parseInt(index);
	/*var parents=$("#td1"+index+"").parent().parent();
	if(index<=3){
		rut=parents.find("td").eq(0).text();
	}else if(index>=4){
		rut=parents.find("td").eq(1).text();
	}
	rut=rut+"_"+parents.find("td").eq(index+1).text();*/
	var date = new Date();
	var year = date.getFullYear();
	switch (index) {
	case 1:
		rut = (year-3)+"年度销售合同";
		break;
	case 2:
		rut = (year-2)+"年度销售合同";
		break;
	case 3:
		rut = (year-1)+"年度销售合同";
		break;
	case 4:
		rut = (year-3)+"年度银行收款证明";
		break;
	case 5:
		rut = (year-2)+"年度银行收款证明";
		break;
	case 6:
		rut = (year-1)+"年度银行收款证明";
		break;
	default:
		break;
	}
	return rut;
}
