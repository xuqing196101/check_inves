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
  <!-- End Navbar -->
  <div class="container content height-350 job-content ">
     <div class="row magazine-page">
      <div class="col-md-6  margin-bottom-10">
        <div class="tab-v1">
          <h2 class="nav nav-tabs">
            工作动态
		  </h2>
        </div>
          <div class="tab-content">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-left-0">
                <ul class="list-unstyled categories">
                     
                 <li>
                  <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                  <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>
                 <li>
                  <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                  <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>
                 <li>
                  <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                  <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>
                 <li>
                  <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                  <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                  <span class="hex pull-right">2015-11-20</span>
                 </li>
      
                  
                </ul>
              </div>
            </div>
          </div>
       </div>
	  
	  
       <div class="col-md-6 ">
        <div class="tab-v1">
          <h2 class="nav nav-tabs">
            业务通知
		  </h2>
		  </div>
          <div class="tab-content">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-left-0">
                <ul class="list-unstyled categories">
                     
                   <li>
                     <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a> 
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a> 
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a> 
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                   <li>
                     <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a> 
                     <span class="hex pull-right">2015-11-20</span>
                   </li>
                  
                </ul>
             </div>
	        </div>
          </div>
       </div>
	  </div>
  </div>
  <div class="container content height-350">
   <div class="row magazine-page">
    <div class="col-md-9">
	
    <div class="row job-content tab-v2">
	
      <div class="col-md-6 tab-v2 margin-bottom-10">
        <div class="tag-box-v1 margin-bottom-0">
          <h2>采购公告
           <span class="badge badge-light pull-right"><a href="#" target="_blank">更多>></a></span>
          </h2>
          <ul class="list-unstyled categories tab-content margin-0">
              
          <li>
             <a href="#" title="中国储备粮管理总公司2015年度气调及智能通风系统供应商入围项目招标公告" target="_blank">中国储备粮管理总公司2015年度气调及智能通...</a>
            <span class="hex pull-right">2015-10-30</span>
          </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司2015年公务用车采购供应商入围项目国内公开招标公告" target="_blank">中国储备粮管理总公司2015年公务用车采购供...</a>
          
          <span class="hex pull-right">2015-10-13</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司2015年度门窗、汽车衡供应商入围项目国内公开招标公告" target="_blank">中国储备粮管理总公司2015年度门窗、汽车衡...</a>
          
          <span class="hex pull-right">2015-10-01</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司粮食机械设备供应商入围项目招标公告" target="_blank">中国储备粮管理总公司粮食机械设备供应商入...</a>
          
          <span class="hex pull-right">2015-09-18</span>
        </li>
      
              
        <li>
          <a href="#" title="中储粮总公司政策性粮食收购系统配套设备竞争性谈判通知" target="_blank">中储粮总公司政策性粮食收购系统配套设备竞...</a>
          
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

      <div class="col-md-6 tab-v2 ">
        <div class="tag-box-v1 margin-bottom-10">
          <h2>中标公告
          <span class="badge badge-light pull-right"><a href="#" target="_blank">更多>></a></span>
          </h2>
          <ul class="list-unstyled categories tab-content">
              
        <li>
          <a href="#" title="中储粮总公司第三期门窗、汽车衡供应商入围项目入围结果公告" target="_blank">中储粮总公司第三期门窗、汽车衡供应商入围...</a>
          
          <span class="hex pull-right">2015-10-27</span>
        </li>
      
              
        <li>
          <a href="#" title="中储粮总公司第三期输送、清理、风机项目供应商入围结果公告" target="_blank">中储粮总公司第三期输送、清理、风机项目供...</a>
          
          <span class="hex pull-right">2015-10-27</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司地上通风道（地上笼）供应商入围项目入围结果公告" target="_blank">中国储备粮管理总公司地上通风道（地上笼）...</a>
          
          <span class="hex pull-right">2015-09-08</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司与嘉能可公司进口玉米纠纷索赔一案代理律师选聘项目竞争性谈判结果公告" target="_blank">中国储备粮管理总公司与嘉能可公司进口玉米...</a>
          
          <span class="hex pull-right">2015-08-10</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司《中华粮仓集锦》画册设计及制作项目竞争性谈判结果公告" target="_blank">中国储备粮管理总公司《中华粮仓集锦》画册...</a>
          
          <span class="hex pull-right">2015-07-30</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司磷化氢环流熏蒸系统供应商入围项目入围结果公告" target="_blank">中国储备粮管理总公司磷化氢环流熏蒸系统供...</a>
          
          <span class="hex pull-right">2015-07-14</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司谷物冷却机、空调制冷系统供应商入围项目入围结果公告" target="_blank">中国储备粮管理总公司谷物冷却机、空调制冷...</a>
          
          <span class="hex pull-right">2015-07-08</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司太阳能光伏发电系统供应商入围结果公告" target="_blank">中国储备粮管理总公司太阳能光伏发电系统供...</a>
          
          <span class="hex pull-right">2015-05-06</span>
        </li>
      
            
          </ul>
        </div>
      </div>
	</div>  
	</div>
  <!-- Begin 右侧边栏 -->
  <div class="col-md-3">

    <div class="tag-box-v6 margin-bottom-10 tag-box-v1 padding-bottom-30">
      <div class=""><h2 class=""> 用户登录</h2></div>
