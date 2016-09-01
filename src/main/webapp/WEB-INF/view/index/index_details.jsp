<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
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
	<link href="<%=basePath%>public/ZHQ/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/bootstrap.min.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHQ/css/style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/img-hover.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/page_job.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHQ/css/shop.style.css" media="screen" rel="stylesheet">
<script src="<%=basePath%>public/ZHQ/js/hm.js"></script><script src="<%=basePath%>public/ZHQ/js/jquery.min.js"></script>
<!--导航js-->
<script src="<%=basePath%>public/ZHQ/js/jquery_ujs.js"></script>
<script src="<%=basePath%>public/ZHQ/js/bootstrap.min.js"></script>
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
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHQ/images/logo.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-4 mt50">
              <div class="search-block-v2">
                <div class="">
                  <form accept-charset="UTF-8" action="" method="get">
				    <div style="display:none">
				     <input name="utf8" value="✓" type="hidden">
					</div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 pull-right">
                      <div class="input-group bround4">
                        <input class="form-control h38" id="k" name="k" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-u h38" name="commit" value="搜索" type="submit">
                        </span>
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
            <span class="full-width-menu">全部商品分类</span>
            <span class="icon-toggle">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </span>
          </button>
      </div>

    <div class="clearfix"></div>

    <div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
    <div class="container">
      <ul class="nav navbar-nav">
      <!-- 通知 -->
        <li class="active dropdown tongzhi_li">
          <a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a>
        </li>
      <!-- End 通知 -->

      <!-- 公告 -->
        <li class="dropdown gonggao_li">
          <a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a>
        </li>
      <!-- End 公告 -->

      <!-- 公示 -->
        <li class="dropdown gongshi_li">
          <a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a>
        </li>
      <!-- End 公示 -->

      <!-- 专家 -->
        <li class="dropdown zhuanjia_li">
          <a  href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a>
        </li>
      <!-- End 专家 -->

      <!-- 投诉 -->
        <li class="dropdown tousu_li">
          <a data-toggle="dropdown" class="dropdown-toggle p0_30" href="" ><i class="tousu nav_icon"></i>投诉</a>
        </li>
      <!-- End 投诉 -->

      <!-- 法规 -->
        <li class="dropdown  fagui_li">
          <a href="" class="dropdown-toggle p0_30" data-toggle="dropdown" ><i class="fagui nav_icon"></i>法规</a>
        </li>
      <!-- End 法规 -->

        <li class="dropdown luntan_li">
          <a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a>
        </li>

      </ul>
    </div>
	</div>
  <!--/end container-->
   </div>
  </div>
<div class="container content height-350 job-content ">
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">${articleDetail.articleType.name}</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="col-md-12 p30_40 border1 margin-top-20">
     <h3 class="tc f30">
	   <div class="title bbgrey ">${articleDetail.name}</div>
	   <div class="new_time bbgrey mt10">（2016-KJBJ-1008-2)</div>
	 </h3>
	 <div class="p15_0" ><div class="fr"><span>文章来源：${articleDetail.user.name }</span><span class="ml15"><i class="mr5"><img src="<%=basePath%>public/ZHQ/images/block.png"/></i><fmt:formatDate value='${i.createdAt}' pattern="yyyy.MM.dd" /></span></div></div>
	 <div class="clear margin-top-20 new_content">
	    ${articleDetail.content }
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
