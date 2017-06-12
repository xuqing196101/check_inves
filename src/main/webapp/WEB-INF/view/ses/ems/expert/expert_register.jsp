<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%
	//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>

<%@ include file="/reg_head.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
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
	   if(loginName.replace(/\s/g,"").length<6){
		   $("#spp").html("登录名由6-20位字母或数字组成 ").css('color','red');
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
				   flag = 2;
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
		   $("#phone2").html("手机号码格式不正确").css('color','red');
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
//    function validateFrom(){
// 	   var from = $("#expertsFrom").val();
// 	   if (from == "" || from == null) {
// 		   $("#fro").html("请选择专家来源!").css('color','red');
// 		   return false;
// 	   }
//    }
   	function submitForm1(){
   		validataLoginName();
//    		validateFrom();
   		validataPassword();
   		validataPwd2();
   		validatePhone();
	 	if(flag==2 && flag2==2 && flag3==2&&flag4==2){
	 	$("#password1").val(setPublicKey($("#password1").val()));
        $("#password2").val(setPublicKey($("#password2").val()));
			$("#formExpert").submit();
	 	}
   	}
   </script>

</head>
<body>
   <!-- 修改订列表开始-->
  <div class="container clear margin-top-30">
    <div class="col-md-12 col-sm-12 col-xs-12 margin-top-40">
	  <div class="row" style="background-color:#f6f6f6;">
		<div class="col-md-6 col-sm-6 col-xs-12 p20">
   			<form action="${pageContext.request.contextPath}/expert/register.html" method="post"  id="formExpert">
			   <input type="hidden"  name="token2" value="<%=tokenValue%>">
		       <input type="hidden" id="message" value="${message }"/>
		       <em>
		       <div class="col-md-10 col-xs-10 col-sm-10 p0" style="margin-left: 15px;font-size: 18px;font-style:normal">评审专家账号注册：
		       <c:if test="${ipAddressType=='1'}">
		       </br><span class="red" style="font-size:16px">(军队单位人员请登录内网进行注册！)</span>
		       </c:if>
		       </div>
		       </em>
            <ul class="list-unstyled overflow_h mt20 col-md-12 col-sm-12 col-xs-12">
			 <li class="login_item col-md-12  col-sm-12 col-xs-12 pl10">
			  <span class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>专家类型：</span>
			  <div class="col-md-7 col-xs-12 col-sm-12 p0 select_common">
		        <select  name="expertsFrom" id="expertsFrom"  disabled="disabled">
<!-- 				  <option selected="selected" value="">-请选择-</option> -->
				  <c:forEach items="${lyTypeList}" var="ly">
				    <c:if test="${ipAddressType eq  ly.id}">
  				    <option value="${ly.id}">${ly.name}</option>
				    </c:if>
				  </c:forEach>
				</select>
				 <div id="fro" class="cue"></div>
		      </div>
			</li>
     		 <li class="login_item col-md-12  col-sm-12 col-xs-12">
			   <span class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>用户名：</span>
			  <div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
		        <input name="loginName" id="loginName" placeholder="用户名由6-20位字母或数字组成" maxlength="20" type="text" onkeyup="validataLoginName();" value="">
		        <div id="spp" class="cue"></div>
		       </div>
			 </li>
		     <li class="login_item col-md-12  col-sm-12 col-xs-12">
			   <span class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>密码：</span>
			   <div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
		        <input name="password" placeholder="密码由6~20位字母或数字组成" maxlength="20" id="password1" autocomplete="off" onkeyup="validataPassword();"  type="password" >
		        <div id="pwd1" class="cue"></div>
		       </div>
			 </li> 
	 		 <li class="login_item col-md-12  col-sm-12 col-xs-12">
			   <span class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>确认密码：</span>
			   <div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
		        <input id="password2"  maxlength="20" onkeyup="validataPwd2();" type="password" autocomplete="off" value="">
		        <div class="cue" id="pwd2">
		       </div>
			 </li> 
			 <li class="login_item col-md-12  col-sm-12 col-xs-12">
			   <span class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>手机号码：</span>
			   <div class="col-md-7 col-xs-12 col-sm-12 p0 input-append input_group">
			    <!--<div class="col-md-12 col-sm-12 col-xs-12 pl0">-->
			    <div class="col-md-12 col-sm-12 col-xs-12 p0">
		          <input name="mobile" placeholder="请输入正确的手机号码" maxlength="14" id="phone" onkeyup="validatePhone();"  value="" type="text">
		        </div>
		        <!-- <button type="button" class="btn ml10">发送验证码</button>-->
		        <div class="cue" id="phone2"></div>
		       </div>  
			 </li>
			 <!-- <li class="p0">
			   <span class=""><i class="red mr5">*</i>验证码：</span>
			   <div class="input-append">
		        <input class="span2" name="yzm" maxlength="6" type="text" value="">
		        <span class="add-on">i</span>
		       </div><font  id="yzm"></font>
			 </li> -->
        </ul>
	  <div  class="col-md-12 col-sm-12 col-xs-12 tc">
		   <button class="btn btn-windows add"    type="button" onclick="submitForm1();"  >注册</button>
		   <button class="btn btn-windows reset"  type="button" onclick="location.href='javascript:history.go(-1);'"> 返回</button>
	 </div>
  </form>
 </div>
 <div class="col-md-6 col-sm-6 col-xs-12">
	<img src="${pageContext.request.contextPath}/public/front/images/sup_login.jpg" width="100%" />
</div>
 </div>
 </div>
 </div>
 <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>