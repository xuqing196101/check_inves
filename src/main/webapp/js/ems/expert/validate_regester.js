   function validataPassword(){
	   var password1 = $("#password1").val();
	   var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
	   if(patrn.test(password1)){  
			$("#pwd1").html("密码不能有非法字符").css('color','red');
			flag2=1;
			return false;
		}else
		if(password1.indexOf(" ")!=-1){
			$("#pwd1").html("密码中不能有空格").css('color','red');
			flag2=1;
			return false;
		}else
	   if(password1.replace(/\s/g,"")==null || password1.replace(/\s/g,"")==""){
		   $("#pwd1").html("密码不能为空").css('color','red');
		   flag2=1;
		   return false;
	   }else
	   if(password1.replace(/\s/g,"").length<6){
		   $("#pwd1").html("密码长度为6-20位").css('color','red');
		   flag2=1;
		   return false;
	   }else{
	   $("#pwd1").html("");
	   flag2=2;		   
	   }
   }
   function validataPwd2(){
	   var password1 = $("#password1").val();
	   var password2 = $("#password2").val();
	   if(password2.replace(/\s/g,"")==null || password2.replace(/\s/g,"")==""){
		   $("#pwd2").html("重复密码不能为空").css('color','red');
		   flag3=1;
		   return false;
	   }else
	   if(password1!=password2){
		   $("#pwd2").html("两次密码不一致").css('color','red');
		   flag3=1;
		   return false;
	   }else{
		   $("#pwd2").html("");
		   flag3=2;
		   return true;
	   }
   }
   function validataPhone(){
	   var phone = $("#phone").val();
	   if(phone.replace(/\s/g,"")==null || phone.replace(/\s/g,"")==""){
		   $("#phone2").html("手机号不能为空").css('color','red');
		   flag4=1;
		   return false;
	   }else if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))){ 
		   $("#phone2").html("手机号码格式错误").css('color','red');
		   flag4=1;
		   return false;
		}else{
			$("#phone2").html("");
			flag4=2;
			return true;
		}
   }
    function submitForm(){
    	validataLoginName();
    	validataPassword();
    	validataPwd2();
    	validataPhone();
	 if(flag==2 && flag2==2 && flag3==2&&flag4==2){
		 $("#form1").submit();
	 }
    }   