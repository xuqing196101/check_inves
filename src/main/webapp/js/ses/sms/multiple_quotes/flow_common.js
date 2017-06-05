/**
 * 根据参数 打印提示语
 * 
 * @param type
 */
function show(type) {
	switch (type) {
	case '0':
		layer.msg("开标一览表未完成");
		break;
	case '1':
		layer.msg("价格构成表未完成");
		break;
	case '2':
		layer.msg("明细表未完成");
		break;
	case '3':
		layer.msg("编制标书未完成");
		break;
	}
};
function ycDiv(obj, index) {
	if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
		$(obj).removeClass("shrink");
		$(obj).addClass("spread");
	} else {
		if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
			$(obj).removeClass("spread");
			$(obj).addClass("shrink");
		}
	}
	var divObj = new Array();
	divObj = $(".p0" + index);
	for ( var i = 0; i < divObj.length; i++) {
		if ($(divObj[i]).hasClass("p0" + index)	&& $(divObj[i]).hasClass("hide")) {
			$(divObj[i]).removeClass("hide");
		} else {
			if ($(divObj[i]).hasClass("p0" + index)) {
				$(divObj[i]).addClass("hide");
			};
		};
	};
};