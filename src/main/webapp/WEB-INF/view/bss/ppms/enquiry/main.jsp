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
    <link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <!--导航js-->
    <script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
	$(function(){
	    $("#menu a").click(function() {
		    $('#menu li').each(function(index) {
			    $(this).removeClass('active');  // 删除其他兄弟元素的样式
			  });
	        $(this).parent().addClass('active');                            // 添加当前元素的样式
	    });
	}); 
	
	function back(page){
		location.href = '<%=basePath%>project/list.html?page='+page;
	}
</script>
<body>
  
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs " id="bread_crumbs">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="">保障作业</a></li><li><a href="">采购项目管理</a></li><li><a href="">询价采购项目实施</a></li> 
		</ul>
	  </div>
   </div>
   <!--=== End Breadcrumbs ===-->

   <!--=== Content Part ===-->
   <div class="container content">
            <div class="row">
                <!-- Begin Content -->
                      <div class="col-md-2 col-sm-3 col-xs-12" id="show_tree_div">
	                     <ul class="btn_list" id="menu">
						   <li class="active"><a href="<%=basePath%>project/mplement.html?id=${project.id}" target="open_bidding_main" class="son-menu">项目信息</a></li>
						   <li><a href="<%=basePath%>firstAudit/toAdd.html?projectId=${project.id}" target="open_bidding_main" class="son-menu">拟制询价文件</a></li>
						   <li><a href="<%=basePath%>open_bidding/firstAduitView.html?projectId=${project.id}" target="open_bidding_main" class="son-menu">确认询价文件</a></li>
						   <li><a href="<%=basePath%>SupplierExtracts/Extraction.html?id=${project.id}" target="open_bidding_main" class="son-menu">抽取供应商</a></li>
						   <li><a href="<%=basePath%>saleTender/list.html?projectId=${project.id}" target="open_bidding_main" class="son-menu">发售标书</a></li>
						   <li><a href="<%=basePath%>ExpExtract/Extraction.html?id=${project.id}" target="open_bidding_main" class="son-menu">抽取评审专家</a></li>
						   <li><a href="<%=basePath%>packageExpert/toPackageExpert.html?projectId=${project.id}" target="open_bidding_main" class="son-menu">组织专家评审</a></li>
						   <li><a href="<%=basePath%>open_bidding/bidNotice.html?projectId=${project.id}" target="open_bidding_main" class="son-menu">拟制中标公告</a></li>
						   <li><a href="<%=basePath%>open_bidding/" target="open_bidding_main" class="son-menu">确认成交供应商</a></li>
						 </ul>
					  </div>
                        <script type="text/javascript" language="javascript">   
                          function getContentSize() {
	         				var he = document.documentElement.clientHeight;
							var btn = $("#iframe_btns").outerHeight(true);
	   						var bread= $("#bread_crumbs").outerHeight(true) ;
							ch = (he - btn - bread) + "px";
							document.getElementById("open_bidding_iframe").style.height = ch;
							}
							window.onload = getContentSize;
							window.onresize = getContentSize;
 					  </script>
					  <!-- 右侧内容开始-->
					  <div class="tag-box tag-box-v4 col-md-10 col-sm-9 col-xs-12 " >
						 <iframe  frameborder="0" name="open_bidding_main" id="open_bidding_iframe" scrolling="auto" marginheight="0"  width="100%" onLoad="iFrameHeight();"  src="<%=basePath%>project/mplement.html?id=${project.id}"></iframe>
					  </div>
					  <div class="col-md-12 tc mt20" id="iframe_btns" >
					  		<button class="btn btn-windows back" onclick="back(${page});" type="button">返回项目列表</button>
       	   			  </div>
       	   			</div>
                <!-- End Content -->
        </div><!--/container-->
</body>
</html>
