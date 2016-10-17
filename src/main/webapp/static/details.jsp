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
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
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
                 <img alt="Logo" src="images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 ">
              <ul class="top-v1-data padiing-0">
			    <li>
				<a href="#">
				  <div><img src="images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="images/top_08.png"/></div>
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
   <ul class="list-unstyled padding-left-40">
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">采购单位</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">发票抬头</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">采购单位联系人</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">采购单位联系人座机</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">采购单位联系人手机</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">采购单位地址</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">供应商名称</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">供应商单位联系人</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">供应商单位联系人座机</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">供应商单位联系人手机 </span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">供应商单位地址 </span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">交付日期</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on"><img src="images/time_icon.png"/></span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">预算金额（元）</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">发票编号</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-11 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">备注</span>
	   <div class="">
        <textarea class="col-md-12" style="height:130px" title="不超过800个字"></textarea>
       </div>
	 </li> 
	 
   </ul>
  </div> 
   
  <!-- 产品明细开始-->
  <div class="padding-top-10 clear">
   <div class="headline-v2">
   <h2>产品明细</h2>
   </div>
   <ul class="list-unstyled padding-left-40">
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">品目</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
		<div class="btn-group  margin-bottom-10">
          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		  <img src="images/down.png"/>
          </button>
          <ul class="dropdown-menu list-unstyled">
          </ul>
       </div>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">品牌</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">型号</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">版本号</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">市场单价（元）</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">成交单价（元）</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">数量</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">单位</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">小计（元）</span>
	   <div class="input-append">
        <input class="span5" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-11 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">备注</span>
	   <div class="">
        <textarea class="col-md-12" style="height:130px" title="不超过800个字"></textarea>
       </div>
	 </li> 
	 </ul>
	 <div class="tab-v1 clear col-md-11 margin-20">
      <h2 class="nav nav-tabs">
	  </h2>
    </div>
	 <ul class="list-unstyled padding-left-40">
     <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">运费（元）</span>
	   <div class="input-append col-md-12 padding-0 ">
        <input class="span5 col-md-8 padding-0 " id="appendedInput" type="text">
        <span class="add-on col-md-4">i</span>
       </div>
	 </li> 
     <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">其他费用（元）</span>
	   <div class="input-append col-md-12 padding-0 ">
        <input class="span5 col-md-8 padding-0 " id="appendedInput" type="text">
        <span class="add-on col-md-4">i</span>
       </div>
	 </li> 
     <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">其他费用说明</span>
	   <div class="input-append col-md-12 padding-0 ">
        <input class="span5 col-md-8 padding-0 " id="appendedInput" type="text">
        <span class="add-on col-md-4">i</span>
       </div>
	 </li> 
   </ul>
   <div class="clear padding-left-40"><span>总计：</span><span>¥10000</span></div>
   <div class="headline-v2">
   <h2>上传附件</h2>
   </div>
  </div>
  <div class="padding-left-40 padding-right-40 clear">
   <ul class="list-unstyled  bg8 padding-20">
    <li>1 . 仅支持jpg、jpeg、png、pdf等格式的文件;</li>
	<li>2 . 单个文件大小不能超过1M;</li>
	<li>3 . 上传文件的数量不超过10个;/li>
   </ul>
  </div>
  
  <div  class="col-md-11">
   <div class="fl padding-10">
    <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows delete" type="submit">删除</button>
	<button class="btn btn-windows save" type="submit">保存</button>
	<button class="btn btn-windows reset" type="submit">重置</button>
	</div>
	<div class="fr padding-top-15"><input type="checkbox"  class="margin-top-0 fl"/><span class="margin-left-5 fl">选中全部文件</span></div>
  </div>
  </form>
  <div class="padding-left-40 padding-right-40 clear  ">
   <ul class="list-unstyled bgdd padding-10">
	<li class="padding-10"> 
    <div class="col-md-6 padding-10">
	 <div class="col-md-1 tc"><input type="checkbox"/></div>
	 <div class="col-md-11 padding-0 tl">
	 <div class="col-md-3 padding-0"> 
        <a href="#" class="thumbnail margin-0">
         <img src="images/yzm.jpg"/>
        </a>
	  </div>
	 </div>
	</div>
	<div class="col-md-6 padding-10">
	 <div class="col-md-9">1oa－1000乘370.jpg</div>
	 <div class="col-md-3">614.82KB</div>
	</div>
	<div class="clear"></div>
   </div>
  </li>
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
