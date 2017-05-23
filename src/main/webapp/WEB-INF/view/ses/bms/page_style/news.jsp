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
	<jsp:include page="backend_common.jsp"></jsp:include>	
	
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
  <div class="container content height-350 job-content ">
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">中标公示</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
    <div class="col-md-12 p20 border1 margin-top-20">
        <div class="tab-v1">
          <h2 class="nav nav-tabs border0 padding-left-15">
            采购公告
		  </h2>
        </div>
          <div class="tab-content margin-bottom-20 margin-top-10">
            <div class="tab-pane fade active in">
              <div class="tag-box margin-bottom-0 padding-0">
                <ul class="categories li_square padding-left-15 margin-bottom-0">
                                
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>济南军区青岛第一疗养院理体疗康复中心豪华型呼叫系统竞争性谈判成交结果公示（2016-JNYL-3011）</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>              
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>济南军区青岛第一疗养院理体疗康复中心豪华型呼叫系统竞争性谈判成交结果公示（2016-JNYL-3011）</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>〇五单位五五七部保密柜、密码携行箱、维修工具箱预中标结果公示[2015-ZHBJ-1102]...</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>              
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>重庆通信学院科训楼E区教学场地空调采购项目中标公示（2016-CQTY-JC-012）</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>
                     
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>重庆通信学院科训楼E区教学场地空调采购项目中标公示（2016-CQTY-JC-012）...</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>              
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>〇五单位五五一部信息化设备（第四包）中标结果公示（2015-ZHSY-1026）</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>〇五单位五五一部信息化设备（第四包）中标结果公示（2015-ZHSY-1026）...</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>              
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>重庆通信学院科训楼E区教学场地空调采购项目中标公示（2016-CQTY-JC-012）..</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>中央储备粮金昌直属库其他办公用品竞价...</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li>              
                  <li>
                   <a href="" title="" target="_blank"><span class="f18 mr5">·</span>中央储备粮惠州直属库打印机竞价项目</a>
                   <span class="hex pull-right">2015-11-20</span>
                  </li> 
				  
                  
                </ul>
              </div>
            </div>
          </div>
	     <div class="fenye">
           <div class="page_box fr">
	         <span class="pre_page page">上一页</span><span class="curr_page page">1</span><span class="page">2</span><span class="page">3</span><span class="page">4</span><span class="page">5</span><span class="page">6</span><span class="next_page page">下一页</span><span class="ml15">到</span><input type="text" class="page_input" value="1">页 <input type="submit" class="ml10 search_page" value="确 定">
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
              <div class="ratio">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->

<!--/footer--> 
    </div>
</div>
</body>
</html>
