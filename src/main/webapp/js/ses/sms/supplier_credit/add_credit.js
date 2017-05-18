// 添加诚信
$(function(){
	$("#submitForm").click(function(){
		// 新增前的校验
		$("#Err_Name").html("");
		if($("#name").val() == ''){
			$("#Err_Name").html("*请填写诚信形式名称");
			return;
		}
		
		$.ajax({
			   type: "POST",
			   url: globalPath + "/supplier_credit/save_or_update_supplier_credit.do",
			   data: $("#search_form_id").serialize(),
			   dataType: "json",
			   success: function(data){
			     if(data.status == 200){
			    	 parent.location.href = globalPath + "/supplier_credit/list.html";
			     }else{
			    	 layer.alert(data.msg);
			     }
			   }
		});
	});
});