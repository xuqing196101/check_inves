<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
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
	   var loginName = $("input[name='loginName']").val();
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
		   url:"<%=basePath%>expert/findAllLoginName.do",
		   type:"post",
		   data:{"loginName":loginName},
		   success:function(obj){
			   if(obj.length>0){
				   $("#spp").html("用户名已存在").css('color','red');
				   flag=1;
				   return false;
			   }else{
				   $("#spp").html("OK!").css('color','lime');
				   flag=2;
				   return true;
			   }
		   }
	   });
   }
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
		   $("#pwd1").html("密码必须六位以上").css('color','red');
		   flag2=1;
		   return false;
	   }else{
	   $("#pwd1").html("OK!").css('color','lime');
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
		   $("#pwd2").html("OK!").css('color','lime');
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
   
   </script>

</head>
<body>
 
 
  
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/register.do" method="post"  id="form1">
   		<%
			session.setAttribute("tokenSession", tokenValue);
		 %>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
		 <input type="hidden" id="message" value="${message }"/>
   <div>
   <jsp:include page="../../../../indexhead.jsp"></jsp:include>
   <div class="container clear margin-top-30" >
   			<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
	</div>
	<br/><br/>
   <ul class="list-unstyled list-flow" style="margin-left: 250px;">
     		<li class="p0">
			   <span class="">用户名：</span>
			   <div class="input-append">
		        <input class="span2" name="loginName" placeholder="用户名为3~8位" maxlength="8" type="text" onblur="validataLoginName();" value="">
		        <span class="add-on">i</span>
		       </div><font  id="spp"></font>
			 </li>
			 
		    
		      <li class="p0 ">
			   <span class="">密码：</span>
			   <div class="input-append">
		        <input class="span2" name="password" placeholder="密码为6~20位" maxlength="20" id="password1" onblur="validataPassword();"  type="password" >
		        <span class="add-on">i</span>
		       </div><font  id="pwd1"></font>
			 </li> 
	 		
			  <li class="p0 ">
			   <span class="">确认密码：</span>
			   <div class="input-append">
		        <input class="span2" id="password2"  maxlength="20" onblur="validataPwd2();" type="password" value="">
		        <span class="add-on">i</span>
		       </div><font  id="pwd2"></font>
			 </li> 
			 <li class="p0 ">
			   <span class="">手机号码：</span>
			   <div class="input-append">
		        <input class="span2" name="mobile" placeholder="请输入正确的手机号码" maxlength="14" id="phone" onblur="validataPhone();"  value="" type="text">
		        <span class="add-on">i</span>
		       </div>
		        <input class="btn" type="button" value="发送验证码"><font  id="phone2"></font>
			 </li>
			 <li class="p0">
			   <span class="">验证码：</span>
			   <div class="input-append">
		        <input class="span2" name="phone" maxlength="6" type="text" value="">
		        <span class="add-on">i</span>
		       </div><font  id="yzm"></font>
			 </li>
   </ul>
  </div> 
  <div  class="col-md-12">
   <div class="padding-10" align="center">
   <input class="btn btn-windows add" type="button" onclick="submitForm();" value="开始注册">
	</div>
  </div>
  </form>
 </div>
 <br/>
 <!--底部代码开始-->
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->

<!--/footer--> 
      </div>
</div>
</body>
</html>
