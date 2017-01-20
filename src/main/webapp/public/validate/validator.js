(function ($){
	$.fn.extend({
		validForm: function (){
			$(this).validate({  
				errorPlacement:function(error,element) {  
					$(element).nextAll(".cue").text($(error).text());
			   },
			   debug:false,
			   success: function(){
			   }
			});
		}
	});
})(jQuery);

//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) { 
  var length = value.length; 
  var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
  return this.optional(element) || (length == 11 && mobile.test(value)); 
}, "请输入正确的手机号码"); 

// 电话号码验证 
jQuery.validator.addMethod("isTel", function(value, element) { 
  var tel = /^\d{3,4}-?\d{7,9}$/; //电话号码格式010-12345678 
  return this.optional(element) || (tel.test(value)); 
}, "请输入正确的电话号码"); 

// 联系电话(手机/电话皆可)验证 
jQuery.validator.addMethod("isPhone", function(value,element) { 
  var length = value.length; 
  var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
  var tel = /^\d{3,4}-?\d{7,9}$/; 
  return this.optional(element) || (tel.test(value) || mobile.test(value)); 
}, "请输入正确的联系电话"); 

// 邮政编码验证 
jQuery.validator.addMethod("isZipCode", function(value, element) { 
  var tel = /^[0-9]{6}$/; 
  return this.optional(element) || (tel.test(value)); 
}, "请输入正确的邮政编码"); 

// 传真
jQuery.validator.addMethod("isFax", function(value, element){
	return this.optional(element) || /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(value);
}, "请输入正确的传真格式");

// 网址验证
jQuery.validator.addMethod("isUrl", function(value, element) { 
	var url = /^((https?|ftp|news):\/\/)?([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/; 
	return this.optional(element) || (url.test(value));
}, "请输入正确的网址"); 