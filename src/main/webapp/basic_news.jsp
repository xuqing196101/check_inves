<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
   <h2 class="padding-20 mt40">
     <span class="new_step current fl"><i class="">1</i><div class="line"></div><span class="step_desc_01">用户名密码</span></span>
	 <span class="new_step current fl"><i class="">2</i><div class="line"></div><span class="step_desc_02">基本信息</span></span>
	 <span class="new_step fl"><i class="">3</i><div class="line"></div><span class="step_desc_01">供应商类型</span></span>
	 <span class="new_step fl"><i class="">4</i><div class="line"></div><span class="step_desc_02">专业信息</span></span>
	 <span class="new_step fl"><i class="">5</i><div class="line"></div><span class="step_desc_01">品目信息</span></span>
	 <span class="new_step fl"><i class="">6</i><div class="line"></div><span class="step_desc_02">产品信息</span></span>
	 <span class="new_step fl"><i class="">7</i><div class="line"></div><span class="step_desc_01">初审采购机构</span></span>
	 <span class="new_step fl"><i class="">8</i><div class="line"></div><span class="step_desc_02">打印申请表</span></span>
	 <span class="new_step fl"><i class="">9</i><span class="step_desc_01">申请表承诺书上传</span></span>
	 <div class="clear"></div>
   </h2>
  </div>
  
  
