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
<script src="<%=basePath%>public/ZHH/js/WdatePicker.js"></script>
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
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
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
				  <div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  <a href="#">
				  <span>支撑环境</span>
				  </a>
				   <ul class="dropdown-menu">
                   <li><a href="#" target="_blank" class="son-menu">后台管理</a></li>
                   <li><a href="#" target="_blank" class="son-menu">用户管理</a></li>
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
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">订单中心</a></li><li class="active"><a href="#">查看订单</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!--查看订单流程开始-->
  <div class="container clear margin-top-30">
   <div class="list-unstyled padding-20 breadcrumbs-v3 bbgreen">
    <span>
	  <a href="#" class="img-v1 green_link">下单</a>
	  <span class="green_link">→</span>
	</span>
	<span>
	  <a href="#" class="img-v2 orange_link">卖方确认</a>
	  <span class="">→</span>
	</span>
	<span>
	  <a href="#" class="img-v3">买方确认</a>
	  <span class="">→</span>
	</span>
    <span>
	  <a href="#" class="img-v4">评价</a>
	  <span class="">→</span>
	</span>
    <span>
	  <a href="#" class="img-v5">完成</a>
	</span>
   </div>
  </div>
  
  
<!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
   <h2 class="bg8 padding-20 bbyellow xmsj">
	 <span class="brown">2015-06-20</span><span class="margin-left-10 brown">台式机采购项目</span><div class="btn bround14 margin-left-20">选定成交人</div>
   </h2>
  </div>
  
  