<<<<<<< HEAD
      <form class="form-horizontal p15_0">
        <div class="control-group margin-top-15">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" class="form-control" placeholder="请输入用户名"/>
=======
      <form class="form-horizontal p15_0" action="<%=basePath %>login/login.do" method="post">
        <div class="control-group margin-top-15">
          <label class="control-label" for="inputEmail">用户名：</label>
			  <div class="controls padding-right-10">
            <input type="text" id="inputEmail" name="loginName" class="form-control" placeholder="请输入用户名"/>
>>>>>>> abc0f768372ddc08a7eb316e1f3aa51ef55971e4
          </div>
        </div>
        <div class="control-group  margin-top-20 padding-right-10">
          <label class="control-label" for="inputPassword">密码：</label>
          <div class="controls">
<<<<<<< HEAD
          <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码">
=======
          <input type="password" id="inputPassword" name="password" class="form-control" placeholder="请输入密码">
>>>>>>> abc0f768372ddc08a7eb316e1f3aa51ef55971e4
          </div>
        </div>
        <div class="control-group  margin-top-20 ">
        <label class="control-label" for="inputPassword">验证码：</label>
        <div class="controls">
          <input type="password" placeholder="" class="input-mini fl">
		  <span class=" margin-left-20"><img src="<%=basePath%>public/ZHQ/images/yzm.jpg"/></span>
        </div>
       </div>
       <div class="control-group margin-top-22 clear">
        <div class="controls">
          <button class="btn" type="submit">登陆</button>
		  <button class="btn btn-u-light-grey margin-left-20" type="reset">重置</button>
        </div>
      </div>
    </form>

	  
    </div>
    </div>
  </div>
  </div>
  <!-- End 右侧边栏 -->
  <!--/*中间图片*/-->
  <div class="container content">
   <div class="margin-bottom-10">
	  <img src="<%=basePath%>public/ZHQ/images/center_pic.jpg" class="img-responsive full-width"/>
    </div>
