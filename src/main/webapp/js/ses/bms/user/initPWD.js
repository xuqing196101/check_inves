/**
 * 验证 旧密码
 * 
 * @returns {Number}
 */
function ajaxOldPassword() {
	$("#ajaxOldPassword").html("");
	var is_error = 0;
	var u = new Object();
	u.id = $("#userId").val();
	if (u.id) {
		pwd = setPublicKey($("#oldPassword").val());
		u.password = pwd;
		$.ajax({
			type : "POST",
			async : false,
			url : globalPath + "/user/ajaxOldPassword.do",
			contentType : "application/json",
			data : JSON.stringify(u),
			success : function(data) {
				data = JSON.parse(data);
				if (!data.success) {
					is_error = 1;
					$("#ajaxOldPassword").html(data.msg);
				} else {
					$("#ajaxOldPassword").html("");
				}
			}
		});
	}
	return is_error;
}
/**
 * 强制修改密码
 * 
 * @returns {Boolean}
 */
function initPasswSubmit() {
	var inde = layer.load(0, {
		shade : [ 0.1, '#fff' ],
		offset : [ '45%', '53%' ]
	});
	var is_error = ajaxOldPassword();
	if (is_error == 1) {
		layer.close(inde);
		return false;
	} else {
		$("#password").val(setPublicKey($("#password").val()));
		$("#password2").val(setPublicKey($("#password2").val()));
		$.ajax({
			type : "POST",
			url : globalPath + "/user/resetPwd.do",
			data : $('#form2').serializeArray(),
			dataType : 'json',
			success : function(result) {
				if (!result.success) {
					layer.close(inde);
					layer.msg(result.msg, {
						offset : [ '150px' ]
					});
					$("#password").val();
					$("#password2").val();
				} else {
					layer.confirm(result.msg + ",请重新登录", {
						btn : [ '确定' ]
					}, function() {
						// 重新登陆
						window.location.href = globalPath
								+ '/login/loginOut.do';
						layer.close(inde);
					});
				}
			},
			error : function(result) {
				layer.msg("重置失败", {
					offset : [ '222px' ]
				});
				$("#password").val();
				$("#password2").val();
				layer.close(inde);
			}
		});
	}
}
/**
 * 个人修改密码
 * 
 * @returns {Boolean}
 */
function resetPasswSubmit() {
	var inde = layer.load(0, {
		shade : [ 0.1, '#fff' ],
		offset : [ '45%', '53%' ]
	});
	//var is_error = ajaxOldPassword();
	var is_error = 0;
	if (is_error == 1) {
		layer.close(inde);
		return false;
	} else {
		
		$("#oldPassword").val(setPublicKey($("#oldPassword").val()));
		$("#password").val(setPublicKey($("#password").val()));
		$("#password2").val(setPublicKey($("#password2").val()));
		$.ajax({
			type : "POST",
			url : globalPath + "/user/resetPwd.do",
			data : $('#form2').serializeArray(),
			dataType : 'json',
			success : function(result) {
				if (!result.success) {
					layer.close(inde);
					layer.msg(result.msg, {
						offset : [ '150px' ]
					});
					$("#password").val("");
					$("#password2").val("");
				} else {
					layer.msg(result.msg, {
						offset : [ '222px' ]
					});
					layer.close(inde);
				}
			},
			error : function(result) {
				layer.msg("重置失败", {
					offset : [ '222px' ]
				});
				$("#password").val();
				$("#password2").val();
				layer.close(inde);
			}
		});
	}
}
/**
 * 后台用户管理 重置密码
 */
function userResetPasswSubmit() {
	var inde = layer.load(0, {
		shade : [ 0.1, '#fff' ],
		offset : [ '45%', '53%' ]
	});
	$("#oldPassword").val(setPublicKey($("#oldPassword").val()));
	$("#password").val(setPublicKey($("#password").val()));
	$("#password2").val(setPublicKey($("#password2").val()));
	$.ajax({
		type : "POST",
		url : globalPath + "/user/resetPwdForUser.do",
		data : $('#form2').serializeArray(),
		dataType : 'json',
		success : function(result) {
			if (!result.success) {
				layer.close(inde);
				layer.msg(result.msg, {
					offset : [ '150px' ]
				});
				$("#password").val("");
				$("#password2").val("");
			} else {
				layer.confirm(result.msg, {
					btn : [ '确定' ]
				}, function() {
					cancel();
				});
			}
		},
		error : function(result) {
			layer.msg("重置失败", {
				offset : [ '222px' ]
			});
			$("#password").val();
			$("#password2").val();
			layer.close(inde);
		}
	});
}
// 重置 供应商/专家密码
function supplierResetPasswSubmit() {
	var inde = layer.load(0, {
		shade : [ 0.1, '#fff' ],
		offset : [ '45%', '53%' ]
	});
	$("#oldPassword").val(setPublicKey($("#oldPassword").val()));
	$("#password").val(setPublicKey($("#password").val()));
	$("#password2").val(setPublicKey($("#password2").val()));
	$.ajax({
		type : "POST",
		url : globalPath+"/user/setPassword.html",
		data : $('#form2').serializeArray(),
		dataType : 'json',
		success : function(result) {
			if (result == "重置密码成功" || result == "重置失败") {
				layer.closeAll();
			}
			layer.msg(result, {
				offset : [ '222px' ]
			});
			$("#password").val("");
			$("#password2").val("");
			layer.close(inde);
		},
		error : function(result) {
			layer.msg("重置失败", {
				offset : [ '222px' ]
			});
			$("#password").val("");
			$("#password2").val("");
			layer.close(inde);
		}
	});
}