$(function(){
	var ind = parseInt($("#ids").val());
	ind=ind+1;
	var auditCount = $("#isContractPageAudit"+ind+"",window.parent.document).val();
	if(auditCount>0){
		$("#contract"+ind+"",window.parent.document).css('border-color', '#FF0000');
		$("#show_td").css('border-color', '#FF0000');
		$("#count").val(auditCount);
	}
});
//审核资质不通过理由
function reasonProject(ind,auditField, auditFieldName) {
	var supplierId = $("#supplierId").val();
	var auditCount = $("#count").val();
	var auditContent='上传销售合同信息';
	var auditType='contract_page';
	if(auditCount!=null && auditCount !='' && auditCount>'0' ){
		layer.msg('已审核', {offset:'100px'});
		return;
	}
	var index = layer.prompt({
		title: '请填写不通过的理由：',
		formType: 2,
		offset: '30px',
		maxlength: '100',
	}, function(text) {
		var text = $.trim(text);
	  if(text != null && text !=""){
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
						$("#isContractPageAudit"+(parseInt(ind)+1)+"",window.parent.document).val('1');
						$("#contract"+(parseInt(ind)+1)+"",window.parent.document).css('border-color', '#FF0000');
						$("#show_td").css('border-color', '#FF0000');
						$("#count").val('1');
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