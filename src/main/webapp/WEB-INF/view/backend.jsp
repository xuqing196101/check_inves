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
		   <li><a href="#"> 首页</a></li><li><a href="#">后台管理</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 后台管理内容开始-->
<div class="container content height-350 job-content ">

 <div class="row magazine-page">
  <div class="col-md-3 col-md-12 padding-0">
    <div class="col-md-12 p0_10 margin-bottom-20">
     <div class="tag-box tag-box-v3 margin-0 p0_10">
      <div class="margin-0"><h2 class="margin-0 news"> 站内消息</h2></div>
       <ul class="padding-left-20  categories hex padding-bottom-5">
	    <li><a href="#">关于印发2016年集中采购目录的通</a></li>
		<li><a href="#">关于印发2016年集中采购管录通知</a></li>
		<li><a href="#">关于印发2016年集中采购目录通知</a></li>
		<li><a href="#">关于印发2016年集中采购目录的通</a></li>
		<li><a href="#">关于印发2016年集中采购目录的通</a></li>
		<li><a href="#">关于印发2016年集中采购管理目录的目录的通</a></li>
		<li><a href="#">关于印发2016年集中采理目录的通</a></li>
		<li><a href="#">关于印发2016年集中管理目录的通</a></li>
	   </ul>
	  </div>
     </div>
  </div> 
  <div class="col-md-9 padding-0">
    <div class="col-md-6 tab-v5 col-md-12 p0_10">
	  <div class="padding-bottom-17">
       <div class="headline-v2 margin-0 padding-left-0">
	   <h2>代办事项
	   	   <span class="pull-right f14">
	        <a href="#" target="_blank">更多>></a>
	       </span>
	   </h2>
	  </div>
	  </div>
       <ul class="list-unstyled categories tab-content border1 p15_10">       
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-10-30</span>
        </li>              
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-10-13</span>
        </li>    
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-10-01</span>
        </li>  
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-09-18</span>
        </li> 
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-09-15</span>
        </li>   
        <li>
          <a href="#" title="中国储备粮管理总公司地上通风道（地上笼）供应商入围项目招标公告" target="_blank">中国储备粮管理总公司地上通风道（地上笼）...</a>
          <span class="hex pull-right">2015-07-31</span>
        </li>    
        <li>
          <a href="#" title="关于进一步严格磷化铝药剂配送管理的通知" target="_blank">关于进一步严格磷化铝药剂配送管理的通知</a>
          <span class="hex pull-right">2015-07-16</span>
        </li>
        <li>
          <a href="#" title="中国储备粮管理总公司磷化氢环流熏蒸系统供应商入围项目国内公开招标公告" target="_blank">中国储备粮管理总公司磷化氢环流熏蒸系统供...</a>
          <span class="hex pull-right">2015-06-17</span>
        </li> 
      </ul>
     </div>
    <div class="col-md-6 tab-v5 col-md-12 p0_10">
	  <div class="padding-bottom-17">
       <div class="headline-v2 margin-0 padding-left-0">
	   <h2>催办事项
	   	   <span class="pull-right f14">
	        <a href="#" target="_blank">更多>></a>
	       </span>
	   </h2>
	  </div>
	  </div>
       <ul class="list-unstyled categories tab-content border1 p15_10">       
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-10-30</span>
        </li>              
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-10-13</span>
        </li>    
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-10-01</span>
        </li>  
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-09-18</span>
        </li> 
        <li>
          <a href="#" title="" target="_blank">成都军区重庆某医院家具采购技术成都军区…</a>
          <span class="hex pull-right">2015-09-15</span>
        </li>   
        <li>
          <a href="#" title="中国储备粮管理总公司地上通风道（地上笼）供应商入围项目招标公告" target="_blank">中国储备粮管理总公司地上通风道（地上笼）...</a>
          <span class="hex pull-right">2015-07-31</span>
        </li>    
        <li>
          <a href="#" title="关于进一步严格磷化铝药剂配送管理的通知" target="_blank">关于进一步严格磷化铝药剂配送管理的通知</a>
          <span class="hex pull-right">2015-07-16</span>
        </li>
        <li>
          <a href="#" title="中国储备粮管理总公司磷化氢环流熏蒸系统供应商入围项目国内公开招标公告" target="_blank">中国储备粮管理总公司磷化氢环流熏蒸系统供...</a>
          <span class="hex pull-right">2015-06-17</span>
        </li> 
      </ul>
     </div>
    </div>   
  </div>
