// 审核操作
function opr(_this, id, type) {
	if(type == 1){
		if($(_this).hasClass("bgdd") && $(_this).hasClass("black_link")){// 默认按钮
			$(_this).removeClass("bgdd");
			$(_this).removeClass("black_link");
			$("#isAccord_" + id).val(type);
			$(_this).next().removeClass("bgred");
			$(_this).next().addClass("bgdd");
			$(_this).next().addClass("black_link");
		}else{
			$(_this).addClass("bgdd");
			$(_this).addClass("black_link");
			$("#isAccord_" + id).val("");
		}
	}
	if(type == 2){
		if($(_this).hasClass("bgdd") && $(_this).hasClass("black_link")){// 默认按钮
			$(_this).removeClass("bgdd");
			$(_this).removeClass("black_link");
			$(_this).addClass("bgred");
			$("#isAccord_" + id).val(type);
			$(_this).prev().addClass("bgdd");
			$(_this).prev().addClass("black_link");
		}else{
			$(_this).removeClass("bgred");
			$(_this).addClass("bgdd");
			$(_this).addClass("black_link");
			$("#isAccord_" + id).val("");
		}
	}
}