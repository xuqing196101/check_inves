// 添加诚信
$(function(){
	$("#submitForm").click(function(){
		// 新增前的校验
		$("#Err_Name").html("");
		$("#Err_Score").html("");
		if($("#name").val() == ''){
			$("#Err_Name").html("*请填写诚信内容名称");
			return;
		}
		$("#Err_Score").html("");
		if($("#score").val() == ''){
			$("#Err_Score").html("*请填写诚信内容分数");
			return;
		}
		
		// 校验是否为数字
		flag = validateIsNum($("#score").val());
		if(flag){
			$("#Err_Score").html("*您输入的整数类型格式不正确");
			return;
		}
		
		$.ajax({
			   type: "POST",
			   url: globalPath + "/supplier_credit_ctnt/save_or_update_supplier_credit_ctnt.do",
			   data: $("#form1").serialize(),
			   dataType: "json",
			   success: function(data){
			     if(data.status == 200){
			    	 window.location.href = globalPath + "/supplier_credit_ctnt/list_by_credit_id.html?supplierCreditId=" + data.data;
			     }else{
			    	 layer.alert(data.msg);
			     }
			   }
		});
	});
});


//表单是否是整数校验
function validateIsNum(argu){
	reg=/^[-+]?\d*$/;
	if(argu.length != 0 && !reg.test(argu)){
		return true;
	}else{
		return false;
	}
}