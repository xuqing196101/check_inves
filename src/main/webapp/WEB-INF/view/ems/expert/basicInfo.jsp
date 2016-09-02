<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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



<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
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
<script type="text/javascript">
	function validataForm(inputText,fontId){
		var text = $(inputText).val();
		if(text.replace(/\s/g,"")==null || text.replace(/\s/g,"")==""){
			$("#"+fontId+"").html("不能为空").css('color','red');
		}else{
			$("#"+fontId+"").html("");
		}
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
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">订单中心</a></li><li class="active"><a href="#">修改订单</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/edit.do" method="post" enctype="multipart/form-data">
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${uuid }">
   <div>
   <div class="headline-v2">
   <h2>专家信息</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">姓名：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" onblur="validataForm(this,'nameFont');" name="relName" type="text">
         
       </div><font id="nameFont"></font>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">性别：</span>
	   <div >
	   <select class="span2" name="sex" >
	   	<option value="男">男</option>
	   	<option value="女">女</option>
	   	<option value="其他">其他</option>
	   </select>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">出生日期：</span>
       <div class="input-append">
        <input class="span2 Wdate w250" onfocus="validataForm(this,'nameFont2');" onblur="validataForm(this,'nameFont2');" readonly="readonly" name="birthday" id="appendedInput" type="text" onclick='WdatePicker()'>
       </div><font id="nameFont2"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">政治面貌：</span>
       <div >
	   <select class="span2" name="politicsStatus" >
	   	<option value="党员">党员</option>
	   	<option value="团员">团员</option>
	   	<option value="群众">群众</option>
	   	<option value="其他">其他</option>
	   </select>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件类型：</span>
        <div >
	   <select class="span2" name="idType" >
	   	<option value="身份证">身份证</option>
	   	<option value="士兵证">士兵证</option>
	   	<option value="驾驶证">驾驶证</option>
	   	<option value="工作证">工作证</option>
	   	<option value="护照">护照</option>
	   	<option value="其他">其他</option>
	   </select>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件号码：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont3');" name="idNumber" id="appendedInput" type="text">
         
       </div><font id="nameFont3"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专家来源：</span>
        <div >
	   <select class="span2" name="expertsFrom" >
	   	<option value="军队">军队</option>
	   	<option value="地方">地方</option>
	   	<option value="其他">其他</option>
	   </select>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">民族：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont4');" name="nayion" id="appendedInput" type="text">
         
       </div><font id="nameFont4"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">所在地区：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont5');" name="address" id="appendedInput" type="text">
         
       </div><font id="nameFont5"></font>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">毕业院校：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont6');" name="graduateSchool" id="appendedInput" type="text">
         
       </div><font id="nameFont6"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业技术职称：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont7');" name="professTechTitles" id="appendedInput" type="text">
         
       </div><font id="nameFont7"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">参加工作时间：</span>
	   <div class="input-append">
        <input class="span2 Wdate w250" onfocus="validataForm(this,'nameFont8');" onblur="validataForm(this,'nameFont8');" readonly="readonly" name="timeToWork" id="appendedInput" type="text" onclick='WdatePicker()'>
       </div><font id="nameFont8"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">最高学历：</span>
        <div >
	   <select class="span2" name="hightEducation" >
	   	<option value="博士">博士</option>
	   	<option value="硕士">硕士</option>
	   	<option value="研究生">研究生</option>
	   	<option value="本科">本科</option>
	   	<option value="专科">专科</option>
	   	<option value="高中">高中</option>
	   	<option value="其他">其他</option>
	   </select>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont9');" name="major" id="appendedInput" type="text">
         
       </div><font id="nameFont9"></font>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class="">从事专业起始年度：</span>
	   <div class="input-append">
        <input class="span2 Wdate w250" onfocus="validataForm(this,'nameFont10');" onblur="validataForm(this,'nameFont10');" readonly="readonly" name="timeStartWork" id="appendedInput" type="text" onclick='WdatePicker()'>
       </div><font id="nameFont10"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">工作单位：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont11');" name="workUnit" id="appendedInput" type="text">
         
       </div><font id="nameFont11"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">单位地址：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont12');" name="unitAddress" id="appendedInput" type="text">
         
       </div><font id="nameFont12"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">联系电话（固话）：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont13');" name="fixPhone" id="appendedInput" type="text">
         
       </div><font id="nameFont13"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">传真：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont14');" name="fax" id="appendedInput" type="text">
         
       </div><font id="nameFont14"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">邮政编码：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont15');" name="zipCode" id="appendedInput" type="text">
         
       </div><font id="nameFont15"></font>
	 </li> 
	<li class="col-md-6  p0 ">
	   <span class="">取得技术职称时间：</span>
	   <div class="input-append">
        <input class="span2 Wdate w250" onfocus="validataForm(this,'nameFont16');" onblur="validataForm(this,'nameFont16');" readonly="readonly" name="makeTechDate" id="appendedInput" type="text" onclick='WdatePicker()'>
       </div><font id="nameFont16"></font>
	 </li>  
	  <li class="col-md-6  p0 ">
	   <span class="">学位：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont17');" name="degree" id="appendedInput" type="text">
         
       </div><font id="nameFont17"></font>
	 </li>
	  <li class="col-md-6  p0 ">
	   <span class="">健康状态：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont18');" name="healthState" id="appendedInput" type="text">
         
       </div><font id="nameFont18"></font>
	 </li>  
	 <li class="col-md-6  p0 ">
	   <span class="">现任职务：</span>
	   <div class="input-append">
        <input class="span2" onblur="validataForm(this,'nameFont19');" name="atDuty" id="appendedInput" type="text">
       </div><font id="nameFont19"></font>
	 </li>
   </ul>
  </div> 
   
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>上传附件</h2>
   </div>
  </div>
  <div class="padding-left-40 padding-right-20 clear">
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
  </div>
  
  <div  class="col-md-12">
   <div class="fl padding-10">
   <input class="btn btn-windows save" type="submit" value="保存">
    <!-- <button class="btn btn-windows add" type="submit">下一步</button> -->
	<!-- <button class="btn btn-windows delete" type="submit">删除</button> -->
	<button class="btn btn-windows save" type="button">暂存</button>
	<button class="btn btn-windows reset" type="reset">重置</button>
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
