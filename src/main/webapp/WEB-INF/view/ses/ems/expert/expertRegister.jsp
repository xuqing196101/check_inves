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
 <script src="${pageContext.request.contextPath}/public/ZHQ/js/expert/validate_regester.js"></script>
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
				   $("#spp").html("通过!").css('color','lime');
				   flag=2;
				   return true;
			    }
		   }
	   }); 
   }
   </script>

</head>
<body>
 
 <div class="wrapper">
		<div class="header-v4">
			<!-- Navbar -->
			<div class="navbar navbar-default mega-menu" role="navigation">
				<div class="container">
					<!-- logo和搜索 -->
					<div class="navbar-header">
						<div class="row container margin-bottom-10">
							<div class="col-md-8">
								<a href=""> <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHQ/images/logo.png" id="logo-header"> </a>
							</div>
							<!--搜索开始-->
							<div class="col-md-4 mt50">
								<div class="search-block-v2">
									<div class="">
										<form accept-charset="UTF-8" action="" method="get">
											<div style="display:none">
												<input name="utf8" value="" type="hidden">
											</div>
											<input id="t" name="t" value="search_products" type="hidden">
											<div class="col-md-12 pull-right">
												<div class="input-group bround4">
													<input class="form-control h38" id="k" name="k" placeholder="" type="text"> <span class="input-group-btn"> <input class="btn-u h38" name="commit" value="搜索" type="submit"> </span>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
							<!--搜索结束-->
						</div>
					</div>

					<button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
						<span class="full-width-menu">全部商品分类</span> <span class="icon-toggle"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </span>
					</button>
				</div>

				<div class="clearfix"></div>

				<div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
					<div class="container">
						<ul class="nav navbar-nav">
							<!-- 通知 -->
							<li class="active dropdown tongzhi_li"><a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a></li>
							<!-- End 通知 -->

							<!-- 公告 -->
							<li class="dropdown gonggao_li"><a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a></li>
							<!-- End 公告 -->

							<!-- 公示 -->
							<li class="dropdown gongshi_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a></li>
							<!-- End 公示 -->

							<!-- 专家 -->
							<li class="dropdown zhuanjia_li"><a href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a></li>
							<!-- End 专家 -->

							<!-- 投诉 -->
							<li class="dropdown tousu_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30" href=""><i class="tousu nav_icon"></i>投诉</a></li>
							<!-- End 投诉 -->

							<!-- 法规 -->
							<li class="dropdown  fagui_li"><a href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="fagui nav_icon"></i>法规</a></li>
							<!-- End 法规 -->

							<li class="dropdown luntan_li"><a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a></li>

						</ul>
					</div>
				</div>
				</div>
				<!--/end container-->
			</div>
		</div>
  
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/register.html" method="post"  id="form1">
   		<%
			session.setAttribute("tokenSession", tokenValue);
		 %>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
		 <input type="hidden" id="message" value="${message }"/>
   <div>
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
		        <input class="span2" name="mobile" placeholder="请输入正确的手机号码" maxlength="14" id="phone" onblur="validataPhone();"  value="" type="text">
		        <span class="add-on">i</span>
		       </div>
		        <input class="btn" type="button" value="发送验证码"><font  id="phone2"></font>
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
   <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
   <input class="btn btn-windows add" type="button" onclick="submitForm();" value="注册">
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
