/**表单数据交单**/
function vertifySubmitForm(){
	// 表单校验代码
	// 类别
	if($("#citySel4").val()==''){
		$("#err_category").html("*请选择类别");
		layer.msg("*请选择类别");
		return false;
	}
	// 类别
	if($("#name").val()==''){
		$("#err_name").html("*请输入名称");
		layer.msg("*请输入名称");
		return false;
	}
	// 价格
	var priceStr = $("#price").val();
	if(priceStr==''){
		$("#err_price").html("*请输入价格");
		layer.msg("*请输入价格");
		return false;
	}
	if(isNaN(priceStr)){
		$("#err_price").html("*格式错误");
		layer.msg("*价格格式错误");
		return false;
	}
	// 品牌
	if($("#brand").val()==''){
		$("#err_brand").html("*请输入品牌");
		layer.msg("*请输入品牌");
		return false;
	}
	// 型号
	if($("#typeNum").val()==''){
		$("#err_typeNum").html("*请输入型号");
		layer.msg("*请输入型号");
		return false;
	}
	// 库存
	var storeStr = $("#store").val();
	if(storeStr==''){
		$("#err_store").html("*请输入库存");
		layer.msg("*请输入库存");
		return false;
	}
	if(isNaN(storeStr)){
		$("#err_store").html("*格式错误");
		layer.msg("*库存格式错误");
		return false;
	}
	
	// SKU
	if($("#sku").val()==''){
		$("#err_sku").html("*请输入SKU");
		layer.msg("*请输入SKU");
		return false;
	}
	// 商品介绍
	if(ue.getContentTxt()==''){
		$("#err_introduce").html("*请输入商品介绍");
		layer.msg("*请输入商品介绍");
		return false;
	}
	return true;
}
