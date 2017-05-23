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
    <!-- <script src="js/jquery_ujs.js"></script>
    <script src="js/bootstrap.min.js"></script> -->
</head>

<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li class="active"><a href="javascript:void(0);">页面样式列表</a></li><li class="active"><a href="javascript:void(0);">实施页面</a></li>
		</ul>
	  </div>
   </div>
        <!--=== End Breadcrumbs ===-->

        <!--=== Content Part ===-->
        <div class="container content height-350">
        <div class="ml50 mt10">
	    			<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
        		  </div>
            <div class="row mt5">
                <!-- Begin Content -->
                  <div class="col-md-12 col-sm-12 col-xs-12" style="min-height:400px;">
                      <div class="col-md-3 col-sm-12 col-xs-12 md-margin-bottom-40" id="show_tree_div">
	                     <ul class="btn_list">
						   <li class="active">项目信息</li>
						   <li>拟制招标文件</li>
						   <li>确认招标文件</li>
						   <li>拟制招标公告</li>
						   <li>发售标书</li>
						   <li>抽取评审专家</li>
						   <li>投标开标</li>
						   <li>唱标</li>
						   <li>组织专家评审</li>
						   <li>拟制中标公告</li>
						   <li>确认中标供应商</li>
						 </ul>
					  </div>
                      <div class="tag-box tag-box-v4 col-md-9 col-sm-12 col-xs-12">
	                     <div class="col-md-12 col-sm-12 col-xs-12 p0">
						   <ul class="flow_step">
						     <li class="active">
							   <a aria-expanded="true" href="#tab-1" data-toggle="tab">符合性</a>
							   <i></i>
							 </li>
							 
							 <li>
							   <a aria-expanded="false" href="#tab-2" data-toggle="tab">符合性关联</a>
							   <i></i>							  
							 </li>
						     <li>
							   <a aria-expanded="false" href="#tab-3" data-toggle="tab"> 评标细则</a>
							   <i></i>
							 </li>
							 <li>
							   <a aria-expanded="false" href="#tab-4" data-toggle="tab"> 招标文件</a>
							 </li>
						   </ul>
						 </div>
						 <div class="tab-content clear step_cont">
						 <div class="col-md-12 tab-pane active"  id="tab-1">
						  <h2 class="count_flow fl"><i>01</i>符合性条款</h2>
						  <div class="fr pr15 mt10">
						    <button class="btn btn-windows delete">删除</button>
							<button class="btn btn-windows edit">修改</button>
							<button class="btn btn-windows add">新增</button>
							
						  </div>
						  <div class="col-md-12 col-sm-12 col-xs-12">
						    <table class="table table-striped table-bordered table-hover ">
							  <thead>
							    <tr>
								  <th class="w30"><input type="checkbox" class="mt0"/></th>
								  <th>符合性名称</th>
								</tr>
							 </thead>
							 <tbody>
							    <tr>
								  <td class="w30"><input type="checkbox" class="mt0"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							    <tr>
								  <td class="w30"><input type="checkbox" class="mt0"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							  </tbody>
							</table>
						  
						  </div>
						  <h2 class="count_flow fl"><i>02</i>资格性条款</h2>
						  <div class="fr pr15 mt10">
						    <button class="btn btn-windows add">新增</button>
							<button class="btn btn-windows edit">修改</button>
							<button class="btn btn-windows delete">删除</button>
						  </div>
						  <div class="col-md-12 col-sm-12 col-xs-12">
						    <table class="table table-striped table-bordered table-hover ">
							  <thead>
							    <tr>
								  <th class="w30"><input type="checkbox" class="mt0"/></th>
								  <th>符合性名称</th>
								</tr>
							 </thead>
							 <tbody>
							    <tr>
								  <td class="w30"><input type="checkbox" class="mt0"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							    <tr>
								  <td class="w30"><input type="checkbox" class="mt0"/></td>
								  <td>赶快写赶快写赶快写赶快写赶快写赶快写</td>
								</tr>
							  </tbody>
							</table>
						  </div>
						 </div>
						 <div class="col-md-12 col-sm-12 col-xs-12 tab-pane" id="tab-2">222</div>				 
						 <div class="col-md-12 col-sm-12 col-xs-12 tab-pane" id="tab-3">333</div>	
						 <div class="col-md-12 col-sm-12 col-xs-12 tab-pane" id="tab-4">444</div>
                      </div>
                   </div>
				  </div>
                </div>
                <!-- End Content -->
            </div>
        </div><!--/container-->
        <!--=== End Content Part ===-->
    </div>
</body>
</html>
