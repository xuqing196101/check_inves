$(function(){
	var count1=$("#count1").val();
	var count2=$("#count2").val();
	var count3=$("#count3").val();
	var count4=$("#count4").val();
	var count5=$("#count5").val();
	var count6=$("#count6").val();
	var ind = parseInt($("#ids").val());
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
	}
});
//审核 销售合同不通过理由
function reasonProject(ind,auditField, auditFieldName,ids) {
	var businessId=$("#fileId"+ids+"").val();
	auditField=auditField+"_"+businessId;
	var supplierId = $("#supplierId").val();
	var auditCount = $("#count"+ids+"").val();
	ind=parseInt(ind)+1;
	var tablerId=$("#tablerId").val();
	var auditContent=content(tablerId,ind,'销售合同_'+showData(ids));
	
	var audits;
	switch (tablerId) {
	case 'content_1'://物资生产
	case 'content_3'://工程
	case 'content_4'://服务
		audits = $("#"+tablerId+" #isItemsProductPageAudit"+ind+"",window.parent.document).val();
		break;
	case 'content_2'://物资销售
		audits = $("#"+tablerId+" #isItemsSalesPageAudit"+ind+"",window.parent.document).val();
		break;
	}if(audits!=null && audits !='' && audits>'0' ){
		layer.msg('产品目录审核不通过,该销售合同不可审核', {offset:'100px'});
		return;
	}
	
	var auditType;
	if(auditCount!=null && auditCount !='' && auditCount>'0' ){
		layer.msg('已审核', {offset:'100px'});
		return;
	}
	switch (tablerId) {
	case 'content_1'://物资生产
		auditFieldName='物资-生产销售合同';
		auditType="contract_product_page";
		break;
	case 'content_3'://工程
		auditFieldName='工程-销售合同';
		auditType="contract_product_page";
		break;
	case 'content_4'://服务
		auditType="contract_product_page";
		auditFieldName='服务-销售合同';
		break;
	case 'content_2'://物资销售
		auditType="contract_sales_page";
		auditFieldName='物资-销售合同';
		break;
	}
	var index = layer.prompt({
		title: '请填写不通过的理由：',
		formType: 2,
		offset: '30px',
		maxlength: '100',
	}, function(text) {
		var text = $.trim(text);
	  if(text != null && text !=""){
		  if(text.length>900){
		    	layer.msg('审核理由内容太长', {offset:'100px'});
		    	return;
		    }
			$.ajax({
				url: globalPath+"/supplierAudit/auditReasons.do",
				type: "post",
				data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType="+auditType+"&auditContent=" + auditContent + "&auditField=" + auditField,
				dataType: "json",
				success: function(result) {
					if(result.status==500){
						layer.msg(result.msg, {
							shift: 6, //动画类型
							offset: '100px',
						});    
						switch (tablerId) {
						case 'content_1'://物资生产
						case 'content_3'://工程
						case 'content_4'://服务
							var old=$("#"+tablerId+" #isContractProductPageAudit"+ind+"",window.parent.document).val();
							$("#"+tablerId+" #isContractProductPageAudit"+ind+"",window.parent.document).val(parseInt(old)+1);
							break;
						case 'content_2'://物资销售
							var old=$("#"+tablerId+" #isContractSalesPageAudit"+ind+"",window.parent.document).val();;
							$("#"+tablerId+" #isContractSalesPageAudit"+ind+"",window.parent.document).val(parseInt(old)+1);
							break;
						}
						$("#"+tablerId+" #contract"+ind+"",window.parent.document).css('border-color', '#FF0000');
						$("#td1"+ids+"").css('border-color', '#FF0000');
						$("#show_td"+ids+"").attr('src', globalPath+'/public/backend/images/sc.png');
						$("#count"+ids+"").val('1');
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
function showData(index){
	var rut;
	index=parseInt(index);
	var parents=$("#td1"+index+"").parent().parent();
	if(index<=3){
		rut=parents.find("td").eq(0).text();
	}else if(index>=4){
		rut=parents.find("td").eq(1).text();
	}
	rut=rut+"_"+parents.find("td").eq(index+1).text();
	return rut;
}