<!--详情开始-->
<div class="container content height-350">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">财务信息</a></li>
          </ul>
          <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in height-450" id="tab-1">
              <div class=" margin-bottom-0">
                <h2 class="f16 jbxx"><i>01</i>基本信息</h2>
                <ul class="list-unstyled list-flow">
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 公司名称：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 公司网址：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                <li class="col-md-6  p0 ">
	              <span class=""><i class="red">＊</i>成立日期：</span>
	              <div class="input-append">
                   <input class="span2" id="appendedInput" type="text" >
                   <span class="add-on"><img src="<%=basePath%>public/ZHQ/images/time_icon.png" class="mb10"/></span>
                  </div>
	           </li> 
               <li class="col-md-6 p0">
	             <span class=""><i class="red">＊</i>公司性质：</span>
	             <div class="input-append">
                   <input class="span2" id="appendedInput" type="text">
		           <div class="btn-group ">
                    <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		              <img src="<%=basePath%>public/ZHQ/images/down.png" class="margin-bottom-5"/>
                    </button>
                    <ul class="dropdown-menu list-unstyled">
                    </ul>
                   </div>
                 </div>
	           </li> 
			   
               <li class="col-md-6 p0">
	             <span class=""><i class="red">＊</i>公司地址：</span>
				 <div class="fl">
	              <div class="input-append mr20">
                   <input class="span4" id="appendedInput" type="text">
		           <div class="btn-group ">
                    <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		              <img src="<%=basePath%>public/ZHQ/images/down.png" class="margin-bottom-5"/>
                    </button>
                    <ul class="dropdown-menu list-unstyled">
                    </ul>
                   </div>
				   </div>
                   <div class="input-append">
                   <input class="span4" id="appendedInput" type="text">
		           <div class="btn-group ">
                    <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		              <img src="<%=basePath%>public/ZHQ/images/down.png" class="margin-bottom-5"/>
                    </button>
                    <ul class="dropdown-menu list-unstyled">
                    </ul>
                   </div>
				   </div>
                 </div>
	           </li> 
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i>开户行名称：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i>详细地址：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i>开户行账号：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i>注册资金（万）：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i>法人姓名：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i>  企业人数：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 法人身份证号：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 营业执照注册号：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 邮编：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>
                <li class="col-md-12 p0 mt10">
	              <span class="fl"><i class="red">＊</i>公司简介：</span>
	              <div class="col-md-9 mt5">
				    <div class="row">
                    <textarea class="text_area col-md-12 " title="不超过800个字" placeholder=""></textarea>
					</div>
                  </div>
				  <div class="clear"></div>
	            </li>
                <li class="col-md-12 p0 mt10">
	              <span class="fl"><i class="red">＊</i>投标经历：</span>
	              <div class="col-md-9 mt5">
				    <div class="row">
                    <textarea class="text_area col-md-12 " title="不超过800个字" placeholder=""></textarea>
					</div>
                  </div>
				  <div class="clear"></div>
	            </li>
                <li class="col-md-12 p0 mt10">
	              <span class="fl"><i class="red">＊</i> 售后服务地址信息：</span>
	              <div class="col-md-9 mt5">
				    <div class="row">
                    <textarea class="text_area col-md-12 " title="不超过800个字" placeholder=""></textarea>
					</div>
                  </div>
				  <div class="clear"></div>
	            </li>
			   
				<div class="clear"></div>
		    </ul>
				
				
				
				
             <h2 class="f16 jbxx mt40"><i>02</i>资质资信</h2>
                <ul class="list-unstyled list-flow">
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i> 营业执照（三证合一）：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>法人身份证（正反面）：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>开户许可证：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>良好纳税证明：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i> 银行资信：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>良好的财务状况：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i> 三年内无不良记录：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>非外资独资或控股国内企业：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>近三年销售业绩：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>   
                   <li class="col-md-6 p0 ">
	                  <span class="zzzx"><i class="red">＊</i>提供售后服务技术支持：</span>
	                  <div class="input-append">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
	               </li>  
			   <div class="clear"></div>
			   </ul>
                 
             <h2 class="f16 jbxx mt40"><i>03</i>联系人基本信息</h2>  
               <ul class="list-unstyled list-flow">
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 军队项目联系人：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 职务：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 电话：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 手机：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 传真：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class=""><i class="red">＊</i> 邮箱：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
				<div class="clear"></div>
			   </ul>
				<div class="mt40 tc mb50">
				 <button class="btn padding-left-20 padding-right-20 btn_back margin-5">下一步</button>
				 <button class="btn padding-left-20 padding-right-20 btn_back margin-5">暂存</button>
				 <button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button>
				</div>
              </div>
            </div>
            <div class="tab-pane fade height-450" id="tab-2">
              <div class="margin-bottom-0  categories">
             <h2 class="f16 jbxx mt40"><i>03</i>主要股东信息</h2>  
               <ul class="list-unstyled list-flow">
                 <li class="col-md-6 p0 ">
	               <span class="zzzx"><i class="red">＊</i> 出资人（股东）性质：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class="zzzx"><i class="red">＊</i> 国籍：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class="zzzx"><i class="red">＊</i> 出资人（股东）姓名：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class="zzzx"><i class="red">＊</i> 出资金额（万）：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class="zzzx"><i class="red">＊</i> 身份证号码：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
                 <li class="col-md-6 p0 ">
	               <span class="zzzx"><i class="red">＊</i>所占比例：</span>
	               <div class="input-append">
                    <input class="span3" id="appendedInput" type="text">
                   </div>
	            </li>  
				<div class="clear"></div>
			   </ul>
               <h2 class="f16 jbxx mt40"><i>03</i>企业财物状况</h2>  
			   
               <table class="table table-bordered table-condensed">
		         <thead>
		           <tr>
		              <th class="info">年份</th>
		              <th class="info"> 净资产</th>
	                  <th class="info">净利润</th>
		              <th class="info">销售收入</th>
					  <th class="info">负债率</th>
					  <th class="info" colspan="2">附件上传</th>
		          </tr>
		        </thead>
		        <tbody>
				  <tr>
		            <td rowspan="2" class="tc">
					  <div class="input-append mr20">
                         <input class="span4" id="appendedInput" type="text">
		                 <div class="btn-group ">
                             <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		                      <img src="<%=basePath%>public/ZHQ/images/down.png" class="margin-bottom-5">
                             </button>
                             <ul class="dropdown-menu list-unstyled">
                             </ul>
                          </div>
				      </div>
					</td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
	           	 </tr>
				  <tr>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
	           	 </tr>
				  <tr>
		            <td rowspan="2"  class="tc">
					  <div class="input-append mr20">
                         <input class="span4" id="appendedInput" type="text">
		                 <div class="btn-group ">
                             <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		                      <img src="<%=basePath%>public/ZHQ/images/down.png" class="margin-bottom-5">
                             </button>
                             <ul class="dropdown-menu list-unstyled">
                             </ul>
                          </div>
				      </div>
					</td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
	           	 </tr>
				  <tr>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
	           	 </tr>
				  <tr>
		            <td rowspan="2"  class="tc">
					  <div class="input-append mr20">
                         <input class="span4" id="appendedInput" type="text">
		                 <div class="btn-group ">
                             <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		                      <img src="<%=basePath%>public/ZHQ/images/down.png" class="margin-bottom-5">
                             </button>
                             <ul class="dropdown-menu list-unstyled">
                             </ul>
                          </div>
				      </div>
					</td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td rowspan="2"></td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
	           	 </tr>
				  <tr>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
		            <td>
					  <div class="input-append margin-bottom-0">
                        <a href="javascript:void(0);"><i></i>上传附件</a>
                      </div>
					</td>
	           	 </tr>
				 
				 
				 
				 
				 
	           </tbody>   
	          </table>
				<div class="mt40 tc mb50">
				 <button class="btn padding-left-20 padding-right-20 btn_back margin-5">下一步</button>
				 <button class="btn padding-left-20 padding-right-20 btn_back margin-5">暂存</button>
				 <button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button>
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
</div>
</body>
</html>
