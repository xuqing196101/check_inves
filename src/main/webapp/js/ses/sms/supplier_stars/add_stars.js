// 供应商诚信记录模块  增加/修改星级表单  Auth:Easong
function submitForm(){
	
	// 清空错误信息
	$("#oneStarsErr").html("");
	$("#twoStarsErr").html("");
	$("#threeStarsErr").html("");
	$("#fourStarsErr").html("");
	$("#fiveStarsErr").html("");
	// 表单提交前校验
	// 获取一星级所需分数
	if($("#oneStars").val() == ''){
		$("#oneStarsErr").html("*请输入一星级所需分数");
		return;
	}
	var flag = validateIsNum($("#oneStars").val());
	if(flag){
		$("#oneStarsErr").html("*您输入的整数类型格式不正确");
		return;
	}
	// 获取二星级所需分数
	if($("#twoStars").val() == ''){
		$("#twoStarsErr").html("*请输入二星级所需分数");
		return;
	}
	flag = validateIsNum($("#twoStars").val());
	if(flag){
		$("#twoStarsErr").html("*您输入的整数类型格式不正确");
		return;
	}
	// 获取三星级所需分数
	if($("#threeStars").val() == ''){
		$("#threeStarsErr").html("*请输入三星级所需分数");
		return;
	}
	flag = validateIsNum($("#threeStars").val());
	if(flag){
		$("#threeStarsErr").html("*您输入的整数类型格式不正确");
		return;
	}
	// 获取四星级所需分数
	if($("#fourStars").val() == ''){
		$("#fourStarsErr").html("*请输入四星级所需分数");
		return;
	}
	flag = validateIsNum($("#fourStars").val());
	if(flag){
		$("#fourStarsErr").html("*您输入的整数类型格式不正确");
		return;
	}
	// 获取五星级所需分数
	if($("#fiveStars").val() == ''){
		$("#fiveStarsErr").html("*请输入五星级所需分数");
		return;
	}
	flag = validateIsNum($("#fiveStars").val());
	if(flag){
		$("#fiveStarsErr").html("*您输入的整数类型格式不正确");
		return;
	}
	
	$.post(globalPath + "/supplier_stars/save_or_update_supplier_stars.do", $("#starForm").serialize(), function(data) {
		if (data.status == 200) {
			layer.confirm("操作成功", {
				btn : [ '确定' ]
			}, function() {
				window.location.href = globalPath + "/supplier_stars/list.html";
			})
		}
		
		if(data.status == 500){
			layer.msg(data.data);
			return;
		}
	});
}

// 表单是否是整数校验
function validateIsNum(argu){
	reg=/^[-+]?\d*$/;
	if(argu.length != 0 && !reg.test(argu)){
		return true;
	}else{
		return false;
	}
}