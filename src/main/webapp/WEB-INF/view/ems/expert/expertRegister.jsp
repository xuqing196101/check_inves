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
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style(1).css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet">
    <script src="<%=basePath%>public/ZHH/js/hm.js"></script><script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script src="<%=basePath%>public/ZHH/js/app.js"></script>
<script src="<%=basePath%>public/ZHH/js/common.js"></script>
<script src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/WdatePicker.js"></script><link href="<%=basePath%>public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script src="<%=basePath%>public/ZHH/js/form.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script src="<%=basePath%>public/ZHH/js/application.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/james.js"></script>
</head>
<body>
  <div class="wrapper">
	<div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0 ">
              <ul class="top-v1-data padiing-0">
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li class="dropdown">
			     	<a aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="">
				  		<div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  		<span>支撑环境</span>
				 	</a>
					<ul class="dropdown-menu">
                   		<li class="line-block">
                   			<a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>后台管理</a>
                   			<ul class="dropdown-menuson dropdown-menu">
                   				<li><a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>用户管理</a></li>
                   			</ul>
                   		</li>
               		</ul>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_08.png"/></div>
				  <span>安全退出</span>
				 </a>
				</li>
				
			  </ul>
			</div>
    </div>
	</div>
	</div>
   </div>
</div>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">评审专家</a></li><li class="active"><a href="#">专家注册</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <script type="text/javascript">
   //用户信息验证
   var flag = 1;
   var flag2 = 1;
   var flag3 = 1;
   var flag4 = 1;
   function validataLoginName(){
	   var loginName = $("input[name='loginName']").val();
	   var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
		if(patrn.test(loginName)){  
			$("#spp").html("不能有非法字符").css('color','red');
			flag=1;
			return false;
		}
		if(loginName.indexOf(" ")!=-1){
			$("#spp").html("不能有空格").css('color','red');
			flag=1;
			return false;
		}
	   if(loginName.replace(/\s/g,"")==null || loginName.replace(/\s/g,"")==""){
		   $("#spp").html("用户名不能为空").css('color','red');
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
				   $("#spp").html("用户名可以使用").css('color','lime');
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
			$("#pwd1").html("不能有非法字符").css('color','red');
			flag2=1;
			return false;
		}else
		if(password1.indexOf(" ")!=-1){
			$("#pwd1").html("不能有空格").css('color','red');
			flag2=1;
			return false;
		}else
	   if(password1.replace(/\s/g,"")==null || password1.replace(/\s/g,"")==""){
		   $("#pwd1").html("密码不能为空").css('color','red');
		   flag2=1;
		   return false;
	   }else
	   if(password1.replace(/\s/g,"").length<6){
		   $("#pwd1").html("必须六位以上").css('color','red');
		   flag2=1;
		   return false;
	   }else{
	   $("#pwd1").html("密码可以使用").css('color','lime');
	   flag2=2;		   
	   }
   }
   function validataPwd2(){
	   var password1 = $("#password1").val();
	   var password2 = $("#password2").val();
	   if(password2.replace(/\s/g,"")==null || password2.replace(/\s/g,"")==""){
		   $("#pwd2").html("密码不能为空").css('color','red');
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
		   $("#phone2").html("号码格式错误").css('color','red');
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
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/register.do" method="post"  id="form1">
   		<%
			session.setAttribute("tokenSession", tokenValue);
		 %>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <div>
   <div class="headline-v2">
   <h2>评审专家注册</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
     		<li class="col-md-6 p0">
			   <span class="">用户名：</span>
			   <div class="input-append">
		        <input class="span2" name="loginName" type="text" onblur="validataLoginName();" value="">
		        <span class="add-on">i</span>
		       </div><font  id="spp"></font>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="">手机号码：</span>
			   <div class="input-append">
		        <input class="span2" name="mobile" id="phone" onblur="validataPhone();"  value="" type="text">
		        <span class="add-on">i</span>
		       </div><font  id="phone2"></font>
		        <input class="btn" type="button" value="发送验证码">
			 </li>
		    
		      <li class="col-md-6  p0 ">
			   <span class="">密码：</span>
			   <div class="input-append">
		        <input class="span2" name="password" id="password1" onblur="validataPassword();"  type="password" >
		        <span class="add-on">i</span>
		       </div><font  id="pwd1"></font>
			 </li> 
	 		<li class="col-md-6 p0">
			   <span class="">验证码：</span>
			   <div class="input-append">
		        <input class="span2" name="phone" type="text" value="">
		        <span class="add-on">i</span>
		       </div><font  id="yzm"></font>
			 </li>
			  <li class="col-md-6  p0 ">
			   <span class="">确认密码：</span>
			   <div class="input-append">
		        <input class="span2" id="password2" onblur="validataPwd2();" type="password" value="">
		        <span class="add-on">i</span>
		       </div><font  id="pwd2"></font>
			 </li> 
   </ul>
  </div> 
  <div  class="col-md-12">
   <div class="fl padding-10">
   <input class="btn btn-windows add" type="button" onclick="submitForm();" value="开始注册">
   <!--  <button class="btn btn-windows add" type="submit">开始注册</button> -->
	</div>
  </div>
  </form>
 </div>
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
</div>
</body>
</html>
