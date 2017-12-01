/**
 * 验证金额
 * @param val			输入值
 * @param radix			小数位(default:2)
 * @param unsigned		无符号(default:true)
 * @returns {Boolean}	true|false
 */
// 验证金额
// negative | positive
function validateMoney(val, radix, unsigned) {
	radix = radix || 2;
	unsigned = unsigned === undefined ? true : unsigned;
	var msg = "";
	if(unsigned == true){
		eval("var reg = /^([0-9]+([.]{1}[0-9]{1," + radix + "})?)?$/");
		msg = "金额格式不对，请输入正确的金额，非负数最多" + radix + "位小数！";
	}else{
		eval("var reg = /^((\-)?[0-9]+([.]{1}[0-9]{1," + radix + "})?)?$/");
		msg = "金额格式不对，请输入正确的金额，最多" + radix + "位小数！";
	}
	if (!reg.test(val)) {
		layer.msg(msg);
		return false;
	}
	return true;
}

// 验证百分比
function validatePercentage(val, radix) {
	radix = radix || 2;
	eval("var reg = /^(([1-9]\\d{0,1}|0|100)(\\.\\d{1," + radix + "})?%)?$/");
	if (!reg.test(val)) {
		layer.msg("百分比格式不对，正确格式为0%-100%，最多" + radix + "位小数！");
		return false;
	}
	return true;
}

// 验证百分比
function validatePercentage2(val, radix) {
	radix = radix || 2;
	eval("var reg = /^(([1-9]\\d{0,1}|0|100)(\\.\\d{1," + radix + "})?)?$/");
	if (!reg.test(val)) {
		layer.msg("百分比格式不对，正确格式为0-100的数字，最多" + radix + "位小数！");
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

//验证正整数
function validatePositiveInteger(val) {
	var reg = /^(\d+)?$/g;
	if(!reg.test(val)){
		layer.msg("请输入正整数！");
        return false;
	}
	return true;
}

//验证邮编
function validatePostCode(val) {
	var reg = /^([0-9]{6})?$/;
	if(!reg.test(val)){
		layer.msg("请输入正确的邮编！");
        return false;
	}
	return true;
}