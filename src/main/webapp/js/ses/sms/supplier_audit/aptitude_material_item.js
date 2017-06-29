//审核不通过理由
function reasonProject(auditField, auditFieldName,auditType,auditContent) {
	var supplierId = $("#supplierId").val();
	var auditType = $("#auditType").val();
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
			$.ajax({
				url: globalPath+"/supplierAudit/auditReasons.do",
				type: "post",
				data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType="+auditType+"&auditContent=" + auditContent + "&auditField=" + auditField,
				dataType: "json",
				success: function(result) {
					alert(result);
					result = eval("(" + result + ")");
					if(result.msg == "fail") {
						layer.msg('该条信息已审核过！', {
							shift: 6, //动画类型
							offset: '100px',
						});
					};
				}
			});
			$("#auditType").val('1');
				layer.close(index);
			}else{
  		layer.msg('不能为空！', {offset:'100px'});
  	};
	});
}