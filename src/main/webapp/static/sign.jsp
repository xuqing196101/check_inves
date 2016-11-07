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
   <!--  <link href="css/header-v4.css" media="screen" rel="stylesheet">
    <link href="css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="css/page_job.css" media="screen" rel="stylesheet">
    <link href="css/shop.style.css" media="screen" rel="stylesheet">
	<link href="css/page_log_reg_v1.css" media="screen" rel="stylesheet"> -->
	<script type="text/javascript">
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
				if(data=="errorcode"){
					layer.tips("验证码不正确","#inputCode",{
						tips : 1
					});	
					layer.close(index);
				}else if(data=="errorlogin"){				
					layer.msg("用户名或密码错误！");
					layer.close(index);
				}else if(data=="nullcontext"){				
					layer.msg("请输入用户名密码或者验证码!");
				}else if(data=="scuesslogin"){				
					layer.close(index);
					window.location.href="${pageContext.request.contextPath}/login/index.html";
				}else if(data="deleteLogin"){
					layer.msg("账号不存在!");
					layer.close(index);
				}
				kaptcha();
			}
		});
	}

}
function kaptcha(){
	$("#kaptchaImage").hide().attr('src','Kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
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
	   <div class="col-md-5 mt60 login_right  col-md-offset-1 ">
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
				     <div class="col-md-8 p0"><input id="inputCode" type="password" placeholder="" class="fl col-md-8"></div>
                     <div class="col-md-4"><img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" /></div>
                  </label>
                  <div class="note"></div>
                </div>
              </div>
			</section>
          <div class="content border-top-1 tc">
            <button type="submit" onclick="login();" class="btn login_btn" id="">登 录</button>
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
				     <div class="col-md-8 p0"><input type="password" placeholder="" class="fl col-md-8"></div>
                     <div class="col-md-4"><img src="${pageContext.request.contextPath}/public/ZHQ/images/yzm.jpg" width="100%" height="34px"/></div>
                  </label>
                  <div class="note"></div>
                </div>
              </div>
			</section>
   
          <div class="content border-top-1 tc">
            <button type="submit" class="btn login_btn" id="">登 录</button>
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
