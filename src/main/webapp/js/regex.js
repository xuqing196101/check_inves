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
	var reg = /^(([1-9]\d{0,1}|0|100)(\.\d{1,2})?%)?$/;
	if (!reg.test(val)) {
		//alert("百分比格式不对，正确格式为0%-100%，最多两位小数！");
		layer.msg("百分比格式不对，正确格式为0%-100%，最多两位小数！");
		return false;
	}
	return true;
}

// 验证百分比
function validatePercentage2(val) {
	var reg = /^(([1-9]\d{0,1}|0|100)(\.\d{1,2})?)?$/;
	if (!reg.test(val)) {
		//alert("百分比格式不对，正确格式为0-100的数字，最多两位小数！");
		layer.msg("百分比格式不对，正确格式为0-100的数字，最多两位小数！");
		return false;
	}
	return true;
}


//验证百分比 供应商注册--供应商类型，物资删除半分比不能填超过100的数字
function validatePercentageSupplier(val,id) {
	var reg = /^(([1-9]\d{0,1}|0|100)(\.\d{1,2})?)?$/;
	if (!reg.test(val)) {
		//alert("百分比格式不对，正确格式为0-100的数字，最多两位小数！");
		$("#"+id+"").val("");
		layer.msg("百分比格式不对，正确格式为0-100的数字，最多两位小数！");
		return false;
	}
	return true;
}

//验证手机号
function validateMobile(val) {
	var reg = /^(1[3|4|5|8][0-9]\d{4,8})?$/gi;
	if (!reg.test(val)) {
		layer.msg("手机号格式不对！例如：13800000000");
		return false;
	}
	return true;
}

//验证电话和手机
function validateMobileAndTel(val){
	var regMob = /^(1[3|4|5|8][0-9]\d{4,8})?$/;
    var regTel = /^(([0-9]{3,4}-)?[0-9]{7,8})?$/;
    if(regMob.test(val) || regTel.test(val)){
        return true;
    } else {
    	layer.msg("联系电话格式不对！手机：13800000000，固话：010-12345678或0310-1234567");
        return false;
    }
}

//验证电话号码格式
function chkTelPhone(obj) {
    var regTel = /^(([0-9]{3,4}-)?[0-9]{7,8})?$/;
    if(!regTel.test(val)){
    	layer.msg("固话格式不对！例如：010-12345678或0310-1234567");
        return false;
    }
    return true;
}

//验证Email
function validateEmail(val) {
	var reg = /^([a-zA-Z]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/gi;
	if(!reg.test(val)){
		layer.msg("电子邮箱格式不对！例如：123456@qq.com");
        return false;
	}
	return true;
}