</div>
  <div class="container content height-350">
   <div class="row magazine-page">
    <div  class="col-md-9">
	
    <div class="row job-content margin-bottom-10 tab-v2">
	
      <div class="col-md-6 margin-bottom-10">
        <div class="tag-box-v1 margin-bottom-0">
          <h2>业务通知
          <span class="badge badge-light pull-right"><a href="#" target="_blank">更多>></a></span>
         </h2>
          <ul class="list-unstyled categories tab-content">
              
        <li>
          <a href="#" title="中国储备粮管理总公司2015年度气调及智能通风系统供应商入围项目招标公告" target="_blank">中国储备粮管理总公司2015年度气调及智能通...</a>
          
          <span class="hex pull-right">2015-10-30</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司2015年公务用车采购供应商入围项目国内公开招标公告" target="_blank">中国储备粮管理总公司2015年公务用车采购供...</a>
          
          <span class="hex pull-right">2015-10-13</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司2015年度门窗、汽车衡供应商入围项目国内公开招标公告" target="_blank">中国储备粮管理总公司2015年度门窗、汽车衡...</a>
          
          <span class="hex pull-right">2015-10-01</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司粮食机械设备供应商入围项目招标公告" target="_blank">中国储备粮管理总公司粮食机械设备供应商入...</a>
          
          <span class="hex pull-right">2015-09-18</span>
        </li>
      
              
        <li>
          <a href="#" title="中储粮总公司政策性粮食收购系统配套设备竞争性谈判通知" target="_blank">中储粮总公司政策性粮食收购系统配套设备竞...</a>
          
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

      <div class="col-md-6">
        <div class="tag-box-v1 margin-bottom-0">
          <h2>投诉处理
      <span class="badge badge-light pull-right"><a href="#" target="_blank">更多>></a></span>
    </h2>
          <ul class="list-unstyled categories tab-content">
              
        <li>
          <a href="#" title="中储粮总公司第三期门窗、汽车衡供应商入围项目入围结果公告" target="_blank">中储粮总公司第三期门窗、汽车衡供应商入围...</a>
          
          <span class="hex pull-right">2015-10-27</span>
        </li>
      
              
        <li>
          <a href="#" title="中储粮总公司第三期输送、清理、风机项目供应商入围结果公告" target="_blank">中储粮总公司第三期输送、清理、风机项目供...</a>
          
          <span class="hex pull-right">2015-10-27</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司地上通风道（地上笼）供应商入围项目入围结果公告" target="_blank">中国储备粮管理总公司地上通风道（地上笼）...</a>
          
          <span class="hex pull-right">2015-09-08</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司与嘉能可公司进口玉米纠纷索赔一案代理律师选聘项目竞争性谈判结果公告" target="_blank">中国储备粮管理总公司与嘉能可公司进口玉米...</a>
          
          <span class="hex pull-right">2015-08-10</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司《中华粮仓集锦》画册设计及制作项目竞争性谈判结果公告" target="_blank">中国储备粮管理总公司《中华粮仓集锦》画册...</a>
          
          <span class="hex pull-right">2015-07-30</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司磷化氢环流熏蒸系统供应商入围项目入围结果公告" target="_blank">中国储备粮管理总公司磷化氢环流熏蒸系统供...</a>
          
          <span class="hex pull-right">2015-07-14</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司谷物冷却机、空调制冷系统供应商入围项目入围结果公告" target="_blank">中国储备粮管理总公司谷物冷却机、空调制冷...</a>
          
          <span class="hex pull-right">2015-07-08</span>
        </li>
      
              
        <li>
          <a href="#" title="中国储备粮管理总公司太阳能光伏发电系统供应商入围结果公告" target="_blank">中国储备粮管理总公司太阳能光伏发电系统供...</a>
          
          <span class="hex pull-right">2015-05-06</span>
        </li>
      
            
          </ul>
        </div>
      </div>
	</div>  
	</div>
  <!-- Begin 右侧边栏 -->
  <div class="col-md-3">

    <div class="tag-box-v6 margin-bottom-10 tag-box-v1">
      <h2 class=""> 用户登录</h2>
	  <div class="padding-top-13 padding-bottom-10">
	  <ul class="list-inline blog-photostream ">
      <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm qyzc"></i>企业注册</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjzc"></i>专家注册</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm qyml"></i>企业名录</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm zjmd"></i>专家名单</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm xzzq"></i>下载专区</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm gjfg"></i>国家法规</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm jgzs"></i>价格指数</span></a></li>
	  <li class="fl margin-5"><a href="#" class="content-boxes-v3"><span class="btn-u btn-u-lg btn-u-yellow"><i class="icon-custom icon-sm cfmd"></i>处罚名单</span></a></li>
	  </ul>
	  <div class="clear"></div>
	  </div>
    </div>
    </div>
  </div>



</div>


