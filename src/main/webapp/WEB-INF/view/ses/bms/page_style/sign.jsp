<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	<link href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" media="screen" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
   <!--  <link href="css/header-v4.css" media="screen" rel="stylesheet">
    <link href="css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="css/page_job.css" media="screen" rel="stylesheet">
    <link href="css/shop.style.css" media="screen" rel="stylesheet">
	<link href="css/page_log_reg_v1.css" media="screen" rel="stylesheet"> -->
	<script type="text/javascript">
	$(function(){
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			    login();
			  }
		});
	});
	
	function login(){
	if($("#inputEmail").val()==""){
		layer.tips("请输入用户名","#inputEmail",{
			tips : 1
		});		
	}else if($("#inputPassword").val()==""){
		layer.tips("请输入密码","#inputPassword",{
			tips : 1
		});		
	}else if($("#inputCode").val()==""){
		layer.tips("请输入验证码","#inputCode",{
			tips : 1
		});		
	}else{
		var index=layer.load();
		$.ajax({
			url:"${pageContext.request.contextPath}/login/login.html",
			type:"post",
			data:{loginName:$("#inputEmail").val(),password:$("#inputPassword").val(),rqcode:$("#inputCode").val()},
			success:function(data){
				var flag = data.split(",");
				if(data=="errorcode"){
					layer.tips("验证码不正确","#inputCode",{
						tips : 1
					});	
					getIdentityCode(1);
					layer.close(index);
				}else if(data=="errorlogin"){				
					layer.msg("用户名或密码错误！");
					layer.close(index);
				}else if(data=="nullcontext"){				
					layer.msg("请输入用户名密码或者验证码!");
				}else if(data=="scuesslogin"){				
					layer.close(index);
					window.location.href="${pageContext.request.contextPath}/login/index.html";
				}else if(data=="black"){
					layer.msg("对不起，你已被限制登录!");
					layer.close(index);
				}else if(flag[0]=="audit"){
					 //layer.msg("你的信息还未审核，请耐心等待!");
					 window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId="+flag[1];
					//layer.close(index);
				}else if(flag[0]=="empty"){
					//询问框
					layer.confirm('你还未注册个人信息，是否前去完善？', {
					 	btn: ['是','否'] //按钮
					}, function(){
					  window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId="+flag[1];
					 	}, function(){
					 		layer.close(index);
					 		window.location.href="${pageContext.request.contextPath}/";
					 	    });
					}else if(flag[0]=="reset"){
					 		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId="+flag[1];
				}else if(data="deleteLogin"){
					layer.msg("账号不存在!");
					layer.close(index);
				}
				getIdentityCode();
			}
		});
	}

}
function kaptcha(){
	$("#kaptchaImage").hide().attr('src','Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
}

function getIdentityCode(){
	var random = Math.random();
	$("#identity_code_img_id").hide().attr("src","${pageContext.request.contextPath}/supplier/get_identity.html?random"+random).fadeIn();
}
</script>
</head>
<body class="sign_bg">
    <div class="container content">
     <div class="row">
	   <div class="col-md-8"><img src="${pageContext.request.contextPath}/public/ZHQ/images/logo.png" width="50%" height="90%"/></div><div class="col-md-4"></div>
    <div class="container content">
     <div class="row">
	   <div class="sign_left col-md-5 mt60 ">
	     <img src="${pageContext.request.contextPath}/public/ZHQ/images/sign_left.jpg" width="100%" height="100%"/>
	   </div>
	   <div class="col-md-5 mt60 login_right ">
        <div class="col-md-10 col-sm-8 clear">
         <div class="box-shadow shadow-effect-2 opacity-80 sign_box">
          <header class="margin-top-10 ofh">
		   <ul class="list-unstyled sign_kinds col-md-12 p0">
		     <li class="active fl col-md-5">
			   <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="col-md-12">
			   <!--<span class="icon-user common_user"></span> --> 用户登录</a>
			 </li>
		     <li class="fl col-md-5">
			   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="col-md-12">
			   <!--<span class="icon-user ca-user" ></span>--> CA登录</a>
			 </li>
		 </ul>
		  </header>
         <div class="tab-content">
		   <div class="tab-pane active in" id="tab-1">
            
			 <form accept-charset="UTF-8"  class="sky-form reg-page" method="post" >
             <section  class="mb5">
              <div class="row">
                <label class="label col col-md-3">用户名</label>
                <div class="col col-md-8">
                  <label class="input">
                    <input id="inputEmail" name="" placeholder="用户名" type="text">
                  </label>
                </div>
              </div>
            </section>
            <section class="mb5">
              <div class="row">
                <label class="label col col-md-3">密 码</label>
                <div class="col col-md-8">
                  <label class="input">
                    <input id="inputPassword" name="" placeholder="密 码" type="password">
                  </label>
                  <div class="note"></div>
                </div>
              </div>
            </section>
			<section  class="mb20">
			 <div class="row">
                <label class="label col col-md-3">验证码</label>
                <div class="col col-md-8">
                  <label class="input">
				     <div class="col-md-6 pl0"><input id="inputCode" type="text" placeholder="" class="fl col-md-12">
				     </div>
                     <div class="col-md-6 p0"><img id="identity_code_img_id" height="34px" class="hand w100p" src="${pageContext.request.contextPath}/supplier/get_identity.html" onclick="getIdentityCode()"></div>
                   </label>
                  <div class="note"></div>
                </div>
              </div>
			</section>
          <div class="content border-top-1 tc">
            <button type="button" onclick="login();" class="btn login_btn" >登 录</button>
          </div>
        </form> 
	   </div>   
		   <div class="tab-pane" id="tab-2">
			 <form accept-charset="UTF-8"  class="sky-form reg-page" method="post" >
             <section  class="mb5">
              <div class="row">
                <label class="label col col-md-3">用户名</label>
                <div class="col col-md-8">
                  <label class="input">
                    <input id="" name="" placeholder="用户名" type="text">
                  </label>
                </div>
              </div>
            </section>

            <section class="mb5">
              <div class="row">
                <label class="label col col-md-3">密 码</label>
                <div class="col col-md-8">
                  <label class="input">
                    <input id="" name="" placeholder="密 码" type="password">
                  </label>
                  <div class="note"></div>
                </div>
              </div>
            </section>
			<section  class="mb20">
			 <div class="row">
                <label class="label col col-md-3">验证码</label>
                <div class="col col-md-8">
                  <label class="input">
				     <div class="col-md-6 pl0"><input type="password" placeholder="" class="fl col-md-12"></div>
                     <div class="col-md-6 p0"><img id="identity_code_img_id" height="34px" class="hand w100p" src="${pageContext.request.contextPath}/supplier/get_identity.html" onclick="getIdentityCode()"></div>
                  </label>
                  <div class="note"></div>
                </div>
              </div>
			</section>
   
          <div class="content border-top-1 tc">
            <button type="button" class="btn login_btn" onclick="login();">登 录</button>
          </div>
        </form> 
	   </div>  
      </div>

     </div>
    </div>
	
	   </div>
	 </div>
	 <div class="container clear tc mt60">Copyright  2016 版权所有：中央军委后勤保障部</div>
	</div>
</body>
</html>
