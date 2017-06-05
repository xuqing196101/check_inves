// 验证金额
function validateMoney(val) {
	var reg = /^([0-9]+([.]{1}[0-9]{1,2})?)?$/;
	if (!reg.test(val)) {
		//alert("金额格式不对，请输入正确的金额，最多两位小数！");
		layer.msg("金额格式不对，请输入正确的金额，最多两位小数！");
		return false;
	}
	return true;
}

// 验证百分比
function validatePercentage(val) {
	var reg = /^(([1-9]\d{1}|0|100)(\.\d{1,2})?%)?$/;
	if (!reg.test(val)) {
		//alert("百分比格式不对，正确格式为0%-100%，最多两位小数！");
		layer.msg("百分比格式不对，正确格式为0%-100%，最多两位小数！");
		return false;
	}
	return true;
}

// 验证百分比
function validatePercentage2(val) {
	var reg = /^(([1-9]\d{1}|0|100)(\.\d{1,2})?)?$/;
	if (!reg.test(val)) {
		//alert("百分比格式不对，正确格式为0-100的数字，最多两位小数！");
		layer.msg("百分比格式不对，正确格式为0-100的数字，最多两位小数！");
		return false;
	}
	return true;
}