<!--详情开始-->
<div class="container content height-350">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgwhite">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">附件</a></li>
			<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="record f18">历史纪录</a></li>
          </ul>
          <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in height-450" id="tab-1">
              <div class=" margin-bottom-0">
                <h2 class="f16 jbxx">基本信息</h2>
				<table class="table table-condensed table-bordered">
				 <tbody>
				 <tr>
				  <td class="bggrey">采购单位：</td>
				  <td>中国储备粮管理总公司</td>
				  <td class="bggrey">发票抬头：</td>
				  <td>中国储备粮管理总公司</td>
				  <td class="bggrey">采购单位联系人：</td>
				  <td>系统管理员</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">采购单位联系人座机：</td>
				  <td> －</td>
				  <td class="bggrey">采购单位联系人手机:</td>
				  <td>－</td>
				  <td class="bggrey">采购单位地址：</td>
				  <td>北京市西城区西直门外大街甲</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">供应商名称：</td>
				  <td>商丘市庆源机械设备有限公司</td>
				  <td class="bggrey">供应商单位联系人:</td>
				  <td>联系人</td>
				  <td class="bggrey">供应商单位联系人座机：</td>
				  <td>56229828</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">供应商单位联系人手机：</td>
				  <td>13011111111</td>
				  <td class="bggrey">供应商单位地址：</td>
				  <td>商丘市庆源机械设备有限公司</td>
				  <td class="bggrey">交付日期：</td>
				  <td>  2016-06-30 </td>
				 </tr> 
				 <tr>
				  <td class="bggrey">预算金额：</td>
				  <td> 10000.0</td>
				  <td class="bggrey">发票编号：</td>
				  <td></td>
				  <td class="bggrey"></td>
				  <td></td>
				 </tr> 
				 <tr>
				  <td class="bggrey">备注：</td>
				  <td colspan="5"></td>
				 </tr> 
				 
				</tbody>
			   </table>
              </div>
             <div class=" margin-bottom-0">
                <h2 class="f16 jbxx">产品明细</h2>
				<table class="table table-condensed table-bordered">
				 <tbody>
				 <tr>
				  <td class="bggrey">品目：</td>
				  <td>台式机</td>
				  <td class="bggrey">品牌：</td>
				  <td>123</td>
				  <td class="bggrey">型号：</td>
				  <td>123</td>
				  <td class="bggrey">版本号：</td>
				  <td>123</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">市场单价（元）：</td>
				  <td>1200.0</td>
				  <td class="bggrey">成交单价（元）:</td>
				  <td>1000.0</td>
				  <td class="bggrey">数量：</td>
				  <td>1000.0</td>
				  <td class="bggrey">单位：</td>
				  <td>123</td>
				 </tr> 
				 <tr>
				  <td class="bggrey">小计（元）：</td>
				  <td>10000.0</td>
				  <td class="bggrey">供应商单位联系人:</td>
				  <td>联系人</td>
				  <td class="bggrey">供应商单位联系人座机：</td>
				  <td>56229828</td>
				  <td class="bggrey"></td>
				  <td></td>
				 </tr> 
				 <tr>
				  <td class="bggrey">备注：</td>
				  <td colspan="7"></td>
				 </tr> 
				</tbody>
			   </table>
              </div>
              <h2 class="f16"><span>总计：</span>￥1000.0</h2>
            </div>
            <div class="tab-pane fade height-450" id="tab-2">
              <div class="margin-bottom-0  categories">

              </div>
            </div>
			
            <div class="tab-pane fade height-450" id="tab-3" style="position:relative">
              <div class=" margin-bottom-0">
                <div class="tml_container padding-top-0">
				  <div class="dingwei">
				  <div class="tml_spine">
					<span class="tml_spine_bg"></span>
					<i id="add_event_plus" class="spine_plus" style="top: -6px; display: none;"></i>
					<span id="timeline_start_point" class="start_point"></span>
				  </div>
				  <div class="tml_poster" id="post_area" ><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="f16 history_icon">分公司审核</h2>
				        <div class="padding-left-40">
				 		  <span>确认并结束审核流程，理由是：同意采购。</span>
						   <ul class="list-unstyled margin-bottom-0">
						   <li class="fl margin-left-0">状态：<span>暂存</span></li>
						   <li class="fl">姓名：<span>韩扬</span></li>
						   <li class="fl">ID：<span>152260</span></li>
						   <li class="fl">单位：<span>军队采购网</span></li>
						   <li class="">IP地址：<span>124.65.46.14｜北京市</span></li>
						   </ul>
					    </div>
                     </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				 </div>
                </div>
               </div>
			  </div>
			  
			  
              <div class=" margin-bottom-0">
                <div class="tml_container">
				 <div class="dingwei">
				  <div class="tml_spine">
					<span class="tml_spine_bg"></span>
					<i id="add_event_plus" class="spine_plus" style="top: -6px; display: none;"></i>
					<span id="timeline_start_point" class="start_point"></span>
				  </div>
				  <div class="tml_poster" id="post_area" style=""><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="f16 history_icon">选择中标人</h2>
				        <div class="padding-left-40">
				 		  <span>选择中标人成功！请等待分公司审核。选择［湖南嘉利塑业有限公司］为中标单位</span>
						   <ul class="list-unstyled margin-bottom-0">
						   <li class="fl margin-left-0">状态：<span>暂存</span></li>
						   <li class="fl">姓名：<span>韩扬</span></li>
						   <li class="fl">ID：<span>152260</span></li>
						   <li class="fl">单位：<span>军队采购网</span></li>
						   <li class="">IP地址：<span>124.65.46.14｜北京市</span></li>
						   </ul>
					    </div>
                   </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				 </div>
                </div>
			   </div>
              </div>
			  
			  
              <div class=" margin-bottom-0">
                <div class="tml_container">
				  <div class="dingwei">
				  <div class="tml_spine">
					<span class="tml_spine_bg"></span>
					<i id="add_event_plus" class="spine_plus" style="top: -6px; display: none;"></i>
					<span id="timeline_start_point" class="start_point"></span>
				  </div>
				  <div class="tml_poster" id="post_area" ><div class="poster" id="poster_1">
                   <div class=" margin-bottom-0">
                       <h2 class="f16 history_icon">报价</h2>
				        <div class="padding-left-40">
				 		  <span>［广西华塑集团有限公司］报价成功！</span>
						   <ul class="list-unstyled margin-bottom-0">
						   <li class="fl margin-left-0">状态：<span>暂存</span></li>
						   <li class="fl">姓名：<span>韩扬</span></li>
						   <li class="fl">ID：<span>152260</span></li>
						   <li class="fl">单位：<span>军队采购网</span></li>
						   <li class="">IP地址：<span>124.65.46.14｜北京市</span></li>
						   </ul>
					    </div>
                     </div>
				  </div>
				  <div class="period_header"><span>11:17:41 2015-11-18</span></div>
				  <span class="ui_left_arrow">
				    <span class="ui_arrow"></span>
				  </span>
				 </div>
                </div>
               </div>
			  </div>
          </div> 
		</div> 
     </div>
  </div>
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
