<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<head>
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
                 <img alt="Logo" src="<%=basePath%>public/ZHQ/imageslogo.png" id="logo-header">
              </a>
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
            <span class="full-width-menu">全部菜单</span>
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



<!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
   <h2 class="step_flow">
     <span class="new_step current fl"><i class="">1</i><div class="line"></div><span class="step_desc_01">用户名密码</span></span>
	 <span class="new_step fl"><i class="">2</i><div class="line"></div><span class="step_desc_02">基本信息</span></span>
	 <span class="new_step fl"><i class="">3</i><div class="line"></div><span class="step_desc_01">供应商类型</span></span>
	 <span class="new_step fl"><i class="">4</i><div class="line"></div><span class="step_desc_02">专业信息</span></span>
	 <span class="new_step fl"><i class="">5</i><div class="line"></div><span class="step_desc_01">品目信息</span></span>
	 <span class="new_step fl"><i class="">6</i><div class="line"></div><span class="step_desc_02">产品信息</span></span>
	 <span class="new_step fl"><i class="">7</i><div class="line"></div><span class="step_desc_01">初审采购机构</span></span>
	 <span class="new_step fl"><i class="">8</i><div class="line"></div><span class="step_desc_02">打印申请表</span></span>
	 <span class="new_step fl new_step_last"><i class="">9</i><span class="step_desc_01">申请表承诺书上传</span></span>
	 <div class="clear"></div>
   </h2>
  
 <div class="col-md-12 margin-top-40">
  <div class="row">
   <div class="login_main mt20">
   <div class="login_item">
     <label class="col-md-3 p0"><i class="red mr5">*</i>用户名：</label>
	 <input type="text" name="username" class="fl">
	 <span class="fl warning">（用户名由字母、数字、－等字符组成）</span>
	 <div class="clear"></div>
   </div>
   <div class="login_item margin-top-10">
     <label class="col-md-3 p0"><i class="red mr5">*</i>登录密码：</label>
     <input type="password" name="username" class="fl">
     <span class="fl warning">（密码由6-20位，由字母、数字组成）</span>
	 <div class="clear"></div>
   </div>
   <div class="login_item margin-top-10">
     <label class="col-md-3 p0"><i class="red mr5">*</i>确认密码：</label>
     <input type="password" name="username" class="fl">
	 <div class="clear"></div>
   </div>   
   <div class="login_item margin-top-10">
     <label class="col-md-3 p0"><i class="red mr5">*</i>手机号码：</label>
     <input type="text" name="username" class="fl">
	 <button class="btn padding-left-10 padding-right-10 btn_back ml10">发送验证码</button>
	 <div class="clear"></div>
   </div>
   <div class="login_item margin-top-10">
     <label class="col-md-3 p0"><i class="red mr5">*</i>手机验证码：</label>
     <input type="text" name="username" class="fl">
	 <div class="clear"></div>
   </div>
   <div class="login_item margin-top-10">
     <label class="col-md-3 p0"><i class="red mr5">*</i>验证码：</label>
     <input type="text" name="username" class="fl input-yzm">
	 <div class="fl">
	 <div class="yzm fl"><img src="<%=basePath%>public/ZHQ/images/yanzheng.png"/></div>
	 <button class="btn padding-left-10 padding-right-10 btn_back ml10 fl">换一张</button>
	 </div>
	 <div class="clear"></div>
   </div>
  <div class="tc mt20 clear col-md-11">
    <button class="btn padding-left-20 padding-right-20 btn_back margin-5">下一步</button> 
    <button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button>
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
			  Copyright &#169 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="ratio">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->

<!--/footer--> 
</div>
</body>
</html>
