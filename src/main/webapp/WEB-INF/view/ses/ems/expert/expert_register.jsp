<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="/WEB-INF/view/common/tags.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/front.jsp"></jsp:include>
<%
	//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
 <script type="text/javascript">
   $(function(){
	   var message = $("#message").val();
	   $("#massage").html(message).css('color','red');
   });
 //用户信息验证
   var flag = 1;
   var flag2 = 1;
   var flag3 = 1;
   var flag4 = 1;
   function validataLoginName(){
	   var loginName = $("#loginName").val();
	   var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
	   //var patrn2=/^(?=.*[a-z])[a-z0-9]+/ig;
	   if(loginName.replace(/\s/g,"")==null || loginName.replace(/\s/g,"")==""){
		   $("#spp").html("用户名不能为空").css('color','red');
		   flag=1;
		   return false;
	   }
		
		if(loginName.indexOf(" ")!=-1){
			$("#spp").html("不能有空格").css('color','red');
			flag=1;
			return false;
		}
		if(patrn.test(loginName)){  
			$("#spp").html("不能有非法字符").css('color','red');
			flag=1;
			return false;
		}
	   if(/[\u4e00-\u9fa5]/.test(loginName)){  
			$("#spp").html("不能有中文").css('color','red');
			flag=1;
			return false;
		}
	   if(loginName.replace(/\s/g,"").length<3){
		   $("#spp").html("必须三位以上").css('color','red');
		   flag=1;
		   return false;
		   
	   }
	  
	    $.ajax({
		   url:'${pageContext.request.contextPath}/expert/findAllLoginName.do',
		   type:"post",
		   data:{"loginName":loginName},
		   success:function(obj){
			   if(obj=='1'){
				   $("#spp").html("用户名已存在").css('color','red');
				   flag=1;
				   return false;
			   }else{ 
				   $("#spp").html("");
				   flag=2;
				   return true;
			    }
		   }
	   }); 
   }
   function validatePhone(){
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
			$.ajax({
				url:'${pageContext.request.contextPath}/expert/validatePhone.do',
				type:"post",
				data:{"phone":phone},
				success:function(obj){
					if(obj=='1'){
						$("#phone2").html("该手机号码已被使用!").css('color','red');
						flag4=1;
						return false;
					}else{ 
						$("#phone2").html("");
						flag4=2;
						return true;
					}
				}
			}); 
		}
   	}
   	function submitForm1(){
   		validataLoginName();
   		validataPassword();
   		validataPwd2();
   		validatePhone();
	 	if(flag==2 && flag2==2 && flag3==2&&flag4==2){
			$("#form1").submit();
	 	}
   	}
   </script>

</head>
<body>
 	 <jsp:include page="/index_head.jsp"></jsp:include>
<!-- 修改订列表开始-->
   <div class="container">
   <form action="${pageContext.request.contextPath}/expert/register.html" method="post"  id="form1">
	 <input type="hidden"  name="token2" value="<%=tokenValue%>">
	 <input type="hidden" id="message" value="${message }"/>
   <div>
	<br/><br/>
   <ul class="list-unstyled list-flow" style="margin-left: 250px;">
     		<li class="p0">
			   <span class=""><i class="red mr5">*</i>用户名：</span>
			   <div class="input-append">
		        <input class="span2" name="loginName" id="loginName" placeholder="用户名为3~8位" maxlength="8" type="text" onblur="validataLoginName();" value="">
		        <span class="add-on">i</span>
		       </div><font  id="spp"></font>
			 </li>
		      <li class="p0 ">
			   <span class=""><i class="red mr5">*</i>密码：</span>
			   <div class="input-append">
		        <input class="span2" name="password" placeholder="密码为6~20位" maxlength="20" id="password1" onblur="validataPassword();"  type="password" >
		        <span class="add-on">i</span>
		       </div><font  id="pwd1"></font>
			 </li> 
	 		
			  <li class="p0 ">
			   <span class=""><i class="red mr5">*</i>确认密码：</span>
			   <div class="input-append">
		        <input class="span2" id="password2"  maxlength="20" onblur="validataPwd2();" type="password" value="">
		        <span class="add-on">i</span>
		       </div><font  id="pwd2"></font>
			 </li> 
			 <li class="p0 ">
			   <span class=""><i class="red mr5">*</i>手机号码：</span>
			   <div class="input-append">
		        <input class="span2" name="mobile" placeholder="请输入正确的手机号码" maxlength="14" id="phone" onblur="validatePhone();"  value="" type="text">
		        <span class="add-on">i</span>&nbsp;<input class="btn" type="button" value="发送验证码"><font  id="phone2"></font>
		       </div>
		        
			 </li>
			 <li class="p0">
			   <span class=""><i class="red mr5">*</i>验证码：</span>
			   <div class="input-append">
		        <input class="span2" name="yzm" maxlength="6" type="text" value="">
		        <span class="add-on">i</span>
		       </div><font  id="yzm"></font>
			 </li>
   </ul>
  </div> 
	  <div  class="col-md-12">
	   <div class="padding-10" align="center">
		   <button class="btn btn-windows reset"  type="button" onclick="location.href='javascript:history.go(-1);'"> 返回</button>
		   <button class="btn btn-windows add"    type="button" onclick="submitForm1();"  >注册</button>
		</div>
	 </div>
  </form>
 </div>
 <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
