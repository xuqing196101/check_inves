/** 校验注册用户名和密码信息 */
function validateRegSupplierInfo() {
	var loginName = $("#loginName").val();
	if(!loginName) {
		layer.tips("请输入用户名", "#loginName", {
			tips : 1
		});
		return false;
	}
	var password = $("#password").val();
	if(!password) {
		layer.tips("请输入密码", "#password", {
			tips : 1
		});
		return false;
	} else if(!password.match(/^(?!(?:\d*$))[A-Za-z0-9_]{6,20}$/)) {
		layer.tips("密码由6-20位字母 数字组成 !", "#password", {
			tips : 1
		});
		return false;
	}
	var confirmPassword = $("#confirmPassword").val();
	if (!confirmPassword) {
		layer.tips("请输入确认密码 !", "#confirmPassword", {
			tips : 1
		});
		return false;
	} else if (confirmPassword != password) {
		layer.tips("密码不一致 !", "#confirmPassword", {
			tips : 1
		});
		return false;
	}
	var mobile = $("#mobile").val();
	if (!mobile) {
		layer.tips("请输入手机号码 !", "#mobile", {
			tips : 1
		});
		return false;
	} else {
		if (!mobile.match(/^[1][34578]{1}[0-9]{9}$/)) {
			layer.tips("请输入有效的手机号码 !", "#mobile", {
				tips : 1
			});
			return false;
		}
	}
	var inputMobileCode = $("#mobileCode_input_id").val();
	if (!inputMobileCode) {
		layer.tips("请输入短信验证码 !", "#mobileCode_input_id", {
			tips : 1
		});
		return false;
	} /*else if (mobileCode != inputMobileCode) {
		layer.tips("短信验证码错误 !", "#mobileCode_input_id", {
			tips : 1
		});
	}*/
	var cc = $("#identityCode_input_id").val().toLowerCase();
	if (!cc) {
		layer.tips("请输入验证码 !", "#identityCode_input_id", {
			tips : 1
		});
		return false;
	}/* else if (code != cc) {
		layer.tips("验证码错误 !", "#identityCode_input_id", {
			tips : 1
		});
		return false;
	}*/
	return true;
}

/** 校验工商注册信息 */
function validateBusinessSupplierInfo() {
	var supplierName = $("#name").val();
	if (!supplierName) {
		layer.tips("请输入名称 !", "#name");
		return false;
	}
	var supplierTepe = $("#supplierType").val();
	if (!supplierTepe) {
		layer.tips("请输入企业类别!", "#supplierType");
		return false;
	}
	var supplierChinesrName = $("#chinesrName").val();
	if (!supplierChinesrName) {
		layer.tips("请输入中文译名 !", "#chinesrName");
		return false;
	}
	var legalName = $("#legalName").val();
	if (!legalName) {
		layer.tips("请输入法定代表人!", "#legalName");
		return false;
	}/* else if (!legalCode.match(/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/)) {
		layer.tips("请输入有效的法人身份证号码 !", "#legalCode");
		return false;
	}*/
	var address = $("#address").val();
	if (!address) {
		layer.tips("请输入地址 !", "#address");
		return false;
	}
	var supplierZipCode = $("#postCode").val();
	if (!supplierZipCode) {
		layer.tips("请输入邮政编码 !", "#postCode");
		return false;
	}
	var productType = $("#productType").val();
	if (!productType) {
		layer.tips("请输经营产品大类 !", "#productType");
		return false;
	}
	var majorProduct = $("#majorProduct").val();
	if (!majorProduct) {
		layer.tips("请输入主营产品 !", "#majorProduct");
		return false;
	}
	var sideProduct = $("#byproduct").val();
	if (!sideProduct) {
		layer.tips("请输入兼营产品 !", "#byproduct");
		return false;
	}
	var producerName = $("#producerName").val();
	if (!producerName) {
		layer.tips("请输入生产商名称 !", "#producerName");
		return false;
	}
	var contactPerson = $("#contactPerson").val();
	if (!contactPerson) {
		layer.tips("请输入联系人 !", "#contactPerson");
		return false;
	}
	var supplierTele = $("#telephone").val();
	if (!supplierTele) {
		layer.tips("请输入电话 !", "#telephone");
		return false;
	}
	var supplierFax = $("#fax").val();
	if (!supplierFax) {
		layer.tips("请输入传真 !", "#fax");
		return false;
	}
	var supplierEmail = $("#email").val();
	if (!supplierEmail) {
		layer.tips("请输入电子邮件 !", "#email");
		return false;
	} /*else if(!regMoney.match(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/)) {
		layer.tips("请输入合法数字 !", "#regMoney");
		return false;
	}*/
	var netUrl = $("#website").val();
	if (!netUrl) {
		layer.tips("请输入企业网址 !", "#website");
		return false;
	}
	var supplyLevel = $("#civilAchievement").val().trim();
	if (!supplyLevel) {
		layer.tips("请输入国内供货业绩 !", "#civilAchievement");
		return false;
	}
	var supplierRemark = $("#remark").val();
	if (!supplierRemark) {
		layer.tips("请输入企业简介 !", "#remark");
		return false;
	}
	return true;
}

