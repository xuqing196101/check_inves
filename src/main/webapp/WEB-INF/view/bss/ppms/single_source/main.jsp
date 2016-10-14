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

    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <!--导航js-->
    <script src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
</head>

<body>
  
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="">保障作业</a></li><li><a href="">采购项目管理</a></li> 
		</ul>
	  </div>
   </div>
   <!--=== End Breadcrumbs ===-->

   <!--=== Content Part ===-->
   <div class="container content height-350">
      <div class="row">
          <!-- Begin Content -->
          <div class="col-md-12" style="min-height:400px;">
             <div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
			  <div class="tag-box tag-box-v3">
				<ul id="ztree_show" class="ztree">
				  <li id="ztree_show_1" class="level0" tabindex="0" hidefocus="true" treenode="">
				    <span id="ztree_show_1_switch" title="" class="button level0 switch root_close" treenode_switch=""></span>
					<a id="ztree_show_1_a" class="level0" treenode_a="" onClick="" target="_blank" style="" title="xxxx有限公司">
					<span id="ztree_show_1_ico" title="" treenode_ico="" class="button ico_close" style=""></span>
					<span id="ztree_show_1_span">xxxx有限公司</span></a>
				  </li>
				</ul>
			  </div>
			 </div>
			 <div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
				<div class="tab-content">
		  			<div class="tab-pane fade active in" id="show_ztree_content">
						<div class="panel-heading overflow-h margin-bottom-20 no-padding" id="ztree_title">
				          <div class="pull-right">
					        <a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i class="fa fa-search-plus"></i> 详细</a> 
							<a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i class="fa fa-wrench"></i> 修改</a> 
							<a class="btn btn-sm btn-default" href="javascript:void(0)" onClick=""><i class="fa fa-plus"></i> 增加下属单位</a> 
							<a class="btn btn-sm btn-default" data-toggle="modal" href=""><i class="fa fa-plus"></i> 增加人员</a>
				          </div>
						 </div>
					 </div>
				</div>
			 </div>
		  </div>
	  </div>
   </div><!--/container-->
</body>
</html>
