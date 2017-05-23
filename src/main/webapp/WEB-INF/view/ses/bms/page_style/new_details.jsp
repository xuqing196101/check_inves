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
   
   <div class="col-md-12 p30_40 border1 margin-top-20">
     <h3 class="tc f30">
	   <div class="title bbgrey ">中国人民解放军95981部队气象物资中标结果公示</div>
	   <div class="new_time bbgrey mt10">（2016-KJBJ-1008-2)</div>
	 </h3>
	 <div class="p15_0" ><div class="fr"><span>文章来源：空军</span><span class="ml15"><i class="mr5"><img src="<%=basePath%>public/ZHQ/images/block.png"/></i>2016.01.15</span></div></div>
	 <div class="clear margin-top-20 new_content">
	       中国人民解放军95981部队对气象物资进行了公开招标，现就本次评标结果排序（前三名）及中标结果公示如下：
一、项目名称：气象物资
二、项目编号：2016-KJBJ－1008-2
三、公示有效时间：2016年6月15日-2016年6月19日
四、评审结果
第一包：便携式气压发生器
1.杭州佐格通信设备有限公司；
2.太原市太航压力测试科技有限公司；
3.浙江蓝天气象技术装备有限公司；
根据评审结果，确定排名第一的杭州佐格通信设备有限公司为中标供应商。
第二包：高精度镜面露点仪
1.杭州佐格通信设备有限公司；
2.北京市国瑞智新技术有限公司；
3.浙江蓝天气象技术装备有限公司；
根据评审结果，确定排名第一的杭州佐格通信设备有限公司为中标供应商。
五、如有关供应商对评审结果有异议，可以在公示有效期内，以书面形式向我部提出质疑，我部将在收到书面质疑7个工作日内，向质疑供应商做出书面答复。
对积极参与本次采购活动的供应商深表感谢，希望今后继续保持合作。
联系人：张主任
联系电话：010-66712665-8013

中国人民解放军95981部队
2016年6月16日
	 
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
