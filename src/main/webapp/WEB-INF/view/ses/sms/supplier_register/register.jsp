<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
<script type="text/javascript">
	$(function() {
//		document.getElementById("login_input_id").focus();// 用户名自动获取焦点

		/** ajax 校验用户名是否存在 */
        $("#login_input_id").focus(function(){
            $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
        }).blur(function(){
            var oldVal=($(this).attr("data-oval")); //获取原值
            var newVal=($(this).val()); //获取当前值
            if (oldVal!=newVal){
                //值改变
                var loginName = $(this).val();
                if (loginName != null && loginName != "" && loginName !="null" && loginName !="undefined"){
                    $.ajax({
                        url : "${pageContext.request.contextPath}/supplier/check_login_name.do",
                        type : "post",
                        data : {
                            loginName : loginName
                        },
                        dataType : "json",
                        success : function(result) {
                            result = eval("(" + result + ")");
                            if (result.msg == "fail") {
                                $("#login_input_id").next().text("用户名已被使用，请更换重试！");
                                $("#submit_button_id").prop("disabled", true);
                            } else {
                                $("#login_input_id").next().text("");
                                $("#submit_button_id").prop("disabled", false);
                            }
                        }
                    });
                }
            }
        });
        
		/** ajax 校验手机号是否存在 */
        $("#mobile").focus(function(){
            $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
        }).blur(function(){
            var oldVal=($(this).attr("data-oval")); //获取原值
            var newVal=($(this).val()); //获取当前值
            if (oldVal!=newVal){
                //值改变
                var mobile = $(this).val();
                if (mobile != null && mobile != "" && mobile !="null" && mobile !="undefined"){
                    $.ajax({
                        url : "${pageContext.request.contextPath}/supplier/check_mobile.do",
                        type : "post",
                        data : {
                            mobile : mobile
                        },
                        dataType : "json",
                        success : function(result) {
                            result = eval("(" + result + ")");
                            if (result.msg == "fail") {
                                $("#mobile").next().text("手机号已被使用，请更换重试！");
                                $("#submit_button_id").prop("disabled", true);
                            } else {
                                $("#mobile").next().text("");
                                $("#submit_button_id").prop("disabled", false);
                            }
                        }
                    });
                }
            }
        });

    });
	var count = 0;
	function getIdentityCode() {
		var random = Math.random();
		$("#identity_code_img_id").hide().attr("src", "${pageContext.request.contextPath}/supplier/get_identity.html?random" + random).fadeIn();
	}
	function register(){
	 $("[name=password]").val(setPublicKey($("[name=password]").val()));
	 $("[name=confirmPassword]").val(setPublicKey($("[name=confirmPassword]").val()));
	}
</script>

</head>

<body>
	<div class="wrapper">
		<div class="container clear margin-top-30">
			<div class="col-md-12 col-sm-12 col-xs-12 margin-top-40">
				<div class="row" style="background-color:#f6f6f6;">
					<div class="mt60 col-md-6 col-sm-6 col-xs-12 p20">
					<div class="login_item col-md-12  col-sm-12 col-xs-12">
						<div class="col-md-10 col-xs-10 col-sm-10 p0" style="margin-top: -50px;font-size: 18px">供应商账号注册：</div>
					  <div class="col-md-10 col-xs-10 col-sm-10 p0">
					    <div class="msg-wrap hide">
	              <div class="msg-error"><b></b>请输入密码</div>
              </div>
            </div>
           </div>
						<form action="${pageContext.request.contextPath}/supplier/register.html" method="post">
							<div class="login_item col-md-12  col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"> <i class="red mr5">*</i>用户名：</label>
								<div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
							     <input id="login_input_id" type="text" name="loginName" placeholder="由6-20位字母和数字组成" value="${supplier.loginName}"  class="col-md-12 col-sm-12 col-xs-12">
								   <span class="cue">${err_msg_loginName }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12  col-sm-12 col-xs-12 ">
								<label class="col-md-3 col-sm-12 col-xs-12  p0"><i class="red mr5">*</i>登录密码：</label> 
								 <div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
								  <input type="password" name="password" value="" placeholder="密码长度为6-20位" autocomplete="off" class="col-md-12 col-sm-12 col-xs-12">
								  <span class="cue" >${err_msg_password }</span> 
								</div>
								
							</div>
							<div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12 ">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>确认密码：</label> 
								 <div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
								   <input type="password" name="confirmPassword" value="" placeholder="请再次输入密码" autocomplete="off" class="col-md-12 col-sm-12 col-xs-12">
								   <span class="cue">${err_msg_ConfirmPassword }</span> 
								</div>
							</div>
							<div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>手机号码：</label> 
								 <div class="input-append col-md-7 col-xs-12 col-sm-12 p0 input_group">
								   <input type="text" id="mobile" name="mobile" value="${supplier.mobile}" class="col-md-12 col-sm-12 col-xs-12">
								   <!-- <button type="button" class="btn ml10">发送验证码</button> -->
								   <span class="cue">${err_msg_mobile }</span> 
								</div>
							</div>
							<%-- <div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>手机验证码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								    <input type="text" name="mobileCode" value="${supplier.mobileCode}" class="col-md-12 col-sm-12 col-xs-12">
							        <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_mobileCode }</span> 
								</div>
							</div> --%>
							<!-- <div class="login_item margin-top-10 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-12 col-xs-12 p0"><i class="red mr5">*</i>验证码：</label> 
								<div class="col-md-7 col-xs-12 col-sm-12 p0">
								    <input type="text" name="identifyCode" id="identifyCode" value="${supplier.identifyCode}" class="col-md-4 col-sm-3 col-xs-4">
								    <div class="fl">
								       <div class="yzm fl">
										<img id="identity_code_img_id" class="hand" src="${pageContext.request.contextPath}/supplier/get_identity.html" onclick="getIdentityCode()" />
									   </div>
									   <button class="btn ml10 fl" onclick="getIdentityCode()">换一张</button>
								    </div>
								 <span class="red clear col-md-12 col-xs-12 col-sm-12 p0">${err_msg_code }</span> 
								</div>
							</div> -->
							<input type="hidden" name="id" value="${id }">
							<div class="tc mt10 clear col-md-12 col-sm-12 col-xs-12">
								<button id="submit_button_id" type="submit" onclick="register()" class="btn margin-5">注册</button>
								<button type="button" class="btn margin-5" onclick="location='${pageContext.request.contextPath}/supplier/registration_page.html'">返回</button>
							</div>
						</form>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<img src="${pageContext.request.contextPath}/public/front/images/sup_login.jpg" width="100%" />
					</div>
				</div>
				
			</div>
			
		</div>
	</div>
	<div class="footer_margin">
   		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
   </div>
</body>
</html>

			
