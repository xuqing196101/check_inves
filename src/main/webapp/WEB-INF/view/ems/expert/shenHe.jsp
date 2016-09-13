<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%=basePath%>public/ZHQ/css/validForm/style.css" type="text/css" media="all" />
<link href="<%=basePath%>public/ZHQ/css/validForm/demo.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">


<script type="text/javascript" src="<%=basePath%>public/ZHH/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/hm.js"></script><script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>



<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.va.2.min.jsidate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/application.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/james.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
<script type="text/javascript">
	
	 function validataForm(inputText,fontId){
		var text = $(inputText).val();
		if(text.replace(/\s/g,"")==null || text.replace(/\s/g,"")==""){
			$("#"+fontId+"").html("不能为空").css('color','red');
		}else{
			$("#"+fontId+"").html("");
		}
	} 
	 function submitForm(){
		 	var x1=document.forms["form1"]["relName"].value;
			var x2=document.forms["form1"]["birthday"].value;
			var x3=document.forms["form1"]["idNumber"].value;
			var x4=document.forms["form1"]["nayion"].value;
			var x5=document.forms["form1"]["address"].value;
			var x6=document.forms["form1"]["graduateSchool"].value;
			var x7=document.forms["form1"]["professTechTitles"].value;
			var x8=document.forms["form1"]["timeToWork"].value;
			var x9=document.forms["form1"]["major"].value;
			var x10=document.forms["form1"]["timeStartWork"].value;
			var x11=document.forms["form1"]["workUnit"].value;
			var x12=document.forms["form1"]["unitAddress"].value;
			var x13=document.forms["form1"]["fixPhone"].value;
			var x14=document.forms["form1"]["fax"].value;
			var x15=document.forms["form1"]["zipCode"].value;
			var x16=document.forms["form1"]["makeTechDate"].value;
			var x17=document.forms["form1"]["degree"].value;
			var x18=document.forms["form1"]["healthState"].value;
			var x19=document.forms["form1"]["atDuty"].value;
			if (x1==null || x1.replace(/(^\s*)|(\s*$)/g, "")=="" || x2==null || x2.replace(/(^\s*)|(\s*$)/g, "")=="" || x3==null || x3.replace(/(^\s*)|(\s*$)/g, "")==""|| x4==null || x4.replace(/(^\s*)|(\s*$)/g, "")==""|| x5==null || x5.replace(/(^\s*)|(\s*$)/g, "")==""|| x6==null || x6.replace(/(^\s*)|(\s*$)/g, "")==""|| x7==null || x7.replace(/(^\s*)|(\s*$)/g, "")==""|| x8==null || x8.replace(/(^\s*)|(\s*$)/g, "")==""||  x9==null || x9.replace(/(^\s*)|(\s*$)/g, "")==""|| x10==null || x10.replace(/(^\s*)|(\s*$)/g, "")==""|| x11==null || x11.replace(/(^\s*)|(\s*$)/g, "")==""|| x12==null || x12.replace(/(^\s*)|(\s*$)/g, "")==""|| x13==null || x13.replace(/(^\s*)|(\s*$)/g, "")==""|| x14==null || x14.replace(/(^\s*)|(\s*$)/g, "")==""|| x15==null || x15.replace(/(^\s*)|(\s*$)/g, "")==""|| x16==null || x16.replace(/(^\s*)|(\s*$)/g, "")=="" || x17==null || x17.replace(/(^\s*)|(\s*$)/g, "")=="" || x18==null || x18.replace(/(^\s*)|(\s*$)/g, "")=="" || x19==null || x19.replace(/(^\s*)|(\s*$)/g, "")=="")
			  {
				return false;
			  }else{
				  return true;
			  }
	 }
	 //是否通过标示
	 function pass(flag){
		 $("#isPass").val(flag);
		/*  if(flag==1 || flag=="1"){
			 $("#form1").submit();
		 }else { */
			var remark = $("#remark").val(); 
			 if(remark.replace(/(^\s*)|(\s*$)/g, "")=="" || remark==null){
				 layer.alert("请填写意见！",{offset: ['222px', '390px'],shade:0.01});
			 }else{
				 $("#form1").submit();
			 }
		 //}
	 }