/** 校验税务登记信息 */
function validateTaxSupplierInfo() {
	var bankName = $("#bankName").val();
	if (!bankName) {
		layer.tips("请输入开户银行 !", "#bankName");
		return false;
	}
	var bankCode = $("#bankCode").val();
	if (!bankCode) {
		layer.tips("请输入开户银行账号 !", "#bankCode");
		return false;
	} else if(!bankCode.match(/^(\d{16}|\d{19})$/)) {
		layer.tips("请输入合法银行账号 !", "#bankCode");
		return false;
	}
	var taxCode = $("#taxCode").val();
	if (!taxCode) {
		layer.tips("请输入税务登记号 !", "#taxCode");
		return false;
	}
	var depTel = $("#depTel").val();
	if (!depTel) {
		layer.tips("请输入单位电话 !", "#depTel");
		return false;
	} else if(!depTel.match(/^(\d{4}-|\d{3}-)?(\d{8}|\d{7})$/)) {
		layer.tips("请输入有效的电话 !", "#depTel");
		return false;
	}
	var depFax = $("#depFax").val();
	if (!depFax) {
		layer.tips("请输入单位传真 !", "#depFax");
		return false;
	}
	var len = $("input[name='supplierType']:checked").length;
	if (!len) {
		layer.tips("请勾选供应商类型 !", "#supplierType", {
			tips : 1
		});
		return false;
	}
	return true;
}

/** 校验附件 */
function validatePicSupplierInfo() {
	var business = $("#business").attr("alt");
	if (!business) {
		layer.tips("请选择营业执照扫描件 !", "#business");
		return false;
	} else if(business == "update") {
		if(!$("#business").val()) {
			layer.tips("请选择营业执照扫描件 !", "#business");
			return false;
		} else if (!checkSuffix($("#business").val())) {
			layer.tips("图片限于bmp,png,gif,jpeg,jpg格式 !", "#business");
			return false;
		} else if (!checkFileSize(document.getElementById("business"))) {
			layer.tips("上传的图片不能大于1M !", "#business");
			return false;
		}
	}
	var tax = $("#tax").attr("alt");
	if (!tax) {
		layer.tips("请选择税务登记证扫描件 !", "#tax");
		return false;
	} else if(tax == "update") {
		if(!$("#tax").val()) {
			layer.tips("请选择税务登记证扫描件 !", "#tax");
			return false;
		} else if (!checkSuffix($("#tax").val())) {
			layer.tips("图片限于bmp,png,gif,jpeg,jpg格式 !", "#tax");
			return false;
		} else if (!checkFileSize(document.getElementById("tax"))) {
			layer.tips("上传的图片不能大于1M !", "#tax");
			return false;
		}
	}
	var legal = $("#legal").attr("alt");
	if (!legal) {
		layer.tips("请选择单位法人身份证扫描件 !", "#legal");
		return false;
	} else if(legal == "update") {
		if(!$("#legal").val()) {
			layer.tips("请选择单位法人身份证扫描件 !", "#legal");
			return false;
		} else if (!checkSuffix($("#legal").val())) {
			layer.tips("图片限于bmp,png,gif,jpeg,jpg格式 !", "#legal");
			return false;
		} else if (!checkFileSize(document.getElementById("legal"))) {
			layer.tips("上传的图片不能大于1M !", "#legal");
			return false;
		}
	}
	var regIdentity = $("#regIdentity").attr("alt");
	if (!regIdentity) {
		layer.tips("请选择注册人身份证扫描件 !", "#regIdentity");
		return false;
	} else if(regIdentity == "update") {
		if(!$("#regIdentity").val()) {
			layer.tips("请选择注册人身份证扫描件 !", "#regIdentity");
			return false;
		} else if (!checkSuffix($("#regIdentity").val())) {
			layer.tips("图片限于bmp,png,gif,jpeg,jpg格式 !", "#regIdentity");
			return false;
		} else if (!checkFileSize(document.getElementById("regIdentity"))) {
			layer.tips("上传的图片不能大于1M !", "#regIdentity");
			return false;
		}
	}
	var regInfo = $("#regInfo").attr("alt");
	if (!regInfo) {
		layer.tips("请选择盖公章注册资料扫描件 !", "#regInfo");
		return false;
	} else if(regInfo == "update") {
		if(!$("#regInfo").val()) {
			layer.tips("请选择盖公章注册资料扫描件 !", "#regInfo");
			return false;
		} else if(!checkSuffix($("#regInfo").val())) {
			layer.tips("图片限于bmp,png,gif,jpeg,jpg格式 !", "#regInfo");
			return false;
		} else if (!checkFileSize(document.getElementById("regInfo"))) {
			layer.tips("上传的图片不能大于1M !", "#regInfo");
			return false;
		}
	}
	return true;
}