</div>	


<div class="container content job-content ">

 <div class="row magazine-page">
  <div class="col-md-3 col-md-12 padding-0 margin-bottom-20">
    <div class="col-md-12 p0_10">
     <div class="tag-box tag-box-v3 margin-0 padding-10">
      <div class="margin-0"><h2 class="margin-0 percent"> 本年度辖区内采购方式占比</h2></div>
       <div class="categories margin-0 hex padding-bottom-5 padding-top-20">
	   <div class="">
		<div class="col-md-12 padding-0"><span class="fl">协议供货（¥41845.94万元）</span><span class="fr">71.18%</span></div>
		<div class="col-md-12 padding-0">
         <div class="progress margin-left-0">
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:40%;">
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 30%;"> 
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
          </div>
        </div>
		</div>
		<div class="clear"></div>
		</div>
	   <div class="">
		<div class="col-md-12 padding-0"><span class="fl">协议供货（¥41845.94万元）</span><span class="fr">71.18%</span></div>
		<div class="col-md-12 padding-0">
         <div class="progress margin-left-0">
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:40%;">
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 30%;"> 
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
          </div>
        </div>
		</div>
		<div class="clear"></div>
	   </div>
	   </div>
	   <div class="">
		<div class="col-md-12 padding-0"><span class="fl">协议供货（¥41845.94万元）</span><span class="fr">71.18%</span></div>
		<div class="col-md-12 padding-0">
         <div class="progress margin-left-0">
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:40%;">
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 30%;"> 
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
          </div>
        </div>
		</div>
		<div class="clear"></div>
		</div>
	   <div class="">
		<div class="col-md-12 padding-0"><span class="fl">协议供货（¥41845.94万元）</span><span class="fr">71.18%</span></div>
		<div class="col-md-12 padding-0">
         <div class="progress margin-left-0">
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:40%;">
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 30%;"> 
          </div>
          <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
          </div>
        </div>
		</div>
		<div class="clear">
		</div>
	   </div>
      </div>
    </div> 
	</div>
    <div class="col-md-9 padding-0 col-md-12">
     <div class="col-md-12 p0_10">
      <div class="headline-v2 margin-left-0 margin-bottom-10 margin-top-0">
       <h2>我的协议供货订单</h2>
      </div>
     </div>
<!-- 操作按钮开始-->
    <div class="col-md-12 p0_10 margin-top-10">
      <button class="btn btn-windows add" type="submit">新增</button>
	  <button class="btn btn-windows edit" type="submit">修改</button>
	  <button class="btn btn-windows git" type="submit">提交</button>
	  <button class="btn btn-windows delete" type="submit">删除</button>
	</div>
<!--表格开始  -->
   <div class="clear">
     <div class="content p10_25">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info">凭证编号</th>
		  <th class="info">项目名称</th>
		  <th class="info">总金额（元）</th>
		  <th class="info">
			状态
		  </th>
		</tr>
		</thead>
		<tr>
		  <td>BG-XY-IT20131120106054</td>
		  <td><a href="#" class="black">2013-11-20  台式机采购项目</a></td>
		  <td class="tc">¥40,000.00</td>
		  <td>
          <div class="col-md-12 padding-0">
		  <span class="fl margin-right-5">暂存</span>
		  <div class="progress w80 fl margin-0 ">
             <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:10%;"> 
             </div> 
          </div>
		  <span class="fl ml5">10%</span>
		  </div>
		 
		  </td>
		</tr>
	  </table>
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