</script>
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
            <div class="col-md-8 topbar-v1 col-md-12 padding-0 " id="s1">
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
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">评标专家注册</a></li><li class="active"><a href="#">评标专家信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">基本信息</a></li>
							<li class="">	   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">专家类型</a></li>
							<!-- <li class="">	   <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18"></a></li> -->
						</ul>
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/shenhe.do"  method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
   <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${expert.id }">
   <input type="hidden" name="isPass" id="isPass"/>
  <div class="tab-content padding-top-20" >
	<div class="tab-pane fade active in height-450" id="tab-1">
	<div class=" margin-bottom-0">
	<i>01</i>评标专家基本信息
   </div>
   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">姓名：</span>
        <input class="span2"  id="relName" maxlength="10" value="${expert.relName }" disabled="disabled"  name="relName" type="text" onblur="validataForm(this,'nameFont');">
          
       <font id="nameFont"></font>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">性别：</span>
	   <select class="span2" name="gender" disabled="disabled">
	   	<option <c:if test="${expert.gender eq '男' }">selected="selected"</c:if> value="男">男</option>
	   	<option <c:if test="${expert.gender eq '女' }">selected="selected"</c:if> value="女">女</option>
	   	<option value="其他">其他</option>
	   </select>
	   
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">出生日期：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont2');" onblur="validataForm(this,'nameFont2');" readonly="readonly" name="birthday" id="appendedInput" type="text" >
      <font id="nameFont2"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">政治面貌：</span>
	   <select class="span2" name="politicsStatus" disabled="disabled">
	   	<option <c:if test="${expert.politicsStatus eq '党员' }">selected="selected"</c:if> value="党员">党员</option>
	   	<option <c:if test="${expert.politicsStatus eq '团员' }">selected="selected"</c:if> value="团员">团员</option>
	   	<option <c:if test="${expert.politicsStatus eq '群众' }">selected="selected"</c:if> value="群众">群众</option>
	   	<option <c:if test="${expert.politicsStatus eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件类型：</span>
	   <select class="span2" name="idType" disabled="disabled">
	   	<option <c:if test="${expert.idType eq '身份证' }">selected="selected"</c:if> value="身份证">身份证</option>
	   	<option <c:if test="${expert.idType eq '士兵证' }">selected="selected"</c:if> value="士兵证">士兵证</option>
	   	<option <c:if test="${expert.idType eq '驾驶证' }">selected="selected"</c:if> value="驾驶证">驾驶证</option>
	   	<option <c:if test="${expert.idType eq '工作证' }">selected="selected"</c:if> value="工作证">工作证</option>
	   	<option <c:if test="${expert.idType eq '护照' }">selected="selected"</c:if> value="护照">护照</option>
	   	<option <c:if test="${expert.idType eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件号码：</span>
        <input class="span2" disabled="disabled" maxlength="30" onblur="validataForm(this,'nameFont3');" value="${expert.idNumber }" name="idNumber" id="appendedInput" type="text">
          
    <font id="nameFont3"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专家来源：</span>
	   <select class="span2" name="expertsFrom" disabled="disabled">
	   	<option <c:if test="${expert.expertsFrom eq '军队' }">selected="selected"</c:if> value="军队">军队</option>
	   	<option <c:if test="${expert.expertsFrom eq '地方' }">selected="selected"</c:if> value="地方">地方</option>
	   	<option <c:if test="${expert.expertsFrom eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">民族：</span>
        <input class="span2" disabled="disabled" maxlength="10" onblur="validataForm(this,'nameFont4');"  value="${expert.nation }" name="nation" id="appendedInput" type="text">
          
       <font id="nameFont4"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">所在地区：</span>
        <input class="span2" disabled="disabled" maxlength="40" onblur="validataForm(this,'nameFont5');"  value="${expert.address }"  name="address" id="appendedInput" type="text">
          
       <font id="nameFont5"></font>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">毕业院校：</span>
        <input class="span2" disabled="disabled" maxlength="40" onblur="validataForm(this,'nameFont6');" value="${expert.graduateSchool }" name="graduateSchool" id="appendedInput" type="text">
          
       <font id="nameFont6"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业技术职称：</span>
        <input class="span2" disabled="disabled" maxlength="20" onblur="validataForm(this,'nameFont7');" value="${expert.professTechTitles }" name="professTechTitles" id="appendedInput" type="text">
          
       <font id="nameFont7"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">参加工作时间：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont8');" onblur="validataForm(this,'nameFont8');" readonly="readonly" name="timeToWork" id="appendedInput" type="text" >
       <font id="nameFont8"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">最高学历：</span>
	   <select class="span2" name="hightEducation" disabled="disabled">
	   	<option <c:if test="${expert.hightEducation eq '博士' }">selected="selected"</c:if> value="博士">博士</option>
	   	<option <c:if test="${expert.hightEducation eq '硕士' }">selected="selected"</c:if> value="硕士">硕士</option>
	   	<option <c:if test="${expert.hightEducation eq '研究生' }">selected="selected"</c:if> value="研究生">研究生</option>
	   	<option <c:if test="${expert.hightEducation eq '本科' }">selected="selected"</c:if> value="本科">本科</option>
	   	<option <c:if test="${expert.hightEducation eq '专科' }">selected="selected"</c:if> value="专科">专科</option>
	   	<option <c:if test="${expert.hightEducation eq '高中' }">selected="selected"</c:if> value="高中">高中</option>
	   	<option <c:if test="${expert.hightEducation eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业：</span>
        <input class="span2" value="${expert.major }" disabled="disabled" maxlength="20" onblur="validataForm(this,'nameFont9');" name="major" id="appendedInput" type="text">
          
       <font id="nameFont9"></font>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class="">从事专业起始年度：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont10');" onblur="validataForm(this,'nameFont10');" readonly="readonly" name="timeStartWork" id="appendedInput" type="text" >
      <font id="nameFont10"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">工作单位：</span>
        <input class="span2" disabled="disabled" maxlength="40" onblur="validataForm(this,'nameFont11');" value="${expert.workUnit }" name="workUnit" id="appendedInput" type="text">
          
       <font id="nameFont11"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">单位地址：</span>
        <input class="span2" maxlength="40" disabled="disabled" onblur="validataForm(this,'nameFont12');" value="${expert.unitAddress }" name="unitAddress" id="appendedInput" type="text">
          
       <font id="nameFont12"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">联系电话（固话）：</span>
        <input class="span2" maxlength="15" disabled="disabled" onblur="validataForm(this,'nameFont13');" value="${expert.fixPhone }" name="fixPhone" id="appendedInput" type="text">
          
       <font id="nameFont13"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">传真：</span>
        <input class="span2" maxlength="10" disabled="disabled" onblur="validataForm(this,'nameFont14');" value="${expert.fax }" name="fax" id="appendedInput" type="text">
          
       <font id="nameFont14"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">邮政编码：</span>
        <input class="span2" maxlength="6" disabled="disabled" onblur="validataForm(this,'nameFont15');"  value="${expert.postCode }" name="postCode" id="appendedInput" type="text">
          
       <font id="nameFont15"></font>
	 </li> 
	<li class="col-md-6  p0 ">
	   <span class="">取得技术职称时间：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont16');" onblur="validataForm(this,'nameFont16');" readonly="readonly" name="makeTechDate" id="appendedInput" type="text" onclick='WdatePicker()'>
       <font id="nameFont16"></font>
	 </li>  
	  <li class="col-md-6  p0 ">
	   <span class="">学位：</span>
        <input class="span2" disabled="disabled" value="${expert.degree }"  maxlength="10" onblur="validataForm(this,'nameFont17');" name="degree" id="appendedInput" type="text">
          
       <font id="nameFont17"></font>
	 </li>
	  <li class="col-md-6  p0 ">
	   <span class="">健康状态：</span>
        <input class="span2" maxlength="10" value="${expert.healthState }" disabled="disabled" onblur="validataForm(this,'nameFont18');" name="healthState" id="appendedInput" type="text">
          
       <font id="nameFont18"></font>
	 </li>  
	 <li class="col-md-6  p0 ">
	   <span class="">现任职务：</span>
        <input class="span2" maxlength="10" value="${expert.atDuty }" disabled="disabled" onblur="validataForm(this,'nameFont19');" name="atDuty" id="appendedInput" type="text">
        
       <font id="nameFont19"></font>
	 </li>
   </ul>
  
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>附件信息</h2>
   </div>
  </div>
  </div> 
   <div class="tab-pane fade height-450" id="tab-2">
		<div class="margin-bottom-0  categories">
		 <ul class="list-unstyled list-flow" style="margin-left: 250px;">
     		<li class="p0">
			   <span class="">专家类型：</span>
			   <input type="hidden" id="expertsTypeIds" value="${expert.expertsTypeId }">
			   <select name="expertsTypeId" id="expertsTypeId" disabled="disabled">
			   		<option value="0">-请选择-</option>
			   		<option <c:if test="${expert.expertsTypeId eq '1' }">selected="selected"</c:if> value="1">技术</option>
			   		<option <c:if test="${expert.expertsTypeId eq '2' }">selected="selected"</c:if> value="2">法律</option>
			   		<option <c:if test="${expert.expertsTypeId eq '3' }">selected="selected"</c:if> value="3">商务</option>
			   </select>
			 </li>
        </ul>
		</div>
	</div>
	<!-- <div class="tab-pane fade height-450" id="tab-3">
			<div class="margin-bottom-0  categories">
			</div>
	</div> -->
  </div>
 <!--  <div class="padding-left-40 padding-right-20 clear">
   <ul class="list-unstyled list-flow p0_20">
   <li class="col-md-6  p0 ">
	   <span class="">身份证：</span>
	   <div >
        <input class="span2" name="file" id="appendedInput" type="file" >
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">学历证书：</span>
	   <div >
        <input class="span2" name="file" id="appendedInput" type="file">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">职称证书：</span>
	   <div >
        <input class="span2" name="file" id="appendedInput" type="file">
       </div>
	 </li>
	  <li class="col-md-6  p0 ">
	   <span class="">学位证书：</span>
	   <div >
        <input class="span2" name="file" id="appendedInput" type="file">
       </div>
	  </li>
	  <li class="col-md-6  p0 ">
	   <span class="">本人照片：</span>
	   <div >
        <input class="span2" name="file" id="appendedInput" type="file">
       </div>
	 </li>
   </ul>
  </div> -->
  <div>
	  <ul  class="list-unstyled list-flow p0_20">
	  	<li class="col-md-12 p0">
	   <span class="fl">审核意见：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " maxlength="200" id="remark" name="remark"  placeholder="不超过200个字"></textarea>
       </div>
	 </li> 
	  </ul>
  </div>
  <div  class="col-md-12">
   <div class="fl padding-10">
   <input class="btn btn-windows add" type="button" onclick="pass('1');" value="通过">
    <!-- <button class="btn btn-windows add" type="submit">下一步</button> -->
	<!-- <button class="btn btn-windows delete" type="submit">删除</button> -->
	<input class="btn btn-windows delete" type="button" onclick="pass('2');" value="不通过">
	<input class="btn btn-windows back" type="button" onclick="pass('3');" value="退回修改">
	<a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
  </form>
 </div>
 	</div>
				</div>
			</div>
	</div>
	</div>
 <!--底部代码开始-->
<div >
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