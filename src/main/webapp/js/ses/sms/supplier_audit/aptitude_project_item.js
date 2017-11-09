$(function(){
	/*var tablerId=$("#tablerId").val();
	var ind = parseInt($("#ids").val());
	ind=ind+1;
	var auditCount;
	var type="";
	switch (tablerId) {
	case 'content_1'://物资生产
	case 'content_3'://工程
	case 'content_4'://服务
		type='product';
		auditCount = $("#"+tablerId+" #isAptitudeProductPageAudit"+ind+"",window.parent.document).val();
		break;
	case 'content_2'://物资销售
		type="sales";
		auditCount = $("#"+tablerId+" #isAptitudeSalesPageAudit"+ind+"",window.parent.document).val();
		break;
	}
	if(auditCount>0){
		$("#"+tablerId+" #qualifications"+ind+"",window.parent.document).css('border-color', '#FF0000');
		$("#show_td").attr('src', globalPath+'/public/backend/images/sc.png');
		$("#count").val(auditCount);
	}*/
});

//审核资质不通过理由
function reasonProject(ind, auditField, auditFieldName, certType) {
	var supplierStatus = $("input[name='supplierStatus']").val();
    var sign = $("input[name='sign']").val();
    // 只有审核的状态能审核
    if(window.parent.isAudit){
		var supplierId = $("#supplierId").val();
		var auditCount = $("#count").val();
		ind=parseInt(ind)+1;
		var tablerId=$("#tablerId").val();
		var auditContent=content(tablerId,ind,certType);
		var audits;
		var type="";
		/*if(auditCount!=null && auditCount !='' && auditCount>'0' ){
			layer.msg('已审核', {offset:'100px'});
			return;
		}*/
		var auditType;
		switch (tablerId) {
		case 'content_1'://物资生产
			auditFieldName='物资-生产专业资质要求';
			auditType="aptitude_product_page";
			break;
		case 'content_3'://工程
			auditFieldName='工程-专业资质要求';
			auditType="aptitude_product_page";
			break;
		case 'content_4'://服务
			auditType="aptitude_product_page";
			auditFieldName='服务-专业资质要求';
			break;
		case 'content_2'://物资销售
			auditType="aptitude_sales_page";
			auditFieldName='物资-销售专业资质要求';
			break;
		}
		if("aptitude_product_page"==auditType){
			audits = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"",window.parent.document).val();
		}else{
			audits = $("#"+tablerId+" #isItemsSalesPageAudit"+ind+"",window.parent.document).val();
		}
		
		if(audits!=null && audits !='' && audits>'0' ){
			layer.msg('产品目录审核不通过,该专业资质要求不可审核', {offset:'100px'});
			return;
		}
		
	    var auditData = {
			"supplierId": supplierId,
	        "auditType": auditType,
	        "auditField": auditField,
	        "auditFieldName": auditFieldName,
	        "auditContent": auditContent
	    };
	    // 判断：新审核/可再次审核/不可再次审核
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
					$("#show_td").attr('src', globalPath+'/public/backend/images/light_icon.png');
					$("#count").val('0');
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
	 		if(text != null && text !=""){
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
							
							/*var aptitudeId=$("#"+tablerId+" #aptitudeId"+ind+"",window.parent.document).val();
							//工程 只有专业资质 要求
							$("input[name='"+tablerId+"itemsCheckboxName']",window.parent.document).each(function(){
								var index=$(this).val();
								var firstNodeId=$("#"+tablerId+" #firstNodeId"+index+"",window.parent.document).val();
								var secondNodeId=$("#"+tablerId+" #secondNodeId"+index+"",window.parent.document).val();
								var thirdNodeId=$("#"+tablerId+" #thirdNodeId"+index+"",window.parent.document).val();
								var fourthNodeId=$("#"+tablerId+" #fourthNodeId"+index+"",window.parent.document).val();
								if(aptitudeId){
									var slip=aptitudeId.split(',');
									$(slip).each(function(inde,value){
										//判断 资质关联id 是否包含
										if($("input[id$='NodeId"+index+"'][value*='"+value+"']",window.parent.document).val()){
											$("#"+tablerId+" #qualifications"+index+"",window.parent.document).css('border-color', '#FF0000');
											if('aptitude_product_page'==auditType){
												//物资生产  工程 服务
												$("#"+tablerId+" #isAptitudeProductPageAudit"+index+"",window.parent.document).val('1');
											}else{
												//物资销售
												$("#"+tablerId+" #isAptitudealesPageAudit"+index+"",window.parent.document).val('1');
											}
										}
									});
								}
						 	});*/
							//$("#show_td").attr('src', globalPath+'/public/backend/images/sc.png');
							$("#show_td").attr('src', globalPath+'/public/backend/images/light_icon_2.png');
							$("#count").val('1');
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
		});
    }
}

//提示之前的信息
function isCompare(field, targetId) {
    var supplierId = $("#supplierId").val();
    $.ajax({
        url: globalPath+"/supplierAudit/showModify.do",
        data: {"supplierId": supplierId, "beforeField": field, "modifyType": "mat_eng_page", "listType": "9"},
        async: false,
        success: function (result) {
            layer.tips("修改前:" + result, "#" + targetId, {
                tips: 3
            });
        }
    });
}
