<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<!--导航js-->
</head>

<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">切换标签页面</a></li> 
		</ul>
	  </div>
   </div>
 <div class="container">
  <div class="mt10">
	   <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
	   <div class="tab-content mt10">
          <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
              <li class="active"><a href="#dep_tab-0" data-toggle="tab" class="f18">页签一</a></li>
			  <li><a href="#dep_tab-1" data-toggle="tab" class="f18">页签二</a></li>
			  <li><a href="#dep_tab-2" data-toggle="tab" class="f18">页签三</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
                                         内容一
            </div>
            <div class="tab-pane fade in" id="dep_tab-1">
                                           内容二
            </div>
			<div class="tab-pane fade in" id="dep_tab-2">
		                      内容三
		    </div>
	   </div>
	</div>		  
       </div>

  </body>
</html>