/** 校验修改联系人信息 */
function validateContactPersonInfo() {
	var name = $("#name").val();
	if (!name) {
		layer.tips("请输入联系人姓名 !", "#name");
		return false;
	}
	var identityCard = $("#identityCard").val();
	if (!identityCard) {
		layer.tips("请输入身份证号码 !", "#identityCard");
		return false;
	} else {
		if (!identityCard.match(/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/)) {
			layer.tips("请输入有效的身份证号码 !", "#identityCard");
			return false;
		}
	}
	var mobile = $("#mobile").val();
	if (!mobile) {
		layer.tips("请输入手机号码 !", "#mobile");
		return false;
	} else {
		if (!mobile.match(/^[1][34578]{1}[0-9]{9}$/)) {
			layer.tips("请输入有效的手机号码 !", "#mobile");
			return false;
		}
	}
	var code = $("#mobileCode").val();
	if (!code) {
		layer.tips("请输入验证码 !", "#mobileCode", {
			tips : 1
		});
		return false;
	} else {
		if (code != mobileCode) {
			layer.tips("验证码错误 !", "#mobileCode", {
				tips : 1
			});
			return false;
		}
	}
	var tel = $("#tel").val();
	if (!tel) {
		layer.tips("请输入电话号码 !", "#tel");
		return false;
	} else {
		if (!tel.match(/^(\d{4}-|\d{3}-)?(\d{8}|\d{7})$/)) {
			layer.tips("请输入有效的电话号码 !", "#tel");
			return false;
		}
	}
	var email = $("#email").val();
	if (!email) {
		layer.tips("请输入电子邮箱 !", "#email");
		return false;
	} else {
		if (!email.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/)) {
			layer.tips("请输入有效的电子邮箱 !", "#email");
			return false;
		}
	}
	return true;
}

/** 校验注册联系人信息 */
function validateRegContactPersonInfo() {
	var name = $("#name").val();
	if (!name) {
		layer.tips("请输入联系人姓名 !", "#name");
		return false;
	}
	var identityCard = $("#identityCard").val();
	if (!identityCard) {
		layer.tips("请输入身份证号码 !", "#identityCard", {
			tips : 1
		});
		return false;
	} else {
		if (!identityCard.match(/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/)) {
			layer.tips("请输入有效的身份证号码 !", "#identityCard", {
				tips : 1
			});
			return false;
		}
	}
	var tel = $("#tel").val();
	if (!tel) {
		layer.tips("请输入电话号码 !", "#tel");
		return false;
	} else {
		if (!tel.match(/^(\d{4}-|\d{3}-)?(\d{8}|\d{7})$/)) {
			layer.tips("请输入有效的电话号码 !", "#tel");
			return false;
		}
	}
	var email = $("#email").val();
	if (!email) {
		layer.tips("请输入电子邮箱 !", "#email");
		return false;
	} else {
		if (!email.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/)) {
			layer.tips("请输入有效的电子邮箱 !", "#email");
			return false;
		}
	}
	return true;
}

/** 校验文件格式 */
function checkSuffix(filePath) {
	 var extStart = filePath.lastIndexOf(".");
    var ext = filePath.substring(extStart, filePath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        return false;
    }
    return true;
}

/** 校验文件大小 */
function checkFileSize(ele) {
	var size = ele.files[0].size / 1024;
   if (size > 1024) {
       return false;
   }
   return true;
}