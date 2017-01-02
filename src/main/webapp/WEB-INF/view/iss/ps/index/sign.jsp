<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
  <head>
    <jsp:include page="/WEB-INF/view/portal.jsp" />
    <script type="text/javascript">
    	$(function() {
    		$(document).keyup(function(event) {
    			if (event.keyCode == 13) {
    				login();
    			}
    		});
    	});

    	function login() {
    		if ($("#inputEmail").val() == "") {
/*     			layer.tips("请输入用户名", "#inputEmail", {
    				tips : 1
    			}); */
    		} else if ($("#inputPassword").val() == "") {
/*     			layer.tips("请输入密码", "#inputPassword", {
    				tips : 1
    			}); */
    		} else if ($("#inputCode").val() == "") {
    			layer.tips("请输入验证码", "#inputCode", {
    				tips : 1
    			});
    		} else {
    			var index = layer.load(0,{
    				  shade: [0.1,'#fff'],
    				  offset:['45%','53%']
    			}); 
    			$.ajax({
    				url : "${pageContext.request.contextPath}/login/login.html",
    				type : "post",
    				data : {
    					loginName : $("#inputEmail").val(),
    					password : $("#inputPassword").val(),
    					rqcode : $("#inputCode").val()
    				},
    				success : function(data) {
    					var flag = data.split(",");
    					if (data == "errorcode") {
    						layer.tips("验证码不正确", "#inputCode", {
    							tips : 1
    						});
    						getIdentityCode(1);
    						layer.close(index);
    					} else if (data == "errorlogin") {
    						layer.msg("用户名或密码错误！");
    						layer.close(index);
    					} else if (data == "nullcontext") {
    						layer.msg("请输入用户名密码或者验证码!");
    					} else if (data == "scuesslogin") {
    						layer.close(index);
    						window.location.href = "${pageContext.request.contextPath}/login/index.html";
    					} else if (data == "black") {
    						layer.msg("对不起，您已被列入黑名单!");
    						layer.close(index);
    					} else if (data == "reject") {
    						layer.msg("对不起，您的审核没有通过!");
    						layer.close(index);
    					} else if (flag[0] == "firset") {
    						//询问框
    						layer.confirm('您还未完善个人信息，是否前去完善？', {
    							btn : [ '是', '否' ]
    						//按钮
    						}, function() {
    							window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=" + flag[1];
    						}, function() {
    							layer.close(index);
    							window.location.href = "${pageContext.request.contextPath}/";
    						});
    					} else if (flag[0] == "reset") {
    						//询问框
    						layer.confirm('您提交的审核被退回，是否前去修改？', {
    							btn : [ '是', '否' ]
    						//按钮
    						}, function() {
    							window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=" + flag[1];
    						}, function() {
    							layer.close(index);
    							window.location.href = "${pageContext.request.contextPath}/";
    						});
    					} else if (data == "weed") {
    						layer.msg("抱歉,您已被踢除,无法登陆！");
    						layer.close(index);
    					} else if (flag[0] == "auditExp") {
    						$.ajax({
    							url: "${pageContext.request.contextPath}/expert/validateAuditTime.do",
    							data: {"userId" : flag[1]},
    							dataType: "json",
    							async: false,
    							success: function(response){
    								if (response.isok == "1") {
    									// 没有超过45天
    									//询问框
    		    						layer.confirm("您的信息已于" + response.submitDate + "提交审核,将于45天内审核完成,请耐心等待!", {
    		    							btn : [ '确定' ]
    		    						//按钮
    		    						}, function() {
    		    							window.location.href = "${pageContext.request.contextPath}/";
    		    						});
    								} else {
    									// 超过45天
    									//询问框
    		    						layer.confirm("您的信息提交审核已经超过45天,请耐心等待或联系相关初审机构(" + response.contact + ":" + response.contactTelephone + ")!", {
    		    							btn : [ '确定' ]
    		    						//按钮
    		    						}, function() {
    		    							window.location.href = "${pageContext.request.contextPath}/";
    		    						});
    								}
    							}
    						});
    					}else if(flag[0]=="unperfect"){
    						//询问框
    						layer.confirm('信息未完善，是否前去完善？', {
    						 	btn: ['是','否'] //按钮
    						}, function(){
    						  window.location.href="${pageContext.request.contextPath}/supplier/login.html?name="+flag[1];
    						 	}, function(){
    						 		layer.close(index);
    						 		window.location.href="${pageContext.request.contextPath}/";
    						 	    });
    					}else if(flag[0]=="reject"){
    						//询问框
    						layer.confirm('您提交的审核被退回，是否前去修改？', {
    						 	btn: ['是','否'] //按钮
    						}, function(){
    						  window.location.href="${pageContext.request.contextPath}/supplier/login.html?name="+flag[1];
    						 	}, function(){
    						 		layer.close(index);
    						 		window.location.href="${pageContext.request.contextPath}/";
    						 	    });
    					} else if (data == "firstNotPass") {
    						layer.msg("抱歉,您的审核没有通过,无法登陆！");
    						layer.close(index);
    					} else if (data == "secondNotPass") {
    						layer.msg("抱歉,您的复核没有通过,无法登陆！");
    						layer.close(index);
    					} else if (data == "thirdNotPass") {
    						layer.msg("抱歉,您的实地考察不合格,无法登陆！");
    						layer.close(index);
    					} else if(flag[0]=="commit"){
    						$.ajax({
    							url: "${pageContext.request.contextPath}/supplier/validateAuditTime.do",
    							data: {"userId" : flag[1]},
    							dataType: "json",
    							async: false,
    							success: function(response){
    								if (response.isok == "1") {
    									// 没有超过45天
    									//询问框
    		    						layer.confirm("您的信息已于" + response.submitDate + "提交审核,将于45天内审核完成,请耐心等待!", {
    		    							btn : [ '确定' ]
    		    						//按钮
    		    						}, function() {
    		    							window.location.href = "${pageContext.request.contextPath}/";
    		    						});
    								} else {
    									// 超过45天
    									//询问框
    		    						layer.confirm("您的信息提交审核已经超过45天,请耐心等待或联系相关初审机构(" + response.contact + ":" + response.contactTelephone + ")!", {
    		    							btn : [ '确定' ]
    		    						//按钮
    		    						}, function() {
    		    							window.location.href = "${pageContext.request.contextPath}/";
    		    						});
    								}
    							}
    						});    
    					} else if (flag[0] == "reset") {
    						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=" + flag[1];
    					} else if (data == "outer_net_limit") {
    						layer.msg("管理员账号请在内网登录");
    						layer.close(index);
    					} else if (data = "deleteLogin") {
    						layer.msg("账号不存在!");
    						layer.close(index);
    					} 
    					getIdentityCode();
    				}
    			});
    		}

    	}
    	function kaptcha() {
    		$("#kaptchaImage").hide().attr('src', 'Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
    	}

    	function getIdentityCode(sign) {
    		var random = Math.random();
    		$("#identity_code_img_id_" + sign).hide().attr("src", "${pageContext.request.contextPath}/supplier/get_identity.html?random" + random).fadeIn("fast", function() {
    			getCode();
    		});
    	}
    	
    	function getCode() {
    		$.ajax({
    			url : "${pageContext.request.contextPath}/my_test/auto_write.do",
    			type : "post",
    			dataType : "json",
    			success : function(result) {
    				$("#inputCode").val(result);
    			}
    		});
    	}
    	/*window.onload = function() {
    		getCode();
    	};

    	function getCode() {
    		$.ajax({
    			url : "${pageContext.request.contextPath}/my_test/auto_write.do",
    			type : "post",
    			dataType : "json",
    			success : function(result) {
    				$("#inputCode").val(result);
    			}
    		});
    	}*/
    </script>
  </head>

  <body class="sign_bg">
  	<div class="container content login_content">
  		<div class="row">

  			<div class='login-logo col-md-8 col-sm-6 col-xs-12'>
  			  <a href="${pageContext.request.contextPath}/index/selectIndexNews.html">
  				<img src="${pageContext.request.contextPath}/public/portal/images/logo.png" width="50%" height="90%" />
  			  </a>
  			</div>

  			<div class="col-md-4 col-sm-5 col-xs-12"></div>

  			<div class="container content login_content">
  				<div class="row">
  					<div class="sign_left col-md-6 col-sm-12 col-xs-12">
  						<img src="${pageContext.request.contextPath}/public/portal/images/newsign_left.jpg" class='sign_left_image'/>
  					</div>

  					<div class="col-md-5 col-sm-12 col-xs-12 login_right">
  						<div class="col-md-10 col-sm-12 col-xs-12 clear">
  							<div class="shadow-effect-2 opacity-80 sign_box">

  								<header class="margin-top-10 ofh">
  									<ul class="list-unstyled sign_kinds col-md-12 p0">
  										<li class="active fl col-md-5"><a aria-expanded="true"
  											href="#tab-1" data-toggle="tab" class="col-md-12"> <!--<span class="icon-user common_user"></span> -->
  												用户登录
  										</a></li>
  										<li class="fl col-md-5"><a aria-expanded="false"
  											href="#tab-2" data-toggle="tab" class="col-md-12"> <!--<span class="icon-user ca-user" ></span>-->
  												CA登录
  										</a></li>
  									</ul>
  								</header>

  								<div class="tab-content reg-page">
  									<div class="tab-pane active in" id="tab-1">

  										<form accept-charset="UTF-8" class="sky-form" method="post">
  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">用户名</label>
  													<div class="col col-md-8 col-sm-12 col-xs-12">
  														<label class="input"> <input id="inputEmail"
  															name="" placeholder="请输入用户名" type="text">
  														</label>
  													</div>
  												</div>
  											</section>
  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">密
  														码</label>
  													<div class="col col-md-8 col-sm-12 col-xs-12">
  														<label class="input"> <input id="inputPassword"
  															name="" placeholder="请输入密码" type="password">
  														</label>
  														<div class="note"></div>
  													</div>
  												</div>
  											</section>

  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">验证码</label>
  													<div class="col col-md-8 col-sm-12 col-xs-12">
  														<label class="input">
  															<div class="col-md-6 col-sm-6 col-xs-6 pl0">
  																<input id="inputCode" type="text" placeholder="" class="fl col-md-12">
  															</div>
  															<div class="col-md-6 col-sm-6 col-xs-6 p0">
  																<img id="identity_code_img_id_0" style="border: 1px solid #2c9fa6; cursor: pointer;" height="34px" class="hand w100p"
  																	src="${pageContext.request.contextPath}/supplier/get_identity.html"
  																	onclick="getIdentityCode(0)">
  															</div>
  														</label>
  														<div class="note"></div>
  													</div>
  												</div>
  											</section>

  											<div class="content border-top-1 tc">
  												<button type="button" onclick="login();"
  													class="btn login_btn">登 录</button>
  											</div>

  										</form>
  									</div>
  									<div class="tab-pane" id="tab-2">
  										<form accept-charset="UTF-8" class="sky-form"
  											method="post">
  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">用户名</label>
  													<div class="col col-md-8 col-sm-12 col-xs-12">
  														<label class="input"> <input id="" name=""
  															placeholder="用户名" type="text">
  														</label>
  													</div>
  												</div>
  											</section>

  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">密 码</label>
  													<div class="col col-md-8 col-sm-12 col-xs-12">
  														<label class="input"> <input id="" name=""
  															placeholder="密 码" type="password">
  														</label>
  														<div class="note"></div>
  													</div>
  												</div>
  											</section>

  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">验证码</label>
  													<div class="col col-md-8 col-sm-12 col-xs-12">
  														<label class="input">
  															<div class="col-md-6 col-sm-6 col-xs-6 pl0">
  																<input type="password" placeholder="" class="fl col-md-12">
  															</div>
  															<div class="col-md-6 col-sm-6 col-xs-6 p0">
  																<img id="identity_code_img_id_1"
  																	style="border: 1px solid #2c9fa6; cursor: pointer;"
  																	height="34px" class="hand w100p"
  																	src="${pageContext.request.contextPath}/supplier/get_identity.html"
  																	onclick="getIdentityCode(1)">
  															</div>
  														</label>
  														<div class="note"></div>
  													</div>
  												</div>
  											</section>

  											<div class="content border-top-1 tc">
  												<button type="button" class="btn login_btn"
  													onclick="login();">登 录</button>
  											</div>
  										</form>

  									</div>
  								</div>
  							</div>
  						</div>
  					</div>
  				</div>
  			</div>
  		</div>

  		<div class="container clear tc login-footer">
  			<address class="">Copyright © 2016 版权所有：中央军委后勤保障部
  				京ICP备09055519号</address>
  			<div class="">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</div>
  		</div>

  	</div>
  </body>
</html>
