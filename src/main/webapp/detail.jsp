<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
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
   <form>
   <div>
   <div class="headline-v2">
   <h2>修改订单</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">采购单位：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">发票抬头：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">采购单位联系人：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">采购单位联系人座机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">采购单位联系人手机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">采购单位地址：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">供应商名称：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位联系人：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位联系人座机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位联系人手机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位地址：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">交付日期：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on"><img src="<%=basePath%>public/ZHH/images/time_icon.png" class="mb10"/></span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">预算金额（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">发票编号：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-12 p0">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea>
       </div>
	 </li> 
	 
   </ul>
  </div> 
   
  <!-- 产品明细开始-->
  <div class="padding-top-10 clear">
   <div class="headline-v2">
   <h2>产品明细</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20 ">

     <li class="col-md-6 p0">
	   <span class="">品目：</span>
	   <div class="input-append">
         <input class="span2" id="appendedInput" type="text">
		 <div class="btn-group ">
          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
          </button>
          <ul class="dropdown-menu list-unstyled">
          </ul>
       </div>
      </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class="">品牌：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">型号：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6 p0 ">
	   <span class=" ">版本号：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6 p0 ">
	   <span class=" ">市场单价（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">成交单价（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">数量：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">单位：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">小计（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-12 p0">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea>
       </div>
	 </li> 
	 </ul>
	 
	 <div class="tab-v1 clear col-md-12 margin-20 padding-right-40">
      <h2 class="nav nav-tabs">
	  </h2>
    </div>
	 <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0 ">
	   <span class=" ">运费（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class="fl">其他费用（元）：</span>
	   <div class="input-append ">
        <input class="span2 " id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class="fl">其他费用说明：</span>
	   <div class="input-append  ">
        <input class=" span2 " id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
   </ul>
   <div class="clear padding-left-20 total"><span class="w200 block">总计：</span><span>¥10000</span></div>
   <div class="headline-v2 clear">
   <h2>上传附件</h2>
   </div>
  </div>
  <div class="padding-left-40 padding-right-20 clear">
   <ul class="list-unstyled  bg8 padding-20">
    <li>1 . 仅支持jpg、jpeg、png、pdf等格式的文件;</li>
	<li>2 . 单个文件大小不能超过1M;</li>
	<li>3 . 上传文件的数量不超过10个;</li>
   </ul>
  </div>
  
  <div  class="col-md-12">
   <div class="fl padding-10">
    <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows delete" type="submit">删除</button>
	<button class="btn btn-windows save" type="submit">保存</button>
	<button class="btn btn-windows reset" type="submit">重置</button>
	</div>
	<div class="fr padding-top-15"><input type="checkbox"  class="margin-top-0 fl"/><span class="margin-left-5 fl padding-right-25">选中全部文件</span></div>
  </div>
  </form>
  <div class="padding-left-40 padding-right-20 clear  ">
   <ul class="list-unstyled bgdd padding-10">
	<li class="padding-10"> 
    <div class="col-md-4 padding-10 fl">
	 <div class="col-md-3 tc h60 fl"><input type="checkbox"/></div>
	 <div class="col-md-9 padding-0 fl">
	   <div class="fl suolue"> 
        <a href="#" class="thumbnail margin-0 suolue">
         <img src="<%=basePath%>public/ZHH/images/suolue.jpg" class="suolue"/>
        </a>
	   </div>
	 </div>
	</div>
	<div class="col-md-8 padding-10 h60 fl">
	 <div class="col-md-9 fl">1oa－1000乘370.jpg</div>
	 <div class="col-md-3 fl">614.82KB</div>
	</div>
	<div class="clear"></div>
  </li>
  </ul>
  </div>
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
</body>
</html>