<div class="container content height-350">
 <div class="row magazine-page">
   <div class="col-md-6 tab-v2 job-content margin-bottom-10">
        <div class="">
          <ul class="nav nav-tabs">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" style="font-size:18px;">国内动态</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" style="font-size: 18px;">时政要闻</a></li>
			<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" style="font-size: 18px;">理论探索</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade active in" id="tab-1">
              <div class=" margin-bottom-0  categories">
                <ul class="list-unstyled categories">              
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">222222中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>            
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>                   
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>      
                 <li>
                   <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>        
                 <li>
                   <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>
                </ul>
              </div>
            </div>
            <div class="tab-pane fade" id="tab-2">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">2eqwwqw22222中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>            
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>                   
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>      
                 <li>
                   <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>        
                 <li>
                   <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>
			   </ul>
              </div>
            </div>
			
            <div class="tab-pane fade" id="tab-3">
              <div class=" margin-bottom-10  categories">
                <ul class="list-unstyled categories">              
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">44442中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>            
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>                   
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>      
                 <li>
                   <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>        
                 <li>
                   <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>
                </ul>
              </div>
            </div>
          </div> 
		</div> 
  </div>
    <div class="col-md-6 job-content tab-v2">
        <div class="">
          <ul class="nav nav-tabs">
            <li class="active"><a aria-expanded="true" href="#tab-4" data-toggle="tab" style="font-size:18px;">国际前沿</a></li>
            <li class=""><a aria-expanded="false" href="#tab-5" data-toggle="tab" style="font-size:18px;">以案说法</a></li>
			<li class=""><a aria-expanded="false" href="#tab-6" data-toggle="tab" style="font-size:18px;">实物操作</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade active in" id="tab-4">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
                    
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">2244442222中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>            
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>                   
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>      
                 <li>
                   <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>        
                 <li>
                   <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>           
                </ul>

              </div>
            </div>
            <div class="tab-pane fade" id="tab-5">
              <div class="margin-bottom-0">
                <ul class="list-unstyled categories">
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">2222333322中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>            
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>                   
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>      
                 <li>
                   <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>        
                 <li>
                   <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>
                </ul>

              </div>
            </div>
			
            <div class="tab-pane fade" id="tab-6">
              <div class="margin-bottom-0  categories">
                <ul class="list-unstyled categories">
                    
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">2244442222中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>         
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>            
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>          
                 <li>
                    <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>                   
                 <li>
                    <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                    <span class="hex pull-right">2015-11-20</span>
                 </li>      
                 <li>
                   <a href="#" title="中央储备粮金昌直属库其他办公用品竞价项目" target="_blank">中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>        
                 <li>
                   <a href="#" title="中央储备粮惠州直属库打印机竞价项目" target="_blank">中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                 </li>           
                </ul>

              </div>
            </div>
			
          </div>
        </div>
    </div>
  </div>
</div> 
	 
<!--友情链接代码开始-->
   <div class="container content padding-top-0">
     <div class=" magazine-page">
     <div class="partners">
       <div class="headline margin-top-30"><h2 class="padding-left-15">友情链接</h2></div>
       <ul class="list-inline our-clients margin-top-20" id="effect-2">

    <li>
        <a href="http://www.ctba.org.cn/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_01.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://ecp.sgcc.com.cn/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_02.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://www.bidding.csg.cn/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_03.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://eportal.energyahead.com/" target="_blank">
		  <img src="<%=basePath%>public/ZHQ/images/friend_04.jpg"/>
        </a>
    </li>
    <li>
        <a href="http://www.sinograin.com.cn/" target="_blank">
          <img src="<%=basePath%>public/ZHQ/images/friend_05.jpg"/>
        </a>
    </li>
  </ul>
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
<!--[if lt IE 9]>
    <script src="/assets/plugins/respond.js?body=1"></script>
<script src="/assets/plugins/html5shiv.js?body=1"></script>
<script src="/assets/plugins/html5.js?body=1"></script>
<script src="/assets/plugins/placeholder-IE-fixes.js?body=1"></script>
<script src="/assets/ie_9.js?body=1"></script>
<![endif]-->

</body>
</html>
