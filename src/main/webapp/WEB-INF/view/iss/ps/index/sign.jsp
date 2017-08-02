<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
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
   /*  		 $("#divPrompt").addClass("hide"); */
    		if ($("#inputEmail").val() == "") {
    			 $("#divPrompt").removeClass("hide");
    		    $("#spanPrompt").text("请输入用户名");
/*     			layer.tips("请输入用户名", "#inputEmail", {
    				tips : 1
    			}); */
    		} else if ($("#inputPassword").val() == "") {
    			$("#divPrompt").removeClass("hide");
    		    $("#spanPrompt").text("请输入密码");
/*     			layer.tips("请输入密码", "#inputPassword", {
    				tips : 1
    			}); */
    		} else if ($("#inputCode").val() == "") {
    			$("#divPrompt").removeClass("hide");
    			 $("#spanPrompt").text("请输入验证码");
    			/* layer.tips("请输入验证码", "#inputCode", {
    				tips : 1
    			}); */
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
    					password : setPublicKey($("#inputPassword").val()),
    					rqcode : $("#inputCode").val()
    				},
    				success : function(data) {
    					var flag = data.split(",");
    					if (data == "errorcode") {
    						 getIdentityCode(0);
    						 $("#divPrompt").removeClass("hide");
    						  $("#spanPrompt").text("验证码不正确");
    						  $("#inputCode").val("");
    						layer.close(index);
    					} else if (data == "errorlogin") {
    						$("#divPrompt").removeClass("hide");
    						 $("#spanPrompt").text("用户名或密码错误！");
    						getIdentityCode(0);
    						$("#inputCode").val("");
    						layer.close(index);
    					} else if (data == "errorNumMax") {
    						$("#divPrompt").removeClass("hide");
    						 $("#spanPrompt").text("您密码输入错误5次，账号被锁，请联系管理员解锁");
    						layer.close(index);
    					}  else if (data == "nullcontext") {
    						$("#divPrompt").removeClass("hide");
   						    $("#spanPrompt").text("请输入用户名密码或者验证码!");
    					} else if (data == "scuesslogin") {
    						layer.close(index);
    						window.location.href = "${pageContext.request.contextPath}/login/index.html";
    					} else if (data == "black") {
    						$("#divPrompt").removeClass("hide");
   						  	$("#spanPrompt").text("对不起，您已被列入黑名单!");
    						layer.close(index);
    					} else if (data == "review") {
    						$("#divPrompt").removeClass("hide");
   						  	$("#spanPrompt").text("初审已通过，待复审!");
   						    layer.close(index);
    					} else if (data == "notLogin") {
    						$("#divPrompt").removeClass("hide");
  						  	$("#spanPrompt").text("对不起，您参加的评审项目已结束!");
  							layer.close(index);
  						} else if (data == "reject") {
    						$("#divPrompt").removeClass("hide");
    					  	$("#spanPrompt").text("对不起，您的审核没有通过!");
    						layer.close(index);
                        } else if (flag[0] == "expert_logout") {
                            $("#divPrompt").removeClass("hide");
                            $("#spanPrompt").text("您未在 "+flag[1]+" 天内提交审核,注册信息已失效");
                            layer.close(index);
                        } else if (flag[0] == "supplier_logout") {
                            $("#divPrompt").removeClass("hide");
                            $("#spanPrompt").text("您未在 "+flag[1]+" 天内提交审核,注册信息已失效");
                            layer.close(index);
    					} else if(data == "expert_waitOnceCheck"){
                            $("#divPrompt").removeClass("hide");
                            $("#spanPrompt").text("对不起，您处于待复审状态");
                            layer.close(index);
                        } else if(data == "onceCheckNoPass"){
                            $("#divPrompt").removeClass("hide");
                            $("#spanPrompt").text("对不起，您的复审未通过");
                            layer.close(index);
                        } else if(data == "prepass"){
                            $("#divPrompt").removeClass("hide");
                            $("#spanPrompt").text("对不起，您处于预审核通过期间");
                            layer.close(index);
                        }else if(data == "publicity"){
                            $("#divPrompt").removeClass("hide");
                            $("#spanPrompt").text("对不起，您处于公示期间");
                            layer.close(index);
                        } else if (flag[0] == "firset") {
    						//询问框
    						layer.confirm('您还未完善个人信息，是否前去完善？', {
    							btn : [ '是', '否' ]
    						//按钮
    						}, function() {
    							window.location.href = "${pageContext.request.contextPath}/expert/login.html?userId=" + flag[1]+"&stepNumber=one";
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
    							window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=" + flag[1]+"&stepNumber=one";
    						}, function() {
    							layer.close(index);
    							window.location.href = "${pageContext.request.contextPath}/";
    						});
    					} else if (data.indexOf("weed")>=0) {
    					  	/* $("#divPrompt").removeClass("hide");
    						  $("#spanPrompt").text("请耐心等待复查");
    						layer.close(index); */
    						$.ajax({
				                  url: "${pageContext.request.contextPath}/expert/validateAuditTime.do",
				                  data: {"userId" : data.split(",")[1]},
				                  dataType: "json",
				                  async: false,
				                  success: function(response){
				                	  layer.alert("<span style='margin-left:26px;'> 您的信息已于" + response.submitDate + "提交审核,将于45天内审核完成,请耐心等待！</span>"+"<br/> <span style='margin-left:26px;'> 您选择的采购机构是</span>：" +response.purchaseDep.shortName + "；联系人是:" + response.purchaseDep.experContact + ";"+"联系人电话：" +  response.purchaseDep.experPhone + "；联系人地址是：" + response.purchaseDep.experAddress +";邮编："+response.purchaseDep.unitPostCode+ "。");
				                	  layer.close(index);
				                  }
    						});
    						
    					} else if (flag[0] == "auditExp") {
    						
    				 	 /*layer.confirm('您的个人信息被退回，是否前去完善？', {
    							btn : [ '是', '否' ]
    						//按钮
    						}, function() {
    							window.location.href = "${pageContext.request.contextPath}/expert/login.html?userId=" + flag[1];
    						}, function() {
    							layer.close(index);
    							window.location.href = "${pageContext.request.contextPath}/";
    						});   */
    						
    						   $.ajax({
    							url: "${pageContext.request.contextPath}/expert/validateAuditTime.do",
    							data: {"userId" : flag[1]},
    							dataType: "json",
    							async: false,
    							success: function(response){
   									//询问框
   		    						layer.confirm("<span style='margin-left:26px;'> 您的信息已于" + response.submitDate + "提交审核,将于45天内审核完成,请耐心等待！</span>"+"<br/> <span style='margin-left:26px;'> 您选择的采购机构是</span>：" +response.purchaseDep.shortName + "；联系人是:" + response.purchaseDep.experContact + ";"+"联系人电话：" +  response.purchaseDep.experPhone + "；联系人地址是：" + response.purchaseDep.experAddress +";邮编："+response.purchaseDep.experPostcode+ "。", {
   		    							btn : [ '确定' ]
   		    						//按钮
   		    						}, function() {
   		    							window.location.href = "${pageContext.request.contextPath}/";
   		    						});
    							}
    						});  
    						
    					}else if(flag[0]=="unperfect"){
    						//询问框
    					/* 	if(flag[2]==null){ */
    							layer.confirm("<span style='margin-left:26px;'> 信息未完善，是否前去完善？</span>", {
        						 	btn: ['是','否'] //按钮
        						}, function(){
        						  window.location.href="${pageContext.request.contextPath}/supplier/login.html?name="+flag[1];
        						 	}, function(){
        						 		layer.close(index);
        						 		window.location.href="${pageContext.request.contextPath}/";
        						 	    });
    						/* }else{
    							layer.confirm("<span style='margin-left:26px;'> 信息未完善，是否前去完善？</span>"+"<br/> <span style='margin-left:26px;'>您选择的采购机构是</span>："+flag[2]+"；联系人是:"+flag[3]+";"+"联系人电话："+flag[4]+"；联系人地址是："+flag[5]+"；联系人邮编："+flag[6], {
        						 	btn: ['是','否'] //按钮
        						}, function(){
        						  window.location.href="${pageContext.request.contextPath}/supplier/login.html?name="+flag[1];
        						 	}, function(){
        						 		layer.close(index);
        						 		window.location.href="${pageContext.request.contextPath}/";
        						 	    });
    						} */
    						
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
    						$("#divPrompt").removeClass("hide");
    					    $("#spanPrompt").text("抱歉,您的审核没有通过,无法登录！");
    						layer.close(index);
    					} else if (data == "secondNotPass") {
    						$("#divPrompt").removeClass("hide");
    					    $("#spanPrompt").text("抱歉,您的复核没有通过,无法登录！");
    						layer.close(index);
    					} else if (data == "thirdNotPass") {
    						$("#divPrompt").removeClass("hide");
    					    $("#spanPrompt").text("抱歉,您的实地考察不合格,无法登录！");
    						layer.close(index);
    					} else if(flag[0]=="commit"){
    						$.ajax({
    							url: "${pageContext.request.contextPath}/supplier/validateAuditTime.do",
    							data: {"userId" : flag[1]},
    							dataType: "json",
    							async: false,
    							success: function(response){
    								if(response.isok == "3"){
    									$("#divPrompt").removeClass("hide");
    		   							$("#spanPrompt").text("账号不存在!");
    		   							layer.close(index);
    								}else if (response.isok == "1") {
    									// 没有超过45天
    									//询问框
    		    						layer.confirm("<span style='margin-left:26px;'> 您的信息已于" + response.submitDate + "提交审核,将于45天内审核完成,请耐心等待！</span>"+"<br/> <span style='margin-left:26px;'> 您选择的采购机构是</span>：" +response.purchaseDep.shortName + "；联系人是:" + response.purchaseDep.supplierContact + ";"+"联系人电话：" +  response.purchaseDep.supplierPhone + "；联系人地址是：" + response.purchaseDep.supplierAddress +";邮编："+response.purchaseDep.supplierPostcode+ "。", {
    		    							btn : [ '确定' ]
    		    						//按钮
    		    						}, function() {
    		    							window.location.href = "${pageContext.request.contextPath}/";
    		    						});
    								} else {
    									// 超过45天
    									//询问框
    									console.log(response);
    		    						layer.confirm("您的信息提交审核已经超过45天,请耐心等待或联系相关初审机构(" + response.purchaseDep.supplierContact + ":" + response.purchaseDep.supplierPhone + ")!", {
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
    						$("#divPrompt").removeClass("hide");
    						 $("#spanPrompt").text("管理员账号请在内网登录");
    						layer.close(index);
    					} else if (data == "deleteLogin") {
    						$("#divPrompt").removeClass("hide");
    						 $("#spanPrompt").text("账号不存在!");
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
//     			getCode();
    		});
    	}
    	
    /* 	function getCode() {
    		$.ajax({
    			url : "${pageContext.request.contextPath}/my_test/auto_write.do",
    			type : "post",
    			dataType : "json",
    			success : function(result) {
    				$("#inputCode").val(result);
    			}
    		});
    	} */
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
  			  <a href="${pageContext.request.contextPath}/">
  				<img src="${pageContext.request.contextPath}/public/portal/images/logo.png" width="50%" height="90%" />
  			  </a>
  			</div>
        <div class="clear"></div>

  			<div class="col-md-4 col-sm-5 col-xs-12"></div>

  			<div class="container content login_content">
  				<div class="row">
  					<div class="sign_left col-md-6 col-sm-12 col-xs-12">
  						<img src="${pageContext.request.contextPath}/public/portal/images/newsign_left.jpg" class='sign_left_image'/>
  					</div>

  					<div class="col-md-5 col-sm-12 col-xs-12 login_right">
  						<div class="col-md-10 col-sm-12 col-xs-12 clear">
  							<div class="shadow-effect-2 opacity-80 sign_box">

  								<header class="mt10 overflow_h">
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
  									<div class="msg_box">
  									   <div class="msg-wrap hide" id="divPrompt">
			                              <div class="msg-error "><b></b><span id="spanPrompt">请输入密码</span></div>
                                       </div>
                                     </div>
  										<form accept-charset="UTF-8" class="sky-form" method="post">
  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">用户名</label>
  													<div class="col col-md-9 col-sm-12 col-xs-12">
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
  													<div class="col col-md-9 col-sm-12 col-xs-12">
  														<label class="input"> <input id="inputPassword"
  															name="" placeholder="请输入密码" type="password" autocomplete="off">
  														</label>
  														<div class="note"></div>
  													</div>
  												</div>
  											</section>

  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">验证码</label>
  													<div class="col col-md-9 col-sm-12 col-xs-12">
  														<label class="input">
  															<div class="col-md-6 col-sm-6 col-xs-6 pl0">
  																<input id="inputCode" type="text" placeholder="" class="fl col-md-12">
  															</div>
  															<div class="col-md-6 col-sm-6 col-xs-6 p0">
  																<img id="identity_code_img_id_0" style="border: 1px solid #2c9fa6; cursor: pointer;" height="34px" class="hand w100p"
  																	src="${pageContext.request.contextPath}/supplier/get_identity.html"  title="看不清？点击刷新" 
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
  									<div class="msg_box">
  									  <div class="msg-wrap">
			                               <div class="msg-error  hide"><b></b>请输入密码</div>
                                      </div>
                                    </div>
  										<form accept-charset="UTF-8" class="sky-form"
  											method="post">
  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">用户名</label>
  													<div class="col col-md-9 col-sm-12 col-xs-12">
  														<label class="input"> <input id="" name=""
  															placeholder="请输入用户名" type="text">
  														</label>
  													</div>
  												</div>
  											</section>

  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">密 码</label>
  													<div class="col col-md-9 col-sm-12 col-xs-12">
  														<label class="input"> <input id="" name=""
  															placeholder="请输入密码" type="password" autocomplete="off">
  														</label>
  														<div class="note"></div>
  													</div>
  												</div>
  											</section>

  											<section>
  												<div class="row">
  													<label class="label col col-md-3 col-sm-12 col-xs-12">验证码</label>
  													<div class="col col-md-9 col-sm-12 col-xs-12">
  														<label class="input">
  															<div class="col-md-6 col-sm-6 col-xs-6 pl0">
  																<input type="password" placeholder="" autocomplete="off" class="fl col-md-12">
  															</div>
  															<div class="col-md-6 col-sm-6 col-xs-6 p0">
  																<img id="identity_code_img_id_1"
  																	style="border: 1px solid #2c9fa6; cursor: pointer;"
  																	height="34px" class="hand w100p"
  																	src="${pageContext.request.contextPath}/supplier/get_identity.html"
  																	title="看不清？点击刷新"
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
  			 <address>
	        	Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号<span class="ratio"> 浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</span>
             </address>
  		</div>

  	</div>
  </body>
</html>
